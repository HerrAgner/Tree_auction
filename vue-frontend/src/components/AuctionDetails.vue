<template>
  <v-responsive>
    <div v-if="!isFetching">
      <h1 class="text-xs-center" v-if="!this.auction">
        The auction you're looking for does not exist
      </h1>
      <div v-else>
        <v-card-text id="header">
          <h1>{{ auction.title }}</h1>
        </v-card-text>
        <v-layout justify-space-around row id="first_section">
          <v-content id="image_carousel">
            <v-carousel
                    hide-delimiters
            >
              <v-carousel-item
                v-for="(item, i) in items"
                :key="i"
                :src="item.src"
                contain
              ></v-carousel-item>
            </v-carousel>
          </v-content>
          <v-content id="bid_section">
            <v-layout align-start justify-center row>
              <v-container class="bid">
                <h5>Current bid</h5>
                <p>£ {{ bidUpdate }}</p>
              </v-container>
              <v-container class="bid">
                <h5>End time</h5>
                <p>{{ convertDate }}</p>
                <p>{{ convertTime }}</p>
              </v-container>
              <v-container class="bid">
                <h5>Bids</h5>
                {{ bidAmount }}
              </v-container>
            </v-layout>
            <div
              id="countdownTimerBox"
              v-if="showCountdownTimer"
              :key="countdownKey"
            >
              <p>Time left:</p>
              <flip-countdown
                id="countdownTimer"
                :deadline="countdown"
              ></flip-countdown>
            </div>
            <v-card id="bidCard" v-if="!auctionEnded">
              <v-flex xs8>
                <v-form ref="form" @submit.prevent lazy-validation>
                  <v-text-field
                    solo
                    placeholder="Enter bid"
                    :rules="bidRules"
                    required
                    v-model="bidField"
                    prefix="£"
                    mask="######"
                    >{{ bidField }}
                  </v-text-field>
                </v-form>
              </v-flex>

              <v-flex xs8>
                <v-btn round color="success" dark @click="validate"
                  >Place bid
                </v-btn>
              </v-flex>
            </v-card>
            <v-layout align-center justify-center row v-else>
              <h2 id="ended">Auction ended</h2>
            </v-layout>
            <v-alert id="bidAlert" :color="type" value="true" v-if="type">
              {{ bidAlertText }}
            </v-alert>
          </v-content>
        </v-layout>

        <v-layout justify-space-around row id="second_section">
          <v-content id="description">
            <v-card>
              <p>
                {{ auction.description }}
              </p>
            </v-card>
          </v-content>
          <v-card id="contact_info">
            <v-flex xs8>
              <p>{{ seller.firstname }} {{ seller.lastname }}</p>
            </v-flex>
            <v-flex xs8>
              <v-btn round color="success" @click="goToChat" dark>Chat with seller</v-btn>
            </v-flex>
          </v-card>
        </v-layout>
      </div>
    </div>
  </v-responsive>
</template>

<script>
import FlipCountdown from "@/components/FlipCountdown.vue";
import router from '@/router.js'

