<template>
  <v-app id="app">
    <BottomNav />
    <v-content class="content">
      <router-view />
    </v-content>
    <div id="messages"></div>
    <Notification v-if="showNotification" :auctionLink="this.$store.state.notification.notis.url" :text="text" :key="text" />
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
    showNotification() {          

      if (this.$store.state.notification.notis){
        this.text ="Du har blivit överbudat i auktionen \n '"+ this.$store.state.notification.notis.title+"' \n"
        +"det vinnande budet ligger nu på "+this.$store.state.notification.notis.amount
       }
      return this.$store.state.notification.show;    
    },
   
  },
  data() {
    return {
      bottomNav: "recent",
      text: "", 
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
