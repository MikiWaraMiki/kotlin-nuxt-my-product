/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable require-await */
import { Context } from '@nuxt/types'
import { ActionContext, ActionTree, Store } from 'vuex'
import { initializeStores, userAuthStore } from '~/utils/store-accessor'

export const state = () => ({})
export type RootState = ReturnType<typeof state>

// initialize store
const initializer = (store: Store<any>) => initializeStores(store)
export const plugins = [initializer]

// nuxt server init
export const actions: ActionTree<any, any> = {
  nuxtServerInit: async (
    context: ActionContext<RootState, RootState>,
    server: Context
  ) => {
    const decodedToken: any = await userAuthStore.getCookie(server.req)
    if (decodedToken) {
      userAuthStore.setUserInfoFromCookie(decodedToken)
    }
  },
}

export * from '~/utils/store-accessor'
