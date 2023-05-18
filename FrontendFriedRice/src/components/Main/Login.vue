<template>
    <div style="margin: 50px;">
        <el-form :model="form" label-width="120px" label-position="top">
            <el-form-item label="Username">
                <el-input v-model="form.name" placeholder="Please input username" />
            </el-form-item>
            <el-form-item label="Password">
                <el-input v-model="form.password" type="password" placeholder="Please input password" show-password />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">Log in</el-button>
                <el-button @click="jump('Signup')">Sign up</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script setup>
import { reactive } from 'vue'
import { useRoute, useRouter } from 'vue-router';

// do not use same name with ref
const form = reactive({
    name: '',
    password: '',
})

const onSubmit = () => {
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