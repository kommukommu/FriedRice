<template>
    <el-space direction="vertical" fill :fill-ratio="98" class="container">
        <!-- <template class="space" /> -->
        <el-card class="box-card" shadow="hover">
            <template #header>
                <div class="card-header">
                    <span>Card name</span>
                </div>
            </template>
            <div v-for="o in 4" :key="o" class="text item">{{ 'List item ' + o }}</div>
        </el-card>
        <el-card class="box-card" shadow="hover" @click="jump('EditArticle')">
            <template #header>
                <div class="card-header">
                    <span>Card name</span>
                </div>
            </template>
            <div v-for="o in 4" :key="o" class="text item">{{ 'List item ' + o }}</div>
        </el-card>
        <el-card class="box-card" shadow="hover">
            <div v-for="o in 4" :key="o" class="text item">{{ '正文 ' + o }}</div>
        </el-card>
    </el-space>
</template>
<script setup>
import { useRoute, useRouter } from 'vue-router';

const router = useRouter()

function jump(name){
    console.log(router);
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

.box-card {
    height: 150px;
}

.space {
    height: 8px;
}

.writer {
    text-align: right;
}</style>