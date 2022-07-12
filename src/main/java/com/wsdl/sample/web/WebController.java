package com.wsdl.sample.web;

import com.baeldung.springsoap.gen.Country;
import com.baeldung.springsoap.gen.Currency;
import com.baeldung.springsoap.gen.GetCountryRequest;
import com.baeldung.springsoap.gen.GetCountryResponse;

import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import tns.wpg.TokenSettle;
import tns.wpg.TokenSettleResponse;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jul 2022
 **/
@Endpoint
public class WebController {

    private static final String NAMESPACE_URI = "http://www.baeldung.com/springsoap/gen";
    private static final String WPG_NAMESPACE_URI = "tns:wpg";

    @PayloadRoot(namespace = WPG_NAMESPACE_URI, localPart = "customerRequest")
    @ResponsePayload
    public CustomerResponse checkCustomer(@RequestPayload JAXBElement<Customer> request) {
        LoggerFactory.getLogger(this.getClass().getName()).info("Request: {}", request.getValue());
//        return new JAXBElement<>(new QName(WPG_NAMESPACE_URI, "customerResponse"), CustomerResponse.class,
//                new CustomerResponse("10", "Wiley"));
        return new CustomerResponse("10", "Wiley");
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        Country country = new Country();
        country.setName("LK");
        country.setCurrency(Currency.EUR);
        country.setCapital("Col");
        country.setPopulation(11);
        response.setCountry(country);
        return response;
    }

    @PayloadRoot(namespace = WPG_NAMESPACE_URI, localPart = "tokenSettle")
    @ResponsePayload
    public TokenSettleResponse tokenSettle(@RequestPayload JAXBElement<TokenSettle> request) {
        TokenSettleResponse response = new TokenSettleResponse();
        response.setReturnCode("12");
        response.setReturnMessage("12");
        response.setTransID("12");
        response.setMerchantResponse("12");
        response.setMerchantID("12");
        response.setValue("12");
        response.setAcquirerID("12");
        response.setAcquirerName("12");
        response.setBankID("12");
        response.setBankName("12");
        response.setMaskedCardNumber("12");
        response.setCardExpiry("12");
        response.setMerchantReference("12");
        response.setAuthTransID("12");
        response.setOperation("12");
        response.setVendorID("12");
        response.setTimestamp("12");

        return response;
    }
}
