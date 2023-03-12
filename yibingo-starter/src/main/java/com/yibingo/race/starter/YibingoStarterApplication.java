package com.yibingo.race.starter;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@ServletComponentScan("com.yibingo.race")
@ComponentScan("com.yibingo.race")
@SpringBootApplication(scanBasePackages = {"com.yibingo.race"})
@MapperScan({"com.yibingo.race.dal","com.yibingo.race.quartz.dao"})
public class YibingoStarterApplication {

	public static void main(String[] args) {
		SpringApplication.run(YibingoStarterApplication.class, args);
	}


	//todo 部署https的地方
	/*//重定向启用https
	@Bean
	public ServletWebServerFactory servletContainer() {

		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint constraint = new SecurityConstraint();
				constraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				constraint.addCollection(collection);
				context.addConstraint(constraint);
			}
		};

		tomcat.addAdditionalTomcatConnectors(createHTTPConnector());

		return tomcat;

	}
	private Connector createHTTPConnector() {

		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");

		//同时启用http（8080）、https（8443）两个端口

		connector.setScheme("http");

		connector.setSecure(false);
		//Connector监听的http的端口号
		connector.setPort(8080);
		//监听到http的端口号后转向到的https的端口号
		connector.setRedirectPort(8443);

		return connector;

	}*/
}
