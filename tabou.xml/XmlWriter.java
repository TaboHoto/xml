package tabou.xml;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Stack;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

public class XmlWriter implements AutoCloseable{
 private XMLStreamWriter xWriter;
 private Stack<String> stack = new Stack<>();
 public XmlWriter(Result input){
  try{
   this.xWriter = XMLOutputFactory.newFactory()
    .createXMLStreamWriter(input);
  }catch(XMLStreamException e){
   throw new RuntimeException(e);
  }
 }
    public XmlWriter(Path path){
        this(new StreamResult(path.toString()));
    }
    public XmlWriter tag(String tagName){
        try{
            this.xWriter.writeStartElement(tagName);
        }catch(XMLStreamException e){
            throw new RuntimeException(e);
        }
        this.stack.push(tagName);
        return this;
    }
    public XmlWriter empty(String tagName){
        try{
            this.xWriter.writeEmptyElement(tagName);
        }catch(XMLStreamException e){
            throw new RuntimeException(e);
        }
        return this;
    }
    public XmlWriter text(String value){
        try{
            this.xWriter.writeCharacters(value);
        }catch(XMLStreamException e){
            throw new RuntimeException(e);
        }
        return this;
    }
    public XmlWriter att(String name,String value){
        try{
            this.xWriter.writeAttribute(name,value);
        }catch(XMLStreamException e){
            throw new RuntimeException(e);
        }
        return this;
    }
    @Override
    public void close(){
        try{
            if(this.stack.empty()){
                this.xWriter.close();
                return;
            }
            this.xWriter.writeEndElement();
            this.stack.pop();
        }catch(XMLStreamException e){
            throw new RuntimeException(e);
        }
    }
}
