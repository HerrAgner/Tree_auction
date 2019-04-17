<template>
  <v-responsive>
    <v-card-text id="header">
      <h1>{{ auction.title }}</h1>
    </v-card-text>
    <v-layout justify-space-around row id="first_section">
      <v-content id="image_carousel">
        <v-carousel>
          <v-carousel-item
            v-for="(item, i) in items"
            :key="i"
            :src="item.src"
          ></v-carousel-item>
        </v-carousel>
      </v-content>
      <v-content id="bid_section">
        <v-layout align-start justify-center row>
          <v-container class="bid">
            <h5>Current bid</h5>
            <p>{{ highestBid }}</p>
          </v-container>
          <v-container class="bid">
            <h5>End time</h5>
            <p>{{ convertDate }}</p>
            <p>{{ convertTime }}</p>
          </v-container>
          <v-container class="bid">
            <h5>Bids</h5>
            {{ bids.length }}
          </v-container>
        </v-layout>
        <v-card id="bidCard">
          <v-flex xs8>
            <v-text-field solo placeholder="Enter bid"></v-text-field>
          </v-flex>
          <v-flex xs8>
            <v-btn round color="success" dark>Place bid</v-btn>
          </v-flex>
        </v-card>
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
          <p>{{ sellerName.firstname }} {{ sellerName.lastname }}</p>
        </v-flex>
        <v-flex xs8>
          <v-btn round color="success" dark>Chat with seller</v-btn>
        </v-flex>
      </v-card>
    </v-layout>
  </v-responsive>
</template>

<script>
const API_URL = "http://localhost:7999/api/";
export default {
  name: "AuctionDetails",
  data() {
    return {
      auction: "",
      sellerName: "",
      bids: [],
      highestBid: null,
      items: [
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/squirrel.jpg"
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/sky.jpg"
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/bird.jpg"
        },
        {
          src: "https://cdn.vuetifyjs.com/images/carousel/planet.jpg"
        }
      ]
    };
  },
  async created() {
    this.auction = await fetch(
      API_URL + "auctions/" + this.$route.params.id
    ).then(res => res.json());
    this.sellerName = await fetch(
      API_URL + "users/" + this.auction.seller_id
    ).then(res => res.json());
    this.bids = await (await fetch(API_URL + "bids/" + this.auction.id)).json();
    this.bids.sort(function(a, b) {
      return b.amount - a.amount;
    });
    if(this.bids.length === 0) {
        this.highestBid = this.auction.start_price
    } else {
        this.highestBid = this.bids[0].amount;
    }
  },
  mounted() {},
  methods: {},
  computed: {
    convertDate: function() {
      let newDate = new Date(this.auction.end_time);
      return newDate.toLocaleDateString();
    },
    convertTime: function() {
      let newDate = new Date(this.auction.end_time);
      return newDate.getHours() + ":" + newDate.getMinutes();
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
#bidCard {
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
</style>
