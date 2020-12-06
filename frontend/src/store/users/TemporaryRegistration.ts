import { NuxtAxiosInstance } from '@nuxtjs/axios'
import { Action, Module, Mutation, VuexModule } from 'vuex-module-decorators'
import {
  PostRepository,
  RegistrationPostParams,
  ResponseData,
} from '~/repositories/users/temporary_registration/postRepository'
import { $axios } from '~/utils/api'

export interface ITemporaryRegistrationState {
  onSuccess: boolean
  id: number
  email: string
}

export interface IRegistrationPostRepository {
  axios: NuxtAxiosInstance

  post(params: RegistrationPostParams): Promise<ResponseData>
}

@Module({
  stateFactory: true,
  namespaced: true,
  name: 'users/TemporaryRegistration',
})
export default class TemporaryRegistration
  extends VuexModule
  implements ITemporaryRegistrationState {
  onSuccess = false
  id = 0
  email = ''

  @Mutation
  responseIsSuccess(data: ResponseData) {
    this.onSuccess = true
    this.id = data.id
    this.email = data.email
  }

  @Action({ rawError: true })
  async postRegistration(params: RegistrationPostParams) {
    const repository = new PostRepository($axios)
    const result = await repository.post(params)

    this.responseIsSuccess(result)
  }
}
