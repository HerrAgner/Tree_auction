<template>
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
</template>

<script>
import { mapState } from 'vuex';
import router from '@/router.js'

export default {
  data() {
    return {
      snackbar: false,
      color: 'green',
      mode: '',
      timeout: 15000,
      text: '',
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
      router.push({ path: '/chat/' + this.chatroomData().slice(-1)[0].id + '/' + this.chatroomData().slice(-1)[0].senderID})
      this.snackbar = false
      }
    },
    async created() {
    this.$store.dispatch("init");
  },
  watch: {
    receivedMessage(newValue, oldValue){
        if (newValue){
          if (this.$store.state.userInfo.email !== this.chatroomData().slice(-1)[0].senderID){              
            this.text = this.chatroomData().slice(-1)[0].senderID + ': ' + this.chatroomData().slice(-1)[0].message;
            this.$store.state.receivedMessage = false;

            if (this.$route.params['user'] !== this.chatroomData().slice(-1)[0].senderID){
              if (this.$route.params['user'] !== this.$store.state.userInfo.email){
                this.snackbar = newValue;
              }
            }
            if (this.$route.params['id'] !== this.chatroomData().slice(-1)[0].id){              
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

</style>
