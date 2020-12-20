<template>
  <div class="header">
    <v-app-bar clipped-left fixed app>
      <v-toolbar-title v-text="title" />
      <v-spacer />
      <v-btn text class="my-btn-header mr-2" small>
        <v-icon>mdi-magnify</v-icon>
      </v-btn>
      <v-btn text class="my-btn-header mr-2" small>
        <v-icon>mdi-bell-outline</v-icon>
      </v-btn>
      <v-menu offset-y>
        <template v-slot:activator="{ on, attrs }">
          <a-icon
            :img-url="iconImagePath"
            :icon-text="iconImagePrefix"
            color="primary"
            class="white-text mr-2"
            v-bind="attrs"
            v-on="on"
          />
        </template>
        <v-list>
          <v-list-item v-for="item in accountLinkItems" :key="item.path" link>
            <v-list-item-icon>
              <v-icon v-text="item.icon" />
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title v-text="item.title" />
            </v-list-item-content>
          </v-list-item>
          <v-divider />
          <v-list-item link>
            <v-list-item-icon>
              <v-icon> mdi-key-variant </v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>アカウント設定</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <v-list-item link>
            <v-list-item-icon>
              <v-icon>mdi-logout</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>ログアウト</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-menu>
      <v-btn color="primary" class="ml-2"> 新しくページを作成 </v-btn>
    </v-app-bar>
  </div>
</template>
<script lang="ts">
import Vue, { PropType } from 'vue'
import AIcon from '~/components/atoms/AAvatarIcon.vue'
export default Vue.extend({
  components: {
    AIcon,
  },
  props: {
    iconImagePath: {
      type: String as PropType<string | null>,
      default: null,
    },
    userDisplayName: {
      type: String as PropType<string>,
      required: true,
    },
  },
  data() {
    return {
      title: 'Skill Share',
      accountLinkItems: [
        {
          icon: 'mdi-account',
          title: 'プロフィール編集',
          link: '/dashboard/profiles',
        },
        {
          icon: 'mdi-wrench',
          title: 'スキルの管理',
          link: '/dashboard/skill',
        },
        {
          icon: 'mdi-map',
          title: 'スキルマップの管理',
          link: '/dashborad/skillmaps',
        },
      ],
    }
  },

  computed: {
    iconImagePrefix(): string {
      if (this.userDisplayName) {
        return this.userDisplayName[0]
      }
      return ''
    },
  },
})
</script>
