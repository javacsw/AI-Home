<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import * as THREE from 'three'
import { OrbitControls } from 'three-stdlib'

// 响应式引用 - 只对 DOM 元素使用响应式
const containerRef = ref<HTMLDivElement>()

// 场景切换状态
const currentStyle = ref<'living_room' | 'bedroom' | 'bathroom' | 'kitchen'>('living_room')
const currentTheme = ref<'chinese' | 'european' | 'modern' | 'nordic'>('chinese')
const isTransitioning = ref(false)

// Three.js 对象不使用响应式包装，避免代理冲突
let scene: THREE.Scene
let camera: THREE.PerspectiveCamera
let renderer: THREE.WebGLRenderer
let controls: OrbitControls

// 场景对象存储
let currentSceneObjects: THREE.Object3D[] = []

// 动画ID
let animationId: number

// 初始化场景
const initScene = () => {
  if (!containerRef.value) return

  // 创建场景
  scene = new THREE.Scene()
  scene.background = new THREE.Color(0x87CEEB) // 天蓝色背景

  // 创建相机
  camera = new THREE.PerspectiveCamera(
    60,
    containerRef.value.clientWidth / containerRef.value.clientHeight,
    0.1,
    1000
  )
  camera.position.set(8, 6, 8)
  camera.lookAt(0, 0, 0)

  // 创建渲染器
  renderer = new THREE.WebGLRenderer({ 
    antialias: true,
    alpha: true
  })
  renderer.setSize(containerRef.value.clientWidth, containerRef.value.clientHeight)
  renderer.shadowMap.enabled = true
  renderer.shadowMap.type = THREE.PCFSoftShadowMap
  renderer.outputColorSpace = THREE.SRGBColorSpace
  renderer.toneMapping = THREE.ACESFilmicToneMapping
  renderer.toneMappingExposure = 2.0
  containerRef.value.appendChild(renderer.domElement)

  // 添加轨道控制器
  controls = new OrbitControls(camera, renderer.domElement)
  controls.enableDamping = true
  controls.dampingFactor = 0.05
  controls.enableZoom = true
  controls.enablePan = true
  controls.minDistance = 3
  controls.maxDistance = 20
  controls.maxPolarAngle = Math.PI / 2.2

  // 添加光照
  addLighting()

  // 创建初始室内环境
  createIndoorEnvironment()

  // 开始动画循环
  animate()
}

// 创建室内环境
const createIndoorEnvironment = () => {
  if (!scene) return

  // 清除现有场景对象
  clearScene()

  // 根据风格和房间类型创建环境
  switch (currentStyle.value) {
    case 'living_room':
      createLivingRoomEnvironment()
      break
    case 'bedroom':
      createBedroomEnvironment()
      break
    case 'bathroom':
      createBathroomEnvironment()
      break
    case 'kitchen':
      createKitchenEnvironment()
      break
  }
}

// 清除场景对象
const clearScene = () => {
  currentSceneObjects.forEach(obj => {
    scene.remove(obj)
    if ('geometry' in obj && obj.geometry) {
      (obj.geometry as THREE.BufferGeometry).dispose()
    }
    if ('material' in obj && obj.material) {
      if (Array.isArray(obj.material)) {
        obj.material.forEach((mat: THREE.Material) => mat.dispose())
      } else {
        (obj.material as THREE.Material).dispose()
      }
    }
  })
  currentSceneObjects = []
}

// 创建客厅环境
const createLivingRoomEnvironment = () => {
  if (!scene) return

  // 根据风格创建不同的客厅环境
  switch (currentTheme.value) {
    case 'chinese':
      createChineseLivingRoom()
      break
    case 'european':
      createEuropeanLivingRoom()
      break
    case 'modern':
      createModernLivingRoom()
      break
    case 'nordic':
      createNordicLivingRoom()
      break
  }
}

// 中式客厅
const createChineseLivingRoom = () => {
  if (!scene) return

  // 中式地板 - 深红色木地板
  const floorGeometry = new THREE.PlaneGeometry(20, 15)
  const floorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色
    roughness: 0.9,
    metalness: 0.0
  })
  const floor = new THREE.Mesh(floorGeometry, floorMaterial)
  floor.rotation.x = -Math.PI / 2
  floor.receiveShadow = true
  scene.add(floor)
  currentSceneObjects.push(floor)

  // 中式天花板 - 米黄色
  const ceilingGeometry = new THREE.PlaneGeometry(20, 15)
  const ceilingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF5DEB3, // 米黄色
    roughness: 0.3,
    metalness: 0.0
  })
  const ceiling = new THREE.Mesh(ceilingGeometry, ceilingMaterial)
  ceiling.rotation.x = Math.PI / 2
  ceiling.position.y = 3.5
  scene.add(ceiling)
  currentSceneObjects.push(ceiling)

  // 中式墙壁 - 米黄色
  const wallMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF5DEB3, // 米黄色
    roughness: 0.6,
    metalness: 0.0
  })

  // 创建墙壁
  createWalls(wallMaterial, 20, 15, 3.5)

  // 添加中式家具
  addChineseLivingRoomFurniture()
}

// 欧式客厅
const createEuropeanLivingRoom = () => {
  if (!scene) return

  // 欧式地板 - 深色木地板
  const floorGeometry = new THREE.PlaneGeometry(20, 15)
  const floorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x654321, // 深棕色
    roughness: 0.8,
    metalness: 0.1
  })
  const floor = new THREE.Mesh(floorGeometry, floorMaterial)
  floor.rotation.x = -Math.PI / 2
  floor.receiveShadow = true
  scene.add(floor)
  currentSceneObjects.push(floor)

  // 欧式天花板 - 白色带装饰
  const ceilingGeometry = new THREE.PlaneGeometry(20, 15)
  const ceilingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF,
    roughness: 0.2,
    metalness: 0.0
  })
  const ceiling = new THREE.Mesh(ceilingGeometry, ceilingMaterial)
  ceiling.rotation.x = Math.PI / 2
  ceiling.position.y = 4
  scene.add(ceiling)
  currentSceneObjects.push(ceiling)

  // 欧式墙壁 - 米白色
  const wallMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF5F5DC, // 米白色
    roughness: 0.5,
    metalness: 0.0
  })

  // 创建墙壁
  createWalls(wallMaterial, 20, 15, 4)

  // 添加欧式家具
  addEuropeanLivingRoomFurniture()
}

// 现代客厅
const createModernLivingRoom = () => {
  if (!scene) return

  // 现代地板 - 浅色木地板
  const floorGeometry = new THREE.PlaneGeometry(20, 15)
  const floorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xD2B48C, // 浅棕色
    roughness: 0.3,
    metalness: 0.0
  })
  const floor = new THREE.Mesh(floorGeometry, floorMaterial)
  floor.rotation.x = -Math.PI / 2
  floor.receiveShadow = true
  scene.add(floor)
  currentSceneObjects.push(floor)

  // 现代天花板 - 白色
  const ceilingGeometry = new THREE.PlaneGeometry(20, 15)
  const ceilingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF,
    roughness: 0.1,
    metalness: 0.0
  })
  const ceiling = new THREE.Mesh(ceilingGeometry, ceilingMaterial)
  ceiling.rotation.x = Math.PI / 2
  ceiling.position.y = 3
  scene.add(ceiling)
  currentSceneObjects.push(ceiling)

  // 现代墙壁 - 浅灰色
  const wallMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF0F0F0, // 浅灰色
    roughness: 0.4,
    metalness: 0.0
  })

  // 创建墙壁
  createWalls(wallMaterial, 20, 15, 3)

  // 添加现代家具
  addModernLivingRoomFurniture()
}

// 北欧客厅
const createNordicLivingRoom = () => {
  if (!scene) return

  // 北欧地板 - 浅色木地板
  const floorGeometry = new THREE.PlaneGeometry(20, 15)
  const floorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xDEB887, // 浅棕色
    roughness: 0.7,
    metalness: 0.0
  })
  const floor = new THREE.Mesh(floorGeometry, floorMaterial)
  floor.rotation.x = -Math.PI / 2
  floor.receiveShadow = true
  scene.add(floor)
  currentSceneObjects.push(floor)

  // 北欧天花板 - 白色
  const ceilingGeometry = new THREE.PlaneGeometry(20, 15)
  const ceilingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF,
    roughness: 0.2,
    metalness: 0.0
  })
  const ceiling = new THREE.Mesh(ceilingGeometry, ceilingMaterial)
  ceiling.rotation.x = Math.PI / 2
  ceiling.position.y = 3.2
  scene.add(ceiling)
  currentSceneObjects.push(ceiling)

  // 北欧墙壁 - 米白色
  const wallMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFAF0, // 米白色
    roughness: 0.6,
    metalness: 0.0
  })

  // 创建墙壁
  createWalls(wallMaterial, 20, 15, 3.2)

  // 添加北欧家具
  addNordicLivingRoomFurniture()
}

// 通用墙壁创建函数
const createWalls = (wallMaterial: THREE.MeshStandardMaterial, width: number, depth: number, height: number) => {
  // 后墙
  const backWallGeometry = new THREE.PlaneGeometry(width, height)
  const backWall = new THREE.Mesh(backWallGeometry, wallMaterial)
  backWall.position.z = -depth / 2
  backWall.position.y = height / 2
  scene.add(backWall)
  currentSceneObjects.push(backWall)

  // 左墙
  const leftWallGeometry = new THREE.PlaneGeometry(depth, height)
  const leftWall = new THREE.Mesh(leftWallGeometry, wallMaterial)
  leftWall.rotation.y = Math.PI / 2
  leftWall.position.x = -width / 2
  leftWall.position.y = height / 2
  scene.add(leftWall)
  currentSceneObjects.push(leftWall)

  // 右墙
  const rightWallGeometry = new THREE.PlaneGeometry(depth, height)
  const rightWall = new THREE.Mesh(rightWallGeometry, wallMaterial)
  rightWall.rotation.y = -Math.PI / 2
  rightWall.position.x = width / 2
  rightWall.position.y = height / 2
  scene.add(rightWall)
  currentSceneObjects.push(rightWall)

  // 前墙（部分，留出入口）
  const frontWallLeftGeometry = new THREE.PlaneGeometry(width / 3, height)
  const frontWallLeft = new THREE.Mesh(frontWallLeftGeometry, wallMaterial)
  frontWallLeft.position.z = depth / 2
  frontWallLeft.position.x = -width / 3
  frontWallLeft.position.y = height / 2
  scene.add(frontWallLeft)
  currentSceneObjects.push(frontWallLeft)

  const frontWallRightGeometry = new THREE.PlaneGeometry(width / 3, height)
  const frontWallRight = new THREE.Mesh(frontWallRightGeometry, wallMaterial)
  frontWallRight.position.z = depth / 2
  frontWallRight.position.x = width / 3
  frontWallRight.position.y = height / 2
  scene.add(frontWallRight)
  currentSceneObjects.push(frontWallRight)
}

// 创建卧室环境
const createBedroomEnvironment = () => {
  if (!scene) return

  // 根据风格创建不同的卧室环境
  switch (currentTheme.value) {
    case 'chinese':
      createChineseBedroom()
      break
    case 'european':
      createEuropeanBedroom()
      break
    case 'modern':
      createModernBedroom()
      break
    case 'nordic':
      createNordicBedroom()
      break
  }
}

// 中式卧室
const createChineseBedroom = () => {
  if (!scene) return

  // 中式卧室地板 - 深红色木地板
  const floorGeometry = new THREE.PlaneGeometry(12, 10)
  const floorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色
    roughness: 0.8,
    metalness: 0.0
  })
  const floor = new THREE.Mesh(floorGeometry, floorMaterial)
  floor.rotation.x = -Math.PI / 2
  floor.receiveShadow = true
  scene.add(floor)
  currentSceneObjects.push(floor)

  // 中式卧室天花板 - 米黄色
  const ceilingGeometry = new THREE.PlaneGeometry(12, 10)
  const ceilingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF5DEB3, // 米黄色
    roughness: 0.3,
    metalness: 0.0
  })
  const ceiling = new THREE.Mesh(ceilingGeometry, ceilingMaterial)
  ceiling.rotation.x = Math.PI / 2
  ceiling.position.y = 3.2
  scene.add(ceiling)
  currentSceneObjects.push(ceiling)

  // 中式卧室墙壁 - 深红色
  const wallMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色
    roughness: 0.6,
    metalness: 0.0
  })

  // 创建墙壁
  createBedroomWalls(wallMaterial, 12, 10, 3.2)

  // 添加中式卧室家具
  addChineseBedroomFurniture()
}

// 欧式卧室
const createEuropeanBedroom = () => {
  if (!scene) return

  // 欧式卧室地板 - 深色木地板
  const floorGeometry = new THREE.PlaneGeometry(12, 10)
  const floorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x654321, // 深棕色
    roughness: 0.8,
    metalness: 0.1
  })
  const floor = new THREE.Mesh(floorGeometry, floorMaterial)
  floor.rotation.x = -Math.PI / 2
  floor.receiveShadow = true
  scene.add(floor)
  currentSceneObjects.push(floor)

  // 欧式卧室天花板 - 白色带装饰
  const ceilingGeometry = new THREE.PlaneGeometry(12, 10)
  const ceilingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF, // 白色
    roughness: 0.2,
    metalness: 0.0
  })
  const ceiling = new THREE.Mesh(ceilingGeometry, ceilingMaterial)
  ceiling.rotation.x = Math.PI / 2
  ceiling.position.y = 3.5
  scene.add(ceiling)
  currentSceneObjects.push(ceiling)

  // 欧式卧室墙壁 - 米白色
  const wallMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF5F5DC, // 米白色
    roughness: 0.5,
    metalness: 0.0
  })

  // 创建墙壁
  createBedroomWalls(wallMaterial, 12, 10, 3.5)

  // 添加欧式卧室家具
  addEuropeanBedroomFurniture()
}

// 现代卧室
const createModernBedroom = () => {
  if (!scene) return

  // 现代卧室地板 - 浅色木地板
  const floorGeometry = new THREE.PlaneGeometry(12, 10)
  const floorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xD2B48C, // 浅棕色
    roughness: 0.3,
    metalness: 0.0
  })
  const floor = new THREE.Mesh(floorGeometry, floorMaterial)
  floor.rotation.x = -Math.PI / 2
  floor.receiveShadow = true
  scene.add(floor)
  currentSceneObjects.push(floor)

  // 现代卧室天花板 - 白色
  const ceilingGeometry = new THREE.PlaneGeometry(12, 10)
  const ceilingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF, // 白色
    roughness: 0.1,
    metalness: 0.0
  })
  const ceiling = new THREE.Mesh(ceilingGeometry, ceilingMaterial)
  ceiling.rotation.x = Math.PI / 2
  ceiling.position.y = 2.8
  scene.add(ceiling)
  currentSceneObjects.push(ceiling)

  // 现代卧室墙壁 - 浅灰色
  const wallMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF0F0F0, // 浅灰色
    roughness: 0.4,
    metalness: 0.0
  })

  // 创建墙壁
  createBedroomWalls(wallMaterial, 12, 10, 2.8)

  // 添加现代卧室家具
  addModernBedroomFurniture()
}

// 北欧卧室
const createNordicBedroom = () => {
  if (!scene) return

  // 北欧卧室地板 - 浅色木地板
  const floorGeometry = new THREE.PlaneGeometry(12, 10)
  const floorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xDEB887, // 浅棕色
    roughness: 0.7,
    metalness: 0.0
  })
  const floor = new THREE.Mesh(floorGeometry, floorMaterial)
  floor.rotation.x = -Math.PI / 2
  floor.receiveShadow = true
  scene.add(floor)
  currentSceneObjects.push(floor)

  // 北欧卧室天花板 - 白色
  const ceilingGeometry = new THREE.PlaneGeometry(12, 10)
  const ceilingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF, // 白色
    roughness: 0.2,
    metalness: 0.0
  })
  const ceiling = new THREE.Mesh(ceilingGeometry, ceilingMaterial)
  ceiling.rotation.x = Math.PI / 2
  ceiling.position.y = 3.0
  scene.add(ceiling)
  currentSceneObjects.push(ceiling)

  // 北欧卧室墙壁 - 米白色
  const wallMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFAF0, // 米白色
    roughness: 0.6,
    metalness: 0.0
  })

  // 创建墙壁
  createBedroomWalls(wallMaterial, 12, 10, 3.0)

  // 添加北欧卧室家具
  addNordicBedroomFurniture()
}

// 通用卧室墙壁创建函数
const createBedroomWalls = (wallMaterial: THREE.MeshStandardMaterial, width: number, depth: number, height: number) => {
  // 后墙
  const backWallGeometry = new THREE.PlaneGeometry(width, height)
  const backWall = new THREE.Mesh(backWallGeometry, wallMaterial)
  backWall.position.z = -depth / 2
  backWall.position.y = height / 2
  scene.add(backWall)
  currentSceneObjects.push(backWall)

  // 左墙
  const leftWallGeometry = new THREE.PlaneGeometry(depth, height)
  const leftWall = new THREE.Mesh(leftWallGeometry, wallMaterial)
  leftWall.rotation.y = Math.PI / 2
  leftWall.position.x = -width / 2
  leftWall.position.y = height / 2
  scene.add(leftWall)
  currentSceneObjects.push(leftWall)

  // 右墙
  const rightWallGeometry = new THREE.PlaneGeometry(depth, height)
  const rightWall = new THREE.Mesh(rightWallGeometry, wallMaterial)
  rightWall.rotation.y = -Math.PI / 2
  rightWall.position.x = width / 2
  rightWall.position.y = height / 2
  scene.add(rightWall)
  currentSceneObjects.push(rightWall)

  // 前墙（部分，留出入口）
  const frontWallLeftGeometry = new THREE.PlaneGeometry(width / 3, height)
  const frontWallLeft = new THREE.Mesh(frontWallLeftGeometry, wallMaterial)
  frontWallLeft.position.z = depth / 2
  frontWallLeft.position.x = -width / 3
  frontWallLeft.position.y = height / 2
  scene.add(frontWallLeft)
  currentSceneObjects.push(frontWallLeft)

  const frontWallRightGeometry = new THREE.PlaneGeometry(width / 3, height)
  const frontWallRight = new THREE.Mesh(frontWallRightGeometry, wallMaterial)
  frontWallRight.position.z = depth / 2
  frontWallRight.position.x = width / 3
  frontWallRight.position.y = height / 2
  scene.add(frontWallRight)
  currentSceneObjects.push(frontWallRight)
}

