import axios from "axios";
//通过axios创建对象-request对象,用于发送请求到后端
const request = axios.create({
    // 五秒之内请求需要完成 不然报错
    timeout: 5000
})

//request拦截器的处理
//1. 可以对请求做统一的处理
//2. 比如统一的加入token, Content-Type等
// 这里是对发送请求进行统一处理
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8'
    return config
}, error => {
    return Promise.reject(error)
})
// 这里是对返回的数据进行统一处理
// 所以当axios再拿到数据的时候, 就会先经过这里
// 我们可以直接操作response.data 通过res

request.interceptors.response.use(
    response => {
        let res = response.data
        // 如果返回的是文件,就返回
        if(response.config.responseType === 'blob'){
            return res
        }
        // 如果是string, 就转成json对象
        if(typeof res === 'string'){
            //如果res 不为null, 就进行转换成json对象
            res = res ? JSON.parse(res) : res
        }
        return res
    },error =>{
        console.log("err",error)
        return Promise.reject(error)
    }
)

export default request

