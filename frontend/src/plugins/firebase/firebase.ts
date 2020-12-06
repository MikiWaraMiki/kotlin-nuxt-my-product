import firebase from 'firebase/app'
import 'firebase/auth'
import { Plugin } from '@nuxt/types'

if (!firebase.apps.length) {
  const config = {
    apiKey: process.env.FIREBASE_API_KEY,
    authDomain: process.env.FIREBASE_AUTH_DOMAIN,
    projectId: process.env.FIREBASE_PROJECT_ID,
    appId: process.env.FIREBASE_APP_ID,
  }
  firebase.initializeApp(config)
}

// eslint-disable-next-line @typescript-eslint/no-unused-vars
const firebasePlugins: Plugin = (context, inject) => {
  firebase.auth().languageCode = 'ja'
  inject('firebase', firebase)
  inject('auth', firebase.auth())
}
export default firebasePlugins
export { firebase }
