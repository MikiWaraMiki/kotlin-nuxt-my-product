<template>
  <v-app>
    <un-login-header></un-login-header>
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
export default Vue.extend({
  components: {
    UnLoginHeader
  },
  data() {
    return {

    }
  },
})
</script>
