<template>
  <div class="v-content__wrap">
    <v-flex lg4 offset-lg4 class="searchField">
      <v-layout row wrap>
        <v-text-field label="Search for items" solo></v-text-field>
        <v-btn>Search items</v-btn>
      </v-layout>
    </v-flex>
    <div class="container grid-list-xl">

     <div class="layout wrap" v-for="auction in getAuctions">
        <AuctionListItem :title="auction.title" image="https://cdn.vuetifyjs.com/images/cards/desert.jpg"
         :endTime="auction.end_time" currBid=2500 :seller="auction.seller_id"></AuctionListItem>
      </div>
    </div>
    <div class="text-xs-center">
      <v-pagination v-model="page" :length="pages" @input="onPageChange"></v-pagination>
    </div>
  </div>
</template>

<script>
import AuctionListItem from "@/components/AuctionListItem.vue";

export default {
  components: {
    // Profile,
    AuctionListItem
  },
  methods: {
    onPageChange(page){
      console.log("page change!")
      console.log(page)
    }
  },
  computed: {
    getAuctions() {
      // console.log(this.$store.state.posts.length);
      return this.$store.state.auctions;
    }, 
    countAuctions() {
      this.$store.dispatch("countAuctions");
      return this.$store.state.amountOfAuctions;
    }, 
    pages() {
      let pages= Math.round(this.countAuctions/10+0.5);
      return pages;
    }, 

  },

  data() {
    return {
    };
  }
};
</script>

<style>
.v-content__wrap {
  margin-top: 10px;
  margin-bottom: 20px;
}
.searchField {
  color: black;
  margin-left: 150px;
}
</style>
