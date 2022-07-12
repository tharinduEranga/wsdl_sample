package com.wsdl.sample;

/**
 * Created by: Tharindu Eranga
 * Date: 12 Jul 2022
 **/
import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
//import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class MyNamespaceMapper extends NamespacePrefixMapper {

    private static final String BAR_PREFIX = "ns1";
    private static final String TNS_URI = "tns:wpg";

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if(TNS_URI.equals(namespaceUri)) {
            return BAR_PREFIX;
        }
        return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[] { TNS_URI };
    }

}