// 创建卫生间环境
const createBathroomEnvironment = () => {
  if (!scene) return

  // 根据风格创建不同的卫生间环境
  switch (currentTheme.value) {
    case 'chinese':
      createChineseBathroom()
      break
    case 'european':
      createEuropeanBathroom()
      break
    case 'modern':
      createModernBathroom()
      break
    case 'nordic':
      createNordicBathroom()
      break
  }
}

// 中式卫生间
const createChineseBathroom = () => {
  if (!scene) return

  // 中式卫生间地板 - 深色瓷砖
  const floorGeometry = new THREE.PlaneGeometry(8, 6)
  const floorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.1,
    metalness: 0.2
  })
  const floor = new THREE.Mesh(floorGeometry, floorMaterial)
  floor.rotation.x = -Math.PI / 2
  floor.receiveShadow = true
  scene.add(floor)
  currentSceneObjects.push(floor)

  // 中式卫生间天花板 - 米黄色
  const ceilingGeometry = new THREE.PlaneGeometry(8, 6)
  const ceilingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF5DEB3, // 米黄色
    roughness: 0.2,
    metalness: 0.0
  })
  const ceiling = new THREE.Mesh(ceilingGeometry, ceilingMaterial)
  ceiling.rotation.x = Math.PI / 2
  ceiling.position.y = 2.8
  scene.add(ceiling)
  currentSceneObjects.push(ceiling)

  // 中式卫生间墙壁 - 深红色瓷砖
  const wallMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色
    roughness: 0.1,
    metalness: 0.1
  })

  // 创建墙壁
  createBathroomWalls(wallMaterial, 8, 6, 2.8)

  // 添加中式卫生间设备
  addChineseBathroomFixtures()
  
  // 添加中式卫生间装饰
  addChineseBathroomDecorations()
}

// 欧式卫生间
const createEuropeanBathroom = () => {
  if (!scene) return

  // 欧式卫生间地板 - 大理石瓷砖
  const floorGeometry = new THREE.PlaneGeometry(8, 6)
  const floorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF5F5DC, // 米白色
    roughness: 0.05,
    metalness: 0.1
  })
  const floor = new THREE.Mesh(floorGeometry, floorMaterial)
  floor.rotation.x = -Math.PI / 2
  floor.receiveShadow = true
  scene.add(floor)
  currentSceneObjects.push(floor)

  // 欧式卫生间天花板 - 白色带装饰
  const ceilingGeometry = new THREE.PlaneGeometry(8, 6)
  const ceilingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF,
    roughness: 0.1,
    metalness: 0.0
  })
  const ceiling = new THREE.Mesh(ceilingGeometry, ceilingMaterial)
  ceiling.rotation.x = Math.PI / 2
  ceiling.position.y = 3.2
  scene.add(ceiling)
  currentSceneObjects.push(ceiling)

  // 欧式卫生间墙壁 - 米白色瓷砖
  const wallMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF5F5DC, // 米白色
    roughness: 0.1,
    metalness: 0.1
  })

  // 创建墙壁
  createBathroomWalls(wallMaterial, 8, 6, 3.2)

  // 添加欧式卫生间设备
  addEuropeanBathroomFixtures()
  
  // 添加欧式卫生间装饰
  addEuropeanBathroomDecorations()
}

// 现代卫生间
const createModernBathroom = () => {
  if (!scene) return

  // 现代卫生间地板 - 黑色瓷砖
  const floorGeometry = new THREE.PlaneGeometry(8, 6)
  const floorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x1C1C1C, // 黑色
    roughness: 0.1,
    metalness: 0.3
  })
  const floor = new THREE.Mesh(floorGeometry, floorMaterial)
  floor.rotation.x = -Math.PI / 2
  floor.receiveShadow = true
  scene.add(floor)
  currentSceneObjects.push(floor)

  // 现代卫生间天花板 - 白色
  const ceilingGeometry = new THREE.PlaneGeometry(8, 6)
  const ceilingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF,
    roughness: 0.1,
    metalness: 0.0
  })
  const ceiling = new THREE.Mesh(ceilingGeometry, ceilingMaterial)
  ceiling.rotation.x = Math.PI / 2
  ceiling.position.y = 2.5
  scene.add(ceiling)
  currentSceneObjects.push(ceiling)

  // 现代卫生间墙壁 - 浅灰色瓷砖
  const wallMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xE0E0E0, // 浅灰色
    roughness: 0.1,
    metalness: 0.2
  })

  // 创建墙壁
  createBathroomWalls(wallMaterial, 8, 6, 2.5)

  // 添加现代卫生间设备
  addModernBathroomFixtures()
  
  // 添加现代卫生间装饰
  addModernBathroomDecorations()
}

// 北欧卫生间
const createNordicBathroom = () => {
  if (!scene) return

  // 北欧卫生间地板 - 浅色木纹瓷砖
  const floorGeometry = new THREE.PlaneGeometry(8, 6)
  const floorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xDEB887, // 浅棕色
    roughness: 0.3,
    metalness: 0.0
  })
  const floor = new THREE.Mesh(floorGeometry, floorMaterial)
  floor.rotation.x = -Math.PI / 2
  floor.receiveShadow = true
  scene.add(floor)
  currentSceneObjects.push(floor)

  // 北欧卫生间天花板 - 白色
  const ceilingGeometry = new THREE.PlaneGeometry(8, 6)
  const ceilingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF,
    roughness: 0.2,
    metalness: 0.0
  })
  const ceiling = new THREE.Mesh(ceilingGeometry, ceilingMaterial)
  ceiling.rotation.x = Math.PI / 2
  ceiling.position.y = 2.6
  scene.add(ceiling)
  currentSceneObjects.push(ceiling)

  // 北欧卫生间墙壁 - 米白色瓷砖
  const wallMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFAF0, // 米白色
    roughness: 0.2,
    metalness: 0.0
  })

  // 创建墙壁
  createBathroomWalls(wallMaterial, 8, 6, 2.6)

  // 添加北欧卫生间设备
  addNordicBathroomFixtures()
  
  // 添加北欧卫生间装饰
  addNordicBathroomDecorations()
}

// 通用卫生间墙壁创建函数
const createBathroomWalls = (wallMaterial: THREE.MeshStandardMaterial, width: number, depth: number, height: number) => {
  // 后墙
  const backWallGeometry = new THREE.PlaneGeometry(width, height)
  const backWall = new THREE.Mesh(backWallGeometry, wallMaterial)
  backWall.position.z = -depth / 2
  backWall.position.y = height / 2
  scene.add(backWall)
  currentSceneObjects.push(backWall)

  // 左墙
  const leftWallGeometry = new THREE.PlaneGeometry(depth, height)
  const leftWall = new THREE.Mesh(leftWallGeometry, wallMaterial)
  leftWall.rotation.y = Math.PI / 2
  leftWall.position.x = -width / 2
  leftWall.position.y = height / 2
  scene.add(leftWall)
  currentSceneObjects.push(leftWall)

  // 右墙
  const rightWallGeometry = new THREE.PlaneGeometry(depth, height)
  const rightWall = new THREE.Mesh(rightWallGeometry, wallMaterial)
  rightWall.rotation.y = -Math.PI / 2
  rightWall.position.x = width / 2
  rightWall.position.y = height / 2
  scene.add(rightWall)
  currentSceneObjects.push(rightWall)

  // 前墙（部分，留出入口）
  const frontWallLeftGeometry = new THREE.PlaneGeometry(width / 3, height)
  const frontWallLeft = new THREE.Mesh(frontWallLeftGeometry, wallMaterial)
  frontWallLeft.position.z = depth / 2
  frontWallLeft.position.x = -width / 3
  frontWallLeft.position.y = height / 2
  scene.add(frontWallLeft)
  currentSceneObjects.push(frontWallLeft)

  const frontWallRightGeometry = new THREE.PlaneGeometry(width / 3, height)
  const frontWallRight = new THREE.Mesh(frontWallRightGeometry, wallMaterial)
  frontWallRight.position.z = depth / 2
  frontWallRight.position.x = width / 3
  frontWallRight.position.y = height / 2
  scene.add(frontWallRight)
  currentSceneObjects.push(frontWallRight)
}

// 创建厨房环境
const createKitchenEnvironment = () => {
  if (!scene) return

  // 厨房地板 - 深色瓷砖
  const floorGeometry = new THREE.PlaneGeometry(10, 8)
  const floorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.2,
    metalness: 0.3
  })
  const floor = new THREE.Mesh(floorGeometry, floorMaterial)
  floor.rotation.x = -Math.PI / 2
  floor.receiveShadow = true
  scene.add(floor)
  currentSceneObjects.push(floor)

  // 厨房天花板 - 白色
  const ceilingGeometry = new THREE.PlaneGeometry(10, 8)
  const ceilingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF,
    roughness: 0.1,
    metalness: 0.0
  })
  const ceiling = new THREE.Mesh(ceilingGeometry, ceilingMaterial)
  ceiling.rotation.x = Math.PI / 2
  ceiling.position.y = 2.8
  scene.add(ceiling)
  currentSceneObjects.push(ceiling)

  // 厨房墙壁 - 米白色
  const wallMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF5F5DC, // 米白色
    roughness: 0.3,
    metalness: 0.1
  })

  // 后墙
  const backWallGeometry = new THREE.PlaneGeometry(10, 2.8)
  const backWall = new THREE.Mesh(backWallGeometry, wallMaterial)
  backWall.position.z = -4
  backWall.position.y = 1.4
  scene.add(backWall)
  currentSceneObjects.push(backWall)

  // 左墙
  const leftWallGeometry = new THREE.PlaneGeometry(8, 2.8)
  const leftWall = new THREE.Mesh(leftWallGeometry, wallMaterial)
  leftWall.rotation.y = Math.PI / 2
  leftWall.position.x = -5
  leftWall.position.y = 1.4
  scene.add(leftWall)
  currentSceneObjects.push(leftWall)

  // 右墙
  const rightWallGeometry = new THREE.PlaneGeometry(8, 2.8)
  const rightWall = new THREE.Mesh(rightWallGeometry, wallMaterial)
  rightWall.rotation.y = -Math.PI / 2
  rightWall.position.x = 5
  rightWall.position.y = 1.4
  scene.add(rightWall)
  currentSceneObjects.push(rightWall)

  // 前墙（部分）
  const frontWallLeftGeometry = new THREE.PlaneGeometry(3, 2.8)
  const frontWallLeft = new THREE.Mesh(frontWallLeftGeometry, wallMaterial)
  frontWallLeft.position.z = 4
  frontWallLeft.position.x = -3.5
  frontWallLeft.position.y = 1.4
  scene.add(frontWallLeft)
  currentSceneObjects.push(frontWallLeft)

  const frontWallRightGeometry = new THREE.PlaneGeometry(3, 2.8)
  const frontWallRight = new THREE.Mesh(frontWallRightGeometry, wallMaterial)
  frontWallRight.position.z = 4
  frontWallRight.position.x = 3.5
  frontWallRight.position.y = 1.4
  scene.add(frontWallRight)
  currentSceneObjects.push(frontWallRight)

  // 添加厨房设备
  addKitchenAppliances()
}

// 添加中式客厅家具
const addChineseLivingRoomFurniture = () => {
  if (!scene) return

  // 中式红木沙发 - 更复杂的几何体
  const sofaGroup = new THREE.Group()
  
  // 沙发主体
  const sofaGeometry = new THREE.BoxGeometry(4, 0.6, 1.2)
  const sofaMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色
    roughness: 0.8,
    metalness: 0.0
  })
  const sofa = new THREE.Mesh(sofaGeometry, sofaMaterial)
  sofa.position.set(0, 0.3, -3)
  sofa.castShadow = true
  sofa.receiveShadow = true
  sofaGroup.add(sofa)

  // 沙发靠背 - 带弧度
  const sofaBackGeometry = new THREE.BoxGeometry(4, 1.2, 0.15)
  const sofaBack = new THREE.Mesh(sofaBackGeometry, sofaMaterial)
  sofaBack.position.set(0, 1.2, -3.5)
  sofaBack.castShadow = true
  sofaGroup.add(sofaBack)

  // 沙发扶手 - 中式雕花效果
  const armrestGeometry = new THREE.BoxGeometry(0.8, 0.6, 1.2)
  const armrest = new THREE.Mesh(armrestGeometry, sofaMaterial)
  armrest.position.set(-2.4, 0.3, -3)
  armrest.castShadow = true
  sofaGroup.add(armrest)

  const armrest2 = new THREE.Mesh(armrestGeometry, sofaMaterial)
  armrest2.position.set(2.4, 0.3, -3)
  armrest2.castShadow = true
  sofaGroup.add(armrest2)

  // 沙发腿 - 中式风格
  for (let i = 0; i < 6; i++) {
    const legGeometry = new THREE.CylinderGeometry(0.08, 0.12, 0.3, 8)
    const leg = new THREE.Mesh(legGeometry, sofaMaterial)
    const positions = [
      { x: -1.8, z: -2.4 },
      { x: 0, z: -2.4 },
      { x: 1.8, z: -2.4 },
      { x: -1.8, z: -3.6 },
      { x: 0, z: -3.6 },
      { x: 1.8, z: -3.6 }
    ]
    leg.position.set(positions[i].x, 0.15, positions[i].z)
    leg.castShadow = true
    sofaGroup.add(leg)
  }

  // 沙发靠垫 - 中式刺绣效果
  for (let i = 0; i < 3; i++) {
    const cushionGeometry = new THREE.BoxGeometry(0.8, 0.15, 0.4)
    const cushionMaterial = new THREE.MeshStandardMaterial({ 
      color: 0xFFD700, // 金色刺绣
      roughness: 0.9,
      metalness: 0.0
    })
    const cushion = new THREE.Mesh(cushionGeometry, cushionMaterial)
    cushion.position.set(-1.2 + i * 1.2, 0.9, -3.5)
    cushion.castShadow = true
    sofaGroup.add(cushion)
  }

  scene.add(sofaGroup)
  currentSceneObjects.push(sofaGroup)

  // 中式茶几 - 更精美的设计
  const teaTableGroup = new THREE.Group()
  
  // 茶几桌面
  const teaTableGeometry = new THREE.BoxGeometry(1.8, 0.08, 1.0)
  const teaTableMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色
    roughness: 0.7,
    metalness: 0.0
  })
  const teaTable = new THREE.Mesh(teaTableGeometry, teaTableMaterial)
  teaTable.position.set(0, 0.04, -1)
  teaTable.castShadow = true
  teaTable.receiveShadow = true
  teaTableGroup.add(teaTable)

  // 茶几腿 - 中式雕花柱
  for (let i = 0; i < 4; i++) {
    const legGeometry = new THREE.CylinderGeometry(0.08, 0.12, 0.08, 12)
    const leg = new THREE.Mesh(legGeometry, teaTableMaterial)
    const positions = [
      { x: 0.7, z: 0.4 },
      { x: -0.7, z: 0.4 },
      { x: 0.7, z: -0.4 },
      { x: -0.7, z: -0.4 }
    ]
    leg.position.set(positions[i].x, 0.04, positions[i].z)
    leg.castShadow = true
    teaTableGroup.add(leg)
  }

  // 茶几装饰边框
  const borderGeometry = new THREE.BoxGeometry(1.85, 0.02, 1.05)
  const borderMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.3,
    metalness: 0.8
  })
  const border = new THREE.Mesh(borderGeometry, borderMaterial)
  border.position.set(0, 0.09, -1)
  teaTableGroup.add(border)

  scene.add(teaTableGroup)
  currentSceneObjects.push(teaTableGroup)

  // 中式茶具
  const teapotGeometry = new THREE.SphereGeometry(0.15, 8, 6)
  const teapotMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.3,
    metalness: 0.2
  })
  const teapot = new THREE.Mesh(teapotGeometry, teapotMaterial)
  teapot.position.set(0, 0.2, -1)
  scene.add(teapot)
  currentSceneObjects.push(teapot)

  // 中式屏风
  const screenGeometry = new THREE.BoxGeometry(0.05, 2.5, 1.5)
  const screenMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.6,
    metalness: 0.0
  })
  const screen = new THREE.Mesh(screenGeometry, screenMaterial)
  screen.position.set(-8, 1.25, -6)
  scene.add(screen)
  currentSceneObjects.push(screen)

  // 屏风装饰画
  const paintingGeometry = new THREE.BoxGeometry(0.02, 1.8, 1.2)
  const paintingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.8,
    metalness: 0.0
  })
  const painting = new THREE.Mesh(paintingGeometry, paintingMaterial)
  painting.position.set(-7.9, 1.25, -6)
  scene.add(painting)
  currentSceneObjects.push(painting)

  // 中式花瓶
  const vaseGeometry = new THREE.CylinderGeometry(0.1, 0.15, 0.4, 8)
  const vaseMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.4,
    metalness: 0.1
  })
  const vase = new THREE.Mesh(vaseGeometry, vaseMaterial)
  vase.position.set(6, 0.2, -2)
  scene.add(vase)
  currentSceneObjects.push(vase)

  // 花瓶中的梅花
  for (let i = 0; i < 5; i++) {
    const branchGeometry = new THREE.CylinderGeometry(0.01, 0.01, 0.3, 8)
    const branchMaterial = new THREE.MeshStandardMaterial({ 
      color: 0x8B4513, // 棕色
      roughness: 0.8,
      metalness: 0.0
    })
    const branch = new THREE.Mesh(branchGeometry, branchMaterial)
    branch.position.set(6 + (Math.random() - 0.5) * 0.1, 0.4 + i * 0.05, -2 + (Math.random() - 0.5) * 0.1)
    branch.rotation.z = (Math.random() - 0.5) * 0.5
    scene.add(branch)
    currentSceneObjects.push(branch)

    // 梅花
    const flowerGeometry = new THREE.SphereGeometry(0.02, 6, 4)
    const flowerMaterial = new THREE.MeshStandardMaterial({ 
      color: 0xFF69B4, // 粉红色
      roughness: 0.8,
      metalness: 0.0
    })
    const flower = new THREE.Mesh(flowerGeometry, flowerMaterial)
    flower.position.set(6 + (Math.random() - 0.5) * 0.1, 0.5 + i * 0.05, -2 + (Math.random() - 0.5) * 0.1)
    scene.add(flower)
    currentSceneObjects.push(flower)
  }

  // 中式地毯
  const carpetGeometry = new THREE.PlaneGeometry(6, 4)
  const carpetMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色
    roughness: 0.9,
    metalness: 0.0
  })
  const carpet = new THREE.Mesh(carpetGeometry, carpetMaterial)
  carpet.rotation.x = -Math.PI / 2
  carpet.position.set(0, 0.01, -2)
  carpet.receiveShadow = true
  scene.add(carpet)
  currentSceneObjects.push(carpet)

  // 地毯装饰图案
  const patternGeometry = new THREE.PlaneGeometry(5, 3)
  const patternMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.8,
    metalness: 0.0
  })
  const pattern = new THREE.Mesh(patternGeometry, patternMaterial)
  pattern.rotation.x = -Math.PI / 2
  pattern.position.set(0, 0.02, -2)
  scene.add(pattern)
  currentSceneObjects.push(pattern)
}

