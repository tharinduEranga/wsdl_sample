package com.wsdl.sample.config;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

/**
 * Created by: Tharindu Eranga
 * Date: 11 Jul 2022
 **/
@Component
public class SOAPConnector extends WebServiceGatewaySupport {

    public SOAPConnector() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this is the package name specified in the <generatePackage> specified in pom.xml
        marshaller.setContextPath("tns.wpg");

        super.setDefaultUri("http://localhost:8080/wpg");
        super.setMarshaller(marshaller);
        super.setUnmarshaller(marshaller);
    }

    public <K, T> T callWebService(String url, K request){
        return (T) getWebServiceTemplate().marshalSendAndReceive(url, request);
    }
}
