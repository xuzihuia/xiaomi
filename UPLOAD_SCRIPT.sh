#!/bin/bash

# 黄金灵动岛 - 快速上传到 GitHub 脚本
# 使用方法：./UPLOAD_SCRIPT.sh

set -e

echo "🐱 黄金灵动岛 - GitHub 上传脚本"
echo "================================"

# 检查 Git 是否安装
if ! command -v git &> /dev/null; then
    echo "❌ Git 未安装，请先安装 Git"
    echo "Ubuntu/Debian: sudo apt install git"
    echo "CentOS: sudo yum install git"
    exit 1
fi

# 检查是否在正确的目录
if [ ! -f "build.gradle" ]; then
    echo "❌ 请在项目根目录运行此脚本"
    exit 1
fi

# 初始化 Git 仓库（如果还没有）
if [ ! -d ".git" ]; then
    echo "📦 初始化 Git 仓库..."
    git init
fi

# 添加远程仓库
echo ""
echo "请输入你的 GitHub 仓库地址："
echo "格式：https://github.com/YOUR_USERNAME/gold-price-dynamic-island.git"
read -p "仓库地址：" REPO_URL

if [ -z "$REPO_URL" ]; then
    echo "❌ 仓库地址不能为空"
    exit 1
fi

echo ""
echo "🔗 添加远程仓库..."
git remote add origin "$REPO_URL" || {
    echo "⚠️  远程仓库已存在，继续..."
}

# 添加所有文件
echo "📁 添加项目文件..."
git add .

# 提交
echo "💾 提交更改..."
git commit -m "Initial commit: Gold Price Dynamic Island App v1.0.0" || {
    echo "⚠️  没有更改需要提交"
}

# 推送
echo "🚀 推送到 GitHub..."
git branch -M main 2>/dev/null || true
git push -u origin main

echo ""
echo "✅ 上传完成！"
echo ""
echo "📱 下一步："
echo "1. 访问你的 GitHub 仓库"
echo "2. 点击 Actions 标签查看构建进度"
echo "3. 构建完成后下载 APK"
echo ""
echo "📖 详细指南：查看 GITHUB_ACTIONS_GUIDE.md"
echo ""
