
网站通用框架使用说明
@author jmwyw
@date 2016-03-01

com.jmwyw.action
---JmwywConfig 开发基础配置类，开发时启动项目
---CommonController  用户可匿名访问的应用根
---UploadController  文件上传控制器

com.jmwyw.action_app
	业务控制器目录，建议命名方式
		界面类控制器为 小写表名+"Web"
		接口类控制器为 小写表名+"Api"

com.jmwyw.db
	数据库实体类

com.jmwyw.process
	复杂业务处理类
	---BaseJOSNController基于JSON的RESTful风格控制器基础类，用于接口开发
	---BaseWebController 基于Web网页的控制器基础类，用于界面开发
	---BaseController 控制器基础类
	
com.jmwyw.timer
	跑批业务目录
		
com.jmwyw.action_sys
	通用系统权限管理控制器
	
com.jmwyw.tools
	工具类
	
com.jmwyw.security
	安全类
		加密算法及相关内容
		在线用户列表

com.jmwyw.z
	自动生成类，不建议在该包下写入代码，可能被自动生成的代码覆盖