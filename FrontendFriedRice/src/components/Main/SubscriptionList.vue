<template>
    <div>
        <el-button style="margin: 10px;" type="primary" @click="back">返回</el-button>
        <el-button style="margin: 10px;" type="danger" @click="unsubscribe">取消关注</el-button>
    </div>
    <el-table :data="tableData" style="width: 100%" highlight-current-row @current-change="handleCurrentChange"
        @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="id" width="100" />
        <el-table-column prop="name" label="用户名" />
    </el-table>
</template>
<script setup>
import { markRaw, ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router';
import { Delete } from '@element-plus/icons-vue'
import axios from 'axios';

onMounted(() => {
    getSubscriptions()
})

function getSubscriptions() {
    axios.get('/Subscription')
        .then(function (response) {
            const res = response.data

            console.log(response);
            if (res.code == 0) {
                // ElMessage({
                //     message: res.message,
                //     type: 'success',
                // })
                console.log(res.list);
                tableData.value = res.list
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
    // console.log('subscribe!')
}

const router = useRouter()

function back() {
    // console.log(router);
    router.back()
}

const currentRow = ref()
const handleCurrentChange = (val) => {
    currentRow.value = val
    console.log(currentRow.value.name);
}

const multipleSelection = ref([])

const handleSelectionChange = (val) => {
    multipleSelection.value = val
    console.log(multipleSelection.value);
}

function unsubscribe() {
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
            // ElMessage({
            //     type: 'success',
            //     message: 'Delete completed',
            // })
            removeSubscriptions()
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: 'Delete canceled',
            })
        })
}

function removeSubscriptions() {
    axios.delete('/Subscription', {
        data: multipleSelection.value.map(x => x.id)
    })
        .then(function (response) {
            const res = response.data

            console.log(response);
            if (res.code == 0) {
                ElMessage({
                    message: res.message,
                    type: 'success',
                })
                getSubscriptions()
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
    // console.log('subscribe!')
}

const tableData = ref([
    {
        id: 0,
        name: 'Jerry',
    },
])
</script>
<style scoped></style>