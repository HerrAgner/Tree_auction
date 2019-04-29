import Vue from "vue";
import Vuex from "vuex";
import router from '@/router.js'
import {ws, connect, disconnect} from '@/main.js'


Vue.use(Vuex);
const API_URL = "http://localhost:7999/api/";
const API_URLLog = "http://localhost:7999/";

function transformRequest(jsonData = {}){
  return Object.entries(jsonData)
    .map(x => `${encodeURIComponent(x[0])}=${encodeURIComponent(x[1])}`)
    .join('&');
}

export default new Vuex.Store({
  state: {
    auctions: [],
    status: false,
    userInfo: {},
    currentAuction: "",
    currentSeller: "",
    currentSellerID: null,
    currentBids: null,
    message:[],
    senderID: null,
    receivedMessage: false,
    chatroomID: null,
    goTochatRoom: false,
    receiverID: null

  },
  mutations: {
    setAuctions(state, auctions) {
      state.auctions = auctions;
    },
    setUserInfo(state, user) {      
      state.userInfo = user;
    },
    setCurrentAuction(state, auction) {
      state.currentAuction = auction;
    },
    setCurrentSeller(state, seller) {
      state.currentSeller = seller;
    },
    setStatus(state, status){
      state.status = status;
    },
    setAmountOfAuctions(state, amountOfAuctions) {
      state.amountOfAuctions = amountOfAuctions;
    },
    setCurrentBids(state, currBids){
      state.currentBids = currBids;
    },
    setSenderID(state, senderID){
      state.senderID = senderID;
    },
    setReceivedMessage(state, boolean){
      state.receivedMessage = boolean
    },
    setChatroomID(state, chatroomID){
      state.chatroomID = chatroomID
    },
    setReceiverID(state, receiverID){
      state.receiverID = receiverID
    },
    setChatroomObject(state, data){
      let message = {id: data.chatroom_id, message: data.message, senderID: data.sender_id, receiverID: data.receiver_id }
      // state.message.push[message]
      Vue.set(state.message, state.message.length, message);
      // console.log(state.message);
      
    }
  },
  actions: {
    init() {
      ws.onmessage = (e) => {
        let data = JSON.parse(e.data);
        if(data.type === "bid") {
          if (data.auctionId === this.state.currentAuction.id) {
            this.dispatch("getBidsForOneAuction", data.auctionId);
          }
        }
        else if (data.type === "chat"){
            this.commit("setSenderID", data.sender_id)
            this.commit("setReceivedMessage", true)
            this.commit("setChatroomID", data.chatroom_id)
            this.commit("setReceiverID", data.receiver_id)

            this.commit("setChatroomObject", data)
        }
      }
    },
    async getUsersFromDb() {
      let users = await (await fetch(API_URL + "users")).json().catch(e => {});      
      return users;
    },
    async getUserInfoFromDb(context, email) {  
      let user = await (await fetch(API_URL + "users/" + email)).json().catch(e => {});
      if (user) {
        this.commit("setUserInfo", user);
      }
      return user;
    },
    async login(context, info) {       
      await fetch(API_URLLog + 'login', {
        method: "POST",
        body: transformRequest({username: info.email, password: info.password}),
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      })
      .then(response => {
        let successfulLogin = !response.url.includes("error");
        console.log("the login result is:", successfulLogin);
        if(successfulLogin){
          this.commit("setStatus", successfulLogin);
          router.push({ path: '/' }) 
          this.dispatch('getUserInfoFromDb', info.email)
            console.log('hello from true');
            ws.send(JSON.stringify({type: 'login', loginID: info.email}))
        }
      })
    },
    async logout(context) {       
      await fetch(API_URLLog + 'logout', {
        method: "GET",
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      })
      .then(response => {
        let successfulLogout = !response.url.includes("error");
        console.log("the logout result is:", successfulLogout);        
        if(successfulLogout){
          this.commit("setStatus", !successfulLogout);
          router.push({ path: '/' }) 
          disconnect();
          connect();
        }
      })
    },
    async saveMessageToDB(state, reqBody) {      
      await fetch(API_URL + "chat", {
        method: "POST",
        body: JSON.stringify(reqBody),
        headers: { "Content-Type": "application/json" }
      }).catch(e => {});

      // Update the state.blogPosts since we just added a new one
      // this.dispatch("getUsersFromDb");
    },
    async addUserToDB(state, reqBody) {      
      await fetch(API_URL + "users", {
        method: "POST",
        body: JSON.stringify(reqBody),
        headers: { "Content-Type": "application/json" }
      }).catch(e => {});

      // Update the state.blogPosts since we just added a new one
      // this.dispatch("getUsersFromDb");
    },

    async getBidsFromDb() {
      let bids = await (await fetch(API_URL + "bids")).json();
      return bids;
    },
    async getAuctionsFromDb() {
      let auctions = await (await fetch(API_URL + "auctions")).json();
      this.commit("setAuctions", auctions);
      // this.commit("setCurrentAuction", auctions[0]);
    },
    async addAuctionToDB(state, reqBody) {
      await fetch(API_URL + "auctions", {
        method: "POST",
        body: reqBody,
        headers: { "Content-Type": "application/json" }
      });
      

      // Update the state.blogPosts since we just added a new one
      this.dispatch("getAuctionsFromDb");
    },

    async addBidToDb(state, reqBody) {
      await fetch(API_URL + "bids", {
        method: "POST",
        body: JSON.stringify(reqBody),
        headers: {"Content-Type": "application/json"}
      });
      ws.send(JSON.stringify(reqBody))
    },


    async getOneAuction(context, auction) {
      let currAuction = await fetch(API_URL + "auctions/" + auction).then(res =>
        res.json()
      );
      this.commit("setCurrentAuction", currAuction);
    },
    async getSeller(context, user) {
      let currSeller = await (await fetch(API_URL + "users/" + user)).json();
      this.commit("setCurrentSeller", currSeller);
    },
    async getBidsForOneAuction(context, auctionId) {
      let bids = await (await fetch(API_URL + "bids/" + auctionId)).json();
      bids.sort((a, b) => b.amount - a.amount);

      this.commit("setCurrentBids", bids);
      return bids;
    },
  }
});