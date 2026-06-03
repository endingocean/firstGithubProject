import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default defineConfig(() => {
  // 根据环境变量设置 base 路径
  const base = process.env.GITHUB_PAGES_BASE || '/'

  console.log('Building with base path:', base)

  return {
    plugins: [vue()],
    root: '.',
    base: base,
    build: {
      outDir: resolve('/workspace/src/main/resources/static'),
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
