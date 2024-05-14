# usage
new Xml(path).children("tag")
 .filter(x -> x.att("att"))
 .map(x -> x.text())
 .forEach(System.out::println);
