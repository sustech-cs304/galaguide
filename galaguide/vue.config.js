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
      '/v2-api': {
        target: 'http://43.139.21.229:9262',
        changeOrigin: true,
        pathRewrite: {
          '^/v2-api': ''
        }
      }
    }
  },
  pluginOptions: {
    electronBuilder: {
      chainWebpackMainProcess: (config) => {
        config.output.filename('background.js');
      },
      builderOptions: {
        appId: 'com.galaguide.app',
        productName: 'GalaGuide',
        win: {
          icon: 'public/icon.ico'
        },
        linux: {
          icon: 'public/icon.ico'
        },
        files: [
          'dist/**/*'
        ],
        extraResources: [
          {
            from: 'public',
            to: '.',
            filter: ['**/*']
          }
        ],
        asar: false
      }
    }
  }
})
