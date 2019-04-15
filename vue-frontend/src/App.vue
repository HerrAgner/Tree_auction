<template>
  <v-app id="app">
    <v-content class="content">
      <router-view />
    </v-content>
    <BottomNav />
  </v-app>
</template>

<script>
import BottomNav from "./components/BottomNav";

export default {
  name: "App",
  components: {
    BottomNav
  },
  data() {
    return {
      bottomNav: "recent"
    };
  },
  created() {
    // this.$store.dispatch("addPostToDB", { name: "sup", populationInMillions: 2 });
    this.$store.dispatch("getPostsFromDb");
  }
};
</script>

<style>
#app {
  display: flex;
  min-height: 100vh;
  flex-direction: column;
}

.content {
  margin-top: 50px;
}

#nav {
  width: 100vw;
  justify-content: left;
  top: 0;
  position: fixed;
}
</style>
