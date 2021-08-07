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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A model view descriptor.
 *
 * @see NCClient#getModelInfo(String)
 * @see NCModel
 */
public interface NCModelInfo {
    /**
     * Gets unique, <i>immutable</i> ID of this model.
     * <p>
     * Note that <b>model IDs are immutable</b> while name and version
     * can be changed freely. Changing model ID is equal to creating a completely new model.
     * Model IDs (unlike name and version) are not exposed to the end user and only serve a
     * technical purpose. ID's max length is 32 characters.
     *
     * @return Unique, <i>immutable</i> ID of this model.
     */
    String getId();

    /**
     * Gets descriptive name of this model. Name's max length is 64 characters.
     *
     * @return Descriptive name for this model.
     */
    String getName();

    /**
     * Gets the version of this model using semantic versioning. Version's max length is 16 characters.
     *
     * @return A version compatible with (<a href="http://www.semver.org">www.semver.org</a>) specification.
     */
    String getVersion();

    /**
     * Gets optional short model description. This can be displayed by the management tools.
     *
     * @return Optional short model description.
     */
    String getDescription();

    /**
     * Gets the origin of this model like name of the class, file path or URL.
     *
     * @return Origin of this model like name of the class, file path or URL.
     */
    String getOrigin();

    /**
     * Gets maximum number of unknown words until automatic rejection. An unknown word is a word
     * that is not part of Princeton WordNet database. If you expect a very formalized and well-defined
     * input without uncommon slang and abbreviations you can set this to a small number
     * like one or two. However, in most cases we recommend leaving it as or set it to a larger
     * number like five or more.
     *
     * @return Maximum number of unknown words until automatic rejection.
     */
    int getMaxUnknownWords();

    /**
     * Gets maximum number of free words until automatic rejection. A free word is a known word that is
     * not part of any recognized token. In other words, a word that is present in the user input
     * but won't be used to understand its meaning. Setting it to a non-zero risks the misunderstanding
     * of the user input, while setting it to zero often makes understanding logic too rigid. In most
     * cases we recommend setting to between one and three. If you expect the user input to contain
     * many <i>noisy</i> idioms, slang or colloquials - you can set it to a larger number.
     *
     * @return Maximum number of free words until automatic rejection.
     */
    int getMaxFreeWords();

    /**
     * Gets maximum number of suspicious words until automatic rejection. A suspicious word is a word
     * that is defined by the model that should not appear in a valid user input under no circumstances.
     * A typical example of suspicious words would be words "sex" or "porn" when processing
     * queries about children books. In most cases this should be set to zero (default) to automatically
     * reject any such suspicious words in the user input.
     *
     * @return Maximum number of suspicious words until automatic rejection.
     */
    int getMaxSuspiciousWords();

    /**
     * Gets minimum word count (<i>including</i> stopwords) below which user input will be automatically
     * rejected as too short. In almost all cases this value should be greater than or equal to one.
     *
     * @return Minimum word count (<i>including</i> stopwords) below which user input will be automatically
     * rejected as too short.
     */
    int getMinWords();

    /**
     * Gets maximum word count (<i>including</i> stopwords) above which user input will be automatically
     * rejected as too long. In almost all cases this value should be greater than or equal to one.
     *
     * @return Maximum word count (<i>including</i> stopwords) above which user input will be automatically
     * rejected as too long.
     */
    int getMaxWords();

    /**
     * Gets minimum number of all tokens (system and user defined) below which user input will be
     * automatically rejected as too short. In almost all cases this value should be greater than or equal to one.
     *
     * @return Minimum number of all tokens.
     */
    int getMinTokens();

    /**
     * Gets maximum number of all tokens (system and user defined) above which user input will be
     * automatically rejected as too long. Note that sentences with large number of token can result
     * in significant processing delay and substantial memory consumption.
     *
     * @return Maximum number of all tokens.
     */
    int getMaxTokens();

