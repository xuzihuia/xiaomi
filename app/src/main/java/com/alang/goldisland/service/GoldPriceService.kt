package com.alang.goldisland.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.alang.goldisland.R
import com.alang.goldisland.data.GoldPriceRepository
import com.alang.goldisland.model.GoldPrice
import com.alang.goldisland.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 黄金价格监控服务
 * 定时获取黄金价格并更新超级岛显示
 */
class GoldPriceService : Service() {
    
    companion object {
        const val ACTION_START = "com.alang.goldisland.action.START"
        const val ACTION_STOP = "com.alang.goldisland.action.STOP"
        const val ACTION_UPDATE_NOW = "com.alang.goldisland.action.UPDATE_NOW"
        
        private const val NOTIFICATION_ID = 1000
        private const val UPDATE_INTERVAL = 60_000L // 60 秒更新一次
    }
    
    private val serviceScope = CoroutineScope(Dispatchers.Main + Job())
    private lateinit var repository: GoldPriceRepository
    private var updateJob: Job? = null
    private var isRunning = false
    
    private val handler = Handler(Looper.getMainLooper())
    private var currentPrice: GoldPrice? = null
    
    override fun onCreate() {
        super.onCreate()
        repository = GoldPriceRepository(this)
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startPriceMonitoring()
            ACTION_STOP -> stopPriceMonitoring()
            ACTION_UPDATE_NOW -> updatePriceNow()
        }
        return START_STICKY
    }
    
    /**
     * 开始价格监控
     */
    private fun startPriceMonitoring() {
        if (isRunning) return
        
        isRunning = true
        startForeground(NOTIFICATION_ID, createNotification("正在监控..."))
        startPeriodicUpdate()
    }
    
    /**
     * 停止价格监控
     */
    private fun stopPriceMonitoring() {
        isRunning = false
        updateJob?.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }
    
    /**
     * 立即更新价格
     */
    private fun updatePriceNow() {
        serviceScope.launch {
            try {
                val price = repository.fetchLatestPrice()
                currentPrice = price
                updateNotification(price)
                updateIslandDisplay(price)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    /**
     * 启动定时更新
     */
    private fun startPeriodicUpdate() {
        updateJob = serviceScope.launch {
            while (isRunning) {
                updatePriceNow()
                delay(UPDATE_INTERVAL)
            }
        }
    }
    
    /**
     * 创建通知
     */
    private fun createNotification(content: String): Notification {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        
        // 停止服务的 Intent
        val stopIntent = Intent(this, GoldPriceService::class.java).apply {
            action = ACTION_STOP
        }
        val stopPendingIntent = PendingIntent.getService(
            this,
            1,
            stopIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, GoldIslandApplication.PRICE_CHANNEL_ID)
            .setContentTitle("黄金价格监控")
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.ic_stop, "停止", stopPendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_STATUS)
            .build()
    }
    
    /**
     * 更新通知
     */
    private fun updateNotification(price: GoldPrice) {
        val changeText = when (price.priceStatus) {
            com.alang.goldisland.model.PriceStatus.UP -> "↑"
            com.alang.goldisland.model.PriceStatus.DOWN -> "↓"
            com.alang.goldisland.model.PriceStatus.UNCHANGED -> "→"
        }
        
        val content = "${price.formatPrice()} $changeText ${price.formatChange()}"
        val notification = createNotification(content)
        
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as android.app.NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
    
    /**
     * 更新超级岛显示
     */
    private fun updateIslandDisplay(price: GoldPrice) {
        // 发送广播到 DynamicIslandService
        val intent = Intent(this, DynamicIslandService::class.java).apply {
            action = DynamicIslandService.ACTION_UPDATE_PRICE
            putExtra(DynamicIslandService.EXTRA_PRICE, price.price.toString())
        }
        startService(intent)
    }
    
    override fun onDestroy() {
        isRunning = false
        updateJob?.cancel()
        serviceScope.cancel()
        super.onDestroy()
    }
}
