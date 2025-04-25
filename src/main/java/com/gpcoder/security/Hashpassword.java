package com.gpcoder.security;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Hashpassword {
    static final String FILE_NAME = "users.xml";
    public static void initXML() throws Exception {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element root = doc.createElement("users");
            doc.appendChild(root);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer t = tf.newTransformer();
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(doc), new StreamResult(file));
        }
    }

    public static String digest(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static boolean verifyUser(String username, String password) throws Exception {
        File xmlFile = new File(FILE_NAME);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document doc = dbFactory.newDocumentBuilder().parse(xmlFile);

        NodeList nodeList = doc.getElementsByTagName("user");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element user = (Element) nodeList.item(i);
            if (user.getElementsByTagName("username").item(0).getTextContent().equals(username)) {
                String hash = user.getElementsByTagName("password").item(0).getTextContent();
                return hash.equals(digest(password));
            }
        }
        return false;
    }

    public void saveUser(String username, String hashedPassword, int position) throws Exception {
        File xmlFile = new File(FILE_NAME);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document doc = dbFactory.newDocumentBuilder().parse(xmlFile);

        Element root = doc.getDocumentElement();
        Element user = doc.createElement("user");

        Element uname = doc.createElement("username");
        uname.setTextContent(username);

        Element pass = doc.createElement("password");
        pass.setTextContent(hashedPassword);

        Element pos = doc.createElement("position");
        pos.setTextContent(Integer.toString(position));

        user.appendChild(uname);
        user.appendChild(pass);
        user.appendChild(pos);
        root.appendChild(user);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(doc), new StreamResult(xmlFile));
    }
    
    public static String getPosition(String username) throws ParserConfigurationException, IOException {
        File xmlFile = new File(FILE_NAME);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document doc;
        try {
            doc = dbFactory.newDocumentBuilder().parse(xmlFile);
            NodeList nodeList = doc.getElementsByTagName("user");
            for (int i = 0; i < nodeList.getLength(); i++) {
            Element user = (Element) nodeList.item(i);
            if (user.getElementsByTagName("username").item(0).getTextContent().equals(username)) {
                return user.getElementsByTagName("position").item(0).getTextContent();
            }
        }
        } catch (SAXException ex) {
        }
        return null;
    }
    
}
