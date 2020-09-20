1.把依赖整合到父工程和common中
2.把公共类(exception，R应该也可以提取，但是由于设置的状态码不同 后期再后考虑提出来)整合到common中
3.把application.properties变成了application.yml使其层级更清楚
4.改变了包名：变为edu.kmust.xxx的命名形式，因为tedu是达内的简称，我们是学校的项目，所以用了kmust
5.public MultipartConfigElement multipartConfigElement
  这个配置被我从主运行类中提出来专门作为配置类加入了，后期提取文件上传功能应该会一起提出来
6.diary和publish作为单独微服务运行
  user：运行在10000端口
  publish：运行在20000端口
  diary：运行在30000端口
  以此类推
7.提取diary和publish都有的文件上传功能作为一个单独的微服务（这个还没做）
9.SpringSecurity的明文密码为1234
  加密后为：{bcrypt}$2a$10$6C0gna8SZ/xhharngKyaeeTHWXMeWUkDVbrdLUf4cFW/3/sI7obhS
10.添加服务注册功能，有一个简单的登录注册界面
session共享到redis里面

