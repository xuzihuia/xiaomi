package com.alang.goldisland.model

/**
 * 黄金价格数据模型
 */
data class GoldPrice(
    val price: Double,           // 当前价格
    val change: Double,          // 涨跌额
    val changePercent: Double,   // 涨跌幅百分比
    val high: Double,            // 最高价
    val low: Double,             // 最低价
    val open: Double,            // 开盘价
    val timestamp: Long          // 时间戳
) {
    // 价格状态：上涨、下跌、持平
    val priceStatus: PriceStatus
        get() = when {
            change > 0 -> PriceStatus.UP
            change < 0 -> PriceStatus.DOWN
            else -> PriceStatus.UNCHANGED
        }
    
    // 格式化价格显示
    fun formatPrice(): String = "¥${String.format("%.2f", price)}"
    
    // 格式化涨跌显示
    fun formatChange(): String {
        val sign = when {
            change > 0 -> "+"
            change < 0 -> ""
            else -> ""
        }
        return "$sign${String.format("%.2f", change)} (${formatPercent()})"
    }
    
    private fun formatPercent(): String {
        val sign = when {
            changePercent > 0 -> "+"
            changePercent < 0 -> ""
            else -> ""
        }
        return "${sign}${String.format("%.2f", changePercent)}%"
    }
}

enum class PriceStatus {
    UP, DOWN, UNCHANGED
}
