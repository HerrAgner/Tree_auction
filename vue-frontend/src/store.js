import Vue from "vue";
import Vuex from "vuex";
import router from '@/router.js'

Vue.use(Vuex);
const API_URL = "http://localhost:7999/api/";
const API_URL2 = "http://localhost:7999/login";

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
    currentBids: null,

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
    }
  },
  actions: {
    async getUsersFromDb() {
      let users = await (await fetch(API_URL + "users")).json().catch(e => {});
      console.log(users);
      
      return users;
    },
    async getUserInfoFromDb(context, email) {  
      let user = await (await fetch(API_URL2 + "/" + email)).json().catch(e => {});
      if (user) {
        console.log(user);
        this.commit("setUserInfo", user);
      }
      return user;
    },
    async login(context, info) {       
      await fetch(API_URL2, {
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
        }
        
      })
      
    },

    async addUserToDB(state, reqBody) {      
      await fetch(API_URL2 + "/", {
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
      console.log('hellllllo');
      await fetch(API_URL + "auctions", {
        method: "POST",
        body: JSON.stringify(reqBody),
        headers: { "Content-Type": "application/json" }
      });
      

      // Update the state.blogPosts since we just added a new one
      this.dispatch("getAuctionsFromDb");
    },

    async addBidToDb(state, reqBody) {
      console.log('helllllo');      
      await fetch(API_URL + "bids", {
        method: "POST",
        body: JSON.stringify(reqBody),
        headers: {"Content-Type": "application/json"}
      });
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
      this.commit("setCurrentBids", bids);
    },
  }
});
