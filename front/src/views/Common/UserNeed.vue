<script setup>
import { ref, onMounted } from 'vue';
import * as echarts from 'echarts';

// 装修风格数据
const styleData = ref([
  { name: '现代简约', value: 42, description: '简洁、实用、线条感强' },
  { name: '北欧风格', value: 38, description: '自然、简约、温馨' },
  { name: '新中式', value: 25, description: '传统与现代的融合' },
  { name: '美式乡村', value: 18, description: '温馨、自然、复古' },
  { name: '工业风', value: 15, description: '粗犷、原始、随性' },
  { name: '地中海风格', value: 12, description: '明亮、清爽、自然' },
  { name: '日式风格', value: 20, description: '极简、禅意、自然' },
  { name: '轻奢风格', value: 28, description: '优雅、精致、品质感' }
]);

// 性别与风格偏好关联数据
const genderStyleData = ref({
  male: [35, 25, 30, 10, 20, 8, 12, 18],
  female: [45, 42, 20, 22, 10, 15, 25, 32]
});

// 年龄与风格偏好关联数据
const ageStyleData = ref([
  { age: '20-30岁', 现代简约: 50, 北欧风格: 45, 工业风: 30, 轻奢风格: 40, 日式风格: 25 },
  { age: '30-40岁', 现代简约: 42, 北欧风格: 38, 工业风: 20, 轻奢风格: 35, 日式风格: 22 },
  { age: '40-50岁', 现代简约: 32, 北欧风格: 25, 工业风: 12, 轻奢风格: 28, 日式风格: 15 },
  { age: '50岁以上', 现代简约: 20, 北欧风格: 15, 工业风: 5, 轻奢风格: 10, 日式风格: 18 }
]);

// 装修预算与风格选择关系
const budgetStyleData = ref([
  { budget: '10万以下', 现代简约: 30, 日式风格: 25, 北欧风格: 35 },
  { budget: '10-20万', 现代简约: 40, 轻奢风格: 20, 北欧风格: 30, 新中式: 15 },
  { budget: '20-30万', 现代简约: 35, 轻奢风格: 30, 新中式: 25, 美式乡村: 15 },
  { budget: '30万以上', 轻奢风格: 45, 新中式: 30, 美式乡村: 25, 工业风: 20 }
]);

// 区域热力图数据
const regionData = ref([
  { name: '北京', value: [{ name: '现代简约', percent: 45 }, { name: '北欧风格', percent: 30 }] },
  { name: '上海', value: [{ name: '轻奢风格', percent: 40 }, { name: '现代简约', percent: 35 }] },
  { name: '广州', value: [{ name: '新中式', percent: 38 }, { name: '现代简约', percent: 30 }] },
  { name: '深圳', value: [{ name: '现代简约', percent: 42 }, { name: '工业风', percent: 28 }] },
  { name: '成都', value: [{ name: '新中式', percent: 35 }, { name: '北欧风格', percent: 25 }] }
]);

// 初始化图表
onMounted(() => {
  // 初始化风格占比饼图
  initStylePieChart();
  
  // 初始化年龄段风格雷达图
  initAgeRadarChart();
  
  // 初始化性别风格对比条形图
  initGenderBarChart();
  
  // 初始化预算风格关系折线图
  initBudgetLineChart();
  
  // 初始化年度趋势图
  initYearTrendChart();
});

// 风格占比饼图
const initStylePieChart = () => {
  const chartDom = document.getElementById('style-pie-chart');
  const myChart = echarts.init(chartDom);
  
  const option = {
    backgroundColor: 'transparent',
    title: {
      text: '装修风格偏好分布',
      left: 'center',
      textStyle: {
        color: '#fff'
      }
    },
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      textStyle: {
        color: '#fff'
      }
    },
    series: [
      {
        name: '风格偏好',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#000',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '18',
            fontWeight: 'bold',
            color: '#fff'
          }
        },
        labelLine: {
          show: false
        },
        data: styleData.value.map(item => ({ value: item.value, name: item.name }))
      }
    ]
  };
  
  option && myChart.setOption(option);
  
  // 窗口大小变化时自适应
  window.addEventListener('resize', () => {
    myChart.resize();
  });
};

