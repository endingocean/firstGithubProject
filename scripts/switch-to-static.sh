#!/bin/bash

# 切换到纯静态版本
echo "切换到纯静态版本..."

cd "$(dirname "$0")/.." || exit 1

# 备份原始 App.vue
if [ ! -f "src/main/vue/src/App.vue.backup" ]; then
  cp src/main/vue/src/App.vue src/main/vue/src/App.vue.backup
  echo "已备份原始 App.vue 到 App.vue.backup"
fi

# 复制静态版本
cp src/main/vue/src/App.static.vue src/main/vue/src/App.vue

echo "已切换到纯静态版本！"
echo "您现在可以构建并部署到 GitHub Pages 了。"
