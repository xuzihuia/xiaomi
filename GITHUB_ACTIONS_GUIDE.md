# GitHub Actions 自动构建 APK 指南 🚀

## 📋 方案三：使用 GitHub Actions 在线构建

本方案使用 GitHub Actions 自动构建 APK，无需安装任何软件！

---

## ⚡ 快速开始（5 步完成）

### 步骤 1: 创建 GitHub 仓库

1. 访问 https://github.com
2. 登录你的 GitHub 账号（没有就注册一个，免费）
3. 点击右上角 **+** -> **New repository**
4. 仓库名：`gold-price-dynamic-island`
5. 设为 **Public** 或 **Private** 都可以
6. 点击 **Create repository**

### 步骤 2: 上传项目代码

#### 方法 A: 使用 Git 命令行（推荐）

```bash
# 1. 进入项目目录
cd /home/node/clawd/projects/gold-price-dynamic-island

# 2. 初始化 Git 仓库
git init

# 3. 添加所有文件
git add .

# 4. 提交
git commit -m "Initial commit: Gold Price Dynamic Island App"

# 5. 添加远程仓库（替换为你的仓库地址）
git remote add origin https://github.com/YOUR_USERNAME/gold-price-dynamic-island.git

# 6. 推送到 GitHub
git push -u origin main
```

#### 方法 B: 使用 GitHub Desktop

1. 下载 GitHub Desktop: https://desktop.github.com/
2. 添加项目到 Git
3. 推送到 GitHub

#### 方法 C: 手动上传

1. 在 GitHub 仓库页面点击 **uploading an existing file**
2. 拖拽所有项目文件
3. 点击 **Commit changes**

### 步骤 3: 配置 GitHub Actions

**已自动完成！** 

项目中的 `.github/workflows/android-build.yml` 文件已经配置好，推送到 GitHub 后会自动触发构建。

### 步骤 4: 查看构建进度

1. 进入你的 GitHub 仓库页面
2. 点击顶部 **Actions** 标签
3. 查看构建进度（绿色表示成功，红色表示失败）
4. 等待构建完成（约 5-10 分钟）

### 步骤 5: 下载 APK

1. 点击最近的构建记录
2. 在页面底部找到 **Artifacts** 区域
3. 点击 `app-debug` 或 `app-release` 下载 APK
4. 下载到手机安装即可

---

## 🔧 手动触发构建

如果推送后没有自动构建，可以：

1. 进入仓库 -> **Actions** 标签
2. 左侧选择 **Android APK Build**
3. 点击右上角 **Run workflow**
4. 选择分支（main/master）
5. 点击 **Run workflow**

---

## 📱 下载和安装 APK

### 下载方式

**方式 1: GitHub Actions 下载**
```
1. Actions -> 选择构建记录
2. 页面底部 Artifacts -> 点击 app-debug 或 app-release
3. 下载 ZIP 文件
4. 解压得到 APK
```

**方式 2: Release 下载**（推荐）
```
1. 仓库 -> Releases
2. 下载最新版本 APK
```

### 安装到小米设备

```
1. 下载 APK 到手机
2. 设置 -> 密码与安全 -> 授权管理 -> 安装未知应用 -> 允许
3. 点击 APK 文件安装
4. 打开应用，授予权限
5. 启动服务，享受实时金价！
```

---

## 🎯  GitHub Actions 配置说明

### 构建配置

```yaml
name: Android APK Build

on:
  push:              # 推送时触发
  pull_request:      # PR 时触发
  workflow_dispatch: # 手动触发
```

### 构建环境

- **系统**: Ubuntu latest
- **JDK**: 17
- **Gradle**: 8.2
- **Android SDK**: 自动安装

### 构建产物

- `app-debug.apk` - 调试版（7 天有效）
- `app-release.apk` - 发布版（7 天有效）

---

## ⚠️ 注意事项

### 1. 存储空间
- GitHub Actions 每月免费额度：2000 分钟
- 每次构建约 5-10 分钟
- 足够个人使用

### 2. 保留时间
- Artifacts 默认保留 7 天
- 重要 APK 建议及时下载

### 3. 私有仓库
- 私有仓库也可以使用 Actions
- 免费额度相同

### 4. 构建失败
查看 Actions 日志：
```
1. Actions -> 选择构建
2. 查看 build 步骤
3. 找到错误信息
4. 修复后重新推送
```

---

## 🚀 进阶用法

### 自动发布 Release

创建 `.github/workflows/release.yml`:

```yaml
name: Release APK

on:
  push:
    tags:
      - 'v*'

jobs:
  release:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      
      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
      
      - name: Build Release APK
        run: ./gradlew assembleRelease
      
      - name: Create Release
        uses: softprops/action-gh-release@v1
        with:
          files: app/build/outputs/apk/release/app-release.apk
```

### 设置定时构建

```yaml
on:
  schedule:
    - cron: '0 0 * * 1'  # 每周一构建
```

---

## 📞 常见问题

### Q: 构建失败怎么办？
A: 
1. 查看 Actions 日志
2. 检查错误信息
3. 修复代码后重新推送

### Q: 下载链接失效？
A: Artifacts 7 天后失效，重新触发构建即可

### Q: 构建太慢？
A: 
- 使用缓存：`actions/cache@v4`
- 选择更快的 Runner

### Q: 如何自动发布？
A: 使用上面的 release.yml 配置

---

## 🎉 完成！

现在你已经：
- ✅ 创建了 GitHub 仓库
- ✅ 上传了项目代码
- ✅ 配置了自动构建
- ✅ 可以下载 APK 了！

**享受你的黄金价格灵动岛 APP 吧！** 📈✨

---

**需要帮助？** 
- 查看 GitHub Actions 文档：https://docs.github.com/actions
- 查看项目 README.md
- 随时问我！🐱
