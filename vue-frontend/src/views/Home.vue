<template>
    <div class="v-content__wrap">
        <div class="container grid-list-xl">
            <div class="layout wrap">
                <Profile></Profile>
                <BlogCard></BlogCard>
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
        }
    }
</script>
