import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import editor from './editor'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'
// import './assets/main.css'

const app = createApp(App)

const globalVar = {
    value: '',
    set(val){
        this.value = val
    }
}
app.provide('$globalVar', globalVar);

app.use(ElementPlusIconsVue)
app.use(router)
app.use(editor)

app.mount('#app')