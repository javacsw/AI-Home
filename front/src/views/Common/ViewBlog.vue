<script setup>
import {onBeforeMount, onMounted, reactive, ref} from 'vue';
import { MdPreview, MdCatalog } from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
import {useThemeStore} from "@/stores/theme.js";
import {useRoute, useRouter} from "vue-router";
import {get} from "@/net/index.js";
import {message} from "ant-design-vue";
const [messageApi, contextHolder] = message.useMessage();

const route = useRoute();
const router = useRouter();
const pid = route.params.id;
const themeStore = useThemeStore();
const id = 'preview-only';

// 添加滚动元素的引用
const scrollRef = ref(null);

const options = reactive({
  data: {
    blog: {
      content: '加载中...',
      title: '加载中...',
      isPublic: 0
    },
    username: ''
  },
  loading: true
});

const fetchBlogContent = () => {
  options.loading = true;
  get('api/blog/getBlog',
    { id: pid },
    (message, data) => {
      options.data = data;
      options.loading = false;
    })
};

onBeforeMount(() => {
  fetchBlogContent();
});

// 在组件挂载后设置滚动元素引用
onMounted(() => {
  scrollRef.value = document.documentElement;
});

// 返回列表
const goBack = () => {
  router.push('/notes');
};

// 下载博客内容为 Markdown 文件
const downloadBlog = () => {
  const title = options.data.blog.title || '未命名文章';
  const content = options.data.blog.content || '';
  const fileContent = `# ${title}\n\n${content}`;
  const blob = new Blob([fileContent], { type: 'text/markdown;charset=utf-8' });
  const link = document.createElement('a');
  link.href = URL.createObjectURL(blob);
  link.download = `${title}.md`;
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
  URL.revokeObjectURL(link.href);
};
</script>

<template>
  <contextHolder />
  <div class="blog-view-container">
    <!-- 返回按钮 -->
    <div class="back-button" @click="goBack">
      <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <path d="M19 12H5M12 19l-7-7 7-7" />
      </svg>
      <span>返回列表</span>
    </div>

    <!-- 博客内容区域 -->
    <div class="blog-content-container">
      <!-- 左侧导航区域 -->
      <div class="side-menu">
        <div class="sticky-wrapper">
          <div class="toc-container" v-if="!options.loading && options.data.blog.content">
            <h3 class="toc-title">目录</h3>
            <MdCatalog
                :editorId="id"
                theme="light"
            />
          </div>

          <!-- 文章信息 -->
          <div class="blog-info-card">
            <h4>文章信息</h4>
            <div class="info-item">
              <span class="info-label">状态</span>
              <span class="info-value" :class="options.data.blog.isPublic === 0 ? 'status-public' : 'status-private'">
                {{ options.data.blog.isPublic === 0 ? '公开' : '私密' }}
              </span>
            </div>
            <div class="info-item">
              <span class="info-label">作者</span>
              <span class="info-value">{{ options.data.username}}</span>
            </div>
          </div>
        </div>
      </div>

      <!-- 中间主内容区域 -->
      <div class="main-content">
        <!-- 加载中状态 -->
        <div v-if="options.loading" class="loading-container">
          <div class="loading-spinner"></div>
          <span>正在加载文章内容...</span>
        </div>

        <!-- 正文内容 -->
        <div v-else class="blog-content">
          <div class="blog-header">
            <h1 class="blog-title">{{ options.data.blog.title }}</h1>
            <div class="blog-meta">
              <span class="blog-author">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                  <circle cx="12" cy="7" r="4"></circle>
                </svg>
                {{ options.data.username || '匿名用户' }}
              </span>
              <span class="blog-visibility">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path>
                  <circle cx="12" cy="12" r="3"></circle>
                </svg>
                {{ options.data.blog.isPublic === 0 ? '公开分享' : '私密文章' }}
              </span>
            </div>
          </div>

          <div class="blog-body">
            <MdPreview theme="light" :editorId="id" :modelValue="options.data.blog.content" />
          </div>
        </div>
      </div>

      <!-- 右侧推荐区域 -->
      <div class="side-actions">
        <div class="sticky-wrapper">
          <div class="action-card">
            <div class="action-icon">👍</div>
            <div class="action-text">点赞支持</div>
          </div>

          <div class="action-card">
            <div class="action-icon">💬</div>
            <div class="action-text">添加评论</div>
          </div>

          <div class="action-card">
            <div class="action-icon">⭐</div>
            <div class="action-text">收藏文章</div>
          </div>

          <div class="action-card" @click="downloadBlog">
            <div class="action-icon">📱</div>
            <div class="action-text">分享文章</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 全局容器 */
