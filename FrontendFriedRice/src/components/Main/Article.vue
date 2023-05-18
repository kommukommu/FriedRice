<template>
    <el-space direction="vertical" fill :fill-ratio="98" class="container">
        <!-- <template class="space" /> -->
        <div>
            <el-button style="margin: 10px;" type="primary" @click="back">返回</el-button>
            <el-button style="margin: 10px;"  @click="jump('EditArticle')">修改正文</el-button>
            <el-button style="margin: 10px;"  @click="back">修改要求</el-button>
        </div>
        <el-card class="title-card" shadow="hover">
            <template #header>
                <div class="card-header">
                    <span>Card name</span>
                </div>
            </template>
            <div v-for="o in 4" :key="o" class="text item">{{ 'List item ' + o }}</div>
        </el-card>
        <el-card class="requirement-card" shadow="hover">
            <template #header>
                <div class="card-header">
                    <span>Card name</span>
                </div>
            </template>
            <div v-for="o in 4" :key="o" class="text item">{{ 'List item ' + o }}</div>
        </el-card>
        <el-card class="body-card" shadow="hover">
            <!-- <div v-for="o in 4" :key="o" class="text item">{{ '正文 ' + o }}</div> -->
            <v-md-preview :text="text"></v-md-preview>
        </el-card>
    </el-space>
</template>
<script setup>
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const text = ref(`
# 一级标题
## 二级标题
### 三级标题
# 一级标题
## 二级标题
### 三级标题
`)
const router = useRouter()

function back(){
    // console.log(router);
    router.back()
}

function jump(name){
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
}</style>