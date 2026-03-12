<script setup>
import {ref, onMounted, watch} from 'vue';
import * as echarts from 'echarts';

// 著作基本信息表
const ancientBooks = ref([
  {
    id: 1,
    title: '灵宪',
    author: '张衡',
    dynasty: '东汉',
    year: '120',
    category: '天文学',
    brief: '天文历法著作，阐述天体运行规律'
  },
  {
    id: 2,
    title: '缀术',
    author: '祖冲之',
    dynasty: '南朝',
    year: '480',
    category: '数学',
    brief: '数学著作，包含圆周率计算等内容'
  },
  {
    id: 3,
    title: '梦溪笔谈',
    author: '沈括',
    dynasty: '北宋',
    year: '1088',
    category: '综合',
    brief: '百科全书式著作，涵盖天文、地理、物理等多个领域'
  },
  {
    id: 4,
    title: '历算全书',
    author: '梅文鼎',
    dynasty: '清代',
    year: '1700',
    category: '天文学',
    brief: '天文历法著作，系统总结古代历法'
  },
  {
    id: 5,
    title: '考工记图',
    author: '戴震',
    dynasty: '清代',
    year: '1760',
    category: '工程学',
    brief: '工程测量著作，研究古代工艺技术'
  },
  {
    id: 6,
    title: '算学启蒙',
    author: '梅文鼎',
    dynasty: '清代',
    year: '1690',
    category: '数学',
    brief: '数学教育著作，介绍基础数学知识'
  },
  {
    id: 7,
    title: '天工开物',
    author: '宋应星',
    dynasty: '明代',
    year: '1637',
    category: '工艺学',
    brief: '工艺技术著作，记录古代工艺技术'
  },
  {
    id: 8,
    title: '本草纲目',
    author: '李时珍',
    dynasty: '明代',
    year: '1578',
    category: '医学',
    brief: '药物学著作，系统总结中药知识'
  }
]);

// 学科领域覆盖度表
const subjectScores = ref([
  {book: '灵宪', astronomy: 95, mathematics: 70, physics: 60, geography: 40, medicine: 30, engineering: 50},
  {book: '缀术', astronomy: 60, mathematics: 98, physics: 50, geography: 30, medicine: 20, engineering: 40},
  {book: '梦溪笔谈', astronomy: 80, mathematics: 75, physics: 85, geography: 90, medicine: 70, engineering: 80},
  {book: '历算全书', astronomy: 90, mathematics: 85, physics: 60, geography: 50, medicine: 40, engineering: 60},
  {book: '考工记图', astronomy: 40, mathematics: 70, physics: 60, geography: 50, medicine: 30, engineering: 90},
  {book: '算学启蒙', astronomy: 50, mathematics: 95, physics: 40, geography: 30, medicine: 20, engineering: 50},
  {book: '天工开物', astronomy: 30, mathematics: 60, physics: 70, geography: 60, medicine: 40, engineering: 95},
  {book: '本草纲目', astronomy: 20, mathematics: 30, physics: 40, geography: 60, medicine: 98, engineering: 50}
]);

// 历史影响力变化表
const historicalInfluence = ref({
  years: ['东汉', '三国', '晋朝', '南北朝', '隋朝', '唐朝', '五代', '宋朝', '元朝', '明朝', '清朝', '民国', '现代'],
  books: {
    '灵宪': [0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60],
    '缀术': [0, 0, 0, 10, 20, 30, 40, 50, 60, 70, 80, 85, 90],
    '梦溪笔谈': [0, 0, 0, 0, 0, 0, 0, 20, 40, 60, 80, 85, 95],
    '历算全书': [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 50, 70],
    '考工记图': [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 40, 60],
    '算学启蒙': [0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 30, 50, 70],
    '天工开物': [0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 40, 60, 80],
    '本草纲目': [0, 0, 0, 0, 0, 0, 0, 0, 0, 30, 50, 70, 90]
  }
});

