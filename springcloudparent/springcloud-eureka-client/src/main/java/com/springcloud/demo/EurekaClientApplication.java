package com.springcloud.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Description: 模拟eureka客户端一个简单的链接例子：获取服务启动端口号
 * 
 * @EnableEurekaClient 注释表名此服务为注册中心的客户端程序
 * @RestController @Controller 和 @ResponseBody 的组合
 *                 Controller中的方法无法返回jsp页面，或者html，配置的视图解析器
 *                 InternalResourceViewResolver不起作用， 返回的内容就是Return 里的内容。
 * @Controller 如果需要返回JSON，XML或自定义mediaType内容到页面，则需要在对应的方法上加上@ResponseBody注解。
 * @author zx
 * @date 2019年2月18日
 */
@SpringBootApplication
@EnableEurekaClient
@RestController
public class EurekaClientApplication {
    
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Autowired
    RestTemplate restTemplate;
    
	public static void main(String[] args) {
		SpringApplication.run(EurekaClientApplication.class, args);
	}
	/**
	 * 通过 @Value 获取配置文件中的属性 书写格式 ${server.port}
	 */
	@Value("${server.port}")
	String port;

	@RequestMapping(value = "/port", method = RequestMethod.GET)
	public String home(
			@RequestParam(value = "name", defaultValue = "赵大大") String name) {
		return "Hello " + name + " ,I am from port:" + port;
	}
	
	@RequestMapping(value = "/zipkin", method = RequestMethod.GET)
    public String zipkinTest(String name) {
	    return restTemplate.getForObject("http://springcloud-zipkin-client/port?name="+name,String.class);
    }

}
