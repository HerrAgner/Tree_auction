<template>
  <div class="flex xs12">
    <router-link :to="router" class="v-card v-sheet theme--dark elevation-6">
      <div class="v-card__title">
        <div>
          <h2 class="title font-weight-regular">{{ title }}</h2>
          <div class="layout grey--text text--lighten-1 pa-3">
            <div>
              <i
                aria-hidden="true"
                class="v-icon mdi mdi-pencil theme--dark grey--text text--lighten-1"
                style="font-size: 16px;"
              ></i>
            </div>
          </div>
        </div>
      </div>
      <div class="v-responsive v-image" style="height: 400px;">
        <div
          class="v-responsive__sizer"
          style="padding-bottom: 56.2667%;"
        ></div>
        <div class="v-image__image v-image__image--cover">
          <v-img :src="image" />
        </div>
        <div class="v-responsive__content"></div>
      </div>
    </router-link>
  </div>
</template>

<script>
export default {
  name: "BlogCard",
  data() {
    return {
      router: "/about",
      header: "asd"
    };
  },
  props: {
    title: String,
    image: String,
    text: String,
    author: String
  },
  computed: {},
  methods: {}
};
</script>

<style scoped></style>
