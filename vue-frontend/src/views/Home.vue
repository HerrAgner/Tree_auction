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
         :endTime="auction.end_time" :sellerId="auction.seller_id" :startPrice="auction.start_price" ></AuctionListItem> 
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
    onPageChange(pageNumber){
      if(this.contentIsSearchResult){
        this.searchClicked(pageNumber-1);
      }else{
        this.getPageFromDB(pageNumber-1);
      }
      window.scrollTo(0,0);
    },
    async getPageFromDB(pageNumber) {
      this.page = await (await fetch("http://localhost:7999/api/auctions/auctionPage/"+pageNumber)).json();
      this.pageContent = this.page.content;
      this.totalPages = this.page.totalPages;
      this.contentIsSearchResult = false;
    },
    searchClicked() {
      this.getSearchPage(0);
    },
    async getSearchPage(pageNumber) {
      this.page = await (await fetch("http://localhost:7999/api/auctions/auctionPage/"+this.search+"/"+pageNumber)).json();
      this.pageContent = this.page.content;
      this.totalPages = this.page.totalPages;
      this.contentIsSearchResult = true;
    },
  },
  data() {
    return {
      pageNumber: 0,
      pageContent: null,
      page: null,
      totalPages: null,
      search: '',
      contentIsSearchResult: false
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
