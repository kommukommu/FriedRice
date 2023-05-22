<template>
    <div style="margin: 50px;">
        <el-form ref="ruleFormRef" :model="form" :rules="rules" label-width="120px" label-position="top" status-icon>
            <el-form-item label="用户名" prop="name">
                <el-input v-model="form.name" placeholder="Please input username" />
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input v-model="form.password" type="password" placeholder="Please input password" show-password />
            </el-form-item>
            <el-form-item label="确认密码" prop="checkpassword">
                <el-input v-model="form.checkpassword" type="password" placeholder="Please confirm password"
                    show-password />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm(ruleFormRef)" :loading="isLoading">注册</el-button>
                <el-button @click="jump('Login')">登录</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script setup>
import axios from 'axios';
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router';

const ruleFormRef = ref()
const isLoading = ref(false)

const validatePass = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('Please input the password'))
    } else {
        if (form.checkpassword !== '') {
            if (!ruleFormRef.value) return
            ruleFormRef.value.validateField('checkpassword', () => null)
        }
        callback()
    }
}
const validatePass2 = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('Please input the password again'))
    } else if (value !== form.password) {
        callback(new Error("Two inputs don't match!"))
    } else {
        callback()
    }
}

const rules = reactive({
    name: [
        { min: 3, max: 20, message: 'Length should be 3 to 20', trigger: 'blur' },
        { required: true, message: 'Please input Username', trigger: 'blur' },

    ],
    password: [
        { required: true, message: 'Please input the password', trigger: 'blur' },
        { validator: validatePass, trigger: 'blur' }],
    checkpassword: [
        { required: true, message: 'Please input the password again', trigger: 'blur' },
        { validator: validatePass2, trigger: 'blur' }],
})

// do not use same name with ref
const form = reactive({
    name: '',
    password: '',
    checkpassword: '',
})

const submitForm = (formEl) => {
    if (!formEl) return
    console.log(form);
    formEl.validate((valid) => {
        if (valid) {
            console.log('submit!')
            signup()
        } else {
            console.log('error submit!')
            return false
        }
    })
}

const signup = () => {
    isLoading.value = true
    axios.post('/User', {
        name: form.name,
        password: form.password
    })
        .then(function (response) {
            const res = response.data
            isLoading.value = false
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