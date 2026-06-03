# GitHub Pages 部署完整指南

## 您的仓库信息

- **仓库地址**：https://github.com/endingocean/firstGithubProject
- **仓库名称**：firstGithubProject

---

## 第一步：将仓库设为公开（Public）

### 在 GitHub 网页上操作：

1. 登录 GitHub 账户
2. 进入仓库：https://github.com/endingocean/firstGithubProject
3. 点击右上角的 **Settings**（设置）
4. 在左侧菜单中找到 **Danger Zone**（危险区域）
5. 找到 **Change visibility**（更改可见性）选项
6. 点击 **Change visibility** 按钮
7. 输入仓库名称 `firstGithubProject` 确认
8. 选择 **Public** 并确认

⚠️ **注意**：公开仓库后，所有人都可以看到您的代码。

---

## 第二步：切换到纯静态版本

在本地项目目录中运行：

```bash
cd /workspace

# 切换到静态版本
./scripts/switch-to-static.sh
```

---

## 第三步：提交并推送代码

```bash
# 添加所有文件到暂存区
git add .

# 提交更改
git commit -m "配置静态版本用于 GitHub Pages 部署"

# 推送到 GitHub
git push origin main
```

---

## 第四步：启用 GitHub Pages

### 在 GitHub 网页上操作：

1. 进入仓库设置：https://github.com/endingocean/firstGithubProject/settings
2. 在左侧菜单中点击 **Pages**
3. **Build and deployment**（构建和部署）：
   - Source（源）：选择 **Deploy from a branch**
   - Branch（分支）：选择 **main**，文件夹选择 **/ (root)**
   - 点击 **Save**
4. 或者选择 **GitHub Actions** 作为源

> 💡 **提示**：我们之前已经配置了 GitHub Actions 工作流，所以也可以选择 GitHub Actions 作为源。

---

## 第五步：等待部署

1. 进入仓库的 **Actions** 页面查看部署状态
2. 点击最新的 workflow 运行
3. 等待 "Deploy to GitHub Pages" job 完成
4. 状态会变为绿色 ✓ 表示成功

---

## 第六步：访问您的网站

部署成功后，您可以在以下地址访问：

```
https://endingocean.github.io/firstGithubProject/
```

---

## 常见问题

### Q1: 为什么我的网站需要 `/firstGithubProject/` 路径？

因为您的仓库名不是 `endingocean.github.io`。GitHub Pages 会将仓库名作为路径的一部分。

### Q2: 部署需要多长时间？

通常 1-3 分钟。如果长时间处于 "pending" 状态，检查 Actions 日志。

### Q3: 部署失败了怎么办？

1. 点击失败的 workflow run
2. 查看具体的错误信息
3. 常见问题：
   - `npm ci` 失败：检查 package.json 是否正确
   - 构建失败：检查 Vue 代码是否有语法错误
   - 部署失败：确保已启用 GitHub Pages

### Q4: 如何更新网站内容？

修改 `src/main/vue/src/App.static.vue` 中的 `staticData` 对象，然后重新提交推送：

```bash
git add .
git commit -m "更新新闻数据"
git push origin main
```

网站会自动重新部署。

---

## 重要提醒

### 权限设置

由于您使用的是私有仓库，需要确保 GitHub Actions 有权限部署：

1. 进入仓库 Settings
2. 点击左侧 **Actions** → **General**
3. 找到 **Workflow permissions**（工作流权限）
4. 选择 **Read and write permissions**（读写权限）
5. 保存设置

### 公开仓库的注意事项

- 所有代码和提交历史都会公开
- 确保没有敏感信息（如 API 密钥、私钥等）
- 可以创建新的公开仓库来托管静态网站，原私有仓库保留后端代码

---

## 架构说明

当前配置：
- **静态版本**：纯前端，数据硬编码在前端代码中
- **部署平台**：GitHub Pages（免费）
- **自动部署**：每次推送到 main 分支自动触发

如需完整功能（实时数据），可考虑：
- 前端：部署到 GitHub Pages
- 后端：部署到 Railway/Render/Fly.io 等平台
