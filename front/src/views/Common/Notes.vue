<script setup>
import {onMounted, reactive, ref, computed} from 'vue';
import router from "@/router/index.js";
import {get} from "@/net/index.js";
import { CloseOutlined } from '@ant-design/icons-vue';

const options = reactive({
  blogs: []
})

const searchText = ref('');

const recommendList = ref([
  { id: 1, type: '项目', title: 'Web 开发资源-个人收藏', date: '2024-03-21' },
  { id: 2, type: '项目', title: 'Vue3创建前端项目', date: '2024-03-21' },
  { id: 3, type: '项目', title: '网页部署', date: '2024-03-21' },
  { id: 4, type: '算法', title: '最长上升子序列（优化版）', date: '2024-03-21' },
  { id: 5, type: '项目', title: '上传图片（本地）', date: '2024-03-21' },
  { id: 6, type: '项目', title: 'Spring AI（Open AI）', date: '2024-03-21' },
]);

const initData = () => {
  get('api/blog/getInroduce', {}, (message, data) => {
    options.blogs = data
  })
}

onMounted(() => {
  initData()
})

const filteredBlogs = computed(() => {
  if (!searchText.value) return options.blogs;
  return options.blogs.filter(blog =>
    blog.blog.title.includes(searchText.value) ||
    blog.blog.content.includes(searchText.value) ||
    blog.username.includes(searchText.value)
  );
});
</script>


<template>
  <div class="notes-main-container">
    <!-- 顶部搜索栏 -->
    <div class="notes-search-bar">
      <input v-model="searchText" type="text" placeholder="搜索博客..." class="notes-search-input" />
      <button class="notes-search-btn">
        <svg width="20" height="20" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" viewBox="0 0 24 24"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>
      </button>
    </div>
    <div class="notes-content-row">
      <!-- 左侧博客列表 -->
      <div class="notes-blog-list">
        <div v-for="blog in filteredBlogs" :key="blog.blog.id" class="notes-blog-card" @click="router.push('/view/'+blog.blog.id)">
          <div class="notes-blog-card-header">
            <h2 class="notes-blog-title">{{ blog.blog.title }}</h2>
            <span class="notes-blog-type">{{ blog.blog.type || '项目' }}</span>
          </div>
          <div class="notes-blog-desc">{{ blog.blog.content }}</div>
          <div class="notes-blog-footer">
            <div class="notes-blog-author">
              <img :src="blog.avator==null?'https://narcissism-66.github.io/image/1.jpg':blog.avator" class="notes-author-avatar" />
              <span class="notes-author-name">{{ blog.username }}</span>
            </div>
            <span class="notes-blog-date">{{ blog.blog.time || blog.blog.createTime || '' }}</span>
          </div>
        </div>
      </div>
      <!-- 右侧其他模块 -->
      <div class="notes-other-list">
        <button class="add-feedback-in-other-btn" @click="router.push('/addnotes')">
          <span class="add-feedback-plus">+</span> 添加反馈
        </button>
        <div class="notes-other-title">其他模块</div>
        <div class="notes-other-card">
          <div class="notes-other-label">热门标签</div>
          <div class="notes-other-tags">
            <span class="notes-tag">家装</span>
            <span class="notes-tag">设计</span>
            <span class="notes-tag">智能</span>
            <span class="notes-tag">测绘</span>
            <span class="notes-tag">AI</span>
          </div>
        </div>
        <div class="notes-other-card">
          <div class="notes-other-label">最新动态</div>
          <ul class="notes-other-news">
            <li>新功能上线，欢迎体验！</li>
            <li>支持多风格装修方案</li>
            <li>优化了博客搜索体验</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>
.notes-main-container {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 32px 0 0 0;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.notes-search-bar {
  width: 700px;
  margin: 0 auto 32px auto;
  display: flex;
  align-items: center;
  background: #fff;
  border-radius: 32px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.04);
  padding: 0 18px;
  height: 56px;
}
.notes-search-input {
  flex: 1;
  border: none;
  outline: none;
  font-size: 1.1rem;
  background: transparent;
  padding: 0 12px;
  color: #222;
}
.notes-search-btn {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0 8px;
  display: flex;
  align-items: center;
  color: #888;
}

