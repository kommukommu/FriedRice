<template>
    <el-table :data="tableData" highlight-current-row @current-change="handleCurrentChange">
        <el-table-column prop="name" label="项目名" width="200" />
        <el-table-column prop="description" label="简介" min-width="200" />
        <el-table-column prop="ownerName" label="作者" width="100" />
    </el-table>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const props = defineProps({
    userID: Number
})
const route = useRoute()
const router = useRouter()

onMounted(() => {
    getProjects()
})

function getProjects() {
    axios.get('/Project/Owner/' + route.params.id)
        .then(function (response) {
            const res = response.data

            console.log(response);
            if (res.code == 0) {
                // ElMessage({
                //     message: res.message,
                //     type: 'success',
                // })
                tableData.value = res.projects
            } else {
                ElMessage({
                    message: res.message,
                    type: 'error',
                })
            }
        })
        .catch(function (error) {
            console.log(error);
        });
}

const currentRow = ref()
const handleCurrentChange = (val) => {
    currentRow.value = val
    console.log(currentRow.value);
    openProject()
}

const openProject = () => {
    router.push({
        name: "Project",
        params: {
            projectID: currentRow.value.id
        }
    })
}

const tableData = ref([
    {
        id: 0,
        name: 'projecrt',
        owner: -1,
        ownerName: '123456',
        description: 'desc',
    },
    {
        id: 0,
        name: 'projecrt',
        owner: -1,
        ownerName: '123456',
        description: 'desc',
    },
    {
        id: 0,
        name: 'projecrt',
        owner: -1,
        ownerName: '123456',
        description: 'desc',
    },
    {
        id: 0,
        name: 'projecrt',
        owner: -1,
        ownerName: '123456',
        description: 'desc',
    },
    {
        id: 0,
        name: 'projecrt',
        owner: -1,
        ownerName: '123456',
        description: 'desc',
    },
])
</script>
<style scoped></style>