<template>
  <v-app id="app">
    <BottomNav />
    <v-content class="content">
      <router-view />
    </v-content>
    <div id="messages"></div>
    <Notification v-if="showNotification" :text="text" />
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
  computed: {
    /*getNotification() {
      
      return this.$store.state.notification;    
    },*/
    async showNotification() {    
      // DU ÄR HÄR: notis appendar vid rätt läge så nu vill vi fetcha lite info om budet som lagts 
      //så vi kan skräddaresy meddlendet och göra det till en länk till rätt auktion  
      await console.log(this.$store.state.notification.data);

      return this.$store.state.notification.show;    
    },
  },
  data() {
    return {
      bottomNav: "recent",
      text: "Någon har överbudat dig"
    };
  },
  async created() {
    this.$store.dispatch("init");
  },
  
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
