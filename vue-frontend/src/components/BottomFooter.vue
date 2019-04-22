<template>
  <v-footer id="bottomFooter" class="pa-5" color="white"  >
    <a target="_blank" rel="noopener noreferrer" href="https://www.google.com/maps/place/10+Downing+St,+Westminster,+London+SW1A+2AB,+UK/@51.5033635,-0.1298135,17z/data=!3m1!4b1!4m5!3m4!1s0x487604c56a6cc61d:0x2eca1ef309dd9534!8m2!3d51.5033635!4d-0.1276248" class="link">10 Downing Street, London</a>
    <p>+44 (0)7845 804513</p>
    <p>&copy;Tree {{ new Date().getFullYear() }}</p>
    <a href="mailto:info@tree.com" class="link">info@tree.com</a>

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
    text-decoration: none!important;
  color: black;

}
.link{
  text-decoration: none!important;
  color: black;
  margin-bottom: 15px;
}
</style>

