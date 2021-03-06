/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * NLPCraft Java Client API - provides native wrapper for
 * <a target=_ href="https://nlpcraft.apache.org/using-rest.html">REST APIs</a> for any JVM-based languages
 * like Java, Scala, Kotlin or Groovy.
 * <p>
 * <b>Usage</b><br>
 * Java client usage is straightforward - create client instance using {@link org.apache.nlpcraft.client.NCClientBuilder} and use the
 * client instance for all API calls:
 * <pre class="brush: java">
 *     // Get client instance with all defaults.
 *     NCClient cli = new NCClientBuilder().build();
 *
 *     // Perform any necessary calls...
 *     NCResult res = cli.askSync("my.model.id", txt);
 *
 *     // Close client &amp; sign out at the end.
 *     cli.close();
 * </pre>
 */
package org.apache.nlpcraft.client;