// 国际传播范围表
const internationalSpread = ref([
  {book: '灵宪', country: '日本', year: 750, influence: 70},
  {book: '灵宪', country: '朝鲜', year: 800, influence: 60},
  {book: '缀术', country: '日本', year: 850, influence: 80},
  {book: '缀术', country: '朝鲜', year: 900, influence: 75},
  {book: '梦溪笔谈', country: '日本', year: 1200, influence: 85},
  {book: '梦溪笔谈', country: '朝鲜', year: 1250, influence: 80},
  {book: '梦溪笔谈', country: '越南', year: 1300, influence: 70},
  {book: '历算全书', country: '日本', year: 1750, influence: 75},
  {book: '历算全书', country: '朝鲜', year: 1780, influence: 70},
  {book: '考工记图', country: '日本', year: 1800, influence: 65},
  {book: '算学启蒙', country: '日本', year: '1730', influence: 80},
  {book: '天工开物', country: '日本', year: '1700', influence: 85},
  {book: '天工开物', country: '朝鲜', year: '1720', influence: 75},
  {book: '天工开物', country: '欧洲', year: '1850', influence: 60},
  {book: '本草纲目', country: '日本', year: '1650', influence: 90},
  {book: '本草纲目', country: '朝鲜', year: '1680', influence: 85},
  {book: '本草纲目', country: '欧洲', year: '1750', influence: 70}
]);

// 现代研究热度表
const modernResearch = ref({
  years: ['2010', '2011', '2012', '2013', '2014', '2015', '2016', '2017', '2018', '2019', '2020', '2021', '2022'],
  papers: {
    '灵宪': [5, 6, 7, 8, 9, 10, 12, 14, 16, 18, 20, 22, 25],
    '缀术': [8, 9, 10, 12, 14, 16, 18, 20, 22, 25, 28, 30, 35],
    '梦溪笔谈': [15, 17, 20, 22, 25, 28, 30, 35, 40, 45, 50, 55, 60],
    '历算全书': [3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 18, 20],
    '考工记图': [2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 18],
    '算学启蒙': [4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 18, 20, 22],
    '天工开物': [10, 12, 14, 16, 18, 20, 22, 25, 28, 30, 35, 40, 45],
    '本草纲目': [20, 22, 25, 28, 30, 35, 40, 45, 50, 55, 60, 65, 70]
  },
  citations: {
    '灵宪': [10, 12, 14, 16, 18, 20, 22, 25, 28, 30, 35, 40, 45],
    '缀术': [15, 17, 20, 22, 25, 28, 30, 35, 40, 45, 50, 55, 60],
    '梦溪笔谈': [25, 28, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80],
    '历算全书': [5, 6, 7, 8, 9, 10, 12, 14, 16, 18, 20, 22, 25],
    '考工记图': [3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 18, 20],
    '算学启蒙': [8, 9, 10, 12, 14, 16, 18, 20, 22, 25, 28, 30, 35],
    '天工开物': [20, 22, 25, 28, 30, 35, 40, 45, 50, 55, 60, 65, 70],
    '本草纲目': [40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100]
  }
});

// 保存完整度与修复表
const preservationStatus = ref([
  {book: '灵宪', original: 60, restored: 85, digital: 90},
  {book: '缀术', original: 50, restored: 80, digital: 95},
  {book: '梦溪笔谈', original: 70, restored: 90, digital: 95},
  {book: '历算全书', original: 80, restored: 95, digital: 100},
  {book: '考工记图', original: 75, restored: 90, digital: 95},
  {book: '算学启蒙', original: 65, restored: 85, digital: 90},
  {book: '天工开物', original: 85, restored: 95, digital: 100},
  {book: '本草纲目', original: 90, restored: 98, digital: 100}
]);

