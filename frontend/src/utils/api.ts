import { NuxtAxiosInstance } from '@nuxtjs/axios'
import { AxiosError, AxiosResponse } from 'axios'
import { userAuthStore } from '~/store'

// eslint-disable-next-line import/no-mutable-exports
let $axios: NuxtAxiosInstance

export function initializeAxios(axiosInstance: NuxtAxiosInstance) {
  $axios = axiosInstance
  $axios.onRequest((config) => {
    const token = userAuthStore.idToken
    if (token) {
      config.headers.common.Authorization = 'Bearer ' + token
    }
  })
  $axios.onResponse((response: AxiosResponse) => {
    return Promise.resolve(response)
  })
  $axios.onError((error: AxiosError) => {
    return Promise.reject(error.response)
  })
}

export { $axios }
