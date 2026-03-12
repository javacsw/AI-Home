<script setup>
// 这里可以添加一些逻辑代码，如果有需要的话
import router from "@/router/index.js";

// 精选功能（前两个最重要的功能）
const featuredFeatures = [
  {
    title: "风格展示",
    description: "多元风格，尽在掌握！风格展示功能汇聚了丰富多样的设计风格案例，从现代简约到古典奢华，从自然清新到科技未来，应有尽有。",
    author: "AI设计团队",
    readTime: "专修专业团队",
    route: "/aifront"
  },
  {
    title: "风格设计图生成",
    description: "一键生成，灵感无限！基于强大AI算法，可根据您的需求，快速生成多种风格的设计图。无论是简约现代、复古优雅，还是个性潮流，都能轻松实现。",
    author: "AI生成团队",
    readTime: "专修专业团队",
    route: "/ai"
  }
];

// 所有功能模块
const allFeatures = [
  {
    title: "家具预算评估",
    description: "精准预算，轻松规划！帮助您快速估算家具采购成本，根据空间大小、风格偏好和材质选择，智能生成合理的预算范围。",
    author: "预算分析团队",
    readTime: "专修专业团队",
    route: "/new"
  },
  {
    title: "装修咨询",
    description: "专业解答，省心装修！为您提供一站式装修咨询服务，涵盖设计、材料、施工等全方位指导。",
    author: "装修顾问团队",
    readTime: "专修专业团队",
    route: "/aichat"
  },
  {
    title: "用户需求分析",
    description: "深度洞察，量身定制！通过智能问卷和AI对话分析您的家居需求，精准识别风格偏好、功能需求和预算范围。",
    author: "需求分析团队",
    readTime: "专修专业团队",
    route: "/userneed"
  },
  {
    title: "装修户型合理性分析",
    description: "科学规划，空间优化！上传户型图即可获得专业级布局分析，AI将评估动线设计、采光通风、功能分区等核心要素。",
    author: "户型分析团队",
    readTime: "专修专业团队",
    route: "/analyse"
  },
  {
    title: "环境需求分析",
    description: "通过AI智能分析用户的空间环境数据，结合地域气候与居住习惯，自动生成适配的装修方案。",
    author: "环境分析团队",
    readTime: "专修专业团队",
    route: "/environment"
  },
  {
    title: "智能预算沙盘",
    description: "科学规划，空间优化！上传户型图即可获得专业级布局分析，AI将评估动线设计、采光通风、功能分区等核心要素。",
    author: "沙盘分析团队",
    readTime: "专修专业团队",
    route: "/sandboxie"
  }
];

const navigateTo = (route) => {
  router.push(route);
};
</script>