// 区域文化资源表
const regionalCulture = ref([
  {region: '亳州', astronomy: 85, mathematics: 80, physics: 70, geography: 60, medicine: 50, engineering: 65},
  {region: '宣城', astronomy: 75, mathematics: 90, physics: 80, geography: 85, medicine: 70, engineering: 75},
  {region: '休宁', astronomy: 60, mathematics: 85, physics: 70, geography: 75, medicine: 80, engineering: 70},
  {region: '合肥', astronomy: 70, mathematics: 75, physics: 80, geography: 85, medicine: 90, engineering: 85},
  {region: '芜湖', astronomy: 65, mathematics: 70, physics: 75, geography: 80, medicine: 85, engineering: 90}
]);

// 当前选中的著作
const selectedBook = ref(null);

// 选择著作
const selectBook = (book) => {
  selectedBook.value = book;
};

onMounted(() => {
  // 确保DOM加载完成后再初始化图表
  setTimeout(() => {
    initCharts();
    
    // 添加窗口大小改变时的自适应
    window.addEventListener('resize', () => {
      Object.values(window.charts).forEach(chart => {
        chart && chart.resize();
      });
    });

    // 默认选中第一本著作
    if (ancientBooks.value.length > 0) {
      selectedBook.value = ancientBooks.value[0];
    }
  }, 100);
});

// 监听选中著作变化，更新相关图表
watch(selectedBook, (newVal) => {
  if (newVal) {
    updateCharts(newVal);
  }
});

