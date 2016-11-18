package org.openspaces.textsearch;

import com.gigaspaces.internal.io.IOUtils;
import com.gigaspaces.query.extension.metadata.impl.DefaultQueryExtensionAnnotationInfo;

import org.apache.lucene.analysis.Analyzer;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.annotation.Annotation;

/**
 * @author Vitaliy_Zinchenko
 */
public class TextAnalyzerQueryExtensionAnnotationInfo extends DefaultQueryExtensionAnnotationInfo implements Externalizable {

    private static final long serialVersionUID = 1L;

    private Class<? extends Analyzer> analyzer;

    public TextAnalyzerQueryExtensionAnnotationInfo() {
    }

    public TextAnalyzerQueryExtensionAnnotationInfo(Class<? extends Annotation> analyzerClass, Class<? extends Analyzer> analyzer) {
        super(analyzerClass);
        this.analyzer = analyzer;
    }

    public Class<? extends Analyzer> getAnalazerClass() {
        return analyzer;
    }

    public TextAnalyzerQueryExtensionAnnotationInfo setClazz(Class<? extends Analyzer> analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    @Override
    public boolean isIndexed() {
        return false;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        IOUtils.writeObject(out, analyzer);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        analyzer = IOUtils.readObject(in);
    }
}