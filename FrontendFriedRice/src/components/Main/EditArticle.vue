<template>
    <v-md-editor v-model="text" height="400px"></v-md-editor>
    <el-button style="margin: 10px;" type="primary" @click="submit">提交</el-button>
    <el-button style="margin: 10px;" @click="back">返回</el-button>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

onMounted(()=>{
    
})

const text = ref('')
const router = useRouter()

function back() {
    // console.log(router);
    router.back()
}

function submit() {
    console.log(text.value);
}
</script>