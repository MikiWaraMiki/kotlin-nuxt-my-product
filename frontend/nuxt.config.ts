import colors from 'vuetify/es5/util/colors'
const environment = process.env.NODE_ENV || 'development'
const envSet = require(`./env/${environment}.js`)
const proxyConfig = environment === 'development' ? {
  '/api': {
    target: 'http://host.docker.internal:8080',
  }
} : undefined
console.log(proxyConfig)

export default {
  srcDir: 'src/',
  /*
   ** Nuxt target
   ** See https://nuxtjs.org/api/configuration-target
   */
  target: 'server',
  server: {
    port: 3000,
    host: '0.0.0.0',
  },
  // Global page headers (https://go.nuxtjs.dev/config-head)
  head: {
    htmlAttrs: {
      lang: 'ja',
    },
    bodyAttrs: {},
    titleTemplate: '%s - skillmapshare',
    title: 'skillmapshare',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' },
    ],
    link: [{ rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }],
  },

  // Global CSS (https://go.nuxtjs.dev/config-css)
  css: ['~/assets/common.scss'],

  // Router Config
  router: {
    middleware: [
      'authenticated'
    ]
  },
  // Plugins to run before rendering page (https://go.nuxtjs.dev/config-plugins)
  plugins: [
    { src: "~/plugins/validator.ts" },
    { src: "~/plugins/axios/index.ts" },
    { src: "~/plugins/firebase/firebase.ts" }
  ],

  // Auto import components (https://go.nuxtjs.dev/config-components)
  components: true,

  // Modules for dev and build (recommended) (https://go.nuxtjs.dev/config-modules)
  buildModules: [
    // https://go.nuxtjs.dev/typescript
    '@nuxt/typescript-build',
    // https://go.nuxtjs.dev/vuetify
    '@nuxtjs/vuetify',
  ],

  // Modules (https://go.nuxtjs.dev/config-modules)
  modules: [
    // https://go.nuxtjs.dev/axios
    '@nuxtjs/axios',
    '@nuxtjs/proxy',
  ],

  // Axios module configuration (https://go.nuxtjs.dev/config-axios)
  axios: {
    baseUrl: process.env.API_BASE_URL,
    proxy: environment === 'development'
  },
  proxy: proxyConfig,

  // Runtime Config (https://ja.nuxtjs.org/docs/2.x/configuration-glossary/configuration-runtime-config/)
  publicRuntimeConfig: {
  },
  privateRuntimeConfig: {
  },

  // Vuetify module configuration (https://go.nuxtjs.dev/config-vuetify)
  vuetify: {
    customVariables: ['~/assets/variables.scss'],
    theme: {
      dark: false,
      themes: {
        dark: {
          primary: colors.blue.darken2,
          accent: colors.grey.darken3,
          secondary: colors.amber.darken3,
          info: colors.teal.lighten1,
          warning: colors.amber.base,
          error: colors.deepOrange.accent4,
          success: colors.green.accent3,
        },
      },
    },
  },

  // Build Configuration (https://go.nuxtjs.dev/config-build)
  build: {
    // eslint-disable-next-line @typescript-eslint/no-unused-vars
    extend(config: any, ctx: any) {},
  },
  // Env set
  env: envSet,
  // TypeScript
  typescript: {
    typeCheck: true,
    ignoreNotFoundWarnings: true,
  },
}
