//package com.wsdl.sample;
//
//import com.baeldung.springsoap.gen.GetCountryRequest;
//import com.baeldung.springsoap.gen.GetCountryResponse;
//import com.wsdl.sample.config.SOAPConnector;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class WsdlSampleApplicationTests {
//
//    @Autowired
//    private SOAPConnector soapConnector;
//
//    @Test
//    void contextLoads() {
//    }
//
//    @Test
//    void tester() {
//        String name = "Sajal";//Default Name
//
//        GetCountryRequest request = new GetCountryRequest();
//        request.setName(name);
//        GetCountryResponse response = soapConnector
//                .callWebService("http://localhost:8080/ws", request);
//        System.out.println("Got Response As below ========= : ");
//        System.out.println(response);
//        System.out.println(response.getCountry().getName());
//    }
//}
