<template>
  <v-footer id="bottomFooter" class="pa-5" color="white"  >
    <p>10 Downing Street, London</p>
    <p>+44 (0)7845 804513</p>
    <p>&copy;Tree {{ new Date().getFullYear() }}</p>
    <br>
    <p>info@tree.com</p>

  </v-footer>
</template>

<script>
export default {
  name: "BottomFooter"
};
</script>

<style>
#bottomFooter {
  display: flex;
  justify-content: center;
  justify-content: space-around;
  height: 200px;
  box-shadow: 0 4px 10px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);

}
</style>

