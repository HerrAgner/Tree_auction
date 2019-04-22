<template>
    <div id="app">
        <h1>Sign Up</h1>
        <v-app id="inspire">
            <v-form
                ref="registerForm"
                v-model="valid"
                lazy-validation
                >
                <v-text-field
                v-model="firstName"
                :counter="10"
                :rules="firstNameRules"
                label="First name"
                required
                ></v-text-field>

                <v-text-field
                v-model="lastName"
                :counter="10"
                :rules="lastNameRules"
                label="Last name"
                required
                ></v-text-field>

                <v-text-field
                v-model="email"
                :rules="emailRules"
                label="E-mail"
                required
                ></v-text-field>

                <v-text-field
                v-model="phoneNumber"
                :counter="10"
                :rules="phoneRules"
                label="Phone number"
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
                sign up
                </v-btn> 
                {{messageToClient}}
            </v-form>
      </v-app>
    </div>    
</template>

<script>


export default {
    data: () => ({
    valid: true,
    messageToClient: '',
    password: '',
    passwordRules: [
        v => !!v || 'Password is required'
    ],
    email: '',
    emailRules: [
        v => !!v || 'E-mail is required'
    ],
    firstName: '',
    firstNameRules: [
        v => !!v || 'First name is required',
        v => (v && v.length <= 10) || 'Name must be less than 10 characters'
    ],
    lastName: '',
    lastNameRules: [
        v => !!v || 'Last name is required',
        v => (v && v.length <= 10) || 'Name must be less than 10 characters'
    ],
    phoneNumber: '',
    phoneRules: [
        v => !!v || 'Phone number is required',
        v => (v && v.length <= 10) || 'Number must be less than 10 characters'
    ]
    }),
    methods: {
        async validate() {
            if (this.$refs.registerForm.validate()) {
                this.$store.state.userInfo = [];
                let u = await this.$store.dispatch('getUserInfoFromDb', this.email);                
                if (this.$store.state.userInfo.email === undefined){
                    this.messageToClient = '';
                    this.snackbar = true;
                    
                    this.$store.dispatch('addUserToDB',{email: this.email,
                                                         firstname:this.firstName,
                                                         lastname: this.lastName,
                                                         password: this.password,
                                                         phone: this.phoneNumber})
                    this.messageToClient = 'Successfully!'
                }else{
                    this.messageToClient = 'This email is already used!';
                }
            }
        }
    },
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
        height: 60vh;

    }
}
</style>