const initCharts = () => {
  try {
    // 确保所有图表容器都存在
    const chartContainers = [
      'radarChart',
      'historicalChart',
      'internationalChart',
      'modernResearchChart',
      'preservationChart',
      'regionalChart'
    ];

    // 检查所有容器是否存在
    for (const containerId of chartContainers) {
      const container = document.getElementById(containerId);
      if (!container) {
        console.error(`找不到图表容器: ${containerId}`);
        return;
      }
    }

    // 初始化所有图表
    window.charts = {
      radar: echarts.init(document.getElementById('radarChart')),
      historical: echarts.init(document.getElementById('historicalChart')),
      international: echarts.init(document.getElementById('internationalChart')),
      modernResearch: echarts.init(document.getElementById('modernResearchChart')),
      preservation: echarts.init(document.getElementById('preservationChart')),
      regional: echarts.init(document.getElementById('regionalChart'))
    };

    // 设置雷达图配置
    const radarOption = {
      backgroundColor: 'transparent',
      title: {
        text: '著作学科领域覆盖度',
        textStyle: {
          color: '#fff',
          fontSize: 16
        }
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        data: ancientBooks.value.map(book => book.title),
        textStyle: {
          color: '#fff'
        },
        top: 30,
        type: 'scroll',
        pageButtonPosition: 'end'
      },
      radar: {
        indicator: [
          {name: '天文学', max: 100},
          {name: '数学', max: 100},
          {name: '物理学', max: 100},
          {name: '地理学', max: 100},
          {name: '医学', max: 100},
          {name: '工程学', max: 100}
        ],
        splitArea: {
          areaStyle: {
            color: ['rgba(255,87,34,0.05)', 'rgba(255,87,34,0.1)']
          }
        },
        axisLine: {
          lineStyle: {
            color: 'rgba(255,87,34,0.3)'
          }
        },
        splitLine: {
          lineStyle: {
            color: 'rgba(255,87,34,0.3)'
          }
        },
        name: {
          textStyle: {
            color: '#fff'
          }
        }
      },
      series: [{
        type: 'radar',
        data: subjectScores.value.map(item => ({
          value: [item.astronomy, item.mathematics, item.physics, item.geography, item.medicine, item.engineering],
          name: item.book,
          areaStyle: {
            opacity: 0.3
          },
          lineStyle: {
            width: 2
          }
        }))
      }]
    };
    window.charts.radar.setOption(radarOption);

    // 设置历史影响图配置
    const historicalOption = {
      backgroundColor: 'transparent',
      title: {
        text: '历史影响力变化',
        textStyle: {
          color: '#fff',
          fontSize: 16
        }
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
          label: {
            backgroundColor: '#6a7985'
          }
        }
      },
      legend: {
        data: ancientBooks.value.map(book => book.title),
        textStyle: {
          color: '#fff'
        },
        top: 30,
        type: 'scroll',
        pageButtonPosition: 'end'
      },
      grid: {
        top: '15%',
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: historicalInfluence.value.years,
        axisLine: {
          lineStyle: {
            color: '#fff'
          }
        },
        axisLabel: {
          color: '#fff',
          interval: 0,
          rotate: 45
        }
      },
      yAxis: {
        type: 'value',
        axisLine: {
          show: true,
          lineStyle: {
            color: '#fff'
          }
        },
        splitLine: {
          show: true,
          lineStyle: {
            color: 'rgba(255,255,255,0.1)'
          }
        },
        axisLabel: {
          color: '#fff'
        }
      },
      series: Object.keys(historicalInfluence.value.books).map(book => ({
        name: book,
        type: 'line',
        stack: 'Total',
        areaStyle: {
          opacity: 0.1
        },
        emphasis: {
          focus: 'series'
        },
        data: historicalInfluence.value.books[book],
        lineStyle: {
          width: 2
        }
      }))
    };
    window.charts.historical.setOption(historicalOption);

    // 设置国际传播图配置
    const internationalOption = {
      backgroundColor: 'transparent',
      title: {
        text: '国际传播范围',
        textStyle: {
          color: '#fff',
          fontSize: 16
        }
      },
      tooltip: {
        trigger: 'item',
        formatter: function (params) {
          return `${params.data.book}<br/>国家: ${params.data.country}<br/>年份: ${params.data.year}<br/>影响力: ${params.data.influence}`;
        }
      },
      legend: {
        data: [...new Set(internationalSpread.value.map(item => item.country))],
        textStyle: {
          color: '#fff'
        },
        top: 30
      },
      xAxis: {
        type: 'value',
        name: '年份',
        nameTextStyle: {
          color: '#fff'
        },
        axisLine: {
          lineStyle: {
            color: '#fff'
          }
        },
        splitLine: {
          show: true,
          lineStyle: {
            color: 'rgba(255,255,255,0.1)'
          }
        },
        axisLabel: {
          color: '#fff'
        }
      },
      yAxis: {
        type: 'value',
        name: '影响力',
        nameTextStyle: {
          color: '#fff'
        },
        axisLine: {
          lineStyle: {
            color: '#fff'
          }
        },
        splitLine: {
          show: true,
          lineStyle: {
            color: 'rgba(255,255,255,0.1)'
          }
        },
        axisLabel: {
          color: '#fff'
        }
      },
      series: [{
        type: 'scatter',
        data: internationalSpread.value.map(item => ({
          value: [item.year, item.influence],
          book: item.book,
          country: item.country,
          year: item.year,
          influence: item.influence,
          itemStyle: {
            color: item.country === '日本' ? '#ff5722' :
                item.country === '朝鲜' ? '#2196f3' :
                    item.country === '越南' ? '#4caf50' : '#ffc107'
          }
        })),
        symbolSize: function (data) {
          return data[2] / 5;
        }
      }]
    };
    window.charts.international.setOption(internationalOption);

    // 设置现代研究热度图配置
    const modernResearchOption = {
      backgroundColor: 'transparent',
      title: {
        text: '现代研究热度',
        textStyle: {
          color: '#fff',
          fontSize: 16
        }
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'cross',
          crossStyle: {
            color: '#999'
          }
        }
      },
      legend: {
        data: ['论文数量', '引用次数'],
        textStyle: {
          color: '#fff'
        },
        top: 30
      },
      grid: {
        top: '15%',
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: [
        {
          type: 'category',
          data: modernResearch.value.years,
          axisPointer: {
            type: 'shadow'
          },
          axisLine: {
            lineStyle: {
              color: '#fff'
            }
          },
          axisLabel: {
            color: '#fff',
            interval: 0,
            rotate: 45
          }
        }
      ],
      yAxis: [
        {
          type: 'value',
          name: '论文数量',
          nameTextStyle: {
            color: '#fff'
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: '#fff'
            }
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: 'rgba(255,255,255,0.1)'
            }
          },
          axisLabel: {
            color: '#fff'
          }
        },
        {
          type: 'value',
          name: '引用次数',
          nameTextStyle: {
            color: '#fff'
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: '#fff'
            }
          },
          splitLine: {
            show: false
          },
          axisLabel: {
            color: '#fff'
          }
        }
      ],
      series: [
        {
          name: '论文数量',
          type: 'bar',
          data: selectedBook.value ? modernResearch.value.papers[selectedBook.value.title] : [],
          itemStyle: {
            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
              {offset: 0, color: '#ff5722'},
              {offset: 1, color: 'rgba(255,87,34,0.3)'}
            ])
          }
        },
        {
          name: '引用次数',
          type: 'line',
          yAxisIndex: 1,
          data: selectedBook.value ? modernResearch.value.citations[selectedBook.value.title] : [],
          lineStyle: {
            width: 3
          },
          itemStyle: {
            color: '#2196f3'
          }
        }
      ]
    };
    window.charts.modernResearch.setOption(modernResearchOption);

    // 设置保存完整度图配置
    const preservationOption = {
      backgroundColor: 'transparent',
      title: {
        text: '保存完整度与修复情况',
        textStyle: {
          color: '#fff',
          fontSize: 16
        }
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {c}% ({d}%)'
      },
      legend: {
        orient: 'vertical',
        right: 10,
        top: 'center',
        textStyle: {
          color: '#fff'
        }
      },
      series: [
        {
          name: '保存状态',
          type: 'pie',
          radius: ['40%', '70%'],
          center: ['50%', '50%'],
          avoidLabelOverlap: false,
          itemStyle: {
            borderColor: '#0c2c5a',
            borderWidth: 2
          },
          label: {
            show: true,
            position: 'outside',
            formatter: '{b}: {c}%',
            color: '#fff'
          },
          emphasis: {
            label: {
              show: true,
              fontSize: '18',
              fontWeight: 'bold'
            }
          },
          data: selectedBook.value ? [
            {
              value: preservationStatus.value.find(item => item.book === selectedBook.value.title)?.original || 0,
              name: '原始完整度',
              itemStyle: {color: '#ff5722'}
            },
            {
              value: preservationStatus.value.find(item => item.book === selectedBook.value.title)?.restored || 0,
              name: '修复后完整度',
              itemStyle: {color: '#2196f3'}
            },
            {
              value: preservationStatus.value.find(item => item.book === selectedBook.value.title)?.digital || 0,
              name: '数字化程度',
              itemStyle: {color: '#4caf50'}
            }
          ] : []
        }
      ]
    };
    window.charts.preservation.setOption(preservationOption);

    // 设置区域文化资源图配置
    const regionalOption = {
      backgroundColor: 'transparent',
      title: {
        text: '区域文化资源分布',
        textStyle: {
          color: '#fff',
          fontSize: 16
        }
      },
      tooltip: {
        position: 'top',
        formatter: function (params) {
          return `${params.data[0]}: ${params.data[1]}<br/>领域: ${params.data[2]}<br/>强度: ${params.data[3]}`;
        }
      },
      grid: {
        top: '15%',
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'category',
        data: ['天文学', '数学', '物理学', '地理学', '医学', '工程学'],
        splitArea: {
          show: true
        },
        axisLine: {
          lineStyle: {
            color: '#fff'
          }
        },
        axisLabel: {
          color: '#fff',
          interval: 0,
          rotate: 45
        }
      },
      yAxis: {
        type: 'category',
        data: regionalCulture.value.map(item => item.region),
        splitArea: {
          show: true
        },
        axisLine: {
          lineStyle: {
            color: '#fff'
          }
        },
        axisLabel: {
          color: '#fff'
        }
      },
      visualMap: {
        min: 0,
        max: 100,
        calculable: true,
        orient: 'horizontal',
        left: 'center',
        bottom: '0%',
        textStyle: {
          color: '#fff'
        }
      },
      series: [{
        name: '文化资源强度',
        type: 'heatmap',
        data: regionalCulture.value.flatMap(region => [
          [region.region, '天文学', region.astronomy],
          [region.region, '数学', region.mathematics],
          [region.region, '物理学', region.physics],
          [region.region, '地理学', region.geography],
          [region.region, '医学', region.medicine],
          [region.region, '工程学', region.engineering]
        ]),
        label: {
          show: true,
          color: '#fff'
        },
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }]
    };
    window.charts.regional.setOption(regionalOption);

    // 添加窗口大小改变时的自适应
    window.addEventListener('resize', () => {
      Object.values(window.charts).forEach(chart => {
        chart && chart.resize();
      });
    });

  } catch (error) {
    console.error('图表初始化失败:', error);
  }
};

