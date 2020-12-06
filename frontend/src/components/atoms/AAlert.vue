<template>
  <v-alert v-bind="$attrs" dense>
    {{ message }}
  </v-alert>
</template>
<script lang="ts">
import Vue, { PropType } from 'vue'
export default Vue.extend({
  props: {
    message: {
      type: String as PropType<string | null>,
      default: null,
    },
  },
})
</script>
