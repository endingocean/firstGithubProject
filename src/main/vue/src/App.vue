<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'

const currentDate = ref(new Date().toISOString().split('T')[0])
const newsList = ref<any[]>([])
const loading = ref(false)
const error = ref('')

const API_BASE = '/api/news'

const formattedDate = computed(() => {
  const date = new Date(currentDate.value)
  const today = new Date()
  today.setHours(0, 0, 0, 0)
  const targetDate = new Date(currentDate.value)
  targetDate.setHours(0, 0, 0, 0)

  if (targetDate.getTime() === today.getTime()) {
    return '今日热点'
  }
  return `${date.getMonth() + 1}月${date.getDate()}日`
})

const fetchNews = async () => {
  loading.value = true
  error.value = ''
  try {
    const response = await fetch(`${API_BASE}/all/${currentDate.value}`)
    const result = await response.json()
    if (result.code === 200) {
      newsList.value = result.data
    } else {
      error.value = result.message
      newsList.value = []
    }
  } catch (e) {
    error.value = '网络错误，请检查后端服务是否启动'
    newsList.value = []
  } finally {
    loading.value = false
  }
}

const goToToday = () => {
  currentDate.value = new Date().toISOString().split('T')[0]
}

const goBack = () => {
  const date = new Date(currentDate.value)
  date.setDate(date.getDate() - 1)
  currentDate.value = date.toISOString().split('T')[0]
}

const goForward = () => {
  const date = new Date(currentDate.value)
  date.setDate(date.getDate() + 1)
  const today = new Date()
  today.setHours(0, 0, 0, 0)
  if (date <= today) {
    currentDate.value = date.toISOString().split('T')[0]
  }
}

const isToday = computed(() => {
  return currentDate.value === new Date().toISOString().split('T')[0]
})

watch(currentDate, () => {
  fetchNews()
})

onMounted(() => {
  fetchNews()
})
</script>

<template>
  <div class="container">
    <header class="header">
      <h1 class="title">
        <span class="title-icon">⚡</span>
        AI新闻热点
      </h1>
      <p class="subtitle">每日人工智能资讯精选</p>
    </header>

    <div class="date-nav">
      <button class="nav-btn" @click="goBack">←</button>
      <div class="date-display">
        <span class="date-text">{{ formattedDate }}</span>
        <input
          type="date"
          v-model="currentDate"
          class="date-picker"
          :max="new Date().toISOString().split('T')[0]"
        />
      </div>
      <button class="nav-btn" @click="goForward" :disabled="isToday">→</button>
      <button v-if="!isToday" class="today-btn" @click="goToToday">回到今日</button>
    </div>

    <div v-if="loading" class="loading">
      <div class="loading-spinner"></div>
      <span>加载中...</span>
    </div>

    <div v-else-if="error" class="error">
      {{ error }}
    </div>

    <div v-else-if="newsList.length === 0" class="empty">
      <div class="empty-icon">📰</div>
      <p>当日暂无新闻数据</p>
      <button class="today-btn" @click="goToToday">查看今日热点</button>
    </div>

    <div v-else class="news-list">
      <a
        v-for="(news, index) in newsList"
        :key="news.id"
        :href="news.url"
        target="_blank"
        class="news-card"
        :style="{ animationDelay: `${index * 0.1}s` }"
      >
        <div class="rank" :class="`rank-${index + 1}`">{{ index + 1 }}</div>
        <div class="news-content">
          <div class="news-header">
            <span class="source-tag">{{ news.source }}</span>
            <span class="heat-score">
              <span class="heat-icon">🔥</span>
              {{ news.heatScore }}
            </span>
          </div>
          <h2 class="news-title">{{ news.title }}</h2>
          <p class="news-summary">{{ news.summary }}</p>
          <div class="news-footer">
            <span class="publish-time">{{ news.publishTime }}</span>
            <span class="read-more">阅读原文 →</span>
          </div>
        </div>
      </a>
    </div>

    <footer class="footer">
      <p>数据来源：各大科技媒体 | 热度指数基于多维度算法计算</p>
    </footer>
  </div>
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

:root {
  --bg-primary: #0a0a0f;
  --bg-secondary: #12121a;
  --bg-card: #1a1a24;
  --accent: #00d4ff;
  --accent-glow: rgba(0, 212, 255, 0.3);
  --text-primary: #e8e8e8;
  --text-secondary: #888;
  --text-muted: #555;
  --border: #2a2a3a;
}

body {
  font-family: 'SF Mono', 'Fira Code', 'Consolas', monospace;
  background: var(--bg-primary);
  color: var(--text-primary);
  min-height: 100vh;
  line-height: 1.6;
}

.container {
  max-width: 900px;
  margin: 0 auto;
  padding: 40px 20px;
}

