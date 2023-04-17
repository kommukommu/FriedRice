<template>
  <div>
    <el-container class="layout-container-demo">
      <el-header class="layout-header">
        <div class="toolbar">
          <el-input v-model="inputSearch" class="input-search" placeholder="Type something" :prefix-icon="Search" />
          <el-dropdown>
            <el-icon style="margin-right: 8px; margin-top: 1px">
              <User />
            </el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>View</el-dropdown-item>
                <el-dropdown-item>Add</el-dropdown-item>
                <el-dropdown-item>Delete</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <span>Tom</span>
        </div>
      </el-header>
      <el-main class="layout-main"><Homepage /></el-main>
      <el-footer class="layout-footer">Footer</el-footer>
    </el-container>
  </div>
</template>

<script setup>
import { User, Search } from '@element-plus/icons-vue'
import { ref } from 'vue'
import Homepage from './Main/Homepage.vue';

let inputSearch = ref('')

</script>
  
<style scoped>
.layout-container-demo {
  display: flex;
  height: 80%;
}

.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}

.layout-container-demo .el-main {
  position: relative;
  background-color: var(--el-color-primary-light-9);
  color: var(--el-text-color-primary);
}

.layout-container-demo .el-footer {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}

.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0;
}

.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
  width: 100%;
}

.layout-container-demo .layout-header {
  width: 100%;
  text-align: right;
  font-size: 16px;
}

.layout-container-demo .input-search {
  display: inline-flex;
  margin: auto;
  width: 70%;
  min-width: 100px;
}
</style>