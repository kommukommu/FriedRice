<template>
    <el-space direction="vertical" fill :fill-ratio="98" class="container">
        <!-- <template class="space" /> -->
        <div>
            <el-button style="margin: 10px;" type="primary" @click="back">返回</el-button>
            <el-button style="margin: 10px;" @click="back">修改要求</el-button>
            <el-switch v-model="isReleased" class="switch"
                style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949" active-text="公开"
                inactive-text="不公开" />
        </div>
        <el-card class="title-card" shadow="hover">
            <template #header>
                <div class="card-header">
                    <span>{{ articleData.title }}</span>
                </div>
                <div class="writer">
                    <span>{{ '作者：' + articleData.writer }}</span>
                </div>
            </template>
            <div class="text item">{{ articleData.lastChange }}</div>
        </el-card>
        <el-card v-show="isWriter" class="requirement-card" shadow="hover">
            <template #header>
                <div class="card-header">
                    <span>写作要求</span>
                </div>
            </template>
            <div class="text item">{{ requirement }}</div>
        </el-card>
        <el-card class="body-card" shadow="hover">
            <template #header>
                <div class="card-header">
                    <span>最新版本</span>
                </div>
            </template>
            <!-- <div v-for="o in 4" :key="o" class="text item">{{ '正文 ' + o }}</div> -->
            <v-md-preview :text="text"></v-md-preview>
        </el-card>
        <el-card class="body-card" shadow="hover">
            <template #header>
                <div class="card-header">
                    <span>历史版本</span>
                </div>
            </template>
            <!-- <div v-for="o in 4" :key="o" class="text item">{{ '正文 ' + o }}</div> -->
            <v-md-preview :text="textOld"></v-md-preview>
        </el-card>
    </el-space>
</template>
<script setup>
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const isReleased = ref (false)

const articleData = ref({
    id: 0,
    title: '标题',
    writer: '作者',
    lastChange: '2000-01-01'
})

const isWriter = ref(true)

const requirement = ref(`
yaoqiu
要求
`)

const text = ref(`
# 一级标题
## 二级标题
### 三级标题
# 一级标题
## 二级标题
### 三级标题
`)

const textOld = ref(`
### 三级标题
# 一级标题
## 二级标题

## 二级标题
# 一级标题
### 三级标题
`)

const router = useRouter()

function back() {
    // console.log(router);
    router.back()
}

function jump(name) {
    // console.log(router);
    router.push({
        name,
    })
}
</script>
<style scoped>
.container {
    height: auto;
    width: 100%;
    margin: 8px 0;
}

.space {
    height: 8px;
}

.writer {
    text-align: right;
}
</style>