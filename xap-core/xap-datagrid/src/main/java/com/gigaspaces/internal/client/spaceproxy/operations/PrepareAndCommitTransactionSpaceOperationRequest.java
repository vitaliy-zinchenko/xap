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

package com.gigaspaces.internal.client.spaceproxy.operations;

import com.gigaspaces.internal.io.IOUtils;
import com.gigaspaces.internal.remoting.routing.partitioned.PartitionedClusterExecutionType;
import com.gigaspaces.internal.remoting.routing.partitioned.PartitionedClusterRemoteOperationRouter;
import com.gigaspaces.internal.server.space.operations.SpaceOperationsCodes;
import com.gigaspaces.internal.utils.Textualizer;
import com.j_spaces.core.OperationID;
import com.j_spaces.core.client.IProxySecurityManager;

import net.jini.core.transaction.server.TransactionManager;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @author eitany
 * @since 9.0
 */
@com.gigaspaces.api.InternalApi
public class PrepareAndCommitTransactionSpaceOperationRequest extends SpaceOperationRequest<PrepareAndCommitTransactionSpaceOperationResult> {
    private static final long serialVersionUID = 1L;
    private TransactionManager _mgr;
    private long _id;
    private Object _xid;
    private OperationID _operationID;
    private transient int _partitionId;

    public PrepareAndCommitTransactionSpaceOperationRequest() {
    }

    public PrepareAndCommitTransactionSpaceOperationRequest(
            TransactionManager mgr, long id, int partitionId,
            OperationID operationID) {
        _mgr = mgr;
        _id = id;
        _partitionId = partitionId;
        _operationID = operationID;
    }

    public PrepareAndCommitTransactionSpaceOperationRequest(
            TransactionManager mgr, Object xid, int partitionId,
            OperationID operationID) {
        _mgr = mgr;
        _xid = xid;
        _partitionId = partitionId;
        _operationID = operationID;
    }

    public long getId() {
        return _id;
    }

    public Object getXid() {
        return _xid;
    }

    public TransactionManager getMgr() {
        return _mgr;
    }

    public OperationID getOperationID() {
        return _operationID;
    }

    @Override
    public int getOperationCode() {
        return SpaceOperationsCodes.PREPARE_AND_COMMIT_TRANSACTION;
    }

    @Override
    public PrepareAndCommitTransactionSpaceOperationResult createRemoteOperationResult() {
        return new PrepareAndCommitTransactionSpaceOperationResult();
    }

    @Override
    public PartitionedClusterExecutionType getPartitionedClusterExecutionType() {
        return PartitionedClusterExecutionType.SINGLE;
    }

    @Override
    public Object getPartitionedClusterRoutingValue(PartitionedClusterRemoteOperationRouter router) {
        return _partitionId;
    }

    @Override
    public void toText(Textualizer textualizer) {
        super.toText(textualizer);

        if (_xid != null)
            textualizer.append("xid", _xid);
        else
            textualizer.append("id", _id);
        textualizer.append("operationID", _operationID);
        textualizer.append("partitionId", _partitionId);
        textualizer.append("manager", _mgr);
    }

    @Override
    public String getLRMIMethodTrackingId() {
        return "prepareAndCommit";
    }

    private static final short FLAG_XID = 1 << 0;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);

        final short flags = buildFlags();
        out.writeShort(flags);
        IOUtils.writeObject(out, _mgr);
        IOUtils.writeObject(out, _operationID);
        if (_xid != null)
            IOUtils.writeObject(out, _xid);
        else
            out.writeLong(_id);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
        super.readExternal(in);

        final short flags = in.readShort();
        _mgr = IOUtils.readObject(in);
        _operationID = IOUtils.readObject(in);
        if ((flags & FLAG_XID) != 0)
            _xid = IOUtils.readObject(in);
        else
            _id = in.readLong();
    }

    private short buildFlags() {
        short flags = 0;

        if (_xid != null)
            flags |= FLAG_XID;

        return flags;
    }

    @Override
    public boolean supportsSecurity() {
        return IProxySecurityManager.SUPPORT_TRANSACTION_AUTHENTICATION;
    }
}
