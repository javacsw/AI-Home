<script setup>
import {reactive, ref} from 'vue';
import { MdEditor } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import {post} from "@/net/index.js";
import {message} from "ant-design-vue";
const [messageApi, contextHolder] = message.useMessage();

const blogForm=reactive({
  title:'',
  content:''
})

const submitHandler = () => {
  post('/api/blog/addNew', {
    title: blogForm.title,
    content: blogForm.content
  }, (message) => {
    messageApi.success(message);
  }, (message) => {
    messageApi.warning(message)
  }, (message) => {
    messageApi.error(message)
  })
}

</script>

<template>
  <contextHolder/>
  <div class="page-container">
    <div class="content-container">
      <div class="editor-header">
        <h1 class="title">发表反馈</h1>
        <div class="input-group">
          <label for="title">标题:</label>
          <input 
            id="title"
            v-model="blogForm.title" 
            class="title-input" 
            placeholder="请输入标题...">
        </div>
      </div>
      
      <div class="editor-container">
        <MdEditor 
          v-model="blogForm.content" 
          :toolbarsExclude="['github']"
          class="md-editor"/>
      </div>

      <div class="button-container">
        <button 
          @click="submitHandler" 
          class="submit-button">
          发布博客
        </button>
      </div>
    </div>
  </div>
</template>


<style scoped>
.page-container {
  min-height: 100vh;
  background: #f5f5f5;
  padding: 40px 20px;
  display: flex;
  justify-content: center;
  align-items: flex-start;
}

.content-container {
  width: 80%;
  max-width: 1200px;
  background: #fff;
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 0 20px rgba(6, 182, 212, 0.08);
  border: 1px solid #e0e0e0;
}

.editor-header {
  margin-bottom: 25px;
  border-bottom: 1px solid #e0e0e0;
  padding-bottom: 20px;
}

.title {
  font-size: 28px;
  color: #2563eb;
  margin-bottom: 20px;
  font-weight: 600;
  text-align: center;
}

.input-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 8px;
  font-size: 16px;
  color: #333;
}

.title-input {
  width: 100%;
  padding: 12px 15px;
  background: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  color: #222;
  font-size: 16px;
  transition: all 0.3s ease;
}

.title-input:focus {
  outline: none;
  border-color: #2563eb;
  box-shadow: 0 0 10px rgba(37,99,235,0.12);
}

.title-input::placeholder {
  color: #aaa;
}

.editor-container {
  margin-bottom: 25px;
}

/* 修改编辑器样式以匹配浅色主题 */
:deep(.md-editor) {
  border-color: #e0e0e0;
  background-color: #fff;
}

:deep(.md-editor-toolbar) {
  background-color: #fff;
  border-bottom-color: #e0e0e0;
}

:deep(.md-editor-toolbar-item) {
  color: #333;
}

:deep(.md-editor-toolbar-item:hover) {
  color: #2563eb;
}

:deep(.md-editor-input) {
  background-color: #fff;
  color: #222;
}

.button-container {
  display: flex;
  justify-content: center;
  margin-top: 30px;
}

.submit-button {
  display: inline-block;
  padding: 12px 40px;
  font-size: 16px;
  font-weight: 600;
  color: #fff;
  background: linear-gradient(135deg, #2563eb, #60a5fa); /* 蓝色渐变背景 */
  border: none;
  border-radius: 25px; /* 圆角 */
  box-shadow: 0 4px 12px rgba(37,99,235,0.12); /* 蓝色阴影 */
  cursor: pointer;
  transition: all 0.3s ease; /* 过渡效果 */
}

.submit-button:hover {
  background: linear-gradient(135deg, #60a5fa, #2563eb); /* 渐变反转 */
  box-shadow: 0 0 20px rgba(37,99,235,0.18); /* 蓝色光晕增强 */
  color: #fff;
  transform: translateY(-2px); /* 轻微上移 */
}

.submit-button:active {
  transform: translateY(0); /* 恢复原位 */
  box-shadow: 0 4px 12px rgba(37,99,235,0.08); /* 阴影恢复 */
}
</style>