// 添加欧式客厅家具
const addEuropeanLivingRoomFurniture = () => {
  if (!scene) return

  // 欧式沙发
  const sofaGeometry = new THREE.BoxGeometry(4, 0.8, 1.5)
  const sofaMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.7,
    metalness: 0.0
  })
  const sofa = new THREE.Mesh(sofaGeometry, sofaMaterial)
  sofa.position.set(0, 0.4, -3)
  sofa.castShadow = true
  sofa.receiveShadow = true
  scene.add(sofa)
  currentSceneObjects.push(sofa)

  // 沙发扶手
  const armrestGeometry = new THREE.BoxGeometry(1, 0.8, 1.5)
  const armrest = new THREE.Mesh(armrestGeometry, sofaMaterial)
  armrest.position.set(-2.5, 0.4, -1.5)
  armrest.castShadow = true
  armrest.receiveShadow = true
  scene.add(armrest)
  currentSceneObjects.push(armrest)

  // 欧式茶几
  const coffeeTableGeometry = new THREE.BoxGeometry(2, 0.1, 1)
  const coffeeTableMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.6,
    metalness: 0.1
  })
  const coffeeTable = new THREE.Mesh(coffeeTableGeometry, coffeeTableMaterial)
  coffeeTable.position.set(0, 0.05, -1)
  coffeeTable.castShadow = true
  coffeeTable.receiveShadow = true
  scene.add(coffeeTable)
  currentSceneObjects.push(coffeeTable)

  // 茶几腿
  for (let i = 0; i < 4; i++) {
    const legGeometry = new THREE.CylinderGeometry(0.05, 0.05, 0.1, 8)
    const leg = new THREE.Mesh(legGeometry, coffeeTableMaterial)
    const positions = [
      { x: 0.8, z: 0.4 },
      { x: -0.8, z: 0.4 },
      { x: 0.8, z: -0.4 },
      { x: -0.8, z: -0.4 }
    ]
    leg.position.set(positions[i].x, 0.05, positions[i].z)
    leg.castShadow = true
    scene.add(leg)
    currentSceneObjects.push(leg)
  }

  // 欧式壁炉
  const fireplaceGeometry = new THREE.BoxGeometry(2, 2, 1)
  const fireplaceMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.3,
    metalness: 0.2
  })
  const fireplace = new THREE.Mesh(fireplaceGeometry, fireplaceMaterial)
  fireplace.position.set(0, 1, -6.5)
  fireplace.castShadow = true
  fireplace.receiveShadow = true
  scene.add(fireplace)
  currentSceneObjects.push(fireplace)

  // 壁炉装饰
  const fireplaceDecorGeometry = new THREE.BoxGeometry(1.8, 0.1, 0.8)
  const fireplaceDecorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.2,
    metalness: 0.8
  })
  const fireplaceDecor = new THREE.Mesh(fireplaceDecorGeometry, fireplaceDecorMaterial)
  fireplaceDecor.position.set(0, 1.5, -6.9)
  scene.add(fireplaceDecor)
  currentSceneObjects.push(fireplaceDecor)

  // 欧式吊灯
  const chandelierGeometry = new THREE.SphereGeometry(0.3, 8, 6)
  const chandelierMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.2,
    metalness: 0.8
  })
  const chandelier = new THREE.Mesh(chandelierGeometry, chandelierMaterial)
  chandelier.position.set(0, 2.8, 0)
  scene.add(chandelier)
  currentSceneObjects.push(chandelier)

  // 吊灯链条
  const chainGeometry = new THREE.CylinderGeometry(0.01, 0.01, 0.5, 8)
  const chain = new THREE.Mesh(chainGeometry, chandelierMaterial)
  chain.position.set(0, 2.5, 0)
  scene.add(chain)
  currentSceneObjects.push(chain)

  // 欧式地毯
  const carpetGeometry = new THREE.PlaneGeometry(6, 4)
  const carpetMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.9,
    metalness: 0.0
  })
  const carpet = new THREE.Mesh(carpetGeometry, carpetMaterial)
  carpet.rotation.x = -Math.PI / 2
  carpet.position.set(0, 0.01, -2)
  carpet.receiveShadow = true
  scene.add(carpet)
  currentSceneObjects.push(carpet)
}

// 添加现代客厅家具
const addModernLivingRoomFurniture = () => {
  if (!scene) return

  // 现代沙发
  const sofaGeometry = new THREE.BoxGeometry(4, 0.4, 1.5)
  const sofaMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.3,
    metalness: 0.1
  })
  const sofa = new THREE.Mesh(sofaGeometry, sofaMaterial)
  sofa.position.set(0, 0.2, -3)
  sofa.castShadow = true
  sofa.receiveShadow = true
  scene.add(sofa)
  currentSceneObjects.push(sofa)

  // 现代茶几
  const coffeeTableGeometry = new THREE.BoxGeometry(2, 0.05, 1)
  const coffeeTableMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x000000, // 黑色
    roughness: 0.1,
    metalness: 0.8
  })
  const coffeeTable = new THREE.Mesh(coffeeTableGeometry, coffeeTableMaterial)
  coffeeTable.position.set(0, 0.025, -1)
  coffeeTable.castShadow = true
  coffeeTable.receiveShadow = true
  scene.add(coffeeTable)
  currentSceneObjects.push(coffeeTable)

  // 茶几支撑
  const supportGeometry = new THREE.CylinderGeometry(0.03, 0.03, 0.05, 8)
  const support = new THREE.Mesh(supportGeometry, coffeeTableMaterial)
  support.position.set(0, 0.025, -1)
  scene.add(support)
  currentSceneObjects.push(support)

  // 现代电视柜
  const tvStandGeometry = new THREE.BoxGeometry(3, 0.3, 0.6)
  const tvStandMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.3,
    metalness: 0.2
  })
  const tvStand = new THREE.Mesh(tvStandGeometry, tvStandMaterial)
  tvStand.position.set(0, 0.15, -6.5)
  tvStand.castShadow = true
  tvStand.receiveShadow = true
  scene.add(tvStand)
  currentSceneObjects.push(tvStand)

  // 现代电视
  const tvGeometry = new THREE.BoxGeometry(2.5, 1.5, 0.1)
  const tvMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x000000, // 黑色
    roughness: 0.1,
    metalness: 0.8
  })
  const tv = new THREE.Mesh(tvGeometry, tvMaterial)
  tv.position.set(0, 1.2, -6.9)
  tv.castShadow = true
  scene.add(tv)
  currentSceneObjects.push(tv)

  // 现代地毯
  const carpetGeometry = new THREE.PlaneGeometry(6, 4)
  const carpetMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.9,
    metalness: 0.0
  })
  const carpet = new THREE.Mesh(carpetGeometry, carpetMaterial)
  carpet.rotation.x = -Math.PI / 2
  carpet.position.set(0, 0.01, -2)
  carpet.receiveShadow = true
  scene.add(carpet)
  currentSceneObjects.push(carpet)
}

// 添加北欧客厅家具
const addNordicLivingRoomFurniture = () => {
  if (!scene) return

  // 北欧沙发
  const sofaGeometry = new THREE.BoxGeometry(4, 0.5, 1.5)
  const sofaMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF5F5DC, // 米白色
    roughness: 0.8,
    metalness: 0.0
  })
  const sofa = new THREE.Mesh(sofaGeometry, sofaMaterial)
  sofa.position.set(0, 0.25, -3)
  sofa.castShadow = true
  sofa.receiveShadow = true
  scene.add(sofa)
  currentSceneObjects.push(sofa)

  // 北欧茶几
  const coffeeTableGeometry = new THREE.BoxGeometry(1.5, 0.08, 0.8)
  const coffeeTableMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xDEB887, // 浅棕色
    roughness: 0.6,
    metalness: 0.0
  })
  const coffeeTable = new THREE.Mesh(coffeeTableGeometry, coffeeTableMaterial)
  coffeeTable.position.set(0, 0.04, -1)
  coffeeTable.castShadow = true
  coffeeTable.receiveShadow = true
  scene.add(coffeeTable)
  currentSceneObjects.push(coffeeTable)

  // 茶几腿
  for (let i = 0; i < 4; i++) {
    const legGeometry = new THREE.CylinderGeometry(0.03, 0.03, 0.08, 8)
    const leg = new THREE.Mesh(legGeometry, coffeeTableMaterial)
    const positions = [
      { x: 0.6, z: 0.3 },
      { x: -0.6, z: 0.3 },
      { x: 0.6, z: -0.3 },
      { x: -0.6, z: -0.3 }
    ]
    leg.position.set(positions[i].x, 0.04, positions[i].z)
    leg.castShadow = true
    scene.add(leg)
    currentSceneObjects.push(leg)
  }

  // 北欧地毯
  const carpetGeometry = new THREE.PlaneGeometry(6, 4)
  const carpetMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF5F5DC, // 米白色
    roughness: 0.9,
    metalness: 0.0
  })
  const carpet = new THREE.Mesh(carpetGeometry, carpetMaterial)
  carpet.rotation.x = -Math.PI / 2
  carpet.position.set(0, 0.01, -2)
  carpet.receiveShadow = true
  scene.add(carpet)
  currentSceneObjects.push(carpet)

  // 北欧绿植
  const plantPotGeometry = new THREE.CylinderGeometry(0.15, 0.2, 0.3, 8)
  const plantPotMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.7,
    metalness: 0.0
  })
  const plantPot = new THREE.Mesh(plantPotGeometry, plantPotMaterial)
  plantPot.position.set(6, 0.15, -2)
  scene.add(plantPot)
  currentSceneObjects.push(plantPot)

  // 植物叶子
  const leavesGeometry = new THREE.SphereGeometry(0.4, 8, 6)
  const leavesMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x228B22, // 绿色
    roughness: 0.8,
    metalness: 0.0
  })
  const leaves = new THREE.Mesh(leavesGeometry, leavesMaterial)
  leaves.position.set(6, 0.6, -2)
  scene.add(leaves)
  currentSceneObjects.push(leaves)
}

