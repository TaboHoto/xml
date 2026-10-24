package tabou.xml;
import java.nio.file.Path;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Xml{
 private final Element element;
 public Xml(Path path){
  try{
   this.element = DocumentBuilderFactory
    .newInstance().newDocumentBuilder()
    .parse(path.toFile()).getDocumentElement();
  }catch(ParserConfigurationException
   | SAXException | IOException e){
   throw new RuntimeException(e);
  }
 }
 public Xml(Element e){
  this.element = e;
 }
 public Xml first(String tagName){
  return children(tagName).findFirst().orElse(null);
 }
 public Xml parent(){
  return new Xml((Element)element.getParentNode());
 }
 public Stream<Xml> child(){
  return XmlSpliterator.stream(element.getChildNodes());
 }
 public Stream<Xml> children(String tagName){
  return XmlSpliterator.stream(element.getElementsByTagName(tagName));
 }
 public String name(){
  return this.element.getTagName();
 }
 public String text(){
   return this.element.getTextContent();
 }
 public List<String> textList(){
  var list = new ArrayList<String>();
  var nodeList = this.element.getChildNodes();
  for(int i= 0;i < nodeList.getLength();i++){
   var node = nodeList.item(i);
   if (node.getNodeType() == Node.TEXT_NODE
    || node.getNodeType() == Node.ELEMENT_NODE){
    list.add(node.getTextContent());
   }
  }
  return list;
 }
 public Optional<String> att(String attName){
  return Optional.of(element.getAttribute(attName))
   .filter(s -> !s.equals(""));
 }
 public Element getElement(){
  return this.element;
 }
}
