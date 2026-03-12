<script setup>
import { onMounted, ref } from 'vue'
import { get, post } from '@/net/index.js'

const formData = ref({
  roomType: '', // 房间类型
  style: '', // 装修风格
  area: '', // 面积
  budget: '', // 预算
  specialRequirements: '', // 特殊要求
  colorScheme: '', // 配色方案
  furniture: '', // 家具需求
  lighting: '', // 照明需求
});

const messages = ref([]);
const isLoading = ref(false);
const budgetError = ref(''); // 添加预算错误提示状态

// 添加面积输入验证
const handleAreaInput = (event) => {
  const value = event.target.value;
  if (value < 0) {
    formData.value.area = '';
    alert('面积不能为负数');
  }
};

// 修改预算验证逻辑
const handleBudgetBlur = () => {
  const value = Number(formData.value.budget);
  if (value !== 0 && value < 1000) {
    budgetError.value = '预算不能低于1000元';
  } else {
    budgetError.value = '';
  }
};

const generateImage = async () => {
  if (!formData.value.roomType || !formData.value.style || !formData.value.area) {
    alert('请填写必要信息（房间类型、装修风格、面积）');
    return;
  }

  if (formData.value.budget && Number(formData.value.budget) < 1000) {
    alert('预算不能低于1000元');
    return;
  }

  isLoading.value = true;

  // 构建提示词
  const prompt = `请生成一个${formData.value.area}平方米的${formData.value.roomType}的${formData.value.style}风格装修效果图。
    预算：${formData.value.budget}
    特殊要求：${formData.value.specialRequirements}
    配色方案：${formData.value.colorScheme}
    家具需求：${formData.value.furniture}
    照明需求：${formData.value.lighting}`;

  // 调用 AI 接口
  await get('/ai/image', {
    message: prompt
  }, (message, data) => {
    const imageUrl = data;
    messages.value.push({
      text: imageUrl,
      isUser: false,
      isImage: true,
      formData: { ...formData.value } // 保存生成图片时使用的表单数据
    });
    isLoading.value = false;
  });
};

const resetForm = () => {
  formData.value = {
    roomType: '',
    style: '',
    area: '',
    budget: '',
    specialRequirements: '',
    colorScheme: '',
    furniture: '',
    lighting: '',
  };
};

// 添加图片缩放状态
const imageScale = ref(1);
const isFullscreen = ref(false);

// 图片缩放功能
const zoomIn = () => {
  imageScale.value = Math.min(imageScale.value + 0.2, 2);
};

const zoomOut = () => {
  imageScale.value = Math.max(imageScale.value - 0.2, 0.5);
};

// 重置缩放
const resetZoom = () => {
  imageScale.value = 1;
  isFullscreen.value = false;
};

// 切换全屏显示
const toggleFullscreen = () => {
  isFullscreen.value = !isFullscreen.value;
  resetZoom();
};

// 下载图片
const downloadImage = async (imageUrl) => {
  try {
    const response = await fetch(imageUrl);
    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = `设计图_${new Date().getTime()}.png`;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);
  } catch (error) {
    console.error('下载图片失败:', error);
    alert('下载图片失败，请稍后重试');
  }
};

onMounted(() => {
  messages.value.push({
    text: "欢迎使用AI装修设计助手，请填写以下信息，我们将为您生成理想的设计图。",
    isUser: false
  });
});
</script>

