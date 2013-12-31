/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package com.baidu.hsb.heartbeat;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import com.baidu.hsb.CobarServer;
import com.baidu.hsb.config.model.config.CobarNodeConfig;
import com.baidu.hsb.config.model.config.SystemConfig;
import com.baidu.hsb.net.factory.BackendConnectionFactory;

/**
 * @author xiongzhao@baidu.com
 */
public class CobarDetectorFactory extends BackendConnectionFactory {

    public CobarDetectorFactory() {
        this.idleTimeout = 120 * 1000L;
    }

    public CobarDetector make(CobarHeartbeat heartbeat) throws IOException {
        SocketChannel channel = openSocketChannel();
        CobarNodeConfig cnc = heartbeat.getNode().getConfig();
        SystemConfig sys = CobarServer.getInstance().getConfig().getSystem();
        CobarDetector detector = new CobarDetector(channel);
        detector.setHost(cnc.getHost());
        detector.setPort(cnc.getPort());
        detector.setUser(sys.getClusterHeartbeatUser());
        detector.setPassword(sys.getClusterHeartbeatPass());
        detector.setHeartbeatTimeout(sys.getClusterHeartbeatTimeout());
        detector.setHeartbeat(heartbeat);
        postConnect(detector, CobarServer.getInstance().getConnector());
        return detector;
    }

}
