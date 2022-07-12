package com.wsdl.sample;

import com.dataaccess.webservicesserver.NumberToWords;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Created by: Tharindu Eranga
 * Date: 12 Jul 2022
 **/
public class NumberSender {
    public static void main(String[] args) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_XML);

        NumberToWords body = new NumberToWords();
        body.setUbiNum(BigInteger.TEN.add(BigInteger.ONE));

        String xml = jaxbObjectToXML(body);

        HttpEntity<String> request = new HttpEntity<>(xml, headers);
        ResponseEntity<String> response = new RestTemplate()
                .postForEntity("https://www.dataaccess.com/webservicesserver/NumberConversion.wso",
                        request, String.class);
        System.out.println(response.getBody());
    }


    private static String jaxbObjectToXML(NumberToWords numberToWords) {
        try {
            //Create JAXB Context
            JAXBContext jaxbContext = JAXBContext.newInstance(NumberToWords.class);

            //Create Marshaller
            Marshaller marshaller = jaxbContext.createMarshaller();

            //Required formatting??
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //Verify XML Content

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            marshaller.marshal(numberToWords, doc);

            MessageFactory mfactory = MessageFactory.newInstance();
            SOAPMessage soapMessage = mfactory.createMessage();

            SOAPPart soapPart = soapMessage.getSOAPPart();
            SOAPEnvelope envelope = soapPart.getEnvelope();
            envelope.removeNamespaceDeclaration(envelope.getPrefix());

            SOAPBody soapBody = soapMessage.getSOAPBody();
            soapBody.addDocument(doc);

            soapBody.addNamespaceDeclaration("xmlns", "http://www.dataaccess.com/webservicesserver/");

//            envelope.addNamespaceDeclaration("xmlns:ns1", "tns:wpg");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            soapMessage.writeTo(baos);

            return new String(baos.toByteArray(), StandardCharsets.UTF_8);

        } catch (JAXBException | SOAPException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
