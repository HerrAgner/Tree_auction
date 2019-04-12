<template>
  <div id="image-box">
    <img :src="profilePicture" alt="Profile picture" id="image" />
  </div>
</template>

<script>
export default {
  name: "ProfilePicture",
  computed: {
    profilePicture() {
      return this.$store.state.profilePicture;
    }
  }
};
</script>

<style scoped>
#image-box {
  max-width: 90vw;
}
#image {
  max-width: 100%;
}
</style>
