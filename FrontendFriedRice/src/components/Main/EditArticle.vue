<template>
    <el-space v-if="isWriter" direction="vertical" fill :fill-ratio="98" class="container">
        <!-- <template class="space" /> -->
        <div>
            <el-button style="margin: 10px;" type="primary" @click="back">返回</el-button>
            <!-- <el-button @click="isVisible = false">Cancel</el-button> -->
            <el-button @click="changeText">
                Submit
            </el-button>
            <!-- <el-button v-if="isWriter" style="margin: 10px;" @click="initialiseChange">修改正文</el-button> -->
        </div>
        <el-card class="title-card" shadow="hover">
            <template #header>
                <div class="card-header">
                    <span>{{ articleData.title }}</span>
                </div>
                <div class="writer">
                    <span>{{ '作者：' + articleData.writerName }}</span>
                </div>
            </template>
            <div class="text item">{{ articleData.lastChange }}</div>
        </el-card>
        <el-card v-show="isWriter" class="requirement-card" shadow="hover">
            <template #header>
                <div class="card-header">
                    <span>写作要求</span>
                </div>
            </template>
            <!-- <div class="text item">
                <p>{{ requirement }}</p>
            </div> -->
            <v-md-preview :text="articleData.requirement"></v-md-preview>
        </el-card>
        <el-card class="body-card" shadow="hover">
            <!-- <div v-for="o in 4" :key="o" class="text item">{{ '正文 ' + o }}</div> -->
            <v-md-preview :text="text"></v-md-preview>
        </el-card>
        <el-card>
            <v-md-editor v-model="textChange" height="400px"></v-md-editor>
            <!-- <template>
                <span class="dialog-footer">

                </span>
            </template> -->
        </el-card>
    </el-space>
</template>
<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

// const isVisible = ref(false)
const route = useRoute()

onMounted(() => {
    articleData.id = route.params.articleID
    getArticleData()
    getText()
})

function getText() {
    axios.get('/Body/Latest/' + articleData.id)
        .then(function (response) {
            const res = response.data

            console.log(response);
            if (res.code == 0) {
                // ElMessage({
                //     message: res.message,
                //     type: 'success',
                // })
                textChange.value = text.value = res.body.body
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

function getArticleData() {
    axios.get('/Article/ID/' + articleData.id)
        .then(function (response) {
            const res = response.data

            console.log(response);
            if (res.code == 0) {
                // ElMessage({
                //     message: res.message,
                //     type: 'success',
                // })
                articleData.id = res.article.id
                articleData.title = res.article.title
                articleData.writerName = res.article.writerName
                articleData.writer = res.article.writer
                articleData.writerName = res.article.writerName
                articleData.lastChange = res.article.lastChange
                if ('requirement' in res.article) {
                    isWriter.value = true
                    articleData.requirement = res.article.requirement

                } else {
                    isWriter.value = false
                }
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

const textChange = ref('')

function changeText() {
    console.log(textChange.value);
}

const articleData = reactive({
    id: 0,
    title: '标题',
    writerName: '作者',
    writer: -1,
    project: -1,
    requirement: `
yaoqiu
123
要求
`,
    lastChange: '2000-01-01'
})

const isWriter = ref(false)

const text = ref(`
# 一级标题
## 二级标题
### 三级标题
# 一级标题
## 二级标题
### 三级标题
`)
const router = useRouter()

function back() {
    // console.log(router);
    router.back()
}

function jump(name) {
    // console.log(router);
    router.push({
        name,
    })
}
</script>
<style scoped>
.container {
    height: auto;
    width: 100%;
    margin: 8px 0;
}

.space {
    height: 8px;
}

.writer {
    text-align: right;
}
</style>