// 添加中式卧室家具
const addChineseBedroomFurniture = () => {
  if (!scene) return

  // 中式床 - 红木雕花床
  const bedGroup = new THREE.Group()
  
  // 床架主体
  const bedGeometry = new THREE.BoxGeometry(3.2, 0.6, 2.2)
  const bedMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色
    roughness: 0.8,
    metalness: 0.0
  })
  const bed = new THREE.Mesh(bedGeometry, bedMaterial)
  bed.position.set(0, 0.3, -2)
  bed.castShadow = true
  bed.receiveShadow = true
  bedGroup.add(bed)

  // 床腿 - 中式雕花柱
  for (let i = 0; i < 4; i++) {
    const legGeometry = new THREE.CylinderGeometry(0.1, 0.15, 0.6, 12)
    const leg = new THREE.Mesh(legGeometry, bedMaterial)
    const positions = [
      { x: 1.4, z: -1.0 },
      { x: -1.4, z: -1.0 },
      { x: 1.4, z: -3.0 },
      { x: -1.4, z: -3.0 }
    ]
    leg.position.set(positions[i].x, 0.3, positions[i].z)
    leg.castShadow = true
    bedGroup.add(leg)
  }

  // 床头板 - 中式雕花
  const headboardGeometry = new THREE.BoxGeometry(3.2, 1.5, 0.2)
  const headboard = new THREE.Mesh(headboardGeometry, bedMaterial)
  headboard.position.set(0, 1.35, -3.1)
  headboard.castShadow = true
  bedGroup.add(headboard)

  // 床头板装饰
  const decorGeometry = new THREE.BoxGeometry(2.8, 0.1, 0.05)
  const decorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.2,
    metalness: 0.8
  })
  const decor = new THREE.Mesh(decorGeometry, decorMaterial)
  decor.position.set(0, 1.4, -3.15)
  bedGroup.add(decor)

  // 床垫
  const mattressGeometry = new THREE.BoxGeometry(3.0, 0.2, 2.0)
  const mattressMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFE4E1, // 浅粉色
    roughness: 0.9,
    metalness: 0.0
  })
  const mattress = new THREE.Mesh(mattressGeometry, mattressMaterial)
  mattress.position.set(0, 0.7, -2)
  mattress.castShadow = true
  mattress.receiveShadow = true
  bedGroup.add(mattress)

  // 枕头
  const pillowGeometry = new THREE.BoxGeometry(0.8, 0.3, 0.6)
  const pillowMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.8,
    metalness: 0.0
  })
  const pillow = new THREE.Mesh(pillowGeometry, pillowMaterial)
  pillow.position.set(0, 0.9, -1.2)
  pillow.castShadow = true
  bedGroup.add(pillow)

  // 被子 - 中式刺绣
  const quiltGeometry = new THREE.BoxGeometry(2.8, 0.1, 1.8)
  const quiltMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色
    roughness: 0.8,
    metalness: 0.0
  })
  const quilt = new THREE.Mesh(quiltGeometry, quiltMaterial)
  quilt.position.set(0, 0.85, -2.2)
  quilt.castShadow = true
  bedGroup.add(quilt)

  // 被子装饰图案
  const patternGeometry = new THREE.BoxGeometry(2.6, 0.02, 1.6)
  const patternMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.8,
    metalness: 0.0
  })
  const pattern = new THREE.Mesh(patternGeometry, patternMaterial)
  pattern.position.set(0, 0.86, -2.2)
  bedGroup.add(pattern)

  scene.add(bedGroup)
  currentSceneObjects.push(bedGroup)

  // 中式衣柜 - 红木雕花
  const wardrobeGroup = new THREE.Group()
  
  const wardrobeGeometry = new THREE.BoxGeometry(2, 2.5, 0.8)
  const wardrobeMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色
    roughness: 0.8,
    metalness: 0.0
  })
  const wardrobe = new THREE.Mesh(wardrobeGeometry, wardrobeMaterial)
  wardrobe.position.set(-4, 1.25, -2)
  wardrobe.castShadow = true
  wardrobe.receiveShadow = true
  wardrobeGroup.add(wardrobe)

  // 衣柜门 - 中式雕花
  const doorGeometry = new THREE.BoxGeometry(0.05, 2.3, 0.7)
  const door = new THREE.Mesh(doorGeometry, wardrobeMaterial)
  door.position.set(-4.4, 1.15, -2)
  door.castShadow = true
  wardrobeGroup.add(door)

  // 衣柜装饰
  const wardrobeDecorGeometry = new THREE.BoxGeometry(1.8, 0.1, 0.05)
  const wardrobeDecor = new THREE.Mesh(wardrobeDecorGeometry, decorMaterial)
  wardrobeDecor.position.set(-4, 1.8, -2.4)
  wardrobeGroup.add(wardrobeDecor)

  scene.add(wardrobeGroup)
  currentSceneObjects.push(wardrobeGroup)

  // 中式梳妆台
  const dresserGroup = new THREE.Group()
  
  const dresserGeometry = new THREE.BoxGeometry(2.5, 0.8, 0.8)
  const dresserMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色
    roughness: 0.8,
    metalness: 0.0
  })
  const dresser = new THREE.Mesh(dresserGeometry, dresserMaterial)
  dresser.position.set(3, 0.4, -3)
  dresser.castShadow = true
  dresser.receiveShadow = true
  dresserGroup.add(dresser)

  // 梳妆台腿
  for (let i = 0; i < 4; i++) {
    const dresserLegGeometry = new THREE.CylinderGeometry(0.08, 0.12, 0.8, 12)
    const dresserLeg = new THREE.Mesh(dresserLegGeometry, dresserMaterial)
    const positions = [
      { x: 2.2, z: -2.6 },
      { x: -2.2, z: -2.6 },
      { x: 2.2, z: -3.4 },
      { x: -2.2, z: -3.4 }
    ]
    dresserLeg.position.set(positions[i].x, 0.4, positions[i].z)
    dresserLeg.castShadow = true
    dresserGroup.add(dresserLeg)
  }

  // 梳妆镜 - 中式镜框
  const mirrorGeometry = new THREE.BoxGeometry(1.8, 1.2, 0.1)
  const mirrorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色
    roughness: 0.6,
    metalness: 0.0
  })
  const mirror = new THREE.Mesh(mirrorGeometry, mirrorMaterial)
  mirror.position.set(3, 1.4, -3.5)
  dresserGroup.add(mirror)

  // 镜面
  const mirrorGlassGeometry = new THREE.BoxGeometry(1.6, 1.0, 0.02)
  const mirrorGlassMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x000000, // 黑色
    roughness: 0.0,
    metalness: 1.0
  })
  const mirrorGlass = new THREE.Mesh(mirrorGlassGeometry, mirrorGlassMaterial)
  mirrorGlass.position.set(3, 1.4, -3.55)
  dresserGroup.add(mirrorGlass)

  scene.add(dresserGroup)
  currentSceneObjects.push(dresserGroup)

  // 中式床头柜
  const nightstandGroup = new THREE.Group()
  
  const nightstandGeometry = new THREE.BoxGeometry(0.8, 0.6, 0.6)
  const nightstandMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色
    roughness: 0.8,
    metalness: 0.0
  })
  const nightstand = new THREE.Mesh(nightstandGeometry, nightstandMaterial)
  nightstand.position.set(-2.5, 0.3, -1.5)
  nightstand.castShadow = true
  nightstand.receiveShadow = true
  nightstandGroup.add(nightstand)

  // 床头柜腿
  for (let i = 0; i < 4; i++) {
    const nightstandLegGeometry = new THREE.CylinderGeometry(0.06, 0.1, 0.6, 12)
    const nightstandLeg = new THREE.Mesh(nightstandLegGeometry, nightstandMaterial)
    const positions = [
      { x: -2.2, z: -1.2 },
      { x: -2.8, z: -1.2 },
      { x: -2.2, z: -1.8 },
      { x: -2.8, z: -1.8 }
    ]
    nightstandLeg.position.set(positions[i].x, 0.3, positions[i].z)
    nightstandLeg.castShadow = true
    nightstandGroup.add(nightstandLeg)
  }

  // 床头柜装饰
  const nightstandDecorGeometry = new THREE.BoxGeometry(0.7, 0.05, 0.05)
  const nightstandDecor = new THREE.Mesh(nightstandDecorGeometry, decorMaterial)
  nightstandDecor.position.set(-2.5, 0.65, -1.5)
  nightstandGroup.add(nightstandDecor)

  scene.add(nightstandGroup)
  currentSceneObjects.push(nightstandGroup)

  // 中式台灯
  const lampGroup = new THREE.Group()
  
  const lampBaseGeometry = new THREE.CylinderGeometry(0.1, 0.15, 0.8, 12)
  const lampBaseMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色
    roughness: 0.8,
    metalness: 0.0
  })
  const lampBase = new THREE.Mesh(lampBaseGeometry, lampBaseMaterial)
  lampBase.position.set(-2.5, 0.4, -1.5)
  lampBase.castShadow = true
  lampGroup.add(lampBase)

  // 灯罩 - 中式设计
  const lampShadeGeometry = new THREE.CylinderGeometry(0.4, 0.3, 0.5, 12)
  const lampShadeMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.8,
    metalness: 0.0,
    emissive: 0x332211
  })
  const lampShade = new THREE.Mesh(lampShadeGeometry, lampShadeMaterial)
  lampShade.position.set(-2.5, 0.9, -1.5)
  lampGroup.add(lampShade)

  scene.add(lampGroup)
  currentSceneObjects.push(lampGroup)

  // 中式地毯
  const carpetGeometry = new THREE.PlaneGeometry(4, 3)
  const carpetMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色
    roughness: 0.9,
    metalness: 0.0
  })
  const carpet = new THREE.Mesh(carpetGeometry, carpetMaterial)
  carpet.rotation.x = -Math.PI / 2
  carpet.position.set(0, 0.01, -2)
  carpet.receiveShadow = true
  scene.add(carpet)
  currentSceneObjects.push(carpet)

  // 地毯装饰图案
  const carpetPatternGeometry = new THREE.PlaneGeometry(3.5, 2.5)
  const carpetPatternMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.8,
    metalness: 0.0
  })
  const carpetPattern = new THREE.Mesh(carpetPatternGeometry, carpetPatternMaterial)
  carpetPattern.rotation.x = -Math.PI / 2
  carpetPattern.position.set(0, 0.02, -2)
  scene.add(carpetPattern)
  currentSceneObjects.push(carpetPattern)

  // 中式装饰画 - 山水画
  const paintingGeometry = new THREE.BoxGeometry(1.5, 1, 0.05)
  const paintingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色画框
    roughness: 0.6,
    metalness: 0.0
  })
  const painting = new THREE.Mesh(paintingGeometry, paintingMaterial)
  painting.position.set(0, 2.0, -4.9)
  scene.add(painting)
  currentSceneObjects.push(painting)

  // 画框内容
  const canvasGeometry = new THREE.BoxGeometry(1.3, 0.8, 0.02)
  const canvasMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x87CEEB, // 天蓝色
    roughness: 0.8,
    metalness: 0.0
  })
  const canvas = new THREE.Mesh(canvasGeometry, canvasMaterial)
  canvas.position.set(0, 2.0, -4.92)
  scene.add(canvas)
  currentSceneObjects.push(canvas)
}

// 添加欧式卧室家具
const addEuropeanBedroomFurniture = () => {
  if (!scene) return

  // 欧式床 - 古典四柱床
  const bedGroup = new THREE.Group()
  
  // 床架主体
  const bedGeometry = new THREE.BoxGeometry(3.5, 0.8, 2.5)
  const bedMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 深棕色
    roughness: 0.7,
    metalness: 0.1
  })
  const bed = new THREE.Mesh(bedGeometry, bedMaterial)
  bed.position.set(0, 0.4, -2)
  bed.castShadow = true
  bed.receiveShadow = true
  bedGroup.add(bed)

  // 四柱床柱
  for (let i = 0; i < 4; i++) {
    const postGeometry = new THREE.CylinderGeometry(0.12, 0.15, 2.8, 12)
    const post = new THREE.Mesh(postGeometry, bedMaterial)
    const positions = [
      { x: 1.6, z: -0.8 },
      { x: -1.6, z: -0.8 },
      { x: 1.6, z: -3.2 },
      { x: -1.6, z: -3.2 }
    ]
    post.position.set(positions[i].x, 1.4, positions[i].z)
    post.castShadow = true
    bedGroup.add(post)
  }

  // 床头板 - 欧式雕花
  const headboardGeometry = new THREE.BoxGeometry(3.5, 1.8, 0.3)
  const headboard = new THREE.Mesh(headboardGeometry, bedMaterial)
  headboard.position.set(0, 1.7, -3.35)
  headboard.castShadow = true
  bedGroup.add(headboard)

  // 床头板装饰
  const headboardDecorGeometry = new THREE.BoxGeometry(3.3, 0.1, 0.05)
  const headboardDecorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.2,
    metalness: 0.8
  })
  const headboardDecor = new THREE.Mesh(headboardDecorGeometry, headboardDecorMaterial)
  headboardDecor.position.set(0, 1.8, -3.4)
  bedGroup.add(headboardDecor)

  // 床垫
  const mattressGeometry = new THREE.BoxGeometry(3.2, 0.25, 2.2)
  const mattressMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF5F5DC, // 米白色
    roughness: 0.9,
    metalness: 0.0
  })
  const mattress = new THREE.Mesh(mattressGeometry, mattressMaterial)
  mattress.position.set(0, 0.8, -2)
  mattress.castShadow = true
  mattress.receiveShadow = true
  bedGroup.add(mattress)

  // 枕头
  const pillowGeometry = new THREE.BoxGeometry(0.9, 0.4, 0.7)
  const pillowMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFE4E1, // 浅粉色
    roughness: 0.9,
    metalness: 0.0
  })
  const pillow = new THREE.Mesh(pillowGeometry, pillowMaterial)
  pillow.position.set(0, 1.0, -1.2)
  pillow.castShadow = true
  bedGroup.add(pillow)

  // 被子 - 欧式刺绣
  const quiltGeometry = new THREE.BoxGeometry(3.0, 0.15, 2.0)
  const quiltMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 深棕色
    roughness: 0.8,
    metalness: 0.0
  })
  const quilt = new THREE.Mesh(quiltGeometry, quiltMaterial)
  quilt.position.set(0, 0.95, -2.2)
  quilt.castShadow = true
  bedGroup.add(quilt)

  // 被子装饰图案
  const patternGeometry = new THREE.BoxGeometry(2.8, 0.02, 1.8)
  const patternMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.8,
    metalness: 0.0
  })
  const pattern = new THREE.Mesh(patternGeometry, patternMaterial)
  pattern.position.set(0, 0.96, -2.2)
  bedGroup.add(pattern)

  scene.add(bedGroup)
  currentSceneObjects.push(bedGroup)

  // 欧式衣柜 - 古典设计
  const wardrobeGroup = new THREE.Group()
  
  const wardrobeGeometry = new THREE.BoxGeometry(2.2, 2.8, 0.9)
  const wardrobeMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 深棕色
    roughness: 0.7,
    metalness: 0.1
  })
  const wardrobe = new THREE.Mesh(wardrobeGeometry, wardrobeMaterial)
  wardrobe.position.set(-4.5, 1.4, -2)
  wardrobe.castShadow = true
  wardrobe.receiveShadow = true
  wardrobeGroup.add(wardrobe)

  // 衣柜门 - 欧式雕花
  const doorGeometry = new THREE.BoxGeometry(0.08, 2.6, 0.8)
  const door = new THREE.Mesh(doorGeometry, wardrobeMaterial)
  door.position.set(-4.9, 1.3, -2)
  door.castShadow = true
  wardrobeGroup.add(door)

  // 衣柜装饰
  const wardrobeDecorGeometry = new THREE.BoxGeometry(2.0, 0.1, 0.05)
  const wardrobeDecor = new THREE.Mesh(wardrobeDecorGeometry, headboardDecorMaterial)
  wardrobeDecor.position.set(-4.5, 2.0, -2.45)
  wardrobeGroup.add(wardrobeDecor)

  scene.add(wardrobeGroup)
  currentSceneObjects.push(wardrobeGroup)

  // 欧式梳妆台
  const dresserGroup = new THREE.Group()
  
  const dresserGeometry = new THREE.BoxGeometry(2.8, 0.9, 0.9)
  const dresserMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 深棕色
    roughness: 0.7,
    metalness: 0.1
  })
  const dresser = new THREE.Mesh(dresserGeometry, dresserMaterial)
  dresser.position.set(3.5, 0.45, -3)
  dresser.castShadow = true
  dresser.receiveShadow = true
  dresserGroup.add(dresser)

  // 梳妆台腿
  for (let i = 0; i < 4; i++) {
    const dresserLegGeometry = new THREE.CylinderGeometry(0.1, 0.15, 0.9, 12)
    const dresserLeg = new THREE.Mesh(dresserLegGeometry, dresserMaterial)
    const positions = [
      { x: 2.4, z: -2.5 },
      { x: -2.4, z: -2.5 },
      { x: 2.4, z: -3.5 },
      { x: -2.4, z: -3.5 }
    ]
    dresserLeg.position.set(positions[i].x, 0.45, positions[i].z)
    dresserLeg.castShadow = true
    dresserGroup.add(dresserLeg)
  }

  // 梳妆镜 - 欧式镜框
  const mirrorGeometry = new THREE.BoxGeometry(2.0, 1.4, 0.15)
  const mirror = new THREE.Mesh(mirrorGeometry, dresserMaterial)
  mirror.position.set(3.5, 1.6, -3.6)
  dresserGroup.add(mirror)

  // 镜面
  const mirrorGlassGeometry = new THREE.BoxGeometry(1.8, 1.2, 0.02)
  const mirrorGlassMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x000000, // 黑色
    roughness: 0.0,
    metalness: 1.0
  })
  const mirrorGlass = new THREE.Mesh(mirrorGlassGeometry, mirrorGlassMaterial)
  mirrorGlass.position.set(3.5, 1.6, -3.65)
  dresserGroup.add(mirrorGlass)

  scene.add(dresserGroup)
  currentSceneObjects.push(dresserGroup)

  // 欧式床头柜
  const nightstandGroup = new THREE.Group()
  
  const nightstandGeometry = new THREE.BoxGeometry(0.9, 0.7, 0.7)
  const nightstandMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 深棕色
    roughness: 0.7,
    metalness: 0.1
  })
  const nightstand = new THREE.Mesh(nightstandGeometry, nightstandMaterial)
  nightstand.position.set(-2.8, 0.35, -1.5)
  nightstand.castShadow = true
  nightstand.receiveShadow = true
  nightstandGroup.add(nightstand)

  // 床头柜腿
  for (let i = 0; i < 4; i++) {
    const nightstandLegGeometry = new THREE.CylinderGeometry(0.08, 0.12, 0.7, 12)
    const nightstandLeg = new THREE.Mesh(nightstandLegGeometry, nightstandMaterial)
    const positions = [
      { x: -2.4, z: -1.2 },
      { x: -3.2, z: -1.2 },
      { x: -2.4, z: -1.8 },
      { x: -3.2, z: -1.8 }
    ]
    nightstandLeg.position.set(positions[i].x, 0.35, positions[i].z)
    nightstandLeg.castShadow = true
    nightstandGroup.add(nightstandLeg)
  }

  // 床头柜装饰
  const nightstandDecorGeometry = new THREE.BoxGeometry(0.8, 0.05, 0.05)
  const nightstandDecor = new THREE.Mesh(nightstandDecorGeometry, headboardDecorMaterial)
  nightstandDecor.position.set(-2.8, 0.75, -1.5)
  nightstandGroup.add(nightstandDecor)

  scene.add(nightstandGroup)
  currentSceneObjects.push(nightstandGroup)

  // 欧式台灯
  const lampGroup = new THREE.Group()
  
  const lampBaseGeometry = new THREE.CylinderGeometry(0.12, 0.18, 1.0, 12)
  const lampBaseMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 深棕色
    roughness: 0.7,
    metalness: 0.1
  })
  const lampBase = new THREE.Mesh(lampBaseGeometry, lampBaseMaterial)
  lampBase.position.set(-2.8, 0.5, -1.5)
  lampBase.castShadow = true
  lampGroup.add(lampBase)

  // 灯罩 - 欧式设计
  const lampShadeGeometry = new THREE.CylinderGeometry(0.5, 0.4, 0.6, 12)
  const lampShadeMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.8,
    metalness: 0.0,
    emissive: 0x443311
  })
  const lampShade = new THREE.Mesh(lampShadeGeometry, lampShadeMaterial)
  lampShade.position.set(-2.8, 1.1, -1.5)
  lampGroup.add(lampShade)

  scene.add(lampGroup)
  currentSceneObjects.push(lampGroup)

  // 欧式地毯
  const carpetGeometry = new THREE.PlaneGeometry(4.5, 3.5)
  const carpetMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 深棕色
    roughness: 0.9,
    metalness: 0.0
  })
  const carpet = new THREE.Mesh(carpetGeometry, carpetMaterial)
  carpet.rotation.x = -Math.PI / 2
  carpet.position.set(0, 0.01, -2)
  carpet.receiveShadow = true
  scene.add(carpet)
  currentSceneObjects.push(carpet)

  // 地毯装饰图案
  const carpetPatternGeometry = new THREE.PlaneGeometry(4.0, 3.0)
  const carpetPatternMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.8,
    metalness: 0.0
  })
  const carpetPattern = new THREE.Mesh(carpetPatternGeometry, carpetPatternMaterial)
  carpetPattern.rotation.x = -Math.PI / 2
  carpetPattern.position.set(0, 0.02, -2)
  scene.add(carpetPattern)
  currentSceneObjects.push(carpetPattern)

  // 欧式装饰画 - 古典油画
  const paintingGeometry = new THREE.BoxGeometry(1.8, 1.2, 0.08)
  const paintingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 深棕色画框
    roughness: 0.6,
    metalness: 0.1
  })
  const painting = new THREE.Mesh(paintingGeometry, paintingMaterial)
  painting.position.set(0, 2.2, -4.9)
  scene.add(painting)
  currentSceneObjects.push(painting)

  // 画框内容
  const canvasGeometry = new THREE.BoxGeometry(1.6, 1.0, 0.02)
  const canvasMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 深棕色
    roughness: 0.8,
    metalness: 0.0
  })
  const canvas = new THREE.Mesh(canvasGeometry, canvasMaterial)
  canvas.position.set(0, 2.2, -4.92)
  scene.add(canvas)
  currentSceneObjects.push(canvas)
}

