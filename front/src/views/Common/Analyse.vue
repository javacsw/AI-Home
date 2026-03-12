<script setup>
import { ref, reactive, nextTick, onUnmounted } from 'vue';
import { message, Upload } from 'ant-design-vue';
import { InboxOutlined, SendOutlined, PictureOutlined, LoadingOutlined } from '@ant-design/icons-vue';
import {post} from "@/net/index.js";
import { useUserStore } from '@/stores/userStore.js';

const messageApi = message;
const userStore = useUserStore(); // 引入用户存储

// 消息列表
const messageList = ref([
  {
    id: 1,
    type: 'ai',
    content: '你好，我是AI助手，请上传图片或输入文字进行对话。',
    time: new Date().toLocaleTimeString()
  }
]);

// 存储所有创建的对象URL，以便在组件卸载时释放
const objectUrls = ref([]);

// 组件卸载时释放所有创建的对象URL
onUnmounted(() => {
  objectUrls.value.forEach(url => {
    URL.revokeObjectURL(url);
  });
});

// 用户输入
const userInput = ref('');

// 上传状态
const uploading = ref(false);

// 消息容器引用
const chatContainer = ref(null);

// 滚动到底部
const scrollToBottom = () => {
  nextTick(() => {
    if (chatContainer.value) {
      chatContainer.value.scrollTop = chatContainer.value.scrollHeight;
    }
  });
};

// 发送文本消息
const sendMessage = () => {
  if (!userInput.value.trim()) return;
  
  // 添加用户消息
  messageList.value.push({
    id: Date.now(),
    type: 'user',
    content: userInput.value,
    time: new Date().toLocaleTimeString()
  });
  
  // 清空输入框
  const userContent = userInput.value;
  userInput.value = '';
  
  // 滚动到底部
  scrollToBottom();
  
  // 模拟AI回复
  setTimeout(() => {
    messageList.value.push({
      id: Date.now(),
      type: 'ai',
      content: `我收到了你的消息: "${userContent}"，这里是AI回复内容。在实际应用中，这里将由后端API提供的数据替代。`,
      time: new Date().toLocaleTimeString()
    });
    
    // 滚动到底部
    scrollToBottom();
  }, 1000);
};

// 上传图片前的处理
const beforeUpload = (file) => {
  // 检查文件类型
  const isImage = /image\/\w+/.test(file.type);
  if (!isImage) {
    messageApi.error('只能上传图片文件!');
    return false;
  }
  
  // 检查文件大小，限制为10MB
  const isLt10M = file.size / 1024 / 1024 < 10;
  if (!isLt10M) {
    messageApi.error('图片必须小于10MB!');
    return false;
  }
  
  // 手动处理上传
  handleUpload(file);
  return false; // 阻止自动上传
};

// 处理文件上传
const handleUpload = (file) => {
  uploading.value = true;
  
  // 创建预览URL用于显示本地图片
  const imageUrl = URL.createObjectURL(file);
  // 存储创建的URL以便后续释放
  objectUrls.value.push(imageUrl);
  
  // 添加用户上传图片消息
  const userImageMessage = {
    id: Date.now(),
    type: 'user',
    content: imageUrl,
    contentType: 'image',
    time: new Date().toLocaleTimeString(),
    fileName: file.name
  };
  
  // 将用户图片消息添加到列表
  messageList.value.push(userImageMessage);
  
  // 滚动到底部
  scrollToBottom();
  
  // 创建FormData对象，添加后端所需参数
  const formData = new FormData();
  formData.append('file', file);
  formData.append('message', userInput.value || '请分析这张图片');
  
  // 显示等待消息
  const waitingMsgId = Date.now();
  messageList.value.push({
    id: waitingMsgId,
    type: 'ai',
    content: '正在分析图片，请稍候...',
    time: new Date().toLocaleTimeString()
  });
  scrollToBottom();
  
  // 调用后端API发送图片数据
  post('/ai/analyse', formData, 
    (message, data) => {
      // 成功回调
      uploading.value = false;
      
      // 移除等待消息
      const waitingIndex = messageList.value.findIndex(msg => msg.id === waitingMsgId);
      if (waitingIndex !== -1) {
        messageList.value.splice(waitingIndex, 1);
      }
      
      // 添加AI分析结果
      messageList.value.push({
        id: Date.now(),
        type: 'ai',
        content: data,
        time: new Date().toLocaleTimeString()
      });
      
      // 滚动到底部
      scrollToBottom();
    }, 
    (errorMsg) => {
      // 警告回调
      uploading.value = false;
      
      // 移除等待消息
      const waitingIndex = messageList.value.findIndex(msg => msg.id === waitingMsgId);
      if (waitingIndex !== -1) {
        messageList.value.splice(waitingIndex, 1);
      }
      
      messageApi.warning(errorMsg);
      messageList.value.push({
        id: Date.now(),
        type: 'ai',
        content: `分析过程出现警告: ${errorMsg}`,
        time: new Date().toLocaleTimeString()
      });
      scrollToBottom();
    }, 
    (errorMsg) => {
      // 错误回调
      uploading.value = false;
      
      // 移除等待消息
      const waitingIndex = messageList.value.findIndex(msg => msg.id === waitingMsgId);
      if (waitingIndex !== -1) {
        messageList.value.splice(waitingIndex, 1);
      }
      
      messageApi.error(errorMsg);
      messageList.value.push({
        id: Date.now(),
        type: 'ai',
        content: `分析失败: ${errorMsg}`,
        time: new Date().toLocaleTimeString()
      });
      scrollToBottom();
    },
    true  // 表示要发送FormData
  );
};
</script>

