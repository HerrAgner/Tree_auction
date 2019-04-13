<template>
    
    <div class="v-content__wrap">
        <div class="container grid-list-xl">
            <h1>test</h1>
            <div class="layout wrap" v-for="post in getPosts">
                <BlogCard :title="post.title" :image="post.image"></BlogCard>
            </div>
        </div>
    </div>
</template>

<script>
    import HelloWorld from '../components/HelloWorld'
    import BlogCard from "@/components/BlogCard.vue"
    import Profile from "@/components/Profile.vue"

    export default {
        components: {
            Profile,
            HelloWorld,
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