// 添加现代卧室家具
const addModernBedroomFurniture = () => {
  if (!scene) return

  // 现代床 - 极简设计
  const bedGroup = new THREE.Group()
  
  // 床架主体 - 低矮设计
  const bedGeometry = new THREE.BoxGeometry(3.2, 0.3, 2.2)
  const bedMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.3,
    metalness: 0.2
  })
  const bed = new THREE.Mesh(bedGeometry, bedMaterial)
  bed.position.set(0, 0.15, -2)
  bed.castShadow = true
  bed.receiveShadow = true
  bedGroup.add(bed)

  // 床头板 - 现代简约
  const headboardGeometry = new THREE.BoxGeometry(3.2, 1.2, 0.1)
  const headboard = new THREE.Mesh(headboardGeometry, bedMaterial)
  headboard.position.set(0, 0.75, -3.15)
  headboard.castShadow = true
  bedGroup.add(headboard)

  // 床垫
  const mattressGeometry = new THREE.BoxGeometry(3.0, 0.2, 2.0)
  const mattressMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF, // 白色
    roughness: 0.8,
    metalness: 0.0
  })
  const mattress = new THREE.Mesh(mattressGeometry, mattressMaterial)
  mattress.position.set(0, 0.35, -2)
  mattress.castShadow = true
  mattress.receiveShadow = true
  bedGroup.add(mattress)

  // 枕头
  const pillowGeometry = new THREE.BoxGeometry(0.8, 0.25, 0.6)
  const pillowMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF0F0F0, // 浅灰色
    roughness: 0.8,
    metalness: 0.0
  })
  const pillow = new THREE.Mesh(pillowGeometry, pillowMaterial)
  pillow.position.set(0, 0.6, -1.2)
  pillow.castShadow = true
  bedGroup.add(pillow)

  // 被子 - 现代简约
  const quiltGeometry = new THREE.BoxGeometry(2.8, 0.08, 1.8)
  const quiltMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.8,
    metalness: 0.0
  })
  const quilt = new THREE.Mesh(quiltGeometry, quiltMaterial)
  quilt.position.set(0, 0.58, -2.2)
  quilt.castShadow = true
  bedGroup.add(quilt)

  scene.add(bedGroup)
  currentSceneObjects.push(bedGroup)

  // 现代衣柜 - 极简设计
  const wardrobeGroup = new THREE.Group()
  
  const wardrobeGeometry = new THREE.BoxGeometry(2.5, 2.5, 0.6)
  const wardrobeMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.3,
    metalness: 0.2
  })
  const wardrobe = new THREE.Mesh(wardrobeGeometry, wardrobeMaterial)
  wardrobe.position.set(-4.5, 1.25, -2)
  wardrobe.castShadow = true
  wardrobe.receiveShadow = true
  wardrobeGroup.add(wardrobe)

  // 衣柜门 - 现代简约
  const doorGeometry = new THREE.BoxGeometry(0.05, 2.4, 0.5)
  const door = new THREE.Mesh(doorGeometry, wardrobeMaterial)
  door.position.set(-4.8, 1.25, -2)
  door.castShadow = true
  wardrobeGroup.add(door)

  // 现代把手
  const handleGeometry = new THREE.BoxGeometry(0.02, 0.15, 0.01)
  const handleMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xC0C0C0, // 银色
    roughness: 0.1,
    metalness: 0.9
  })
  const handle = new THREE.Mesh(handleGeometry, handleMaterial)
  handle.position.set(-4.75, 1.25, -1.7)
  wardrobeGroup.add(handle)

  scene.add(wardrobeGroup)
  currentSceneObjects.push(wardrobeGroup)

  // 现代梳妆台
  const dresserGroup = new THREE.Group()
  
  const dresserGeometry = new THREE.BoxGeometry(2.5, 0.6, 0.8)
  const dresserMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.3,
    metalness: 0.2
  })
  const dresser = new THREE.Mesh(dresserGeometry, dresserMaterial)
  dresser.position.set(3.5, 0.3, -3)
  dresser.castShadow = true
  dresser.receiveShadow = true
  dresserGroup.add(dresser)

  // 梳妆镜 - 现代简约
  const mirrorGeometry = new THREE.BoxGeometry(1.8, 1.0, 0.05)
  const mirror = new THREE.Mesh(mirrorGeometry, dresserMaterial)
  mirror.position.set(3.5, 1.1, -3.4)
  dresserGroup.add(mirror)

  // 镜面
  const mirrorGlassGeometry = new THREE.BoxGeometry(1.6, 0.8, 0.02)
  const mirrorGlassMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x000000, // 黑色
    roughness: 0.0,
    metalness: 1.0
  })
  const mirrorGlass = new THREE.Mesh(mirrorGlassGeometry, mirrorGlassMaterial)
  mirrorGlass.position.set(3.5, 1.1, -3.42)
  dresserGroup.add(mirrorGlass)

  scene.add(dresserGroup)
  currentSceneObjects.push(dresserGroup)

  // 现代床头柜
  const nightstandGroup = new THREE.Group()
  
  const nightstandGeometry = new THREE.BoxGeometry(0.8, 0.4, 0.6)
  const nightstandMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.3,
    metalness: 0.2
  })
  const nightstand = new THREE.Mesh(nightstandGeometry, nightstandMaterial)
  nightstand.position.set(-2.5, 0.2, -1.5)
  nightstand.castShadow = true
  nightstand.receiveShadow = true
  nightstandGroup.add(nightstand)

  scene.add(nightstandGroup)
  currentSceneObjects.push(nightstandGroup)

  // 现代台灯
  const lampGroup = new THREE.Group()
  
  const lampBaseGeometry = new THREE.CylinderGeometry(0.08, 0.08, 0.6, 12)
  const lampBaseMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.3,
    metalness: 0.2
  })
  const lampBase = new THREE.Mesh(lampBaseGeometry, lampBaseMaterial)
  lampBase.position.set(-2.5, 0.3, -1.5)
  lampBase.castShadow = true
  lampGroup.add(lampBase)

  // 灯罩 - 现代设计
  const lampShadeGeometry = new THREE.CylinderGeometry(0.3, 0.3, 0.4, 12)
  const lampShadeMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF, // 白色
    roughness: 0.8,
    metalness: 0.0,
    emissive: 0x111111
  })
  const lampShade = new THREE.Mesh(lampShadeGeometry, lampShadeMaterial)
  lampShade.position.set(-2.5, 0.7, -1.5)
  lampGroup.add(lampShade)

  scene.add(lampGroup)
  currentSceneObjects.push(lampGroup)

  // 现代地毯
  const carpetGeometry = new THREE.PlaneGeometry(4, 3)
  const carpetMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.9,
    metalness: 0.0
  })
  const carpet = new THREE.Mesh(carpetGeometry, carpetMaterial)
  carpet.rotation.x = -Math.PI / 2
  carpet.position.set(0, 0.01, -2)
  carpet.receiveShadow = true
  scene.add(carpet)
  currentSceneObjects.push(carpet)

  // 现代装饰画 - 抽象艺术
  const paintingGeometry = new THREE.BoxGeometry(1.2, 0.8, 0.05)
  const paintingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色画框
    roughness: 0.3,
    metalness: 0.2
  })
  const painting = new THREE.Mesh(paintingGeometry, paintingMaterial)
  painting.position.set(0, 1.8, -4.9)
  scene.add(painting)
  currentSceneObjects.push(painting)

  // 画框内容
  const canvasGeometry = new THREE.BoxGeometry(1.0, 0.6, 0.02)
  const canvasMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x000000, // 黑色
    roughness: 0.8,
    metalness: 0.0
  })
  const canvas = new THREE.Mesh(canvasGeometry, canvasMaterial)
  canvas.position.set(0, 1.8, -4.92)
  scene.add(canvas)
  currentSceneObjects.push(canvas)
}

// 添加北欧卧室家具
const addNordicBedroomFurniture = () => {
  if (!scene) return

  // 北欧床 - 简约自然
  const bedGroup = new THREE.Group()
  
  // 床架主体 - 原木色
  const bedGeometry = new THREE.BoxGeometry(3.2, 0.5, 2.2)
  const bedMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xDEB887, // 浅棕色
    roughness: 0.7,
    metalness: 0.0
  })
  const bed = new THREE.Mesh(bedGeometry, bedMaterial)
  bed.position.set(0, 0.25, -2)
  bed.castShadow = true
  bed.receiveShadow = true
  bedGroup.add(bed)

  // 床头板 - 北欧简约
  const headboardGeometry = new THREE.BoxGeometry(3.2, 1.0, 0.15)
  const headboard = new THREE.Mesh(headboardGeometry, bedMaterial)
  headboard.position.set(0, 0.75, -3.1)
  headboard.castShadow = true
  bedGroup.add(headboard)

  // 床垫
  const mattressGeometry = new THREE.BoxGeometry(3.0, 0.2, 2.0)
  const mattressMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF, // 白色
    roughness: 0.8,
    metalness: 0.0
  })
  const mattress = new THREE.Mesh(mattressGeometry, mattressMaterial)
  mattress.position.set(0, 0.5, -2)
  mattress.castShadow = true
  mattress.receiveShadow = true
  bedGroup.add(mattress)

  // 枕头
  const pillowGeometry = new THREE.BoxGeometry(0.8, 0.3, 0.6)
  const pillowMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFAF0, // 米白色
    roughness: 0.8,
    metalness: 0.0
  })
  const pillow = new THREE.Mesh(pillowGeometry, pillowMaterial)
  pillow.position.set(0, 0.8, -1.2)
  pillow.castShadow = true
  bedGroup.add(pillow)

  // 被子 - 北欧风格
  const quiltGeometry = new THREE.BoxGeometry(2.8, 0.1, 1.8)
  const quiltMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFAF0, // 米白色
    roughness: 0.8,
    metalness: 0.0
  })
  const quilt = new THREE.Mesh(quiltGeometry, quiltMaterial)
  quilt.position.set(0, 0.75, -2.2)
  quilt.castShadow = true
  bedGroup.add(quilt)

  scene.add(bedGroup)
  currentSceneObjects.push(bedGroup)

  // 北欧衣柜 - 简约设计
  const wardrobeGroup = new THREE.Group()
  
  const wardrobeGeometry = new THREE.BoxGeometry(2.2, 2.6, 0.7)
  const wardrobeMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xDEB887, // 浅棕色
    roughness: 0.7,
    metalness: 0.0
  })
  const wardrobe = new THREE.Mesh(wardrobeGeometry, wardrobeMaterial)
  wardrobe.position.set(-4.5, 1.3, -2)
  wardrobe.castShadow = true
  wardrobe.receiveShadow = true
  wardrobeGroup.add(wardrobe)

  // 衣柜门 - 北欧简约
  const doorGeometry = new THREE.BoxGeometry(0.06, 2.5, 0.6)
  const door = new THREE.Mesh(doorGeometry, wardrobeMaterial)
  door.position.set(-4.8, 1.3, -2)
  door.castShadow = true
  wardrobeGroup.add(door)

  // 北欧把手
  const handleGeometry = new THREE.BoxGeometry(0.03, 0.12, 0.01)
  const handleMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.6,
    metalness: 0.0
  })
  const handle = new THREE.Mesh(handleGeometry, handleMaterial)
  handle.position.set(-4.75, 1.3, -1.7)
  wardrobeGroup.add(handle)

  scene.add(wardrobeGroup)
  currentSceneObjects.push(wardrobeGroup)

  // 北欧梳妆台
  const dresserGroup = new THREE.Group()
  
  const dresserGeometry = new THREE.BoxGeometry(2.5, 0.7, 0.8)
  const dresserMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xDEB887, // 浅棕色
    roughness: 0.7,
    metalness: 0.0
  })
  const dresser = new THREE.Mesh(dresserGeometry, dresserMaterial)
  dresser.position.set(3.5, 0.35, -3)
  dresser.castShadow = true
  dresser.receiveShadow = true
  dresserGroup.add(dresser)

  // 梳妆镜 - 北欧简约
  const mirrorGeometry = new THREE.BoxGeometry(1.8, 1.2, 0.08)
  const mirror = new THREE.Mesh(mirrorGeometry, dresserMaterial)
  mirror.position.set(3.5, 1.3, -3.4)
  dresserGroup.add(mirror)

  // 镜面
  const mirrorGlassGeometry = new THREE.BoxGeometry(1.6, 1.0, 0.02)
  const mirrorGlassMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x000000, // 黑色
    roughness: 0.0,
    metalness: 1.0
  })
  const mirrorGlass = new THREE.Mesh(mirrorGlassGeometry, mirrorGlassMaterial)
  mirrorGlass.position.set(3.5, 1.3, -3.42)
  dresserGroup.add(mirrorGlass)

  scene.add(dresserGroup)
  currentSceneObjects.push(dresserGroup)

  // 北欧床头柜
  const nightstandGroup = new THREE.Group()
  
  const nightstandGeometry = new THREE.BoxGeometry(0.8, 0.5, 0.6)
  const nightstandMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xDEB887, // 浅棕色
    roughness: 0.7,
    metalness: 0.0
  })
  const nightstand = new THREE.Mesh(nightstandGeometry, nightstandMaterial)
  nightstand.position.set(-2.5, 0.25, -1.5)
  nightstand.castShadow = true
  nightstand.receiveShadow = true
  nightstandGroup.add(nightstand)

  scene.add(nightstandGroup)
  currentSceneObjects.push(nightstandGroup)

  // 北欧台灯
  const lampGroup = new THREE.Group()
  
  const lampBaseGeometry = new THREE.CylinderGeometry(0.1, 0.12, 0.7, 12)
  const lampBaseMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xDEB887, // 浅棕色
    roughness: 0.7,
    metalness: 0.0
  })
  const lampBase = new THREE.Mesh(lampBaseGeometry, lampBaseMaterial)
  lampBase.position.set(-2.5, 0.35, -1.5)
  lampBase.castShadow = true
  lampGroup.add(lampBase)

  // 灯罩 - 北欧设计
  const lampShadeGeometry = new THREE.CylinderGeometry(0.4, 0.35, 0.5, 12)
  const lampShadeMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFAF0, // 米白色
    roughness: 0.8,
    metalness: 0.0,
    emissive: 0x221100
  })
  const lampShade = new THREE.Mesh(lampShadeGeometry, lampShadeMaterial)
  lampShade.position.set(-2.5, 0.85, -1.5)
  lampGroup.add(lampShade)

  scene.add(lampGroup)
  currentSceneObjects.push(lampGroup)

  // 北欧地毯
  const carpetGeometry = new THREE.PlaneGeometry(4, 3)
  const carpetMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFAF0, // 米白色
    roughness: 0.9,
    metalness: 0.0
  })
  const carpet = new THREE.Mesh(carpetGeometry, carpetMaterial)
  carpet.rotation.x = -Math.PI / 2
  carpet.position.set(0, 0.01, -2)
  carpet.receiveShadow = true
  scene.add(carpet)
  currentSceneObjects.push(carpet)

  // 北欧装饰画 - 自然风景
  const paintingGeometry = new THREE.BoxGeometry(1.4, 0.9, 0.05)
  const paintingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xDEB887, // 浅棕色画框
    roughness: 0.7,
    metalness: 0.0
  })
  const painting = new THREE.Mesh(paintingGeometry, paintingMaterial)
  painting.position.set(0, 1.9, -4.9)
  scene.add(painting)
  currentSceneObjects.push(painting)

  // 画框内容
  const canvasGeometry = new THREE.BoxGeometry(1.2, 0.7, 0.02)
  const canvasMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x87CEEB, // 天蓝色
    roughness: 0.8,
    metalness: 0.0
  })
  const canvas = new THREE.Mesh(canvasGeometry, canvasMaterial)
  canvas.position.set(0, 1.9, -4.92)
  scene.add(canvas)
  currentSceneObjects.push(canvas)

  // 北欧绿植
  const plantGroup = new THREE.Group()
  
  const plantPotGeometry = new THREE.CylinderGeometry(0.2, 0.25, 0.4, 8)
  const plantPotMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.7,
    metalness: 0.0
  })
  const plantPot = new THREE.Mesh(plantPotGeometry, plantPotMaterial)
  plantPot.position.set(5, 0.2, -2)
  plantGroup.add(plantPot)

  // 植物叶子
  const leavesGeometry = new THREE.SphereGeometry(0.5, 8, 6)
  const leavesMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x228B22, // 绿色
    roughness: 0.8,
    metalness: 0.0
  })
  const leaves = new THREE.Mesh(leavesGeometry, leavesMaterial)
  leaves.position.set(5, 0.7, -2)
  plantGroup.add(leaves)

  scene.add(plantGroup)
  currentSceneObjects.push(plantGroup)
}

