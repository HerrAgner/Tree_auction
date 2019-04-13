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
            
            <v-btn to="/about" color="teal" flat value="add">
                <router-link to="/about"></router-link>
                <span>Add</span>
                <v-icon>add</v-icon>
            </v-btn>
            
            <v-btn color="teal" flat value="settings">
                <span>Settings</span>
                <v-icon>settings</v-icon>
            </v-btn>
        </v-bottom-nav>
    </v-card>
</template>

<script>
    export default {
        name: "BottomNav"
    }
</script>

<style scoped>
    #nav{
        height: 5vh;
    }
</style>