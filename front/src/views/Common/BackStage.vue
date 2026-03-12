<script setup>
import {ref, onMounted, computed, reactive} from "vue";
import router from "@/router/index.js";
import * as echarts from 'echarts';
import{get} from "@/net/index.js";
import {message} from "ant-design-vue";


// 导航菜单
const Types = ref(['首页', '用户信息', '访问记录']);
const selected = ref('首页');

// 切换内容
const switchtype = (type) => {
  selected.value = type;
};

const options = reactive({
  back: []
})
const option = reactive({
  access: []
})


const initData=()=>{
  get('api/back/getback',{},(message,data)=>{
    options.back=data
  })
}
const initDatas=()=>{
  get('api/back/getaccess',{},(message,data)=>{
    option.access=data
  })
}

// 获取用户信息数据
const fetchAllUsers = () => {
  get('/api/user/AllUser', {}, (msg, res) => {
    if(res && res.data) {
      options.back = res.data;
    } else if(Array.isArray(res)) {
      options.back = res;
    }
  });
}

// 访问记录数据
const accessLogs = ref([
  { id: 1, username: 'admin', ip: '192.168.1.100', action: '登录系统', time: '2023-06-15 14:30:25', status: '成功' },
  { id: 2, username: 'user1', ip: '192.168.1.101', action: '查看数据', time: '2023-06-14 09:15:10', status: '成功' },
  { id: 3, username: 'user2', ip: '192.168.1.102', action: '修改设置', time: '2023-06-13 16:45:30', status: '失败' },
  { id: 4, username: 'user3', ip: '192.168.1.103', action: '导出报表', time: '2023-06-15 10:20:15', status: '成功' },
  { id: 5, username: 'user4', ip: '192.168.1.104', action: '登录系统', time: '2023-06-12 11:30:45', status: '成功' },
  { id: 6, username: 'admin', ip: '192.168.1.100', action: '添加用户', time: '2023-06-15 15:10:20', status: '成功' },
  { id: 7, username: 'user1', ip: '192.168.1.101', action: '删除数据', time: '2023-06-14 10:25:30', status: '失败' },
  { id: 8, username: 'user3', ip: '192.168.1.103', action: '查看报表', time: '2023-06-15 11:05:40', status: '成功' }
]);

// 首页统计数据
const statistics = ref({
  totalUsers: 6,
  activeUsers: 2,
  totalVisits:22,
  todayVisits: 24
});

// 首页图表数据
const visitData = ref({
  dates: ['6-10', '6-11', '6-12', '6-13', '6-14', '6-15'],
  visits: [120, 132, 101, 134, 90, 230]
});

// 用户分布数据
const userDistribution = ref({
  roles: ['管理员', 'VIP用户', '普通用户'],
  counts: [5, 120, 1125]
});

// 初始化图表
onMounted(() => {
  initData()
  initDatas()
  fetchAllUsers()

  // 访问量趋势图
  const visitChart = echarts.init(document.getElementById('visitChart'));
  visitChart.setOption({
    title: {
      text: '近7天访问量趋势',
      textStyle: {
        color: '#333'
      }
    },
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: visitData.value.dates
    },
    yAxis: {
      type: 'value'
    },
    series: [{
      data: visitData.value.visits,
      type: 'line',
      smooth: true,
      areaStyle: {
        opacity: 0.3
      }
    }]
  });

  // 用户分布图
  const userChart = echarts.init(document.getElementById('userChart'));
  userChart.setOption({
    title: {
      text: '用户角色分布',
      textStyle: {
        color: '#333'
      }
    },
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 10,
      data: userDistribution.value.roles
    },
    series: [
      {
        name: '用户分布',
        type: 'pie',
        radius: ['50%', '70%'],
        avoidLabelOverlap: false,
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '18',
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: userDistribution.value.roles.map((role, index) => ({
          value: userDistribution.value.counts[index],
          name: role
        }))
      }
    ]
  });

  // 窗口大小改变时重绘图表
  window.addEventListener('resize', () => {
    visitChart.resize();
    userChart.resize();
  });
});

// 搜索功能
const searchQuery = ref('');
const searchType = ref('all');

const filteredLogs = computed(() => {
  if (!searchQuery.value) return accessLogs.value;

  return accessLogs.value.filter(log => {
    if (searchType.value === 'all') {
      return log.username.includes(searchQuery.value) ||
          log.action.includes(searchQuery.value) ||
          log.ip.includes(searchQuery.value);
    } else if (searchType.value === 'username') {
      return log.username.includes(searchQuery.value);
    } else if (searchType.value === 'action') {
      return log.action.includes(searchQuery.value);
    } else if (searchType.value === 'ip') {
      return log.ip.includes(searchQuery.value);
    }
    return true;
  });
});

// 导出访问记录
const exportLogs = () => {
  // 这里可以实现导出功能
  alert('导出功能已触发，实际项目中应实现CSV或Excel导出');
};
</script>