// 添加中式卫生间设备
const addChineseBathroomFixtures = () => {
  if (!scene) return

  // 中式马桶 - 深色陶瓷
  const toiletGroup = new THREE.Group()
  
  const toiletBaseGeometry = new THREE.CylinderGeometry(0.4, 0.4, 0.4, 16)
  const toiletMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.1,
    metalness: 0.2
  })
  const toiletBase = new THREE.Mesh(toiletBaseGeometry, toiletMaterial)
  toiletBase.position.set(-2, 0.2, -1.5)
  toiletBase.castShadow = true
  toiletBase.receiveShadow = true
  toiletGroup.add(toiletBase)

  // 马桶水箱 - 中式装饰
  const toiletTankGeometry = new THREE.BoxGeometry(0.6, 0.4, 0.3)
  const toiletTank = new THREE.Mesh(toiletTankGeometry, toiletMaterial)
  toiletTank.position.set(-2, 0.6, -1.2)
  toiletTank.castShadow = true
  toiletGroup.add(toiletTank)

  // 中式装饰边框
  const decorGeometry = new THREE.BoxGeometry(0.65, 0.05, 0.35)
  const decorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.2,
    metalness: 0.8
  })
  const decor = new THREE.Mesh(decorGeometry, decorMaterial)
  decor.position.set(-2, 0.8, -1.2)
  toiletGroup.add(decor)

  scene.add(toiletGroup)
  currentSceneObjects.push(toiletGroup)

  // 马桶盖
  const toiletSeatGeometry = new THREE.CylinderGeometry(0.4, 0.4, 0.05, 16)
  const toiletSeat = new THREE.Mesh(toiletSeatGeometry, toiletMaterial)
  toiletSeat.position.set(-2, 0.45, -1.5)
  scene.add(toiletSeat)
  currentSceneObjects.push(toiletSeat)

  // 洗手台
  const sinkGeometry = new THREE.BoxGeometry(1.5, 0.8, 0.6)
  const sinkMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF, // 白色
    roughness: 0.1,
    metalness: 0.0
  })
  const sink = new THREE.Mesh(sinkGeometry, sinkMaterial)
  sink.position.set(2, 0.4, -1.5)
  sink.castShadow = true
  sink.receiveShadow = true
  scene.add(sink)
  currentSceneObjects.push(sink)

  // 洗手台台面
  const countertopGeometry = new THREE.BoxGeometry(1.8, 0.1, 0.8)
  const countertopMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF, // 白色
    roughness: 0.1,
    metalness: 0.0
  })
  const countertop = new THREE.Mesh(countertopGeometry, countertopMaterial)
  countertop.position.set(2, 0.85, -1.5)
  countertop.castShadow = true
  scene.add(countertop)
  currentSceneObjects.push(countertop)

  // 水龙头
  const faucetGeometry = new THREE.CylinderGeometry(0.05, 0.05, 0.2, 8)
  const faucetMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xC0C0C0, // 银色
    roughness: 0.1,
    metalness: 0.9
  })
  const faucet = new THREE.Mesh(faucetGeometry, faucetMaterial)
  faucet.position.set(2, 1.0, -1.5)
  scene.add(faucet)
  currentSceneObjects.push(faucet)

  // 淋浴房 - 干湿分离设计
  const showerWallGeometry = new THREE.BoxGeometry(0.05, 2.5, 2)
  const showerWallMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF, // 白色
    roughness: 0.1,
    metalness: 0.0,
    transparent: true,
    opacity: 0.3
  })
  const leftShowerWall = new THREE.Mesh(showerWallGeometry, showerWallMaterial)
  leftShowerWall.position.set(-1.5, 1.25, 1)
  scene.add(leftShowerWall)
  currentSceneObjects.push(leftShowerWall)

  const rightShowerWall = new THREE.Mesh(showerWallGeometry, showerWallMaterial)
  rightShowerWall.position.set(-2.5, 1.25, 1)
  scene.add(rightShowerWall)
  currentSceneObjects.push(rightShowerWall)

  const backShowerWall = new THREE.Mesh(showerWallGeometry, showerWallMaterial)
  backShowerWall.rotation.y = Math.PI / 2
  backShowerWall.position.set(-2, 1.25, 2)
  scene.add(backShowerWall)
  currentSceneObjects.push(backShowerWall)

  // 淋浴头
  const showerHeadGeometry = new THREE.CylinderGeometry(0.15, 0.15, 0.1, 8)
  const showerHeadMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xC0C0C0, // 银色
    roughness: 0.1,
    metalness: 0.9
  })
  const showerHead = new THREE.Mesh(showerHeadGeometry, showerHeadMaterial)
  showerHead.position.set(-2, 2.2, 1)
  scene.add(showerHead)
  currentSceneObjects.push(showerHead)

  // 淋浴管
  const showerPipeGeometry = new THREE.CylinderGeometry(0.02, 0.02, 0.8, 8)
  const showerPipe = new THREE.Mesh(showerPipeGeometry, showerHeadMaterial)
  showerPipe.rotation.x = Math.PI / 2
  showerPipe.position.set(-2, 1.8, 1.5)
  scene.add(showerPipe)
  currentSceneObjects.push(showerPipe)

  // 浴缸
  const bathtubGeometry = new THREE.BoxGeometry(1.8, 0.6, 0.8)
  const bathtubMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF, // 白色
    roughness: 0.1,
    metalness: 0.0
  })
  const bathtub = new THREE.Mesh(bathtubGeometry, bathtubMaterial)
  bathtub.position.set(2, 0.3, 1.5)
  bathtub.castShadow = true
  bathtub.receiveShadow = true
  scene.add(bathtub)
  currentSceneObjects.push(bathtub)

  // 浴缸内部
  const bathtubInsideGeometry = new THREE.BoxGeometry(1.6, 0.4, 0.6)
  const bathtubInsideMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x87CEEB, // 天蓝色（水）
    roughness: 0.0,
    metalness: 0.0,
    transparent: true,
    opacity: 0.8
  })
  const bathtubInside = new THREE.Mesh(bathtubInsideGeometry, bathtubInsideMaterial)
  bathtubInside.position.set(2, 0.5, 1.5)
  scene.add(bathtubInside)
  currentSceneObjects.push(bathtubInside)

  // 浴缸水龙头
  const bathtubFaucetGeometry = new THREE.CylinderGeometry(0.04, 0.04, 0.15, 8)
  const bathtubFaucet = new THREE.Mesh(bathtubFaucetGeometry, faucetMaterial)
  bathtubFaucet.position.set(2, 0.9, 1.2)
  scene.add(bathtubFaucet)
  currentSceneObjects.push(bathtubFaucet)

  // 毛巾架
  const towelRackGeometry = new THREE.CylinderGeometry(0.02, 0.02, 1.2, 8)
  const towelRackMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xC0C0C0, // 银色
    roughness: 0.1,
    metalness: 0.9
  })
  const towelRack = new THREE.Mesh(towelRackGeometry, towelRackMaterial)
  towelRack.rotation.z = Math.PI / 2
  towelRack.position.set(3.5, 1.5, 0)
  scene.add(towelRack)
  currentSceneObjects.push(towelRack)

  // 毛巾
  const towelGeometry = new THREE.BoxGeometry(0.8, 0.02, 0.4)
  const towelMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x87CEEB, // 天蓝色
    roughness: 0.8,
    metalness: 0.0
  })
  const towel = new THREE.Mesh(towelGeometry, towelMaterial)
  towel.position.set(3.5, 1.5, 0)
  scene.add(towel)
  currentSceneObjects.push(towel)

  // 储物架
  const shelfGeometry = new THREE.BoxGeometry(1.2, 0.05, 0.3)
  const shelfMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.7,
    metalness: 0.0
  })
  const shelf1 = new THREE.Mesh(shelfGeometry, shelfMaterial)
  shelf1.position.set(3.5, 1.8, -0.5)
  scene.add(shelf1)
  currentSceneObjects.push(shelf1)

  const shelf2 = new THREE.Mesh(shelfGeometry, shelfMaterial)
  shelf2.position.set(3.5, 2.1, -0.5)
  scene.add(shelf2)
  currentSceneObjects.push(shelf2)

  // 储物架支撑
  for (let i = 0; i < 2; i++) {
    const supportGeometry = new THREE.BoxGeometry(0.05, 0.3, 0.05)
    const support = new THREE.Mesh(supportGeometry, shelfMaterial)
    support.position.set(3.5, 1.95, -0.5 + i * 0.35)
    scene.add(support)
    currentSceneObjects.push(support)
  }

  // 洗发水瓶
  const shampooGeometry = new THREE.CylinderGeometry(0.05, 0.05, 0.15, 8)
  const shampooMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFF69B4, // 粉红色
    roughness: 0.3,
    metalness: 0.7
  })
  const shampoo = new THREE.Mesh(shampooGeometry, shampooMaterial)
  shampoo.position.set(3.5, 1.95, -0.5)
  scene.add(shampoo)
  currentSceneObjects.push(shampoo)

  // 沐浴露瓶
  const soapGeometry = new THREE.CylinderGeometry(0.04, 0.04, 0.12, 8)
  const soapMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x87CEEB, // 天蓝色
    roughness: 0.3,
    metalness: 0.7
  })
  const soap = new THREE.Mesh(soapGeometry, soapMaterial)
  soap.position.set(3.5, 2.25, -0.5)
  scene.add(soap)
  currentSceneObjects.push(soap)

  // 马桶刷
  const brushGeometry = new THREE.CylinderGeometry(0.02, 0.02, 0.3, 8)
  const brushMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.7,
    metalness: 0.0
  })
  const brush = new THREE.Mesh(brushGeometry, brushMaterial)
  brush.position.set(-2.8, 0.3, -1.5)
  scene.add(brush)
  currentSceneObjects.push(brush)

  // 垃圾桶
  const trashCanGeometry = new THREE.CylinderGeometry(0.2, 0.2, 0.4, 8)
  const trashCanMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.3,
    metalness: 0.2
  })
  const trashCan = new THREE.Mesh(trashCanGeometry, trashCanMaterial)
  trashCan.position.set(-2.8, 0.2, -2.5)
  scene.add(trashCan)
  currentSceneObjects.push(trashCan)

  // 卫生间地毯
  const bathroomRugGeometry = new THREE.PlaneGeometry(1.5, 1)
  const bathroomRugMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x87CEEB, // 天蓝色
    roughness: 0.9,
    metalness: 0.0
  })
  const bathroomRug = new THREE.Mesh(bathroomRugGeometry, bathroomRugMaterial)
  bathroomRug.rotation.x = -Math.PI / 2
  bathroomRug.position.set(0, 0.01, -0.5)
  bathroomRug.receiveShadow = true
  scene.add(bathroomRug)
  currentSceneObjects.push(bathroomRug)
}

// 添加厨房设备
const addKitchenAppliances = () => {
  if (!scene) return

  // 橱柜 - 下柜
  const lowerCabinetGeometry = new THREE.BoxGeometry(2, 0.8, 0.6)
  const cabinetMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.6,
    metalness: 0.0
  })
  const lowerCabinet = new THREE.Mesh(lowerCabinetGeometry, cabinetMaterial)
  lowerCabinet.position.set(-3, 0.4, -3)
  lowerCabinet.castShadow = true
  lowerCabinet.receiveShadow = true
  scene.add(lowerCabinet)
  currentSceneObjects.push(lowerCabinet)

  // 橱柜门把手
  const cabinetHandleGeometry = new THREE.BoxGeometry(0.03, 0.08, 0.01)
  const cabinetHandleMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xC0C0C0, // 银色
    roughness: 0.1,
    metalness: 0.9
  })
  const cabinetHandle = new THREE.Mesh(cabinetHandleGeometry, cabinetHandleMaterial)
  cabinetHandle.position.set(-2.5, 0.4, -2.7)
  scene.add(cabinetHandle)
  currentSceneObjects.push(cabinetHandle)

  // 橱柜 - 上柜
  const upperCabinetGeometry = new THREE.BoxGeometry(2, 0.8, 0.4)
  const upperCabinet = new THREE.Mesh(upperCabinetGeometry, cabinetMaterial)
  upperCabinet.position.set(-3, 1.6, -3.2)
  upperCabinet.castShadow = true
  scene.add(upperCabinet)
  currentSceneObjects.push(upperCabinet)

  // 上柜门把手
  const upperCabinetHandle = new THREE.Mesh(cabinetHandleGeometry, cabinetHandleMaterial)
  upperCabinetHandle.position.set(-2.5, 1.6, -3)
  scene.add(upperCabinetHandle)
  currentSceneObjects.push(upperCabinetHandle)

  // 台面
  const counterGeometry = new THREE.BoxGeometry(2.2, 0.1, 0.8)
  const counterMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xF5F5DC, // 米白色
    roughness: 0.2,
    metalness: 0.1
  })
  const counter = new THREE.Mesh(counterGeometry, counterMaterial)
  counter.position.set(-3, 0.85, -3)
  counter.castShadow = true
  scene.add(counter)
  currentSceneObjects.push(counter)

  // 水槽
  const kitchenSinkGeometry = new THREE.BoxGeometry(0.8, 0.3, 0.6)
  const kitchenSinkMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xC0C0C0, // 银色
    roughness: 0.1,
    metalness: 0.9
  })
  const kitchenSink = new THREE.Mesh(kitchenSinkGeometry, kitchenSinkMaterial)
  kitchenSink.position.set(-3, 0.65, -3)
  scene.add(kitchenSink)
  currentSceneObjects.push(kitchenSink)

  // 水龙头
  const kitchenFaucetGeometry = new THREE.CylinderGeometry(0.05, 0.05, 0.3, 8)
  const kitchenFaucetMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xC0C0C0, // 银色
    roughness: 0.1,
    metalness: 0.9
  })
  const kitchenFaucet = new THREE.Mesh(kitchenFaucetGeometry, kitchenFaucetMaterial)
  kitchenFaucet.position.set(-3, 1.0, -3)
  scene.add(kitchenFaucet)
  currentSceneObjects.push(kitchenFaucet)

  // 微波炉
  const microwaveGeometry = new THREE.BoxGeometry(0.6, 0.4, 0.5)
  const microwaveMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.3,
    metalness: 0.2
  })
  const microwave = new THREE.Mesh(microwaveGeometry, microwaveMaterial)
  microwave.position.set(-1.5, 1.8, -3.1)
  microwave.castShadow = true
  scene.add(microwave)
  currentSceneObjects.push(microwave)

  // 微波炉门把手
  const microwaveHandleGeometry = new THREE.BoxGeometry(0.02, 0.1, 0.01)
  const microwaveHandle = new THREE.Mesh(microwaveHandleGeometry, cabinetHandleMaterial)
  microwaveHandle.position.set(-1.5, 1.8, -3.35)
  scene.add(microwaveHandle)
  currentSceneObjects.push(microwaveHandle)

  // 烤箱
  const ovenGeometry = new THREE.BoxGeometry(0.6, 0.8, 0.6)
  const ovenMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.3,
    metalness: 0.2
  })
  const oven = new THREE.Mesh(ovenGeometry, ovenMaterial)
  oven.position.set(-1.5, 0.4, -3)
  oven.castShadow = true
  scene.add(oven)
  currentSceneObjects.push(oven)

  // 烤箱门把手
  const ovenHandleGeometry = new THREE.BoxGeometry(0.02, 0.2, 0.01)
  const ovenHandle = new THREE.Mesh(ovenHandleGeometry, cabinetHandleMaterial)
  ovenHandle.position.set(-1.5, 0.4, -2.7)
  scene.add(ovenHandle)
  currentSceneObjects.push(ovenHandle)

  // 冰箱
  const fridgeGeometry = new THREE.BoxGeometry(0.8, 2.2, 0.8)
  const fridgeMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF, // 白色
    roughness: 0.2,
    metalness: 0.1
  })
  const fridge = new THREE.Mesh(fridgeGeometry, fridgeMaterial)
  fridge.position.set(4, 1.1, -3)
  fridge.castShadow = true
  fridge.receiveShadow = true
  scene.add(fridge)
  currentSceneObjects.push(fridge)

  // 冰箱把手
  const fridgeHandleGeometry = new THREE.BoxGeometry(0.05, 0.3, 0.02)
  const fridgeHandleMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xC0C0C0, // 银色
    roughness: 0.1,
    metalness: 0.9
  })
  const fridgeHandle = new THREE.Mesh(fridgeHandleGeometry, fridgeHandleMaterial)
  fridgeHandle.position.set(4.4, 1.1, -3)
  scene.add(fridgeHandle)
  currentSceneObjects.push(fridgeHandle)

  // 炉灶
  const stoveGeometry = new THREE.BoxGeometry(0.8, 0.8, 0.6)
  const stoveMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.3,
    metalness: 0.2
  })
  const stove = new THREE.Mesh(stoveGeometry, stoveMaterial)
  stove.position.set(1, 0.4, -3)
  stove.castShadow = true
  stove.receiveShadow = true
  scene.add(stove)
  currentSceneObjects.push(stove)

  // 炉灶台面
  const stoveTopGeometry = new THREE.BoxGeometry(0.8, 0.1, 0.6)
  const stoveTopMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x000000, // 黑色
    roughness: 0.1,
    metalness: 0.8
  })
  const stoveTop = new THREE.Mesh(stoveTopGeometry, stoveTopMaterial)
  stoveTop.position.set(1, 0.85, -3)
  scene.add(stoveTop)
  currentSceneObjects.push(stoveTop)

  // 炉灶旋钮
  for (let i = 0; i < 4; i++) {
    const knobGeometry = new THREE.CylinderGeometry(0.05, 0.05, 0.02, 8)
    const knobMaterial = new THREE.MeshStandardMaterial({ 
      color: 0xFFD700, // 金色
      roughness: 0.2,
      metalness: 0.8
    })
    const knob = new THREE.Mesh(knobGeometry, knobMaterial)
    const positions = [
      { x: 0.7, z: -2.7 },
      { x: 1.3, z: -2.7 },
      { x: 0.7, z: -3.3 },
      { x: 1.3, z: -3.3 }
    ]
    knob.position.set(positions[i].x, 0.9, positions[i].z)
    scene.add(knob)
    currentSceneObjects.push(knob)
  }

  // 调料架
  const spiceRackGeometry = new THREE.BoxGeometry(0.8, 0.05, 0.2)
  const spiceRackMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.6,
    metalness: 0.0
  })
  const spiceRack = new THREE.Mesh(spiceRackGeometry, spiceRackMaterial)
  spiceRack.position.set(-0.5, 2.0, -3.1)
  scene.add(spiceRack)
  currentSceneObjects.push(spiceRack)

  // 调料瓶
  for (let i = 0; i < 4; i++) {
    const spiceGeometry = new THREE.CylinderGeometry(0.03, 0.03, 0.08, 8)
    const spiceMaterial = new THREE.MeshStandardMaterial({ 
      color: new THREE.Color().setHSL(i * 0.25, 0.5, 0.5),
      roughness: 0.3,
      metalness: 0.7
    })
    const spice = new THREE.Mesh(spiceGeometry, spiceMaterial)
    spice.position.set(-0.8 + i * 0.2, 2.05, -3.1)
    scene.add(spice)
    currentSceneObjects.push(spice)
  }

  // 餐具架
  const utensilRackGeometry = new THREE.BoxGeometry(0.6, 0.05, 0.15)
  const utensilRackMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xC0C0C0, // 银色
    roughness: 0.1,
    metalness: 0.9
  })
  const utensilRack = new THREE.Mesh(utensilRackGeometry, utensilRackMaterial)
  utensilRack.position.set(0.5, 2.0, -3.1)
  scene.add(utensilRack)
  currentSceneObjects.push(utensilRack)

  // 餐具
  for (let i = 0; i < 3; i++) {
    const utensilGeometry = new THREE.BoxGeometry(0.01, 0.01, 0.12)
    const utensilMaterial = new THREE.MeshStandardMaterial({ 
      color: 0xC0C0C0, // 银色
      roughness: 0.1,
      metalness: 0.9
    })
    const utensil = new THREE.Mesh(utensilGeometry, utensilMaterial)
    utensil.position.set(0.5, 2.05, -3.1 + i * 0.05)
    scene.add(utensil)
    currentSceneObjects.push(utensil)
  }

  // 餐桌
  const diningTableGeometry = new THREE.BoxGeometry(2, 0.1, 1.2)
  const tableMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.6,
    metalness: 0.0
  })
  const diningTable = new THREE.Mesh(diningTableGeometry, tableMaterial)
  diningTable.position.set(0, 0.4, 2)
  diningTable.castShadow = true
  diningTable.receiveShadow = true
  scene.add(diningTable)
  currentSceneObjects.push(diningTable)

  // 餐桌腿
  for (let i = 0; i < 4; i++) {
    const tableLegGeometry = new THREE.CylinderGeometry(0.05, 0.05, 0.4, 8)
    const tableLeg = new THREE.Mesh(tableLegGeometry, tableMaterial)
    const positions = [
      { x: 0.8, z: 1.4 },
      { x: -0.8, z: 1.4 },
      { x: 0.8, z: 2.6 },
      { x: -0.8, z: 2.6 }
    ]
    tableLeg.position.set(positions[i].x, 0.2, positions[i].z)
    tableLeg.castShadow = true
    scene.add(tableLeg)
    currentSceneObjects.push(tableLeg)
  }

  // 餐桌上的盘子
  const plateGeometry = new THREE.CylinderGeometry(0.15, 0.15, 0.02, 8)
  const plateMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF, // 白色
    roughness: 0.1,
    metalness: 0.0
  })
  const plate = new THREE.Mesh(plateGeometry, plateMaterial)
  plate.position.set(0, 0.47, 2)
  scene.add(plate)
  currentSceneObjects.push(plate)

  // 餐桌上的杯子
  const cupGeometry = new THREE.CylinderGeometry(0.04, 0.04, 0.08, 8)
  const cupMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x87CEEB, // 天蓝色
    roughness: 0.1,
    metalness: 0.0
  })
  const cup = new THREE.Mesh(cupGeometry, cupMaterial)
  cup.position.set(0.3, 0.47, 2)
  scene.add(cup)
  currentSceneObjects.push(cup)

  // 椅子
  for (let i = 0; i < 4; i++) {
    const chairSeatGeometry = new THREE.BoxGeometry(0.6, 0.05, 0.6)
    const chairMaterial = new THREE.MeshStandardMaterial({ 
      color: 0x8B4513, // 棕色
      roughness: 0.7,
      metalness: 0.0
    })
    const chairSeat = new THREE.Mesh(chairSeatGeometry, chairMaterial)
    
    const positions = [
      { x: 0.8, z: 1.2 },
      { x: -0.8, z: 1.2 },
      { x: 0.8, z: 2.8 },
      { x: -0.8, z: 2.8 }
    ]
    
    chairSeat.position.set(positions[i].x, 0.425, positions[i].z)
    chairSeat.castShadow = true
    chairSeat.receiveShadow = true
    scene.add(chairSeat)
    currentSceneObjects.push(chairSeat)

    // 椅子靠背
    const chairBackGeometry = new THREE.BoxGeometry(0.6, 0.8, 0.05)
    const chairBack = new THREE.Mesh(chairBackGeometry, chairMaterial)
    chairBack.position.set(positions[i].x, 0.85, positions[i].z)
    chairBack.castShadow = true
    scene.add(chairBack)
    currentSceneObjects.push(chairBack)

    // 椅子腿
    for (let j = 0; j < 4; j++) {
      const chairLegGeometry = new THREE.CylinderGeometry(0.03, 0.03, 0.4, 8)
      const chairLeg = new THREE.Mesh(chairLegGeometry, chairMaterial)
      const legPositions = [
        { x: positions[i].x - 0.25, z: positions[i].z - 0.25 },
        { x: positions[i].x + 0.25, z: positions[i].z - 0.25 },
        { x: positions[i].x - 0.25, z: positions[i].z + 0.25 },
        { x: positions[i].x + 0.25, z: positions[i].z + 0.25 }
      ]
      chairLeg.position.set(legPositions[j].x, 0.2, legPositions[j].z)
      chairLeg.castShadow = true
      scene.add(chairLeg)
      currentSceneObjects.push(chairLeg)
    }
  }

  // 厨房地毯
  const kitchenRugGeometry = new THREE.PlaneGeometry(2, 1.5)
  const kitchenRugMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xDEB887, // 浅棕色
    roughness: 0.9,
    metalness: 0.0
  })
  const kitchenRug = new THREE.Mesh(kitchenRugGeometry, kitchenRugMaterial)
  kitchenRug.rotation.x = -Math.PI / 2
  kitchenRug.position.set(0, 0.01, 2)
  kitchenRug.receiveShadow = true
  scene.add(kitchenRug)
  currentSceneObjects.push(kitchenRug)
}

