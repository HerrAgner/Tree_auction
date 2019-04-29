import Vue from "vue";
import Vuex from "vuex";
import router from "@/router.js";
import { ws } from "@/main.js";

Vue.use(Vuex);
const API_URL = "http://localhost:7999/api/";
const API_URLLog = "http://localhost:7999/";

function transformRequest(jsonData = {}) {
  return Object.entries(jsonData)
    .map(x => `${encodeURIComponent(x[0])}=${encodeURIComponent(x[1])}`)
    .join("&");
}

export default new Vuex.Store({
  state: {
    auctions: [],
    status: false,
    userInfo: {},
    searchAuctions: [],
    currentAuction: "",
    currentSeller: "",
    notification : { show: false },
    latestAddedAuction: "",
    images: [],
    currentBids: [],
    userBids: null,
  },
  mutations: {
    setAuctions(state, auctions) {
      state.auctions = auctions;
    },
    setSearchAuctions(state, auctions) {
      state.searchAuctions = auctions;
    },
    setUserInfo(state, user) {
      state.userInfo = user;
    },
    setLatestAddedAuction(state, auction) {
      state.latestAddedAuction = auction.id;
    },
    setCurrentAuction(state, auction) {
      state.currentAuction = auction;
    },
    setCurrentImages(state, images) {
      state.images = images
    },
    setCurrentSeller(state, seller) {
      state.currentSeller = seller;
    },
    setStatus(state, status) {
      state.status = status;
    },
    setAmountOfAuctions(state, amountOfAuctions) {
      state.amountOfAuctions = amountOfAuctions;
    },
    setCurrentBids(state, currBids) {
      state.currentBids = currBids;
    },
    setUserBids(state, bids) {
      state.userBids = bids;
    },
    setSearchAuctionBids(state, { bids, index }) {
      state.searchAuctions[index].bidsAmount = bids.length;
      Vue.set(
        this.state.searchAuctions,
        index,
        this.state.searchAuctions[index]
      );
    }
  },
  actions: {
    init() {
      ws.onmessage = async e => {
        let data = JSON.parse(e.data);
        if (data.type === "bid") {
          let previousBids = await this.dispatch(
            "returnBidsForOneAuction",
            data.auctionId
          );
          if (data.auctionId === this.state.currentAuction.id) {
            // let bids = await this.dispatch("returnBidsForOneAuction", data.auctionId);
            await this.commit("setCurrentBids", previousBids);
          }
          let index = this.state.searchAuctions.findIndex(
            a => a.id === data.auctionId
          );
          if (index !== -1) {
            this.state.searchAuctions[index].highestBid = data.amount;
            this.dispatch("getBidsForOneAuction", data.auctionId);
            Vue.set(
              this.state.searchAuctions,
              index,
              this.state.searchAuctions[index]
            );
          }
          console.log(previousBids);
          
          //om currUser tidigare hade högst bud på auktionen i fråga - gör notifikation 
          if(previousBids[1].bidderId === this.state.userInfo.email){
            console.log("DU ÄGDE AUKTIONEN MEN NU ÄR DU ÖVERBJUDEN!!!");
            this.state.notification = { 
              show: true, 
              notis: data
            }
          }


          
        }
      };
    },
    async getUsersFromDb() {
      let users = await (await fetch(API_URL + "users")).json().catch(e => {});
      return users;
    },
    async getUserInfoFromDb(context, email) {
      let user = await (await fetch(API_URL + "users/" + email))
        .json()
        .catch(e => {});
      if (user) {
        this.commit("setUserInfo", user);
      }
      return user;
    },
    async login(context, info) {
      await fetch(API_URLLog + "login", {
        method: "POST",
        body: transformRequest({
          username: info.email,
          password: info.password
        }),
        headers: { "Content-Type": "application/x-www-form-urlencoded" }
      }).then(response => {
        let successfulLogin = !response.url.includes("error");
        console.log("the login result is:", successfulLogin);
        if (successfulLogin) {
          this.commit("setStatus", successfulLogin);
          router.push({ path: "/" });
          this.dispatch("getUserInfoFromDb", info.email);
        }
      });
    },
    async logout(context) {
      await fetch(API_URLLog + "logout", {
        method: "GET",
        headers: { "Content-Type": "application/x-www-form-urlencoded" }
      }).then(response => {
        let successfulLogin = !response.url.includes("error");
        console.log("the logout result is:", successfulLogin);
        if (successfulLogin) {
          this.commit("setStatus", !successfulLogin);
          router.push({ path: "/" });
        }
      });
    },
    async addUserToDB(state, reqBody) {
      await fetch(API_URL + "users", {
        method: "POST",
        body: JSON.stringify(reqBody),
        headers: { "Content-Type": "application/json" }
      }).catch(e => { });

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
    //LÄGG TILL AUCTIONS/BILDER ________________________________
    async getLatest() {
      let auction = await (await fetch(API_URL + "auctions/latest")).json().catch(e => { });
      this.commit("setLatestAddedAuction", auction);
    },
    async addAuctionToDB(state, reqBody) {
      await fetch(API_URL + "auctions", {
        method: "POST",
        body: reqBody,
        headers: { "Content-Type": "application/json" }
      });
      this.dispatch("getAuctionsFromDb");
    },
    async addImagesToDB(state, reqBody) {
      await fetch(API_URL + "pictures", {
        method: "POST",
        body: reqBody,
        headers: { "Content-Type": "application/json" }
      });
    },
    //__________________________________________________________________

    async addBidToDb(state, reqBody) {
      await fetch(API_URL + "bids", {
        method: "POST",
        body: JSON.stringify(reqBody),
        headers: { "Content-Type": "application/json" }
      });
      ws.send(JSON.stringify(reqBody));
    },
    async getOneAuction(context, auction) {
      let currAuction = await fetch(API_URL + "auctions/" + auction).then(res =>
        res.json()
      );
      this.commit("setCurrentAuction", currAuction);
    },
      async getSearchAuctions(state, content) {
          await this.commit("setSearchAuctions", content);
      },

    async getImages(context, auctionId) {
      let currImages = await fetch (API_URL + "pictures/" + auctionId).then(res =>
        res.json()
        );
        this.commit("setCurrentImages", currImages)
    },

    async getSeller(context, user) {
      let currSeller = await (await fetch(API_URL + "users/" + user)).json();
      this.commit("setCurrentSeller", currSeller);
    },
    async getBidsForOneAuction(context, auctionId) {
      let bids = await (await fetch(API_URL + "bids/" + auctionId)).json();
      bids.sort((a, b) => b.amount - a.amount);
      let index = this.state.searchAuctions.findIndex(a => a.id === auctionId);
      await this.commit("setSearchAuctionBids", { bids: bids, index: index });
      await this.commit("setCurrentBids", bids);
      return bids;
    },
    async returnBidsForOneAuction(context, auctionId) {
      let bids = await (await fetch(API_URL + "bids/" + auctionId)).json();
      bids.sort((a, b) => b.amount - a.amount);
      return bids;
    }
  }
});
