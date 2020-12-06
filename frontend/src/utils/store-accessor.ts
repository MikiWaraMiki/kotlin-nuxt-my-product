/* eslint-disable import/no-mutable-exports */
import { Store } from 'vuex'
import { getModule } from 'vuex-module-decorators'
import TemporaryRegistration from '~/store/users/TemporaryRegistration'
import UserAuth from '~/store/users/UserAuth'

let temporaryRegistrationStore: TemporaryRegistration
let userAuthStore: UserAuth
function initializeStores(store: Store<any>): void {
  temporaryRegistrationStore = getModule(TemporaryRegistration, store)
  userAuthStore = getModule(UserAuth, store)
}

export { initializeStores, temporaryRegistrationStore, userAuthStore }
