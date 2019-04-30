<template>
  <Chatroom class="fix"/>
</template>

<script>

import Chatroom from "@/components/Chatroom.vue"

export default {
  components:{
      Chatroom
  }
}
</script>

<style scoped>
.fix{
  overflow: hidden;
}
</style>
