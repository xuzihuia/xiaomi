# 小米澎湃 OS 3.0 适配指南

## 🎯 超级岛（灵动岛）接入详解

### 澎湃 OS 3.0 特性

小米澎湃 OS 3.0 的"超级岛"是位于屏幕顶部居中的胶囊状交互区域，支持显示：
- 通知内容
- 进度条
- 操作按钮
- 实时数据（如黄金价格）

### 接入方式

本项目采用 **客户端实现** 方式，这是最简单且官方推荐的接入方式。

#### 1. 通知栏配置

```kotlin
// 在 GoldIslandApplication.kt 中创建通知渠道
val islandChannel = NotificationChannel(
    ISLAND_CHANNEL_ID,
    "超级岛通知",
    NotificationManager.IMPORTANCE_LOW  // 低优先级，避免打扰
).apply {
    description = "小米超级岛通知"
    setShowBadge(false)  // 不显示角标
}
```

#### 2. 发送岛通知

```kotlin
val notification = NotificationCompat.Builder(this, ISLAND_CHANNEL_ID)
    .setSmallIcon(R.drawable.ic_notification)
    .setContentTitle("黄金价格")
    .setContentText("AU9999: ¥4517.90")
    .setSubText("+12.50 (+0.28%)")
    .setOngoing(true)  // 常驻通知
    .setPriority(NotificationCompat.PRIORITY_LOW)
    .build()

notificationManager.notify(NOTIFICATION_ID, notification)
```

#### 3. 小米扩展参数（可选）

如果需要更深度集成，可以添加小米专属参数：

```kotlin
if (Build.MANUFACTURER.lowercase().contains("xiaomi")) {
    // 设置超级岛焦点通知
    extrasBundle.putBoolean("mipush_focus_island", true)
    extrasBundle.putString("mipush_island_title", "黄金价格")
    extrasBundle.putString("mipush_island_content", "AU9999: ¥4517.90")
}
```

### 澎湃 OS 3.0 兼容模式

对于不支持超级岛的老设备，应用会自动降级为标准通知栏显示。

## 📱 澎湃 OS 3.0 设备适配

### 支持设备（预测）

根据小米官方信息，以下设备预计支持澎湃 OS 3.0 和超级岛：

- ✅ Xiaomi 16 / 16 Pro（出厂自带）
- ✅ Xiaomi 15 系列
- ✅ Xiaomi 14 系列
- ✅ Xiaomi 13 系列
- ✅ MIX Fold 系列
- ✅ Redmi K70 系列

### 系统要求

- **最低版本**: 澎湃 OS 3.0 (Android 14)
- **推荐版本**: 澎湃 OS 3.0 正式版
- **屏幕要求**: 支持灵动岛显示（顶部居中）

## 🔧 澎湃 OS 专属优化

### 1. 后台保活

澎湃 OS 对后台应用管理较严格，建议：

```kotlin
// 在 AndroidManifest.xml 中声明前台服务
<service
    android:name=".service.GoldPriceService"
    android:enabled="true"
    android:exported="false"
    android:foregroundServiceType="specialUse">
    <property
        android:name="android.app.PROPERTY_SPECIAL_USE_FGS_SUBTYPE"
        android:value="gold_price_monitor" />
</service>
```

### 2. 开机自启

澎湃 OS 允许应用开机自启，但需要用户授权：

```kotlin
// 在 AndroidManifest.xml 中
<receiver
    android:name=".util.BootReceiver"
    android:enabled="true"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED" />
    </intent-filter>
</receiver>
```

### 3. 电量优化白名单

引导用户将应用加入白名单：

```
设置 -> 省电与电池 -> 点击右上角齿轮 -> 应用智能省电 -> 黄金灵动岛 -> 无限制
```

## 📋 上架小米应用商店

### 1. 注册开发者账号

1. 访问 [小米开放平台](https://dev.mi.com/)
2. 注册开发者账号
3. 完成实名认证

### 2. 创建应用

1. 登录开发者后台
2. 应用管理 -> 创建应用
3. 填写应用信息
4. 上传 APK

### 3. 配置超级岛权限

1. 进入应用管理页面
2. 选择"能力配置" -> "超级岛"
3. 上传配置指纹证书
4. 提交上岛场景预审

### 4. 审核要点

- ✅ 应用功能完整可用
- ✅ 超级岛显示正常
- ✅ 无违规内容
- ✅ 隐私政策完善

## 🐛 澎湃 OS 特有问题解决

### 问题 1: 通知不显示

**原因**: 澎湃 OS 通知管理严格

**解决**:
1. 引导用户开启通知权限
2. 使用前台服务
3. 设置合理的通知优先级

### 问题 2: 后台被杀

**原因**: 系统内存优化

**解决**:
1. 使用前台服务
2. 引导用户锁定应用
3. 加入电池优化白名单

### 问题 3: 超级岛不显示

**原因**: 设备不支持或未适配

**解决**:
1. 检查设备系统版本
2. 确认设备在支持列表
3. 使用标准通知作为降级方案

## 📊 性能优化建议

### 1. 网络请求优化

```kotlin
// 使用 OkHttp 连接池
val client = OkHttpClient.Builder()
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(10, TimeUnit.SECONDS)
    .connectionPool(ConnectionPool(5, 5, TimeUnit.MINUTES))
    .build()
```

### 2. 电量优化

```kotlin
// 合理设置更新间隔
private const val UPDATE_INTERVAL = 60_000L  // 60 秒
// 避免频繁唤醒
```

### 3. 内存优化

```kotlin
// 及时取消协程
override fun onDestroy() {
    serviceScope.cancel()
    super.onDestroy()
}
```

## 📝 测试清单

### 功能测试
- [ ] 价格实时更新
- [ ] 超级岛显示正常
- [ ] 通知栏显示正常
- [ ] 点击跳转正确
- [ ] 服务启动/停止正常

### 兼容性测试
- [ ] 澎湃 OS 3.0 正式版
- [ ] 澎湃 OS 2.0（降级方案）
- [ ] 不同分辨率设备
- [ ] 不同 Android 版本

### 性能测试
- [ ] CPU 占用 < 5%
- [ ] 内存占用 < 50MB
- [ ] 网络请求 < 1 次/分钟
- [ ] 电量消耗 < 1%/小时

## 🔗 相关链接

- [小米澎湃 OS 开发者平台](https://dev.mi.com/xiaomihyperos/)
- [超级岛开发指南](https://dev.mi.com/xiaomihyperos/doc/detail?pId=1910)
- [通知适配指南](https://dev.mi.com/xiaomihyperos/doc/detail?pId=1825)
- [后台适配指南](https://dev.mi.com/xiaomihyperos/doc/detail?pId=1826)

---

**祝你的应用顺利上架小米应用商店！** 🎉
