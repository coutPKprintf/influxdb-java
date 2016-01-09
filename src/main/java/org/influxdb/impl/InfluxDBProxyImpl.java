package org.influxdb.impl;

import org.influxdb.ClusterConfig;
import org.influxdb.InfluxDBClusterClient;
import org.influxdb.InfluxDBProxy;
import org.influxdb.dto.Point;

import java.util.List;

/**
 * Created by cw on 16-1-9.
 */
public class InfluxDBProxyImpl implements InfluxDBProxy{
    private InfluxDBClusterClient influxDBClusterClient;
    private ClusterConfig clusterConfig;

    public InfluxDBProxyImpl(ClusterConfig clusterConfig){
        this.clusterConfig = clusterConfig;
        influxDBClusterClient = new DefualtInfluxDBClusterClientImpl(clusterConfig);
    }
    @Override
    public void writeWithRandom(Point point) {
        influxDBClusterClient.getRandomInfluxDB().write(clusterConfig.getDatabase(),clusterConfig.getRetentionPolicy(),point);
    }

    @Override
    public void writeWithHost(Point point, String host) {
        influxDBClusterClient.getInfluxDBByHost(host).write(clusterConfig.getDatabase(), clusterConfig.getRetentionPolicy(), point);
    }

    @Override
    public void writeWithLRU(Point point) {
        influxDBClusterClient.getLRUInfluxDB().write(clusterConfig.getDatabase(), clusterConfig.getRetentionPolicy(), point);
    }

    @Override
    public void writeWithRandom(List<Point> pointList) {
        for(Point point:pointList){
            writeWithRandom(point);
        }
    }

    @Override
    public void writeWithHost(List<Point> pointList, String host) {
        for(Point point:pointList){
            writeWithHost(point,host);
        }
    }

    @Override
    public void writeWithLRU(List<Point> pointList) {
        for(Point point:pointList){
            writeWithLRU(point);
        }
    }
}
