import { Middleware } from '@nuxt/types'
import { userAuthStore } from '~/store'

/**
 * 未ログインユーザに対する認証処理
 * store上にuidが存在しなければ、登録画面にリダイレクト
 */
const nonNeedLoginPath: string[] = ['/users/registration', '/', '/login']
const firebaseAuthMiddleware: Middleware = ({ route, redirect }) => {
  if (!userAuthStore.uid && !nonNeedLoginPath.includes(route.path || '/')) {
    redirect('/users/registration')
  }
}
export default firebaseAuthMiddleware
