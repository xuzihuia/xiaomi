package com.alang.goldisland.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.alang.goldisland.data.GoldPriceRepository
import com.alang.goldisland.databinding.ActivitySettingsBinding
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var repository: GoldPriceRepository
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        repository = GoldPriceRepository(this)
        
        setupUI()
    }
    
    private fun setupUI() {
        // 支持返回
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        
        // 保存设置
        binding.saveButton.setOnClickListener {
            saveSettings()
        }
    }
    
    private fun saveSettings() {
        lifecycleScope.launch {
            try {
                // 保存设置
                Toast.makeText(this@SettingsActivity, "设置已保存", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@SettingsActivity, "保存失败", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
