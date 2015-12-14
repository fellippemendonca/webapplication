/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SoapConnections;

import HttpConnections.ResponseContents;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import org.apache.http.client.methods.HttpRequestBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author fellippe.mendonca
 */
public class SoapConnFactory {

    ResponseContents responseObj;
    SOAPConnection soapConnection;

    public SoapConnFactory() {
        this.soapConnection = null;
        this.responseObj = new ResponseContents("", "", "");
    }

    public ResponseContents SoapRequest(SoapPost soapPost) throws IOException {
        SOAPMessage soapResponse = null;
        String soapResponseString = "";
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            this.soapConnection = soapConnectionFactory.createConnection();
            //soapConnectionFactory

            /**/
            // Using a URLConnection
            /*var conn = new URLConnection("http://localhost:8080/se/soap");

            conn.setConnectTimeout(10000);	// Set timeout to 10 seconds
            conn.setReadTimeout(10000);

            var response = soapConnection.call(conn, request);
            */
            /**/
            
            
            soapResponse = this.soapConnection.call(getSoapMessageFromString(soapPost.getXmlRequest()), soapPost.getUri());
            soapResponseString = getStringFromSoapMessage(soapResponse);
        } catch (IOException ex) {
            System.out.println("An error occured while executing the request. Message: " + ex);
            this.responseObj.setContents(ex.toString());
        } finally {
            if (soapResponse == null) {
                this.responseObj.setStatus("HTTP/1.1 500 ERROR");
            } else {
                this.responseObj.setStatus("HTTP/1.1 200 OK");
                this.responseObj.setContents(soapResponseString);
            }
            try {
                this.soapConnection.close();
            } catch (SOAPException ex) {
                Logger.getLogger(SoapConnFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
            return this.responseObj;
        }
    }

    private static SOAPMessage getSoapMessageFromString(String xml) throws SOAPException, IOException {
        MessageFactory factory = MessageFactory.newInstance();
        SOAPMessage message = factory.createMessage(new MimeHeaders(), new ByteArrayInputStream(xml.getBytes(Charset.forName("UTF-8"))));
        return message;
    }

    private static String getStringFromSoapMessage(SOAPMessage soapResponse) throws SOAPException, IOException {
        
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        soapResponse.writeTo(out);
        return new String(out.toByteArray());
    }

    private static String getValueFromTag(String tagName, String xml) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));
        Element rootElement = document.getDocumentElement();

        NodeList list = rootElement.getElementsByTagName(tagName);

        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }
        return null;
    }
}
