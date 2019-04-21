<template>
    <div id="app">
        <h1>Log In</h1>
        <v-app id="inspire">
            <v-form
                ref="loginForm"
                v-model="valid"
                lazy-validation
                >
                <v-text-field
                v-model="email"
                :rules="emailRules"
                label="E-mail"
                required
                ></v-text-field>

                <v-text-field
                v-model="password"
                :rules="passwordRules"
                label="Password"
                required
                ></v-text-field>

                <v-btn
                :disabled="!valid"
                color="success"
                @click="validate"
                >
                Log in
                </v-btn>
            </v-form>
      </v-app>
    </div>    
</template>

<script>
export default {
  data: () => ({
    valid: true,
    password: '',
    passwordRules: [
      v => !!v || 'Password is required'
    ],
    email: '',
    emailRules: [
      v => !!v || 'E-mail is required'
    ]
  }),

  methods: {    
            async validate() {
              if(this.$refs.loginForm.validate()) {
                let u = await this.$store.dispatch('checkUserInDb', 
                              {email: this.email, password: this.password});                
              }
            }
  }

}
</script>

<style scoped>
#app{
  width: 40vw;
  margin-top: 3vh;
  margin-left: 2vw;
  margin-bottom: 0;
  height: 60vh;
}

@media screen and (max-width:667px ){
    #app{
        width: 85vw;
        height: 40vh;
    }
}

</style>
