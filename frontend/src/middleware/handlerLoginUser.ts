import { Middleware } from '@nuxt/types'
import { userAuthStore } from '~/store'

/**
 * ログインユーザに対するリダイレクト処理
 * ログイン状態で、未ログインユーザ向けのページ閲覧時にはダッシュボードに飛ばす
 */
const targetRoutePath: string[] = ['/users/registration', '/login']
const alreadyAuthenticatedMiddleware: Middleware = ({ route, redirect }) => {
  if (!userAuthStore.uid && targetRoutePath.includes(route.path)) {
    redirect('/dashboard')
  }
}

export default alreadyAuthenticatedMiddleware