    /**
     * Gets minimum word count (<i>excluding</i> stopwords) below which user input will be automatically rejected
     * as ambiguous sentence.
     *
     * @return Minimum word count (<i>excluding</i> stopwords) below which user input will be automatically
     * rejected as too short.
     */
    int getMinNonStopwords();

    /**
     * Whether to allow non-English language in user input.
     * Currently, only English language is supported. However, model can choose whether
     * to automatically reject user input that is detected to be a non-English. Note that current
     * algorithm only works reliably on longer user input (10+ words). On short sentences it will
     * often produce an incorrect result.
     *
     * @return Whether to allow non-English language in user input.
     */
    boolean isNonEnglishAllowed();

    /**
     * Whether to allow non-Latin charset in user input. Currently, only
     * Latin charset is supported. However, model can choose whether to automatically reject user
     * input with characters outside of Latin charset. If {@code false} such user input will be automatically
     * rejected.
     *
     * @return Whether to allow non-Latin charset in user input.
     */
    boolean isNotLatinCharsetAllowed();

    /**
     * Whether to allow known English swear words in user input. If {@code false} - user input with
     * detected known English swear words will be automatically rejected.
     *
     * @return Whether to allow known swear words in user input.
     */
    boolean isSwearWordsAllowed();

    /**
     * Whether to allow user input without a single noun. If {@code false} such user input
     * will be automatically rejected. Typically, for strict command or query-oriented models this should be set to
     * {@code false} as any command or query should have at least one noun subject. However, for conversational
     * models this can be set to {@code false} to allow for a smalltalk and one-liners.
     *
     * @return Whether to allow user input without a single noun.
     */
    boolean isNoNounsAllowed();

    /**
     * Whether to permutate multi-word synonyms. Automatic multi-word synonyms permutations greatly
     * increase the total number of synonyms in the system and allows for better multi-word synonym detection.
     * For example, if permutation is allowed the synonym "a b c" will be automatically converted into a
     * sequence of synonyms of "a b c", "b a c", "a c b". This property is closely related to {@link #isSparse()}
     * which are typically changed together. Note that individual model elements can override this property using
     * {@link NCElement#isPermutateSynonyms()} method.
     *
     * @return Whether to permutate multi-word synonyms.
     * @see NCElement#isPermutateSynonyms()
     * @see NCElement#isSparse()
     * @see #isSparse()
     */
    boolean isPermutateSynonyms();

    /**
     * Whether duplicate synonyms are allowed. If {@code true} - the model will pick the random
     * model element when multiple elements found due to duplicate synonyms. If {@code false} - model
     * will print error message and will not deploy.
     *
     * @return Whether to allow duplicate synonyms.
     */
    boolean isDupSynonymsAllowed();

    /**
     * Total number of synonyms allowed per model. Model won't deploy if total number of synonyms exceeds this
     * number.
     *
     * @return Total number of synonyms allowed per model.
     * @see #getMaxElementSynonyms()
     */
    int getMaxTotalSynonyms();

    /**
     * Whether to allow the user input with no user token detected. If {@code false} such user
     * input will be automatically rejected. Note that this property only applies to user-defined
     * token (i.e. model element). Even if there are no user defined tokens, the user input may still
     * contain system token like <code>nlpcraft:city</code> or <code>nlpcraft:date</code>. In many cases models
     * should be build to allow user input without user tokens. However, set it to {@code false} if presence
     * of at least one user token is mandatory.
     *
     * @return Whether to allow the user input with no user token detected.
     */
    boolean isNoUserTokensAllowed();

    /**
     * Whether this model elements allow non-stop words gaps in their multi-word synonyms.
     * This property is closely related to {@link #isPermutateSynonyms()} which are typically changed together.
     * Note that individual model elements can override this property using {@link NCElement#isSparse()}
     * method.
     *
     * @return Optional multi-word synonym sparsity model property.
     * @see NCElement#isSparse()
     * @see NCElement#isPermutateSynonyms()
     * @see #isPermutateSynonyms()
     */
    boolean isSparse();