<template>
  <div class="analyse-container">
    <div class="chat-header">
      <h1>AI图像分析对话</h1>
      <p>上传图片或输入文字，与AI助手进行交流</p>
    </div>
    
    <!-- 聊天消息区域 -->
    <div class="chat-messages" ref="chatContainer">
      <div
        v-for="msg in messageList"
        :key="msg.id"
        :class="['message-item', msg.type === 'user' ? 'user-message' : 'ai-message']"
      >
        <div class="message-avatar">
          <!-- 用户头像 -->
          <div v-if="msg.type === 'user'" class="avatar-img user">
            <template v-if="userStore.user && userStore.user.avator">
              <img :src="userStore.user.avator" alt="用户头像" class="full-avatar" />
            </template>
            <template v-else>
              用户
            </template>
          </div>
          <!-- AI头像 -->
          <div v-else class="avatar-img ai">
            AI
          </div>
        </div>
        <div class="message-content">
          <!-- 文本内容 -->
          <template v-if="!msg.contentType || msg.contentType === 'text'">
            <div class="text-content">{{ msg.content }}</div>
          </template>
          
          <!-- 图片内容 -->
          <template v-else-if="msg.contentType === 'image'">
            <div class="image-wrapper">
              <img :src="msg.content" :alt="msg.fileName || '上传图片'" class="uploaded-image">
              <div class="image-caption">{{ msg.fileName }}</div>
            </div>
          </template>
          
          <div class="message-time">{{ msg.time }}</div>
        </div>
      </div>
    </div>
    
    <!-- 输入区域 -->
    <div class="chat-input-area">
      <a-upload
        :show-upload-list="false"
        :before-upload="beforeUpload"
        class="upload-button"
      >
        <button class="image-button" :disabled="uploading">
          <loading-outlined v-if="uploading" spin />
          <picture-outlined v-else />
        </button>
      </a-upload>
      
      <input
        type="text"
        v-model="userInput"
        placeholder="输入消息..."
        class="chat-input"
        @keyup.enter="sendMessage"
        :disabled="uploading"
      />
      
      <button class="send-button" @click="sendMessage" :disabled="!userInput.trim() || uploading">
        <send-outlined />
      </button>
    </div>
    
    <!-- 上传区域 -->
    <div class="upload-area">
      <a-upload-dragger
        :show-upload-list="false"
        :before-upload="beforeUpload"
        :disabled="uploading"
      >
        <p class="ant-upload-drag-icon">
          <inbox-outlined />
        </p>
        <p class="ant-upload-text">点击或拖拽图片到此区域上传</p>
        <p class="ant-upload-hint">
          支持单个或批量上传，仅支持图片文件
        </p>
      </a-upload-dragger>
    </div>
  </div>
</template>

<style scoped>
.analyse-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #000;
  color: #fff;
  padding: 20px;
  box-sizing: border-box;
  background-image: radial-gradient(circle at 10% 20%, rgba(0, 255, 255, 0.03) 0%, transparent 20%),
                    radial-gradient(circle at 90% 80%, rgba(65, 105, 225, 0.03) 0%, transparent 20%);
}

