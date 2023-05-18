<template>
    <div>
        <el-space direction="vertical" fill :fill-ratio="98" class="container">
            <!-- <template class="space" /> -->
            <div>
                <el-button style="margin: 10px;" type="primary" @click="back">返回</el-button>
                <el-button v-if="isOwner" style="margin: 10px;" @click="">修改标题&简介</el-button>
                <el-button v-if="isOwner" style="margin: 10px;" @click="" :disabled="notTwoArticles">交换文章顺序</el-button>
                <el-button v-if="isOwner" style="margin: 10px;" type="danger"
                    @click="handleDelete">删除</el-button>
            </div>
            <el-card class="box-card" shadow="hover">
                <template #header>
                    <div class="card-header">
                        <span>{{ projectData.name }}</span>
                    </div>
                    <div class="writer">
                        <span>{{ projectData.owner }}</span>
                    </div>
                </template>
                <div class="text item">{{ projectData.desc }}</div>
            </el-card>
            <el-table :data="filterTableData" style="width: 100%" highlight-current-row
                @current-change="handleCurrentChange" @selection-change="handleSelectionChange">
                <el-table-column v-if="isOwner" type="selection" width="55" />
                <el-table-column label="Title" prop="date" min-width="200" />
                <el-table-column label="Writer" prop="name" width="200" />
                <el-table-column align="right" width="200">
                    <template #header>
                        <el-input v-model="search" size="small" placeholder="Type to search" />
                    </template>
                    <template v-if="isOwner" #default="scope">
                        <el-button size="small" @click="handleReview(scope.$index, scope.row)">审核</el-button>
                        <!-- <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
                    </template>
                </el-table-column>
            </el-table>
            <template class="space" />
        </el-space>
    </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router';
import { Delete } from '@element-plus/icons-vue'
import { markRaw, computed, ref } from 'vue'

const isOwner = ref(true)

const currentRow = ref()
const handleCurrentChange = (val) => {
    currentRow.value = val
    console.log(currentRow.value.name);
    openArticle()
}

const multipleSelection = ref([])

const handleSelectionChange = (val) => {
    multipleSelection.value = val
    console.log(multipleSelection.value, notTwoArticles.value);
}

const notTwoArticles = computed(() => {
    if (multipleSelection.value.length == 2) { return false }
    else { return true }
})

const search = ref('')

const filterTableData = computed(() =>
    tableData.value.filter(
        (data) =>
            !search.value ||
            data.name.toLowerCase().includes(search.value.toLowerCase())
    )
)

const router = useRouter()

function back() {
    // console.log(router);
    router.back()
}

const projectData = ref({
    id: 0,
    name: 'projecrt',
    owner: '123456',
    desc: 'desc',
})

const tableData = ref([
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
])

function openArticle() {
    // alert(i)
    router.push({
        name: "Article"
    });
}

function handleReview(index, row) {
    console.log(index, row);
    router.push({
        name: "Review"
    });
}

function handleDelete() {
    console.log(multipleSelection.value);
    ElMessageBox.confirm(
        'The selsected will be permanently deleted. Continue?',
        '警告',
        {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            type: 'warning',
            icon: markRaw(Delete),
        }
    )
        .then(() => {
            ElMessage({
                type: 'success',
                message: 'Delete completed',
            })
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: 'Delete canceled',
            })
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
}
</style>