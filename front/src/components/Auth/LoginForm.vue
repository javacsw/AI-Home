<script setup>
import { post } from "@/net/index.js";
import { reactive } from "vue";
import { message } from "ant-design-vue";
import { ElMessage } from "element-plus";
import router from "@/router/index.js";

const props = defineProps(['isLoginComponent']);
const emits = defineEmits(["update:isLoginComponent"]);

const goAnotherForm = () => {
  emits("update:isLoginComponent", false);
};

const [messageApi, contextHolder] = message.useMessage();

const loginForm = reactive({
  username: '',
  password: ''
});

const loginHandler = () => {
  post('api/auth/login', {
    username: loginForm.username,
    password: loginForm.password
  }, (message, data) => {
    messageApi.success(message + '3s后为您转到首页');
    setTimeout(() => {
      localStorage.setItem("authToken",data);
      router.push('/');
    }, 3000);
  }, (message, data) => {
    messageApi.warning(message);
  }, (message, data) => {
    messageApi.error("发生错误,请联系管理员1");
  });
};
</script>

<template>
  <contextHolder />
  <div class="loginbg">
    <div class="login-container">
      <div class="image-section"></div>
      <div class="form-section">
        <div class="form-content">
          <h1>登入</h1>
          <br><br>
          <input type="text" class="form-input" id="username" v-model="loginForm.username" placeholder="USER NAME">
          <input type="password" id="password" v-model="loginForm.password" class="form-input" placeholder="PASSWORD">
          <div class="link-container">
            <div @click="goAnotherForm" class="text-blue-400 hover:text-blue-500 hover:underline cursor-pointer">注册账号</div>
            <div>忘记密码？</div>
          </div>
          <button @click="loginHandler()" class="login-button">Login</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.loginbg {
  background-image: linear-gradient(to left, #90ff88, #3cebdc);
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-container {
  display: flex;
  width: 80%;
  max-width: 1100px;
  height: 60vh;
  min-height: 400px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.8);
  background-color: white;
  border-radius: 10px;
  overflow: hidden;
}

.image-section {
  flex: 2;
  background-image: url('@/assets/images/登入界面.png');
  background-size: cover;
  background-position: center;
}

.form-section {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: white;
}

.form-content {
  width: 80%;
  max-width: 250px;
  text-align: center;
}

.form-content h1 {
  font-size: 2rem;
  font-weight: 900;
}

.form-input {
  width: 100%;
  margin: 20px 0;
  padding: 10px;
  border: 0;
  border-bottom: 3px solid rgb(80, 146, 170);
  font-size: 1rem;
  font-weight: 900;
  outline: none;
}

.link-container {
  display: flex;
  justify-content: space-between;
  margin: 10px 0;
}

.login-button {
  width: 100%;
  padding: 15px;
  margin-top: 20px;
  font-size: 1.2rem;
  font-weight: 900;
  border-radius: 30px;
  background-image: linear-gradient(to left, #aaff88, #3cadeb);
  border: none;
  cursor: pointer;
  color: white;
}

@media (max-width: 768px) {
  .login-container {
    flex-direction: column;
    height: auto;
  }

  .image-section {
    height: 200px;
  }

  .form-section {
    padding: 20px;
  }
}
</style>