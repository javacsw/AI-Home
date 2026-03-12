<script setup>
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const temperature = ref('')
const humidity = ref('')
const light = ref('')

function extractValue(str) {
  const match = str.match(/value=([\d.\-]+)/)
  return match ? match[1] : ''
}

const fetchTemperature = async () => {
  const res = await axios.post('/ai/push_temperature')
  temperature.value = extractValue(res.data.data || '')
}
const fetchHumidity = async () => {
  const res = await axios.post('/ai/push_humidity')
  humidity.value = extractValue(res.data.data || '')
}
const fetchLight = async () => {
  const res = await axios.post('/ai/push_light_intensity')
  light.value = extractValue(res.data.data || '')
}

const humidityNum = computed(() => parseFloat(humidity.value) || 0)

const router = useRouter()
const goBack = () => {
  router.push('/centre')
}

onMounted(async () => {
  await fetchTemperature()
  await fetchHumidity()
  await fetchLight()
})
</script>

<template>
  <div class="env-screen">
    <h2 class="env-title">环境监测数据大屏</h2>
    <div class="env-cards">
      <div class="env-card temp">
        <div class="env-icon">🌡️</div>
        <div class="env-label">温度数据</div>
        <div class="env-content" v-html="temperature.replace(/\n/g, '<br/>')"></div>
      </div>
      <div class="env-card hum">
        <div class="env-icon">💧</div>
        <div class="env-label">湿度数据</div>
        <div class="env-content" v-html="humidity.replace(/\n/g, '<br/>')"></div>
      </div>
      <div class="env-card light">
        <div class="env-icon">💡</div>
        <div class="env-label">光照数据</div>
        <div class="env-content" v-html="light.replace(/\n/g, '<br/>')"></div>
      </div>
    </div>
    <div class="env-tip" v-if="humidityNum > 80">
      <span class="tip-icon">⚠️</span> 湿度过高，建议加强通风，使用除湿设备，防止墙体发霉、木制品变形。高湿环境下，建议选择防潮性能好的装修材料，如防潮石膏板、防水涂料等，避免实木地板和壁纸，保持室内空气流通，定期检查墙体和家具，及时处理霉变和水渍问题，保障居住健康。
    </div>
    <div class="env-tip" v-else-if="humidityNum >= 30 && humidityNum <= 80">
      <span class="tip-icon">✅</span> 当前湿度适宜，有利于房屋装修和居住。建议选择环保型装修材料，保持适度通风，防止室内空气污染。适宜的湿度有助于延长家具和地板的使用寿命，减少墙体开裂和变形风险。日常可通过绿植调节湿度，营造健康舒适的居住环境。
    </div>
    <div class="env-tip" v-else-if="humidityNum < 30">
      <span class="tip-icon">💡</span> 湿度过低，建议适当加湿，防止木制品开裂、墙面干裂。低湿环境下，建议使用加湿器或摆放水盆，选择耐干燥的装修材料如复合地板、乳胶漆等，避免大面积使用实木。定期为家具补充保养油，防止干裂，保持室内湿度在合理范围，提升居住舒适度。
    </div>
    <div class="env-extra big-extra">
      <div class="extra-card notice big-card">
        <div class="extra-icon">📢</div>
        <div class="extra-title">温馨提示</div>
        <div class="extra-content">请定期检查设备状态，确保数据实时准确。如有异常请及时联系管理员。</div>
      </div>
      <div class="extra-card info big-card">
        <div class="extra-icon">📈</div>
        <div class="extra-title">数据说明</div>
        <div class="extra-content">本页面展示为实时环境监测数据，数据来源于智能采集终端，支持多终端接入。</div>
      </div>
      <div class="extra-card team big-card">
        <div class="extra-icon">🤝</div>
        <div class="extra-title">项目团队</div>
        <div class="extra-content">AI测绘平台研发组<br>感谢您的关注与支持！</div>
      </div>
    </div>
    <button class="back-btn" @click="goBack">返回</button>
  </div>
</template>

