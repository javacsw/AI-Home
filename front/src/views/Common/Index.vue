<script setup>
import {ref} from 'vue';
import image1 from '@/assets/images/简约风装修.jpg';
import image2 from '@/assets/images/北欧风格装修.jpg';
import image3 from '@/assets/images/工业风装修.png';
import image4 from '@/assets/images/中式风格客厅.jpg';
import image5 from '@/assets/images/美式风格客厅.jpg';
import image6 from '@/assets/images/欧式风格客厅.jpg';
import image7 from '@/assets/images/地中海风格客厅.png';
import image8 from '@/assets/images/日式风格客厅.jpg';
import image9 from '@/assets/images/田园风格客厅.jpg';
import image10 from '@/assets/images/轻奢风格客厅.png';
import image11 from '@/assets/images/复古风客厅.jpg';
import image12 from '@/assets/images/艺术装饰风格客厅.jpg';
import image13 from '@/assets/images/简约风卧室.jpg';
import image14 from '@/assets/images/北欧风卧室.jpg';
import image15 from '@/assets/images/工业风卧室.jpg';
import image16 from '@/assets/images/中式风格卧室.png';
import image17 from '@/assets/images/美式风格卧室.jpg';
import image18 from '@/assets/images/欧式风格卧室.jpg';
import image19 from '@/assets/images/地中海风格卧室.jpg';
import image20 from '@/assets/images/日式风格卧室.jpg';
import image21 from '@/assets/images/田园风格卧室.jpg';
import image22 from '@/assets/images/轻奢风格卧室.png';
import image23 from '@/assets/images/复古风格卧室.jpg';
import image24 from '@/assets/images/艺术风格卧室.png';
import image25 from '@/assets/images/简约风厨房.jpg';
import image26 from '@/assets/images/北欧风厨房.png';
import image27 from '@/assets/images/工业风厨房.jpg';
import image28 from '@/assets/images/中式风格厨房.jpg';
import image29 from '@/assets/images/美式风格厨房.jpg';
import image30 from '@/assets/images/欧式风格厨房.jpg';
import image31 from '@/assets/images/地中海风格厨房.png';
import image32 from '@/assets/images/日式风格厨房.jpg';
import image33 from '@/assets/images/田园风格厕所.png';
import image34 from '@/assets/images/轻奢风格厨房.png';
import image35 from '@/assets/images/复古风格厨房.jpg';
import image36 from '@/assets/images/艺术风格厨房.png';
import image37 from '@/assets/images/简约风浴室.png';
import image38 from '@/assets/images/北欧风浴室.png';
import image39 from '@/assets/images/工业风浴室.png';
import image40 from '@/assets/images/中式风格浴室.png';
import image41 from '@/assets/images/美式风格浴室.jpg';
import image42 from '@/assets/images/欧式风格浴室.jpg';
import image43 from '@/assets/images/地中海风格浴室.jpg';
import image44 from '@/assets/images/日式风格浴室.png';
import image45 from '@/assets/images/田园风格浴室.jpg';
import image46 from '@/assets/images/轻奢风格浴室.png';
import image47 from '@/assets/images/复古风浴室.png';
import image48 from '@/assets/images/艺术风格浴室.png';
import router from "@/router/index.js";



// 定义响应式数据
const selectedStyle = ref('modern');
const roomTypes = ref(['客厅', '卧室', '厨房', '浴室']);
const selectedRoom = ref('客厅');

