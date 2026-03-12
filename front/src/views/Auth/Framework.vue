<script setup>
import { computed, onMounted, ref, onUnmounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useUserStore } from "@/stores/userStore.js";
import { get } from "@/net/index.js";

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();
const showDropdown = ref(false); // 控制下拉菜单的显示与隐藏
const isScrolled = ref(false); // 控制滚动状态
let closeTimeout = null; // 用于延迟关闭悬浮窗体

const initUserData = () => {
  get('api/user/myinfo', {}, (message, data) => {
    userStore.login(data);
  });
};

// 获取当前路由路径
const currentRoute = computed(() => route.path);

// 监听滚动事件
const handleScroll = () => {
  isScrolled.value = window.scrollY > 0;
};

onMounted(() => {
  initUserData();
  window.addEventListener('scroll', handleScroll);
});

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});

const handleLogout = () => {
  // 处理退出登录逻辑
  userStore.logout();
  router.push('/auth');
  closeDropdown(); // 关闭下拉菜单
};

const handleViewDetails = () => {
  // 处理查看详细信息逻辑
  router.push('/backstage');
  closeDropdown(); // 关闭下拉菜单
};
const myblog = () => {
  // 处理查看详细信息逻辑
  router.push('/myblog');
  closeDropdown(); // 关闭下拉菜单
};
const mylike = () => {
  // 处理查看详细信息逻辑
  router.push('/mylike');
  closeDropdown(); // 关闭下拉菜单
};

const me = () => {
  // 处理查看详细信息逻辑
  router.push('/me');
  closeDropdown(); // 关闭下拉菜单
};

const openDropdown = () => {
  clearTimeout(closeTimeout); // 清除之前的关闭延迟
  showDropdown.value = true;
};

const closeDropdown = () => {
  showDropdown.value = false;
};

const startCloseTimeout = () => {
  closeTimeout = setTimeout(() => {
    closeDropdown();
  }, 300); // 设置 300ms 的延迟关闭
};

const clearCloseTimeout = () => {
  if (closeTimeout) {
    clearTimeout(closeTimeout);
  }
};
</script>

