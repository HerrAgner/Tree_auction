<template>
    <v-flex xs12 class="AuctionListItem">
        <router-link :to="this.auctionLink">
            <v-card color="cyan darken-2" class="white--text">
                <v-layout>
                    <v-flex xs6>
                        <v-img
                                :src="items[0]"
                                height="125px"
                                contain
                        ></v-img>
                    </v-flex>
                    <v-flex xs7>
                        <v-card-title primary-title>
                            <div>
                                <div class="headline">{{title}}</div>
                                <div>{{bidUpdate}} £</div>
                                <div>{{bids.length}} bids</div>
                                <div>Seller: {{sellerName}}</div>
                                <div>Ends: {{convertDate}}</div>
                            </div>
                        </v-card-title>
                    </v-flex>
                </v-layout>
            </v-card>
        </router-link>
    </v-flex>
</template>

<script>
    export default {
        name: "AuctionListItem",
        data() {
            return {
                auctionLink: "/auction/",
                highestBid: null,
                bids: [],
                sellerName: null,
                items: [],
                index: 0
            };
        },
        created: async function () {
            this.auctionLink += this.auctionId;
            this.getBids();


            // flytta till store. Ta bort härifrån och från auctionDetails
            this.items = [{
                src: await fetch("http://localhost:7999/images/" + this.image).then(res => res.url)
            }]
            await this.$store.dispatch("getSeller", this.sellerId)
            this.sellerName = this.$store.state.currentSeller.firstname + " " + this.$store.state.currentSeller.lastname;
        },
        props: {
            auctionId: Number,
            title: String,
            image: String,
            endTime: String,
            sellerId: String,
            startPrice: Number
        },
        mounted() {
            console.log("assssssd")
        },
        computed: {
            convertDate: function () {
                let newDate = new Date(this.endTime);
                return newDate.toLocaleDateString() + " " + newDate.getHours() + ":" + newDate.getMinutes();
            },
            bidUpdate() {
                return this.$store.state.searchAuctions[this.index].highestBid;
            }
        },
        watch: {
            bigUpdate(newCount, oldCount) {
                return newCount;
            },
        },
        methods: {
            async getBids() {
                let bids = await this.$store.dispatch("getBidsForOneAuction", this.auctionId)
               this.$store.state.searchAuctions.forEach((auction, i) => {
                    if (auction.id === this.auctionId) {
                        this.index = i;
                        if (bids.length !== 0) {
                            auction.highestBid = bids[0].amount;
                        } else {
                            auction.highestBid = auction.start_price;
                        }
                        // this.$store.commit("setListAuctions", auction);
                        this.$set(this.$store.state.searchAuctions[this.index], this.index, auction);
                        this.highestBid = this.$store.state.searchAuctions[this.index].highestBid;
                    }
                })
                this.bids = bids;
            }
        },
    };
</script>

<style scoped>
    .AuctionListItem {
        margin: 5px;
    }
    
    a {
        text-decoration: none;
    }
</style>
