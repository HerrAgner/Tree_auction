<template>
  <v-container grid-list-md text-xs-center>
    <v-layout wrap>
      <v-flex mb-5 xs12>
        <h1>NEW AUCTION</h1>
      </v-flex>
      <v-flex xs7>
        <v-text-field
          label="Title of the auction"
          :rules="rules"
          counter="40"
          solo
          required
          id="title"
          v-model="title"
        ></v-text-field>
      </v-flex>
      <v-flex xs5>
        <h3>Pick an end date for the auction</h3>
        <v-date-picker id="datePicker" v-model="date" :min="minDate" :max="maxDate" required></v-date-picker>
      </v-flex>

      <v-flex xs7 id="productDescription">
        <v-textarea
          :rules="textRules"
          counter="300"
          solo
          name="input-7-4"
          v-model="description"
          id="description"
          label="Add description"
          value
          required
        ></v-textarea>
      </v-flex>
      <v-flex xs4></v-flex>

      <v-flex xs2 id="price">
        <v-text-field
          v-model="price"
          label="Asking Price"
          id="price"
          prefix="£"
          mask="###########"
          required
          solo
        ></v-text-field>
      </v-flex>
      <v-flex xs10></v-flex>

      <v-form method="POST" @submit.prevent="addAuction" id="addAuction" class="flexOne">
        <v-layout sm12 align-center justify-start row>
          <v-flex class="border" sm2>
            <v-img
              class="pointerCursor"
              height="150"
              :src="imageUrl"
              contain
              @click="onPickFilePrimary"
            />
            <p>Primary image</p>
            <input
              type="file"
              name="filesPrimary"
              style="display:none"
              ref="fileInputPrimary"
              accept="image/*"
              @change="onFilePicked"
              value="Upload file(s)"
              required
            >
          </v-flex>
          <v-flex sm2>
            <v-img
              class="pointerCursor"
              height="150"
              :src="imageUrlExtra1"
              contain
              @click="onPickFileSecondary"
            />
            <p>Extra image 1</p>
            <input
              type="file"
              name="filesSecondary"
              style="display:none"
              ref="fileInputSecondary"
              accept="image/*"
              @change="onSecondFilePicked"
              value="Upload file(s)"
            >
          </v-flex>
          <v-flex sm2>
            <v-img
              class="pointerCursor"
              height="150"
              :src="imageUrlExtra2"
              contain
              @click="onPickFileThird"
            />
            <p>Extra image 2</p>
            <input
              type="file"
              name="filesThird"
              style="display:none"
              ref="fileInputThird"
              accept="image/*"
              @change="onThirdFilePicked"
              value="Upload file(s)"
            >
          </v-flex>
        </v-layout>
        <v-btn
          class="buttonSub"
          small
          type="submit"
          form="addAuction"
          :disabled="!formIsValid"
        >Add Auction</v-btn>
      </v-form>
    </v-layout>
  </v-container>
</template>


