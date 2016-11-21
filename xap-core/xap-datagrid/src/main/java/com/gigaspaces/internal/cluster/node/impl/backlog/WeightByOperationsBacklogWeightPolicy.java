package com.gigaspaces.internal.cluster.node.impl.backlog;

import com.gigaspaces.internal.cluster.node.impl.packets.data.IReplicationPacketData;

/**
 * @author yael nahon
 * @since 12.1
 */
public class WeightByOperationsBacklogWeightPolicy implements BacklogWeightPolicy {

    private int defaultPacketWeight = 1;

    @Override
    public int calculateWeight(IReplicationPacketData<?> data) {
        return data.size();
    }

    @Override
    public int predictWeightBeforeOperation(OperationWeightInfo info) {
        if(info.getNumOfOperations() == -1){
            return defaultPacketWeight;
        }
        return info.getNumOfOperations();
    }

    public int getDefaultPacketWeight() {
        return defaultPacketWeight;
    }
}
