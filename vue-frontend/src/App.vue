<template>
  <v-app id="app">
    <v-content class="content">
      <router-view />
    </v-content>
    <BottomNav />
  </v-app>
</template>

<script>
import HelloWorld from "./components/HelloWorld";
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
  max-height: calc(100vh - 5vh);
}

#nav {
  width: 100vw;
  justify-content: space-between;
  bottom: 0;
  position: fixed;
}
</style>
