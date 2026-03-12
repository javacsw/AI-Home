<script setup>
import {reactive, ref} from 'vue';
import { useUserStore } from '@/stores/userStore.js';
import { message } from 'ant-design-vue';
import { post, postAsForm } from "@/net/index.js";

const userStore = useUserStore();
const editing = ref(false);
const avatarFile = ref(null);
const previewUrl = ref(userStore.user?.avator || 'https://narcissism-66.github.io/image/1.jpg');
const nickname = ref(userStore.user?.name || '');
const signature = ref(userStore.user?.signature || '这个人很懒，什么都没写');
const email = ref(userStore.user?.email || '');
const phone = ref(userStore.user?.phone || '');

const handleAvatarChange = (e) => {
  const file = e.target.files[0];
  if (file) {
    // 验证文件类型
    if (!file.type.startsWith('image/')) {
      message.error('请选择图片文件');
      return;
    }
    
    // 验证文件大小 (限制为5MB)
    if (file.size > 5 * 1024 * 1024) {
      message.error('图片大小不能超过5MB');
      return;
    }
    
    const url = URL.createObjectURL(file);
    previewUrl.value = url;
    avatarFile.value = file;
  }
};

// 更新头像到数据库
const updateAvatar = () => {
  return new Promise((resolve, reject) => {
    if (!avatarFile.value) {
      // 如果没有文件，直接 resolve，不执行任何操作
      return resolve('没有需要上传的头像');
    }

    message.loading('正在上传头像...', 0);

    const formData = new FormData();
    formData.append('id', userStore.user.id);
    formData.append('avatar', avatarFile.value);

    postAsForm('/api/avatar', formData,
        (successMessage, newAvatarUrl) => {
          message.destroy();
          // 这里只更新数据，不提示成功，由 saveProfile 统一提示
          userStore.user.avator = newAvatarUrl;
          previewUrl.value = newAvatarUrl;
          avatarFile.value = null;
          resolve(successMessage);
        },
        (failureMessage) => {
          message.destroy();
          message.warning(failureMessage);
          reject(new Error(failureMessage));
        },
        (errorMessage) => {
          message.destroy();
          message.error(errorMessage);
          reject(new Error(errorMessage));
        }
    );
  });
};

// 更新所有个人资料
const saveProfile = async () => {
  try {
    let updatePromises = [];

    // 如果有新的头像文件，先上传头像
    if (avatarFile.value) {
      updatePromises.push(updateAvatar());
    }

    // 准备要更新的个人信息
    const profileData = {
      id: userStore.user.id,
      name: nickname.value,
      signature: signature.value,
      email: email.value,
      phone: phone.value
    };

    // 调用后端API更新个人信息
    updatePromises.push(new Promise((resolve, reject) => {
      post('/api/user/update', profileData,
          (message) => {
            // 更新本地存储的用户信息
            userStore.user.name = nickname.value;
            userStore.user.signature = signature.value;
            userStore.user.email = email.value;
            userStore.user.phone = phone.value;
            resolve(message);
          },
          (message) => reject(new Error(message))
      );
    }));

    // 等待所有更新完成
    await Promise.all(updatePromises);

    // 只有在所有操作成功后才退出编辑模式
    editing.value = false;
    message.success('资料已保存');
  } catch (error) {
    message.error( error.message||'保存失败，请重试');
    console.error('保存资料错误:', error);
  }
};

const startEdit = () => {
  editing.value = true;
};

const cancelEdit = () => {
  editing.value = false;
  previewUrl.value = userStore.user?.avator || '';
  nickname.value = userStore.user?.name || '';
  signature.value = userStore.user?.signature || '这个人很懒，什么都没写';
  email.value = userStore.user?.email || '';
  phone.value = userStore.user?.phone || '';
  avatarFile.value = null; // 清除选择的文件
};
</script>

