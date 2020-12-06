<template>
  <div class="header">
    <v-app-bar clipped-left fixed app>
      <v-toolbar-title v-text="title" />
      <v-spacer />
      <v-btn color="primary" class="font-bold"> 会員登録 </v-btn>
      <v-btn color="primary" class="ml-2"> ログイン </v-btn>
    </v-app-bar>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
export default Vue.extend({
  data() {
    return {
      title: 'Skill Share',
    }
  },
})
</script>
