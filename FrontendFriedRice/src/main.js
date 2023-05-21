import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import editor from './editor'
import preview from './preview'

import axios from 'axios'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// import './assets/main.css'

const app = createApp(App)

axios.defaults.baseURL = '/api';

app.use(ElementPlusIconsVue)
app.use(router)
app.use(editor)
app.use(preview)

app.mount('#app')