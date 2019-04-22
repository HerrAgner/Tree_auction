<template>
  <div class="v-content__wrap">
    <v-flex lg4 offset-lg4 class="searchField">
      <v-layout row wrap>
        <v-text-field label="Search for items" @keyup.enter="searchClicked" v-model="search" solo></v-text-field>
        <v-btn @click="searchClicked">Search items</v-btn>
      </v-layout>
    </v-flex>
    <div class="container grid-list-xl">
     <div class="layout wrap" v-for="auction in this.pageContent">
        <AuctionListItem :auctionId = "auction.id" :title="auction.title" image="https://cdn.vuetifyjs.com/images/cards/desert.jpg"
         :endTime="auction.end_time" currBid=2500 :seller="auction.seller_id"></AuctionListItem> 
       </div>
    </div>
    <div class="text-xs-center">
      <v-pagination v-model="pageNumber" :length="this.totalPages" @input="onPageChange"></v-pagination>
    </div>
  </div>
</template>

<script>
import AuctionListItem from "@/components/AuctionListItem.vue";

export default {
  components: {
    AuctionListItem
  },
  created: function() {
    this.getPageFromDB(0);
  },
  methods: {
    onPageChange(page){
      this.getPageFromDB(page-1);
      window.scrollTo(0,0);
    },
    async getPageFromDB(page) {
      this.page = await (await fetch("http://localhost:7999/api/auctions/auctionPage/"+page)).json();
      this.pageContent = this.page.content;
      this.totalPages = this.page.totalPages;
    },
    async searchClicked() {
      console.log(this.search)
      let auctions = await (await fetch("http://localhost:7999/api/auctions/search/" + this.search)).json()
      this.pageContent = auctions

    },
  },
  data() {
    return {
      pageNumber: 0,
      pageContent: null,
      page: null,
      totalPages: null,
      search: ''
    
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
