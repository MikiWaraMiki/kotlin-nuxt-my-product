<template>
  <div class="thanks-container text-center">
    <h2>メールを確認してください</h2>
    <p class="py-5">{{ email }}</p>
    <p>ご登録いただいたメールアドレスに新規登録用のリンクをお送りしました。</p>
    <p>メールに記載されているリンクから新規登録を行ってください。</p>
  </div>
</template>
<script lang="ts">
import Vue from 'vue'
// Store
import { temporaryRegistrationStore } from '~/store'
export default Vue.extend({
  computed: {
    email(): string {
      const storeEmail = temporaryRegistrationStore.email
      return storeEmail
    },
  },
  created() {
    if (temporaryRegistrationStore.email === '') {
      // eslint-disable-next-line vue/no-side-effects-in-computed-properties
      this.$router.push('/users/registration')
    }
  },
})
</script>

<style lang="scss" scoped>
.thanks-container {
  width: 720px;
  margin: 60px auto;
}
</style>
