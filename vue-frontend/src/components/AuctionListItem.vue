<template>
  <v-flex xs12 class="AuctionListItem">
    <router-link :to="this.auctionLink">
      <v-card color="cyan darken-2" class="white--text">
        <v-layout align-center justify-center>
          <v-flex xs6>
            <v-img :src="items[0]" height="125px" contain></v-img>
          </v-flex>
          <v-flex xs7>
            <v-card-title primary-title>
              <div>
                <div class="headline">{{ title }}</div>
                <div>{{ bidUpdate }} £</div>
                  <div>{{ bidAmount }} bids</div>
                <div>Seller: {{ sellerName }}</div>
                <div>Ends: {{ convertDate }}</div>
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
      bids: [],
      sellerName: null,
      items: [],
      index: 0,
      countdown: null
    };
  },
created: async function() {
  this.auctionLink += this.auctionId;
  this.getBids();
  let currDate = new Date(this.endTime)
  currDate = this.$moment(currDate).format('YYYY-MM-DD hh:mm:ss')
  this.countdown = currDate
  
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
    convertDate: function() {
      let newDate = new Date(this.endTime);
      return newDate.toLocaleDateString()+" "+newDate.getHours() + ":" + newDate.getMinutes();
    },
    showCountdownTimer() {
        let ONE_DAY = new Date().getTime() + (24 * 60 * 60 * 1000)
        if (new Date(ONE_DAY) <= new Date(this.endTime).getTime()) {
            return false;
        } else {
            return true;
        }
    },
    bidUpdate() {
      return this.$store.state.searchAuctions[this.index].highestBid;
    },
      bidAmount() {
        return this.$store.state.searchAuctions[this.index].bidsAmount;
      }
  },
  watch: {
    bidUpdate(newCount, oldCount) {
      return newCount;
    },
      bidAmount(newCount, oldCount) {
          return newCount;
      },
  },
  methods: {
    async getBids() {
      let bids = await this.$store.dispatch(
        "getBidsForOneAuction",
        this.auctionId
      );
      this.index = this.$store.state.searchAuctions.findIndex(
        a => a.id === this.auctionId
      );
      if (bids.length !== 0) {
          this.$store.state.searchAuctions[this.index].highestBid = bids[0].amount;
      } else {
          this.$store.state.searchAuctions[this.index].highestBid = this.$store.state.searchAuctions[this.index].start_price;
      }

      this.$set(this.$store.state.searchAuctions[this.index], this.index, this.$store.state.searchAuctions[this.index]
      );
      // this.highestBid = this.$store.state.searchAuctions[this.index].highestBid;
      //   this.highestBid = this.$store.state.searchAuctions[this.index].highestBid;
      this.bids = bids;

    },
  }
};
</script>

<style scoped>
.AuctionListItem {
}

#countdownTimer {
margin-top: -10px;
margin-left: -150px;
}
.AuctionListItem{
  margin: 5px;
}
p{
  margin: 0;
}
a{
  text-decoration: none;
}
</style>
