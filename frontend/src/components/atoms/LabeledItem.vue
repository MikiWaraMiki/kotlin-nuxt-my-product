<template>
  <div :class="{ 'mb-5': !dense }" class="b-labeled-item">
    <component
      :is="tag"
      :class="{ small: small }"
      class="d-flex flex-column mb-1 text-left"
    >
      <span class="d-flex">
        <v-chip
          v-if="required"
          label
          small
          outlined
          color="error"
          class="mr-2 px-2"
          >必須</v-chip
        >
        <template v-if="label">
          <span class="b-labeled-item-label">{{ label }}</span>
        </template>
        <slot v-else name="label" />
      </span>
      <span v-if="hint" class="caption">{{ hint }}</span>
      <div class="caption">
        <slot name="hint" />
      </div>
      <span v-if="!$props.for" class="mt-1">
        <slot />
      </span>
    </component>
    <div v-if="$props.for" class="my-1">
      <slot :id="$props.for" />
    </div>
  </div>
</template>
<script lang="ts">
import Vue from 'vue'
export default Vue.extend({
  props: {
    label: {
      type: String,
      default: null,
    },
    required: {
      type: Boolean,
      default: false,
    },
    optional: {
      type: Boolean,
      default: false,
    },
    secret: {
      type: Boolean,
      default: false,
    },
    small: {
      type: Boolean,
      default: false,
    },
    for: {
      type: String,
      default: undefined,
    },
    tag: {
      type: String,
      default: 'label',
    },
    hint: {
      type: String,
      default: undefined,
    },
    dense: {
      type: Boolean,
      default: false,
    },
  },
})
</script>
<style lang="scss" scoped>
.small {
  font-size: 15px;
}
</style>