// 添加更新图表的函数
const updateCharts = (book) => {
  if (!window.charts) return;

  // 更新现代研究热度图
  if (window.charts.modernResearch) {
    window.charts.modernResearch.setOption({
      series: [
        {
          name: '论文数量',
          data: modernResearch.value.papers[book.title]
        },
        {
          name: '引用次数',
          data: modernResearch.value.citations[book.title]
        }
      ]
    });
  }

  // 更新保存完整度与修复图
  if (window.charts.preservation) {
    window.charts.preservation.setOption({
      series: [{
        data: [
          {
            value: preservationStatus.value.find(item => item.book === book.title)?.original || 0,
            name: '原始完整度',
            itemStyle: {color: '#ff5722'}
          },
          {
            value: preservationStatus.value.find(item => item.book === book.title)?.restored || 0,
            name: '修复后完整度',
            itemStyle: {color: '#2196f3'}
          },
          {
            value: preservationStatus.value.find(item => item.book === book.title)?.digital || 0,
            name: '数字化程度',
            itemStyle: {color: '#4caf50'}
          }
        ]
      }]
    });
  }
};
</script>

<template>
  <div class="data-screen">
    <!-- 顶部标题 -->
    <div class="header">
      <div class="title">安徽古代自然科学著作数据大屏</div>
      <div class="subtitle">Ancient Scientific Books from Anhui Province</div>
    </div>

    <!-- 主要内容区 -->
    <div class="content">
      <!-- 左侧面板 -->
      <div class="left-panel">
        <div class="panel-title">
          <span class="icon"></span>
          著作基本信息
          <span class="icon"></span>
        </div>
        <div class="book-list">
          <div
              v-for="book in ancientBooks"
              :key="book.id"
              class="book-item"
              :class="{ active: selectedBook && selectedBook.id === book.id }"
              @click="selectBook(book)"
          >
            <div class="book-title">{{ book.title }}</div>
            <div class="book-info">
              <span>{{ book.author }}</span>
              <span>{{ book.dynasty }}</span>
            </div>
            <div class="book-category">{{ book.category }}</div>
            <div class="book-brief">{{ book.brief }}</div>
          </div>
        </div>
      </div>

      <!-- 中间面板 -->
      <div class="center-panel">
        <!-- 第一行：雷达图和历史影响图 -->
        <div class="chart-row">
          <div class="chart-container">
            <div id="radarChart" class="chart"></div>
          </div>
          <div class="chart-container">
            <div id="historicalChart" class="chart"></div>
          </div>
        </div>

        <!-- 第二行：国际传播图和现代研究热度图 -->
        <div class="chart-row">
          <div class="chart-container">
            <div id="internationalChart" class="chart"></div>
          </div>
          <div class="chart-container">
            <div id="modernResearchChart" class="chart"></div>
          </div>
        </div>

        <!-- 第三行：保存完整度图和区域文化资源图 -->
        <div class="chart-row">
          <div class="chart-container">
            <div id="preservationChart" class="chart"></div>
          </div>
          <div class="chart-container">
            <div id="regionalChart" class="chart"></div>
          </div>
        </div>
      </div>

      <!-- 右侧面板 -->
      <div class="right-panel">
        <div class="panel-title">
          <span class="icon"></span>
          著作详细信息
          <span class="icon"></span>
        </div>
        <div v-if="selectedBook" class="book-detail">
          <div class="detail-header">
            <div class="detail-title">{{ selectedBook.title }}</div>
            <div class="detail-author">{{ selectedBook.author }}</div>
          </div>
          <div class="detail-info">
            <div class="info-item">
              <div class="info-label">朝代</div>
              <div class="info-value">{{ selectedBook.dynasty }}</div>
            </div>
            <div class="info-item">
              <div class="info-label">年份</div>
              <div class="info-value">{{ selectedBook.year }}</div>
            </div>
            <div class="info-item">
              <div class="info-label">类别</div>
              <div class="info-value">{{ selectedBook.category }}</div>
            </div>
          </div>
          <div class="detail-brief">
            <div class="brief-title">简介</div>
            <div class="brief-content">{{ selectedBook.brief }}</div>
          </div>
          <div class="detail-metrics">
            <div class="metrics-title">保存与修复情况</div>
            <div class="metrics-content">
              <div class="metric-item">
                <div class="metric-value">
                  {{ preservationStatus.find(item => item.book === selectedBook.title)?.original || 0 }}%
                </div>
                <div class="metric-label">原始完整度</div>
              </div>
              <div class="metric-item">
                <div class="metric-value">
                  {{ preservationStatus.find(item => item.book === selectedBook.title)?.restored || 0 }}%
                </div>
                <div class="metric-label">修复后完整度</div>
              </div>
              <div class="metric-item">
                <div class="metric-value">
                  {{ preservationStatus.find(item => item.book === selectedBook.title)?.digital || 0 }}%
                </div>
                <div class="metric-label">数字化程度</div>
              </div>
            </div>
          </div>
          <div class="detail-metrics">
            <div class="metrics-title">现代研究情况</div>
            <div class="metrics-content">
              <div class="metric-item">
                <div class="metric-value">
                  {{ modernResearch.papers[selectedBook.title]?.[modernResearch.years.length - 1] || 0 }}
                </div>
                <div class="metric-label">最新论文数</div>
              </div>
              <div class="metric-item">
                <div class="metric-value">
                  {{ modernResearch.citations[selectedBook.title]?.[modernResearch.years.length - 1] || 0 }}
                </div>
                <div class="metric-label">最新引用数</div>
              </div>
            </div>
          </div>
        </div>
        <div v-else class="no-selection">
          请选择一本著作查看详细信息
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.data-screen {
  width: 100%;
  min-height: 100vh;
  background-color: #061537;
  color: #fff;
  font-family: "Microsoft YaHei";
  display: flex;
  flex-direction: column;
}