.header {
  text-align: center;
  margin-bottom: 50px;
}

.title {
  font-size: 2.5rem;
  font-weight: 700;
  background: linear-gradient(135deg, var(--accent), #00ff88);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin-bottom: 8px;
  letter-spacing: -0.02em;
}

.title-icon {
  -webkit-text-fill-color: initial;
  margin-right: 8px;
}

.subtitle {
  color: var(--text-secondary);
  font-size: 1rem;
}

.date-nav {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 16px;
  margin-bottom: 40px;
}

.nav-btn {
  background: var(--bg-card);
  border: 1px solid var(--border);
  color: var(--text-primary);
  width: 44px;
  height: 44px;
  border-radius: 12px;
  cursor: pointer;
  font-size: 1.2rem;
  transition: all 0.2s;
}

.nav-btn:hover:not(:disabled) {
  border-color: var(--accent);
  box-shadow: 0 0 20px var(--accent-glow);
}

.nav-btn:disabled {
  opacity: 0.3;
  cursor: not-allowed;
}

.date-display {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.date-text {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--accent);
}

.date-picker {
  position: absolute;
  opacity: 0;
  width: 100%;
  height: 100%;
  cursor: pointer;
}

.today-btn {
  background: linear-gradient(135deg, var(--accent), #00ff88);
  border: none;
  color: var(--bg-primary);
  padding: 10px 20px;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.today-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 20px var(--accent-glow);
}

.loading {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
  padding: 60px 0;
  color: var(--text-secondary);
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 3px solid var(--border);
  border-top-color: var(--accent);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

.error {
  text-align: center;
  padding: 40px;
  background: rgba(255, 100, 100, 0.1);
  border: 1px solid rgba(255, 100, 100, 0.3);
  border-radius: 16px;
  color: #ff6b6b;
}

.empty {
  text-align: center;
  padding: 60px 20px;
}

.empty-icon {
  font-size: 4rem;
  margin-bottom: 16px;
}

.empty p {
  color: var(--text-secondary);
  margin-bottom: 20px;
}

.news-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.news-card {
  display: flex;
  gap: 20px;
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: 16px;
  padding: 24px;
  text-decoration: none;
  color: inherit;
  transition: all 0.3s;
  animation: fadeIn 0.5s ease-out forwards;
  opacity: 0;
}

@keyframes fadeIn {
  to {
    opacity: 1;
  }
}

.news-card:hover {
  border-color: var(--accent);
  transform: translateY(-4px);
  box-shadow: 0 8px 40px rgba(0, 212, 255, 0.15);
}

.rank {
  flex-shrink: 0;
  width: 48px;
  height: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  font-weight: 700;
  border-radius: 12px;
  background: var(--bg-secondary);
}

.rank-1 {
  background: linear-gradient(135deg, #ffd700, #ff8c00);
  color: #000;
}

.rank-2 {
  background: linear-gradient(135deg, #c0c0c0, #808080);
  color: #000;
}

.rank-3 {
  background: linear-gradient(135deg, #cd7f32, #8b4513);
}

.rank-4, .rank-5 {
  background: var(--bg-secondary);
  color: var(--text-secondary);
}

.news-content {
  flex: 1;
  min-width: 0;
}

.news-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.source-tag {
  background: var(--bg-secondary);
  padding: 4px 12px;
  border-radius: 6px;
  font-size: 0.85rem;
  color: var(--accent);
}

.heat-score {
  display: flex;
  align-items: center;
  gap: 4px;
  font-weight: 600;
  color: #ff6b35;
}

.heat-icon {
  font-size: 1.1rem;
}

.news-title {
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 8px;
  line-height: 1.4;
  color: var(--text-primary);
}

.news-summary {
  color: var(--text-secondary);
  font-size: 0.95rem;
  margin-bottom: 16px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.news-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.publish-time {
  color: var(--text-muted);
  font-size: 0.85rem;
}

.read-more {
  color: var(--accent);
  font-size: 0.9rem;
  opacity: 0;
  transition: opacity 0.2s;
}

.news-card:hover .read-more {
  opacity: 1;
}

.footer {
  margin-top: 60px;
  text-align: center;
  color: var(--text-muted);
  font-size: 0.85rem;
}

@media (max-width: 600px) {
  .container {
    padding: 20px 16px;
  }

  .title {
    font-size: 1.8rem;
  }

  .news-card {
    flex-direction: column;
    gap: 16px;
  }

  .rank {
    width: 40px;
    height: 40px;
    font-size: 1.2rem;
  }

  .news-title {
    font-size: 1.1rem;
  }

  .date-nav {
    gap: 8px;
  }

  .nav-btn {
    width: 40px;
    height: 40px;
  }
}
</style>
