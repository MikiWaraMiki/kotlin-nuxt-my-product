<template>
  <v-text-field
    ref="text"
    :type="type"
    :background-color="required && isEmptyValue ? '#fff2df' : '#f7f7f7'"
    :value="value"
    :rules="customRules"
    :error-messages="errorMessages"
    :hide-details="hideDetails"
    :validate-on-blur="validateOnBlur"
    dense
    outlined
    v-bind="$attrs"
    @focus="errorMessages = []"
    @blur="onBlur"
    v-on="$listeners"
  />
</template>

<script lang="ts">
import Vue, { PropType } from 'vue'
export type VText = Vue & { focus: () => void } & { blur: () => void }
export default Vue.extend({
  inheritAttrs: false,
  props: {
    type: {
      type: String as PropType<string>,
      default: 'text',
    },
    required: {
      type: Boolean as PropType<boolean>,
      default: false,
    },
    email: {
      type: Boolean as PropType<boolean>,
      default: false,
    },
    requiredMessage: {
      type: String as PropType<string>,
      default: undefined,
    },
    value: {
      type: [String, Number] as PropType<string | number | undefined>,
      default: undefined,
    },
    rules: {
      type: Array as PropType<any[]>,
      default: () => [],
    },
    asyncRules: {
      type: Array as PropType<any[]>,
      default: () => [],
    },
    hideDetails: {
      type: Boolean as PropType<boolean>,
      default: false,
    },
    validateOnBlur: {
      type: Boolean as PropType<boolean>,
      default: true,
    },
    max: {
      type: Number as PropType<number>,
      default: null,
    },
    min: {
      type: Number as PropType<number>,
      default: null,
    },
  },
  data: () => ({
    errorMessages: [],
  }),
  computed: {
    customRules(): Array<any> {
      if (!this.required) return []
      const requiredRule = [(v: string | number) => this.requiredResult(v)]
      return requiredRule.concat(this.rules)
    },
    isEmptyValue() {
      return (
        this.value === '' || this.value === undefined || this.value === null
      )
    },
  },
  mounted() {
    if (this.email) {
      this.rules.push((v: string) => this.$validator.email(v))
    }
    if (this.max) {
      this.rules.push((v: string) => this.$validator.maxLength(v, this.max))
    }

    if (this.min) {
      this.rules.push((v: string) => this.$validator.minLength(v, this.min))
    }
  },
  methods: {
    requiredResult(v: string | number): Boolean | String {
      return this.$validator.require(v)
    },
    focus() {
      // eslint-disable-next-line prettier/prettier
      ;(this.$refs.text as VText).focus()
    },
    blur() {
      // eslint-disable-next-line prettier/prettier
      ;(this.$refs.text as VText).blur()
    },
    onBlur(event: any) {
      const val = event.target.value
      if (val) {
        ;(this.asyncRules as any[]).forEach(async (rule: any) => {
          const trueOrMessage = await rule(val)
          if (trueOrMessage !== true) {
            this.errorMessages = this.errorMessages.concat(trueOrMessage)
          }
        })
      }
    },
  },
})
</script>

<style scoped lang="scss">
.v-text-field {
  margin-top: 0 !important;
  ::v-deep {
    input {
      border: none;
    }
  }
}
</style>
