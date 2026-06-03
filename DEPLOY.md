# 部署指南

## 重要提示

本项目是 Spring Boot + Vue 3 全栈应用，**GitHub Pages 仅支持静态网站**，无法运行后端服务。

## 部署方案

### 方案一：纯静态版本（最简单，推荐先试这个）

使用硬编码的演示数据，完全部署到 GitHub Pages，无需后端。

#### 步骤：

1. **切换到纯静态版本**：
   ```bash
   ./scripts/switch-to-static.sh
   ```

2. **配置 GitHub Pages**：
   - 在仓库设置中，找到 Pages 选项
   - 构建和部署源选择 "GitHub Actions"
   - 保存设置

3. **推送代码**：
   ```bash
   git add .
   git commit -m "切换到静态版本用于 GitHub Pages"
   git push
   ```

4. **等待部署**：
   GitHub Actions 会自动构建并部署到 GitHub Pages
   您可以在仓库的 Actions 页面查看部署进度

5. **访问网站**：
   部署成功后，您可以在 `https://<your-username>.github.io/<repo-name>/` 访问您的网站

---

### 方案二：前后端分离部署（完整功能）

前端部署到 GitHub Pages，后端部署到其他平台。

#### 1. 后端部署
选择以下任一平台部署后端：
- [Railway](https://railway.app/)
- [Render](https://render.com/)
- [Fly.io](https://fly.io/)
- 阿里云/腾讯云等云服务器

#### 2. 配置前端

1. **设置 GitHub Secrets**：
   - 在仓库设置中，找到 Secrets and variables -> Actions
   - 添加一个名为 `API_BASE` 的 Secret，值为您的后端地址（例如：`https://your-backend.railway.app`）

2. **确保使用动态版本**：
   ```bash
   ./scripts/switch-to-dynamic.sh
   ```

3. **配置 GitHub Pages**（同方案一）

4. **推送代码**：
   ```bash
   git add .
   git commit -m "配置前后端分离部署"
   git push
   ```

---

## 切换版本说明

我们提供了两个方便的脚本来在版本间切换：

- **切换到静态版本**：`./scripts/switch-to-static.sh`
- **切换回动态版本**：`./scripts/switch-to-dynamic.sh`

## 自定义静态数据

如果您想修改静态版本中的新闻数据，请编辑 `src/main/vue/src/App.static.vue` 文件中的 `staticData` 对象。

## 本地开发

### 运行后端：
```bash
mvn spring-boot:run
```

### 运行前端：
```bash
cd src/main/vue
npm install
npm run dev
```

## 注意事项

1. **GitHub Pages 路径问题**：
   我们的 GitHub Actions 工作流会自动配置正确的 base 路径
   如果手动构建，请确保 vite.config.js 中的 base 设置正确

2. **CORS 问题**：
   如果使用前后端分离部署，请确保后端配置了正确的 CORS
   我们已经在 `CorsConfig.java` 中配置了允许所有来源的 CORS

3. **HTTPS**：
   GitHub Pages 只支持 HTTPS，请确保您的后端也使用 HTTPS
