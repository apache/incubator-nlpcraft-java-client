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

package org.apache.nlpcraft.client;

import org.apache.http.client.config.RequestConfig;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * REST client test. Methods `ask`.
 */
class NCMultiClientsTest extends NCTestAdapter {
    /** */
    private static final String MDL_ID = "nlpcraft.alarm.ex";
    
    /**
     *
     * @throws Exception
     */
    @Test
    void test() throws Throwable {
        int clientsCnt = 5;
        int textTimeMs = 20000;
    
        ExecutorService pool = Executors.newFixedThreadPool(clientsCnt);
        Random rnd = new Random();
    
        AtomicReference<Throwable> err = new AtomicReference<>();
        AtomicInteger cnt = new AtomicInteger(0);
        
        long maxTime = System.currentTimeMillis() + textTimeMs;
    
        CountDownLatch cdl = new CountDownLatch(clientsCnt);
        
        for (int i = 0; i < clientsCnt; i++) {
            int fi = i;
            
            pool.execute(() -> {
                NCClient client;
        
                try {
                    client = new NCClientBuilder().
                        setRequestConfig(
                            RequestConfig.custom().
                                setSocketTimeout(20000).
                                setConnectionRequestTimeout(20000).
                                setConnectTimeout(20000).
                                build()
                        ).build();
            
                    try {
                        while (System.currentTimeMillis() < maxTime && err.get() == null) {
                            client.ask(
                                MDL_ID,
                                "Ping me in 3 minutes",
                                null,
                                false,
                                null,
                                null
                            );
    
                            System.out.println("Request sent [reqNum=" + cnt.incrementAndGet() + ", clientNum=" + fi + ']');
    
                            Thread.sleep(rnd.nextInt(500) + 1);
                        }
                    }
                    finally {
                        client.close();
                    }
                }
                catch (InterruptedException e) {
                    // No-op.
                }
                catch (Throwable e) {
                    err.compareAndSet(null, e);
                }
                finally {
                    cdl.countDown();
                }
            });
        }
        
        cdl.await(5, MINUTES);
    
        if (err.get() != null)
            throw err.get();
    
        System.out.println("Clients count: " + clientsCnt);
        System.out.println("Processed requests: " + cnt.get());
    }
}
