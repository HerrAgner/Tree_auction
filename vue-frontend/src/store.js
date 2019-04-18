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
    userEmail: '',
    currentAuction: "",
    currentSeller: "",
    images: [],
    
  },
  mutations: {
    setProfilePicture(state, image) {
      state.profilePicture = image;
    },
    setAuctions(state, auctions) {
      state.auctions = auctions;
    },
    setUserEmail(state, userEmail) {
      state.userEmail = userEmail;
    },
    setCurrentAuction(state, auction) {
      state.currentAuction = auction;
    },
    setCurrentSeller(state, seller) {
      state.currentSeller = seller;
    },
    setUploadedImage(state, image) {
      state.images = image
    }
  }, getters: {
    getUplodedImage: state => {
      return state.uploadedImage
    }
  },
  actions: {
    async getUsersFromDb() {
      let users = await (await fetch(API_URL + "users")).json();
      return users;
    },
    async getUserEmailFromDb(context, email) {
      let user = await (await fetch(API_URL + "users/" + email)).json().catch(e => {});
      if (user) {
        this.commit("setUserEmail", user.email);
      }
      return user;
    },
    async getBidsFromDb() {
      let bids = await (await fetch(API_URL + "bids")).json();
      return bids;
    },
    async getAuctionsFromDb() {
      let auctions = await (await fetch(API_URL + "auctions")).json();
      this.commit("setAuctions", auctions);

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

    async addPictureToDB(state, reqBody) {
      console.log("I addPictureToDB")
      await fetch(API_URL + "pictures", {
        method: "POST",
        body: JSON.stringify(reqBody),
        headers: {'Content-Type': 'multipart/form-data'}
      });
      console.log("Efter addPictureToDB")
    },


    async addUserToDB(state, reqBody) {
      await fetch(API_URL + "users", {
        method: "POST",
        body: JSON.stringify(reqBody),
        headers: { "Content-Type": "application/json" }
      });

      // Update the state.blogPosts since we just added a new one
      this.dispatch("getUsersFromDb");
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
