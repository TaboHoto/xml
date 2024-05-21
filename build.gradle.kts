//gradle publishToMavenLocal
defaultTasks("jar")
plugins{
  id("java-library")
  id("maven-publish")
}
group = "tabou.xml"
version = "0.1"
repositories.mavenCentral()
sourceSets {
    main {
        java.srcDir(".")
    }
}
tasks.withType<JavaCompile>().configureEach{
  options.encoding = "UTF-8"
}

publishing {
 publications {
  create<MavenPublication>("maven") {
   from(components["java"])
  }
 }
}
