import { Validator } from '~/plugins/validator'
declare module 'vue/types/vue' {
  interface Vue {
    $validator: Validator
  }
}
