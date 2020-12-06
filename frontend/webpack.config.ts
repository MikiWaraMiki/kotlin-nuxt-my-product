const path = require('path')

module.exports = {
  resolve: {
    alias: {
      '~': path.resolve('.', '/src'),
      '@': path.resolve('.', '/src')
    },
    extensions: ['.ts', '.tsx']
  }
}
