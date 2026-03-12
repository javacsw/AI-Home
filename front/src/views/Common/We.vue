<template>
  <div class="min-h-screen flex flex-col items-center py-12 bg-[#f5f5f5] from-blue-100 to-purple-100">
    <!-- 标题 -->


    <!-- 更新日志 -->
    <div class="max-w-4xl w-full bg-white rounded-xl shadow-lg p-8 mx-4 border border-gray-200">
      <h2 class="text-3xl font-bold mb-6 text-center text-gray-800">更新情报日志</h2>
      <div class="space-y-6">
        <div class="update-item">
          <div class="flex items-center mb-2">
            <span class="text-sm text-gray-500">2024-03-20</span>
            <span class="ml-2 px-2 py-1 bg-blue-100 text-blue-800 rounded-full text-sm">新功能</span>
          </div>
          <h3 class="text-xl font-semibold mb-2 text-gray-900">AI智能设计功能全面升级</h3>
          <p class="text-gray-700">我们推出了全新的AI智能设计系统，支持一键生成多种风格的装修效果图。新系统采用最新的深度学习算法，能够更准确地理解用户需求，生成更符合实际的设计方案。同时新增了以下功能：</p>
          <ul class="list-disc list-inside text-gray-700 mt-2 ml-4">
            <li>支持多种装修风格智能切换</li>
            <li>新增3D全景预览功能</li>
            <li>支持自定义材质和颜色搭配</li>
            <li>智能预算评估系统</li>
          </ul>
        </div>

        <div class="update-item">
          <div class="flex items-center mb-2">
            <span class="text-sm text-gray-500">2024-03-15</span>
            <span class="ml-2 px-2 py-1 bg-green-100 text-green-800 rounded-full text-sm">优化</span>
          </div>
          <h3 class="text-xl font-semibold mb-2 text-gray-900">用户体验全面优化升级</h3>
          <p class="text-gray-700">我们对整个平台进行了全面的用户体验优化，让操作更加流畅自然。主要优化内容包括：</p>
          <ul class="list-disc list-inside text-gray-700 mt-2 ml-4">
            <li>全新设计的用户界面，更符合直觉的操作流程</li>
            <li>优化移动端适配，支持更多设备类型</li>
            <li>新增深色模式支持，保护用户眼睛</li>
            <li>改进加载速度，页面响应更快</li>
            <li>新增智能提示系统，帮助用户更好地使用功能</li>
          </ul>
        </div>

        <div class="update-item">
          <div class="flex items-center mb-2">
            <span class="text-sm text-gray-500">2024-03-10</span>
            <span class="ml-2 px-2 py-1 bg-purple-100 text-purple-800 rounded-full text-sm">修复</span>
          </div>
          <h3 class="text-xl font-semibold mb-2 text-gray-900">系统稳定性提升与问题修复</h3>
          <p class="text-gray-700">我们对系统进行了全面的稳定性优化，修复了多个已知问题，提升了整体性能：</p>
          <ul class="list-disc list-inside text-gray-700 mt-2 ml-4">
            <li>修复了图片生成时的内存泄漏问题</li>
            <li>解决了移动端页面布局错乱的问题</li>
            <li>优化了数据加载机制，减少卡顿现象</li>
            <li>修复了部分浏览器兼容性问题</li>
            <li>提升了系统整体稳定性，减少崩溃概率</li>
          </ul>
        </div>

        <div class="update-item">
          <div class="flex items-center mb-2">
            <span class="text-sm text-gray-500">2024-03-05</span>
            <span class="ml-2 px-2 py-1 bg-yellow-100 text-yellow-800 rounded-full text-sm">改进</span>
          </div>
          <h3 class="text-xl font-semibold mb-2 text-gray-900">设计素材库扩充与优化</h3>
          <p class="text-gray-700">我们大幅扩充了设计素材库，为用户提供更多选择：</p>
          <ul class="list-disc list-inside text-gray-700 mt-2 ml-4">
            <li>新增超过1000种家具模型</li>
            <li>扩充材质库，新增500种材质选项</li>
            <li>新增多种风格的地板、墙纸、窗帘等装饰材料</li>
            <li>优化素材分类系统，方便快速查找</li>
            <li>新增素材预览功能，支持360度查看</li>
          </ul>
        </div>

        <div class="update-item">
          <div class="flex items-center mb-2">
            <span class="text-sm text-gray-500">2024-03-01</span>
            <span class="ml-2 px-2 py-1 bg-red-100 text-red-800 rounded-full text-sm">安全</span>
          </div>
          <h3 class="text-xl font-semibold mb-2 text-gray-900">系统安全升级</h3>
          <p class="text-gray-700">我们加强了系统的安全性，保护用户数据安全：</p>
          <ul class="list-disc list-inside text-gray-700 mt-2 ml-4">
            <li>升级了数据加密算法，提供更强的安全性</li>
            <li>新增双因素认证功能</li>
            <li>优化了用户隐私保护机制</li>
            <li>新增安全日志记录系统</li>
            <li>定期进行安全漏洞扫描和修复</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const startService = () => {
  alert('服务即将开始，请稍候...');
};
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Ma+Shan+Zheng&display=swap');
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css');

.font-title {
  font-family: "Ma Shan Zheng", cursive;
  letter-spacing: 2px;
}

.title-text {
  position: relative;
  background: linear-gradient(135deg, #00c8ff, #00e1ff, #60efff);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;

}

@keyframes titlePulse {
  0% {
    text-shadow: 0 0 10px rgba(0, 200, 255, 0.7),
                 0 0 20px rgba(0, 200, 255, 0.4),
                 0 0 30px rgba(0, 200, 255, 0.2);
  }
  50% {
    text-shadow: 0 0 15px rgba(0, 200, 255, 0.9),
                 0 0 25px rgba(0, 200, 255, 0.6),
                 0 0 35px rgba(0, 200, 255, 0.3);
  }
  100% {
    text-shadow: 0 0 20px rgba(0, 200, 255, 0.8),
                 0 0 30px rgba(0, 200, 255, 0.5),
                 0 0 40px rgba(0, 200, 255, 0.3);
  }
}

@keyframes titleGlow {
  from {
    text-shadow: 0 0 10px rgba(255, 255, 255, 0.5),
                 0 0 20px rgba(255, 255, 255, 0.3),
                 0 0 30px rgba(255, 255, 255, 0.2);
  }
  to {
    text-shadow: 0 0 20px rgba(255, 255, 255, 0.8),
                 0 0 30px rgba(255, 255, 255, 0.5),
                 0 0 40px rgba(255, 255, 255, 0.3);
  }
}

.bg-gradient-to-br {
  /* 统一背景渐变色 */
  background: linear-gradient(120deg, #2d1746 0%, #6a4bc7 40%, #7b5fd3 70%, #2d1746 100%) !important;
}

.text-gradient {
  background: linear-gradient(135deg, white, white);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
}

.social-card {
  @apply bg-white p-6 rounded-xl shadow-lg transform transition-transform duration-300 hover:scale-105;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  text-align: center;
}

.icon-wrapper {
  @apply w-16 h-16 rounded-full flex items-center justify-center mx-auto mb-4;
  background: rgba(255, 255, 255, 0.1);
}

.update-item {
  @apply p-4 rounded-lg transition-all duration-300 border;
}

.update-item:hover {
  @apply transform -translate-y-1;
  background: #f9fafb;
}

@media (max-width: 768px) {
  .text-8xl {
    font-size: 4rem;
  }
}
</style>