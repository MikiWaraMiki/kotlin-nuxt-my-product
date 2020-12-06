import { NuxtAxiosInstance } from '@nuxtjs/axios'
const basePath = '/api/v1/users'

/**
 * リクエストパラメータ
 */
export type NewUserCreateParams = {
  uuid: string
  email: string
  displayName: string
  userApplicationId: string
}
/**
 * レスポンスパラメータ
 */
export type ResponseData = {
  id: string
  email: string
  displayName: string
  userApplicationId: string
}

export class PostRepository {
  axios: NuxtAxiosInstance

  constructor(axios: NuxtAxiosInstance) {
    this.axios = axios
  }

  async post(params: NewUserCreateParams): Promise<ResponseData> {
    const data = await this.axios.$post(basePath, params)
    return data
  }
}
