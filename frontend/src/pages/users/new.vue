<template>
  <div class="sign-up-container text-center">
    <h2 class="my-bg-primary white--text py-2">新規ユーザ登録</h2>
    <template v-if="errorMessage != ''">
      <div class="my-4 py-2 error-container">
        <a-alert :message="errorMessage" outlined type="error" />
      </div>
    </template>
    <v-form ref="form" class="form-container py-6">
      <sign-up-form v-bind.sync="user" />
    </v-form>

    <div class="btn-container">
      <v-btn
        color="primary"
        dark
        class="submit-btn font-weight-bold py-2"
        @click="send"
      >
        送信する
      </v-btn>
    </div>
    <div class="snackbar-container">
      <template v-if="onSuccess">
        <a-snack-bar v-model="message" />
      </template>
      <template v-if="onError">
        <a-snack-bar v-model="message" color="error" />
      </template>
    </div>
  </div>
</template>
<script lang="ts">
import Vue from 'vue'
import SignUpForm from '~/components/organisms/users/SignUpForm.vue'
import ASnackBar from '~/components/atoms/ASnackbar.vue'
import AAlert from '~/components/atoms/AAlert.vue'
import { userAuthStore } from '~/store'
import {
  NewUserCreateParams,
  PostRepository,
} from '~/repositories/users/postRepository'

export type VForm = Vue & { validate: () => boolean }
export default Vue.extend({
  components: {
    SignUpForm,
    ASnackBar,
    AAlert,
  },
  data() {
    return {
      user: {
        email: '',
        userDisplayName: '',
        userApplicationId: '',
      },
      onError: false,
      onSuccess: false,
      message: '',
      errorMessage: '',
      repository: new PostRepository(this.$axios),
    }
  },
  computed: {
    form(): VForm {
      return this.$refs.form as VForm
    },
  },
  mounted() {
    this.user.email = userAuthStore.email || ''
    this.user.userDisplayName = userAuthStore.displayName || ''
  },
  methods: {
    async send() {
      try {
        this.onError = false
        this.onSuccess = false
        if (!this.form.validate()) {
          this.onError = true
          this.message = '入力内容に不備があります'
          return
        }
        await userAuthStore.getIdToken()
        const params: NewUserCreateParams = {
          uuid: userAuthStore.uid || '',
          email: this.user.email,
          displayName: this.user.userDisplayName,
          userApplicationId: this.user.userApplicationId,
        }
        await this.repository.post(params)
        this.message = '登録が完了しました'
        this.onSuccess = true
        this.errorMessage = ''
      } catch (e) {
        console.log(e)
        if (e.status === 400) {
          this.errorMessage = e.data.error
        }
        this.message = '登録に失敗しました'
        this.onError = true
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
