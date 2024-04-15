const {defineConfig} = require('@vue/cli-service')
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
