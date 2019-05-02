<template>
  <div class="form">
    <Login />
    <Register />
  </div>
</template>

<script>

import Register from "@/components/Register.vue"
import Login from "@/components/Login.vue"

export default {
  components:{
    Register,
    Login
  }
}
</script>

<style scoped>
.form{
  overflow: hidden;
  display: flex;   
  height: 70vh;
  margin-left: 6vw;
}
</style>
