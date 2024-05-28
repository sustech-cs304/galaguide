const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://43.139.21.229:9260',
        // target: 'http://10.16.165.97:9260',
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      },
      '/api2': {
        target: 'http://43.139.21.229:9262',
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
