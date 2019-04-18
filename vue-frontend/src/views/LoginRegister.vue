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
  height: 60vh;
  margin-left: 6vw;
}

@media screen and (max-width:667px ){
  .form{
    width: 90vw;
    margin-top: 4vh;
    margin-left: 5vw;
    display: inline-block;
    overflow: scroll;
    height: 80vh;
  }
}
</style>
