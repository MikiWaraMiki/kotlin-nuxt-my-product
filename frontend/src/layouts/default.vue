<template>
  <v-app>
    <template v-if="isLogin">
      <login-header :user-display-name="userDisplayName" />
    </template>
    <template v-else>
      <un-login-header></un-login-header>
    </template>
    <v-main>
      <v-container>
        <nuxt />
      </v-container>
    </v-main>
    <v-footer absolute app>
      <span>&copy; {{ new Date().getFullYear() }}</span>
    </v-footer>
  </v-app>
</template>

<script lang="ts">
import Vue from 'vue'
import UnLoginHeader from '~/layouts/UnLoginHeader.vue'
import LoginHeader from '~/layouts/LoginHeader.vue'
import { userAuthStore } from '~/store'
export default Vue.extend({
  components: {
    UnLoginHeader,
    LoginHeader,
  },
  computed: {
    isLogin(): boolean {
      return userAuthStore.isLogin
    },
    userDisplayName(): string {
      return userAuthStore.displayName || ''
    },
  },
})
</script>
