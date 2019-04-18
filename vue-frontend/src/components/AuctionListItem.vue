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
              <div>{{currBid}} kr</div>
               <div>{{bids}} bud</div>
               <div>{{seller}}</div>
              <div>Avslutas: {{convertDate}}</div>
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
    endTime: String,
    currBid: String, 
    seller: String,
    bids: Number,
  },
  computed: {
    convertDate: function () {
      let newDate = new Date(this.endTime);
      return newDate.toLocaleDateString()+" "+newDate.getHours() + ":" + newDate.getMinutes();
    }
  },
  methods: {},
};
</script>

<style scoped>
.AuctionListItem{
  margin: 5px;
}
</style>
