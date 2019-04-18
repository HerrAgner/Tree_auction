<template>
    
    <div class="v-content__wrap">
        <div class="container grid-list-xl">
            <h1>test</h1>
            <div class="layout wrap" v-for="post in getPosts">
                <BlogCard :title="post.title" :description="post.description"></BlogCard>
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
        computed: {
           getPosts() {
               // console.log(this.$store.state.posts.length);
                return this.$store.state.posts;
            }
        }
    }
</script>
