<script setup>
import { reactive, computed } from 'vue';
import { ShoppingOutlined, HomeOutlined, DollarOutlined } from '@ant-design/icons-vue';

const state = reactive({
  selectedItems: {
    livingRoom: {
      sofa: { selected: false, price: 3000 },
      tvCabinet: { selected: false, price: 1500 },
      coffeeTable: { selected: false, price: 800 },
      floorLamp: { selected: false, price: 400 }
    },
    bedroom: {
      bed: { selected: false, price: 5000 },
      wardrobe: { selected: false, price: 3000 },
      bedsideTable: { selected: false, price: 600 },
      dressingTable: { selected: false, price: 2000 }
    },
    kitchen: {
      cabinet: { selected: false, price: 8000 },
      refrigerator: { selected: false, price: 4000 },
      stove: { selected: false, price: 2000 },
      dishwasher: { selected: false, price: 3000 }
    },
    bathroom: {
      toilet: { selected: false, price: 1500 },
      shower: { selected: false, price: 2000 },
      sink: { selected: false, price: 800 },
      mirror: { selected: false, price: 500 }
    }
  }
});

const totalPrice = computed(() => {
  let total = 0;
  Object.values(state.selectedItems).forEach(room => {
    Object.values(room).forEach(item => {
      if (item.selected) {
        total += item.price;
      }
    });
  });
  return total;
});

const toggleItem = (room, item) => {
  state.selectedItems[room][item].selected = !state.selectedItems[room][item].selected;
};
</script>

