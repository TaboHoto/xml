package tabou.xml;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathNodes;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Xpath{
 private XPathNodes xPathNodes;
 private Document document;
 public static void main(String[] args){
  new Xpath().file(Paths.get(args[0]))
  .xpath(args[1]).printXml();
 }
 public static Document emptyDocument(){
        try{
            Document docu =
            DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
            docu.appendChild(docu.createElement("xpath"));
            return docu;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static void printXml(Document dom){
        try{
        Transformer transformer = TransformerFactory.newInstance()
            .newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"true");
        transformer.setOutputProperty(OutputKeys.METHOD,"xml");
        transformer.transform(new DOMSource(dom),
            new StreamResult(System.out));
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static void printXml(Element dom){
        try{
        Transformer transformer = TransformerFactory.newInstance()
            .newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"true");
        transformer.setOutputProperty(OutputKeys.METHOD,"xml");
        transformer.transform(new DOMSource(dom),
            new StreamResult(System.out));
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
 public Xpath file(Path file){
  try {
   this.document = DocumentBuilderFactory
    .newInstance().newDocumentBuilder()
    .parse(file.toString());
  }catch(ParserConfigurationException
    | SAXException
    | IOException e) {
   throw new RuntimeException(e);
  }
  return this;
 }
 public Xpath document(Document docu){
  this.document = docu;
  return this;
 }
 public Xpath xpath(String xpath){
  try {
   XPath xPath = XPathFactory
    .newInstance().newXPath();
   this.xPathNodes = xPath
    .evaluateExpression(xpath,
     this.document,XPathNodes.class);
  }catch(XPathExpressionException e) {
   throw new RuntimeException(e);
  }
  return this;
 }
 public void printXml(){
  Document docu = emptyDocument();
  Element docElement = docu.getDocumentElement();
  xmlList()
  .forEach(x -> docElement
   .appendChild(docu
     .importNode(x.getElement(),true)));
  printXml(docu);
 }
 public List<Xml> xmlList(){
  var list = new ArrayList<Xml>();
  for(Node node:this.xPathNodes){
   if(node instanceof Element element){
     list.add(new Xml(element));
   }
  }
  return list;
 }
 public List<String> textList(){
  var list = new ArrayList<String>();
  for(Node node:this.xPathNodes){
   list.add(node.getTextContent().trim());
  }
  return list;
 }
}