// 定义不同房间的设计灵感数据
const designIdeas = ref({
  客厅: [
    {id: 1, name: '现代简约', image: image1, description: '简洁线条，明亮色调'},
    {id: 2, name: '北欧风格', image: image2, description: '自然材质，清新色调'},
    {id: 3, name: '工业风', image: image3, description: '粗犷质感，金属元素'},
    {id: 4, name: '中式风格', image: image4, description: '古今结合，雅致现代'},
    {id: 5, name: '美式风格', image: image5, description: '大气舒适，温馨实用'},
    {id: 6, name: '欧式风格', image: image6, description: '奢华典雅，浪漫精致'},
    {id: 7, name: '地中海风格', image: image7, description: '清新浪漫，海洋风情'},
    {id: 8, name: '日式风格', image: image8, description: '简约宁静，禅意自然'},
    {id: 9, name: '田园风格', image: image9, description: '自然质朴，温馨舒适'},
    {id: 10, name: '轻奢风格', image: image10, description: '低调奢华，精致优雅'},
    {id: 11, name: '复古风格', image: image11, description: '怀旧经典，历史韵味'},
    {id: 12, name: '艺术装饰风格', image: image12, description: '奢华几何，艺术个性'},
  ],
  卧室: [
    {id: 13, name: '现代简约', image: image13, description: '简洁实用 | 干净利落'},
    {id: 14, name: '北欧风格', image: image14, description: '清新自然 | 明亮舒适'},
    {id: 15, name: '工业风', image: image15, description: '粗犷原始 | 金属质感'},
    {id: 16, name: '中式风格', image: image16, description: '典雅古朴 | 木质元素'},
    {id: 17, name: '美式风格', image: image17, description: '大气舒适 | 复古温馨'},
    {id: 18, name: '欧式风格', image: image18, description: '奢华典雅 | 精致雕花'},
    {id: 19, name: '地中海风格', image: image19, description: '海洋风情 | 蓝白清新'},
    {id: 20, name: '日式风格', image: image20, description: '简约宁静 | 自然禅意'},
    {id: 21, name: '田园风格', image: image21, description: '自然质朴 | 温馨舒适'},
    {id: 22, name: '轻奢风格', image: image22, description: '低调奢华 | 精致质感'},
    {id: 23, name: '复古风格', image: image23, description: '怀旧经典 | 历史韵味'},
    {id: 24, name: '艺术装饰风格', image: image24, description: '几何奢华 | 艺术个性'},
  ],
  厨房: [
    {id: 25, name: '现代简约', image: image25, description: '功能至上 | 简洁明快'},
    {id: 26, name: '北欧风格', image: image26, description: '自然纯净 | 温暖明亮'},
    {id: 27, name: '工业风', image: image27, description: '冷峻硬朗 | 原始质感'},
    {id: 28, name: '中式风格', image: image28, description: '古典沉稳 | 木质韵味'},
    {id: 29, name: '美式风格', image: image29, description: '宽敞大气 | 复古怀旧'},
    {id: 30, name: '欧式风格', image: image30, description: '华丽精致 | 典雅浪漫'},
    {id: 31, name: '地中海风格', image: image31, description: '清新浪漫 | 蓝白交织'},
    {id: 32, name: '日式风格', image: image32, description: '简约素雅 | 自然宁静'},
    {id: 33, name: '田园风格', image: image33, description: '质朴温馨 | 自然舒适'},
    {id: 34, name: '轻奢风格', image: image34, description: '精致优雅 | 低调奢华'},
    {id: 35, name: '复古风格', image: image35, description: '怀旧经典 | 时光韵味'},
    {id: 36, name: '艺术装饰风格', image: image36, description: '几何艺术 | 奢华个性'},
  ],
  浴室: [
    {id: 37, name: '现代简约', image: image37, description: '简洁明快 | 功能实用'},
    {id: 38, name: '北欧风格', image: image38, description: '清新自然 | 温暖舒适'},
    {id: 39, name: '工业风', image: image39, description: '粗犷硬朗 | 个性冷峻'},
    {id: 40, name: '中式风格', image: image40, description: '古典雅致 | 沉稳宁静'},
    {id: 41, name: '美式风格', image: image41, description: '大气舒适 | 复古怀旧'},
    {id: 42, name: '欧式风格', image: image42, description: '奢华典雅 | 浪漫精致'},
    {id: 43, name: '地中海风格', image: image43, description: '清新浪漫 | 蓝白交织'},
    {id: 44, name: '日式风格', image: image44, description: '简约宁静 | 自然禅意'},
    {id: 45, name: '田园风格', image: image45, description: '质朴温馨 | 自然舒适'},
    {id: 46, name: '轻奢风格', image: image46, description: '精致优雅 | 低调奢华'},
    {id: 47, name: '复古风格', image: image47, description: '几何艺术 | 奢华个性'},
    {id: 48, name: '艺术装饰风格', image: image48, description: '奢华几何，艺术个性'},
  ],
});

// 切换房间类型
const switchRoom = (room) => {
  selectedRoom.value = room;
};

function goToFoodDetail(homeId) {
  router.push({ name: 'home', params: { id: homeId } });
}
</script>

