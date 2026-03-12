<script setup>
import { onMounted, ref } from 'vue'
import { get, post } from '@/net/index.js'
import { useUserStore } from '@/stores/userStore.js'

const userInput = ref('');
const messages = ref([]);
const isLoading = ref(false); // 新增：加载状态
const userStore = useUserStore(); // 引入用户存储

// 模拟 AI 响应
const askAI = async () => {
  if (!userInput.value.trim()) return;

  // 用户消息
  messages.value.push({ text: userInput.value, isUser: true });
  isLoading.value=true;
  // 模拟 AI 的回答
  const aiAnswer = ref("你好，我是AI大模型！");
  await get('/ai/chat', {
    message: userInput.value
  }, (message,data) => {
    isLoading.value=false;
    aiAnswer.value = data;
    console.log(userInput.value + ":" + aiAnswer.value);
    // 清空输入框
    userInput.value = '';
    messages.value.push({ text: aiAnswer.value, isUser: false });
  });
};
onMounted(()=>{
  messages.value.push({ text: "欢迎来到AI测绘界面，你可以向我咨询任何装修问题，我会尽力为您回答", isUser: false });
})
</script>


<template>
  <div class="bg-gradient-to-br min-h-screen flex flex-col justify-between from-indigo-50 to-blue-200 p-6">
    <!-- 聊天窗口 -->
    <div class="flex-1 overflow-auto p-4 space-y-4 bg-black bg-opacity-90 rounded-lg shadow-lg shadow-cyan-500/30 border border-gray-700">

      <!-- 渲染聊天消息 -->
      <div v-for="(message, index) in messages" :key="index" class="flex items-start animate-fade-in"
           :class="message.isUser ? 'flex-row-reverse' : 'flex-row'">

        <!-- 用户头像 -->
        <div v-if="message.isUser" class="flex-shrink-0 ml-auto">
          <div v-if="userStore.user && userStore.user.avator" class="w-10 h-10 rounded-full overflow-hidden">
            <img :src="userStore.user.avator" alt="用户头像" class="w-full h-full object-cover" />
          </div>
          <div v-else class="w-10 h-10 rounded-full bg-gradient-to-b from-cyan-500 to-cyan-700 text-white text-lg font-bold flex items-center justify-center">
            User
          </div>
        </div>

        <!-- AI 头像 -->
        <div v-else class="flex-shrink-0 mr-auto">
          <div class="w-10 h-10 rounded-full p-[2px] border-2 border-white">
            <div class="w-full h-full rounded-full bg-gray-100 text-white text-lg font-bold flex items-center justify-center">
              <img class="rounded-full" src="https://so1.360tres.com/t018f408d11d2b5d951.jpg" alt="">
            </div>
          </div>
        </div>

        <!-- 消息内容 -->
        <div :class="message.isUser ? 'flex-1 text-right' : 'flex-1'" class="p-2">
          <!-- 消息气泡 -->
          <div class="p-6 rounded-xl shadow-xl max-w-xs mx-auto relative transition-all duration-300 ease-in-out transform hover:scale-105"
               :class="message.isUser ? 'bg-cyan-900 border border-cyan-700 mr-10 text-gray-200' : 'bg-gray-800 border border-gray-700 ml-10 text-gray-200'">
            <!-- 渲染 Markdown 内容 -->
            <div>{{message.text}}</div>
          </div>
        </div>

      </div>
      <!-- 加载状态 -->
      <div v-if="isLoading" class="flex items-start">
        <div class="flex-shrink-0 mr-auto">
          <div class="w-10 h-10 rounded-full bg-gradient-to-b from-gray-600 to-gray-800 text-white text-lg font-bold flex items-center justify-center">
            AI
          </div>
        </div>
        <div class="flex-1 p-2">
          <div class="p-6 rounded-xl shadow-xl max-w-xs mx-auto bg-gray-800 border border-gray-700 ml-10 text-gray-300">
            <div class="flex items-center justify-center">
              <div class="animate-spin rounded-full h-6 w-6 border-b-2 border-cyan-500"></div>
              <span class="ml-2">AI 正在思考...</span>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 输入框始终显示 -->
    <div class="mt-4 flex items-center space-x-4">
      <div class="flex-1">
        <input
            v-model="userInput"
            type="text"
            placeholder="请输入您的问题..."
            class="w-full p-6 border-2 border-gray-700 rounded-3xl bg-gray-800 text-gray-200 shadow-xl focus:ring-2 focus:ring-cyan-500 focus:outline-none transition-all duration-300 ease-in-out hover:shadow-2xl placeholder-gray-500 text-lg"
            @keydown.enter="askAI"
        />
      </div>
      <button
          @click="askAI"
          class="p-6 bg-gradient-to-r from-cyan-600 to-cyan-800 text-white rounded-3xl hover:bg-gradient-to-r hover:from-cyan-700 hover:to-cyan-900 focus:outline-none focus:ring-2 focus:ring-cyan-500 focus:ring-opacity-50 transition duration-300 ease-in-out transform hover:scale-105"
      >
        <span class="text-xl">发送</span>
      </button>
    </div>
  </div>
