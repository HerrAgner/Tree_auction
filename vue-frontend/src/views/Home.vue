<template>
  <div class="v-content__wrap">
    <v-flex lg4 offset-lg4 class="searchField">
      <v-layout row wrap>
    <v-text-field label="Search for items"  solo></v-text-field>
    <v-btn>Search items</v-btn>
    </v-layout>
    </v-flex>
    <div class="container grid-list-xl">
      <h1>test</h1>
      <div class="layout wrap" v-for="post in getPosts">
        <BlogCard :title="post.title" :description="post.description"></BlogCard>
      </div>
    </div>
     <div class="text-xs-center">
    <v-pagination
      v-model="page"
      :length="6"
    ></v-pagination>
  </div>
  </div>
</template>

<script>
import BlogCard from "@/components/BlogCard.vue";

    export default {
        components: {
            // Profile,
            BlogCard
        },
        methods: {
        
        },
        computed: {
           getAuctions() {
               // console.log(this.$store.state.posts.length);
                return this.$store.state.auctions;
            }
        }
    }
  },
  data () {
      return{ 
         page: 1
      }
  }
};
</script>

<style>
.v-content__wrap{
    margin-top: 10px;
    margin-bottom: 20px;
}
.searchField{
    color: black;
    margin-left: 150px;

}
</style>