.chat-header {
  text-align: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid rgba(0, 255, 255, 0.2);
}

.chat-header h1 {
  margin: 0 0 10px;
  background: linear-gradient(to right, #00FFFF, #4169E1);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  font-size: 2rem;
}

.chat-header p {
  color: rgba(255, 255, 255, 0.7);
  margin: 0;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 10px;
  margin-bottom: 20px;
  border-radius: 12px;
  background-color: rgba(0, 0, 0, 0.5);
  border: 1px solid rgba(0, 255, 255, 0.1);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.5);
}

.message-item {
  display: flex;
  margin-bottom: 20px;
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

.user-message {
  flex-direction: row-reverse;
}

.message-avatar {
  flex-shrink: 0;
  margin: 0 10px;
}

.avatar-img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  font-size: 14px;
  overflow: hidden;
}

.full-avatar {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.avatar-img.user {
  background: linear-gradient(135deg, #00FFFF, #4169E1);
  color: #000;
}

.avatar-img.ai {
  background: linear-gradient(135deg, #4169E1, #8A2BE2);
  color: #fff;
}

.message-content {
  max-width: 70%;
  border-radius: 12px;
  padding: 12px 15px;
  position: relative;
}

.user-message .message-content {
  background-color: rgba(0, 255, 255, 0.1);
  border: 1px solid rgba(0, 255, 255, 0.2);
  border-top-right-radius: 0;
}

.ai-message .message-content {
  background-color: rgba(65, 105, 225, 0.1);
  border: 1px solid rgba(65, 105, 225, 0.2);
  border-top-left-radius: 0;
}

.text-content {
  word-break: break-word;
  line-height: 1.5;
}

.image-wrapper {
  margin: 5px 0;
}

.uploaded-image {
  max-width: 100%;
  max-height: 300px;
  border-radius: 8px;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.image-caption {
  margin-top: 5px;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.6);
  text-align: center;
}

.message-time {
  font-size: 11px;
  color: rgba(255, 255, 255, 0.5);
  margin-top: 5px;
  text-align: right;
}

.chat-input-area {
  display: flex;
  padding: 10px;
  border-radius: 12px;
  background-color: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(0, 255, 255, 0.1);
  margin-bottom: 20px;
}

.image-button, .send-button {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  cursor: pointer;
  transition: all 0.3s;
}

.image-button {
  background-color: rgba(0, 255, 255, 0.1);
  color: #00FFFF;
  margin-right: 10px;
}

.image-button:hover:not(:disabled) {
  background-color: rgba(0, 255, 255, 0.2);
}

.send-button {
  background-color: rgba(65, 105, 225, 0.2);
  color: #4169E1;
  margin-left: 10px;
}

.send-button:hover:not(:disabled) {
  background-color: rgba(65, 105, 225, 0.3);
}

.image-button:disabled, .send-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.chat-input {
  flex: 1;
  padding: 10px 15px;
  border: none;
  border-radius: 20px;
  background-color: rgba(255, 255, 255, 0.05);
  color: #fff;
  font-size: 16px;
}

.chat-input:focus {
  outline: none;
  box-shadow: 0 0 0 2px rgba(0, 255, 255, 0.2);
}

.chat-input::placeholder {
  color: rgba(255, 255, 255, 0.5);
}

.upload-area {
  margin-bottom: 20px;
}

:deep(.ant-upload-drag) {
  background-color: rgba(0, 0, 0, 0.3) !important;
  border: 2px dashed rgba(0, 255, 255, 0.2) !important;
  border-radius: 12px;
  transition: all 0.3s;
}

:deep(.ant-upload-drag:hover) {
  border-color: rgba(0, 255, 255, 0.5) !important;
}

:deep(.ant-upload-drag p.ant-upload-drag-icon .anticon) {
  color: #00FFFF !important;
}

:deep(.ant-upload-text) {
  color: rgba(255, 255, 255, 0.9) !important;
}

:deep(.ant-upload-hint) {
  color: rgba(255, 255, 255, 0.6) !important;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .analyse-container {
    padding: 10px;
  }
  
  .chat-header h1 {
    font-size: 1.5rem;
  }
  
  .message-content {
    max-width: 80%;
  }
  
  .uploaded-image {
    max-height: 200px;
  }
}
</style>