<template>
  <div class="bg-[#f5f5f7] min-h-screen flex flex-col p-6">
    <!-- 表单部分 -->
    <div class="bg-white rounded-lg shadow-lg p-6 mb-6 border border-gray-200">
      <h2 class="text-2xl font-bold mb-6 text-gray-800">装修需求信息</h2>
      <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <!-- 基本信息 -->
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">房间类型</label>
            <select v-model="formData.roomType" class="w-full p-2 border border-gray-300 bg-[#f5f5f7] text-gray-700 rounded-lg focus:ring-2 focus:ring-cyan-500">
              <option value="">请选择</option>
              <option value="客厅">客厅</option>
              <option value="卧室">卧室</option>
              <option value="厨房">厨房</option>
              <option value="卫生间">卫生间</option>
              <option value="书房">书房</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">装修风格</label>
            <select v-model="formData.style" class="w-full p-2 border border-gray-300 bg-[#f5f5f7] text-gray-700 rounded-lg focus:ring-2 focus:ring-cyan-500">
              <option value="">请选择</option>
              <option value="现代简约">现代简约</option>
              <option value="北欧">北欧</option>
              <option value="中式">中式</option>
              <option value="美式">美式</option>
              <option value="日式">日式</option>
              <option value="工业风">工业风</option>
              <option value="轻奢">轻奢</option>
              <option value="田园">田园</option>
              <option value="复古">复古</option>
            </select>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">面积（平方米）</label>
            <input 
              type="number" 
              v-model="formData.area" 
              @input="handleAreaInput"
              min="0"
              class="w-full p-2 border border-gray-300 bg-[#f5f5f7] text-gray-700 rounded-lg focus:ring-2 focus:ring-cyan-500"
              placeholder="请输入面积">
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">预算（元）</label>
            <input 
              type="number" 
              v-model="formData.budget" 
              @blur="handleBudgetBlur"
              class="w-full p-2 border border-gray-300 bg-[#f5f5f7] text-gray-700 rounded-lg focus:ring-2 focus:ring-cyan-500"
              placeholder="请输入预算（最低1000元）">
            <p v-if="budgetError" class="text-red-500 text-sm mt-1">{{ budgetError }}</p>
          </div>
        </div>

        <!-- 详细需求 -->
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">特殊要求</label>
            <textarea v-model="formData.specialRequirements" class="w-full p-2 border border-gray-300 bg-[#f5f5f7] text-gray-700 rounded-lg focus:ring-2 focus:ring-cyan-500" rows="3" placeholder="请输入特殊要求"></textarea>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">配色方案</label>
            <input type="text" v-model="formData.colorScheme" class="w-full p-2 border border-gray-300 bg-[#f5f5f7] text-gray-700 rounded-lg focus:ring-2 focus:ring-cyan-500" placeholder="例如：主色调为白色，搭配蓝色点缀">
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">家具需求</label>
            <textarea v-model="formData.furniture" class="w-full p-2 border border-gray-300 bg-[#f5f5f7] text-gray-700 rounded-lg focus:ring-2 focus:ring-cyan-500" rows="2" placeholder="请输入家具需求"></textarea>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">照明需求</label>
            <textarea v-model="formData.lighting" class="w-full p-2 border border-gray-300 bg-[#f5f5f7] text-gray-700 rounded-lg focus:ring-2 focus:ring-cyan-500" rows="2" placeholder="请输入照明需求"></textarea>
          </div>
        </div>
      </div>

      <!-- 按钮组 -->
      <div class="flex justify-end space-x-4 mt-6">
        <button @click="resetForm" class="px-6 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 transition-colors">
          重置
        </button>
        <button @click="generateImage" :disabled="isLoading"
                class="px-6 py-2 bg-cyan-600 text-white rounded-lg hover:bg-cyan-700 transition-colors disabled:opacity-50">
          {{ isLoading ? '生成中...' : '生成设计图' }}
        </button>
      </div>
    </div>

    <!-- 生成结果展示 -->
    <div class="flex-1 overflow-auto bg-white rounded-lg shadow-lg p-4 border border-gray-200">
      <h2 class="text-2xl font-bold mb-4 text-cyan-700">生成结果</h2>
      <div class="space-y-6">
        <div v-for="(message, index) in messages" :key="index" class="animate-fade-in">
          <!-- 欢迎消息 -->
          <div v-if="!message.isImage" class="text-gray-700">
            {{ message.text }}
          </div>

          <!-- 生成的设计图 -->
          <div v-else class="space-y-4">
            <div class="bg-gray-50 p-4 rounded-lg border border-gray-200">
              <h3 class="font-semibold text-cyan-700 mb-2">生成参数：</h3>
              <div class="grid grid-cols-2 gap-2 text-sm text-gray-600">
                <div>房间类型：{{ message.formData.roomType }}</div>
                <div>装修风格：{{ message.formData.style }}</div>
                <div>面积：{{ message.formData.area }}㎡</div>
                <div>预算：{{ message.formData.budget }}元</div>
              </div>
            </div>
            <div class="relative" :class="{ 'fixed inset-0 z-50 bg-black bg-opacity-60': isFullscreen }">
              <div class="flex justify-center items-center relative">
                <img 
                  :src="message.text" 
                  alt="AI生成的设计图" 
                  class="rounded-lg shadow-md transition-transform duration-200 bg-white"
                  :style="{ transform: `scale(${imageScale})` }"
                  @click="toggleFullscreen"
                >
                <div class="absolute bottom-4 right-4 flex space-x-2">
                  <button 
                    @click.stop="zoomOut"
                    class="p-2 bg-gray-200 text-gray-700 rounded-full opacity-80 hover:opacity-100 border border-gray-300">
                    <span class="text-xl">-</span>
                  </button>
                  <button 
                    @click.stop="zoomIn"
                    class="p-2 bg-gray-200 text-gray-700 rounded-full opacity-80 hover:opacity-100 border border-gray-300">
                    <span class="text-xl">+</span>
                  </button>
                  <button 
                    @click.stop="resetZoom"
                    class="p-2 bg-gray-200 text-gray-700 rounded-full opacity-80 hover:opacity-100 border border-gray-300">
                    <span class="text-sm">重置</span>
                  </button>
                  <button 
                    @click.stop="downloadImage(message.text)"
                    class="p-2 bg-cyan-600 text-white rounded-full opacity-90 hover:opacity-100 border border-cyan-700">
                    <span class="text-sm">下载</span>
                  </button>
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
.animate-fade-in {
  animation: fadeIn 0.5s ease-in-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

::placeholder {
  color: rgba(156, 163, 175, 0.7);
}

select option {
  background-color: #fff;
  color: #333;
}

.fixed {
  position: fixed;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem;
}

.fixed img {
  max-height: 90vh;
  max-width: 90vw;
  object-fit: contain;
  background: #fff;
}
</style>