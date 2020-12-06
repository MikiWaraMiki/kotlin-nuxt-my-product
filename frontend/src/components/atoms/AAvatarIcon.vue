<template>
  <v-avatar :size="size" v-bind="$attrs" v-on="$listeners">
    <template v-if="imgUrl">
      <img :src="imgUrl" :alt="altText" />
    </template>
    <template v-else-if="iconText">
      <span class="white--text headline">{{ iconText }}</span>
    </template>
    <template v-else>
      <v-icon :size="size" dark> mdi-account-circle </v-icon>
    </template>
  </v-avatar>
</template>

<script lang="ts">
import Vue, { PropType } from 'vue'
export default Vue.extend({
  props: {
    imgUrl: {
      type: String as PropType<string | null>,
      default: null,
    },
    alt: {
      type: String as PropType<string | null>,
      default: null,
    },
    size: {
      type: Number as PropType<number | null>,
      default: 48,
    },
    iconText: {
      type: String as PropType<string | null>,
      default: null,
    },
  },
  computed: {
    altText(): string {
      if (this.alt) return this.alt

      return 'ユーザアイコン'
    },
  },
})
</script>
