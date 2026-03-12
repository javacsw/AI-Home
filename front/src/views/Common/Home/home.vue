<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import router from "@/router/index.js";

// 模拟数据
const styles = {
  1: {
    imageUrl: new URL('@/assets/images/简约风装修.jpg', import.meta.url).href,
    description: '现代简约风格以简洁、实用为主，注重空间的功能性和舒适性。色彩上多采用中性色调，搭配简洁的线条和几何形状，营造出干净、明快的空间感。',
    benefits: [
      '1. 空间利用率高，适合小户型。',
      '2. 简洁的设计易于清洁和维护。',
      '3. 中性色调和简约风格不易过时，具有较长的使用寿命。',
      '4. 强调自然光和开放空间，提升居住舒适度。'
    ]
  },
  2: {
    imageUrl: new URL('@/assets/images/北欧风格装修.jpg', import.meta.url).href,
    description: '北欧风格以自然、简约为主，注重功能性和舒适性。色彩上多采用白色、灰色和原木色，搭配自然材质，营造出温馨、舒适的空间感。',
    benefits: [
      '1. 自然材质环保健康。',
      '2. 简约设计易于搭配。',
      '3. 色彩柔和，适合长时间居住。',
      '4. 强调自然光和开放空间，提升居住舒适度。'
    ]
  },
  3: {
    imageUrl: new URL('@/assets/images/工业风装修.png', import.meta.url).href,
    description: '工业风格以粗犷、原始为主，注重空间的开放性和功能性。色彩上多采用黑色、灰色和金属色，搭配裸露的砖墙和管道，营造出独特的空间感。',
    benefits: [
      '1. 空间开放，适合大户型。',
      '2. 原始材质耐用且独特。',
      '3. 色彩深沉，适合个性化设计。',
      '4. 强调开放空间和功能性，提升居住舒适度。'
    ]
  },
  4: {
    imageUrl: new URL('@/assets/images/中式风格客厅.jpg', import.meta.url).href,
    description: '中式风格以传统中国文化为灵感，注重对称和平衡。色彩上多采用深木色、红色和金色，搭配古典家具和装饰，营造出庄重、典雅的氛围。',
    benefits: [
      '1. 体现传统文化，具有文化底蕴。',
      '2. 古典家具耐用且具有收藏价值。',
      '3. 色彩沉稳，适合营造庄重氛围。',
      '4. 强调对称和平衡，提升空间美感。'
    ]
  },
  5: {
    imageUrl: new URL('@/assets/images/美式风格客厅.jpg', import.meta.url).href,
    description: '美式风格以舒适、实用为主，注重空间的宽敞和自由。色彩上多采用暖色调，搭配实木家具和布艺装饰，营造出温馨、舒适的氛围。',
    benefits: [
      '1. 空间宽敞，适合大家庭。',
      '2. 实木家具耐用且舒适。',
      '3. 色彩温暖，适合营造家庭氛围。',
      '4. 强调自由和舒适，提升居住体验。'
    ]
  },
  6: {
    imageUrl: new URL('@/assets/images/欧式风格客厅.jpg', import.meta.url).href,
    description: '欧式风格以奢华、典雅为主，注重细节和装饰。色彩上多采用金色、白色和深色，搭配华丽的家具和装饰，营造出高贵、优雅的氛围。',
    benefits: [
      '1. 体现奢华感，适合大空间。',
      '2. 华丽的装饰提升空间档次。',
      '3. 色彩丰富，适合营造高贵氛围。',
      '4. 强调细节和装饰，提升空间美感。'
    ]
  },
  7: {
    imageUrl: new URL('@/assets/images/地中海风格客厅.png', import.meta.url).href,
    description: '地中海风格以自然、清新为主，注重色彩和材质的搭配。色彩上多采用蓝色、白色和黄色，搭配石材和木质装饰，营造出轻松、自然的氛围。',
    benefits: [
      '1. 色彩明亮，适合营造轻松氛围。',
      '2. 自然材质环保健康。',
      '3. 适合海边或阳光充足的地区。',
      '4. 强调自然和清新，提升居住舒适度。'
    ]
  },
  8: {
    imageUrl: new URL('@/assets/images/日式风格客厅.jpg', import.meta.url).href,
    description: '日式风格以简约、自然为主，注重空间的开放性和功能性。色彩上多采用原木色和白色，搭配榻榻米和推拉门，营造出宁静、舒适的氛围。',
    benefits: [
      '1. 空间开放，适合小户型。',
      '2. 自然材质环保健康。',
      '3. 色彩柔和，适合长时间居住。',
      '4. 强调宁静和舒适，提升居住体验。'
    ]
  },
  9: {
    imageUrl: new URL('@/assets/images/田园风格客厅.jpg', import.meta.url).href,
    description: '田园风格以自然、温馨为主，注重色彩和材质的搭配。色彩上多采用绿色、粉色和浅黄色，搭配花卉图案和布艺装饰，营造出温馨、自然的氛围。',
    benefits: [
      '1. 色彩柔和，适合营造温馨氛围。',
      '2. 自然材质环保健康。',
      '3. 适合喜欢自然风格的人群。',
      '4. 强调自然和温馨，提升居住舒适度。'
    ]
  },
  10: {
    imageUrl: new URL('@/assets/images/轻奢风格客厅.png', import.meta.url).href,
    description: '轻奢风格以简约、奢华为主，注重材质和细节的搭配。色彩上多采用灰色、金色和黑色，搭配金属和大理石装饰，营造出低调、奢华的氛围。',
    benefits: [
      '1. 体现低调奢华感，适合现代家居。',
      '2. 材质高档，提升空间档次。',
      '3. 色彩沉稳，适合营造高级氛围。',
      '4. 强调细节和材质，提升空间美感。'
    ]
  },
  11: {
    imageUrl: new URL('@/assets/images/复古风客厅.jpg', import.meta.url).href,
    description: '复古风格以怀旧、经典为主，注重色彩和材质的搭配。色彩上多采用深色和暖色，搭配古典家具和装饰，营造出怀旧、经典的氛围。',
    benefits: [
      '1. 体现怀旧感，适合喜欢经典设计的人群。',
      '2. 古典家具耐用且具有收藏价值。',
      '3. 色彩深沉，适合营造怀旧氛围。',
      '4. 强调经典和怀旧，提升空间美感。'
    ]
  },
  12: {
    imageUrl: new URL('@/assets/images/艺术装饰风格客厅.jpg', import.meta.url).href,
    description: '艺术风格以创意、个性为主，注重色彩和材质的搭配。色彩上多采用鲜艳的颜色，搭配艺术装饰和个性化家具，营造出独特、艺术的氛围。',
    benefits: [
      '1. 体现个性化和创意，适合艺术爱好者。',
      '2. 色彩丰富，适合营造艺术氛围。',
      '3. 适合喜欢独特设计的人群。',
      '4. 强调创意和个性，提升空间美感。'
    ]
  },
  13: {
    imageUrl: new URL('@/assets/images/简约风卧室.jpg', import.meta.url).href,
    description: '简约卧室风格以简洁、实用为主，注重空间的功能性和舒适性。色彩上多采用中性色调，搭配简洁的线条和几何形状，营造出干净、明快的空间感。',
    benefits: [
      '1. 空间利用率高，适合小户型卧室。',
      '2. 简洁的设计易于清洁和维护。',
      '3. 中性色调和简约风格不易过时，具有较长的使用寿命。',
      '4. 强调自然光和开放空间，提升居住舒适度。'
    ]
  },
  14: {
    imageUrl: new URL('@/assets/images/北欧风卧室.jpg', import.meta.url).href,
    description: '北欧风卧室以自然、简约为主，注重功能性和舒适性。色彩上多采用白色、灰色和原木色，搭配自然材质，营造出温馨、舒适的空间感。',
    benefits: [
      '1. 自然材质环保健康。',
      '2. 简约设计易于搭配。',
      '3. 色彩柔和，适合长时间居住。',
      '4. 强调自然光和开放空间，提升居住舒适度。'
    ]
  },
  15: {
    imageUrl: new URL('@/assets/images/工业风卧室.jpg', import.meta.url).href,
    description: '工业风卧室以粗犷、原始为主，注重空间的开放性和功能性。色彩上多采用黑色、灰色和金属色，搭配裸露的砖墙和管道，营造出独特的空间感。',
    benefits: [
      '1. 空间开放，适合大户型卧室。',
      '2. 原始材质耐用且独特。',
      '3. 色彩深沉，适合个性化设计。',
      '4. 强调开放空间和功能性，提升居住舒适度。'
    ]
  },
  16: {
    imageUrl: new URL('@/assets/images/中式风格卧室.png', import.meta.url).href,
    description: '中式风格卧室以传统中国文化为灵感，注重对称和平衡。色彩上多采用深木色、红色和金色，搭配古典家具和装饰，营造出庄重、典雅的氛围。',
    benefits: [
      '1. 体现传统文化，具有文化底蕴。',
      '2. 古典家具耐用且具有收藏价值。',
      '3. 色彩沉稳，适合营造庄重氛围。',
      '4. 强调对称和平衡，提升空间美感。'
    ]
  },
  17: {
    imageUrl: new URL('@/assets/images/美式风格卧室.jpg', import.meta.url).href,
    description: '美式风格卧室以舒适、实用为主，注重空间的宽敞和自由。色彩上多采用暖色调，搭配实木家具和布艺装饰，营造出温馨、舒适的氛围。',
    benefits: [
      '1. 空间宽敞，适合大家庭卧室。',
      '2. 实木家具耐用且舒适。',
      '3. 色彩温暖，适合营造家庭氛围。',
      '4. 强调自由和舒适，提升居住体验。'
    ]
  },
  18: {
    imageUrl: new URL('@/assets/images/欧式风格卧室.jpg', import.meta.url).href,
    description: '欧式风格卧室以奢华、典雅为主，注重细节和装饰。色彩上多采用金色、白色和深色，搭配华丽的家具和装饰，营造出高贵、优雅的氛围。',
    benefits: [
      '1. 体现奢华感，适合大空间卧室。',
      '2. 华丽的装饰提升空间档次。',
      '3. 色彩丰富，适合营造高贵氛围。',
      '4. 强调细节和装饰，提升空间美感。'
    ]
  },
  19: {
    imageUrl: new URL('@/assets/images/地中海风格卧室.jpg', import.meta.url).href,
    description: '地中海风格卧室以自然、清新为主，注重色彩和材质的搭配。色彩上多采用蓝色、白色和黄色，搭配石材和木质装饰，营造出轻松、自然的氛围。',
    benefits: [
      '1. 色彩明亮，适合营造轻松氛围。',
      '2. 自然材质环保健康。',
      '3. 适合海边或阳光充足的地区。',
      '4. 强调自然和清新，提升居住舒适度。'
    ]
  },
  20: {
    imageUrl: new URL('@/assets/images/日式风格卧室.jpg', import.meta.url).href,
    description: '日式风格卧室以简约、自然为主，注重空间的开放性和功能性。色彩上多采用原木色和白色，搭配榻榻米和推拉门，营造出宁静、舒适的氛围。',
    benefits: [
      '1. 空间开放，适合小户型卧室。',
      '2. 自然材质环保健康。',
      '3. 色彩柔和，适合长时间居住。',
      '4. 强调宁静和舒适，提升居住体验。'
    ]
  },
  21: {
    imageUrl: new URL('@/assets/images/田园风格卧室.jpg', import.meta.url).href,
    description: '田园风格卧室以自然、温馨为主，注重色彩和材质的搭配。色彩上多采用绿色、粉色和浅黄色，搭配花卉图案和布艺装饰，营造出温馨、自然的氛围。',
    benefits: [
      '1. 色彩柔和，适合营造温馨氛围。',
      '2. 自然材质环保健康。',
      '3. 适合喜欢自然风格的人群。',
      '4. 强调自然和温馨，提升居住舒适度。'
    ]
  },
  22: {
    imageUrl: new URL('@/assets/images/轻奢风格卧室.png', import.meta.url).href,
    description: '轻奢风格卧室以简约、奢华为主，注重材质和细节的搭配。色彩上多采用灰色、金色和黑色，搭配金属和大理石装饰，营造出低调、奢华的氛围。',
    benefits: [
      '1. 体现低调奢华感，适合现代家居。',
      '2. 材质高档，提升空间档次。',
      '3. 色彩沉稳，适合营造高级氛围。',
      '4. 强调细节和材质，提升空间美感。'
    ]
  },
  23: {
    imageUrl: new URL('@/assets/images/复古风格卧室.jpg', import.meta.url).href,
    description: '复古风格卧室以怀旧、经典为主，注重色彩和材质的搭配。色彩上多采用深色和暖色，搭配古典家具和装饰，营造出怀旧、经典的氛围。',
    benefits: [
      '1. 体现怀旧感，适合喜欢经典设计的人群。',
      '2. 古典家具耐用且具有收藏价值。',
      '3. 色彩深沉，适合营造怀旧氛围。',
      '4. 强调经典和怀旧，提升空间美感。'
    ]
  },
  24: {
    imageUrl: new URL('@/assets/images/艺术风格卧室.png', import.meta.url).href,
    description: '艺术装饰风格卧室以创意、个性为主，注重色彩和材质的搭配。色彩上多采用鲜艳的颜色，搭配艺术装饰和个性化家具，营造出独特、艺术的氛围。',
    benefits: [
      '1. 体现个性化和创意，适合艺术爱好者。',
      '2. 色彩丰富，适合营造艺术氛围。',
      '3. 适合喜欢独特设计的人群。',
      '4. 强调创意和个性，提升空间美感。'
    ]
  },
    25: {
      imageUrl: new URL('@/assets/images/简约风厨房.jpg', import.meta.url).href,
      description: '简约风厨房以简洁、实用为主，注重空间的功能性和舒适性。色彩上多采用中性色调，搭配简洁的线条和几何形状，营造出干净、明快的空间感。',
      benefits: [
        '1. 空间利用率高，适合小户型厨房。',
        '2. 简洁的设计易于清洁和维护。',
        '3. 中性色调和简约风格不易过时，具有较长的使用寿命。',
        '4. 强调自然光和开放空间，提升烹饪体验。'
      ]
    },
    26: {
      imageUrl: new URL('@/assets/images/北欧风厨房.png', import.meta.url).href,
      description: '北欧风厨房以自然、简约为主，注重功能性和舒适性。色彩上多采用白色、灰色和原木色，搭配自然材质，营造出温馨、舒适的空间感。',
      benefits: [
        '1. 自然材质环保健康。',
        '2. 简约设计易于搭配。',
        '3. 色彩柔和，适合长时间烹饪。',
        '4. 强调自然光和开放空间，提升烹饪体验。'
      ]
    },
    27: {
      imageUrl: new URL('@/assets/images/工业风厨房.jpg', import.meta.url).href,
      description: '工业风厨房以粗犷、原始为主，注重空间的开放性和功能性。色彩上多采用黑色、灰色和金属色，搭配裸露的砖墙和管道，营造出独特的空间感。',
      benefits: [
        '1. 空间开放，适合大户型厨房。',
        '2. 原始材质耐用且独特。',
        '3. 色彩深沉，适合个性化设计。',
        '4. 强调开放空间和功能性，提升烹饪体验。'
      ]
    },
    28: {
      imageUrl: new URL('@/assets/images/中式风格厨房.jpg', import.meta.url).href,
      description: '中式风格厨房以传统中国文化为灵感，注重对称和平衡。色彩上多采用深木色、红色和金色，搭配古典家具和装饰，营造出庄重、典雅的氛围。',
      benefits: [
        '1. 体现传统文化，具有文化底蕴。',
        '2. 古典家具耐用且具有收藏价值。',
        '3. 色彩沉稳，适合营造庄重氛围。',
        '4. 强调对称和平衡，提升空间美感。'
      ]
    },
    29: {
      imageUrl: new URL('@/assets/images/美式风格厨房.jpg', import.meta.url).href,
      description: '美式风格厨房以舒适、实用为主，注重空间的宽敞和自由。色彩上多采用暖色调，搭配实木家具和布艺装饰，营造出温馨、舒适的氛围。',
      benefits: [
        '1. 空间宽敞，适合大家庭厨房。',
        '2. 实木家具耐用且舒适。',
        '3. 色彩温暖，适合营造家庭氛围。',
        '4. 强调自由和舒适，提升烹饪体验。'
      ]
    },
    30: {
      imageUrl: new URL('@/assets/images/欧式风格厨房.jpg', import.meta.url).href,
      description: '欧式风格厨房以奢华、典雅为主，注重细节和装饰。色彩上多采用金色、白色和深色，搭配华丽的家具和装饰，营造出高贵、优雅的氛围。',
      benefits: [
        '1. 体现奢华感，适合大空间厨房。',
        '2. 华丽的装饰提升空间档次。',
        '3. 色彩丰富，适合营造高贵氛围。',
        '4. 强调细节和装饰，提升空间美感。'
      ]
    },
    31: {
      imageUrl: new URL('@/assets/images/地中海风格厨房.png', import.meta.url).href,
      description: '地中海风格厨房以自然、清新为主，注重色彩和材质的搭配。色彩上多采用蓝色、白色和黄色，搭配石材和木质装饰，营造出轻松、自然的氛围。',
      benefits: [
        '1. 色彩明亮，适合营造轻松氛围。',
        '2. 自然材质环保健康。',
        '3. 适合海边或阳光充足的地区。',
        '4. 强调自然和清新，提升烹饪体验。'
      ]
    },
    32: {
      imageUrl: new URL('@/assets/images/日式风格厨房.jpg', import.meta.url).href,
      description: '日式风格厨房以简约、自然为主，注重空间的开放性和功能性。色彩上多采用原木色和白色，搭配榻榻米和推拉门，营造出宁静、舒适的氛围。',
      benefits: [
        '1. 空间开放，适合小户型厨房。',
        '2. 自然材质环保健康。',
        '3. 色彩柔和，适合长时间烹饪。',
        '4. 强调宁静和舒适，提升烹饪体验。'
      ]
    },
    33: {
      imageUrl: new URL('@/assets/images/田园风格厕所.png', import.meta.url).href,
      description: '田园风格厨房以自然、温馨为主，注重色彩和材质的搭配。色彩上多采用绿色、粉色和浅黄色，搭配花卉图案和布艺装饰，营造出温馨、自然的氛围。',
      benefits: [
        '1. 色彩柔和，适合营造温馨氛围。',
        '2. 自然材质环保健康。',
        '3. 适合喜欢自然风格的人群。',
        '4. 强调自然和温馨，提升烹饪体验。'
      ]
    },
    34: {
      imageUrl: new URL('@/assets/images/轻奢风格厨房.png', import.meta.url).href,
      description: '轻奢风格厨房以简约、奢华为主，注重材质和细节的搭配。色彩上多采用灰色、金色和黑色，搭配金属和大理石装饰，营造出低调、奢华的氛围。',
      benefits: [
        '1. 体现低调奢华感，适合现代家居。',
        '2. 材质高档，提升空间档次。',
        '3. 色彩沉稳，适合营造高级氛围。',
        '4. 强调细节和材质，提升空间美感。'
      ]
    },
    35: {
      imageUrl: new URL('@/assets/images/复古风格厨房.jpg', import.meta.url).href,
      description: '复古风格厨房以怀旧、经典为主，注重色彩和材质的搭配。色彩上多采用深色和暖色，搭配古典家具和装饰，营造出怀旧、经典的氛围。',
      benefits: [
        '1. 体现怀旧感，适合喜欢经典设计的人群。',
        '2. 古典家具耐用且具有收藏价值。',
        '3. 色彩深沉，适合营造怀旧氛围。',
        '4. 强调经典和怀旧，提升空间美感。'
      ]
    },
    36: {
      imageUrl: new URL('@/assets/images/艺术风格厨房.png', import.meta.url).href,
      description: '艺术厨房以创意、个性为主，注重色彩和材质的搭配。色彩上多采用鲜艳的颜色，搭配艺术装饰和个性化家具，营造出独特、艺术的氛围。',
      benefits: [
        '1. 体现个性化和创意，适合艺术爱好者。',
        '2. 色彩丰富，适合营造艺术氛围。',
        '3. 适合喜欢独特设计的人群。',
        '4. 强调创意和个性，提升空间美感。'
      ]
    },
  37: {
    imageUrl: new URL('@/assets/images/简约风浴室.png', import.meta.url).href,
    description: '简约风浴室以简洁、实用为主，注重空间的功能性和舒适性。色彩上多采用中性色调，搭配简洁的线条和几何形状，营造出干净、明快的空间感。',
    benefits: [
      '1. 空间利用率高，适合小户型浴室。',
      '2. 简洁的设计易于清洁和维护。',
      '3. 中性色调和简约风格不易过时，具有较长的使用寿命。',
      '4. 强调自然光和开放空间，提升沐浴体验。'
    ]
  },
  38: {
    imageUrl: new URL('@/assets/images/北欧风浴室.png', import.meta.url).href,
    description: '北欧风浴室以自然、简约为主，注重功能性和舒适性。色彩上多采用白色、灰色和原木色，搭配自然材质，营造出温馨、舒适的空间感。',
    benefits: [
      '1. 自然材质环保健康。',
      '2. 简约设计易于搭配。',
      '3. 色彩柔和，适合长时间使用。',
      '4. 强调自然光和开放空间，提升沐浴体验。'
    ]
  },
  39: {
    imageUrl: new URL('@/assets/images/工业风浴室.png', import.meta.url).href,
    description: '工业风浴室以粗犷、原始为主，注重空间的开放性和功能性。色彩上多采用黑色、灰色和金属色，搭配裸露的砖墙和管道，营造出独特的空间感。',
    benefits: [
      '1. 空间开放，适合大户型浴室。',
      '2. 原始材质耐用且独特。',
      '3. 色彩深沉，适合个性化设计。',
      '4. 强调开放空间和功能性，提升沐浴体验。'
    ]
  },
  40: {
    imageUrl: new URL('@/assets/images/中式风格浴室.png', import.meta.url).href,
    description: '中式风格浴室以传统中国文化为灵感，注重对称和平衡。色彩上多采用深木色、红色和金色，搭配古典家具和装饰，营造出庄重、典雅的氛围。',
    benefits: [
      '1. 体现传统文化，具有文化底蕴。',
      '2. 古典家具耐用且具有收藏价值。',
      '3. 色彩沉稳，适合营造庄重氛围。',
      '4. 强调对称和平衡，提升空间美感。'
    ]
  },
  41: {
    imageUrl: new URL('@/assets/images/美式风格浴室.jpg', import.meta.url).href,
    description: '美式风格浴室以舒适、实用为主，注重空间的宽敞和自由。色彩上多采用暖色调，搭配实木家具和布艺装饰，营造出温馨、舒适的氛围。',
    benefits: [
      '1. 空间宽敞，适合大家庭浴室。',
      '2. 实木家具耐用且舒适。',
      '3. 色彩温暖，适合营造家庭氛围。',
      '4. 强调自由和舒适，提升沐浴体验。'
    ]
  },
  42: {
    imageUrl: new URL('@/assets/images/欧式风格浴室.jpg', import.meta.url).href,
    description: '欧式风格浴室以奢华、典雅为主，注重细节和装饰。色彩上多采用金色、白色和深色，搭配华丽的家具和装饰，营造出高贵、优雅的氛围。',
    benefits: [
      '1. 体现奢华感，适合大空间浴室。',
      '2. 华丽的装饰提升空间档次。',
      '3. 色彩丰富，适合营造高贵氛围。',
      '4. 强调细节和装饰，提升空间美感。'
    ]
  },
  43: {
    imageUrl: new URL('@/assets/images/地中海风格浴室.jpg', import.meta.url).href,
    description: '地中海风格浴室以自然、清新为主，注重色彩和材质的搭配。色彩上多采用蓝色、白色和黄色，搭配石材和木质装饰，营造出轻松、自然的氛围。',
    benefits: [
      '1. 色彩明亮，适合营造轻松氛围。',
      '2. 自然材质环保健康。',
      '3. 适合海边或阳光充足的地区。',
      '4. 强调自然和清新，提升沐浴体验。'
    ]
  },
  44: {
    imageUrl: new URL('@/assets/images/日式风格浴室.png', import.meta.url).href,
    description: '日式风格浴室以简约、自然为主，注重空间的开放性和功能性。色彩上多采用原木色和白色，搭配榻榻米和推拉门，营造出宁静、舒适的氛围。',
    benefits: [
      '1. 空间开放，适合小户型浴室。',
      '2. 自然材质环保健康。',
      '3. 色彩柔和，适合长时间使用。',
      '4. 强调宁静和舒适，提升沐浴体验。'
    ]
  },
  45: {
    imageUrl: new URL('@/assets/images/田园风格浴室.jpg', import.meta.url).href,
    description: '田园风格浴室以自然、温馨为主，注重色彩和材质的搭配。色彩上多采用绿色、粉色和浅黄色，搭配花卉图案和布艺装饰，营造出温馨、自然的氛围。',
    benefits: [
      '1. 色彩柔和，适合营造温馨氛围。',
      '2. 自然材质环保健康。',
      '3. 适合喜欢自然风格的人群。',
      '4. 强调自然和温馨，提升沐浴体验。'
    ]
  },
  46: {
    imageUrl: new URL('@/assets/images/轻奢风格浴室.png', import.meta.url).href,
    description: '轻奢风格浴室以简约、奢华为主，注重材质和细节的搭配。色彩上多采用灰色、金色和黑色，搭配金属和大理石装饰，营造出低调、奢华的氛围。',
    benefits: [
      '1. 体现低调奢华感，适合现代家居。',
      '2. 材质高档，提升空间档次。',
      '3. 色彩沉稳，适合营造高级氛围。',
      '4. 强调细节和材质，提升空间美感。'
    ]
  },
  47: {
    imageUrl: new URL('@/assets/images/复古风浴室.png', import.meta.url).href,
    description: '复古风格浴室以怀旧、经典为主，注重色彩和材质的搭配。色彩上多采用深色和暖色，搭配古典家具和装饰，营造出怀旧、经典的氛围。',
    benefits: [
      '1. 体现怀旧感，适合喜欢经典设计的人群。',
      '2. 古典家具耐用且具有收藏价值。',
      '3. 色彩深沉，适合营造怀旧氛围。',
      '4. 强调经典和怀旧，提升空间美感。'
    ]
  },
  48: {
    imageUrl: new URL('@/assets/images/艺术风格浴室.png', import.meta.url).href,
    description: '艺术风格浴室以创意、个性为主，注重色彩和材质的搭配。色彩上多采用鲜艳的颜色，搭配艺术装饰和个性化家具，营造出独特、艺术的氛围。',
    benefits: [
      '1. 体现个性化和创意，适合艺术爱好者。',
      '2. 色彩丰富，适合营造艺术氛围。',
      '3. 适合喜欢独特设计的人群。',
      '4. 强调创意和个性，提升空间美感。'
    ]
  }
};

