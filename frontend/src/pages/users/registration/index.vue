<template>
  <div class="sign-up-container">
    <div class="social-login-container text-center">
      <h2>ソーシャルアカウントで始める</h2>
      <h3 class="py-2">
        GoogleアカウントもしくはTwitterアカウントで始めることができます
      </h3>
      <v-row class="mx-auto py-5">
        <v-col cols="12" md="6">
          <google-login-button class="text-center" @click="signInGoole" />
        </v-col>
        <v-col cols="12" md="6">
          <twitter-login-button />
        </v-col>
      </v-row>
    </div>

    <div class="email-login-container text-center">
      <h2>メールアドレスで登録する</h2>
      <h3 class="py-2">登録したメールアドレスに、認証用のURLが届きます</h3>

      <v-form ref="form">
        <v-row class="mx-auto py-5">
          <v-col cols="12">
            <labeled-item required label="メールアドレス">
              <a-text-field v-model="email" required email />
            </labeled-item>
          </v-col>
        </v-row>
      </v-form>
      <div class="btn-container">
        <v-btn
          color="primary"
          class="submit-btn my-bg-primary font-14"
          @click="registerEmail"
        >
          送信
        </v-btn>
      </div>
    </div>
    <div class="snackbar-container">
      <a-snackbar v-model="successMessage" />
      <a-snackbar v-model="errorMessage" color="error" />
    </div>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import firebase from 'firebase'
import GoogleLoginButton from '@/components/atoms/AGoogleLoginButton.vue'
import TwitterLoginButton from '@/components/atoms/ATwitterLoginButton.vue'
import LabeledItem from '@/components/atoms/LabeledItem.vue'
import ATextField from '@/components/atoms/ATextField.vue'
import ASnackbar from '~/components/atoms/ASnackbar.vue'
// Store
import { temporaryRegistrationStore, userAuthStore } from '~/store'
import { IUserState } from '~/store/users/UserAuth'

export type VForm = Vue & { validate: () => boolean }
export default Vue.extend({
  components: {
    GoogleLoginButton,
    TwitterLoginButton,
    ATextField,
    LabeledItem,
    ASnackbar,
  },
  data() {
    return {
      email: '',
      successMessage: '',
      errorMessage: '',
    }
  },
  computed: {
    form(): VForm {
      return this.$refs.form as VForm
    },
  },
  async mounted() {
    const user: firebase.User | null = await new Promise(
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      (resolve, reject) => {
        this.$auth.onAuthStateChanged((user) => resolve(user || null))
      }
    )
    if (user) {
      userAuthStore.setUserInfo(user)
      this.$router.push('/users/new')
    }
  },
  methods: {
    async registerEmail() {
      try {
        if (!this.form.validate()) {
          this.$vuetify.goTo(0)
          return
        }
        const params = {
          email: this.email,
        }
        await temporaryRegistrationStore.postRegistration(params)
        this.successMessage = '登録が完了しました'
        this.$router.push('/users/registration/thanks')
      } catch (e) {
        if (e.status === 400 || e.status === 422) {
          this.errorMessage = e.data.error
        } else {
          this.errorMessage = 'エラーが発生しました。'
        }
      }
    },
    async signInGoole() {
      try {
        console.log('called')
        userAuthStore.setLoginTypeOnGoogle()
        await userAuthStore.signIn()
      } catch (e) {
        this.errorMessage = 'エラーが発生しました'
      }
    },
  },
})
</script>

<style lang="scss" scoped>
.sign-up-container {
  width: 720px;
  margin: 60px auto;
}
.btn-container {
  .submit-btn {
    width: 90%;
  }
}
</style>
