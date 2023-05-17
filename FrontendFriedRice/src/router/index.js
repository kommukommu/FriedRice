import { createRouter, createWebHashHistory } from 'vue-router'

import Homepage from "../components/Main/Homepage.vue"
import Project from '../components/Main/Project.vue'
import Article from '../components/Main/Article.vue'

// 定义路由组件
// const Home = { template: '<div>Home</div>' }
// const About = { template: '<div>About</div>' }

// 定义一些路由
const routes = [
    {
        name: 'Home',
        path: '/',
        component: Homepage
    },
    {
        name: 'Project',
        path: '/project',
        component: Project
    },
    {
        name: 'Article',
        path: '/Article',
        component: Article
    },
]

// 创建路由实例并传递 `routes` 配置
export default createRouter({
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})