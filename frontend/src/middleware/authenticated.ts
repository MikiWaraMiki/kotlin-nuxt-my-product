import firebase from 'firebase'
import { Middleware } from '@nuxt/types'
import { $firebase } from '~/utils/firebase'
import { userAuthStore } from '~/store'

const firebaseAuthMiddleware: Middleware = async ({ route, redirect }) => {
  const user: firebase.User | null = await new Promise(
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    (resolve, reject) => {
      $firebase.auth().onAuthStateChanged((user) => {
        resolve(user || null)
      })
    }
  )
  if (user) {
    userAuthStore.setUserInfo(user)
  }
  if (!user && route.name !== '/users/registration' && route.name !== '/') {
    redirect('/users/registration')
  }
}
export default firebaseAuthMiddleware