    /**
     * Gets optional user defined model metadata that can be set by the developer and accessed later.
     * By default, it returns an empty map. Note that this metadata is mutable and can be
     * changed at runtime by the model's code.
     *
     * @return Optional user defined model metadata. By default, returns an empty map. Never returns {@code null}.
     */
    Map<String, Object> getMetadata();

    /**
     * Gets an optional list of stopwords to add to the built-in ones.
     * <p>
     * Stopword is an individual word (i.e. sequence of characters excluding whitespaces) that contribute no
     * semantic meaning to the sentence. For example, 'the', 'wow', or 'hm' provide no semantic meaning to the
     * sentence and can be safely excluded from semantic analysis.
     * <p>
     * NLPCraft comes with a carefully selected list of English stopwords which should be sufficient
     * for a majority of use cases. However, you can add additional stopwords to this list. The typical
     * use for user-defined stopwords are jargon parasite words that are specific to the model's domain.
     *
     * @return Potentially empty list of additional stopwords.
     */
    Set<String> getAdditionalStopWords();

    /**
     * Gets an optional list of stopwords to exclude from the built-in list of stopwords.
     * <p>
     * Just like you can add additional stopwords via {@link #getAdditionalStopWords()} you can exclude
     * certain words from the list of stopwords. This can be useful in rare cases when built-in
     * stopword has specific meaning of your model. In order to process them you need to exclude them
     * from the list of stopwords.
     *
     * @return Potentially empty list of excluded stopwords.
     */
    Set<String> getExcludedStopWords();

    /**
     * Gets an optional list of suspicious words. A suspicious word is a word that generally should not appear in user
     * sentence when used with this model. For example, if a particular model is for children oriented book search,
     * the words "sex" and "porn" should probably NOT appear in the user input and can be automatically rejected
     * when added here and model's metadata {@code MAX_SUSPICIOUS_WORDS} property set to zero.
     *
     * @return Potentially empty list of suspicious words in their lemma form.
     */
    Set<String> getSuspiciousWords();

    /**
     * Gets an optional map of macros to be used in this model. Macros and option groups are instrumental
     * in defining model's elements.
     *
     * @return Potentially empty map of macros.
     */
    Map<String, String> getMacros();

    /**
     * Gets a set of model elements or named entities. Model can have zero or more user defined elements.
     *
     * @return Set of model elements, potentially empty.
     */
    Set<NCElement> getElements();

    /**
     * Gets a set of IDs for built-in named entities (tokens) that should be enabled and detected for this model.
     * Unless model requests (i.e. enables) the built-in tokens in this method the NLP subsystem will not attempt
     * to detect them. Explicit enablement of the token significantly improves the overall performance by avoiding
     * unnecessary token detection. Note that you don't have to specify your own user elements here as they are
     * always enabled.
     *
     * @return Set of built-in tokens, potentially empty but never {@code null}, that should be enabled
     *      and detected for this model.
     */
    Set<String> getEnabledBuiltInTokens();

    /**
     * Gets s set of named entities (token) IDs that will be considered as abstract tokens.
     * An abstract token is only detected when it is either a constituent part of some other non-abstract token
     * or referenced by built-in tokens. In other words, an abstract token will not be detected in a standalone
     * unreferenced position. By (unless returned by this method), all named entities considered to be
     * non-abstract.
     * <p>
     * Declaring tokens as abstract is important to minimize number of parsing variants automatically
     * generated as permutation of all possible parsing compositions. For example, if it is known that a particular
     * named entity will only be used as a constituent part of some other token - declaring such named entity as
     * abstract can significantly reduce the number of parsing variants leading to a better performance,
     * and often simpler corresponding intent definition and callback logic.
     *
     * @return Set of abstract token IDs. Can be empty but never {@code null}.
     */
    Set<String> getAbstractTokens();

