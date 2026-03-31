# 🚀 一键上传到 GitHub 并自动构建 APK

## 🎯 方案三完成指南

已为你准备好所有文件，现在只需 3 步：

---

## ⚡ 快速开始（3 步）

### 步骤 1️⃣: 创建 GitHub 仓库

1. 访问 https://github.com/new
2. 仓库名：`gold-price-dynamic-island`
3. 设为 Public 或 Private
4. **不要** 勾选 "Add a README file"
5. 点击 **Create repository**
6. 复制仓库地址（类似：`https://github.com/你的用户名/gold-price-dynamic-island.git`）

### 步骤 2️⃣: 上传项目

在终端运行：

```bash
cd /home/node/clawd/projects/gold-price-dynamic-island

# 初始化 Git
git init

# 添加所有文件
git add .

# 提交
git commit -m "Initial commit: Gold Price Dynamic Island"

# 添加远程仓库（替换为你的仓库地址）
git remote add origin https://github.com/你的用户名/gold-price-dynamic-island.git

# 推送
git push -u origin main
```

或者使用我准备的脚本：
```bash
cd /home/node/clawd/projects/gold-price-dynamic-island
./UPLOAD_SCRIPT.sh
# 按提示输入仓库地址
```

### 步骤 3️⃣: 下载 APK

1. 进入 GitHub 仓库页面
2. 点击顶部 **Actions** 标签
3. 等待构建完成（绿色勾）
4. 点击构建记录
5. 页面底部点击 `app-debug` 下载 APK
6. 发送到手机安装

---

## 📁 已准备好的文件

```
✅ .github/workflows/android-build.yml  - GitHub Actions 配置
✅ .gitignore                           - Git 忽略文件
✅ UPLOAD_SCRIPT.sh                     - 快速上传脚本
✅ GITHUB_ACTIONS_GUIDE.md              - 详细指南
✅ README_GITHUB.md                     - 本文件
✅ 所有项目源码
```

---

## 🎯 详细步骤说明

### 创建 GitHub 账号（如果需要）

1. 访问 https://github.com
2. 点击 **Sign up**
3. 填写邮箱、密码
4. 验证邮箱
5. 完成！

### 创建仓库

```
1. 登录后点击右上角 + -> New repository
2. Repository name: gold-price-dynamic-island
3. Description: 黄金价格灵动岛 APP - 小米澎湃 OS 3.0
4. Public/Private 都可以
5. 不要勾选 "Add a README file"
6. 点击 Create repository
```

### 上传代码

**方法 A: 使用 Git 命令（推荐）**

```bash
# 1. 进入项目目录
cd /home/node/clawd/projects/gold-price-dynamic-island

# 2. 初始化 Git（如果还没有）
git init

# 3. 添加所有文件
git add .

# 4. 提交
git commit -m "Initial commit"

# 5. 添加远程仓库
git remote add origin https://github.com/你的用户名/gold-price-dynamic-island.git

# 6. 推送
git push -u origin main
```

**方法 B: 使用上传脚本**

```bash
cd /home/node/clawd/projects/gold-price-dynamic-island
./UPLOAD_SCRIPT.sh
# 按提示输入仓库地址
```

**方法 C: 手动上传文件**

```
1. 在 GitHub 仓库页面点击 "uploading an existing file"
2. 拖拽所有项目文件
3. 点击 Commit changes
```

### 查看构建进度

```
1. 进入 GitHub 仓库
2. 点击 Actions 标签
3. 看到 "Android APK Build" 运行中
4. 等待 5-10 分钟
5. 绿色勾表示成功
```

### 下载 APK

```
1. Actions -> 点击最近的构建记录
2. 滚动到页面底部
3. 找到 Artifacts 区域
4. 点击 app-debug 下载
5. 解压 ZIP 文件
6. 得到 app-debug.apk
```

---

## 📱 安装到手机

### 方法 1: 数据线传输

```
1. 用数据线连接手机和电脑
2. 将 APK 复制到手机
3. 在手机上点击 APK 安装
```

### 方法 2: 云盘传输

```
1. 上传 APK 到网盘/微信/QQ
2. 在手机上下载
3. 点击安装
```

### 方法 3: 直接下载

```
1. 手机浏览器访问 GitHub
2. 进入 Actions 页面
3. 下载 APK
4. 安装
```

---

## ⚠️ 注意事项

### 1. 首次使用需要安装 Git

```bash
# Ubuntu/Debian
sudo apt install git

# CentOS
sudo yum install git

# macOS
xcode-select --install
```

### 2. GitHub 账号

- 免费账号即可使用
- 每月 2000 分钟构建时间
- 足够个人使用

### 3. 构建失败

```
1. 查看 Actions 日志
2. 找到错误信息
3. 修复代码
4. 重新推送
```

### 4. APK 下载链接失效

- Artifacts 保留 7 天
- 重新触发构建即可
- 可以创建 Release 永久保存

---

## 🎉 完成！

现在你已经：
- ✅ 创建了 GitHub 仓库
- ✅ 上传了项目代码  
- ✅ 自动构建了 APK
- ✅ 可以下载安装到小米手机

**享受你的黄金价格灵动岛 APP 吧！** 📈✨

---

## 📞 获取帮助

- 详细指南：`GITHUB_ACTIONS_GUIDE.md`
- GitHub 文档：https://docs.github.com/actions
- 项目文档：`README.md`
- 有问题随时问我！🐱
