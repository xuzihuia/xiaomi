package com.alang.goldisland

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.datastore.preferences.preferencesDataStore

class GoldIslandApplication : Application() {

    companion object {
        const val PRICE_CHANNEL_ID = "gold_price_channel"
        const val ISLAND_CHANNEL_ID = "gold_island_channel"
        
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        // 黄金价格通知渠道
        val priceChannel = NotificationChannel(
            PRICE_CHANNEL_ID,
            getString(R.string.channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "黄金价格更新通知"
            enableLights(true)
            lightColor = getColor(R.color.gold_primary)
        }

        // 超级岛通知渠道
        val islandChannel = NotificationChannel(
            ISLAND_CHANNEL_ID,
            getString(R.string.dynamic_island_name),
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "小米超级岛通知"
            setShowBadge(false)
        }

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(priceChannel)
        notificationManager.createNotificationChannel(islandChannel)
    }
}
