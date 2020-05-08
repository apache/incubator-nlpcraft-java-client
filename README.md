<!--
 Licensed to the Apache Software Foundation (ASF) under one or more
 contributor license agreements.  See the NOTICE file distributed with
 this work for additional information regarding copyright ownership.
 The ASF licenses this file to You under the Apache License, Version 2.0
 (the "License"); you may not use this file except in compliance with
 the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-->

<img src="https://nlpcraft.apache.org/images/nlpcraft_logo_black.gif" height="80px">
<br>

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/apache/opennlp/master/LICENSE)
[![Jenkins](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fbuilds.apache.org%2Fview%2FIncubator%2520Projects%2Fjob%2Fincubator-nlpcraft%2F)](https://builds.apache.org/view/Incubator%20Projects/job/incubator-nlpcraft/)
[![Documentation Status](https://img.shields.io/:docs-latest-green.svg)](https://nlpcraft.apache.org/docs.html)
[![Gitter](https://badges.gitter.im/apache-nlpcraft/community.svg)](https://gitter.im/apache-nlpcraft/community)

## What is Apache NLPCraft?
[Apache NLPCraft](https://nlpcraft.apache.org/) is an open source library for adding a natural language interface to any applications. 
NLPCraft is free and developer friendly, it securely works with any private data source, and has no hardware or software 
lock-in.

## Java Client
Native Java client provide easy-to-use Java-based API that wraps standard NLPCraft [REST APIs](https://nlpcraft.apache.org/using-rest.html). 
It can be used by any JVM language that provides Java interop such as Scala, Groovy, or Kotlin. 

For any questions, feedback or suggestions:

 * Latest [Javadoc](https://github.com/apache/incubator-nlpcraft-java-client/apis/latest/index.html) and [REST APIs](https://nlpcraft.apache.org/using-rest.html)
 * Maven/Grape/Gradle/SBT [instructions](https://nlpcraft.apache.org/download.html#java-client)
 * Post a question at [Stack Overflow](https://stackoverflow.com/questions/ask) using <code>nlpcraft</code> tag
 * Access [GitHub](https://github.com/apache/incubator-nlpcraft-java-client) mirror repository.
 * Join on [dev@nlpcarft.apache.org](mailto:dev@nlpcarft.apache.org)
 
## Other Projects
- [Apache NLPCraft](https://github.com/apache/incubator-nlpcraft) - Main NLPCraft project.
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