// 年龄段风格雷达图
const initAgeRadarChart = () => {
  const chartDom = document.getElementById('age-radar-chart');
  const myChart = echarts.init(chartDom);
  
  const option = {
    backgroundColor: 'transparent',
    title: {
      text: '各年龄段风格偏好',
      textStyle: {
        color: '#fff'
      }
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      data: ageStyleData.value.map(item => item.age),
      textStyle: {
        color: '#fff'
      }
    },
    radar: {
      indicator: [
        { name: '现代简约', max: 60 },
        { name: '北欧风格', max: 50 },
        { name: '工业风', max: 40 },
        { name: '轻奢风格', max: 50 },
        { name: '日式风格', max: 30 }
      ],
      axisName: {
        color: '#fff'
      },
      splitArea: {
        areaStyle: {
          color: ['rgba(0, 0, 0, 0.2)']
        }
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.3)'
        }
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.3)'
        }
      }
    },
    series: ageStyleData.value.map(age => ({
      name: age.age,
      type: 'radar',
      data: [
        {
          value: [age['现代简约'], age['北欧风格'], age['工业风'], age['轻奢风格'], age['日式风格']],
          name: age.age
        }
      ]
    }))
  };
  
  option && myChart.setOption(option);
  
  window.addEventListener('resize', () => {
    myChart.resize();
  });
};

// 性别风格对比条形图
const initGenderBarChart = () => {
  const chartDom = document.getElementById('gender-bar-chart');
  const myChart = echarts.init(chartDom);
  
  const option = {
    backgroundColor: 'transparent',
    title: {
      text: '不同性别的风格偏好对比',
      textStyle: {
        color: '#fff'
      }
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      data: ['男性', '女性'],
      textStyle: {
        color: '#fff'
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'value',
      axisLabel: {
        color: '#fff'
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.3)'
        }
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.1)'
        }
      }
    },
    yAxis: {
      type: 'category',
      data: styleData.value.map(item => item.name),
      axisLabel: {
        color: '#fff'
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.3)'
        }
      }
    },
    series: [
      {
        name: '男性',
        type: 'bar',
        stack: 'total',
        label: {
          show: true
        },
        emphasis: {
          focus: 'series'
        },
        data: genderStyleData.value.male
      },
      {
        name: '女性',
        type: 'bar',
        stack: 'total',
        label: {
          show: true
        },
        emphasis: {
          focus: 'series'
        },
        data: genderStyleData.value.female
      }
    ]
  };
  
  option && myChart.setOption(option);
  
  window.addEventListener('resize', () => {
    myChart.resize();
  });
};

// 预算风格关系折线图
const initBudgetLineChart = () => {
  const chartDom = document.getElementById('budget-line-chart');
  const myChart = echarts.init(chartDom);
  
  const option = {
    backgroundColor: 'transparent',
    title: {
      text: '预算与风格选择关系',
      textStyle: {
        color: '#fff'
      }
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['现代简约', '轻奢风格', '北欧风格', '新中式', '美式乡村', '日式风格'],
      textStyle: {
        color: '#fff'
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: budgetStyleData.value.map(item => item.budget),
      axisLabel: {
        color: '#fff'
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.3)'
        }
      }
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        color: '#fff'
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.3)'
        }
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.1)'
        }
      }
    },
    series: [
      {
        name: '现代简约',
        type: 'line',
        stack: 'Total',
        data: budgetStyleData.value.map(item => item['现代简约'] || 0)
      },
      {
        name: '轻奢风格',
        type: 'line',
        stack: 'Total',
        data: budgetStyleData.value.map(item => item['轻奢风格'] || 0)
      },
      {
        name: '北欧风格',
        type: 'line',
        stack: 'Total',
        data: budgetStyleData.value.map(item => item['北欧风格'] || 0)
      },
      {
        name: '新中式',
        type: 'line',
        stack: 'Total',
        data: budgetStyleData.value.map(item => item['新中式'] || 0)
      },
      {
        name: '美式乡村',
        type: 'line',
        stack: 'Total',
        data: budgetStyleData.value.map(item => item['美式乡村'] || 0)
      },
      {
        name: '日式风格',
        type: 'line',
        stack: 'Total',
        data: budgetStyleData.value.map(item => item['日式风格'] || 0)
      }
    ]
  };
  
  option && myChart.setOption(option);
  
  window.addEventListener('resize', () => {
    myChart.resize();
  });
};