<style scoped>
.env-screen {
  width: 100vw;
  min-height: 100vh;
  background: linear-gradient(120deg, #f0f4f8 0%, #e0e7ef 100%);
  padding: 32px 0 0 0;
  box-sizing: border-box;
}
.env-title {
  text-align: center;
  font-size: 2.5rem;
  font-weight: bold;
  color: #222;
  margin-bottom: 32px;
  letter-spacing: 4px;
}
.env-cards {
  display: flex;
  justify-content: center;
  gap: 40px;
  flex-wrap: wrap;
}
.env-card {
  background: #fff;
  border-radius: 22px;
  box-shadow: 0 6px 32px 0 rgba(30,144,255,0.10);
  padding: 32px 32px 28px 32px;
  min-width: 320px;
  max-width: 400px;
  min-height: 260px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  transition: box-shadow 0.2s, transform 0.2s;
  font-size: 1.2rem;
  word-break: break-all;
  white-space: pre-line;
}
.env-card:hover {
  box-shadow: 0 12px 40px 0 rgba(30,144,255,0.18);
  transform: translateY(-4px) scale(1.03);
}
.env-icon {
  font-size: 2.5rem;
  margin-bottom: 10px;
}
.env-label {
  font-size: 1.3rem;
  font-weight: 600;
  color: #1e90ff;
  margin-bottom: 12px;
  letter-spacing: 2px;
}
.env-content {
  width: 100%;
  color: #333;
  font-size: 2.8rem;
  font-weight: bold;
  line-height: 1.3;
  text-align: center;
  margin-top: 24px;
  word-break: break-all;
  white-space: pre-line;
  letter-spacing: 2px;
  transition: color 0.2s;
}
.env-card.temp .env-content { color: #ff7f50; }
.env-card.hum .env-content { color: #1e90ff; }
.env-card.light .env-content { color: #ffd700; }
.env-card.temp .env-label { color: #ff7f50; }
.env-card.hum .env-label { color: #1e90ff; }
.env-card.light .env-label { color: #ffd700; }

.env-tip {
  margin: 32px auto 0 auto;
  max-width: 800px;
  background: #fffbe6;
  color: #d48806;
  border-left: 6px solid #faad14;
  border-radius: 12px;
  font-size: 1.25rem;
  padding: 18px 32px;
  box-shadow: 0 2px 12px 0 rgba(250,173,20,0.07);
  display: flex;
  align-items: center;
  gap: 12px;
  font-weight: 500;
  letter-spacing: 1px;
}
.tip-icon {
  font-size: 1.6rem;
}

.env-extra.big-extra {
  margin: 56px auto 0 auto;
  display: flex;
  justify-content: center;
  gap: 48px;
  flex-wrap: wrap;
  max-width: 1400px;
}
.big-card {
  min-width: 340px;
  max-width: 440px;
  min-height: 200px;
  font-size: 1.15rem;
  padding: 38px 38px 28px 38px;
}
.extra-card {
  background: #f8fbff;
  border-radius: 18px;
  box-shadow: 0 2px 12px 0 rgba(30,144,255,0.07);
  padding: 28px 32px 20px 32px;
  min-width: 260px;
  max-width: 340px;
  min-height: 160px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  margin-bottom: 18px;
  border-left: 6px solid #1e90ff;
  transition: box-shadow 0.2s, border-color 0.2s;
}
.extra-card.notice { border-left-color: #ff7f50; }
.extra-card.info { border-left-color: #1e90ff; }
.extra-card.team { border-left-color: #ffd700; }
.extra-icon {
  font-size: 1.8rem;
  margin-bottom: 8px;
}
.extra-title {
  font-size: 1.15rem;
  font-weight: 600;
  color: #1e90ff;
  margin-bottom: 8px;
}
.extra-card.notice .extra-title { color: #ff7f50; }
.extra-card.team .extra-title { color: #ffd700; }
.extra-content {
  color: #444;
  font-size: 1rem;
  line-height: 1.7;
  margin-top: 2px;
  word-break: break-all;
}
.back-btn {
  display: block;
  margin: 48px auto 0 auto;
  padding: 12px 48px;
  font-size: 1.2rem;
  font-weight: 600;
  color: #fff;
  background: linear-gradient(90deg, #1e90ff 0%, #ff7f50 100%);
  border: none;
  border-radius: 28px;
  box-shadow: 0 2px 8px 0 rgba(30,144,255,0.10);
  cursor: pointer;
  transition: background 0.2s, box-shadow 0.2s;
}
.back-btn:hover {
  background: linear-gradient(90deg, #ff7f50 0%, #1e90ff 100%);
  box-shadow: 0 4px 16px 0 rgba(30,144,255,0.18);
}
@media (max-width: 1100px) {
  .env-cards, .env-extra.big-extra {
    flex-direction: column;
    align-items: center;
  }
  .env-extra.big-extra {
    gap: 24px;
  }
}
</style>