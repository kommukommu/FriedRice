import { createApp } from 'vue'
import App from './App.vue'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// import './assets/main.css'

// 定义路由组件
// const Home = { template: '<div>Home</div>' }
// const About = { template: '<div>About</div>' }

// 定义一些路由
// const routes = [
//     { path: '/', component: Home },
//     { path: '/about', component: About },
//   ]

// 创建路由实例并传递 `routes` 配置
// const router = VueRouter.createRouter({
//     history: VueRouter.createWebHashHistory(),
//     routes, // `routes: routes` 的缩写
//   })

const app = createApp(App)
app.mount('#app')
app.use(ElementPlusIconsVue)