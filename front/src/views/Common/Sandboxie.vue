<script setup>
import { ref, computed } from 'vue';

const totalBudget = ref(0);
const budgetAllocation = ref({
  hardDecoration: 60,
  softDecoration: 30,
  others: 10
});

const suggestions = ref([
  {
    category: '硬装',
    original: '大理石瓷砖',
    alternative: '仿大理石瓷砖',
    priceDiff: -30,
    quality: '相近',
    reason: '视觉效果相似，价格更实惠'
  },
  {
    category: '软装',
    original: '实木地板',
    alternative: '强化复合地板',
    priceDiff: -40,
    quality: '良好',
    reason: '耐磨性好，性价比高'
  }
]);

const allocatedAmount = computed(() => ({
  hardDecoration: (totalBudget.value * budgetAllocation.value.hardDecoration / 100).toFixed(2),
  softDecoration: (totalBudget.value * budgetAllocation.value.softDecoration / 100).toFixed(2),
  others: (totalBudget.value * budgetAllocation.value.others / 100).toFixed(2)
}));

const updateAllocation = (category, value) => {
  const oldValue = budgetAllocation.value[category];
  const diff = value - oldValue;
  
  // 重新分配剩余比例
  const remainingCategories = Object.keys(budgetAllocation.value).filter(key => key !== category);
  const totalRemaining = remainingCategories.reduce((sum, key) => sum + budgetAllocation.value[key], 0);
  
  remainingCategories.forEach(key => {
    const ratio = budgetAllocation.value[key] / totalRemaining;
    budgetAllocation.value[key] = Math.max(0, budgetAllocation.value[key] - (diff * ratio));
  });
  
  budgetAllocation.value[category] = value;
};
</script>

<template>
  <div class="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 p-8">
    <div class="max-w-6xl mx-auto">
      <!-- 标题区域 -->
      <div class="text-center mb-12">
        <h1 class="text-4xl font-bold text-gray-800 mb-4">智能预算沙盘</h1>
        <p class="text-gray-600">AI智能分配，打造理想家居</p>
      </div>

      <!-- 预算输入区域 -->
      <div class="bg-white rounded-xl shadow-lg p-6 mb-8">
        <div class="flex items-center justify-center space-x-4">
          <span class="text-xl font-medium text-gray-700">总预算：</span>
          <div class="relative">
            <input
              v-model="totalBudget"
              type="number"
              class="w-48 px-4 py-2 text-xl border-2 border-blue-500 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-300"
              placeholder="请输入预算金额"
            />
            <span class="absolute right-3 top-2 text-gray-500">元</span>
          </div>
        </div>
      </div>

      <!-- 预算分配区域 -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
        <!-- 分配比例 -->
        <div class="bg-white rounded-xl shadow-lg p-6">
          <h2 class="text-2xl font-semibold text-gray-800 mb-6">预算分配</h2>
          <div class="space-y-6">
            <div v-for="(value, key) in budgetAllocation" :key="key" class="space-y-2">
              <div class="flex justify-between items-center">
                <span class="text-gray-700">
                  {{ key === 'hardDecoration' ? '硬装' : key === 'softDecoration' ? '软装' : '其他' }}
                </span>
                <span class="text-blue-600 font-medium">¥{{ allocatedAmount[key] }}</span>
              </div>
              <div class="flex items-center space-x-4">
                <input
                  type="range"
                  :value="value"
                  @input="updateAllocation(key, $event.target.value)"
                  class="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer"
                  min="0"
                  max="100"
                />
                <span class="text-gray-600 w-12 text-right">{{ value }}%</span>
              </div>
            </div>
          </div>
        </div>

        <!-- AI建议区域 -->
        <div class="bg-white rounded-xl shadow-lg p-6">
          <h2 class="text-2xl font-semibold text-gray-800 mb-6">AI优化建议</h2>
          <div class="space-y-4">
            <div v-for="(suggestion, index) in suggestions" :key="index" 
                 class="p-4 bg-blue-50 rounded-lg border border-blue-100">
              <div class="flex justify-between items-start mb-2">
                <span class="font-medium text-blue-800">{{ suggestion.category }}</span>
                <span class="text-green-600">节省 {{ suggestion.priceDiff }}%</span>
              </div>
              <div class="grid grid-cols-2 gap-4 text-sm">
                <div>
                  <p class="text-gray-600">原方案：{{ suggestion.original }}</p>
                  <p class="text-gray-600">替代方案：{{ suggestion.alternative }}</p>
                </div>
                <div>
                  <p class="text-gray-600">质量对比：{{ suggestion.quality }}</p>
                  <p class="text-gray-600">推荐理由：{{ suggestion.reason }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
input[type="range"] {
  -webkit-appearance: none;
  background: linear-gradient(to right, #3b82f6, #60a5fa);
}

input[type="range"]::-webkit-slider-thumb {
  -webkit-appearance: none;
  width: 20px;
  height: 20px;
  background: white;
  border: 2px solid #3b82f6;
  border-radius: 50%;
  cursor: pointer;
  transition: all 0.2s ease;
}

input[type="range"]::-webkit-slider-thumb:hover {
  transform: scale(1.1);
  box-shadow: 0 0 10px rgba(59, 130, 246, 0.5);
}
</style>