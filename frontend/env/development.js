import secret from './secret/development.secret'
module.exports = {
  FIREBASE_API_KEY: secret.FIREBASE_API_KEY,
  FIREBASE_PROJECT_ID: secret.FIREBASE_PROJECT_ID,
  FIREBASE_AUTH_DOMAIN: secret.FIREBASE_AUTH_DOMAIN,
  API_BASE_URL: 'http://host.docker.internal:8080/api/v1',
  BROWSER_BASE_URL: 'http://host.docker.internal:8080/api/v1',
  SERVER_SIDE_BASE_URL: 'http://host.docker.internal:8080/api/v1',
}
