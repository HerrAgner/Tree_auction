module.exports = {
  outputDir: '../src/main/resources/static',

  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080/api',
        ws: true,
        changeOrigin: true
      }
    }
  },

  publicPath: undefined,
  assetsDir: undefined,
  runtimeCompiler: undefined,
  productionSourceMap: undefined,
  parallel: undefined,
  css: undefined
};