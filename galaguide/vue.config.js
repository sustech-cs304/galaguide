const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:9260',
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      },
      '/api2': {
        target: 'http://localhost:9262',
        changeOrigin: true,
        pathRewrite: {
          '^/api2': ''
        }
      }
    }
  },
  pluginOptions: {
    electronBuilder: {
      chainWebpackMainProcess: (config) => {
        config.output.filename('background.js');
      }
    }
  }
})
