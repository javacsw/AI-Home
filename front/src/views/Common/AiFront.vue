<script setup>
// 这里可以添加一些逻辑代码，如果有需要的话
import image1 from '@/assets/images/湖边.jpg';
import router from "@/router/index.js";
import { ref, onMounted, onUnmounted } from 'vue';

import img1 from '@/assets/images/项目展示图片1.png';
import img2 from '@/assets/images/项目展示图片2.png';
import img3 from '@/assets/images/项目展示图片3.png';
import img4 from '@/assets/images/项目展示图片4.png';
import img5 from '@/assets/images/项目展示图片5.png';
import img6 from '@/assets/images/项目展示图片6.png';
import img7 from '@/assets/images/项目展示图片7.png';
import img8 from '@/assets/images/项目展示图片8.png';
import img9 from '@/assets/images/项目展示图片9.png';

const featureImages = [
  img1, img2, img3, img4, img5, img6, img7, img8, img9
];
const currentIndex = ref(0);
let intervalId = null;

onMounted(() => {
  intervalId = setInterval(() => {
    currentIndex.value = (currentIndex.value + 1) % featureImages.length;
  }, 2000); // 每2秒切换
});

onUnmounted(() => {
  if (intervalId) clearInterval(intervalId);
});
</script>

<template>
  <div class="Aiitbg">
    <div class="main-content">
      <div class="main-header-row">
        <div class="text-section">
          <h1 class="main-title">
            让装修与设计<br />因智能而更便捷
          </h1>
          <h2 class="sub-title">
            一站式智能设计与装修平台，助您轻松打造理想家园，体验高效便捷服务
          </h2>
        </div>
        <img :src="image1" alt="湖边" class="main-image-inline" />
      </div>
      <!-- 轮播图与按钮区域 -->
      <div class="feature-carousel-vertical">
        <h1 class="section-title" style="text-align:center;width:100%;margin-bottom:24px;">项目核心功能展示</h1>
        <div class="feature-carousel-card card-hover-effect">
          <img :src="featureImages[currentIndex]" alt="项目展示图片" class="feature-carousel-img img-hover-effect" />
        </div>
        <div class="carousel-pagination">
          <button
            v-for="(img, idx) in featureImages"
            :key="idx"
            :class="['carousel-dot', { active: idx === currentIndex }]"
            @click="currentIndex = idx"
          >
            {{ idx + 1 }}
          </button>
        </div>
      </div>
    </div>
    <!-- 保持底部内容居中 -->
    <div class="bottom-section">
      <h1 class="section-title" style="text-align:center;width:100%;margin-bottom:24px;">项目相关内容介绍</h1>
      <h1 class="section-title" style="margin-top: 60px;">更快的构建功能，更好的设计服务</h1>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 max-w-6xl px-4">
        <div class="tech-card">
          <h3 class="text-2xl font-bold mb-3 text-black">专业设计团队</h3>
          <p class="text-gray-600">拥有多年经验的设计师团队，为您打造独特的居住空间，让每一处细节都完美呈现。</p>
        </div>
        <div class="tech-card">
          <h3 class="text-2xl font-bold mb-3 text-black">开发者的明治之选</h3>
          <p class="text-gray-600">在评估过程中，有83%的用户将智绘家园评为最好的测绘软件。</p>
        </div>
        <div class="tech-card">
          <h3 class="text-2xl font-bold mb-3 text-black">工艺保证</h3>
          <p class="text-gray-600">采用先进施工工艺，专业施工团队，确保装修质量，打造精品工程。</p>
        </div>
        <div class="tech-card">
          <h3 class="text-2xl font-bold mb-3 text-black">一站式服务</h3>
          <p class="text-gray-600">从设计到施工，从选材到售后，提供全方位一站式装修服务。</p>
        </div>
        <div class="tech-card">
          <h3 class="text-2xl font-bold mb-3 text-black">智能家居</h3>
          <p class="text-gray-600">融入现代智能家居技术，让您的家更智能、更便捷、更舒适。</p>
        </div>
        <div class="tech-card">
          <h3 class="text-2xl font-bold mb-3 text-black">售后保障</h3>
          <p class="text-gray-600">提供长期质保服务，确保您的居住体验，解决后顾之忧。</p>
        </div>
      </div>
      <!-- 立刻开始测绘模块 -->
      <div class="start-survey-section">
        <h2 class="start-survey-title">准备好了吗？立刻开启您的智能测绘之旅！</h2>
        <button class="start-survey-btn" @click="router.push('/centre')">立刻开始测绘</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.Aiitbg {
  min-height: 100vh;
  width: 100%;
  font-family: Arial, Helvetica, sans-serif;
  padding: 90px 0 100px 0;
  background: linear-gradient(120deg, #f5f5f5 0%, #f5f5f5 40%, #f5f5f5 70%, #f5f5f5 100%);
}

.main-content {
  width: 100%;
  max-width: 1800px;
  margin: 0 auto 0 auto;
  margin-bottom: 120px;
}

.main-header-row {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  gap: 32px;
}

.text-section {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: flex-start;
  text-align: left;
}

.main-image-inline {
  width: 1200px;
  height: 680px;
  object-fit: cover;
  border-radius: 24px;
  box-shadow: 0 4px 32px rgba(0,0,0,0.12);
}

.main-title {
  font-size: 3.5rem;
  font-weight: bold;
  color: #000;
  text-align: left;
  font-family: Arial, Helvetica, sans-serif;
  margin: 180px 0 16px 0;
  letter-spacing: 0.08em;
}

.sub-title {
  font-size: 1.5rem;
  color: #606266;
  margin-bottom: 32px;
  text-align: left;
  font-family: Arial, Helvetica, sans-serif;
  font-weight: normal;
  letter-spacing: 0.06em;
}

.section-title {
  font-size: 2.2rem;
  color: #000;
  margin: 40px 0 24px 0;
  text-align: left;
  font-family: Arial, Helvetica, sans-serif;
  font-weight: normal;
  letter-spacing: 0.06em;
}

.action-button {
  padding: 1.2rem 2.5rem;
  font-size: 1.4rem;
  color: #ffffff;
  background-color: #6c8ebf;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  box-shadow: 2px 2px 6px rgba(0, 0, 0, 0.3);
  margin-bottom: 50px;
  text-align: left;
  font-family: Arial, Helvetica, sans-serif;
}

.action-button:hover {
  background-color: #5a7ca9;
}

/* 保持底部内容和卡片样式不变 */
.card-title {
  color: #262626;
  font-size: 2.5em;
  line-height: normal;
  font-weight: 700;
  margin-bottom: 0.5em;
}

.small-desc {
  font-size: 1.5em;
  font-weight: 400;
  line-height: 1.5em;
  color: #452c2c;
}

.small-desc {
  font-size: 1em;
}

.go-corner {
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  width: 2em;
  height: 2em;
  overflow: hidden;
  top: 0;
  right: 0;
  background: linear-gradient(300deg, #6293c8, #384c6c);
  border-radius: 0 4px 0 32px;
}

.go-arrow {
  margin-top: -4px;
  margin-right: -4px;
  color: white;
  font-family: courier, sans;
}

.card {
  display: block;
  position: relative;
  max-width: 600px;
  max-height: 400px;
  background-color: #f2f8f9;
  border-radius: 10px;
  padding: 3em 2em;
  margin: 12px;
  text-decoration: none;
  z-index: 0;
  overflow: hidden;
  background: linear-gradient(to bottom, #c3e6ec, #a7d1d9);
  font-family: Arial, Helvetica, sans-serif;
}

.card:before {
  content: '';
  position: absolute;
  z-index: -1;
  top: -16px;
  right: -16px;
  background: linear-gradient(135deg, #364a60, #384c6c);
  height: 32px;
  width: 32px;
  border-radius: 32px;
  transform: scale(1);
  transform-origin: 50% 50%;
  transition: transform 0.35s ease-out;
}

.card:hover:before {
  transform: scale(28);
}

.card:hover .small-desc {
  transition: all 0.5s ease-out;
  color: rgba(255, 255, 255, 0.8);
}

.card:hover .card-title {
  transition: all 0.5s ease-out;
  color: #ffffff;
}

.card-list {
  display: flex;
  gap: 20px;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
}

.card-container {
  width: 260px;
  background: #000000;
  padding: 4px;
  border-radius: 32px;
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
  border: 2px solid #808080;
  box-shadow: 0 0 10px rgba(128, 128, 128, 0.3);
}

.card-container .title-card {
  display: flex;
  align-items: center;
  padding: 16px 18px;
  justify-content: space-between;
  color: #fff;
}

.card-container .title-card p {
  font-size: 14px;
  font-weight: 600;
  font-style: italic;
  text-shadow: 2px 2px 6px #2975ee;
  margin-bottom: 10px;
}

.card-container .card-content {
  width: 100%;
  height: 100%;
  padding: 16px;
}

.card-container .card-content .title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #fff;
  margin-bottom: 15px;
}

.card-container .card-content .plain {
  font-size: 1.2rem;
  color: #fff;
  margin-bottom: 15px;
}

.card-container .card-content .description {
  font-size: 1rem;
  color: #fff;
  line-height: 1.6;
  margin-bottom: 10px;
  font-family: "Brush Script MT";
}

.card-container .card-content .card-btn {
  width: 100%;
  padding: 12px 24px;
  font-size: 1rem;
  font-weight: bold;
  color: #090808;
  background-color: #eeeded;
  border: none;
  border-radius: 25px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
  box-shadow: 2px 2px 6px rgba(0, 0, 0, 0.3);
  margin-top: 20px;
}

.card-container .card-content .card-btn:hover {
  background-color: #5a7ca9;
  transform: translateY(-2px);
}

.card-container .card-content .card-btn:active {
  transform: translateY(0);
}

@keyframes clockwise {
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(360deg);
  }
}

@keyframes counter-clockwise {
  0% {
    transform: rotate(0deg);
  }

  100% {
    transform: rotate(-360deg);
  }
}

.tech-card {
  @apply bg-[#ffffff] p-6 rounded-xl shadow-lg transform transition-transform duration-300 hover:scale-105;
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.grid {
  margin-bottom: 100px;
}

.pricing-container {
  display: flex;
  justify-content: center;
  align-items: stretch;
  flex-wrap: wrap;
  gap: 2rem;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto 50px auto;
}

.pricing-card {
  background-color: #ffffff;
  border-radius: 16px;
  padding: 2rem;
  width: 320px;
  display: flex;
  flex-direction: column;
  border: 1px solid #e9ecef;
  transition: all 0.3s ease;
}

.pricing-card.pro {
  background: linear-gradient(135deg, #f9d2f2, #cde5ff, #c3f7e2);
  transform: scale(1.05);
  border: none;
  color: #000;
}

.card-header .plan-name {
  font-size: 1.25rem;
  font-weight: 500;
  color: #6c757d;
  margin-bottom: 0.5rem;
}

.card-header .plan-price {
  font-size: 2.5rem;
  font-weight: 600;
  color: #000;
}

.plan-price .price-period {
  font-size: 1rem;
  font-weight: 400;
  color: #6c757d;
}

.plan-divider {
  height: 1px;
  background-color: #dee2e6;
  margin: 1.5rem 0;
}

.pro .plan-divider {
  background-color: rgba(0,0,0,0.1);
}

.plan-features .includes-title {
  font-size: 0.9rem;
  color: #6c757d;
  margin-bottom: 1rem;
}

.plan-features ul {
  list-style: none;
  padding: 0;
  margin: 0;
  flex-grow: 1;
}

.plan-features ul li {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  margin-bottom: 1rem;
  font-size: 0.95rem;
  color: #212529;
}

.plan-features ul li svg {
  width: 20px;
  height: 20px;
  stroke: #212529;
}

.plan-footer {
  margin-top: auto;
  display: flex;
  gap: 1rem;
}

.btn-download, .btn-other, .btn-get-pro, .btn-get-ultra {
  width: 100%;
  padding: 0.75rem 1rem;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  border: none;
}

.btn-download, .btn-get-pro, .btn-get-ultra {
  background-color: #000;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

.btn-other {
  background-color: #fff;
  color: #000;
  border: 1px solid #dee2e6;
}

.btn-get-pro, .btn-get-ultra {
  flex-grow: 1;
}

.btn-download:hover, .btn-get-pro:hover, .btn-get-ultra:hover {
  opacity: 0.85;
}
.btn-other:hover {
  background-color: #f1f3f5;
}

.core-features {
  margin-top: 24px;
  margin-bottom: 24px;
  font-size: 1.35rem;
  color: #222;
  line-height: 2.2;
  padding-left: 0;
  list-style: none;
  letter-spacing: 0.04em;
}

.core-features li {
  margin-bottom: 10px;
  text-align: left;
}

.core-features-section-inline {
  margin-top: 140px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.bottom-section {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin-top: 120px;
}

.core-features-row {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  gap: 48px;
  width: 100%;
  margin-top: 140px;
}

.feature-carousel-vertical {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  margin-top: 80px;
  width: 100%;
  margin-bottom: 120px;
}

.feature-carousel-card {
  background: transparent;
  display: flex;
  align-items: center;
  justify-content: center;
  min-width: 1000px;
  max-width: 1200px;
  min-height: 400px;
  max-height: 520px;
  padding: 36px 28px;
  border-radius: 32px;
  border: 2px solid #e0e0e0;
  transition: box-shadow 0.3s, transform 0.3s;
  box-shadow: 0 2px 12px rgba(0,0,0,0.06);
}

.card-hover-effect:hover {
  box-shadow: 0 8px 32px rgba(0,0,0,0.18);
  transform: translateY(-8px) scale(1.03);
}

.feature-carousel-img {
  width: 100%;
  height: 100%;
  max-width: 1100px;
  max-height: 440px;
  object-fit: cover;
  border-radius: 18px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.08);
  transition: transform 0.4s cubic-bezier(.25,.8,.25,1), box-shadow 0.3s;
  will-change: transform;
}

.img-hover-effect:hover {
  transform: perspective(600px) rotateY(10deg) scale(1.06);
  box-shadow: 0 8px 24px rgba(0,0,0,0.18);
  z-index: 2;
}

.core-features-section-inline {
  flex: 1;
}

.carousel-pagination {
  display: flex;
  gap: 18px;
  margin-top: 24px;
  justify-content: center;
}

.carousel-dot {
  min-width: 38px;
  min-height: 38px;
  border-radius: 16px;
  border: 2px solid #2563eb;
  background: #fff;
  color: #2563eb;
  font-size: 1.1rem;
  font-weight: 500;
  cursor: pointer;
  outline: none;
  transition: background 0.2s, color 0.2s, border 0.2s;
  position: relative;
  padding: 0 10px;
}

.carousel-dot.active {
  background: #2563eb;
  color: #fff;
}

.carousel-dot::after {
  content: '';
  display: block;
  width: 70%;
  height: 3px;
  background: #2563eb;
  border-radius: 2px;
  position: absolute;
  left: 15%;
  bottom: 6px;
  opacity: 0.5;
}

.carousel-dot.active::after {
  background: #fff;
  opacity: 1;
}

.core-feature-title-wrapper {
  width: 100%;
  margin-top: 48px;
}

.start-survey-section {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  margin: 100px 0 0 0;
  padding-bottom: 100px;
}

.start-survey-title {
  font-size: 2rem;
  color: #333;
  margin-bottom: 32px;
  font-weight: 600;
  text-align: center;
}

.start-survey-btn {
  font-size: 1.4rem;
  padding: 1.2rem 3.5rem;
  background: linear-gradient(135deg, #2563eb, #60a5fa);
  color: #fff;
  border: none;
  border-radius: 32px;
  font-weight: bold;
  box-shadow: 0 4px 18px rgba(37,99,235,0.10);
  cursor: pointer;
  transition: background 0.2s, box-shadow 0.2s, transform 0.2s;
}

.start-survey-btn:hover {
  background: linear-gradient(135deg, #60a5fa, #2563eb);
  box-shadow: 0 8px 32px rgba(37,99,235,0.18);
  transform: translateY(-2px) scale(1.04);
}
</style>