<template>
  <!-- 导航栏 -->
  <nav :class="[
    'text-black fixed top-0 left-0 w-full shadow-md z-10 backdrop-blur-sm transition-all duration-300',
    isScrolled ? 'bg-white' : 'bg-[#f5f5f5]'
  ]">
    <div class="container mx-auto flex justify-between items-center py-6 px-6">
      <!-- 左侧：网站logo -->
      <div @click="router.push('/')" class="flex items-center cursor-pointer">

        <span class="ml-2 site-title">SmartHome</span>
      </div>

      <!-- 导航选项 -->
      <div class="flex items-center space-x-8">
        <div
            @click="router.push('/')"
            :class="{'text-blue-600 border-b-2 border-blue-600': currentRoute === '/'}"
            class="cursor-pointer font-sans text-base font-medium hover:text-blue-600 hover:scale-105 transition-all duration-300"
        >
          首页
        </div>

        <div
            @click="router.push('/notes')"
            :class="{'text-blue-600 border-b-2 border-blue-600': currentRoute === '/notes'}"
            class="cursor-pointer font-sans text-base font-medium hover:text-blue-600 hover:scale-105 transition-all duration-300"
        >
          反馈
        </div>
        <div
            @click="router.push('/ExhibitionHall')"
            :class="{'text-blue-600 border-b-2 border-blue-600': currentRoute === '/ExhibitionHall'}"
            class="cursor-pointer font-sans text-base font-medium hover:text-blue-600 hover:scale-105 transition-all duration-300"
        >
          3D展馆
        </div>
        <div
            @click="router.push('/chat')"
            :class="{'text-blue-600 border-b-2 border-blue-600': currentRoute === '/chat'}"
            class="cursor-pointer font-sans text-base font-medium hover:text-blue-600 hover:scale-105 transition-all duration-300"
        >
          聊天室
        </div>
        <div
            @click="router.push('/we')"
            :class="{'text-blue-600 border-b-2 border-blue-600': currentRoute === '/we'}"
            class="cursor-pointer font-sans text-base font-medium hover:text-blue-600 hover:scale-105 transition-all duration-300"
        >
          关于我们
        </div>
      </div>

      <!-- 右侧：用户头像和主题切换 -->
      <div class="flex items-center gap-4">
        <template v-if="userStore.user !== null">
          <div
              @mouseenter="openDropdown"
              @mouseleave="startCloseTimeout"
              class="relative flex items-center gap-2 cursor-pointer"
          >
            <img
                :src="useUserStore().user.avator==null?'https://narcissism-66.github.io/image/1.jpg':userStore.user.avator"
                alt="User Avatar"
                class=" w-10 h-10 rounded-full transition-transform duration-300 hover:scale-125 hover:shadow-lg"
            />
            <!-- 下拉菜单 -->
            <div
                v-if="showDropdown"
                @mouseenter="clearCloseTimeout"
                @mouseleave="startCloseTimeout"
                class="absolute top-12 left-1/2 transform -translate-x-1/2 bg-white text-gray-800 rounded-lg shadow-lg py-2 w-48"
            >
              <div
                  @click.stop="myblog"
                  class="px-4 py-2 hover:bg-gray-100 cursor-pointer"
              >
                我的博客
              </div>
              <div
                  @click.stop="mylike"
                  class="px-4 py-2 hover:bg-gray-100 cursor-pointer"
              >
                我的喜欢
              </div>
              <div
                  @click.stop="me"
                  class="px-4 py-2 hover:bg-gray-100 cursor-pointer"
              >
                查看详细信息
              </div>
              <div
                v-if="userStore.user && userStore.user.role === 1"
                @click.stop="handleViewDetails"
                class="px-4 py-2 hover:bg-gray-100 cursor-pointer"
              >
                管理员系统
              </div>
              <div
                  @click.stop="handleLogout"
                  class="px-4 py-2 hover:bg-gray-100 cursor-pointer"
              >
                退出登录
              </div>
            </div>
          </div>
        </template>
        <template v-else>
          <button @click="router.push('/auth')" class="register-btn">开始/注册</button>
        </template>
      </div>
    </div>
  </nav>

  <!-- 主要内容 -->
  <main class="basic-color mt-16">
    <router-view />
  </main>
</template>

<style scoped>
/* 自定义样式 */
nav {
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(8px);
  -webkit-backdrop-filter: blur(8px);
}

.cursor-logo {
  width: 50px;
  height: 20px;
  perspective: 800px;
  margin-right: 4px;
}

.cube {
  width: 100%;
  height: 100%;
  position: relative;
  transform-style: preserve-3d;
  transition: transform 0.5s;
  transform: rotateX(-25deg) rotateY(45deg);
}

.cursor-logo:hover .cube {
  animation: rotate 2s infinite linear;
}

.face {
  position: absolute;
  width: 30px;
  height: 30px;
  background: #2C3E50;
  border: 2px solid #ffffff;
  opacity: 0.95;
}

.front  { transform: rotateY(0deg) translateZ(15px); background: #34495e; }
.back   { transform: rotateY(180deg) translateZ(15px); background: #2C3E50; }
.right  { transform: rotateY(90deg) translateZ(15px); background: #2C3E50; }
.left   { transform: rotateY(-90deg) translateZ(15px); background: #34495e; }
.top    { transform: rotateX(90deg) translateZ(15px); background: #34495e; }
.bottom { transform: rotateX(-90deg) translateZ(15px); background: #2C3E50; }

@keyframes rotate {
  from {
    transform: rotateX(-25deg) rotateY(45deg);
  }
  to {
    transform: rotateX(335deg) rotateY(405deg);
  }
}

.site-title {
  font-family: HarmonyOS Sans;
  font-size: 1.6rem;
  font-weight: 500;
  letter-spacing: 2px;
  color: #333;
  text-shadow: none;
  background: none;
  -webkit-text-fill-color: #333;
  transition: all 0.3s ease;
}

.register-btn {
  padding: 0.5rem 1.2rem;
  font-size: 1.1rem;
  color: #fff;
  background-color: #2563eb;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  transition: background 0.2s, transform 0.2s;
  font-family: inherit;
  font-weight: 500;
}
.register-btn:hover {
  background-color: #1d4ed8;
  transform: translateY(-2px) scale(1.05);
}
</style>