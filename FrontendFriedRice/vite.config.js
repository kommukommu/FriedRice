// import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

// import ElementPlus from 'unplugin-element-plus/vite'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver({
        // importStyle: 'sass',
      })],
      // eslintrc: {
      //   enabled: true, // <-- this
      // },
    }),
    Components({
      resolvers: [ElementPlusResolver({
        // importStyle: 'sass',
      })],
    }),
    // ElementPlus({
    //   // options
    //   // importStyle: 'sass',
    //   // useSource: true
    // }),
  ],
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:4030/api",
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ""),
      },
    },
  },
  // resolve: {
  //   alias: {
  //     '@': fileURLToPath(new URL('./src', import.meta.url))
  //   }
  // }
})
