<template>
  <v-container id="container">
    <v-card id="card">
      <AuctionDetails></AuctionDetails>
    </v-card>
  </v-container>
</template>

<script>
import AuctionDetails from "@/components/AuctionDetails.vue";

export default {
  components: {
    AuctionDetails
  }
};
</script>

<style scoped>
#container {
  width: 70vw;
}
    #card{
        padding:20px;
    }
</style>