.header {
  height: 80px;
  background: linear-gradient(90deg, rgba(255, 87, 34, 0.1) 0%, rgba(255, 87, 34, 0.2) 50%, rgba(255, 87, 34, 0.1) 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 0 20px;
  position: sticky;
  top: 0;
  z-index: 100;
}

.title {
  font-size: 28px;
  font-weight: bold;
  background: linear-gradient(180deg, #fff, #ff5722);
  -webkit-background-clip: text;
  color: transparent;
  margin-bottom: 5px;
}

.subtitle {
  font-size: 16px;
  color: rgba(255, 255, 255, 0.7);
}

.content {
  display: flex;
  flex: 1;
  padding: 20px;
  gap: 20px;
  min-height: calc(100vh - 80px);
}

.left-panel, .right-panel {
  width: 25%;
  background: rgba(255, 87, 34, 0.1);
  border: 1px solid rgba(255, 87, 34, 0.2);
  border-radius: 4px;
  padding: 15px;
  display: flex;
  flex-direction: column;
  height: fit-content;
  position: sticky;
  top: 100px;
}

.center-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
  height: fit-content;
}

.panel-title {
  font-size: 16px;
  margin-bottom: 15px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.icon {
  width: 20px;
  height: 2px;
  background-color: #ff5722;
  margin: 0 10px;
}

.book-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
  max-height: calc(100vh - 200px);
  overflow-y: auto;
  padding-right: 10px;
}

.book-list::-webkit-scrollbar {
  width: 6px;
}

.book-list::-webkit-scrollbar-thumb {
  background: rgba(255, 87, 34, 0.3);
  border-radius: 3px;
}

.book-list::-webkit-scrollbar-track {
  background: rgba(255, 87, 34, 0.1);
  border-radius: 3px;
}

.book-item {
  background: rgba(255, 87, 34, 0.1);
  border-radius: 4px;
  padding: 15px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.book-item:hover, .book-item.active {
  background: rgba(255, 87, 34, 0.2);
  transform: translateY(-2px);
}

.book-title {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 5px;
}

.book-info {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: rgba(255, 255, 255, 0.7);
  margin-bottom: 5px;
}

.book-category {
  font-size: 14px;
  color: #ff5722;
  margin-bottom: 5px;
}

.book-brief {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.9);
  line-height: 1.4;
}

.chart-row {
  display: flex;
  gap: 20px;
  min-height: 300px;
  margin-bottom: 20px;
}

.chart-container {
  flex: 1;
  background: rgba(255, 87, 34, 0.05);
  border-radius: 4px;
  padding: 15px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease;
  min-height: 300px;
  position: relative;
  display: flex;
  flex-direction: column;
}

.chart-container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 2px;
  background: linear-gradient(90deg, transparent, #ff5722, transparent);
}

.chart-container:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
  background: rgba(255, 87, 34, 0.1);
}

