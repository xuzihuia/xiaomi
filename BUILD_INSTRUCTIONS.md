# 构建 APK 说明

## ⚠️ 当前状态

由于当前环境没有安装 Android SDK 和 Gradle，无法直接生成 APK 文件。

## 📋 构建方式（3 选 1）

### 方式 1: 使用 Android Studio（最简单）✅ 推荐

1. **下载安装 Android Studio**
   - 官网：https://developer.android.com/studio
   - 下载对应版本（Windows/Mac/Linux）

2. **打开项目**
   ```
   1. 启动 Android Studio
   2. File -> Open
   3. 选择项目目录：/home/node/clawd/projects/gold-price-dynamic-island
   4. 点击 OK
   ```

3. **等待 Gradle 同步**
   - 首次打开会自动下载依赖
   - 等待底部进度条完成

4. **构建 APK**
   ```
   Build -> Build Bundle(s) / APK(s) -> Build APK(s)
   ```

5. **获取 APK**
   ```
   位置：app/build/outputs/apk/debug/app-debug.apk
   ```

### 方式 2: 在本地电脑构建

如果你有本地电脑（Windows/Mac/Linux）：

1. **安装必要软件**
   - JDK 17+: https://adoptium.net/
   - Android Studio 或 Android SDK Command Line Tools

2. **传输项目文件**
   ```bash
   # 使用 scp 或其他方式传输项目
   scp -r root@165.99.43.87:/home/node/clawd/projects/gold-price-dynamic-island ~/
   ```

3. **在本地构建**
   - 用 Android Studio 打开项目
   - 或者使用命令行：`./gradlew assembleDebug`

### 方式 3: 使用在线构建服务

可以使用以下在线服务：
- **GitHub Actions** - 自动构建
- **Codemagic** - 移动应用 CI/CD
- **Bitrise** - 移动应用构建

---

## 🔧 快速部署到小米设备

### 步骤 1: 准备设备
```
1. 进入设置 -> 我的设备 -> 连续点击"版本号"7 次（启用开发者选项）
2. 设置 -> 更多设置 -> 开发者选项 -> 开启"USB 调试"
3. 用数据线连接电脑
```

### 步骤 2: 安装 APK
```bash
# 如果已安装 ADB 工具
adb install app/build/outputs/apk/debug/app-debug.apk

# 或者直接在文件管理器中点击 APK 安装
```

### 步骤 3: 授权权限
```
1. 打开应用
2. 允许通知权限
3. 点击"启动服务"
```

---

## 📦 预编译 APK（可选）

如果你需要预编译的 APK 文件，可以：

### 选项 A: 使用云构建
1. 将项目上传到 GitHub
2. 使用 GitHub Actions 自动构建
3. 下载构建好的 APK

### 选项 B: 找朋友帮忙
1. 把项目文件发给有 Android Studio 的朋友
2. 让他帮忙构建 APK
3. 传输回来安装

---

## 🎯 最简单的方案

**推荐方案**：
1. 在电脑上安装 Android Studio（免费）
2. 打开项目
3. 点击 Build -> Build APK
4. 完成！

**时间**：约 15-30 分钟（含下载时间）

---

## ❓ 常见问题

### Q: 我没有电脑怎么办？
A: 可以使用网吧、学校机房的电脑，或者找朋友帮忙。

### Q: Android Studio 太大（2GB+）？
A: 可以只下载 Android SDK Command Line Tools（约 100MB）

### Q: 构建失败？
A: 检查：
- JDK 版本是否 >= 17
- 网络连接是否正常
- 项目文件是否完整

### Q: 安装失败？
A: 检查：
- 是否允许"未知来源"安装
- 设备架构是否兼容（armeabi-v7a, arm64-v8a）
- Android 版本是否 >= 8.0

---

## 📞 获取帮助

如果遇到问题，可以：
1. 查看项目 README.md
2. 查看 Android Studio 错误日志
3. 搜索错误信息

---

**需要我帮你准备其他东西吗？** 🐱
