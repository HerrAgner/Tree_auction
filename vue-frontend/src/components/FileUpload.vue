<template>
  <div>
    <input type="file" @change="upload" />
  </div>
</template>

<script>
export default {
  name: "FileUpload",
  methods: {
    upload(e) {
      let image = e.target.files[0];
      let reader = new FileReader();
      reader.readAsDataURL(image);
      reader.onload = e => {
        let imageData = e.target.result;
        this.$store.commit("setProfilePicture", imageData);
      };
    }
  }
};
</script>

<style scoped></style>
