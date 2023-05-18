<template>
  <div>
    <el-container class="layout-container-demo">
      <el-header class="layout-header">
        <!-- <div class="toolbar">
          <span class="layout-homepage">
            <el-button @click="goHomePage">
              <el-icon style="margin-right: 8px; margin-bottom: 1px;">
                <House />
              </el-icon>
              首页
            </el-button>
          </span>
          <span class="layout-search">
            <el-input v-model="inputSearch" placeholder="Type something" :prefix-icon="Search" />
            <el-button @click="searchProject">
              <el-icon style="">
                <Search />
              </el-icon>
            </el-button>
          </span>
          <el-dropdown>
            <span class="layout-user">
              <el-icon style="margin-right: 8px;">
                <User />
              </el-icon>
              Tom
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="goUserPage">个人页面</el-dropdown-item>
                <el-dropdown-item @click="logout">登出</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div> -->
        <el-menu default-active="0" class="el-menu-demo" mode="horizontal" :ellipsis="false"
          @select="(index)=>handleSelect(index)">
          <el-menu-item @click="goHomePage" index="0">
            <el-icon style="margin-right: 8px; margin-bottom: 1px;">
              <House />
            </el-icon>
            首页
          </el-menu-item>
          <div class="flex-grow">
            <el-input style="margin: 5px 0 0 50px; height: 40px; width: 30vw;" v-model="inputSearch"
              placeholder="Type something" :prefix-icon="Search" />
            <el-button @click="searchProject" style="margin: 5px 0 0 0; width: 40px; height: 40px">
              <el-icon style="">
                <Search />
              </el-icon>
            </el-button>
          </div>
          <!-- <el-menu-item index="1">
            <el-icon style="margin-right: 8px; margin-bottom: 1px;">
              <Search />
            </el-icon>
            搜索
          </el-menu-item> -->
          <el-sub-menu index="2">
            <template #title>
              <span class="layout-user">
                <el-icon style="margin-right: 8px;">
                  <User />
                </el-icon>
                Tom
              </span>
            </template>
            <el-menu-item index="2-1" @click="goUserPage">个人页面</el-menu-item>
            <el-menu-item index="2-2" @click="goSubcription">关注列表</el-menu-item>
            <el-menu-item index="2-3" @click="logout">登出</el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-header>
      <el-main class="layout-main">
        <div style="margin: 10px 0">
          <router-view></router-view>
        </div>
      </el-main>
      <!-- <div style="height: inherit;"></div>/ -->
      <el-footer class="layout-footer">FriedRice</el-footer>
    </el-container>
  </div>
</template>

<script setup>
import { User, Search, House } from '@element-plus/icons-vue'
import { ref, inject } from 'vue'
import { useRoute, useRouter } from 'vue-router';
// import Homepage from './Main/Homepage.vue';
// import Project from './Main/Project.vue';

const router = useRouter()
const inputSearch = ref('')

const globalVar = inject('$globalVar')

function goHomePage() {
  router.push({
    name: "Home"
  })
}

function goUserPage() {
  router.push({
    name: "User"
  })
}

function goSubcription() {
  router.push({
    name: "SubscriptionList"
  })
}

function logout() {
  router.push({
    name: "Home"
  })
}

function searchProject() {
  router.push({
    name: "Home"
  })
}

function handleSelect(index) {
  console.log(index);
}
</script>
  
<style scoped>
.flex-grow {
  flex-grow: 1;
  /* flex-direction: row;/ */
}

.layout-container-demo {
  display: flex;
  height: 97vh;
  /* flex-direction: column; */
}

.layout-container-demo .el-header {
  position: relative;
  /* background-color: var(--el-color-primary-light-9); */
  color: var(--el-text-color-primary);
  padding: 0;
}

.layout-container-demo .el-main {
  position: relative;
  background-color: var(--el-color-primary-light-9);
  color: var(--el-text-color-primary);
  padding: 0;
}

.layout-container-demo .el-footer {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}

/* .layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
} */

/* .layout-container-demo .el-menu {
  border-right: none;
} */

/* .layout-container-demo .toolbar {
  display: inline-flex;
  /* align-items: center; */
/* justify-content: center; */
/* height: 100%;
  right: 20px;
  width: 100%;
} */

.layout-container-demo .layout-user {
  display: inline-flex;
  align-items: center;
  justify-content: center;

}

.layout-container-demo .layout-header {
  width: 100%;
  font-size: 16px;
}

/* .layout-container-demo .layout-search {
  display: inline-flex;
  margin: auto;
  width: 80%;
  min-width: 100px;
}

.layout-container-demo .layout-search .el-input {
  display: inline-flex;
  margin-right: 10px;
  width: 100%;
  min-width: 100px;
}

.layout-container-demo .layout-search .el-button {
  display: inline-flex;
  width: 10px;
}

.layout-container-demo .layout-homepage {
  display: inline-flex;
  margin: auto;
  /* left: 20px; */
/* margin-left: 0;
  width: 100px
} */

/* .layout-container-demo .layout-homepage .el-button {
  display: inline-flex;
}*/
</style>