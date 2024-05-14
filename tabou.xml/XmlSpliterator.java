package tabou.xml;

import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import java.util.function.Consumer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlSpliterator implements Spliterator<Xml>{
    private final NodeList nodeList;
    private final int nodeLength;
    private int nodeCounter;
    public XmlSpliterator(NodeList nodes){
        this.nodeList   = nodes;
        this.nodeLength = nodes.getLength();
    }
    @Override
    public boolean tryAdvance(Consumer<? super Xml> c){
        while(this.nodeCounter < this.nodeLength) {
            Node node = nodeList.item(nodeCounter);
            this.nodeCounter++;
            if(node instanceof Element element) {
                c.accept(new Xml(element));
                return true;
            }
        }
        return false;
    }
    @Override
    public Spliterator<Xml> trySplit(){
        return null;
    }
    @Override
    //残りの大きさ
    public long estimateSize(){
        return nodeLength - nodeCounter;
    }
    @Override
    public int characteristics(){
        return 0;
    }
    public static Stream<Xml> stream(NodeList nodes){
        return StreamSupport.stream(new XmlSpliterator(nodes),false);
    }
}