const route = useRoute();
const styleInfo = ref(null);




// 根据路由参数加载数据
onMounted(() => {
  const id = route.params.id; // 获取路由参数
  styleInfo.value = styles[id] || styles[1]; // 默认加载第一种风格
});
</script>

<template>
  <div class="page-container">
    <!-- 左侧装饰 -->
    <div class="decorative-left"></div>

    <!-- 主要内容区域 -->
    <div class="container">
      <!-- 图片居中 -->
      <div class="image-container">
        <img :src="styleInfo?.imageUrl" alt="装修风格图片" class="style-image" />
      </div>

      <!-- 风格特点介绍 -->
      <div class="content-box">
        <h2>风格特点</h2>
        <p>{{ styleInfo?.description }}</p>
      </div>

      <!-- 风格建筑的好处 -->
      <div class="content-box">
        <h2>风格建筑的好处</h2>
        <ul>
          <li v-for="(benefit, index) in styleInfo?.benefits" :key="index">{{ benefit }}</li>
        </ul>
      </div>

      <button class="home-button">加入喜欢</button>
      &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;
      <!-- 返回首页按钮 -->
      <button class="home-button" @click="router.push('/aifront')">返回首页</button>
    </div>

    <!-- 右侧装饰 -->
    <div class="decorative-right"></div>
  </div>