<template>
  <div class="backstage-container">
    <!-- 顶部导航栏 -->
    <div class="top-bar">
      <div class="logo">
        <h1>后台管理系统</h1>
      </div>
      <div class="user-info">
        <span class="username">管理员</span>
        <button @click="router.push('/auth')" class="logout-btn">退出</button>
      </div>
    </div>

    <div class="main-content">
      <!-- 左侧菜单 -->
      <div class="sidebar">
        <div class="menu">
          <button
              v-for="type in Types"
              :key="type"
              :class="{ active: selected === type }"
              @click="switchtype(type)"
              class="menu-item"
          >
            {{ type }}
          </button>
        </div>
      </div>

      <!-- 右侧内容区 -->
      <div class="content-area">
        <!-- 首页内容 -->
        <div v-if="selected === '首页'" class="home-content">
          <div class="stats-cards">
            <div class="stat-card">
              <div class="stat-icon users-icon"></div>
              <div class="stat-info">
                <div class="stat-value">{{ statistics.totalUsers }}</div>
                <div class="stat-label">总用户数</div>
              </div>
            </div>
            <div class="stat-card">
              <div class="stat-icon active-icon"></div>
              <div class="stat-info">
                <div class="stat-value">{{ statistics.activeUsers }}</div>
                <div class="stat-label">活跃用户</div>
              </div>
            </div>
            <div class="stat-card">
              <div class="stat-icon visits-icon"></div>
              <div class="stat-info">
                <div class="stat-value">{{ statistics.totalVisits }}</div>
                <div class="stat-label">总访问量</div>
              </div>
            </div>
            <div class="stat-card">
              <div class="stat-icon today-icon"></div>
              <div class="stat-info">
                <div class="stat-value">{{ statistics.todayVisits }}</div>
                <div class="stat-label">今日访问</div>
              </div>
            </div>
          </div>

          <div class="charts-container">
            <div class="chart-card">
              <div id="visitChart" class="chart"></div>
            </div>
            <div class="chart-card">
              <div id="userChart" class="chart"></div>
            </div>
          </div>

          <div class="recent-activities">
            <h3>最近活动</h3>
            <div class="activity-list">
              <div v-for="log in accessLogs.slice(0, 5)" :key="log.id" class="activity-item">
                <div class="activity-time">{{ log.time }}</div>
                <div class="activity-user">{{ log.username }}</div>
                <div class="activity-action">{{ log.action }}</div>
                <div class="activity-status" :class="log.status === '成功' ? 'success' : 'failed'">
                  {{ log.status }}
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 用户信息内容 -->
        <div v-if="selected === '用户信息'" class="user-content">
          <div class="content-header">
            <h2>用户管理</h2>
            <button class="add-user-btn">添加用户</button>
          </div>

          <div class="user-table">
            <table>
              <thead>
              <tr>
                <th>ID</th>
                <th>用户名</th>
                <th>账号</th>
                <th>邮箱</th>
                <th>个性签名</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="user in options.back" :key="user.id">
                <td>{{ user.id }}</td>
                <td>{{ user.name }}</td>
                <td>{{ user.username }}</td>
                <td>{{ user.email }}</td>
                <td>{{ user.signature }}</td>
                <td>
                  <button class="action-btn edit">编辑</button>
                  <button class="action-btn delete">删除</button>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- 访问记录内容 -->
        <div v-if="selected === '访问记录'" class="log-content">
          <div class="content-header">
            <h2>访问记录</h2>
            <div class="search-box">
              <select v-model="searchType" class="search-type">
                <option value="all">全部</option>
                <option value="username">用户名</option>
                <option value="action">操作</option>
                <option value="ip">IP地址</option>
              </select>
              <input
                  v-model="searchQuery"
                  type="text"
                  placeholder="搜索..."
                  class="search-input"
              />
              <button class="search-btn">搜索</button>
            </div>
            <button class="export-btn" @click="exportLogs">导出</button>
          </div>

          <div class="log-table">
            <table>
              <thead>
              <tr>
                <th>ID</th>
                <th>用户名</th>
                <th>IP地址</th>
                <th>操作</th>
                <th>时间</th>
                <th>状态</th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="log in option.access" :key="log.id">
                <td>{{ log.id }}</td>
                <td>{{ log.username }}</td>
                <td>{{ log.ip }}</td>
                <td>{{ log.operation }}</td>
                <td>{{ log.time }}</td>
                <td>
                    <span :class="log.status === '成功' ? 'status-success' : 'status-failed'">
                      {{ log.status }}
                    </span>
                </td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.backstage-container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: #f5f7fa;
  font-family: 'Microsoft YaHei', sans-serif;
}

