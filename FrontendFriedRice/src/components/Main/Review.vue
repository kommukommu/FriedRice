<template>
    <el-space direction="vertical" fill :fill-ratio="98" class="container">
        <!-- <template class="space" /> -->
        <div>
            <el-button style="margin: 10px;" type="primary" @click="back">返回</el-button>
            <el-button style="margin: 10px;" @click="initialiseChange">修改要求</el-button>
            <el-switch v-model="isReleased" class="switch" active-text="使用最新版本" inactive-text="使用历史版本"
                :loading="loadingSwitch" :before-change="beforeChange" />
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
            <!-- <div class="text item">{{ articleData.requirement }}</div> -->
            <v-md-preview :text="articleData.requirement"></v-md-preview>
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
    <el-dialog v-model="isVisible" title="修改要求" align-center width="75%">
        <v-md-editor v-model="requirementChange" height="400px"></v-md-editor>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="isVisible = false">Cancel</el-button>
                <el-button type="primary" @click="changeRequirement">
                    Submit
                </el-button>
            </span>
        </template>
    </el-dialog>
</template>
<script setup>
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const isReleased = ref(false)
const isVisible = ref(false)

const requirementChange = ref('')

const articleData = ref({
    id: 0,
    title: '标题',
    writer: '作者',
    requirement: `
yaoqiu
要求
`,
    lastChange: '2000-01-01'
})

function initialiseChange() {
    isVisible.value = true
    requirementChange.value = articleData.value.requirement
}



function changeRequirement() {
    console.log(requirementChange.value);
    isVisible.value = false
}


const isWriter = ref(true)

const loadingSwitch = ref(false)

const beforeChange = () => {
    loadingSwitch.value = true
    return new Promise((resolve) => {
        setTimeout(() => {
            loadingSwitch.value = false
            ElMessage.success('Switch success')
            return resolve(true)
        }, 1000)
    })
}

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

.el-button--text {
    margin-right: 15px;
}

.el-select {
    width: 300px;
}

/* .el-input {
  width: 300px;
} */

.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>