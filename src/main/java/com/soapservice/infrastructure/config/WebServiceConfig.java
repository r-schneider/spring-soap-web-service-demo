package com.soapservice.infrastructure.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig {
  @Bean
  public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(applicationContext);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean<MessageDispatcherServlet>(servlet, "/service/*");
  }

  @Bean(name = "movies")
  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema moviesSchema) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("MoviesPort");
    wsdl11Definition.setLocationUri("/service/movie");
    wsdl11Definition.setTargetNamespace("http://springsoap.com/demo/movieservice");
    wsdl11Definition.setSchema(moviesSchema);
    return wsdl11Definition;
  }

  @Bean
  public XsdSchema moviesSchema() {
    return new SimpleXsdSchema(new ClassPathResource("wsdl/movie.xsd"));
  }
}