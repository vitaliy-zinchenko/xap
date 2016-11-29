/*
 * Copyright (c) 2008-2016, GigaSpaces Technologies, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openspaces.textsearch;

import com.gigaspaces.query.extension.QueryExtensionManager;
import com.gigaspaces.query.extension.QueryExtensionProvider;
import com.gigaspaces.query.extension.QueryExtensionRuntimeInfo;
import com.gigaspaces.query.extension.metadata.QueryExtensionPathInfo;
import com.gigaspaces.query.extension.metadata.impl.DefaultQueryExtensionAnnotationInfo;
import com.gigaspaces.query.extension.metadata.impl.QueryExtensionPathInfoImpl;

import java.lang.annotation.Annotation;
import java.util.Properties;

/**
 * @author Vitaliy_Zinchenko
 * @since 12.1
 */
public class LuceneTextSearchQueryExtensionProvider extends QueryExtensionProvider {

    public static final String NAMESPACE = "text";

    private final Properties _customProperties;

    public LuceneTextSearchQueryExtensionProvider() {
        this(new Properties());
    }

    public LuceneTextSearchQueryExtensionProvider(Properties customProperties) {
        this._customProperties = customProperties;
    }

    @Override
    public String getNamespace() {
        return NAMESPACE;
    }

    @Override
    public QueryExtensionManager createManager(QueryExtensionRuntimeInfo info) {
        LuceneTextSearchConfiguration configuration = new LuceneTextSearchConfiguration(this, info);
        return new LuceneTextSearchQueryExtensionManager(this, info, configuration);
    }

    @Override
    public QueryExtensionPropertyInfo getPropertyExtensionInfo(String property, Annotation annotation) {
        QueryExtensionPropertyInfo result = new QueryExtensionPropertyInfo();
        if (annotation instanceof SpaceTextIndex) {
            SpaceTextIndex index = (SpaceTextIndex) annotation;
            String path = Utils.makePath(property, index.path());
            result.addPathInfo(path, createIndexPathInfo(index));
        } else if (annotation instanceof SpaceTextIndexes) {
            SpaceTextIndexes indexes = (SpaceTextIndexes)annotation;
            for (SpaceTextIndex index: indexes.value()) {
                String path = Utils.makePath(property, index.path());
                result.addPathInfo(path, createIndexPathInfo(index));
            }
        } else if (annotation instanceof SpaceTextAnalyzer) {
            SpaceTextAnalyzer analyzer = (SpaceTextAnalyzer) annotation;
            String path = Utils.makePath(property, analyzer.path());
            result.addPathInfo(path, createAnalyzerPathInfo(analyzer));
        } else if(annotation instanceof SpaceTextAnalyzers) {
            SpaceTextAnalyzers analyzers = (SpaceTextAnalyzers) annotation;
            for(SpaceTextAnalyzer analyzer: analyzers.value()) {
                String path = Utils.makePath(property, analyzer.path());
                result.addPathInfo(path, createAnalyzerPathInfo(analyzer));
            }
        }
        return result;
    }

    private QueryExtensionPathInfo createAnalyzerPathInfo(SpaceTextAnalyzer analyzer) {
        return new QueryExtensionPathInfoImpl(new TextAnalyzerQueryExtensionAnnotationInfo(analyzer.annotationType(), analyzer.analyzer()));
    }

    private QueryExtensionPathInfo createIndexPathInfo(SpaceTextIndex index) {
        return new QueryExtensionPathInfoImpl(new DefaultQueryExtensionAnnotationInfo(index.annotationType()));
    }

    public LuceneTextSearchQueryExtensionProvider setCustomProperty(String key, String value) {
        this._customProperties.setProperty(key, value);
        return this;
    }

    public String getCustomProperty(String key, String defaultValue) {
        return _customProperties.getProperty(key, defaultValue);
    }

}
