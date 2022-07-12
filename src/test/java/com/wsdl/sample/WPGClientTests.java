//package com.wsdl.sample;
//
//import com.wsdl.sample.config.SOAPConnector;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import tns.wpg.TokenSettle;
//import tns.wpg.TokenSettleResponse;
//
//@SpringBootTest
//class WPGClientTests {
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
//
//        TokenSettle request = new TokenSettle();
//        request.setName("12");
//        request.setAuthCode("12");
//        request.setCustomerPresent("12");
//        request.setRegion("12");
//        request.setSecurity("12");
//        request.setTimestamp("12");
//        request.setToken("12");
//        request.setTransID("12");
//        request.setDescription("12");
//        request.setVendorID("12");
//
//        TokenSettleResponse response = soapConnector
//                .callWebService("http://localhost:8080/wpg", request);
//        System.out.println("Got Response As below ========= : ");
//        System.out.println(response);
//        System.out.println(response.getReturnCode());
//        System.out.println(response.getReturnMessage());
//        System.out.println(response.getBankID());
//    }
//}
