<script setup>
import { onMounted } from 'vue'
import axios from 'axios';
import { store } from "./store"

onMounted(() => {
  axios.get('/Login')
    .then(
      function (response) {
        const res = response.data

        console.log(response);
        if (res.code == 0) {
          store.logIn(res.id, res.name)
        }
      }
    )
    .catch(function (error) {
      console.log(error);
    });
})
</script>

<template>
  <Layout />
</template>

<style scoped></style>
