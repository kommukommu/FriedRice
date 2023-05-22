<template>
    <div style="margin: 50px;">
        <el-form :model="form" label-width="120px" label-position="top">
            <el-form-item label="用户名">
                <el-input v-model="form.name" placeholder="Please input username" />
            </el-form-item>
            <el-form-item label="密码">
                <el-input v-model="form.password" type="password" placeholder="Please input password" show-password />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit" :loading="isLoading">登录</el-button>
                <el-button @click="jump('Signup')">注册</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script setup>
import axios from 'axios'
import { store } from '../../store'
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router';

const isLoading = ref(false)

// do not use same name with ref
const form = reactive({
    name: '',
    password: '',
})

const onSubmit = () => {
    isLoading.value = true
    axios.post('/Login', {
        name: form.name,
        password: form.password
    })
        .then(function (response) {
            isLoading.value = false
            const res = response.data
            
            console.log(response);
            if (res.code == 0) {
                ElMessage({
                    message: res.message,
                    type: 'success',
                })
                store.logIn(res.id, res.name)
                // store.isLogedIn = true
                jump('Home')
            } else {
                ElMessage({
                    message: res.message,
                    type: 'error',
                })
            }
        })
        .catch(function (error) {
            isLoading.value = false
            console.log(error);
        });
    console.log('submit!')
}

const router = useRouter()

function jump(name) {
    // console.log(router);
    router.push({
        name,
    })
}
</script>
<style scoped></style>