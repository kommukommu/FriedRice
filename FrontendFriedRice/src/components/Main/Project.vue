<template>
    <div>
        <el-space direction="vertical" fill :fill-ratio="98" class="container">
            <!-- <template class="space" /> -->
            <div>
                <el-button style="margin: 10px;" type="primary" @click="back">返回</el-button>
                <el-button v-if="isOwner" style="margin: 10px;" @click="initialiseFormChange">修改标题&简介</el-button>
                <el-button v-if="isOwner" style="margin: 10px;" @click="isCreateVisible = true">添加文章</el-button>
                <el-button v-if="isOwner" style="margin: 10px;" @click="swapArticles"
                    :disabled="notTwoArticles">交换文章顺序</el-button>
                <el-button v-if="isOwner" style="margin: 10px;" type="danger" @click="handleDelete">删除</el-button>
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
                <div class="text item">
                    <div>{{ projectData.desc }}</div>
                </div>
            </el-card>
            <el-table :data="filterTableData" style="width: 100%" highlight-current-row
                @current-change="handleCurrentChange" @selection-change="handleSelectionChange">
                <el-table-column v-if="isOwner" type="selection" width="55" />
                <el-table-column label="Title" prop="title" min-width="200" />
                <el-table-column label="Writer" prop="writer" width="200" />
                <el-table-column align="right" width="250">
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
        <el-dialog v-model="isChangeVisible" title="修改项目" align-center>
            <el-form :model="formChange" label-position="top">
                <el-form-item label="项目名" :label-width="formLabelWidth">
                    <el-input v-model="formChange.name" autocomplete="off" />
                </el-form-item>
                <el-form-item label="简介" :label-width="formLabelWidth">
                    <el-input v-model="formChange.desc" type="textarea" autocomplete="off" />
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="isChangeVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="changeProject">
                        Submit
                    </el-button>
                </span>
            </template>
        </el-dialog>
        <el-dialog v-model="isCreateVisible" title="添加文章" align-center>
            <el-form :model="formCreate" label-position="top">
                <el-form-item label="文章标题" :label-width="formLabelWidth">
                    <el-input v-model="formCreate.title" autocomplete="off" />
                </el-form-item>
                <el-form-item label="写作要求" :label-width="formLabelWidth">
                    <el-input v-model="formCreate.requirement" type="textarea" autocomplete="off" />
                </el-form-item>
                <el-form-item label="作者" :label-width="formLabelWidth">
                    <el-select v-model="formCreate.writer" filterable placeholder="Please select a writer">
                        <el-option v-for="i in writerList" :key="i.id" :label="i.name" :value="i.id" />
                    </el-select>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="isCreateVisible = false">Cancel</el-button>
                    <el-button type="primary" @click="createArticle">
                        Submit
                    </el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router';
import { Delete } from '@element-plus/icons-vue'
import { reactive, markRaw, computed, ref } from 'vue'

const isOwner = ref(true)
const isChangeVisible = ref(false)
const isCreateVisible = ref(false)

const formLabelWidth = 'auto'

const formChange = reactive({
    name: '',
    desc: '',
})

function initialiseFormChange() {
    isChangeVisible.value = true
    formChange.name = projectData.value.name
    formChange.desc = projectData.value.desc
}

function changeProject() {
    console.log(formChange);
    isChangeVisible.value = false
}

const formCreate = reactive({
    title: '',
    requirement: '',
    writer: '',
})

const writerList = ref([
    {
        id: 0,
        name: 'writer1',
    },
    {
        id: 1,
        name: 'writer2',
    },
    {
        id: 2,
        name: 'writer3',
    },
    {
        id: 3,
        name: 'writer4',
    },
])

function initialiseFormCreate() {
    isCreateVisible.value = true
    formChange.title = ''
    formChange.requirement = ''
    formChange.writer = ''
}

function createArticle() {
    console.log(formCreate);
    isCreateVisible.value = false
}

const currentRow = ref()
const handleCurrentChange = (val) => {
    currentRow.value = val
    console.log(currentRow.value);
    openArticle()
}

const multipleSelection = ref([])

const handleSelectionChange = (val) => {
    multipleSelection.value = val
    console.log(multipleSelection.value, notTwoArticles.value);
}

const swapArticles = () => {
    console.log(multipleSelection.value);
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
            data.writer.toLowerCase().includes(search.value.toLowerCase())
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
    desc: `descde`,
})

const tableData = ref([
    {
        title: '2016-05-03',
        writer: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        title: '2016-05-02',
        writer: 'John',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        title: '2016-05-04',
        writer: 'Morgan',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        title: '2016-05-01',
        writer: 'Jessy',
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

.el-button--text {
    margin-right: 15px;
}

.el-select {
    width: 300px;
}

/* .el-input {
  width: 200px;
} */

.dialog-footer button:first-child {
    margin-right: 10px;
}
</style>