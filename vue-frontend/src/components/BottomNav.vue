<template>
    <v-card flat id="nav">
        <!--      <div class="headline text-xs-center pa-5">Active: {{ bottomNav }}</div>-->
        <v-bottom-nav
                :value="true"
                absolute
                color="white"
        >
            <v-btn to="/" color="teal" flat value="home">
                <span>Home</span>
                <v-icon>home</v-icon>
            </v-btn>
            
            <v-btn to="/add-auction" color="teal" flat value="add" v-if="status">
                <span>Add Auction</span>
                <v-icon>add</v-icon>
            </v-btn>
            
            <v-btn to="/login" color="teal" flat value="LoginRegister" v-if="!status">
                <span>login</span>
                <v-icon>account_circle</v-icon>
            </v-btn>

            <v-btn color="teal" flat value="log out" v-if="status" @click="changeStatus">
                <span>log out</span>
                <v-icon>account_circle</v-icon>
            </v-btn>
        </v-bottom-nav>
    </v-card>
</template>

<script>
    export default {
        name: "BottomNav",
        data(){
            return {
                status: this.$store.status
            }
        },
        methods:{
            changeStatus(){
                this.status = false;
            }
        }
    }
</script>

<style scoped>
    #nav{
        height: 56px;
        z-index: 999;
    }
</style>