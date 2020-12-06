import { NuxtAxiosInstance } from '@nuxtjs/axios'
import { IRegistrationPostRepository } from '~/store/users/TemporaryRegistration'

const path = '/api/v1/users/temporary_registration'

export type RegistrationPostParams = {
  email: string
}
export type ResponseData = {
  id: number
  email: string
}
export class PostRepository implements IRegistrationPostRepository {
  axios: NuxtAxiosInstance

  constructor(axios: NuxtAxiosInstance) {
    this.axios = axios
  }

  async post(params: RegistrationPostParams): Promise<ResponseData> {
    const data = await this.axios.$post(path, params)
    return data
  }
}
