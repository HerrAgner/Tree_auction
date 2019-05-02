<template>
  <v-card flat id="nav">
    <!--      <div class="headline text-xs-center pa-5">Active: {{ bottomNav }}</div>-->
    <v-bottom-nav :value="true" absolute color="white">
      <v-btn to="/" color="teal" flat value="home">
        <span>Home</span>
        <v-icon>home</v-icon>
      </v-btn>

      <v-btn to="/add-auction" color="teal" flat value="add" v-if="statusT">
        <span>Add Auction</span>
        <v-icon>add</v-icon>
      </v-btn>

      <v-btn
        to="/login"
        color="teal"
        flat
        value="LoginRegister"
        v-if="!statusT"
      >
        <span>Login / Register</span>
        <v-icon>account_circle</v-icon>
      </v-btn>

      <v-btn
        color="teal"
        flat
        value="log out"
        v-if="statusT"
        @click="changeStatus"
      >
        <span>{{usernames}} / Log out </span>
        <v-icon>account_circle</v-icon>
      </v-btn>
    </v-bottom-nav>
  </v-card>
</template>

<script>
import { mapState } from "vuex";

export default {
    name: "BottomNav",
    data(){
        return {
            statusT: this.$store.state.status
        }
    },
    methods:{
        changeStatus(){
            if (this.$store.dispatch('logout', this.$store.state.userInfo.email)){
            }
        }
    },

  watch: {
    status(newValue, oldValue) {
      if (newValue === true) {
        this.statusT = true;
      } else {
        this.statusT = false;
      }
    }
  },

  computed: {
      status() {
        return this.$store.state.status
      },
      usernames() {
          return this.$store.state.userInfo.firstname + " " + this.$store.state.userInfo.lastname
      }
  }
  
  
};
</script>

<style scoped>
#nav {
  height: 56px;
  z-index: 999;
}

#alert {
  color: red;
}
</style>