.top-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 60px;
  background: linear-gradient(90deg, #1e3c72 0%, #2a5298 100%);
  color: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.logo h1 {
  font-size: 1.5rem;
  margin: 0;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.username {
  font-weight: 500;
}

.logout-btn {
  background-color: rgba(255, 255, 255, 0.2);
  border: none;
  color: white;
  padding: 5px 15px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.logout-btn:hover {
  background-color: rgba(255, 255, 255, 0.3);
}

.main-content {
  display: flex;
  flex: 1;
  overflow: hidden;
}

.sidebar {
  width: 200px;
  background-color: #fff;
  box-shadow: 2px 0 10px rgba(0, 0, 0, 0.05);
  z-index: 10;
}

.menu {
  display: flex;
  flex-direction: column;
  padding: 20px 0;
}

.menu-item {
  padding: 15px 20px;
  text-align: left;
  background: none;
  border: none;
  font-size: 1rem;
  color: #333;
  cursor: pointer;
  transition: all 0.3s;
  position: relative;
}

.menu-item:hover {
  background-color: #f0f5ff;
  color: #1e3c72;
}

.menu-item.active {
  background-color: #e6f0ff;
  color: #1e3c72;
  font-weight: 500;
}

.menu-item.active::before {
  content: '';
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  width: 4px;
  background-color: #1e3c72;
}

.content-area {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
}

/* 首页样式 */
.stats-cards {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

.stat-card {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  display: flex;
  align-items: center;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s;
}

.stat-card:hover {
  transform: translateY(-5px);
}

.stat-icon {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-right: 15px;
  background-size: cover;
  background-position: center;
}

.users-icon {
  background-color: #e3f2fd;
  background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="%231e3c72"><path d="M16 11c1.66 0 2.99-1.34 2.99-3S17.66 5 16 5c-1.66 0-3 1.34-3 3s1.34 3 3 3zm-8 0c1.66 0 2.99-1.34 2.99-3S9.66 5 8 5C6.34 5 5 6.34 5 8s1.34 3 3 3zm0 2c-2.33 0-7 1.17-7 3.5V19h14v-2.5c0-2.33-4.67-3.5-7-3.5zm8 0c-.29 0-.62.02-.97.05 1.16.84 1.97 1.97 1.97 3.45V19h6v-2.5c0-2.33-4.67-3.5-7-3.5z"/></svg>');
}

.active-icon {
  background-color: #e8f5e9;
  background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="%234caf50"><path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z"/></svg>');
}

visits-icon {
  background-color: #fff3e0;
  background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="%23ff9800"><path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-5 14H7v-2h7v2zm3-4H7v-2h10v2zm0-4H7V7h10v2z"/></svg>');
}

today-icon {
  background-color: #fce4ec;
  background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="%23e91e63"><path d="M19 3h-1V1h-2v2H8V1H6v2H5c-1.11 0-1.99.9-1.99 2L3 19c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm0 16H5V8h14v11zM7 10h5v5H7z"/></svg>');
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 1.8rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 0.9rem;
  color: #666;
}

.charts-container {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
  margin-bottom: 20px;
}

.chart-card {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.chart {
  height: 300px;
  width: 100%;
}

.recent-activities {
  background-color: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

.recent-activities h3 {
  margin-top: 0;
  margin-bottom: 15px;
  color: #333;
}

.activity-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.activity-item {
  display: flex;
  align-items: center;
  padding: 10px;
  border-radius: 4px;
  background-color: #f9f9f9;
}

.activity-time {
  width: 150px;
  color: #666;
  font-size: 0.9rem;
}

.activity-user {
  width: 100px;
  font-weight: 500;
}

.activity-action {
  flex: 1;
}

.activity-status {
  width: 80px;
  text-align: center;
  padding: 3px 8px;
  border-radius: 4px;
  font-size: 0.8rem;
}

.activity-status.success {
  background-color: #e8f5e9;
  color: #4caf50;
}

.activity-status.failed {
  background-color: #ffebee;
  color: #f44336;
}

/* 用户信息样式 */
.content-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.content-header h2 {
  margin: 0;
  color: #333;
}

.add-user-btn {
  background-color: #1e3c72;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.add-user-btn:hover {
  background-color: #2a5298;
}

.user-table {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

table {
  width: 100%;
  border-collapse: collapse;
}

th, td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

th {
  background-color: #f5f7fa;
  font-weight: 500;
  color: #333;
}

tr:hover {
  background-color: #f9f9f9;
}

.status-online {
  color: #4caf50;
  font-weight: 500;
}

.status-offline {
  color: #9e9e9e;
}

.action-btn {
  padding: 5px 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 5px;
  font-size: 0.8rem;
}

.action-btn.edit {
  background-color: #e3f2fd;
  color: #1e3c72;
}

.action-btn.delete {
  background-color: #ffebee;
  color: #f44336;
}

/* 访问记录样式 */
.search-box {
  display: flex;
  gap: 10px;
}

.search-type {
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: white;
}

.search-input {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  width: 200px;
}

.search-btn {
  background-color: #1e3c72;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
}

.export-btn {
  background-color: #4caf50;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  margin-left: 10px;
}

.log-table {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

.status-success {
  color: #4caf50;
  font-weight: 500;
}

.status-failed {
  color: #f44336;
  font-weight: 500;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .stats-cards {
    grid-template-columns: repeat(2, 1fr);
  }

  .charts-container {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .main-content {
    flex-direction: column;
  }

  .sidebar {
    width: 100%;
    height: auto;
  }

  .menu {
    flex-direction: row;
    overflow-x: auto;
    padding: 10px;
  }

  .menu-item {
    padding: 10px 15px;
    white-space: nowrap;
  }

  .stats-cards {
    grid-template-columns: 1fr;
  }
}
</style>