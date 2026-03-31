# 黄金价格灵动岛 APP - 小米澎湃 OS 3.0

一款专为小米澎湃 OS 3.0 系统设计的黄金价格监控应用，支持超级岛（灵动岛）实时显示黄金价格。

## 📱 功能特性

### 核心功能
- ✅ **实时价格监控** - 每 60 秒自动更新 AU9999 黄金价格
- ✅ **超级岛显示** - 适配小米澎湃 OS 3.0 灵动岛，顶部胶囊状实时显示
- ✅ **价格涨跌提醒** - 红涨绿跌，清晰直观
- ✅ **后台持续运行** - 前台服务保活，确保数据实时更新
- ✅ **开机自启** - 可选开机自动启动监控服务

### 数据源
- 融通金实时行情
- 上海黄金交易所 AU9999
- 支持多数据源切换

### UI 特性
- 🎨 深色主题，护眼设计
- 💰 金色主题配色，符合黄金主题
- 📊 直观的价格卡片展示
- 🔔 通知栏实时显示

## 🏗️ 项目结构

```
app/src/main/
├── java/com/alang/goldisland/
│   ├── GoldIslandApplication.kt      # 应用入口
│   ├── model/
│   │   └── GoldPrice.kt              # 价格数据模型
│   ├── data/
│   │   ├── GoldPriceApi.kt           # API 接口
│   │   └── GoldPriceRepository.kt    # 数据仓库
│   ├── service/
│   │   ├── GoldPriceService.kt       # 价格监控服务
│   │   └── DynamicIslandService.kt   # 超级岛服务
│   ├── ui/
│   │   ├── MainActivity.kt           # 主界面
│   │   └── SettingsActivity.kt       # 设置界面
│   └── util/
│       └── BootReceiver.kt           # 开机自启动
├── res/
│   ├── layout/                       # 布局文件
│   ├── values/                       # 资源值
│   ├── drawable/                     # 图标资源
│   └── mipmap-hdpi/                  # 启动图标
└── AndroidManifest.xml               # 应用配置
```

## 🛠️ 技术栈

- **语言**: Kotlin 1.9.0
- **最低支持**: Android 8.0 (API 26)
- **目标版本**: Android 14 (API 34)
- **架构**: MVVM
- **依赖**:
  - AndroidX Core & AppCompat
  - Material Design Components
  - Kotlin Coroutines
  - DataStore (本地存储)
  - OkHttp3 & Retrofit (网络请求)

## 📦 构建说明

### 前置要求
1. Android Studio Hedgehog (2023.1.1) 或更高版本
2. JDK 17+
3. Android SDK 34

### 构建步骤

1. **克隆项目**
```bash
cd ~/clawd/projects/gold-price-dynamic-island
```

2. **使用 Android Studio 打开**
   - File -> Open -> 选择项目目录
   - 等待 Gradle 同步完成

3. **配置签名（可选）**
   - File -> Project Structure -> Signing
   - 创建或选择签名配置

4. **构建 APK**
   - Build -> Build Bundle(s) / APK(s) -> Build APK(s)
   - 或使用命令行：`./gradlew assembleDebug`

5. **安装到设备**
   - 连接小米设备（需开启开发者选项和 USB 调试）
   - Run -> Run 'app'

## ⚙️ 小米澎湃 OS 适配说明

### 超级岛接入方式

本项目使用两种方式接入小米超级岛：

#### 方式 1: 客户端实现（推荐）
通过标准通知栏 + 小米扩展参数实现：

```kotlin
NotificationCompat.Builder(context, CHANNEL_ID)
    .setProperty("mipush_focus_island", true)
    .setProperty("mipush_island_title", "黄金价格")
    .setProperty("mipush_island_content", price.formatPrice())
    .build()
```

#### 方式 2: MIPUSH 服务
通过小米推送服务发送焦点通知（需要接入小米推送 SDK）。

### 权限配置

在 `AndroidManifest.xml` 中已配置：

```xml
<!-- 前台服务权限 -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_SPECIAL_USE" />

<!-- 通知权限 -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

<!-- 小米超级岛权限 -->
<uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />
```

### 澎湃 OS 3.0 特性适配

1. **灵动岛交互**: 支持点击展开详细信息
2. **实时数据**: 后台服务保持数据更新
3. **低优先级通知**: 避免打扰用户
4. **自适应显示**: 根据系统主题自动适配

## 🎯 使用说明

### 首次使用

1. **启动应用** - 打开"黄金灵动岛"APP
2. **授权通知** - 允许通知权限
3. **启动服务** - 点击"启动服务"按钮
4. **查看灵动岛** - 顶部灵动岛显示实时价格

### 日常使用

- **主界面**: 查看当前价格、涨跌幅
- **设置**: 调整更新间隔、基准价格
- **通知栏**: 快速查看价格，停止服务
- **灵动岛**: 顶部实时显示，无需打开 APP

### 超级岛显示内容

- **价格**: AU9999 实时价格
- **涨跌**: 涨跌额和涨跌幅
- **状态**: 上涨 (红色↑) / 下跌 (绿色↓) / 持平 (灰色→)

## 🔧 开发指南

### 修改数据源

编辑 `GoldPriceApi.kt`:

```kotlin
suspend fun getGoldPrice(): GoldPrice {
    return fetchFromJZJ9999()  // 或 fetchFromSGE()
}
```

### 调整更新频率

在 `GoldPriceService.kt` 中修改：

```kotlin
private const val UPDATE_INTERVAL = 60_000L  // 毫秒
```

### 自定义 UI 样式

修改 `res/values/colors.xml` 和 `themes.xml` 中的颜色配置。

## 📝 注意事项

1. **网络权限**: 需要联网获取实时价格
2. **后台运行**: 建议锁定应用防止被系统清理
3. **电量优化**: 建议将应用加入白名单
4. **小米账号**: 部分超级岛功能可能需要小米开发者认证

## 🚀 后续优化

- [ ] 接入小米推送 SDK，完善超级岛功能
- [ ] 添加价格预警功能
- [ ] 支持多币种黄金价格
- [ ] K 线图展示
- [ ] 历史价格查询
- [ ] 小组件（Widget）支持

## 📄 开源协议

MIT License

## 👨‍💻 开发者

阿亮 (AliangCat)

---

**享受实时掌握黄金价格的便利！** 📈📉
