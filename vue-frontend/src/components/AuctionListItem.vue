<template>
  <v-flex xs12 class="AuctionListItem">
    <router-link :to="this.auctionLink" >
      <v-card color="cyan darken-2" class="white--text">
        <v-layout >
          <v-flex xs6>
            <v-img
              :src="items[0]"
              height="125px"
              contain
            ></v-img>
          </v-flex>
          <v-flex xs7 >
            <v-card-title primary-title>
              <div>
                <div class="headline">{{title}}</div>
                <div>{{highestBid}} £ </div>
                <div>{{bids}} bids</div>
                <div>Seller: {{sellerName}}</div>
                <div>Ends: {{convertDate}}</div>
                <div v-if="showCountdownTimer" :key="auctionId">
                  <p>Time left:</p>
                  <flip-countdown id="countdownTimer" :deadline="countdown"></flip-countdown>
                </div>
              </div>         
            </v-card-title>
          </v-flex>
        </v-layout>
      </v-card>
    </router-link>
  </v-flex>
</template>

<script>
import FlipCountdown from "@/components/FlipCountdown.vue"

export default {
  components: { FlipCountdown },
  name: "AuctionListItem",
  data() {
    return {
      auctionLink: "/auction/",
      highestBid : null,
      bids: null,
      sellerName: null,
      items: [], 
      countdown: null
    };
  },
created: async function() {
  this.auctionLink += this.auctionId;
  this.getBids();
  this.countdown = new Date(this.endTime).toLocaleString()

  
  
  // flytta till store. Ta bort härifrån och från auctionDetails
  this.items = [{ src: await fetch("http://localhost:7999/images/" + this.image).then(res => res.url)
  }]

  await this.$store.dispatch("getSeller", this.sellerId)
  this.sellerName = this.$store.state.currentSeller.firstname + " "+this.$store.state.currentSeller.lastname;
},
  props: {
    auctionId: Number,
    title: String,
    image: String,
    endTime: String,
    sellerId: String,
    startPrice: Number
  },
  computed: {
    convertDate: function () {
      let newDate = new Date(this.endTime);
      return newDate.toLocaleDateString()+" "+newDate.getHours() + ":" + newDate.getMinutes();
    },
    showCountdownTimer() {
      let ONE_DAY = new Date().getTime() + (24 * 60 * 60 * 1000)
      if (new Date(ONE_DAY) <= new Date(this.endTime).getTime()) {
          return false;
      }else {
          return true;
      }
    }
  },
  methods: {
    async getBids() {
      await this.$store.dispatch("getBidsForOneAuction", this.auctionId)
      let bids = this.$store.state.currentBids;
      bids.sort((a, b) => b.amount - a.amount);
      if (bids.length === 0) {
        this.highestBid = this.startPrice;
      } else {
        this.highestBid = bids[0].amount;
      }
      this.bids = bids.length;
    },
  },
};
</script>

<style scoped>

#countdownTimer {
margin-top: -10px;
margin-left: -150px;
} 
.AuctionListItem{
  margin: 5px;
}
p{
  margin: 0px;
}
a{
  text-decoration: none;
}
</style>