<template>
  <div class="blog-layout">
    <!-- 顶部标题区域 -->
    <div class="header-section">
      <div class="main-title">智绘家园</div>
      <div class="subtitle">来自AI团队的智能装修解决方案</div>
    </div>

    <!-- 精选功能区域 -->
    <div class="featured-section">
      <h2 class="section-heading">精选</h2>
      <div class="featured-cards">
        <div 
          v-for="feature in featuredFeatures" 
          :key="feature.title"
          class="featured-card"
          @click="navigateTo(feature.route)"
        >
          <h3 class="card-title">{{ feature.title }}</h3>
          <p class="card-description">{{ feature.description }}</p>
          <div class="card-meta">
            <div class="avatar"></div>
            <span class="author">By {{ feature.author }}</span>
            <span class="read-time">{{ feature.readTime }}</span>
          </div>
        </div>
      </div>
    </div>

    <!-- 所有功能区域 -->
    <div class="all-features-section">
      <h2 class="section-heading">所有功能</h2>
      <div class="features-grid">
        <div 
          v-for="feature in allFeatures" 
          :key="feature.title"
          class="feature-card"
          @click="navigateTo(feature.route)"
        >
          <h3 class="card-title">{{ feature.title }}</h3>
          <p class="card-description">{{ feature.description }}</p>
          <div class="card-meta">
            <div class="avatar"></div>
            <span class="author">By {{ feature.author }}</span>
            <span class="read-time">{{ feature.readTime }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.blog-layout {
  background: #ffffff;
  min-height: 100vh;
  padding: 40px 40px;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

.header-section {
  max-width: 1400px;
  margin: 0 auto 60px auto;
  padding: 0 20px;
}

.main-title {
  font-size: 3.5rem;
  font-weight: bold;
  color: #000000;
  margin-bottom: 8px;
  letter-spacing: -0.5px;
}

.subtitle {
  font-size: 1.25rem;
  color: #6b7280;
  font-weight: 400;
}

.section-heading {
  font-size: 1.75rem;
  font-weight: bold;
  color: #000000;
  margin-bottom: 24px;
  max-width: 1400px;
  margin-left: auto;
  margin-right: auto;
  padding: 0 20px;
}

.featured-section {
  max-width: 1400px;
  margin: 0 auto 80px auto;
  padding: 0 20px;
}

.featured-cards {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;
}

.featured-card {
  background: #ffffff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  padding: 36px;
  cursor: pointer;
  transition: all 0.2s ease;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.featured-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  border-color: #d1d5db;
}

.all-features-section {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 20px;
}

.features-grid {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 32px;
}

.feature-card {
  background: #ffffff;
  border: 1px solid #e5e7eb;
  border-radius: 12px;
  padding: 28px;
  cursor: pointer;
  transition: all 0.2s ease;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.feature-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  border-color: #d1d5db;
}

.card-title {
  font-size: 1.4rem;
  font-weight: bold;
  color: #000000;
  margin-bottom: 12px;
  line-height: 1.3;
}

.card-description {
  font-size: 1rem;
  color: #6b7280;
  line-height: 1.6;
  margin-bottom: 20px;
}

.card-meta {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 0.95rem;
}

.avatar {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.author {
  color: #374151;
  font-weight: 500;
}

.read-time {
  color: #9ca3af;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .blog-layout {
    padding: 40px 30px;
  }
  
  .header-section,
  .featured-section,
  .all-features-section {
    max-width: 1200px;
  }
}

@media (max-width: 1024px) {
  .features-grid {
    grid-template-columns: repeat(2, 1fr);
    gap: 28px;
  }
  
  .featured-cards {
    gap: 32px;
  }
}

@media (max-width: 768px) {
  .blog-layout {
    padding: 20px 20px;
  }
  
  .header-section,
  .featured-section,
  .all-features-section {
    max-width: 100%;
    padding: 0 10px;
  }
  
  .main-title {
    font-size: 3rem;
  }
  
  .subtitle {
    font-size: 1.125rem;
  }
  
  .section-heading {
    font-size: 1.5rem;
  }
  
  .featured-cards {
    grid-template-columns: 1fr;
    gap: 24px;
  }
  
  .features-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }
  
  .featured-card {
    padding: 28px;
  }
  
  .feature-card {
    padding: 24px;
  }
  
  .card-title {
    font-size: 1.3rem;
  }
  
  .card-description {
    font-size: 0.95rem;
  }
  
  .card-meta {
    font-size: 0.9rem;
  }
}

@media (max-width: 480px) {
  .blog-layout {
    padding: 20px 16px;
  }
  
  .header-section,
  .featured-section,
  .all-features-section {
    padding: 0 5px;
  }
  
  .main-title {
    font-size: 2.5rem;
  }
  
  .subtitle {
    font-size: 1.125rem;
  }
  
  .section-heading {
    font-size: 1.4rem;
  }
  
  .card-title {
    font-size: 1.25rem;
  }
  
  .card-description {
    font-size: 0.9rem;
  }
  
  .card-meta {
    font-size: 0.85rem;
  }
}
</style>