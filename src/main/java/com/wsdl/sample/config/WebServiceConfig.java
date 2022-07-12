package com.wsdl.sample.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jul 2022
 **/
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*", "/JPCMSUpdateAssembly/*",
                "/taxation_customs/vies/services/checkVatService/*",
                "/advocate.php/*", "/webservicesserver/NumberConversion.wso/*");
    }

//
//    @Bean(name = "jpcms_update")
//    public SimpleWsdl11Definition wsdl11DefinitionJpcmsUpdate() {
//        SimpleWsdl11Definition definition = new SimpleWsdl11Definition();
//        definition.setWsdl(new ClassPathResource("jpcms_update.wsdl"));
//        return definition;
//    }
//
//    @Bean(name = "vies_web_service")
//    public SimpleWsdl11Definition wsdl11DefinitionViesWebService() {
//        SimpleWsdl11Definition definition = new SimpleWsdl11Definition();
//        definition.setWsdl(new ClassPathResource("vies_web_service.wsdl"));
//        return definition;
//    }
//
//    @Bean(name = "wpg")
//    public SimpleWsdl11Definition wsdl11DefinitionWpg() {
//        SimpleWsdl11Definition definition = new SimpleWsdl11Definition();
//        definition.setWsdl(new ClassPathResource("wpg.wsdl"));
//        return definition;
//    }

    @Bean(name = "numbers")
    public SimpleWsdl11Definition wsdl11DefinitionNumbers() {
        SimpleWsdl11Definition definition = new SimpleWsdl11Definition();
        definition.setWsdl(new ClassPathResource("numbers.wsdl"));
        return definition;
    }

//    @Bean
//    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
//        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
//        servlet.setApplicationContext(applicationContext);
//        servlet.setTransformWsdlLocations(true);
//        return new ServletRegistrationBean<>(servlet, "/ws/*", "/wpg/*");
//    }

    @Bean(name = "countries")
    public DefaultWsdl11Definition countriesWsdl11Definition(XsdSchema countriesSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("CountriesPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.baeldung.com/springsoap/gen");
        wsdl11Definition.setSchema(countriesSchema);
        return wsdl11Definition;
    }

    @Bean(name = "tokenSettle")
    public DefaultWsdl11Definition tokenSettleWsdl11Definition(XsdSchema tokenSettleSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("TokenSettlePort");
        wsdl11Definition.setLocationUri("/wpg");
        wsdl11Definition.setTargetNamespace("tns:wpg");
        wsdl11Definition.setSchema(tokenSettleSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
    }

    @Bean
    public XsdSchema tokenSettleSchema() {
        return new SimpleXsdSchema(new ClassPathResource("token_settle.xsd"));
    }

//    @Bean
//    public Jaxb2Marshaller marshaller() {
//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        // this is the package name specified in the <generatePackage> specified in
//        // pom.xml
//        marshaller.setContextPath("tns.wpg");
//        return marshaller;
//    }
//
//    @Bean
//    public SOAPConnector soapConnector(Jaxb2Marshaller marshaller) {
//        SOAPConnector client = new SOAPConnector();
//        client.setDefaultUri("http://localhost:8080/wpg");
//        client.setMarshaller(marshaller);
//        client.setUnmarshaller(marshaller);
//        return client;
//    }

//    @Bean
//    public Jaxb2Marshaller marshaller() {
//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        // this is the package name specified in the <generatePackage> specified in
//        // pom.xml
//        marshaller.setContextPath("com.baeldung.springsoap.gen");
//        return marshaller;
//    }
//
//    @Bean
//    public SOAPConnector soapConnector(Jaxb2Marshaller marshaller) {
//        SOAPConnector client = new SOAPConnector();
//        client.setDefaultUri("http://localhost:8080/ws");
//        client.setMarshaller(marshaller);
//        client.setUnmarshaller(marshaller);
//        return client;
//    }


}
