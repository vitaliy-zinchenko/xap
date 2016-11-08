package com.gigaspaces.query.extension.metadata;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author Vitaliy_Zinchenko
 */
public class DefaultQueryExtensionPathActionInfo extends QueryExtensionActionInfo implements Externalizable {

    private static final long serialVersionUID = 1L;

    public DefaultQueryExtensionPathActionInfo() {
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }

    @Override
    public boolean isIndexed() {
        return true;
    }
}