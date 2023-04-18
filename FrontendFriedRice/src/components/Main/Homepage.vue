<template>
    <div>
        <el-space direction="vertical" fill fill-ratio=98 class="container">
            <template class="space" />
            <el-card v-for="i in 4" :key="i" class="box-card" shadow="hover" @click="openNewProject">
                <template #header>
                    <div class="card-header">
                        <span>Card name</span>
                    </div>
                </template>
                <div v-for="o in 4" :key="o" class="text item">{{ 'List item ' + o }}</div>
            </el-card>
            <template class="space" />
        </el-space>
    </div>
</template>
<script setup>
function openNewProject (event){
    alert(i)
    console.log(event);
}
</script>
<style scoped>
.container{
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
</style>