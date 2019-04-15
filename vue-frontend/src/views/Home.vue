<template>
   
    <div class="v-content__wrap">
        <div class="container grid-list-xl">
            <h1>test</h1>
            <div class="layout wrap" v-for="auction in getAuctions">
                <BlogCard :title="auction.title" :description="auction.description"></BlogCard>
            </div>
        </div>
    </div>
</template>

<script>
    import BlogCard from "@/components/BlogCard.vue"

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
</script>
