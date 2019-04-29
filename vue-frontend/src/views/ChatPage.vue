<template>
        <v-content>
            <v-container fluid fill-height>
            <v-layout align-center justify-center>
                <v-flex xs12 sm6 md6>
                <v-card class="elevation-12" color="grey lighten-2">
                    <v-toolbar dark color="grey darken-3">
                    <v-toolbar-title>Wellcome to the chat</v-toolbar-title>
                    </v-toolbar>
                    <v-card-text>
                <v-list ref="chat" id="logs">
                <template v-for="(item, index) in logs">
                    <v-subheader v-if="item" :key="index">{{ item }}</v-subheader>
                </template>
                </v-list>
                    </v-card-text>
                    <v-card-actions>
                    <v-form @submit.prevent="submit">
                    <v-text-field v-model="msg" label="Message" single-line solo-inverted></v-text-field>
                    <v-btn fab dark small color="primary" type="submit" @click="send">
                        <v-icon dark>send</v-icon>
                    </v-btn>
                    {{messageToClient}}
                        </v-form>
                    </v-card-actions>
                </v-card>
                </v-flex>
            </v-layout>
            </v-container>
        </v-content>
</template>

<script>

import {ws} from '@/main.js'
import { mapState } from 'vuex';
import router from '@/router.js'

export default {
data(){
    return{
    messageToClient: '',
    logs: [],
    msg: null,
    chat: null
}},
methods: {
    chatroomData(){
        let chatroomData = this.$store.state.message.filter((p) => {
        	return p.id == this.$store.state.chatroomID
        })
        return chatroomData
    },
    send(){
        if(this.msg === null){
            this.messageToClient = 'You have to typ something first';
        }
        else if (this.$store.state.userInfo.email === this.$store.state.receiverID){
            ws.send(JSON.stringify({type: 'chat',chatroomID: this.$route.params['id'], senderID: this.$store.state.userInfo.email , receiverID: this.chatroomData().slice(-1)[0].senderID, message: this.msg}))
        }
        else{
            ws.send(JSON.stringify({type: 'chat', chatroomID: this.$route.params['id'], senderID: this.$store.state.userInfo.email , receiverID: this.$store.state.currentSeller.email, message: this.msg}))
        }
    },
    submit() {
      this.logs.push('Me: ' + this.msg);
      if (this.$store.state.currentSeller.email !== this.$store.state.userInfo.email){
        this.msg = "";
      }
    }
  },
watch: {
    '$route' (to, from){
        this.logs = this.chatroomData().slice(-1).map(obj => obj.senderID + ': ' + obj.message);
    },
    logs() {
      setTimeout(() => {
        this.$refs.chat.$el.scrollTop = this.$refs.chat.$el.scrollHeight;
      }, 0);
    },
    message(newValue, oldValue) {
        if (this.$store.state.userInfo.email !== this.chatroomData().slice(-1)[0].senderID){
            if (this.$route.params['id'] === this.chatroomData().slice(-1)[0].id ){
                if (this.$route.params['user'] === this.chatroomData().slice(-1)[0].senderID || this.$route.params['user'] === this.chatroomData().slice(-1)[0].receiverID){
                    this.logs.push(this.$store.state.senderID + ': ' + this.chatroomData().slice(-1)[0].message);
                }
            }
        }
        this.msg = "";
    }
  },
computed: mapState(['message']),

created() {    
    if (this.$store.state.status === false) {
        this.$router.push({ path: "/login" });
    }else{
        if (this.$store.state.userInfo.email !== this.chatroomData()[0].senderID){
            this.logs = this.chatroomData().map(obj => obj.senderID + ': ' + obj.message);
        }
    }
  },
    
}
</script>
    
<style scoped>
.primary{
    margin-top: 0px;
}
#logs {
    height: 40vh;
    overflow: auto;
}
</style>
