# 快速开始指南 🚀

## 5 分钟快速构建

### 方式 1: 使用 Android Studio（推荐）

```bash
# 1. 打开 Android Studio
# 2. File -> Open -> 选择项目目录
# 3. 等待 Gradle 同步完成
# 4. 点击运行按钮 (绿色三角形)
# 5. 选择设备或模拟器
```

### 方式 2: 命令行构建

```bash
cd ~/clawd/projects/gold-price-dynamic-island

# 构建 Debug 版本
./gradlew assembleDebug

# 构建 Release 版本（需要签名配置）
./gradlew assembleRelease

# APK 位置
# Debug: app/build/outputs/apk/debug/app-debug.apk
# Release: app/build/outputs/apk/release/app-release.apk
```

## 项目文件清单

```
gold-price-dynamic-island/
├── build.gradle                 # 项目级构建配置
├── settings.gradle              # 项目设置
├── gradle.properties            # Gradle 属性
├── gradlew                      # Gradle 包装器
├── README.md                    # 项目说明
├── XIAOMI_HYPEROS_GUIDE.md      # 小米澎湃 OS 适配指南
├── QUICK_START.md               # 快速开始指南
│
└── app/
    ├── build.gradle             # 应用级构建配置
    ├── proguard-rules.pro       # 混淆规则
    │
    └── src/main/
        ├── AndroidManifest.xml  # 应用清单
        │
        ├── java/com/alang/goldisland/
        │   ├── GoldIslandApplication.kt    # 应用入口
        │   ├── model/
        │   │   └── GoldPrice.kt            # 数据模型
        │   ├── data/
        │   │   ├── GoldPriceApi.kt         # API 接口
        │   │   └── GoldPriceRepository.kt  # 数据仓库
        │   ├── service/
        │   │   ├── GoldPriceService.kt     # 价格监控服务
        │   │   └── DynamicIslandService.kt # 超级岛服务
        │   ├── ui/
        │   │   ├── MainActivity.kt         # 主界面
        │   │   └── SettingsActivity.kt     # 设置界面
        │   └── util/
        │       └── BootReceiver.kt         # 开机自启
        │
        └── res/
            ├── layout/
            │   ├── activity_main.xml       # 主界面布局
            │   └── activity_settings.xml   # 设置界面布局
            ├── values/
            │   ├── strings.xml             # 字符串资源
            │   ├── colors.xml              # 颜色资源
            │   └── themes.xml              # 主题资源
            ├── drawable/                   # 图标资源
            └── mipmap-hdpi/                # 启动图标
```

## 核心功能模块

### 1. 数据层 (Data Layer)

**GoldPriceApi.kt** - 获取黄金价格
- 支持多个数据源
- 自动故障转移
- JSON 数据解析

**GoldPriceRepository.kt** - 数据仓库
- 数据持久化
- 本地缓存
- 数据流管理

### 2. 服务层 (Service Layer)

**GoldPriceService.kt** - 价格监控服务
- 定时获取价格（60 秒/次）
- 前台服务保活
- 通知更新

**DynamicIslandService.kt** - 超级岛服务
- 灵动岛显示
- 实时更新
- 交互处理

### 3. UI 层 (UI Layer)

**MainActivity.kt** - 主界面
- 价格展示
- 服务控制
- 状态显示

**SettingsActivity.kt** - 设置界面
- 更新间隔
- 基准价格
- 预警设置

## 数据流程图

```
用户启动应用
    │
    ↓
请求网络 API
    │
    ↓
获取黄金价格
    │
    ↓
保存到本地
    │
    ↓
更新 UI 显示
    │
    ↓
更新超级岛
    │
    ↓
60 秒后重复
```

## 权限说明

| 权限 | 用途 | 必需性 |
|------|------|--------|
| INTERNET | 获取实时金价 | 必需 |
| ACCESS_NETWORK_STATE | 检查网络状态 | 推荐 |
| FOREGROUND_SERVICE | 后台运行服务 | 必需 |
| POST_NOTIFICATIONS | 发送通知 | 必需 (Android 13+) |
| RECEIVE_BOOT_COMPLETED | 开机自启 | 可选 |

## 常见问题 FAQ

### Q1: 构建失败怎么办？

**A**: 检查以下几点：
1. Android Studio 版本是否 >= 2023.1.1
2. JDK 版本是否 >= 17
3. 网络连接是否正常
4. 尝试清理项目：Build -> Clean Project

### Q2: 运行后没有显示价格？

**A**: 
1. 检查网络连接
2. 检查是否授予通知权限
3. 查看 Logcat 日志
4. 确认数据源 API 是否可用

### Q3: 超级岛不显示？

**A**:
1. 确认设备是小米澎湃 OS 3.0
2. 检查通知权限是否开启
3. 查看通知设置中是否允许"悬浮显示"
4. 部分老设备可能不支持超级岛

### Q4: 如何修改更新频率？

**A**: 编辑 `GoldPriceService.kt`，修改：
```kotlin
private const val UPDATE_INTERVAL = 60_000L  // 毫秒
```

### Q5: 如何更换数据源？

**A**: 编辑 `GoldPriceApi.kt`，修改 `getGoldPrice()` 方法：
```kotlin
suspend fun getGoldPrice(): GoldPrice {
    return fetchFromYourSource()  // 使用你的数据源
}
```

## 下一步

1. **编译运行** - 在真机或模拟器上测试
2. **定制 UI** - 修改颜色、布局等
3. **接入真实 API** - 替换为你的数据源
4. **测试上架** - 提交到小米应用商店

## 获取帮助

- 查看 `README.md` 了解详细信息
- 查看 `XIAOMI_HYPEROS_GUIDE.md` 了解澎湃 OS 适配
- 查看源码注释了解实现细节

---

**开始你的黄金价格监控之旅吧！** 📈✨
