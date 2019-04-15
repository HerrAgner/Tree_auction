<template>
  <v-flex xs12>
    <v-card color="cyan darken-2" class="white--text">
      <v-layout>
        <v-flex xs5>
          <v-img
            :src="image"
            height="125px"
            contain
          ></v-img>
        </v-flex>
        <v-flex xs7>
          <v-card-title primary-title>
            <div>
              <div class="headline">{{title}}</div>
              <div>{{description}}</div>
              <div>{{endTime}}</div>
            </div>
          </v-card-title>
        </v-flex>
        <v-flex xs7>
          <v-card-text>
            <div>
              <div>{{seller}}</div>
              <div>{{currBid}}</div>
            </div>
          </v-card-text>
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
    description: String,
    endTime: Date,
    currBid: Number, 
    seller: String,
  },
  computed: {},
  methods: {}
};
</script>

<style scoped></style>
