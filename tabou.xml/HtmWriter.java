package tabou.xml;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Consumer;
import javax.xml.transform.Result;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

public class HtmWriter implements AutoCloseable{
    XmlWriter xWriter;
    public HtmWriter(Path input){
        this.xWriter = new XmlWriter(input);
    }
    public HtmWriter(Result input){
        this.xWriter = new XmlWriter(input);
    }
    public HtmWriter tag(String tag,Consumer<HtmWriter> consumer){
        try(this){
            this.xWriter.tag(tag);
            consumer.accept(this);
        }
        return this;
    }
    public HtmWriter html(){
        this.xWriter.tag("html");
        return this;
    }
    public HtmWriter head(){
        this.xWriter.tag("head");
        return this;
    }
    public HtmWriter dl(){
        this.xWriter.tag("dl");
        return this;
    }
    public HtmWriter dt(){
        this.xWriter.tag("dt");
        return this;
    }
    public HtmWriter dt(Consumer<HtmWriter> consumer){
        return tag("dd",consumer);
    }
    public HtmWriter dd(){
        this.xWriter.tag("dd");
        return this;
    }
    public HtmWriter dd(Consumer<HtmWriter> consumer){
        return tag("dd",consumer);
    }
    public HtmWriter ul(){
        this.xWriter.tag("ul");
        return this;
    }
    public HtmWriter li(){
        this.xWriter.tag("li");
        return this;
    }
    public HtmWriter href(String href,Consumer<HtmWriter> func){
        try(this){
            this.xWriter.tag("a");
            this.xWriter.att("href",href);
            func.accept(this);
        }
        return this;
    }
    public HtmWriter br(){
        this.xWriter.empty("br");
        return this;
    }
    public HtmWriter meta(){
        this.xWriter.empty("meta");
        return this;
    }
    public HtmWriter text(String value){
        this.xWriter.text(value);
        return this;
    }
    public HtmWriter att(String name,String value){
        this.xWriter.att(name,value);
        return this;
    }
    public void end(){
        this.close();
    }
    @Override
    public void close(){
        this.xWriter.close();
    }
    public static void main(String[] args){
        var path = Paths.get(args[0]);
        System.out.println(path);
        System.out.println(path.toFile());
        try(HtmWriter htm = new HtmWriter(path)){
          try(htm){
              htm.dl();
          }
        }
    }
}
