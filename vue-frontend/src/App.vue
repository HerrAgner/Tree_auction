<template>
  <v-app id="app">
    <BottomNav />
    <v-content class="content">
      <router-view />
    </v-content>
    <div id="messages"></div>
    <Notification :text="text" />
    <BottomFooter />
  </v-app>
</template>

<script>
import BottomNav from "./components/BottomNav";
import BottomFooter from "./components/BottomFooter";
import Notification from "./components/Notification";


export default {
  name: "App",
  components: {
    BottomNav,
    BottomFooter,
    Notification
  },
  data() {
    return {
      bottomNav: "recent",
      showSnackbar: true,
      text: "hej"
    };
  },
  async created() {
    this.$store.dispatch("init");
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
  margin-top: 100px;
}

#nav {
  width: 100vw;
  justify-content: left;
  top: 0;
  position: fixed;
}
</style>