.blog-view-container {
  background-color: #f4f5f7; /* 浅灰色背景 */
  min-height: 100vh;
  padding: 2rem;
  color: #333; /* 深色文字 */
  font-family: '-apple-system', 'BlinkMacSystemFont', 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu', 'Cantarell', 'Fira Sans', 'Droid Sans', 'Helvetica Neue', sans-serif;
}

/* 返回按钮 */
.back-button {
  display: inline-flex;
  align-items: center;
  padding: 0.5rem 1rem;
  background-color: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 20px;
  cursor: pointer;
  color: #555;
  margin-bottom: 2rem;
  transition: all 0.3s ease;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
}

.back-button:hover {
  background-color: #f0f0f0;
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.back-button svg {
  margin-right: 0.5rem;
  color: #555;
}

/* 主内容布局 */
.blog-content-container {
  display: grid;
  grid-template-columns: 250px minmax(500px, 800px) 200px;
  gap: 2rem;
  max-width: 1400px;
  margin: 0 auto;
}

/* 侧边菜单 */
.side-menu {
  padding-right: 1rem;
}

.sticky-wrapper {
  position: sticky;
  top: 2rem;
}

.toc-container {
  background-color: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  padding: 1.5rem;
  margin-bottom: 1.5rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.toc-title {
  font-size: 1.2rem;
  margin-bottom: 1rem;
  color: #333;
  border-bottom: 1px solid #eee;
  padding-bottom: 0.5rem;
}

.blog-info-card {
  background-color: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  padding: 1.5rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.blog-info-card h4 {
  font-size: 1.1rem;
  margin-bottom: 1rem;
  color: #333;
  border-bottom: 1px solid #eee;
  padding-bottom: 0.5rem;
}

.info-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.8rem;
  font-size: 0.9rem;
}

.info-label {
  color: #666;
}

.info-value {
  font-weight: 500;
  color: #333;
}

.status-public {
  color: #28a745; /* 绿色表示公开 */
}

.status-private {
  color: #dc3545; /* 红色表示私密 */
}

/* 主内容区域 */
.main-content {
  background-color: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  padding: 2.5rem; /* 增加内边距 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}

/* 加载状态 */
.loading-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 400px;
  color: #666;
}

.loading-spinner {
  width: 40px;
  height: 40px;
  border: 3px solid rgba(0, 0, 0, 0.1);
  border-radius: 50%;
  border-top: 3px solid #007bff; /* 蓝色加载动画 */
  animation: spin 1s linear infinite;
  margin-bottom: 1rem;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}

/* 博客头部 */
.blog-header {
  margin-bottom: 2.5rem;
  padding-bottom: 1.5rem;
  border-bottom: 1px solid #eee;
}

.blog-title {
  font-size: 2.5rem;
  margin-bottom: 1rem;
  color: #333;
  font-weight: 600;
}

.blog-meta {
  display: flex;
  gap: 1.5rem;
  color: #666;
}

.blog-author, .blog-visibility {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

/* 博客正文 */
.blog-body {
  line-height: 1.8;
  color: #333;
}

/* md-editor-v3 的浅色主题适配 */
:deep(.md-editor-preview) {
  background-color: #fff !important;
}

/* 右侧动作 */
.side-actions {
  padding-left: 1rem;
}

.action-card {
  background-color: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  padding: 1rem;
  margin-bottom: 1rem;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
}

.action-card:hover {
  background-color: #f8f9fa;
  transform: translateY(-3px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.action-icon {
  font-size: 1.5rem;
  margin-bottom: 0.5rem;
  color: #007bff;
}

.action-text {
  font-size: 0.8rem;
  color: #555;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .blog-content-container {
    grid-template-columns: 200px 1fr;
  }
  
  .side-actions {
    display: none;
  }
}

@media (max-width: 900px) {
  .blog-content-container {
    grid-template-columns: 1fr;
  }
  
  .side-menu {
    display: none;
  }
  
  .blog-view-container {
    padding: 1rem;
  }
  
  .main-content {
    padding: 1.5rem;
  }
  
  .blog-title {
    font-size: 1.8rem;
  }
}
</style>