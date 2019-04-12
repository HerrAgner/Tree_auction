<template xmlns:v-slot="http://www.w3.org/1999/XSL/Transform">
  <div class="about">
    <v-dialog v-model="dialog" width="500">
      <template v-slot:activator="{ on }">
        <v-btn color="red lighten-2" dark v-on="on">
          Add image
        </v-btn>
      </template>

      <v-card>
        <v-card-title class="headline grey lighten-2" primary-title>
          Add image
        </v-card-title>
        <v-card-text>
          <profile id="upload"></profile>
          <!--                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et-->
          <!--                dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip-->
          <!--                ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu-->
          <!--                fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia-->
          <!--                deserunt mollit anim id est laborum.-->
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" flat @click="addImage">
            I accept
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <div>
      <img :src="currentImage" alt="" id="imageSrc" />
    </div>
  </div>
</template>

<script>
import Profile from "@/components/Profile.vue";

export default {
  components: {
    Profile
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