// 年度趋势图
const initYearTrendChart = () => {
  const chartDom = document.getElementById('year-trend-chart');
  const myChart = echarts.init(chartDom);
  
  const option = {
    backgroundColor: 'transparent',
    title: {
      text: '装修风格年度趋势变化',
      textStyle: {
        color: '#fff'
      }
    },
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['现代简约', '北欧风格', '新中式', '轻奢风格'],
      textStyle: {
        color: '#fff'
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: ['2018', '2019', '2020', '2021', '2022', '2023'],
      axisLabel: {
        color: '#fff'
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.3)'
        }
      }
    },
    yAxis: {
      type: 'value',
      axisLabel: {
        color: '#fff'
      },
      axisLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.3)'
        }
      },
      splitLine: {
        lineStyle: {
          color: 'rgba(255, 255, 255, 0.1)'
        }
      }
    },
    series: [
      {
        name: '现代简约',
        type: 'line',
        data: [25, 30, 35, 40, 42, 48]
      },
      {
        name: '北欧风格',
        type: 'line',
        data: [20, 28, 32, 35, 38, 42]
      },
      {
        name: '新中式',
        type: 'line',
        data: [15, 18, 20, 22, 25, 30]
      },
      {
        name: '轻奢风格',
        type: 'line',
        data: [12, 15, 18, 22, 28, 35]
      }
    ]
  };
  
  option && myChart.setOption(option);
  
  window.addEventListener('resize', () => {
    myChart.resize();
  });
};
</script>

<template>
  <div class="style-statistics-container">
    <div class="header">
      <h1>装修风格偏好统计分析</h1>
      <p>基于用户选择数据分析，了解各类装修风格的受欢迎程度</p>
    </div>
    
    <div class="statistics-wrapper">
      <!-- 顶部卡片：风格排名 -->
      <div class="top-styles-container">
        <h2>最受欢迎的装修风格</h2>
        <div class="top-styles">
          <div v-for="(style, index) in styleData.slice(0, 4)" :key="index" class="style-card">
            <div class="rank">{{ index + 1 }}</div>
            <div class="style-info">
              <h3>{{ style.name }}</h3>
              <p>{{ style.description }}</p>
              <div class="progress-bar">
                <div class="progress" :style="{ width: `${style.value * 1.6}px` }"></div>
              </div>
              <div class="percentage">{{ style.value }}%</div>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 图表区域 -->
      <div class="charts-container">
        <div class="chart-row">
          <!-- 风格占比饼图 -->
          <div class="chart-box">
            <div id="style-pie-chart" class="chart"></div>
          </div>
          
          <!-- 年龄段风格雷达图 -->
          <div class="chart-box">
            <div id="age-radar-chart" class="chart"></div>
          </div>
        </div>
        
        <div class="chart-row">
          <!-- 性别风格对比柱状图 -->
          <div class="chart-box">
            <div id="gender-bar-chart" class="chart"></div>
          </div>
          
          <!-- 预算风格关系折线图 -->
          <div class="chart-box">
            <div id="budget-line-chart" class="chart"></div>
          </div>
        </div>
        
        <div class="chart-row">
          <!-- 年度趋势图 -->
          <div class="chart-box full-width">
            <div id="year-trend-chart" class="chart"></div>
          </div>
        </div>
      </div>
      
      <!-- 底部区域：洞察与趋势 -->
      <div class="insights-container">
        <h2>风格偏好洞察</h2>
        <div class="insights-grid">
          <div class="insight-card">
            <div class="insight-icon">📈</div>
            <div class="insight-content">
              <h3>现代简约持续领先</h3>
              <p>近5年来，现代简约风格一直是用户的首选，特别受年轻人群喜爱。</p>
            </div>
          </div>
          
          <div class="insight-card">
            <div class="insight-icon">🔍</div>
            <div class="insight-content">
              <h3>轻奢风格快速增长</h3>
              <p>轻奢风格近两年增长率最高，预算30万以上用户中，有45%选择轻奢风格。</p>
            </div>
          </div>
          
          <div class="insight-card">
            <div class="insight-icon">🏙️</div>
            <div class="insight-content">
              <h3>区域差异明显</h3>
              <p>一线城市更偏爱现代简约和轻奢风格，而新中式在二三线城市更受欢迎。</p>
            </div>
          </div>
          
          <div class="insight-card">
            <div class="insight-icon">👥</div>
            <div class="insight-content">
              <h3>性别偏好差异</h3>
              <p>女性更倾向于选择北欧风格和轻奢风格，男性则更偏爱现代简约和工业风。</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 全局样式 */
