<template>
  <div>
    <FileUpload />
    <ProfilePicture />
    <ul>
      <li v-for="post in asd">{{ post }}</li>
    </ul>
    <ul>
      <li v-for="(item, index) in list" :key="index">{{ item.color }}</li>
    </ul>
  </div>
</template>

<script>
import FileUpload from "@/components/FileUpload.vue";
import ProfilePicture from "@/components/ProfilePicture.vue";
export default {
  name: "Profile.vue",
  components: {
    FileUpload,
    ProfilePicture
  },
  props: {
    name: String,
    list: Array
  },
  methods: {
  
  },
  computed: {
    asd() {
      return this.$store.state.posts;
    }
  }
};
</script>

<style scoped></style>
