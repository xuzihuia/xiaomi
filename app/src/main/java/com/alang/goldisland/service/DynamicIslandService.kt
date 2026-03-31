package com.alang.goldisland.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.alang.goldisland.R
import com.alang.goldisland.model.GoldPrice
import com.alang.goldisland.ui.MainActivity

/**
 * 小米超级岛服务
 * 负责在灵动岛显示黄金价格
 */
class DynamicIslandService : Service() {
    
    companion object {
        const val ACTION_UPDATE_PRICE = "com.alang.goldisland.action.UPDATE_PRICE"
        const val ACTION_STOP_SERVICE = "com.alang.goldisland.action.STOP_SERVICE"
        const val EXTRA_PRICE = "extra_price"
        
        private const val NOTIFICATION_ID = 1001
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onCreate() {
        super.onCreate()
        startForegroundService()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_UPDATE_PRICE -> {
                val price = intent?.getStringExtra(EXTRA_PRICE)?.toDoubleOrNull()
                price?.let { updateIslandDisplay(it) }
            }
            ACTION_STOP_SERVICE -> {
                stopSelf()
            }
        }
        return START_STICKY
    }
    
    /**
     * 启动前台服务
     */
    private fun startForegroundService() {
        val notification = createNotification("加载中...")
        startForeground(NOTIFICATION_ID, notification)
    }
    
    /**
     * 创建通知（用于超级岛显示）
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
        
        return NotificationCompat.Builder(this, GoldIslandApplication.ISLAND_CHANNEL_ID)
            .setContentTitle("黄金价格")
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setCategory(NotificationCompat.CATEGORY_STATUS)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }
    
    /**
     * 更新超级岛显示
     */
    fun updateIslandDisplay(price: Double) {
        val content = "AU9999: ¥${String.format("%.2f", price)}"
        val notification = createNotification(content)
        
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as android.app.NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
    
    /**
     * 发送岛通知（小米超级岛专用）
     */
    fun sendIslandNotification(price: GoldPrice) {
        // 小米超级岛通知实现
        // 需要使用小米扩展 API
        
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        
        val notification = NotificationCompat.Builder(this, GoldIslandApplication.ISLAND_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("黄金价格")
            .setContentText(price.formatPrice())
            .setSubText(price.formatChange())
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .apply {
                // 小米超级岛扩展参数
                if (Build.MANUFACTURER.lowercase().contains("xiaomi")) {
                    setProperty("mipush_focus_island", true)
                    setProperty("mipush_island_title", "黄金价格")
                    setProperty("mipush_island_content", price.formatPrice())
                }
            }
            .build()
        
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as android.app.NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }
    
    override fun onDestroy() {
        super.onDestroy()
    }
}
