import { IncomingMessage } from 'http'
import { Action, Module, Mutation, VuexModule } from 'vuex-module-decorators'
import firebase from 'firebase'
import Cookies from 'universal-cookie'
import jwtDecode from 'jwt-decode'
import { $firebase } from '~/utils/firebase'

const AuthProviders = {
  GOOGLE: new $firebase.auth.GoogleAuthProvider(),
  EMAIL: new $firebase.auth.EmailAuthProvider(),
} as const
export type AuthProviderType = typeof AuthProviders[keyof typeof AuthProviders]

export interface IUserState {
  uid: string | null
  email: string | null
  displayName: string | null
  idToken: string | null
  loginType: AuthProviderType | null
}

@Module({
  stateFactory: true,
  namespaced: true,
  name: 'users/UserAuth',
})
export default class UserAuth extends VuexModule implements IUserState {
  uid: string | null = null
  email: string | null = null
  displayName: string | null = null
  idToken: string | null = null
  loginType: AuthProviderType | null = null

  get isLogin(): boolean {
    return this.uid !== null
  }

  @Mutation
  private setUid(uid: string) {
    this.uid = uid
  }

  @Mutation
  private setEmail(email: string) {
    this.email = email
  }

  @Mutation
  private setDisplayName(displayName: string) {
    this.displayName = displayName
  }

  @Mutation
  private setLoginType(loginType: AuthProviderType) {
    this.loginType = loginType
  }

  @Mutation
  private setIdToken(token: string) {
    this.idToken = token
  }

  @Action({ rawError: true })
  setLoginTypeOnGoogle() {
    this.setLoginType(AuthProviders.GOOGLE)
  }

  @Action({ rawError: true })
  setLoginTypeOnEmail(email: string) {
    this.setLoginType(AuthProviders.EMAIL)
    this.setEmail(email)
  }

  @Action({ rawError: true })
  async signIn() {
    if (this.loginType === null) {
      throw new Error('ログインタイプが指定されていない')
    }
    await $firebase.auth().signInWithRedirect(this.loginType)
  }

  @Action({ rawError: true })
  async setUserInfo(user: firebase.User) {
    if (user.email) {
      this.setEmail(user.email)
    }
    if (user.displayName) {
      this.setDisplayName(user.displayName)
    }
    this.setUid(user.uid)
    const token = await user.getIdToken(true)
    this.setIdToken(token)
    // cookieに認証情報をセット
    const cookies = new Cookies()
    cookies.set('access_token', token)
  }

  @Action({ rawError: true })
  setUserInfoFromCookie(decodedToken: any) {
    this.setEmail(decodedToken.email)
    this.setUid(decodedToken.user_id)
    this.setDisplayName(decodedToken.name)
  }

  // for cookie
  @Action({ rawError: true })
  getCookie(req: IncomingMessage) {
    if (process.server && process.static) return undefined
    if (!req.headers.cookie) return undefined

    const cookie = new Cookies(req.headers.cookie)
    const token = cookie.get('access_token')

    if (!token) return undefined

    // tokenをセットしておく
    this.setIdToken(token)
    const decodedToken = jwtDecode(token)
    return decodedToken
  }
}
