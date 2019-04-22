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
    status: true,
    userInfo: [],
    currentAuction: "",
    currentSeller: ""
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
    }
  },
  actions: {
    async getUsersFromDb() {
      let users = await (await fetch(API_URL + "/users")).json().catch(e => {});
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
      .then(function(response) {
        let successfulLogin = !response.url.includes("error");
        console.log("the login result is:", successfulLogin);
        if(successfulLogin){
          //this.commit("setStatus", successfulLogin);
          //router.push({ path: '/' })          
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
      await fetch(API_URL + "auctions", {
        method: "POST",
        body: JSON.stringify(reqBody),
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
    },


    async getOneAuction(context, auction) {
      let currAuction = await fetch(API_URL + "auctions/" + auction).then(res =>
        res.json()
      );
      this.commit("setCurrentAuction", currAuction);
    },
    async getSellerName(context, user) {
      let currSeller = await (await fetch(API_URL + "users/" + user)).json();
      console.log(currSeller)
      this.commit("setCurrentSeller", currSeller);
    }
  }
});