<template>
  <div class="decor-page">
    <!-- 头部 -->
    <header class="header">
      <h1>AI 装修设计灵感</h1>
      <p>探索适合您的装修风格，打造理想家居空间</p>
    </header>

    <!-- 主体内容 -->
    <main class="main-content">
      <!-- 房间选择 -->
      <div class="room-selector">
        <button
            v-for="room in roomTypes"
            :key="room"
            :class="{ active: selectedRoom === room }"
            @click="switchRoom(room)"
        >
          {{ room }}
        </button>
      </div>

      <!-- 设计灵感展示 -->
      <div class="design-grid">
        <div
            v-for="idea in designIdeas[selectedRoom]"
            :key="idea.id"
            class="design-card"
        >
          <img :src="idea.image" :alt="idea.name"/>
          <div class="card-content">
            <h3>{{ idea.name }}</h3>
            <p>{{ idea.description }}</p>
            <button class="view-button"
                    @click="goToFoodDetail(idea.id)"
            >查看详情</button>
          </div>
        </div>
      </div>
    </main>

    <!-- 底部 -->
    <footer class="footer">
      <p>&copy; 2025 AI 装修设计平台. 本项目人员保留所有权利.</p>
    </footer>
  </div>
</template>

<style scoped>
/* 全局样式 */
.decor-page {
  font-family: 'Arial', sans-serif;
  background: linear-gradient(135deg, #f5f7fa, #e0e5ec);
  color: #333;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

/* 头部样式 */
.header {
  background-color: rgba(255, 255, 255, 0.8);
  color: #333;
  padding: 40px 20px;
  text-align: center;
  border-bottom: 1px solid #e2e8f0;
}

.header h1 {
  font-size: 2.5rem;
  margin-bottom: 10px;
  color: #000000; /* cyan-600 */
}

.header p {
  font-size: 1.2rem;
  opacity: 0.8;
  color: #475569; /* slate-600 */
}

/* 主体内容样式 */
.main-content {
  flex: 1;
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  width: 100%;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 12px;
  margin-top: 20px;
  border: 1px solid #e2e8f0;
}

/* 房间选择器样式 */
.room-selector {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-bottom: 30px;
}

.room-selector button {
  padding: 10px 20px;
  border: none;
  border-radius: 25px;
  background-color: #e2e8f0; /* slate-200 */
  color: #334155; /* slate-700 */
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
  border: 1px solid #cbd5e1; /* slate-300 */
}

.room-selector button.active {
  background-color: #0891b2; /* cyan-600 */
  color: #f8fafc; /* slate-50 */
  border-color: #06b6d4; /* cyan-500 */
}

.room-selector button:hover {
  transform: translateY(-2px);
  box-shadow: 0 0 15px rgba(6, 182, 212, 0.3); /* cyan-500 with opacity */
}

/* 设计灵感网格样式 */
.design-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
}

.design-card {
  background-color: #ffffff;
  border-radius: 10px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  border: 1px solid #e2e8f0;
}

.design-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(6, 182, 212, 0.1);
  border-color: #0891b2; /* cyan-600 */
}

.design-card img {
  width: 100%;
  height: 200px;
  object-fit: cover;
  filter: brightness(1);
  transition: filter 0.3s ease;
}

.design-card:hover img {
  filter: brightness(1.05);
}

.card-content {
  padding: 15px;
  text-align: center;
}

.card-content h3 {
  font-size: 1.5rem;
  margin-bottom: 10px;
  color: #000000; /* cyan-600 */
}

.card-content p {
  font-size: 0.9rem;
  color: #64748b; /* slate-500 */
  margin-bottom: 15px;
}

.view-button {
  padding: 8px 16px;
  border: none;
  border-radius: 20px;
  background-color: #0891b2; /* cyan-600 */
  color: #fff;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.view-button:hover {
  background-color: #06b6d4; /* cyan-500 */
  transform: scale(1.05);
  box-shadow: 0 0 15px rgba(6, 182, 212, 0.4);
}

/* 底部样式 */
.footer {
  background-color: rgba(255, 255, 255, 0.8);
  color: #64748b; /* slate-500 */
  text-align: center;
  padding: 20px;
  margin-top: auto;
  border-top: 1px solid #e2e8f0;
}

.footer p {
  margin: 0;
  font-size: 0.9rem;
}
</style>