</template>


<style scoped>
/* 样式改为浅色主题 */
.page-container {
  display: flex;
  justify-content: space-between;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f5f5, #f5f5f5);
  padding: 0 20px;
}

.decorative-left,
.decorative-right {
  width: 10%;
  height: 100vh;
  background: linear-gradient(135deg, #f5f5f5 60%, #e0e5ec 100%);
  position: relative;
  border-right: 1px solid #e2e8f0;
  border-left: 1px solid #e2e8f0;
}

.decorative-left::before,
.decorative-right::before {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  width: 2px;
  height: 80%;
  background: rgba(6, 182, 212, 0.12);
  transform: translate(-50%, -50%);
}

.container {
  max-width: 800px;
  width: 100%;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 12px;
  box-shadow: 0 0 20px rgba(6, 182, 212, 0.08);
  border: 1px solid #e2e8f0;
}

.image-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.style-image {
  max-width: 100%;
  height: auto;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  border: 1px solid #e2e8f0;
  filter: brightness(1);
  transition: filter 0.3s ease;
}

.style-image:hover {
  filter: brightness(1.05);
  box-shadow: 0 0 20px rgba(6, 182, 212, 0.12);
}

.content-box {
  background: #f8fafc;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.04);
  text-align: left;
  border: 1px solid #e2e8f0;
}

h2 {
  font-size: 24px;
  margin-bottom: 15px;
  color: #000;
  font-weight: 600;
}

p {
  font-size: 16px;
  line-height: 1.8;
  color: #333;
  margin-bottom: 0;
}

ul {
  list-style-type: disc;
  padding-left: 20px;
  margin-top: 10px;
}

li {
  font-size: 16px;
  line-height: 1.8;
  color: #333;
  margin-bottom: 8px;
}

/* 按钮样式 */
.home-button {
  display: inline-block;
  padding: 12px 24px;
  font-size: 16px;
  font-weight: 600;
  color: #fff;
  background: linear-gradient(135deg, #0891b2, #06b6d4);
  border: none;
  border-radius: 25px;
  box-shadow: 0 4px 12px rgba(6, 182, 212, 0.15);
  cursor: pointer;
  transition: all 0.3s ease;
  margin-top: 20px;
}

.home-button:hover {
  background: linear-gradient(135deg, #06b6d4, #0e7490);
  box-shadow: 0 0 20px rgba(6, 182, 212, 0.18);
  transform: translateY(-2px);
}

.home-button:active {
  transform: translateY(0);
  box-shadow: 0 4px 12px rgba(6, 182, 212, 0.08);
}
</style>