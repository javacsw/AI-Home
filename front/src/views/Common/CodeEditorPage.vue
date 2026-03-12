<template>
  <div class="code-editor-page">
    <div class="page-header">
      <h1>在线代码编辑器</h1>
      <p>支持多种编程语言的代码编辑和运行</p>
    </div>
    
    <div class="editor-wrapper">
      <CodeEditor />
    </div>
    
    <div class="features">
      <h2>功能特性</h2>
      <div class="feature-grid">
        <div class="feature-card">
          <h3>多语言支持</h3>
          <p>支持 JavaScript、TypeScript、Python、Java、Vue、HTML、CSS、JSON 等多种编程语言</p>
        </div>
        <div class="feature-card">
          <h3>代码格式化</h3>
          <p>一键格式化代码，支持 JSON 和 JavaScript 的自动格式化</p>
        </div>
        <div class="feature-card">
          <h3>实时运行</h3>
          <p>支持 JavaScript 代码的实时运行和结果展示</p>
        </div>
        <div class="feature-card">
          <h3>语法高亮</h3>
          <p>提供基本的语法高亮和代码编辑体验</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import CodeEditor from '@/components/CodeEditor.vue'
</script>

<style scoped>
.code-editor-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.page-header {
  text-align: center;
  color: white;
  margin-bottom: 30px;
}

.page-header h1 {
  font-size: 2.5rem;
  margin-bottom: 10px;
  text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
}

.page-header p {
  font-size: 1.2rem;
  opacity: 0.9;
}

.editor-wrapper {
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.2);
  margin-bottom: 40px;
  overflow: hidden;
}

.features {
  background: white;
  border-radius: 12px;
  padding: 30px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.2);
}

.features h2 {
  text-align: center;
  color: #333;
  margin-bottom: 30px;
  font-size: 2rem;
}

.feature-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
}

.feature-card {
  background: #f8f9fa;
  padding: 20px;
  border-radius: 8px;
  border-left: 4px solid #007acc;
  transition: transform 0.2s ease;
}

.feature-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.feature-card h3 {
  color: #007acc;
  margin-bottom: 10px;
  font-size: 1.2rem;
}

.feature-card p {
  color: #666;
  line-height: 1.6;
  margin: 0;
}

@media (max-width: 768px) {
  .code-editor-page {
    padding: 10px;
  }
  
  .page-header h1 {
    font-size: 2rem;
  }
  
  .page-header p {
    font-size: 1rem;
  }
  
  .features {
    padding: 20px;
  }
  
  .feature-grid {
    grid-template-columns: 1fr;
  }
}
</style>
