import Vue from "vue";
import Vuex from "vuex";
import router from '@/router.js'
import { ws, connect, disconnect } from '@/main.js'


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
    currentSellerID: null,
    message: [],
    senderID: null,
    receivedMessage: false,
    chatroomID: null,
    goTochatRoom: false,
    receiverID: null,
    images: [],
    currentBids: [],
    userBids: null,
    notisInfo: null
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
    },
    setSenderID(state, senderID) {
      state.senderID = senderID;
    },
    setReceivedMessage(state, boolean) {
      state.receivedMessage = boolean
    },
    setChatroomID(state, chatroomID) {
      state.chatroomID = chatroomID
    },
    setReceiverID(state, receiverID) {
      state.receiverID = receiverID
    },
    setChatroomObject(state, data) {
      let message = {
        id: data.chatroom_id, message: data.message,
        senderID: data.sender_id, receiverID: data.receiver_id
      }
      Vue.set(state.message, state.message.length, message);

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

          //om currUser tidigare hade högst bud på auktionen i fråga - gör notifikation 
          if(previousBids[1] && previousBids[1].bidderId === this.state.userInfo.email){
            let overBiddedAuction =await this.dispatch("returnOneAuction", data.auctionId);
            
            this.notisInfo = {
              title: overBiddedAuction.title,
              amount: data.amount,
              url: "/auction/"+data.auctionId
            }
                        
            this.state.notification = { 
              show: true, 
              notis: this.notisInfo
            }
          }
        } else if (data.type === "chat") {
          this.commit("setSenderID", data.sender_id)
          this.commit("setReceivedMessage", true)
          this.commit("setChatroomID", data.chatroom_id)
          this.commit("setReceiverID", data.receiver_id)

          this.commit("setChatroomObject", data)
        }
      };
    },
    async getUsersFromDb() {
      let users = await (await fetch(API_URL + "users")).json().catch(e => { });
      return users;
    },
    async getUserInfoFromDb(context, email) {
      let user = await (await fetch(API_URL + "users/" + email))
        .json()
        .catch(e => { });
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
        if (successfulLogin) {
          this.commit("setStatus", successfulLogin);
          router.push({ path: '/' })
          this.dispatch('getUserInfoFromDb', info.email)
          ws.send(JSON.stringify({ type: 'login', loginID: info.email }))
        }
      });
    },
    async logout(context, email) {
      await fetch(API_URLLog + "logout", {
        method: "GET",

        headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
      })
        .then(response => {
          let successfulLogout = !response.url.includes("error");
          if (successfulLogout) {
            this.commit("setStatus", !successfulLogout);
            router.push({ path: '/' })
            ws.send(JSON.stringify({ type: 'logout', logoutID: email }))
          }
        }
        )
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
      let currImages = await fetch(API_URL + "pictures/" + auctionId).then(res =>
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
    },
    async returnOneAuction(context, auction) {
      let currAuction = await fetch(API_URL + "auctions/" + auction).then(res =>
        res.json()
      );
      return currAuction;
    },
  }
});
