#!/bin/bash

# 切换回动态版本
echo "切换回动态版本..."

cd "$(dirname "$0")/.." || exit 1

# 检查是否有备份
if [ -f "src/main/vue/src/App.vue.backup" ]; then
  cp src/main/vue/src/App.vue.backup src/main/vue/src/App.vue
  echo "已从备份恢复动态版本！"
else
  echo "警告：未找到备份文件 App.vue.backup"
  echo "请确保您有原始的 App.vue 文件"
fi
