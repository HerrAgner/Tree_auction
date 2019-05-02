<template>
  <div class="v-content__wrap">
    <v-flex lg4 offset-lg4 class="searchField">
      <v-layout row wrap>
        <v-text-field label="Search for items" @keyup.enter="searchClicked" v-model="search" solo></v-text-field>
        <v-btn @click="searchClicked">Search items</v-btn>
      </v-layout>
    </v-flex>
    <div class="text-xs-center">
      <v-pagination v-if="!this.emptyResult && !this.wrongInput" v-model="pageNumber" :length="this.totalPages" @input="onPageChange"></v-pagination>
    </div>
    <h1 class="text-xs-center" v-if="this.emptyResult">No Result for your search</h1>
    <h1 class="text-xs-center" v-if="this.wrongInput">The page you're looking for does not exist</h1>

    <div class="container grid-list-xl">
     <div class="layout wrap" v-for="auction in this.$store.state.searchAuctions" :key="auction.id">
        <AuctionListItem :auctionId = "auction.id" :title="auction.title" :image="auction.image"
         :endTime="auction.end_time" :sellerId="auction.seller_id" :startPrice="auction.start_price" ></AuctionListItem>
       </div>
    </div>
   <div class="text-xs-center">
      <v-pagination v-if="!this.emptyResult && !this.wrongInput" v-model="pageNumber" :length="this.totalPages" @input="onPageChange"></v-pagination>
    </div>
  </div>
</template>

<script>
    import AuctionListItem from "@/components/AuctionListItem.vue";
    import router from '@/router.js'

export default {
  components: {
    AuctionListItem
  },
 created: function() {    
    this.loadList();
  },
  watch: {
    '$route' (to, from) {
    // this.loadList();
    }
  },
  methods: {
    async loadList(){
      this.emptyResult = false;
      this.wrongInput = false;
    
      if(this.$route.params.search && this.$route.params.page){
        if (this.validatePageInput(this.$route.params.page)){
          await this.getSearchPage(this.pageNumber-1, this.$route.params.search);
        }
      }else if(this.$route.params.page){
        if (this.validatePageInput(this.$route.params.page)){
          await this.getPageFromDB(this.pageNumber-1);
        }
      }else{
        this.pageNumber = 1;
        await this.getPageFromDB(this.pageNumber-1)
      }

      if(this.totalPages === 0){
          this.emptyResult = true;
      }else if(this.pageNumber > this.totalPages){
        this.wrongInput = true;
      }
    },
    validatePageInput(input){
      this.pageNumber = Number(this.$route.params.page);
      if (!this.pageNumber || this.pageNumber < 1){
        this.wrongInput = true;
        return false;
      }else{
        return true;
      }
    },
    onPageChange(pageNumber){
      if(this.contentIsSearchResult){
        this.getSearchPage(pageNumber-1, this.search);
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
      if(pageNumber !== 0){
        this.routerChange("/page/"+(++pageNumber));
      }else{
        this.routerChange("/");
      }
    },
    searchClicked() {
      this.getSearchPage(0, this.search);
    },
    async getSearchPage(pageNumber, searchText) {
      if(!searchText){
        this.getPageFromDB(pageNumber)
      }else{
        this.page = await (await fetch("http://localhost:7999/api/auctions/auctionPage/"+searchText+"/"+pageNumber)).json();
        this.pageContent = this.page.content;
        this.totalPages = this.page.totalPages;
        this.contentIsSearchResult = true;
        this.routerChange("/search/"+searchText+"/page/"+(++pageNumber));
      }
    },
   async routerChange (link) {
        await this.$store.dispatch("getSearchAuctions", this.page.content);
      this.$router.push({path: link })
    }
  },
  data() {
    return {
      pageNumber: 1,
      pageContent: [],
      page: null,
      totalPages: null,
      search: '',
      contentIsSearchResult: false,
      emptyResult: false,
      wrongInput: false
    };
  },
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
