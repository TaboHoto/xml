package tabou.xml;
import java.nio.file.Path;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
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
        return element.getTagName();
 }
 public String text(){
        return element.getTextContent();
 }
 public Optional<String> att(String attName){
  return Optional.of(element.getAttribute(attName))
   .filter(s -> !s.equals(""));
 }
 public Element getElement(){
  return this.element;
 }
}
