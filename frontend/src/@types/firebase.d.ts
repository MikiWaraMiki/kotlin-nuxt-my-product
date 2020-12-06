import Vue from 'vue'
import { firebase } from '~/plugins/firebase/firebase'
declare module 'vue/types/vue' {
  interface Vue {
    $auth: firebase.auth.Auth
  }
}
declare module 'vuex' {
  interface Store<S> {
    $auth: firebase.auth.Auth
  }
}
