<template>
    <div>
        <el-space direction="vertical" fill :fill-ratio="98" class="container">
            <!-- <template class="space" /> -->
            <el-card v-for="i in data" :key="i.id" class="box-card" shadow="hover" @click="openNewProject($event, i.id)">
                <template #header>
                    <div class="card-header">
                        <span>{{  i.title }}</span>
                    </div>
                    <div class="writer">
                        <span>{{ '管理者:' + i.owner }}</span>
                    </div>
                </template>
                <div class="text item">{{ i.desc }}</div>
            </el-card>
            <template class="space" />
        </el-space>
    </div>
</template>
<script setup>
import { ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const router = useRouter()

const data = ref([
    {
        id: '1',
        title: 't1',
        desc: 'd1',
        owner: 'o1',
    },
    {
        id: '2',
        title: 't2',
        desc: 'd2',
        owner: 'o1',
    },
    {
        id: '3',
        title: 't3',
        desc: 'd3',
        owner: 'o1',
    },
    {
        id: '4',
        title: 't4',
        desc: 'd4',
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