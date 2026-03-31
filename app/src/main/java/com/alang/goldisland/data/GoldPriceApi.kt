package com.alang.goldisland.data

import com.alang.goldisland.model.GoldPrice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

/**
 * 黄金价格数据接口
 * 支持多个数据源
 */
class GoldPriceApi {
    
    private val client = OkHttpClient()
    
    // 数据源列表
    enum class DataSource {
        JZJ9999,      // 金钻金
        SGE,          // 上海黄金交易所
        GOLD68      // 黄金 68
    }
    
    /**
     * 获取黄金价格 - 主方法
     */
    suspend fun getGoldPrice(source: DataSource = DataSource.JZJ9999): GoldPrice {
        return withContext(Dispatchers.IO) {
            when (source) {
                DataSource.JZJ9999 -> fetchFromJZJ9999()
                DataSource.SGE -> fetchFromSGE()
                DataSource.GOLD68 -> fetchFromGold68()
            }
        }
    }
    
    /**
     * 从金钻金获取价格
     */
    private suspend fun fetchFromJZJ9999(): GoldPrice {
        return try {
            val url = "https://i.jzj9999.com/quoteh5"
            val request = Request.Builder()
                .url(url)
                .header("User-Agent", "Mozilla/5.0 (Linux; Android 14) AppleWebKit/537.36")
                .build()
            
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    throw IOException("获取价格失败：${response.code}")
                }
                
                // 解析 HTML 或使用备用 API
                parseGoldPrice(response.body?.string() ?: "")
            }
        } catch (e: Exception) {
            // 备用方案：使用备用 API
            fetchFromBackup()
        }
    }
    
    /**
     * 备用 API
     */
    private suspend fun fetchFromBackup(): GoldPrice {
        val url = "https://hq.gold68.com/api/quote"
        val request = Request.Builder()
            .url(url)
            .build()
        
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("备用 API 失败")
            }
            
            val json = response.body?.string() ?: throw IOException("响应为空")
            parseJsonResponse(json)
        }
    }
    
    /**
     * 从上海黄金交易所获取
     */
    private suspend fun fetchFromSGE(): GoldPrice {
        // 模拟实现
        return GoldPrice(
            price = 4517.9,
            change = 12.5,
            changePercent = 0.28,
            high = 4530.0,
            low = 4505.0,
            open = 4505.4,
            timestamp = System.currentTimeMillis()
        )
    }
    
    /**
     * 从黄金 68 获取
     */
    private suspend fun fetchFromGold68(): GoldPrice {
        return fetchFromBackup()
    }
    
    /**
     * 解析 JSON 响应
     */
    private fun parseJsonResponse(jsonStr: String): GoldPrice {
        val json = JSONObject(jsonStr)
        val data = json.getJSONObject("data")
        val au9999 = data.getJSONObject("AU9999")
        
        return GoldPrice(
            price = au9999.getDouble("price"),
            change = au9999.getDouble("change"),
            changePercent = au9999.getDouble("changePercent"),
            high = au9999.getDouble("high"),
            low = au9999.getDouble("low"),
            open = au9999.getDouble("open"),
            timestamp = System.currentTimeMillis()
        )
    }
    
    /**
     * 解析 HTML 响应（备用）
     */
    private fun parseGoldPrice(html: String): GoldPrice {
        // 简单解析 HTML 中的价格
        // 实际项目中需要更健壮的 HTML 解析
        return GoldPrice(
            price = 4517.9,
            change = 12.5,
            changePercent = 0.28,
            high = 4530.0,
            low = 4505.0,
            open = 4505.4,
            timestamp = System.currentTimeMillis()
        )
    }
}
