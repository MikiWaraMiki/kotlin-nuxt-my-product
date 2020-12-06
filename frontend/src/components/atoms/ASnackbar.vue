<template>
  <v-snackbar v-model="hasValue" :timeout="5000" :color="color" right top>
    <span>{{ value }}</span>
  </v-snackbar>
</template>

<script lang="ts">
import Vue, { PropType } from 'vue'
export default Vue.extend({
  props: {
    value: {
      type: String as PropType<string | null>,
      default: null,
    },
    color: {
      type: String,
      default: 'success',
    },
  },
  computed: {
    hasValue: {
      get(): boolean {
        if (this.value === null) return false
        return !!this.value
      },
      set() {
        // stringで受け取ってbooleanで返してしまうので空で返却
        this.$emit('input', '')
      },
    },
  },
})
</script>
