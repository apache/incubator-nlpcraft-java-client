<img src="https://nlpcraft.org/images/nlpcraft_logo_black.gif" height="80px">
<br>
<a target=_ href="https://gitter.im/nlpcraftorg/community"><img alt="Gitter" src="https://badges.gitter.im/nlpcraftorg/community.svg"></a>&nbsp;
<a target=_ href="https://travis-ci.org/nlpcrafters/nlpcraft-java-client"><img alt="Build" src="https://travis-ci.org/nlpcrafters/nlpcraft-java-client.svg?branch=master"></a>&nbsp;
<a target=_ href="https://search.maven.org/search?q=org.nlpcraft"><img src="https://maven-badges.herokuapp.com/maven-central/org.nlpcraft/nlpcraft-java-client/badge.svg" alt="Maven"></a>

### What is Apache NLPCraft?
[Apache NLPCraft](https://nlpcraft.org/) is an open source library for adding a natural language interface to any applications. 
NLPCraft is free and developer friendly, it securely works with any private data source, has no hardware or software 
lock-in all the while giving you tremendous NLP powers.

### Java Client
Native Java client provide easy-to-use Java-based API that wraps standard NLPCraft [REST APIs](https://nlpcraft.docs.apiary.io/)
. It can be used by any JVM language that provides Java interop such as Scala, Groovy, or Kotlin. 

For any questions, feedback or suggestions:

 * Latest [Javadoc](https://nlpcrafters.github.io/nlpcraft-java-client/apis/latest/index.html) and [REST APIs](https://nlpcraft.org/using-rest.html)
 * Maven/Grape/Gradle/SBT [instructions](https://nlpcraft.org/download.html#java-client)
 * Send us a note at [support@nlpcraft.org](mailto:support@nlpcraft.org)
 * Post a question at [Stack Overflow](https://stackoverflow.com/questions/ask) using <code>nlpcraft</code> tag
 * If you found a bug or have an idea file new issue on [GitHub](https://github.com/nlpcrafters/nlpcraft-java-client/issues).
 
### Usage
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
Copyright (C) 2019 [NLPCraft.](https://nlpcraft.org) All Rights Reserved.


