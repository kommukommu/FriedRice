import { createRouter, createWebHashHistory } from 'vue-router'

import Homepage from "../components/Main/Homepage.vue"
import Project from '../components/Main/Project.vue'
import Article from '../components/Main/Article.vue'
import Userpage from "../components/Main/Userpage.vue"
import EditArticle from "../components/Main/EditArticle.vue"

import SubscriptionList from "../components/Main/SubscriptionList.vue"
import Login from "../components/Main/Login.vue"
import Signup from "../components/Main/Signup.vue"

import ProjectList from "../components/Main/User/ProjectList.vue"
import ArticleList from "../components/Main/User/ArticleList.vue"

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
        path: '/article',
        component: Article
    },
    {
        name: 'SubscriptionList',
        path: '/subscriptionList',
        component: SubscriptionList
    },
    {
        name: 'Login',
        path: '/login',
        component: Login
    },
    {
        name: 'Signup',
        path: '/signup',
        component: Signup
    },
    {
        name: 'EditArticle',
        path: '/editArticle',
        component: EditArticle
    },
    {
        name: 'User',
        path: '/user',
        component: Userpage,
        redirect: {
            name: 'ProjectList'
        },
        children: [
            // {
            //     // name: 'User',
            //     path: '',
            //     redirect: {
            //         name: ProjectList
            //     },
            // },
            // {
            //     name: 'SubscriptionList',
            //     path: 'subscriptionList',
            //     component: SubscriptionList
            // },
            {
                name: 'ProjectList',
                path: 'projectList',
                component: ProjectList
            },
            {
                name: 'ArticleList',
                path: 'articleList',
                component: ArticleList
            },
        ]
    },
]

// 创建路由实例并传递 `routes` 配置
export default createRouter({
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})