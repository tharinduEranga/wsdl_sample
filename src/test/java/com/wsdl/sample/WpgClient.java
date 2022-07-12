package com.wsdl.sample;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import tns.wpg.TokenSettle;
import tns.wpg.TokenSettleResponse;
import tns.wpg.TokenSettleResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by: Tharindu Eranga
 * Date: 12 Jul 2022
 **/
public class WpgClient {

    public static void main(String[] args) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);

        TokenSettle body = new TokenSettle();
        body.setVendorID("223");
        body.setTransID("539");
        body.setUserID("PG_PECK");
        body.setMethod("SOAP");
        body.setCustomerPresent("N");
        body.setToken("4000000000002100028");
        body.setAuthCode("123456");
        body.setDescription("AS2 Online Open order #808107935");
        body.setValue("4800.0");
        body.setCurrency("USD");
        body.setTimestamp("1657637591");
        body.setRegion("NA");
        body.setSecurity("58cc10cddbf0877eefd8157ca9ecc74a");

        String xml = jaxbObjectToXML(body);

        HttpEntity<String> request = new HttpEntity<>(xml, headers);
        ResponseEntity<String> response = new RestTemplate()
                .postForEntity("http://localhost:8080/ws",
                        request, String.class);
        System.out.println(response.getBody());
        TokenSettleResponse tokenSettleResponse = parseResponse(response.getBody());
        System.out.println(tokenSettleResponse);
    }


    private static String jaxbObjectToXML(TokenSettle tokenSettle) {
        try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(TokenSettle.class);

            //Create Marshaller
            Marshaller marshaller = jaxbContext.createMarshaller();

            //Required formatting??
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper", new MyNamespaceMapper());

            //Verify XML Content

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            marshaller.marshal(tokenSettle, doc);

            MessageFactory mfactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = mfactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();

            String myNamespace = "ns1";
            String myNamespaceURI = "tns:wpg";

            // SOAP Envelope
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

            SOAPBody soapBody = soapMessage.getSOAPBody();
            soapBody.addDocument(doc);

            soapBody.setPrefix("SOAP-ENV");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            soapMessage.writeTo(baos);

            return new String(baos.toByteArray(), StandardCharsets.UTF_8);

        } catch (JAXBException | SOAPException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static TokenSettleResponse parseResponse(String response) {
        try {

            SOAPMessage message = MessageFactory.newInstance().createMessage(null,
                    new ByteArrayInputStream(response.getBytes()));

            Unmarshaller unmarshaller = JAXBContext.newInstance(TokenSettleResponse.class).createUnmarshaller();
            return (TokenSettleResponse)unmarshaller.unmarshal(message.getSOAPBody().extractContentAsDocument());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
