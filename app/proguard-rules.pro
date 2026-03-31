# Add project specific ProGuard rules here.
# You can control the AAPT2 setting by using this property.
# Set your own version using the versionName property in the build.gradle

# 保留数据模型
-keep class com.alang.goldisland.model.** { *; }

# 保留服务
-keep class com.alang.goldisland.service.** { *; }

# 保留 UI 类
-keep class com.alang.goldisland.ui.** { *; }

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
