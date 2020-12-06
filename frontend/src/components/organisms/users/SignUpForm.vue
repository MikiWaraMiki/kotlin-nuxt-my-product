<template>
  <div class="m-user-signup-form-area text-center">
    <div class="m-user-file-field-area py-4">
      <a-avatar-icon :size="84" color="primary"></a-avatar-icon>
    </div>
    <v-row class="c-user-input-filed-area">
      <v-col cols="12">
        <labeled-item label="メールアドレス">
          <p class="my-0">{{ email }}</p>
        </labeled-item>
      </v-col>
      <v-col cols="12">
        <labeled-item required label="表示名">
          <a-text-field
            :value="userDisplayName"
            required
            :max="50"
            placeholder="ところがどっこい"
            @input="(val) => $emit('update:userDisplayName', val)"
          />
        </labeled-item>
      </v-col>
      <v-col cols="12">
        <labeled-item required label="ユーザID">
          <a-text-field
            :value="userApplicationId"
            required
            :max="30"
            prefix="https://tokoro-ga-dokkoi.com/"
            placeholder="tokora_ga_dokkoi"
            @input="(val) => $emit('update:userApplicationId', val)"
          />
        </labeled-item>
      </v-col>
    </v-row>
  </div>
</template>
<script lang="ts">
import Vue, { PropType } from 'vue'
import AAvatarIcon from '@/components/atoms/AAvatarIcon.vue'
import LabeledItem from '@/components/atoms/LabeledItem.vue'
import ATextField from '@/components/atoms/ATextField.vue'
export default Vue.extend({
  components: {
    AAvatarIcon,
    LabeledItem,
    ATextField,
  },
  props: {
    email: {
      type: String as PropType<string>,
      required: true,
    },
    userDisplayName: {
      type: String as PropType<string>,
      required: true,
    },
    userApplicationId: {
      type: String as PropType<string>,
      required: true,
    },
  },
})
</script>
