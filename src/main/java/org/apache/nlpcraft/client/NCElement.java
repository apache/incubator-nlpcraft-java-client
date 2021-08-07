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

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A model's element descriptor.
 *
 * @see NCModelInfo#getElements() 
 */
public interface NCElement {
    /**
     * Gets unique ID of this element.
     * <p>
     * This unique ID should be human-readable for simpler debugging and testing of the model.
     * Although element ID could be any arbitrary string it is highly recommended having
     * element ID as a lower case string starting with some model prefix, followed by colon and
     * then the element's name. For example, some built-in NLPCraft IDs are: <code>nlpcraft:date</code>,
     * <code>nlpcraft:city</code>.
     *
     * @return Unique ID of this element.
     */
    String getId();

    /**
     * Gets the list of groups this element belongs to.
     * <p>
     * Model element can belong to one or more groups. By default, the element belongs to a single group whose group
     * ID is equal to its {@link #getId() ID}.
     *
     * @return List of groups this element belongs to. By default, the model element belongs to one group
     *      with ID equal to the element {@link #getId() ID}.
     * @see #getId()
     */
    List<String> getGroups();

    /**
     * Gets optional user-defined element's metadata.
     *
     * @return Element's metadata or empty collection if none provided. implementation return empty collection.
     */
    Map<String, Object> getMetadata();

    /**
     * Gets optional element description.
     *
     * @return Optional element description. implementation returns {@code null}.
     */
    String getDescription();

    /**
     * Gets optional map of {@link NCValue values} for this element.
     * <p>
     * Each element can generally be recognized either by one of its synonyms or values. Elements and their values
     * are analogous to types and instances of that type in programming languages. Each value
     * has a name and optional set of its own synonyms by which that value, and ultimately its element, can be
     * recognized by. Note that value name itself acts as an implicit synonym even when no additional synonyms added
     * for that value.
     * <p>
     * Consider this example. A model element {@code x:car} can have:
     * <ul>
     *      <li>
     *          Set of general synonyms:
     *          <code>{transportation|transport|_} {vehicle|car|sedan|auto|automobile|suv|crossover|coupe|truck}</code>
     *      </li>
     *      <li>Set of values:
     *          <ul>
     *              <li>{@code mercedes} with synonyms {@code (mercedes, mercedes-benz, mb, benz)}</li>
     *              <li>{@code bmw} with synonyms {@code (bmw, bimmer)}</li>
     *              <li>{@code chevrolet} with synonyms {@code (chevy, chevrolet)}</li>
     *          </ul>
     *      </li>
     * </ul>
     * With that setup {@code x:car} element will be recognized by any of the following input sub-string:
     * <ul>
     *      <li>{@code transport car}</li>
     *      <li>{@code benz}</li>
     *      <li>{@code automobile}</li>
     *      <li>{@code transport vehicle}</li>
     *      <li>{@code sedan}</li>
     *      <li>{@code chevy}</li>
     *      <li>{@code bimmer}</li>
     *      <li>{@code x:car}</li>
     * </ul>
     *
     * @return Map of value's name and its synonyms or {@code null} if not defined.
     */
    List<NCValue> getValues();

    /**
     * Gets optional ID of the immediate parent element. Parent ID allows model elements to form into hierarchy.
     *
     * @return Optional parent element ID, or {@code null} if not specified. implementation returns
     *      {@code null}.
     */
    String getParentId();

    /**
     * Gets the list of synonyms by which this model element will be recognized by. Read more about
     * many forms of synonyms in <a target=_ href="https://nlpcraft.apache.org/data-model.html">Data Model</a> section
     * and review <a target=_ href="https://github.com/apache/incubator-nlpcraft/tree/master/nlpcraft-examples">examples</a>.
     *
     * @return List of synonyms for this element. List is generally optional since element's ID acts
     *      as an implicit synonym. implementation returns an empty list.
     */
    List<String> getSynonyms();

    /**
     * Whether to permutate multi-word synonyms. Automatic multi-word synonyms permutations greatly
     * increase the total number of synonyms in the system and allows for better multi-word synonym detection.
     * For example, if permutation is allowed the synonym "a b c" will be automatically converted into a
     * sequence of synonyms of "a b c", "b a c", "a c b".
     * <p>
     * This property overrides the value from {@link NCModelInfo#isPermutateSynonyms()}.
     * One should use this property if model's value isn't applicable to this element.
     *
     * @return Optional synonym permutate property overriding model's one.
     * @see NCModelInfo#isPermutateSynonyms()
     */
    Optional<Boolean> isPermutateSynonyms();

    /**
     * Whether this element allows the non-stop words gaps in its multi-word synonyms.
     * <p>
     * This property overrides the value from {@link NCModelInfo#isSparse()}.
     * One should use this property if model's value isn't applicable to this element.
     *
     * @return Optional multi-word synonym sparsity property overriding model's one.
     * @see NCModelInfo#isSparse()
     */
    Optional<Boolean> isSparse();
}