<script>
import router from "@/router.js";
export default {
  components: {},
  data() {
    return {
      title: "",
      description: "",
      price: "",
      imageUrl: "http://localhost:7999/images/noImage.jpg",
      imageUrlExtra1: "http://localhost:7999/images/noImage.jpg",
      imageUrlExtra2: "http://localhost:7999/images/noImage.jpg",
      image: null,
      images: [],
      date: "",
      rules: [v => v.length <= 40 || "Max 40 characters"],
      textRules: [v => v.length <= 300 || "Max 300 characters"],
      fileReader: new FileReader(),
      formData: new FormData(),
      formDataSecond: new FormData(),
      files: []
    };
  },
  methods: {
    async addAuction() {
      this.formData.append("files", this.files[0], this.files[0].name);
      await this.uploadFiles(this.formData)
        .then(image => {
          this.image = image;
        })
        .catch(console.warn);
      await this.uploadFiles(this.formDataSecond)
        .then(images => {
          this.images = images;
        })
        .catch(console.warn);

      if (this.date == "") {
        return alert("Please pick an end date");
      } else {
        var dateToday = new Date();
        var dateEnd = new Date(
          this.date +
            " " +
            dateToday.getHours() +
            ":" +
            dateToday.getMinutes() +
            ":" +
            dateToday.getSeconds()
        );
        dateEnd = dateEnd.toISOString().replace("Z", "+0000");

        const productData = JSON.stringify({
          title: this.title,
          description: this.description,
          start_price: this.price,
          seller_id: this.$store.state.userInfo.email,
          end_time: dateEnd,
          addedTime: dateToday,
          image: this.image[0]
        });
        await this.$store.dispatch("addAuctionToDB", productData);
        await this.$store.dispatch("getLatest");
        let id = this.$store.state.latestAddedAuction;

        for (let i = 0; i < this.images.length; i++) {
          const imageData = JSON.stringify({
            picture: this.images[i],
            auctionId: this.$store.state.latestAddedAuction
          });
          await this.$store.dispatch("addImagesToDB", imageData);
        }
        await this.$router.push("/auction/" + id); //Går till startsidan
      }
    },
    onPickFilePrimary() {
      this.$refs.fileInputPrimary.click(); //Reffererar till fileInput så man kan ha annat utseende på input
    },
    onPickFileSecondary() {
      this.$refs.fileInputSecondary.click(); //Reffererar till fileInput så man kan ha annat utseende på input
    },
    onPickFileThird() {
      this.$refs.fileInputThird.click(); //Reffererar till fileInput så man kan ha annat utseende på input
    },
    onFilePicked(event) {
      this.files = event.target.files;
      if (!this.files.length) return; //Validering
      let fileName = this.files[0].name;
      if (fileName.lastIndexOf(".") <= 0) {
        return alert("Please add a valid file!"); //Validering av fil
      }
      this.fileReader.addEventListener("load", () => {
        this.imageUrl = this.fileReader.result; //För att visa bilden
      });
      this.fileReader.readAsDataURL(this.files[0]);

    },
    onSecondFilePicked(event) {
      let filesSecondary = event.target.files;
      let fileReader2 = new FileReader();

      if (!filesSecondary.length) return; //Validering
      if (filesSecondary[0].name.lastIndexOf(".") <= 0) {
        return alert("Please add a valid file!"); //Validering av fil
      }
      fileReader2.addEventListener("load", () => {
        this.imageUrlExtra1 = fileReader2.result; //För att visa bilden
      });
      fileReader2.readAsDataURL(filesSecondary[0]);
      this.formDataSecond.append(
        "files",
        filesSecondary[0],
        filesSecondary[0].name
      );
    },
    onThirdFilePicked(event) {
      let filesThird = event.target.files;
      let fileReader3 = new FileReader();

      if (!filesThird.length) return; //Validering
      if (filesThird[0].name.lastIndexOf(".") <= 0) {
        return alert("Please add a valid file!"); //Validering av fil
      }
      fileReader3.addEventListener("load", () => {
        this.imageUrlExtra2 = fileReader3.result; //För att visa bilden
      });
      fileReader3.readAsDataURL(filesThird[0]);
      this.formDataSecond.append("files", filesThird[0], filesThird[0].name);
    },
    uploadFiles(formData) {
      //Spara till disk
      return fetch("http://localhost:7999/upload-files", {
        method: "POST",
        body: formData
      }).then(response => response.json());
    }
  },
  computed: {
    formIsValid() {
      return (
        this.title !== "" &&
        this.description !== "" &&
        this.price !== "" &&
        this.title.length < 40 &&
        this.description.length < 300 &&
        this.imageUrl !== "http://localhost:7999/images/noImage.jpg"
      );
    },
    minDate() {
      let minDate = new Date();
      minDate.setDate(minDate.getDate() + 1);
      minDate = minDate.toISOString().slice(0, 10);
      return minDate;
    },
    maxDate() {
      let maxDate = new Date();
      maxDate.setMonth(maxDate.getMonth() + 1);
      maxDate = maxDate.toISOString().slice(0, 10);
      return maxDate;
    }
  },
  created: async function() {
    if (this.$store.state.status === false) {
      this.$router.push({ path: "/login" });
    }
  }
};
</script>

<style>
#productDescription {
  margin-top: -220px;
}
v-image {
  cursor: pointer;
}
.flexOne {
  flex: 1;
}
.pointerCursor {
  cursor: pointer;
}
</style>