// 添加光照
const addLighting = () => {
  if (!scene) return

  // 环境光 - 根据风格调整颜色和强度
  let ambientColor = 0xffffff
  let ambientIntensity = 0.8
  
  switch (currentTheme.value) {
    case 'chinese':
      ambientColor = 0xFFF8DC // 温暖的米色
      ambientIntensity = 0.9
      break
    case 'european':
      ambientColor = 0xF5F5DC // 米白色
      ambientIntensity = 0.7
      break
    case 'modern':
      ambientColor = 0xF0F0F0 // 冷白色
      ambientIntensity = 1.0
      break
    case 'nordic':
      ambientColor = 0xFFFAF0 // 温暖的白色
      ambientIntensity = 0.8
      break
  }
  
  const ambientLight = new THREE.AmbientLight(ambientColor, ambientIntensity)
  scene.add(ambientLight)

  // 主方向光 - 根据风格调整
  let directionalColor = 0xffffff
  let directionalIntensity = 2.0
  
  switch (currentTheme.value) {
    case 'chinese':
      directionalColor = 0xFFF8DC // 温暖的米色
      directionalIntensity = 1.8
      break
    case 'european':
      directionalColor = 0xF5F5DC // 米白色
      directionalIntensity = 2.2
      break
    case 'modern':
      directionalColor = 0xFFFFFF // 纯白色
      directionalIntensity = 2.5
      break
    case 'nordic':
      directionalColor = 0xFFFAF0 // 温暖的白色
      directionalIntensity = 2.0
      break
  }
  
  const directionalLight = new THREE.DirectionalLight(directionalColor, directionalIntensity)
  directionalLight.position.set(10, 15, 8)
  directionalLight.castShadow = true
  directionalLight.shadow.mapSize.width = 4096
  directionalLight.shadow.mapSize.height = 4096
  directionalLight.shadow.camera.near = 0.5
  directionalLight.shadow.camera.far = 50
  directionalLight.shadow.camera.left = -15
  directionalLight.shadow.camera.right = 15
  directionalLight.shadow.camera.top = 15
  directionalLight.shadow.camera.bottom = -15
  directionalLight.shadow.bias = -0.0001
  scene.add(directionalLight)

  // 补光 - 根据风格调整
  let fillColor = 0x87CEEB
  let fillIntensity = 0.8
  
  switch (currentTheme.value) {
    case 'chinese':
      fillColor = 0xFFE4B5 // 温暖的黄色
      fillIntensity = 0.6
      break
    case 'european':
      fillColor = 0xF0E68C // 卡其色
      fillIntensity = 0.7
      break
    case 'modern':
      fillColor = 0xE0E0E0 // 冷灰色
      fillIntensity = 1.0
      break
    case 'nordic':
      fillColor = 0xE6F3FF // 淡蓝色
      fillIntensity = 0.8
      break
  }
  
  const fillLight = new THREE.DirectionalLight(fillColor, fillIntensity)
  fillLight.position.set(-10, 10, -8)
  scene.add(fillLight)

  // 点光源 - 根据风格调整
  let pointColor = 0xffffff
  let pointIntensity = 1.5
  
  switch (currentTheme.value) {
    case 'chinese':
      pointColor = 0xFFD700 // 金色
      pointIntensity = 1.2
      break
    case 'european':
      pointColor = 0xFFD700 // 金色
      pointIntensity = 1.8
      break
    case 'modern':
      pointColor = 0xFFFFFF // 白色
      pointIntensity = 2.0
      break
    case 'nordic':
      pointColor = 0xFFFAF0 // 温暖的白色
      pointIntensity = 1.5
      break
  }
  
  const pointLight1 = new THREE.PointLight(pointColor, pointIntensity, 25)
  pointLight1.position.set(0, 3, 0)
  pointLight1.castShadow = true
  pointLight1.shadow.mapSize.width = 1024
  pointLight1.shadow.mapSize.height = 1024
  scene.add(pointLight1)

  // 角落点光源
  const pointLight2 = new THREE.PointLight(pointColor, pointIntensity * 0.8, 20)
  pointLight2.position.set(8, 2, 8)
  scene.add(pointLight2)

  const pointLight3 = new THREE.PointLight(pointColor, pointIntensity * 0.8, 20)
  pointLight3.position.set(-8, 2, -8)
  scene.add(pointLight3)

  // 聚光灯效果 - 根据风格调整
  let spotColor = 0xffffff
  let spotIntensity = 2.0
  
  switch (currentTheme.value) {
    case 'chinese':
      spotColor = 0xFFD700 // 金色
      spotIntensity = 1.8
      break
    case 'european':
      spotColor = 0xFFD700 // 金色
      spotIntensity = 2.2
      break
    case 'modern':
      spotColor = 0xFFFFFF // 白色
      spotIntensity = 2.5
      break
    case 'nordic':
      spotColor = 0xFFFAF0 // 温暖的白色
      spotIntensity = 2.0
      break
  }
  
  const spotLight = new THREE.SpotLight(spotColor, spotIntensity, 35, Math.PI / 6, 0.3)
  spotLight.position.set(0, 8, 0)
  spotLight.target.position.set(0, 0, 0)
  spotLight.castShadow = true
  spotLight.shadow.mapSize.width = 2048
  spotLight.shadow.mapSize.height = 2048
  scene.add(spotLight)
  scene.add(spotLight.target)

  // 添加氛围光 - 根据风格
  if (currentTheme.value === 'chinese') {
    // 中式氛围光 - 红色调
    const atmosphereLight = new THREE.PointLight(0xFF6B6B, 0.5, 30)
    atmosphereLight.position.set(0, 2, 0)
    scene.add(atmosphereLight)
  } else if (currentTheme.value === 'european') {
    // 欧式氛围光 - 暖黄色
    const atmosphereLight = new THREE.PointLight(0xFFD700, 0.6, 30)
    atmosphereLight.position.set(0, 2, 0)
    scene.add(atmosphereLight)
  } else if (currentTheme.value === 'modern') {
    // 现代氛围光 - 冷白色
    const atmosphereLight = new THREE.PointLight(0xE0E0E0, 0.8, 30)
    atmosphereLight.position.set(0, 2, 0)
    scene.add(atmosphereLight)
  } else if (currentTheme.value === 'nordic') {
    // 北欧氛围光 - 淡蓝色
    const atmosphereLight = new THREE.PointLight(0xE6F3FF, 0.6, 30)
    atmosphereLight.position.set(0, 2, 0)
    scene.add(atmosphereLight)
  }
}

// 场景切换功能
const switchStyle = (newStyle: 'living_room' | 'bedroom' | 'bathroom' | 'kitchen') => {
  if (isTransitioning.value || currentStyle.value === newStyle) return
  
  isTransitioning.value = true
  
  // 切换场景
  currentStyle.value = newStyle
  
  // 重新创建场景
  createIndoorEnvironment()
  
  // 等待一段时间后重置过渡状态
  setTimeout(() => {
    isTransitioning.value = false
  }, 500)
}

// 风格切换功能
const switchTheme = (newTheme: 'chinese' | 'european' | 'modern' | 'nordic') => {
  if (isTransitioning.value || currentTheme.value === newTheme) return
  
  isTransitioning.value = true
  
  // 切换风格
  currentTheme.value = newTheme
  
  // 清除现有光照
  clearLighting()
  
  // 重新创建场景
  createIndoorEnvironment()
  
  // 重新添加光照
  addLighting()
  
  // 等待一段时间后重置过渡状态
  setTimeout(() => {
    isTransitioning.value = false
  }, 500)
}

// 清除光照
const clearLighting = () => {
  if (!scene) return
  
  // 获取所有光照对象
  const lights = scene.children.filter(child => child.type.includes('Light'))
  
  // 移除所有光照
  lights.forEach(light => {
    scene.remove(light)
  })
}

// 动画循环
const animate = () => {
  animationId = requestAnimationFrame(animate)

  // 更新控制器
  if (controls) {
    controls.update()
  }

  // 渲染场景
  if (renderer && scene && camera) {
    renderer.render(scene, camera)
  }
}

// 窗口大小调整
const onWindowResize = () => {
  if (!containerRef.value || !camera || !renderer) return

  camera.aspect = containerRef.value.clientWidth / containerRef.value.clientHeight
  camera.updateProjectionMatrix()
  renderer.setSize(containerRef.value.clientWidth, containerRef.value.clientHeight)
}

// 组件挂载
onMounted(() => {
  initScene()
  window.addEventListener('resize', onWindowResize)
})

// 组件卸载
onUnmounted(() => {
  if (animationId) {
    cancelAnimationFrame(animationId)
  }
  window.removeEventListener('resize', onWindowResize)
  if (renderer && containerRef.value && renderer.domElement) {
    containerRef.value.removeChild(renderer.domElement)
  }
})

// 添加欧式卫生间设备
const addEuropeanBathroomFixtures = () => {
  if (!scene) return

  // 欧式马桶 - 白色陶瓷
  const toiletGroup = new THREE.Group()
  
  const toiletBaseGeometry = new THREE.CylinderGeometry(0.4, 0.4, 0.4, 16)
  const toiletMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF, // 白色
    roughness: 0.1,
    metalness: 0.0
  })
  const toiletBase = new THREE.Mesh(toiletBaseGeometry, toiletMaterial)
  toiletBase.position.set(-2, 0.2, -1.5)
  toiletBase.castShadow = true
  toiletBase.receiveShadow = true
  toiletGroup.add(toiletBase)

  // 马桶水箱 - 欧式装饰
  const toiletTankGeometry = new THREE.BoxGeometry(0.6, 0.4, 0.3)
  const toiletTank = new THREE.Mesh(toiletTankGeometry, toiletMaterial)
  toiletTank.position.set(-2, 0.6, -1.2)
  toiletTank.castShadow = true
  toiletGroup.add(toiletTank)

  // 欧式装饰边框
  const decorGeometry = new THREE.BoxGeometry(0.65, 0.05, 0.35)
  const decorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFD700, // 金色
    roughness: 0.2,
    metalness: 0.8
  })
  const decor = new THREE.Mesh(decorGeometry, decorMaterial)
  decor.position.set(-2, 0.8, -1.2)
  toiletGroup.add(decor)

  scene.add(toiletGroup)
  currentSceneObjects.push(toiletGroup)
}

