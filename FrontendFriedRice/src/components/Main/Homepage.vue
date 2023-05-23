<template>
    <div>
        <el-space direction="vertical" fill :fill-ratio="98" class="container">
            <!-- <template class="space" /> -->
            <el-card v-if="store.isSearchByConditions" class="" shadow="hover">
                <el-radio-group v-model="store.searchingConditions" @change="searchByConditions">
                    <el-radio-button label="搜索项目名称" />
                    <el-radio-button label="搜索项目管理员" />
                </el-radio-group>
            </el-card>
            <el-card v-for="i in data" :key="i.id" class="box-card" shadow="hover" @click="openNewProject($event, i.id)">
                <template #header>
                    <div class="card-header">
                        <span>{{ i.name }}</span>
                    </div>
                    <div class="writer">
                        <span>{{ '管理者:' + i.owner }}</span>
                    </div>
                </template>
                <div class="text item">{{ i.description }}</div>
            </el-card>
            <template class="space" />
        </el-space>
    </div>
</template>
<script setup>
import { ref, onMounted, onUpdated } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { store } from '../../store';
import axios from 'axios';

const router = useRouter()
const route = useRoute()
// const isByConditions = ref(false)

onMounted(() => {
    if ('name' in route.params) {
        store.startSearchByConditions()
        searchByConditions()
    } else {
        store.stopSearchByConditions()
        // url = '/Project'
        getProjects('/Project')
    }
})

onUpdated(() => {
    if ('name' in route.params) {
        store.startSearchByConditions()
        searchByConditions()
    } else {
        store.stopSearchByConditions()
        // url = '/Project'
        getProjects('/Project')
    }
})

function getProjects(url) {
    axios.get(url)
        .then(function (response) {
            const res = response.data

            console.log(response);
            if (res.code == 0) {
                // ElMessage({
                //     message: res.message,
                //     type: 'success',
                // })
                console.log(res.projects);
                data.value = res.projects
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

function searchByConditions() {
    let url = ""
    if (store.searchingConditions == "搜索项目管理员") {
        url = '/Project/Owner/' + route.params.name
    } else if (store.searchingConditions == "搜索项目名称") {
        url = '/Project/Name/' + route.params.name
    }
    getProjects(url)
}

const data = ref([
    {
        id: '1',
        name: 't1',
        description: 'd1',
        owner: 'o1',
    },
    {
        id: '2',
        name: 't2',
        description: 'd2',
        owner: 'o1',
    },
    {
        id: '3',
        name: 't3',
        description: 'd3',
        owner: 'o1',
    },
    {
        id: '4',
        name: 't4',
        description: 'd4',
        owner: 'o1',
    },
])

function openNewProject(event, i) {
    // alert(i)
    router.push({
        name: "Project"
    });
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