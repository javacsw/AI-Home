import { createRouter, createWebHistory } from 'vue-router'
import {useUserStore} from "@/stores/userStore.js";
import {get} from "@/net/index.js"

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      name:'auth',
      path:'/auth',
      component:()=>import('@/views/Auth/Auth.vue'),
    },{
      name:'BackStage',
      path:'/BackStage',
      component:()=>import('@/views/Common/BackStage.vue'),
    },
    {
      name:'framework',
      path:'/',
      component:()=>import('@/views/Auth/Framework.vue'),
      children:[
        {
          name: 'index',
          path: '/',
          component: () => import('@/views/Common/AiFront.vue')
        },{
          name:'editBlog',
          path:'/new',
          component:()=>import('@/views/Common/Menu.vue')
        },{
          name:'viewBlog',
          path:'/view',
          component:()=>import('@/views/Common/viewBlog.vue')
        },{
          name:'notes',
          path:'notes',
          component:()=>import('@/views/Common/Notes.vue')
        },{
          name:'addnotes',
          path:'addnotes',
          component:()=>import('@/views/Common/EditBlog.vue')
        },{
          name:'ai',
          path:'ai',
          component:()=>import('@/views/Common/AI.vue')
        },{
          name:'home',
          path:'/home/:id',
          component:()=>import('@/views/Common/Home/home.vue')
        },{
          name:'aifront',
          path:'/aifront',
          component:()=>import('@/views/Common/Index.vue')
        },{
          name:'aichat',
          path:'aichat',
          component:()=>import('@/views/Common/AiChat.vue')
        },{
          name:'we',
          path:'we',
          component:()=>import('@/views/Common/We.vue')
        },{
          name:'centre',
          path:'centre',
          component:()=>import('@/views/Common/Centre.vue')
        },{
          name:'myblog',
          path:'myblog',
          component:()=>import('@/views/Common/Myblog.vue')
        },{
          name:'ViewBlog',
          path:'view/:id',
          component:()=> import('@/views/Common/ViewBlog.vue')
        },{
          name:'analyse',
          path:'analyse',
          component:()=>import('@/views/Common/Analyse.vue')
        },{
          name:'userneed',
          path:'userneed',
          component:()=>import('@/views/Common/UserNeed.vue')
        },{
          name:'environment',
          path:'environment',
          component:()=>import('@/views/Common/Environment.vue')
        },{
          name:'chat',
          path:'chat',
          component:()=>import('@/views/Common/chat.vue')
        },{
          name:'culture',
          path:'culture',
          component:()=>import('@/views/Common/text3.vue')
        },{
          name:'sandboxie',
          path:'sandboxie',
          component:()=>import('@/views/Common/Sandboxie.vue')
        },{
          name:'me',
          path:'me',
          component:()=>import('@/views/Common/Me.vue')
        },{
          name:'environment',
          path:'environment',
          component:()=>import('@/views/Common/Environment.vue')
        },{
              name:'mylike',
              path:'mylike',
              component:()=>import('@/views/Common/MyLike.vue')
          },{
              name:'ExhibitionHall',
              path:'ExhibitionHall',
              component:()=>import('@/views/Common/ExhibitionHall.vue')
          },{
              name:'CodeEditor',
              path:'code-editor',
              component:()=>import('@/views/Common/CodeEditorPage.vue')
          }
      ]
    }
  ],
})

router.beforeEach((to,from,next)=>{
  const userStore = useUserStore()
  const publicMap = new Map()
  publicMap.set('/', 1)
  publicMap.set('/auth', 2)
  publicMap.set('/test', 3)
  publicMap.set('/error/401', 4)
  publicMap.set('/error/404', 5)
  // 检查要访问的路径是否是根路径
  if (to.matched.length === 0) next('error/404')
  if (to.meta.title) {
    document.title = 'MetaMind'
  } else {
    document.title = to.meta.title
  }
  if (!publicMap.has(to.path)) {
    // 不是访问根路径，检查用户状态
    const user = userStore.user; // 假设你的用户状态保存在Vuex的`user`状态中
    if (user === null) {
      get('api/user/myinfo', {},
          (message,data) => {
            userStore.login(data);
            console.log(data)
            next();
          }, () => {
            next('/auth'); // 3秒后重定向到根路径
          }, () => {
            next('/auth'); // 3秒后重定向到根路径
          }
      )
    } else {
      // 用户已登录，允许路由继续
      next();
    }
  } else {
    // 访问的是根路径，直接放行
    next();
  }

})

export default router
