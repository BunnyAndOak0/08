module.exports={
  chainWebpack:config=>{
    //删除编译后的独立js文件上的预获取操作
    config.plugins.delete("prefetch")
  },
  // devServer: {
  //   proxy: {
  //       '/api': {
  //           // 此处的写法，目的是为了 将 /api 替换成 https://www.baidu.com/
  //           target: 'http://f3346291w6.zicp.vip:20504/',//设置你调用的接口域名和端口号 别忘了加http
  //           // 允许跨域
  //           changeOrigin: true,
  //           ws: true,
  //           pathRewrite: {
  //               '^/api': ''
  //           }
  //       }
  //   }
  // }
// 环境配置
// host: "localhost",
// port: 8080,
// https: false,
// hotOnly: false,
// disableHostCheck: true, // 禁用webpack热重载检查 解决热更新失效问题
// open: true, //配置自动启动浏览器
// proxy: {
//   // 配置多个代理(配置一个 proxy: 'http://localhost:4000' )
//   "/api": {
//     target: "http://f3346291w6.zicp.vip:20504/",
//     ws: true,
//     changeOrigin: true,
//     pathRewrite: {
//       "^/api": "" //请求的时候使用这个api就可以
//     }
//   }
// }
//     }

    // proxyTable:{
    //   '/api/**':{
    //     target:'http://localhost:3000',
    //     pathRewrite:{
    //       '^/api':'/'
    //     }
    //   }
    // }
// }

}






let proxyObj = {};
proxyObj['/']={
ws:false,
// target:'http://f3346291w6.zicp.vip/',
target:'http://f3346291w6.zicp.vip:20504/',

changOrigin:true,
pathRewrite:{
  '^/':''
}
}

module.exports={
devServer:{
  // host:'localhost',
  host:'0.0.0.0',
  port:8080,
  proxy:proxyObj
}
}
