<script setup>
import {onMounted, reactive} from 'vue';
import router from "@/router/index.js";
import {get, post} from "@/net/index.js";
import { CloseOutlined } from '@ant-design/icons-vue';
import {useRoute} from "vue-router";
import { message } from 'ant-design-vue';
const [messageApi, contextHolder] = message.useMessage();

const options = reactive({
  blogs: []
})
const route=useRoute()
const pid=route.params.id
const initData = () => {
  get('api/blog/getMyBlog', {}, (message, data) => {
    options.blogs = data
  })
}

const handleDelete = (blogId) => {
  if (confirm('确定要删除这条反馈吗？')) {
    post('api/blog/delete', {
      id: blogId
    }, (message) => {
      messageApi.success(message);
      initData();
    }, (message) => {
      messageApi.warning(message)
    }, (message) => {
      messageApi.error(message)
    })
  }
}

onMounted(() => {
  initData()
})
</script>


<template>
  <contextHolder/>
  <div class="feedback-container">
    <!-- 页面头部 -->
    <div class="page-header">
      <h1 class="page-title">我的反馈</h1>
      <div class="title-underline"></div>
      <p class="page-subtitle">管理您发布过的所有反馈与建议</p>
    </div>

    <!-- 反馈列表 -->
    <div class="feedback-list">
      <div v-for="blog in options.blogs" 
           :key="blog.blog.id"
           class="feedback-card" 
           @click="router.push('/view/'+blog.blog.id)">
        
        <!-- 卡片内容 -->
        <div class="card-header">
          <h2 class="card-title">{{ blog.blog.title }}</h2>
          <div class="delete-btn" @click.stop="handleDelete(blog.blog.id)">
            <close-outlined />
          </div>
        </div>
        
        <div class="card-author">
          <a-avatar size="large" :src="blog.avator" class="author-avatar"></a-avatar>
          <span class="author-name">{{ blog.username }}</span>
        </div>
        
        <div class="card-content">
          <p>{{ blog.blog.content }}</p>
        </div>
        
        <!-- 装饰元素 -->
        <div class="card-decoration"></div>
      </div>
      
      <!-- 空状态提示 -->
      <div v-if="options.blogs.length === 0" class="empty-state">
        <div class="empty-icon">📝</div>
        <p>暂无反馈记录</p>
        <p class="empty-hint">点击下方按钮添加您的第一条反馈</p>
      </div>
    </div>

    <!-- 添加反馈按钮 -->
    <div class="add-feedback-btn-container">
      <button @click="router.push('/addnotes')" class="add-feedback-btn">
        <span class="btn-icon">+</span>
        <span class="btn-text">添加反馈</span>
      </button>
    </div>
  </div>
</template>


<style scoped>
/* 全局容器 */
.feedback-container {
  background-color: #f5f5f5;
  background-image: none;
  min-height: 100vh;
  padding: 3rem 2rem;
  color: #333;
  font-family: 'Arial', sans-serif;
}

/* 页面头部 */
.page-header {
  text-align: center;
  margin-bottom: 3rem;
  position: relative;
}

.page-title {
  font-size: 2.6rem;
  font-weight: 700;
  margin-bottom: 0.5rem;
  color: #000;
  background: none;
  -webkit-background-clip: unset;
  -webkit-text-fill-color: unset;
  letter-spacing: 1px;
}

.title-underline {
  width: 80px;
  height: 3px;
  background: linear-gradient(to right, #0891b2, #06b6d4);
  margin: 0.8rem auto 1.2rem;
  border-radius: 2px;
}

.page-subtitle {
  color: #64748b;
  font-size: 1.1rem;
  font-weight: 300;
}

/* 反馈列表 */
.feedback-list {
  max-width: 800px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 2rem;
}

/* 反馈卡片 */
.feedback-card {
  background-color: #fff;
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  padding: 1.8rem;
  height: 300px;
  overflow: hidden;
  cursor: pointer;
  position: relative;
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

.feedback-card:hover {
  transform: translateY(-8px);
  background-color: #f0f9ff;
  border-color: #bae6fd;
  box-shadow: 0 10px 30px rgba(6, 182, 212, 0.10), 0 0 20px #bae6fd33;
}

.feedback-card:before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #e0f7fa 0%, transparent 100%);
  opacity: 0;
  transition: opacity 0.4s ease;
  z-index: -1;
  pointer-events: none;
}

.feedback-card:hover:before {
  opacity: 1;
}

/* 卡片内容 */
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.2rem;
}

.card-title {
  font-size: 1.4rem;
  font-weight: 600;
  color: #0891b2;
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 85%;
}

.delete-btn {
  opacity: 0;
  transition: all 0.3s ease;
  color: #b0b0b0;
  padding: 5px;
  border-radius: 50%;
}

.feedback-card:hover .delete-btn {
  opacity: 1;
}

.delete-btn:hover {
  color: #ff6b6b;
  background-color: #f8fafc;
}

.card-author {
  display: flex;
  align-items: center;
  margin-bottom: 1.2rem;
  position: relative;
  z-index: 2;
}

.author-avatar {
  border: 2px solid #bae6fd;
  box-shadow: 0 0 10px #bae6fd33;
}

.author-name {
  margin-left: 0.8rem;
  font-size: 0.95rem;
  color: #64748b;
}

.card-content {
  color: #333;
  font-size: 1rem;
  line-height: 1.6;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 5;
  -webkit-box-orient: vertical;
  position: relative;
  z-index: 2;
}

.card-decoration {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 100px;
  height: 100px;
  background: radial-gradient(circle, #bae6fd33 0%, transparent 70%);
  z-index: 1;
  opacity: 0.5;
  transition: all 0.4s ease;
}

.feedback-card:hover .card-decoration {
  width: 150px;
  height: 150px;
  opacity: 0.8;
}

/* 空状态 */
.empty-state {
  grid-column: 1 / -1;
  text-align: center;
  padding: 5rem 2rem;
  background-color: #f8fafc;
  border-radius: 12px;
  border: 1px dashed #e2e8f0;
}

.empty-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.empty-state p {
  font-size: 1.2rem;
  color: #64748b;
  margin: 0.5rem 0;
}

.empty-hint {
  font-size: 0.9rem !important;
  color: #b0b0b0 !important;
}

/* 添加按钮 */
.add-feedback-btn-container {
  position: fixed;
  bottom: 2.5rem;
  left: 50%;
  transform: translateX(-50%);
  z-index: 10;
}

.add-feedback-btn {
  background: linear-gradient(135deg, #0891b2, #06b6d4);
  color: #fff;
  font-weight: bold;
  font-size: 1rem;
  padding: 1rem 2rem;
  border: none;
  border-radius: 30px;
  cursor: pointer;
  display: flex;
  align-items: center;
  box-shadow: 0 5px 20px #bae6fd33;
  transition: all 0.3s ease;
}

.add-feedback-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 25px #bae6fd55;
}

.add-feedback-btn:active {
  transform: translateY(1px);
}

.btn-icon {
  font-size: 1.4rem;
  margin-right: 0.5rem;
  font-weight: bold;
  line-height: 1;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .feedback-container {
    padding: 2rem 1rem;
  }
  
  .page-title {
    font-size: 2rem;
  }
  
  .feedback-list {
    grid-template-columns: 1fr;
  }
  
  .feedback-card {
    height: auto;
    min-height: 250px;
  }
}
</style>