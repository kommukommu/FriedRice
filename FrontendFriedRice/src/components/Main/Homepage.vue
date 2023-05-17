<template>
    <div>
        <el-space direction="vertical" fill :fill-ratio="98" class="container">
            <!-- <template class="space" /> -->
            <el-card v-for="i in 4" :key="i" class="box-card" shadow="hover" @click="openNewProject($event, i)">
                <template #header>
                    <div class="card-header">
                        <span>{{ '项目' + i }}</span>
                    </div>
                    <div class="writer">
                        <span v-for="j in 4">{{ '作者' + j }}</span>
                    </div>
                </template>
                <div v-for="o in 4" :key="o" class="text item">{{ 'List item ' + o }}</div>
            </el-card>
            <template class="space" />
        </el-space>
    </div>
</template>
<script setup>
import { useRoute,useRouter } from 'vue-router';

const router = useRouter()

function openNewProject(event, i) {
    // alert(i)
    console.log(router.push({
        name:"Project"
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