<template>
  <div class="min-h-screen bg-black text-white p-8">
    <div class="max-w-4xl mx-auto">
      <!-- 标题 -->
      <h1 class="text-4xl font-bold text-center mb-12 text-black">装修预算评估</h1>

      <!-- 房间选择区域 -->
      <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
        <!-- 客厅 -->
        <div class="room-section">
          <div class="room-header">
            <home-outlined class="text-2xl mr-2" />
            <h2 class="text-2xl font-semibold">客厅</h2>
          </div>
          <div class="items-grid">
            <div v-for="(item, key) in state.selectedItems.livingRoom" 
                 :key="key"
                 :class="['item-card', { 'selected': item.selected }]">
              <div class="flex flex-col items-center">
                <shopping-outlined class="text-xl mb-2" />
                <h3 class="text-lg font-medium mb-2">{{ key === 'sofa' ? '沙发' : 
                   key === 'tvCabinet' ? '电视柜' : 
                   key === 'coffeeTable' ? '茶几' : '落地灯' }}</h3>
                <div class="flex items-center space-x-2">
                  <input 
                    type="number" 
                    v-model="item.price"
                    class="w-24 px-2 py-1 rounded bg-gray-800 text-white border border-gray-600 focus:border-blue-500 focus:outline-none"
                    min="0"
                    @click.stop
                  >
                  <span class="text-gray-400">元</span>
                </div>
              </div>
              <div class="mt-2">
                <button 
                  @click="toggleItem('livingRoom', key)"
                  :class="['select-btn', { 'selected': item.selected }]">
                  {{ item.selected ? '已选择' : '选择' }}
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- 卧室 -->
        <div class="room-section">
          <div class="room-header">
            <home-outlined class="text-2xl mr-2" />
            <h2 class="text-2xl font-semibold">卧室</h2>
          </div>
          <div class="items-grid">
            <div v-for="(item, key) in state.selectedItems.bedroom" 
                 :key="key"
                 :class="['item-card', { 'selected': item.selected }]">
              <div class="flex flex-col items-center">
                <shopping-outlined class="text-xl mb-2" />
                <h3 class="text-lg font-medium mb-2">{{ key === 'bed' ? '床' : 
                   key === 'wardrobe' ? '衣柜' : 
                   key === 'bedsideTable' ? '床头柜' : '梳妆台' }}</h3>
                <div class="flex items-center space-x-2">
                  <input 
                    type="number" 
                    v-model="item.price"
                    class="w-24 px-2 py-1 rounded bg-gray-800 text-white border border-gray-600 focus:border-blue-500 focus:outline-none"
                    min="0"
                    @click.stop
                  >
                  <span class="text-gray-400">元</span>
                </div>
              </div>
              <div class="mt-2">
                <button 
                  @click="toggleItem('bedroom', key)"
                  :class="['select-btn', { 'selected': item.selected }]">
                  {{ item.selected ? '已选择' : '选择' }}
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- 厨房 -->
        <div class="room-section">
          <div class="room-header">
            <home-outlined class="text-2xl mr-2" />
            <h2 class="text-2xl font-semibold">厨房</h2>
          </div>
          <div class="items-grid">
            <div v-for="(item, key) in state.selectedItems.kitchen" 
                 :key="key"
                 :class="['item-card', { 'selected': item.selected }]">
              <div class="flex flex-col items-center">
                <shopping-outlined class="text-xl mb-2" />
                <h3 class="text-lg font-medium mb-2">{{ key === 'cabinet' ? '橱柜' : 
                   key === 'refrigerator' ? '冰箱' : 
                   key === 'stove' ? '炉灶' : '洗碗机' }}</h3>
                <div class="flex items-center space-x-2">
                  <input 
                    type="number" 
                    v-model="item.price"
                    class="w-24 px-2 py-1 rounded bg-gray-800 text-white border border-gray-600 focus:border-blue-500 focus:outline-none"
                    min="0"
                    @click.stop
                  >
                  <span class="text-gray-400">元</span>
                </div>
              </div>
              <div class="mt-2">
                <button 
                  @click="toggleItem('kitchen', key)"
                  :class="['select-btn', { 'selected': item.selected }]">
                  {{ item.selected ? '已选择' : '选择' }}
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- 卫生间 -->
        <div class="room-section">
          <div class="room-header">
            <home-outlined class="text-2xl mr-2" />
            <h2 class="text-2xl font-semibold">卫生间</h2>
          </div>
          <div class="items-grid">
            <div v-for="(item, key) in state.selectedItems.bathroom" 
                 :key="key"
                 :class="['item-card', { 'selected': item.selected }]">
              <div class="flex flex-col items-center">
                <shopping-outlined class="text-xl mb-2" />
                <h3 class="text-lg font-medium mb-2">{{ key === 'toilet' ? '马桶' : 
                   key === 'shower' ? '淋浴' : 
                   key === 'sink' ? '洗手盆' : '镜子' }}</h3>
                <div class="flex items-center space-x-2">
                  <input 
                    type="number" 
                    v-model="item.price"
                    class="w-24 px-2 py-1 rounded bg-gray-800 text-white border border-gray-600 focus:border-blue-500 focus:outline-none"
                    min="0"
                    @click.stop
                  >
                  <span class="text-gray-400">元</span>
                </div>
              </div>
              <div class="mt-2">
                <button 
                  @click="toggleItem('bathroom', key)"
                  :class="['select-btn', { 'selected': item.selected }]">
                  {{ item.selected ? '已选择' : '选择' }}
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 总价显示 -->
      <div class="total-price mt-12">
        <div class="price-card">
          <dollar-outlined class="text-3xl mb-4" />
          <h2 class="text-2xl font-bold">预估总价</h2>
          <p class="text-4xl font-bold text-blue-400">¥{{ totalPrice }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.min-h-screen.bg-black.text-white.p-8 {
  background: linear-gradient(135deg, #f5f7fa, #e0e5ec) !important;
  color: #333 !important;
}

.room-section {
  background: #fff !important;
  border-radius: 1rem;
  padding: 1.5rem;
  border: 1px solid #e2e8f0 !important;
}

.room-header {
  display: flex;
  align-items: center;
  margin-bottom: 1.5rem;
  color: #333 !important;
}

.items-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 1rem;
}

.item-card {
  background: #f8fafc !important;
  border: 1px solid #e2e8f0 !important;
  border-radius: 0.75rem;
  padding: 1rem;
  text-align: center;
  transition: all 0.3s ease;
  color: #333 !important;
}

.item-card:hover {
  background: #e0f2fe !important;
  transform: translateY(-2px);
}

.item-card.selected {
  background: #bae6fd !important;
  border-color: #0891b2 !important;
}

.select-btn {
  padding: 0.5rem 1rem;
  border-radius: 0.5rem;
  background: #e0e8ef !important;
  color: #333 !important;
  border: 1px solid #cbd5e1 !important;
  cursor: pointer;
  transition: all 0.3s ease;
}

.select-btn:hover {
  background: #bae6fd !important;
}

.select-btn.selected {
  background: #0891b2 !important;
  border-color: #0891b2 !important;
  color: #fff !important;
}

.total-price {
  display: flex;
  justify-content: center;
}

.price-card {
  background: #fff !important;
  border: 1px solid #e2e8f0 !important;
  border-radius: 1rem;
  padding: 2rem;
  text-align: center;
  min-width: 300px;
  color: #333 !important;
}

.text-blue-400 {
  color: #0891b2 !important;
}

/* 输入框样式 */
input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input[type="number"],
input[type="number"].w-24.px-2.py-1.rounded.bg-gray-800.text-white.border.border-gray-600.focus\:border-blue-500.focus\:outline-none {
  background: #fff !important;
  color: #333 !important;
  border: 1px solid #e2e8f0 !important;
}
</style>