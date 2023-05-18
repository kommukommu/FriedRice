<template>
    <el-table :data="tableData">
        <el-table-column prop="name" label="项目名" width="200" />
        <el-table-column prop="desc" label="简介" width="400" />
        <el-table-column prop="owner" label="作者" />
    </el-table>
</template>
<script setup>
import { ref } from 'vue'
const tableData = ref([
    {
        id: 0,
        name: 'projecrt',
        owner: '123456',
        desc: 'desc',
    },
    {
        id: 0,
        name: 'projecrt',
        owner: '123456',
        desc: 'desc',
    },
    {
        id: 0,
        name: 'projecrt',
        owner: '123456',
        desc: 'desc',
    },
    {
        id: 0,
        name: 'projecrt',
        owner: '123456',
        desc: 'desc',
    },
    {
        id: 0,
        name: 'projecrt',
        owner: '123456',
        desc: 'desc',
    },
])
</script>
<style scoped></style>