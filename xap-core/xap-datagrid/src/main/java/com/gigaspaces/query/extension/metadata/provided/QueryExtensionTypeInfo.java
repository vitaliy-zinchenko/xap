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

package com.gigaspaces.query.extension.metadata.provided;

import com.gigaspaces.query.extension.metadata.QueryExtensionAnnotationAttributesInfo;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vitaliy_Zinchenko
 * @since 12.1
 */
@com.gigaspaces.api.InternalApi
public class QueryExtensionTypeInfo {
    private Map<Class<? extends Annotation>, QueryExtensionAnnotationAttributesInfo> actionInfos = new HashMap<Class<? extends Annotation>, QueryExtensionAnnotationAttributesInfo>();

    public void add(Class<? extends Annotation> action, QueryExtensionAnnotationAttributesInfo actionInfo) {
        actionInfos.put(action, actionInfo);
    }

    public Collection<Class<? extends Annotation>> getActions() {
        return actionInfos.keySet();
    }

    public QueryExtensionAnnotationAttributesInfo getActionInfo(Class<? extends Annotation> actionType) {
        return actionInfos.get(actionType);
    }

}