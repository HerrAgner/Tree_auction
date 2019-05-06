<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div class="about">
    <v-dialog v-model="dialog" width="500">

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="primary" flat @click="addImage">I accept</v-btn>
      </v-card-actions>
    </v-dialog>
    <div>
      <img :src="currentImage" alt id="imageSrc">
    </div>
  </div>
</template>

<script>

export default {
  components: {
  },
  data() {
    return {
      dialog: false,
      profilePic: ""
    };
  },
  methods: {
    addImage() {
      this.dialog = false;
      this.profilePic = this.$store.state.profilePicture;
    }
  },
  computed: {
    currentImage() {
      return this.profilePic;
    }
  }
};
</script>

<style scoped>
.about {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
</style>