<template>
  <div class="profile-container">
    <div class="profile-card">
      <div class="avatar-section">
        <img :src="previewUrl" alt="头像" class="avatar-img" />
        <label v-if="editing" class="avatar-upload-label">
          <input type="file" accept="image/*" @change="handleAvatarChange" class="avatar-upload-input" />
          <span class="upload-text">{{ avatarFile ? '已选择文件' : '更换头像' }}</span>
        </label>
        <div v-if="avatarFile" class="file-info">
          <small>{{ avatarFile.name }}</small>
        </div>
      </div>
      <div class="info-section">
        <div class="info-row">
          <span class="info-label">昵称：</span>
          <span v-if="!editing">{{ nickname }}</span>
          <input v-else v-model="nickname" class="info-input" />
        </div>
        <div class="info-row">
          <span class="info-label">个性签名：</span>
          <span v-if="!editing">{{ signature }}</span>
          <input v-else v-model="signature" class="info-input" />
        </div>
        <div class="info-row">
          <span class="info-label">邮箱：</span>
          <span v-if="!editing">{{ email }}</span>
          <input v-else v-model="email" class="info-input" />
        </div>
        <div class="info-row">
          <span class="info-label">手机号：</span>
          <span v-if="!editing">{{ phone }}</span>
          <input v-else v-model="phone" class="info-input" />
        </div>
      </div>
      <div class="action-section">
        <button v-if="!editing" class="edit-btn" @click="startEdit">编辑资料</button>
        <div v-else class="edit-actions">
          <button class="save-btn" @click="saveProfile">保存</button>
          <button class="cancel-btn" @click="cancelEdit">取消</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.profile-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa, #e0e5ec);
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 2rem 0;
}
.profile-card {
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 4px 24px rgba(0,0,0,0.08);
  padding: 2.5rem 2rem;
  min-width: 350px;
  max-width: 400px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.avatar-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 2rem;
}
.avatar-img {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
  box-shadow: 0 2px 8px #bae6fd55;
  border: 3px solid #bae6fd;
  margin-bottom: 0.5rem;
}
.avatar-upload-label {
  cursor: pointer;
  color: #0891b2;
  font-size: 0.95rem;
  margin-top: 0.5rem;
  padding: 0.5rem 1rem;
  border: 1px solid #0891b2;
  border-radius: 20px;
  transition: all 0.3s;
}
.avatar-upload-label:hover {
  background: #0891b2;
  color: white;
}
.avatar-upload-input {
  display: none;
}
.upload-text {
  display: block;
}
.file-info {
  margin-top: 0.5rem;
  color: #64748b;
  font-size: 0.8rem;
}
.info-section {
  width: 100%;
  margin-bottom: 1.5rem;
}
.info-row {
  display: flex;
  align-items: center;
  margin-bottom: 1.2rem;
}
.info-label {
  width: 80px;
  color: #64748b;
  font-weight: 500;
  font-size: 1rem;
}
.info-input {
  flex: 1;
  padding: 0.5rem 0.8rem;
  border: 1px solid #e2e8f0;
  border-radius: 6px;
  font-size: 1rem;
  color: #333;
  background: #f8fafc;
  margin-left: 0.5rem;
}
.action-section {
  display: flex;
  justify-content: center;
  gap: 1rem;
  margin-top: 1rem;
}
.edit-actions {
  display: flex;
  gap: 1rem;
}
.edit-btn, .save-btn, .cancel-btn {
  padding: 0.5rem 1.5rem;
  border-radius: 20px;
  border: none;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
}
.edit-btn {
  background: linear-gradient(135deg, #0891b2, #06b6d4);
  color: #fff;
}
.save-btn {
  background: linear-gradient(135deg, #06b6d4, #0891b2);
  color: #fff;
}
.cancel-btn {
  background: #e0e5ec;
  color: #333;
}
.edit-btn:hover, .save-btn:hover {
  box-shadow: 0 2px 8px #bae6fd55;
  transform: translateY(-2px);
}
.cancel-btn:hover {
  background: #cbd5e1;
}
</style>