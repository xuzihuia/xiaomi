package com.alang.goldisland.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.alang.goldisland.service.GoldPriceService

/**
 * 开机自启动接收器
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == "android.intent.action.QUICKBOOT_POWERON") {
            
            // 启动价格监控服务
            val serviceIntent = Intent(context, GoldPriceService::class.java).apply {
                action = GoldPriceService.ACTION_START
            }
            context.startService(serviceIntent)
        }
    }
}
