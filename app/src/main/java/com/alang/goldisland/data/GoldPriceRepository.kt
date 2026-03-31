package com.alang.goldisland.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.alang.goldisland.model.GoldPrice
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * 黄金价格数据仓库
 */
class GoldPriceRepository(private val context: Context) {
    
    private val api = GoldPriceApi()
    private val dataStore: DataStore<Preferences> = context.dataStore
    
    companion object {
        private val LAST_PRICE_KEY = doublePreferencesKey("last_price")
        private val LAST_UPDATE_KEY = longPreferencesKey("last_update")
        private val BASELINE_PRICE_KEY = doublePreferencesKey("baseline_price")
    }
    
    /**
     * 获取最新黄金价格
     */
    suspend fun fetchLatestPrice(): GoldPrice {
        val goldPrice = api.getGoldPrice()
        
        // 保存到最后更新
        saveLastPrice(goldPrice.price, goldPrice.timestamp)
        
        return goldPrice
    }
    
    /**
     * 获取最后保存的价格
     */
    val lastPrice: Flow<Double?> = context.dataStore.data
        .map { preferences -> preferences[LAST_PRICE_KEY] }
    
    /**
     * 获取基准价格
     */
    val baselinePrice: Flow<Double?> = context.dataStore.data
        .map { preferences -> preferences[BASELINE_PRICE_KEY] }
    
    /**
     * 保存最后价格
     */
    private suspend fun saveLastPrice(price: Double, timestamp: Long) {
        context.dataStore.edit { preferences ->
            preferences[LAST_PRICE_KEY] = price
            preferences[LAST_UPDATE_KEY] = timestamp
        }
    }
    
    /**
     * 设置基准价格
     */
    suspend fun setBaselinePrice(price: Double) {
        context.dataStore.edit { preferences ->
            preferences[BASELINE_PRICE_KEY] = price
        }
    }
}

// DataStore 扩展
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "gold_preferences")