</template>

<style scoped>
/* 自定义动画效果 */
@keyframes fadeIn {
  0% {
    opacity: 0;
    transform: translateY(20px);
  }
  100% {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fade-in {
  animation: fadeIn 0.5s ease-out;
}

input:focus, button:focus {
  outline: none;
}

/* 保证输入框始终在页面底部 */
div.flex-1 {
  flex-grow: 1;
  overflow-y: auto;
}

div.mt-4 {
  margin-top: 20px;
}

.bg-gradient-to-br {
  background: linear-gradient(135deg, #f5f7fa, #e0e5ec);
}

.flex-1.overflow-auto.p-4.space-y-4.bg-black.bg-opacity-90.rounded-lg.shadow-lg.shadow-cyan-500\/30.border.border-gray-700 {
  background: #fff !important;
  border: 1px solid #e2e8f0 !important;
  box-shadow: 0 4px 20px rgba(0,0,0,0.05) !important;
}

.p-6.rounded-xl.shadow-xl.max-w-xs.mx-auto.relative.transition-all.duration-300.ease-in-out.transform.hover\:scale-105 {
  background: #f8fafc !important;
  border: 1px solid #e2e8f0 !important;
  color: #333 !important;
}

.p-6.rounded-xl.shadow-xl.max-w-xs.mx-auto.bg-gray-800.border.border-gray-700.ml-10.text-gray-300 {
  background: #f1f5f9 !important;
  border: 1px solid #e2e8f0 !important;
  color: #333 !important;
}

input.w-full.p-6.border-2.border-gray-700.rounded-3xl.bg-gray-800.text-gray-200.shadow-xl.focus\:ring-2.focus\:ring-cyan-500.focus\:outline-none.transition-all.duration-300.ease-in-out.hover\:shadow-2xl.placeholder-gray-500.text-lg {
  background: #fff !important;
  border: 2px solid #e2e8f0 !important;
  color: #333 !important;
}

input.w-full.p-6.border-2.border-gray-700.rounded-3xl.bg-gray-800.text-gray-200.shadow-xl.focus\:ring-2.focus\:ring-cyan-500.focus\:outline-none.transition-all.duration-300.ease-in-out.hover\:shadow-2xl.placeholder-gray-500.text-lg::placeholder {
  color: #94a3b8 !important;
}

button.p-6.bg-gradient-to-r.from-cyan-600.to-cyan-800.text-white.rounded-3xl.hover\:bg-gradient-to-r.hover\:from-cyan-700.hover\:to-cyan-900.focus\:outline-none.focus\:ring-2.focus\:ring-cyan-500.focus\:ring-opacity-50.transition.duration-300.ease-in-out.transform.hover\:scale-105 {
  background: linear-gradient(135deg, #0891b2, #06b6d4) !important;
  color: #fff !important;
}

::placeholder {
  color: rgba(156, 163, 175, 0.7);
}
</style>