<template>
    <div>
        <el-container class="layout-container-demo" style="height: 75vh">
            <el-aside width="200px">
                <el-scrollbar>
                    <el-menu >
                        <el-menu-item index="1">
                            <el-icon><Plus /></el-icon>关注

                        </el-menu-item>
                        <!-- <el-menu-item index="2" @click="jump('SubscriptionList')">
                            <el-icon><Bell /></el-icon>关注列表

                        </el-menu-item> -->
                        <el-menu-item index="2" @click="jump('ProjectList')">

                            <el-icon><Files /></el-icon>项目列表

                        </el-menu-item>
                        <el-menu-item index="3" @click="jump('ArticleList')">
                            <el-icon><Tickets /></el-icon>文章所属项目列表

                        </el-menu-item>
                    </el-menu>
                </el-scrollbar>
            </el-aside>

            <el-container>

                <el-main>
                    <el-scrollbar>
                        <router-view></router-view>
                    </el-scrollbar>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>
<script setup>
import { useRoute, useRouter } from 'vue-router';
import { Plus, Bell, Files,Tickets  } from '@element-plus/icons-vue'

const router = useRouter()

function jump(name){
    console.log(router);
    router.push({
        name,        
    })
}
</script>
<style scoped>

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
</style>