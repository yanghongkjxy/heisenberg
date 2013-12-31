/**
 * Baidu.com,Inc.
 * Copyright (c) 2000-2013 All Rights Reserved.
 */
package com.baidu.hsb;

import org.apache.log4j.Logger;

import com.baidu.hsb.config.model.config.CobarNodeConfig;
import com.baidu.hsb.heartbeat.CobarHeartbeat;

/**
 * @author xiongzhao@baidu.com
 */
public class CobarNode {
    private static final Logger LOGGER = Logger.getLogger(CobarNode.class);

    private final String name;
    private final CobarNodeConfig config;
    private final CobarHeartbeat heartbeat;

    public CobarNode(CobarNodeConfig config) {
        this.name = config.getName();
        this.config = config;
        this.heartbeat = new CobarHeartbeat(this);
    }

    public String getName() {
        return name;
    }

    public CobarNodeConfig getConfig() {
        return config;
    }

    public CobarHeartbeat getHeartbeat() {
        return heartbeat;
    }

    public void stopHeartbeat() {
        heartbeat.stop();
    }

    public void startHeartbeat() {
        heartbeat.start();
    }

    public void doHeartbeat() {
        if (!heartbeat.isStop()) {
            try {
                heartbeat.heartbeat();
            } catch (Throwable e) {
                LOGGER.error(name + " heartbeat error.", e);
            }
        }
    }

    public boolean isOnline() {
        return (heartbeat.getStatus() == CobarHeartbeat.OK_STATUS);
    }

}
