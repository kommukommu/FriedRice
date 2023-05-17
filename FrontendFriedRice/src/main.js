import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// import './assets/main.css'

const app = createApp(App)

app.use(ElementPlusIconsVue)
app.use(router)

app.mount('#app')