.chart {
  flex: 1;
  min-height: 250px;
  width: 100%;
  position: relative;
  z-index: 1;
}

.book-detail {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.detail-header {
  text-align: center;
  margin-bottom: 20px;
}

.detail-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 5px;
}

.detail-author {
  font-size: 16px;
  color: rgba(255, 255, 255, 0.7);
}

.detail-info {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 15px;
}

.info-item {
  background: rgba(255, 87, 34, 0.1);
  border-radius: 4px;
  padding: 10px;
}

.info-label {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.7);
  margin-bottom: 5px;
}

.info-value {
  font-size: 16px;
}

.detail-brief {
  background: rgba(255, 87, 34, 0.1);
  border-radius: 4px;
  padding: 15px;
}

.brief-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 10px;
}

.brief-content {
  font-size: 14px;
  line-height: 1.6;
}

.detail-metrics {
  background: rgba(255, 87, 34, 0.1);
  border-radius: 4px;
  padding: 15px;
}

.metrics-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 15px;
}

.metrics-content {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 15px;
}

.metric-item {
  text-align: center;
}

.metric-value {
  font-size: 24px;
  font-weight: bold;
  background: linear-gradient(180deg, #fff, #ff5722);
  -webkit-background-clip: text;
  color: transparent;
  margin-bottom: 5px;
}

.metric-label {
  font-size: 12px;
  color: rgba(255, 255, 255, 0.7);
}

.no-selection {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  color: rgba(255, 255, 255, 0.5);
}

@media (max-width: 1400px) {
  .content {
    flex-direction: column;
  }

  .left-panel, .right-panel {
    width: 100%;
    position: relative;
    top: 0;
  }

  .chart-row {
    flex-direction: column;
  }

  .chart-container {
    min-height: 400px;
    margin-bottom: 20px;
  }

  .book-list {
    max-height: none;
  }
}
</style>