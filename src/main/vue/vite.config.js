import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig(({ mode }) => {
  // 根据模式设置 base 路径
  let base = '/'
  if (mode === 'github-pages') {
    // GitHub Pages 的 base 路径需要设置为仓库名，例如 /repo-name/
    // 这里使用环境变量，方便在构建时传入
    base = process.env.GITHUB_PAGES_BASE || '/'
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
