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
    currentBids: [],
    userBids: null,
    showNotification: false
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
    setCurrentAuction(state, auction) {
      state.currentAuction = auction;
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

          //om den uppdaterade auction är samma som något currUser har högst bud på - gör notifikation
          /*await this.dispatch("getUsersBids", this.state.userInfo.email)
          
                    this.state.userBids.forEach(element => {
                      //om  användaren har lagt bud tidigare på auktionen det nya budet gäller
                      if(data.auctionId == element.auction_id && this.state.userInfo.email !== data.bidderId ){
                        console.log(element);
                        this.state.showNotification = true;
          
                      }
                    });
          
                    this.dispatch('getHighestBidder', data.auctionId);*/
        } else if (data.type === "chat") {
        }
      };
    },
    getHighestBidder(auctionId) {
      this.dispatch("getBidsForOneAuction", auctionId);
      console.log("ny vinnare", this.state.currentBids[0].bidderId);
      console.log("förra vinnare", this.state.currentBids[1].bidderId);
    },
    async getUsersBids(context, userId) {
      let bids = await (await fetch(API_URL + "bids/user/" + userId)).json();
      this.commit("setUserBids", bids);

      return this.state.userBids;
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
