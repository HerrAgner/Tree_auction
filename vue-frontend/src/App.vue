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
          @click="goToChatRoom"
        >
          Go to chatroom
        </v-btn>

        <v-btn
          dark
          flat
          @click="snackbar = false"
        >
          close
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
import router from '@/router.js'

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
  methods: {
    chatroomData(){
      let chatroomData = this.$store.state.message.filter((p) => {
        return p.id == this.$store.state.chatroomID
      })
    return chatroomData
    },
    goToChatRoom(){
      router.push({ path: '/chat/' + this.chatroomData()[0].id})
      this.snackbar = false
      }
    },
    async created() {
    this.$store.dispatch("init");
  },
  watch: {
    receivedMessage(newValue, oldValue){
        if (newValue){
          if (this.$store.state.userInfo.email !== this.chatroomData()[0].senderID){
            this.text = this.chatroomData()[0].senderID + ': ' + this.chatroomData()[0].message;
            this.$store.state.receivedMessage = false
            if (this.$route.params['id'] !== this.chatroomData()[0].id){
               this.snackbar = newValue;
            }
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
