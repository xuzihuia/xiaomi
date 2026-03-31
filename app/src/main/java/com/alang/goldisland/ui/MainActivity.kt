package com.alang.goldisland.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.alang.goldisland.R
import com.alang.goldisland.data.GoldPriceRepository
import com.alang.goldisland.databinding.ActivityMainBinding
import com.alang.goldisland.model.GoldPrice
import com.alang.goldisland.model.PriceStatus
import com.alang.goldisland.service.GoldPriceService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: GoldPriceRepository
    
    private var isServiceRunning = false
    private var currentPrice: GoldPrice? = null
    
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startPriceService()
        } else {
            Toast.makeText(this, "需要通知权限", Toast.LENGTH_SHORT).show()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        repository = GoldPriceRepository(this)
        
        setupUI()
        loadLastPrice()
    }
    
    override fun onResume() {
        super.onResume()
        checkServiceStatus()
    }
    
    private fun setupUI() {
        // 标题
        binding.titleText.text = "黄金价格灵动岛"
        
        // 切换服务按钮
        binding.toggleServiceButton.setOnClickListener {
            if (isServiceRunning) {
                stopPriceService()
            } else {
                checkPermissionAndStart()
            }
        }
        
        // 设置按钮
        binding.settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        
        // 超级岛开关
        binding.dynamicIslandSwitch.setOnCheckedChangeListener { _, isChecked ->
            onIslandSwitchChanged(isChecked)
        }
    }
    
    /**
     * 检查权限并开始服务
     */
    private fun checkPermissionAndStart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) -> {
                    startPriceService()
                }
                else -> {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            startPriceService()
        }
    }
    
    /**
     * 启动价格服务
     */
    private fun startPriceService() {
        val intent = Intent(this, GoldPriceService::class.java).apply {
            action = GoldPriceService.ACTION_START
        }
        startService(intent)
        updateServiceButton(true)
    }
    
    /**
     * 停止价格服务
     */
    private fun stopPriceService() {
        val intent = Intent(this, GoldPriceService::class.java).apply {
            action = GoldPriceService.ACTION_STOP
        }
        startService(intent)
        updateServiceButton(false)
    }
    
    /**
     * 检查服务状态
     */
    private fun checkServiceStatus() {
        // 简单实现：默认未运行
        updateServiceButton(false)
    }
    
    /**
     * 更新服务按钮状态
     */
    private fun updateServiceButton(running: Boolean) {
        isServiceRunning = running
        binding.toggleServiceButton.text = if (running) {
            getString(R.string.stop_service)
        } else {
            getString(R.string.start_service)
        }
    }
    
    /**
     * 加载最后的价格
     */
    private fun loadLastPrice() {
        lifecycleScope.launch {
            try {
                val price = repository.fetchLatestPrice()
                displayPrice(price)
            } catch (e: Exception) {
                // 显示错误
                binding.updateTimeText.text = "加载失败：${e.message}"
            }
        }
    }
    
    /**
     * 显示价格
     */
    private fun displayPrice(price: GoldPrice) {
        currentPrice = price
        
        // 当前价格
        binding.currentPriceText.text = price.formatPrice()
        
        // 价格变化
        binding.priceChangeText.text = price.formatChange()
        binding.priceChangeText.setTextColor(getColorForStatus(price.priceStatus))
        
        // 更新时间
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        binding.updateTimeText.text = "最后更新：${dateFormat.format(price.timestamp)}"
    }
    
    /**
     * 获取状态颜色
     */
    private fun getColorForStatus(status: PriceStatus): Int {
        return when (status) {
            PriceStatus.UP -> getColor(R.color.price_up)
            PriceStatus.DOWN -> getColor(R.color.price_down)
            PriceStatus.UNCHANGED -> getColor(R.color.price_unchanged)
        }
    }
    
    /**
     * 超级岛开关变化
     */
    private fun onIslandSwitchChanged(isChecked: Boolean) {
        if (isChecked) {
            Toast.makeText(this, "已启用超级岛", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "已禁用超级岛", Toast.LENGTH_SHORT).show()
        }
    }
}