.style-statistics-container {
  background-color: #f5f5f5;
  color: #333;
  min-height: 100vh;
  padding: 2rem;
  font-family: 'Arial', sans-serif;
}

.header {
  text-align: center;
  margin-bottom: 2rem;
}

.header h1 {
  font-size: 2.2rem;
  margin-bottom: 0.5rem;
  color: #000;
  background: none;
  -webkit-background-clip: unset;
  -webkit-text-fill-color: unset;
}

.header p {
  color: #64748b;
  font-size: 1rem;
}

.statistics-wrapper {
  max-width: 1400px;
  margin: 0 auto;
}

/* 顶部卡片样式 */
.top-styles-container {
  margin-bottom: 2rem;
  background-color: #fff;
  border-radius: 10px;
  padding: 1.5rem;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
}

.top-styles-container h2 {
  margin-bottom: 1.5rem;
  color: #0891b2;
}

.top-styles {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
  gap: 1rem;
}

.style-card {
  background-color: #f8fafc;
  border-radius: 8px;
  padding: 1rem;
  flex: 1;
  min-width: 220px;
  display: flex;
  align-items: flex-start;
  transition: all 0.3s ease;
  border: 1px solid #e2e8f0;
}

.style-card:hover {
  background-color: #e0f2fe;
  transform: translateY(-5px);
}

.rank {
  font-size: 2rem;
  font-weight: bold;
  color: #0891b2;
  margin-right: 1rem;
  min-width: 30px;
}

.style-info {
  flex: 1;
}

.style-info h3 {
  margin-bottom: 0.3rem;
  font-size: 1.2rem;
  color: #000;
}

.style-info p {
  font-size: 0.8rem;
  color: #64748b;
  margin-bottom: 0.8rem;
}

.progress-bar {
  height: 6px;
  background-color: #e0e8ef;
  border-radius: 3px;
  margin-bottom: 0.3rem;
}

.progress {
  height: 100%;
  background: linear-gradient(to right, #0891b2, #06b6d4);
  border-radius: 3px;
}

.percentage {
  text-align: right;
  font-size: 0.9rem;
  font-weight: bold;
  color: #0891b2;
}

/* 图表容器样式 */
.charts-container {
  margin-bottom: 2rem;
}

.chart-row {
  display: flex;
  gap: 1.5rem;
  margin-bottom: 1.5rem;
}

.chart-box {
  flex: 1;
  background-color: #fff;
  border-radius: 10px;
  padding: 1rem;
  min-height: 350px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  border: 1px solid #e2e8f0;
}

.chart {
  width: 100%;
  height: 350px;
}

.full-width {
  width: 100%;
}

/* 洞察区域样式 */
.insights-container {
  background-color: #fff;
  border-radius: 10px;
  padding: 1.5rem;
  box-shadow: 0 2px 8px rgba(0,0,0,0.04);
  border: 1px solid #e2e8f0;
}

.insights-container h2 {
  margin-bottom: 1.5rem;
  color: #0891b2;
}

.insights-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 1.5rem;
}

.insight-card {
  background-color: #f8fafc;
  border-radius: 8px;
  padding: 1.2rem;
  display: flex;
  align-items: flex-start;
  transition: all 0.3s ease;
  border: 1px solid #e2e8f0;
}

.insight-card:hover {
  background-color: #e0f2fe;
  transform: translateY(-5px);
}

.insight-icon {
  font-size: 2rem;
  margin-right: 1rem;
}

.insight-content h3 {
  font-size: 1.1rem;
  margin-bottom: 0.5rem;
  color: #000;
}

.insight-content p {
  font-size: 0.9rem;
  color: #64748b;
  line-height: 1.5;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .chart-row {
    flex-direction: column;
  }
  
  .chart-box {
    width: 100%;
  }
}

@media (max-width: 768px) {
  .style-statistics-container {
    padding: 1rem;
  }
  
  .header h1 {
    font-size: 1.8rem;
  }
  
  .top-styles {
    flex-direction: column;
  }
  
  .insights-grid {
    grid-template-columns: 1fr;
  }
}
</style>