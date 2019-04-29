<template>
  <div>
      <v-snackbar
      v-model="showSnackbar"
      color="cyan darken"
      :timeout="timeout">
      {{ text }}
      <v-btn
        dark
        flat
        @click="showSnackbar = false"
      >
        Close
      </v-btn>
    </v-snackbar>
  </div>
</template>

<script>
export default {
  name: "Notification",
  props: {
    text: String,
  },
  data() {
    return {
        timeout: 20000,
        showSnackbar: true,
    };
  },
  created() {
  },
  computed: {
  },
  watch: {
  },
  methods: {
  },
};
</script>

<style scoped>
</style>