    /**
     * Gets maximum number of unique synonyms per model element after which either warning or error will be
     * triggered. Note that there is no technical limit on how many synonyms a model element can have apart
     * from memory consumption and performance considerations. However, in cases where synonyms are auto-generated
     * (i.e. from database) this property can serve as a courtesy notification that a model element has too many
     * synonyms. Also, in general, too many synonyms can potentially lead to a performance degradation.
     *
     * @return Maximum number of unique synonyms per model element after which either warning or
     *      error will be triggered.
     * @see #isMaxSynonymsThresholdError()
     * @see #getMaxTotalSynonyms()
     */
    int getMaxElementSynonyms();

    /**
     * Whether exceeding {@link #getMaxElementSynonyms()} will trigger a warning log or throwing an exception.
     * Note that throwing exception will prevent data probe from starting.
     *
     * @return Whether exceeding {@link #getMaxElementSynonyms()} will trigger a warning log or
     *      throwing an exception.
     * @see #getMaxElementSynonyms()
     */
    boolean isMaxSynonymsThresholdError();

    /**
     * Gets timeout in ms after which the unused conversation element is automatically "forgotten".
     * <p>
     * Just like in a normal human conversation if we talk about, say, "Chicago", and then don't mention it
     * for certain period of time during further dialog, the conversation participants subconsciously "forget"
     * about it and exclude it from conversation context. In other words, the term "Chicago" is no longer in
     * conversation's short-term-memory.
     * <p>
     * Note that both conversation timeout and {@link #getConversationDepth() depth}
     * combined define the expiration policy for the conversation management. These two properties allow fine-tuning
     * for different types of dialogs. For example, setting longer timeout and smaller depth mimics
     * slow-moving but topic-focused conversation. Alternatively, settings shorter timeout and longer depth better
     * supports fast-moving wide-ranging conversation that may cover multiple topics.
     *
     * @return Timeout in ms after which the unused conversation element is automatically "forgotten".
     * @see #getConversationDepth()
     */
    long getConversationTimeout();

    /**
     * Gets maximum number of requests after which the unused conversation element is automatically "forgotten".
     * <p>
     * Just like in a normal human conversation if we talk about, say, "Chicago", and then don't mention it
     * for a certain number of utterances during further dialog, the conversation participants subconsciously "forget"
     * about it and exclude it from conversation context. In other words, the term "Chicago" is no longer in
     * conversation's short-term-memory.
     * <p>
     * Note that both conversation {@link #getConversationTimeout() timeout} and depth
     * combined define the expiration policy for the conversation management. These two properties allow fine-tuning
     * for different types of dialogs. For example, setting longer timeout and smaller depth mimics
     * slow-moving but topic-focused conversation. Alternatively, settings shorter timeout and longer depth better
     * supports fast-moving wide-ranging conversation that may cover multiple topics.
     *
     * @return Maximum number of requests after which the unused conversation element is automatically "forgotten".
     * @see #getConversationTimeout()
     */
    int getConversationDepth();

    /**
     * Gets an optional map of restricted named entity combinations (linkage). Returned map is a map of entity ID to a set
     * of other entity IDs, with each key-value pair defining the restricted combination. Restricting certain entities
     * from being linked (or referenced) by some other entities allows reducing "wasteful" parsing variant
     * generation. For example, if we know that entity with ID "adjective" cannot be sorted, we can restrict it
     * from being linked with <code>nlpcraft:limit</code> and <code>nlpcraft:sort</code> entities to reduce the
     * amount of parsing variants being generated.
     * <p>
     * Only the following built-in entities can be restricted (i.e., to be the keys in the returned map):
     * <ul>
     *     <li><code>nlpcraft:limit</code></li>
     *     <li><code>nlpcraft:sort</code></li>
     *     <li><code>nlpcraft:relation</code></li>
     * </ul>
     * Note that entity cannot be restricted to itself (entity ID cannot appear as key as well as a
     * part of the value's set).
     *
     * @return Optional map of restricted named entity combinations. Can be empty but never {@code null}.
     */
    Map<String, Set<String>> getRestrictedCombinations();
}
