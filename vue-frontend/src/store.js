import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);
const API_URL = "http://localhost:7999/api/";
export default new Vuex.Store({
  state: {
    profilename: "",
    header: "",
    profilePicture: "",
    text: "",
    auctions: [],
    currentAuction: "",
    currentSeller: ""
  },
  mutations: {
    setProfilePicture(state, image) {
      state.profilePicture = image;
    },
    setAuctions(state, auctions) {
      state.auctions = auctions;
    },
    setCurrentAuction(state, auction) {
      state.currentAuction = auction;
    },
    setCurrentSeller(state, seller) {
      state.currentSeller = seller;
    }
  },
  actions: {
    async getUsersFromDb() {
      let users = await (await fetch(API_URL + "users")).json();
      return users;
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