export default {
  components: { FlipCountdown },
  name: "AuctionDetails",
  data() {
    return {
      isFetching: true,
      auction: "",
      seller: "",
      countdown: "",
      countdownKey: 0,
      bidRules: [v => !!v || "Bid is required"],
      auctionEnded: false,
      bidField: "",
      type: null,
      bidAlertText: "",
      items: [],
      index: 0,
      images: []
    };
  },
  async created() {
    await this.$store
      .dispatch("getOneAuction", this.$route.params.id)
      .then(() => (this.isFetching = false));
    this.auction = this.$store.state.currentAuction;
    if (this.auction) {
      await this.$store.dispatch("getSeller", this.auction.seller_id);
      this.seller = this.$store.state.currentSeller;
      await this.$store.dispatch("getImages", this.$route.params.id);
      this.images = this.$store.state.images;

      this.items = [
        {
          src: await fetch(
            "http://localhost:7999/images/" + this.auction.image
          ).then(res => res.url)
        }
      ];

      this.images.forEach(image =>
        this.items.push({
          src: "http://localhost:7999/images/" + image.picture
        })
      );
      this.getBids();
      let currDate = new Date(this.auction.end_time)
      currDate = this.$moment(currDate).format('YYYY-MM-DD hh:mm:ss')
      this.countdown = currDate
      this.forceRerender();
    }
  },
  methods: {
    async getBids() {
      let bids = await this.$store
        .dispatch("returnBidsForOneAuction", this.auction.id)
        .then(res => res);
      await this.$store.commit("setCurrentBids", bids);
      if (this.$store.state.currentBids[0] === undefined) {
        this.$store.state.currentBids[0] = {
          amount: this.auction.start_price,
          auction_id: this.auction.id
        };
      }
    },
    goToChat(){
      if (this.$store.state.status){
        if (this.$store.state.userInfo.email === this.$store.state.currentSeller.email){
          this.showAlert("error", "You can not chat with yourself!");
        }else {
          router.push({ path: '/chat/' + this.$route.params['id'] + '/' + this.$store.state.userInfo.email})
        }
      }else {
        this.showAlert("error", "You must be logged in to chat with the owner");
      }
    },
    async compareBid(bid) {
      await this.getBids();
      if (bid > this.$store.state.currentBids[0].amount) {
        let bidObject = {
          amount: bid,
          auctionId: this.auction.id,
          bidderId: this.$store.state.userInfo.email,
          time: new Date().toJSON(),
          type: "bid"
        };
        await this.$store.dispatch("addBidToDb", bidObject);
        this.reset();
        this.showAlert("success", "Bid placed.");
      } else {
        this.showAlert("error", "Bid not high enough.");
      }
    },
    validate() {
      if (this.$refs.form.validate()) {
        if (this.$store.state.status === false) {
          this.showAlert("error", "You must be logged in to place a bid.");
        } else if (
          this.$store.state.userInfo.email === this.auction.seller_id
        ) {
          this.showAlert("error", "Can't bid on your own auction.");
        } else {
          this.compareBid(this.bidField);
        }
      }
    },
    reset() {
      this.$refs.form.reset();
    },
    showAlert(type, text) {
      this.type = type;
      this.bidAlertText = text;

      let timer = this.showAlert.timer;
      if (timer) {
        clearTimeout(timer);
      }
      this.showAlert.timer = setTimeout(() => {
        this.type = null;
      }, 3000);

      let elapse = 1;
      let t = this.showAlert.t;
      if (t) {
        clearInterval(t);
      }

      this.showAlert.t = setInterval(() => {
        if (elapse === 3) {
          elapse = 0;
          clearInterval(this.showAlert.t);
        } else {
          elapse++;
        }
      }, 1000);
    },
    forceRerender() {
      this.countdownKey += 1;
    }
  },
  computed: {
    convertDate() {
      let newDate = new Date(this.auction.end_time);
      newDate = this.$moment(newDate).format('YYYY-MM-DD')
      return newDate;
    },
    convertTime() {
      let newDate = new Date(this.auction.end_time);
      newDate = this.$moment(newDate).format('HH:mm')
      return newDate;
    },
    showCountdownTimer() {
      let ONE_DAY = new Date().getTime() + 24 * 60 * 60 * 1000;
      if (new Date(ONE_DAY) <= new Date(this.auction.end_time).getTime()) {
        return false;
      } else if (
        new Date().getTime() > new Date(this.auction.end_time).getTime()
      ) {
        this.auctionEnded = true;
        return false;
      } else {
        return true;
      }
    },
    bidUpdate() {
      if (this.auction) {
        if (this.$store.state.currentBids.length > 0) {
          return this.$store.state.currentBids[0].amount;
        } else {
          return this.auction.start_price;
        }
      }
    },
    bidAmount() {
      return this.$store.state.currentBids.length;
    }
  },
  watch: {
    bidUpdate(newCount, oldCount) {
      return newCount;
    },
    bidAmount(newCount, oldCount) {
      return newCount;
    }
  }
};
</script>

<style scoped>
#image_carousel {
  width: 60%;
}

#bid_section {
  width: 40%;
}

#description {
  width: 60%;
}

#contact_info {
  width: 40%;
}

#contact_info,
#bidCard,
#bidAlert {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  margin: 20px;
  flex-direction: column;
}

#contact_info {
  margin-top: 0;
}

#second_section {
  margin-top: 20px;
}

.bid {
  height: 7vh;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
  padding: 0;
}

.bid p {
  margin: 0;
}

#countdownTimerBox {
  margin-top: 50px;
  text-align: center;
}

#countdownTimer {
  margin-top: -20px;
}

#ended {
  color: red;
}
</style>