// 添加现代卫生间设备
const addModernBathroomFixtures = () => {
  if (!scene) return

  // 现代马桶 - 黑色陶瓷
  const toiletGroup = new THREE.Group()
  
  const toiletBaseGeometry = new THREE.CylinderGeometry(0.4, 0.4, 0.4, 16)
  const toiletMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x1C1C1C, // 黑色
    roughness: 0.1,
    metalness: 0.3
  })
  const toiletBase = new THREE.Mesh(toiletBaseGeometry, toiletMaterial)
  toiletBase.position.set(-2, 0.2, -1.5)
  toiletBase.castShadow = true
  toiletBase.receiveShadow = true
  toiletGroup.add(toiletBase)

  // 马桶水箱 - 现代设计
  const toiletTankGeometry = new THREE.BoxGeometry(0.6, 0.4, 0.3)
  const toiletTank = new THREE.Mesh(toiletTankGeometry, toiletMaterial)
  toiletTank.position.set(-2, 0.6, -1.2)
  toiletTank.castShadow = true
  toiletGroup.add(toiletTank)

  // 现代装饰边框
  const decorGeometry = new THREE.BoxGeometry(0.65, 0.05, 0.35)
  const decorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xC0C0C0, // 银色
    roughness: 0.1,
    metalness: 0.9
  })
  const decor = new THREE.Mesh(decorGeometry, decorMaterial)
  decor.position.set(-2, 0.8, -1.2)
  toiletGroup.add(decor)

  scene.add(toiletGroup)
  currentSceneObjects.push(toiletGroup)
}

// 添加北欧卫生间设备
const addNordicBathroomFixtures = () => {
  if (!scene) return

  // 北欧马桶 - 白色陶瓷
  const toiletGroup = new THREE.Group()
  
  const toiletBaseGeometry = new THREE.CylinderGeometry(0.4, 0.4, 0.4, 16)
  const toiletMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xFFFFFF, // 白色
    roughness: 0.1,
    metalness: 0.0
  })
  const toiletBase = new THREE.Mesh(toiletBaseGeometry, toiletMaterial)
  toiletBase.position.set(-2, 0.2, -1.5)
  toiletBase.castShadow = true
  toiletBase.receiveShadow = true
  toiletGroup.add(toiletBase)

  // 马桶水箱 - 北欧设计
  const toiletTankGeometry = new THREE.BoxGeometry(0.6, 0.4, 0.3)
  const toiletTank = new THREE.Mesh(toiletTankGeometry, toiletMaterial)
  toiletTank.position.set(-2, 0.6, -1.2)
  toiletTank.castShadow = true
  toiletGroup.add(toiletTank)

  // 北欧装饰边框
  const decorGeometry = new THREE.BoxGeometry(0.65, 0.05, 0.35)
  const decorMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.6,
    metalness: 0.0
  })
  const decor = new THREE.Mesh(decorGeometry, decorMaterial)
  decor.position.set(-2, 0.8, -1.2)
  toiletGroup.add(decor)

  scene.add(toiletGroup)
  currentSceneObjects.push(toiletGroup)
}

// 添加中式卫生间装饰
const addChineseBathroomDecorations = () => {
  if (!scene) return

  // 中式装饰画
  const paintingGeometry = new THREE.BoxGeometry(1.2, 0.8, 0.05)
  const paintingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B0000, // 深红色画框
    roughness: 0.6,
    metalness: 0.0
  })
  const painting = new THREE.Mesh(paintingGeometry, paintingMaterial)
  painting.position.set(0, 1.5, -2.9)
  scene.add(painting)
  currentSceneObjects.push(painting)

  // 画框内容
  const canvasGeometry = new THREE.BoxGeometry(1.0, 0.6, 0.02)
  const canvasMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x87CEEB, // 天蓝色
    roughness: 0.8,
    metalness: 0.0
  })
  const canvas = new THREE.Mesh(canvasGeometry, canvasMaterial)
  canvas.position.set(0, 1.5, -2.92)
  scene.add(canvas)
  currentSceneObjects.push(canvas)

  // 中式花瓶
  const vaseGeometry = new THREE.CylinderGeometry(0.08, 0.12, 0.3, 8)
  const vaseMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.4,
    metalness: 0.1
  })
  const vase = new THREE.Mesh(vaseGeometry, vaseMaterial)
  vase.position.set(3, 0.15, -1)
  scene.add(vase)
  currentSceneObjects.push(vase)
}

// 添加欧式卫生间装饰
const addEuropeanBathroomDecorations = () => {
  if (!scene) return

  // 欧式装饰画
  const paintingGeometry = new THREE.BoxGeometry(1.4, 1.0, 0.08)
  const paintingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 深棕色画框
    roughness: 0.6,
    metalness: 0.1
  })
  const painting = new THREE.Mesh(paintingGeometry, paintingMaterial)
  painting.position.set(0, 1.8, -2.9)
  scene.add(painting)
  currentSceneObjects.push(painting)

  // 画框内容
  const canvasGeometry = new THREE.BoxGeometry(1.2, 0.8, 0.02)
  const canvasMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 深棕色
    roughness: 0.8,
    metalness: 0.0
  })
  const canvas = new THREE.Mesh(canvasGeometry, canvasMaterial)
  canvas.position.set(0, 1.8, -2.92)
  scene.add(canvas)
  currentSceneObjects.push(canvas)

  // 欧式花瓶
  const vaseGeometry = new THREE.CylinderGeometry(0.1, 0.15, 0.4, 8)
  const vaseMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 深棕色
    roughness: 0.4,
    metalness: 0.1
  })
  const vase = new THREE.Mesh(vaseGeometry, vaseMaterial)
  vase.position.set(3, 0.2, -1)
  scene.add(vase)
  currentSceneObjects.push(vase)
}

// 添加现代卫生间装饰
const addModernBathroomDecorations = () => {
  if (!scene) return

  // 现代装饰画
  const paintingGeometry = new THREE.BoxGeometry(1.0, 0.6, 0.05)
  const paintingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色画框
    roughness: 0.3,
    metalness: 0.2
  })
  const painting = new THREE.Mesh(paintingGeometry, paintingMaterial)
  painting.position.set(0, 1.4, -2.9)
  scene.add(painting)
  currentSceneObjects.push(painting)

  // 画框内容
  const canvasGeometry = new THREE.BoxGeometry(0.8, 0.4, 0.02)
  const canvasMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x000000, // 黑色
    roughness: 0.8,
    metalness: 0.0
  })
  const canvas = new THREE.Mesh(canvasGeometry, canvasMaterial)
  canvas.position.set(0, 1.4, -2.92)
  scene.add(canvas)
  currentSceneObjects.push(canvas)

  // 现代花瓶
  const vaseGeometry = new THREE.CylinderGeometry(0.06, 0.06, 0.25, 8)
  const vaseMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x2F4F4F, // 深灰色
    roughness: 0.3,
    metalness: 0.2
  })
  const vase = new THREE.Mesh(vaseGeometry, vaseMaterial)
  vase.position.set(3, 0.125, -1)
  scene.add(vase)
  currentSceneObjects.push(vase)
}

// 添加北欧卫生间装饰
const addNordicBathroomDecorations = () => {
  if (!scene) return

  // 北欧装饰画
  const paintingGeometry = new THREE.BoxGeometry(1.2, 0.8, 0.05)
  const paintingMaterial = new THREE.MeshStandardMaterial({ 
    color: 0xDEB887, // 浅棕色画框
    roughness: 0.7,
    metalness: 0.0
  })
  const painting = new THREE.Mesh(paintingGeometry, paintingMaterial)
  painting.position.set(0, 1.6, -2.9)
  scene.add(painting)
  currentSceneObjects.push(painting)

  // 画框内容
  const canvasGeometry = new THREE.BoxGeometry(1.0, 0.6, 0.02)
  const canvasMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x87CEEB, // 天蓝色
    roughness: 0.8,
    metalness: 0.0
  })
  const canvas = new THREE.Mesh(canvasGeometry, canvasMaterial)
  canvas.position.set(0, 1.6, -2.92)
  scene.add(canvas)
  currentSceneObjects.push(canvas)

  // 北欧花瓶
  const vaseGeometry = new THREE.CylinderGeometry(0.08, 0.1, 0.3, 8)
  const vaseMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.7,
    metalness: 0.0
  })
  const vase = new THREE.Mesh(vaseGeometry, vaseMaterial)
  vase.position.set(3, 0.15, -1)
  scene.add(vase)
  currentSceneObjects.push(vase)

  // 北欧绿植
  const plantGroup = new THREE.Group()
  
  const plantPotGeometry = new THREE.CylinderGeometry(0.15, 0.18, 0.25, 8)
  const plantPotMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x8B4513, // 棕色
    roughness: 0.7,
    metalness: 0.0
  })
  const plantPot = new THREE.Mesh(plantPotGeometry, plantPotMaterial)
  plantPot.position.set(3.5, 0.125, -0.5)
  plantGroup.add(plantPot)

  // 植物叶子
  const leavesGeometry = new THREE.SphereGeometry(0.3, 8, 6)
  const leavesMaterial = new THREE.MeshStandardMaterial({ 
    color: 0x228B22, // 绿色
    roughness: 0.8,
    metalness: 0.0
  })
  const leaves = new THREE.Mesh(leavesGeometry, leavesMaterial)
  leaves.position.set(3.5, 0.4, -0.5)
  plantGroup.add(leaves)

  scene.add(plantGroup)
  currentSceneObjects.push(plantGroup)
}
</script>

<template>
  <div class="exhibition-hall">
    <div class="scene-container" ref="containerRef"></div>
    <div class="controls-info">
      <h2>🏠 3D 家居展厅</h2>
      <p>🖱️ 拖拽旋转视角 | 🔍 滚轮缩放 | 📱 支持触摸操作</p>
      
      <div class="theme-switcher">
        <h3>🏛️ 装修风格</h3>
        <div class="theme-buttons">
          <button 
            @click="switchTheme('chinese')" 
            :disabled="isTransitioning"
            class="theme-button"
            :class="{ active: currentTheme === 'chinese' }"
          >
            <span class="button-icon">🏮</span>
            <span class="button-text">中式</span>
          </button>
          
          <button 
            @click="switchTheme('european')" 
            :disabled="isTransitioning"
            class="theme-button"
            :class="{ active: currentTheme === 'european' }"
          >
            <span class="button-icon">🏰</span>
            <span class="button-text">欧式</span>
          </button>
          
          <button 
            @click="switchTheme('modern')" 
            :disabled="isTransitioning"
            class="theme-button"
            :class="{ active: currentTheme === 'modern' }"
          >
            <span class="button-icon">🏢</span>
            <span class="button-text">现代</span>
          </button>
          
          <button 
            @click="switchTheme('nordic')" 
            :disabled="isTransitioning"
            class="theme-button"
            :class="{ active: currentTheme === 'nordic' }"
          >
            <span class="button-icon">❄️</span>
            <span class="button-text">北欧</span>
          </button>
        </div>
      </div>
      
      <div class="style-switcher">
        <h3>🏠 房间类型</h3>
        <div class="style-buttons">
          <button 
            @click="switchStyle('living_room')" 
            :disabled="isTransitioning"
            class="style-button"
            :class="{ active: currentStyle === 'living_room' }"
          >
            <span class="button-icon">🛋️</span>
            <span class="button-text">客厅</span>
          </button>
          
          <button 
            @click="switchStyle('bedroom')" 
            :disabled="isTransitioning"
            class="style-button"
            :class="{ active: currentStyle === 'bedroom' }"
          >
            <span class="button-icon">🛏️</span>
            <span class="button-text">卧室</span>
          </button>
          
          <button 
            @click="switchStyle('bathroom')" 
            :disabled="isTransitioning"
            class="style-button"
            :class="{ active: currentStyle === 'bathroom' }"
          >
            <span class="button-icon">🚿</span>
            <span class="button-text">卫生间</span>
          </button>
          
          <button 
            @click="switchStyle('kitchen')" 
            :disabled="isTransitioning"
            class="style-button"
            :class="{ active: currentStyle === 'kitchen' }"
          >
            <span class="button-icon">🍳</span>
            <span class="button-text">厨房</span>
          </button>
        </div>
      </div>
      
      <div class="features">
        <span class="feature" v-if="currentStyle === 'living_room' && currentTheme === 'chinese'">
          🏮 红木沙发 | 🍵 茶具 | 🖼️ 屏风 | 🌸 梅花装饰
        </span>
        <span class="feature" v-else-if="currentStyle === 'living_room' && currentTheme === 'european'">
          🏰 欧式沙发 | 🔥 壁炉 | 💎 吊灯 | 🎨 装饰画
        </span>
        <span class="feature" v-else-if="currentStyle === 'living_room' && currentTheme === 'modern'">
          🏢 现代沙发 | 📺 大屏电视 | ⚫ 极简茶几 | 💡 现代照明
        </span>
        <span class="feature" v-else-if="currentStyle === 'living_room' && currentTheme === 'nordic'">
          ❄️ 北欧沙发 | 🌿 绿植 | 🪵 原木茶几 | 🕯️ 温馨照明
        </span>
        <span class="feature" v-else-if="currentStyle === 'bedroom' && currentTheme === 'chinese'">
          🏮 红木雕花床 | 🪑 中式梳妆台 | 🗄️ 红木衣柜 | 🪞 雕花镜框 | 🎨 山水画
        </span>
        <span class="feature" v-else-if="currentStyle === 'bedroom' && currentTheme === 'european'">
          🏰 四柱古典床 | 🪑 欧式梳妆台 | 🗄️ 古典衣柜 | 🪞 欧式镜框 | 🎨 古典油画
        </span>
        <span class="feature" v-else-if="currentStyle === 'bedroom' && currentTheme === 'modern'">
          🏢 极简现代床 | 🪑 现代梳妆台 | 🗄️ 极简衣柜 | 🪞 现代镜框 | 🎨 抽象艺术
        </span>
        <span class="feature" v-else-if="currentStyle === 'bedroom' && currentTheme === 'nordic'">
          ❄️ 北欧简约床 | 🪑 原木梳妆台 | 🗄️ 简约衣柜 | 🪞 自然镜框 | 🌿 绿植装饰
        </span>
        <span class="feature" v-else-if="currentStyle === 'bedroom'">
          🛏️ 双人床 | 🪑 梳妆台 | 🗄️ 衣柜 | 🪞 镜子
        </span>
        <span class="feature" v-else-if="currentStyle === 'bathroom' && currentTheme === 'chinese'">
          🏮 中式深色马桶 | 🚿 干湿分离 | 🚰 中式洗手台 | 🧻 储物架 | 🎨 中式装饰
        </span>
        <span class="feature" v-else-if="currentStyle === 'bathroom' && currentTheme === 'european'">
          🏰 欧式白色马桶 | 🚿 古典浴缸 | 🚰 欧式洗手台 | 🧻 古典毛巾架 | 🎨 欧式装饰
        </span>
        <span class="feature" v-else-if="currentStyle === 'bathroom' && currentTheme === 'modern'">
          🏢 现代黑色马桶 | 🚿 现代浴缸 | 🚰 现代洗手台 | 🧻 现代毛巾架 | 🎨 极简设计
        </span>
        <span class="feature" v-else-if="currentStyle === 'bathroom' && currentTheme === 'nordic'">
          ❄️ 北欧白色马桶 | 🚿 简约浴缸 | 🚰 原木洗手台 | 🧻 自然毛巾架 | 🌿 自然装饰
        </span>
        <span class="feature" v-else-if="currentStyle === 'bathroom'">
          🚿 浴缸 | 🚽 马桶 | 🚰 洗手台 | 🧻 毛巾架
        </span>
        <span class="feature" v-else-if="currentStyle === 'kitchen'">
          🍳 炉灶 | 🧊 冰箱 | 🗄️ 橱柜 | 🪑 餐桌
        </span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.exhibition-hall {
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.scene-container {
  width: 100%;
  height: 100%;
  cursor: grab;
}

.scene-container:active {
  cursor: grabbing;
}

.controls-info {
  position: absolute;
  top: 20px;
  left: 20px;
  background: rgba(255, 255, 255, 0.9);
  padding: 15px;
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  backdrop-filter: blur(10px);
}

.controls-info h2 {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 24px;
  font-weight: bold;
}

.controls-info p {
  margin: 0 0 10px 0;
  color: #666;
  font-size: 14px;
}

.theme-switcher, .style-switcher {
  margin: 15px 0;
}

.theme-switcher h3, .style-switcher h3 {
  margin: 0 0 8px 0;
  color: #333;
  font-size: 16px;
  font-weight: 600;
}

.theme-buttons, .style-buttons {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 8px;
}

.theme-button, .style-button {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 16px;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-radius: 25px;
  background: rgba(255, 255, 255, 0.1);
  color: #333;
  cursor: pointer;
  transition: all 0.3s ease;
  font-size: 14px;
  font-weight: 500;
  backdrop-filter: blur(10px);
}

.theme-button:hover, .style-button:hover {
  background: rgba(255, 255, 255, 0.2);
  border-color: rgba(255, 255, 255, 0.5);
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
}

.theme-button.active, .style-button.active {
  background: rgba(255, 215, 0, 0.3);
  border-color: #FFD700;
  color: #B8860B;
  box-shadow: 0 4px 15px rgba(255, 215, 0, 0.3);
}

.theme-button:disabled, .style-button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.button-icon {
  font-size: 18px;
}

.button-text {
  font-size: 13px;
}

.features {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.feature {
  background: rgba(255, 255, 255, 0.8);
  padding: 6px 12px;
  border-radius: 15px;
  font-size: 12px;
  color: #555;
  border: 1px solid rgba(255, 255, 255, 0.3);
  text-align: center;
  line-height: 1.4;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .controls-info {
    top: 10px;
    left: 10px;
    right: 10px;
    padding: 10px;
  }
  
  .controls-info h2 {
    font-size: 20px;
  }
  
  .controls-info p {
    font-size: 12px;
  }
  
  .theme-buttons, .style-buttons {
    grid-template-columns: 1fr;
    gap: 6px;
  }
  
  .theme-button, .style-button {
    padding: 8px 12px;
    font-size: 13px;
  }
  
  .button-icon {
    font-size: 16px;
  }
  
  .button-text {
    font-size: 12px;
  }
}
</style>