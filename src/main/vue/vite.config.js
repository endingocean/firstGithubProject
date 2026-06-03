import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig(({ mode }) => {
  // 根据模式设置 base 路径
  let base = '/'
  
  // 如果是 GitHub Pages 部署或生产环境，使用仓库名作为 base 路径
  if (mode === 'production' || process.env.GITHUB_PAGES_BASE) {
    base = process.env.GITHUB_PAGES_BASE || '/firstGithubProject/'
  }

  return {
    plugins: [vue()],
    root: '.',
    base: base,
    build: {
      outDir: '../../static',
      emptyOutDir: true
    },
    server: {
      port: 5173,
      proxy: {
        '/api': {
          target: 'http://localhost:8080',
          changeOrigin: true
        }
      }
    },
    define: {
      // 注入 API 地址
      __API_BASE__: JSON.stringify(process.env.API_BASE || '/api')
    }
  }
})
