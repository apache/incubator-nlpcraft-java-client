<img src="https://nlpcraft.org/images/nlpcraft_logo_black.gif" height="80px">
<br>
<a target=_ href="https://gitter.im/apache-nlpcraft/community"><img alt="Gitter" src="https://badges.gitter.im/apache-nlpcraft/community.svg"></a>

## What is Apache NLPCraft?
[Apache NLPCraft](https://nlpcraft.org/) is an open source library for adding a natural language interface to any applications. 
NLPCraft is free and developer friendly, it securely works with any private data source, and has no hardware or software 
lock-in.

## Java Client
Native Java client provide easy-to-use Java-based API that wraps standard NLPCraft [REST APIs](https://nlpcraft.org/using-rest.html). 
It can be used by any JVM language that provides Java interop such as Scala, Groovy, or Kotlin. 

For any questions, feedback or suggestions:

 * Latest [Javadoc](https://github.com/apache/incubator-nlpcraft-java-client/apis/latest/index.html) and [REST APIs](https://nlpcraft.org/using-rest.html)
 * Maven/Grape/Gradle/SBT [instructions](https://nlpcraft.org/download.html#java-client)
 * Post a question at [Stack Overflow](https://stackoverflow.com/questions/ask) using <code>nlpcraft</code> tag
 * Access [GitHub](https://github.com/apache/incubator-nlpcraft-java-client) mirror repository.
 * Ask questions on [dev@nlpcarft.apache.org](mailto:dev@nlpcarft.apache.org)
 
## Other Projects
- [Apache NLPCraft](https://github.com/apache/incubator-nlpcraft) - main NLPCraft project.
- [Apache NLPCraft UI](https://github.com/apache/incubator-nlpcraft-ui) - Web UI for testing and debugging.

## Usage
From any JVM-based application code:
```java
// Get client instance with all defaults.
NCClient cli = new NCClientBuilder().build();
 
// Perform any necessary calls...
NCResult res = cli.askSync("my.model.id", "Hey, isn't NLPCraft cool?");
 
// Close client & sign out at the end.
cli.close();
```

### Copyright
Copyright (C) 2020 Apache Software Foundation

<img src="https://www.apache.org/img/ASF20thAnniversary.jpg" height="64px">