.notes-content-row {
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 100%;
  max-width: 1400px;
  gap: 48px;
}

.notes-blog-list {
  flex: 2;
  display: flex;
  flex-direction: column;
  gap: 32px;
}

.notes-blog-card {
  background: #fff;
  border-radius: 18px;
  box-shadow: 0 2px 12px rgba(0,0,0,0.06);
  padding: 32px 28px;
  cursor: pointer;
  transition: box-shadow 0.3s, transform 0.3s;
  border: 1px solid #f0f0f0;
  display: flex;
  flex-direction: column;
  gap: 18px;
}
.notes-blog-card:hover {
  box-shadow: 0 8px 32px rgba(0,0,0,0.12);
  transform: translateY(-4px) scale(1.01);
}
.notes-blog-card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
}
.notes-blog-title {
  font-size: 1.3rem;
  font-weight: 700;
  color: #222;
  flex: 1;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.notes-blog-type {
  font-size: 1rem;
  color: #888;
  background: #f5f5f5;
  border-radius: 8px;
  padding: 2px 10px;
  margin-left: 8px;
}
.notes-blog-desc {
  color: #444;
  font-size: 1.05rem;
  line-height: 1.7;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
.notes-blog-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 8px;
}
.notes-blog-author {
  display: flex;
  align-items: center;
  gap: 8px;
}
.notes-author-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #e0e0e0;
}
.notes-author-name {
  font-size: 1rem;
  color: #666;
}
.notes-blog-date {
  font-size: 0.95rem;
  color: #aaa;
}

.notes-other-list {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 18px;
  min-width: 260px;
  max-width: 320px;
}
.notes-other-title {
  font-size: 1.2rem;
  font-weight: 700;
  color: #222;
  margin-bottom: 12px;
  text-align: left;
}
.notes-other-card {
  background: #fff;
  border-radius: 14px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  padding: 18px 16px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  border: 1px solid #f0f0f0;
}
.notes-other-label {
  font-size: 0.98rem;
  color: #2563eb;
  font-weight: 600;
  margin-bottom: 4px;
}
.notes-other-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}
.notes-tag {
  background: #f5f5f5;
  color: #2563eb;
  border-radius: 8px;
  padding: 2px 10px;
  font-size: 0.95rem;
  border: 1px solid #e0e0e0;
}
.notes-other-news {
  padding-left: 18px;
  color: #444;
  font-size: 0.98rem;
  margin: 0;
}
.add-feedback-in-other-btn {
  width: 100%;
  margin-bottom: 16px;
  background: #fff;
  color: #2563eb;
  border: 2px solid #2563eb;
  border-radius: 24px;
  font-size: 1.15rem;
  font-weight: 600;
  padding: 0.8rem 2.2rem 0.8rem 1.5rem;
  box-shadow: 0 4px 18px rgba(37,99,235,0.08);
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  transition: background 0.2s, color 0.2s, box-shadow 0.2s, border 0.2s;
}
.add-feedback-in-other-btn:hover {
  background: #2563eb;
  color: #fff;
  border-color: #2563eb;
  box-shadow: 0 8px 32px rgba(37,99,235,0.18);
}
.add-feedback-plus {
  font-size: 1.5rem;
  font-weight: bold;
  margin-right: 4px;
  line-height: 1;
}
@media (max-width: 1100px) {
  .notes-content-row {
    flex-direction: column;
    gap: 32px;
    align-items: center;
  }
  .notes-other-list {
    max-width: 100%;
    min-width: 0;
    width: 100%;
    flex-direction: row;
    flex-wrap: wrap;
    gap: 12px;
  }
}
@media (max-width: 768px) {
  .add-feedback-in-other-btn {
    right: 12px;
    bottom: 16px;
    padding: 0.7rem 1.2rem 0.7rem 1.1rem;
    font-size: 1rem;
  }
  .notes-other-list {
    width: 100%;
    min-width: 0;
    max-width: 100vw;
    flex-direction: row;
    flex-wrap: wrap;
    gap: 8px;
  }
}
</style>