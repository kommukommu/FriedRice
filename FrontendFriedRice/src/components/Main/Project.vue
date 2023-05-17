<template>
    <div>
        <el-space direction="vertical" fill :fill-ratio="98" class="container">
            <!-- <template class="space" /> -->
            <el-card class="box-card" shadow="hover">
                <template #header>
                    <div class="card-header">
                        <span>Card name</span>
                    </div>
                    <div class="writer">
                        <span v-for="j in 4">{{ '作者' + j }}</span>
                    </div>
                </template>
                <div class="text item">简介简介简介简介</div>
            </el-card>
            <el-table :data="filterTableData" style="width: 100%" highlight-current-row
                @current-change="handleCurrentChange">
                <el-table-column label="Date" prop="date" />
                <el-table-column label="Name" prop="name" />
                <el-table-column align="right">
                    <template #header>
                        <el-input v-model="search" size="small" placeholder="Type to search" />
                    </template>
                    <template #default="scope">
                        <el-button size="small" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
                        <el-button size="small" type="danger"
                            @click="handleDelete(scope.$index, scope.row)">Delete</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <template class="space" />
        </el-space>
    </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router';

import { computed, ref } from 'vue'

const currentRow = ref()
const handleCurrentChange = (val) => {
    currentRow.value = val
    console.log(currentRow.value.name);
    openArticle()
}

const search = ref('')

const filterTableData = computed(() =>
    tableData.filter(
        (data) =>
            !search.value ||
            data.name.toLowerCase().includes(search.value.toLowerCase())
    )
)

const router = useRouter()

const tableData = [
    {
        date: '2016-05-03',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-02',
        name: 'John',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-04',
        name: 'Morgan',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-01',
        name: 'Jessy',
        address: 'No. 189, Grove St, Los Angeles',
    },
]

function openArticle() {
    // alert(i)
    console.log(router.push({
        name: "Article"
    }));
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
}
</style>