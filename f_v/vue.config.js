const {defineConfig} = require('@vue/cli-service')
module.exports = defineConfig({
    transpileDependencies: true

})
//   这里是改输出端口的
module.exports = {
    devServer: {
        port: 10002, // 端口号
        //     跨域代理设置
        //解读如果我们请的地址 /api/save => 代理到 http://localhost:8080/ssm/save
        proxy: {                    //设置代理，必须填
            '/api': {               //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
                // 牛逼 这里不要用localhost 用127.0.0.1
                target: 'http://127.0.0.1:8080/ssm2',  //代理的目标地址, 就是/api 代替 http://localhost:10001/
                changeOrigin: true,                 //是否设置同源，输入是的, 浏览器就允许跨域
                pathRewrite: {                      //路径重写
                    '/api': ''                      //选择忽略拦截器里面的单词
                }
            }
        }
    }
}