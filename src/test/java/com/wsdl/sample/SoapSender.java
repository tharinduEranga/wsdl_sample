package com.wsdl.sample;

import com.wsdl.sample.web.Customer;
import com.wsdl.sample.web.CustomerResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * Created by: Tharindu Eranga
 * Date: 09 Jul 2022
 **/
public class SoapSender {
    public static void main(String[] args) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);
//        String body = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:gen=\"http://www.baeldung.com/springsoap/gen\">\n" +
//                "    <soapenv:Header/>\n" +
//                "    <soapenv:Body>\n" +
//                "        <gen:customerRequest>\n" +
//                "            <id>12</id>\n" +
//                "            <name>Thar Era</name>\n" +
//                "        </gen:customerRequest>\n" +
//                "    </soapenv:Body>\n" +
//                "</soapenv:Envelope>";

        Customer body = new Customer();
        body.setId("12");
        body.setName("Tharindu");

        String xml = jaxbObjectToXML(body);
//        String xml = jaxbToXML(body);
        System.out.println(xml);
//        xml = xml.replaceAll("customerRequest", "ns1:customerRequest");

//        JAXBElement<Customer> jaxbElement = objToJaxbXML(body);

//        HttpEntity<String> request = new HttpEntity<>(xml, headers);
//        ResponseEntity<String> response = new RestTemplate()
//                .postForEntity("http://localhost:8080/ws", request, String.class);



        HttpEntity<String> request = new HttpEntity<>(xml, headers);
        ResponseEntity<String> response = new RestTemplate()
                .postForEntity("http://localhost:8080/ws",
                        request, String.class);
        System.out.println(response.getBody());
        CustomerResponse customerResponse = parseResponse(response.getBody());
        System.out.println(customerResponse);
    }

    public static CustomerResponse parseResponse(String response) {
        try {

            SOAPMessage message = MessageFactory.newInstance().createMessage(null,
                    new ByteArrayInputStream(response.getBytes()));

            Unmarshaller unmarshaller = JAXBContext.newInstance(CustomerResponse.class).createUnmarshaller();
            return (CustomerResponse)unmarshaller.unmarshal(message.getSOAPBody().extractContentAsDocument());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    private static String jaxbObjectToXML(Customer body) {
//        try {
//            MessageFactory mfactory = MessageFactory.newInstance();
//            SOAPMessage soapMessage = mfactory.createMessage();
//
//            JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
//            Marshaller marshaller = jaxbContext.createMarshaller();
//
//            SOAPBody soapBody = petition.getSOAPBody();
//            soapBody.addDocument(marshaller.marshallDoc(body));
//
//            soapMessage.saveChanges();
//        } catch (SOAPException e) {
//            e.printStackTrace();
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    private static String jaxbObjectToXML(Customer customer) {
        try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);

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
            System.out.println("========================");
            marshaller.marshal(customer, System.out);
            marshaller.marshal(customer, doc);

            MessageFactory mfactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = mfactory.createMessage();
            SOAPPart soapPart = soapMessage.getSOAPPart();

            String myNamespace = "ns1";
            String myNamespaceURI = "tns:wpg";

            // SOAP Envelope
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

            /*
            Constructed SOAP Request Message:
            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:myNamespace="http://www.webserviceX.NET">
                <SOAP-ENV:Header/>
                <SOAP-ENV:Body>
                    <myNamespace:GetInfoByCity>
                        <myNamespace:USCity>New York</myNamespace:USCity>
                    </myNamespace:GetInfoByCity>
                </SOAP-ENV:Body>
            </SOAP-ENV:Envelope>
            */

            // SOAP Body
//            envelope.addAttribute(QName.valueOf("xmlns:ns1"), "tns:wpg");
//            envelope.setPrefix("SOAP-ENV");

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


    private static String jaxbToXML(Customer customer) {
        try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);

            //Create Marshaller
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            //Required formatting??
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Print XML String to Console
            StringWriter sw = new StringWriter();

            //Write XML to StringWriter
            jaxbMarshaller.marshal(customer, sw);

            //Verify XML Content
            return sw.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static JAXBElement<Customer> objToJaxbXML(Customer body) {
        return new JAXBElement<>(QName.valueOf("customerResponse"), Customer.class, body);
    }

}

