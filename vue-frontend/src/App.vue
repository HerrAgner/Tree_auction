<template>
  <v-app id="app">
    <BottomNav />
    <v-content class="content">
      <router-view />
    </v-content>
    <div id="messages"></div>


    <div id="app">
  <v-app id="inspire">
    <v-card>
      <v-snackbar
        v-model="snackbar"
        :color="color"
        :multi-line="mode === 'multi-line'"
        :timeout="timeout"
        :vertical="mode === 'vertical'"
      >
        {{ text }}
        <v-btn
          dark
          flat
          @click="snackbar = false"
        >
          Close
        </v-btn>
      </v-snackbar>
    </v-card>
  </v-app>
</div>



    <BottomFooter />
  </v-app>
</template>

<script>
import BottomNav from "./components/BottomNav";
import BottomFooter from "./components/BottomFooter";
import { mapState } from 'vuex';

export default {
  name: "App",
  components: {
    BottomNav,
    BottomFooter
  },
  data() {
    return {
      snackbar: false,
      color: 'green',
      mode: '',
      timeout: 15000,
      text: '',
      bottomNav: "recent"
    };
  },
  async created() {
    this.$store.dispatch("init");
  },
  watch: {
    receivedMessage(newValue, oldValue){
        if (newValue){
          if (this.$store.state.userInfo.email !== this.$store.state.senderID){
            this.snackbar = newValue;
            this.text = this.$store.state.senderID + ': ' + this.$store.state.message;
          }
        }
    }
  },
  computed: mapState(['receivedMessage']),

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
