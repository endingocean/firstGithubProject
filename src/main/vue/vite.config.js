import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig(() => {
  // 根据环境变量设置 base 路径
  const base = process.env.GITHUB_PAGES_BASE || '/'

  console.log('Building with base path:', base)

  return {
    plugins: [vue()],
    root: '.',
    base: base,
    build: {
      // 使用相对于 root (.) 的相对路径
      outDir: '../../src/main/resources/static',
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
