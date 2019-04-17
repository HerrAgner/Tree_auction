<template>
  <v-flex xs12 class="AuctionListItem">
    <v-card color="cyan darken-2" class="white--text">
      <v-layout >
        <v-flex xs6>
          <v-img
            :src="image"
            height="125px"
            contain
          ></v-img>
        </v-flex>
        <v-flex xs7 >
          <v-card-title primary-title>
             <div>
              <div class="headline">{{title}}</div>  
              <div>{{endTime}}</div>
              <div>{{seller}}</div>
              <div>{{currBid}} kr</div>
            </div>         
          </v-card-title>
        </v-flex>
      </v-layout>
    </v-card>
  </v-flex>
</template>

<script>
export default {
  name: "AuctionListItem",
  data() {
    return {
      router: "/about",
    };
  },
  props: {
    title: String,
    image: String,
    endTime: Date,
    currBid: Number, 
    seller: String,
  },
  computed: {},
  methods: {}
};
</script>

<style scoped></style>
