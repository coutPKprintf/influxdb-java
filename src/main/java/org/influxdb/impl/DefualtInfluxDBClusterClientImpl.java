package org.influxdb.impl;

import org.influxdb.ClusterConfig;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBClusterClient;
import org.influxdb.InfluxDBFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * the default client
 * this is the client for connecting to a cluster of InfluxDB servers.
 * Created by cw on 16-1-8.
 * @Author chenwen_201116040110 [at] gmail.com
 */
public class DefualtInfluxDBClusterClientImpl implements InfluxDBClusterClient {
    private AtomicInteger account = new AtomicInteger(0);
    private List<InfluxDB> influxDBList = new ArrayList<>();
    private Map<String,InfluxDB> influxDBMap = new HashMap<>();

    public DefualtInfluxDBClusterClientImpl(final ClusterConfig clusterConfig){
        init(clusterConfig);
    }

    private void init(ClusterConfig clusterConfig){
        for(String url:clusterConfig.getUrlList()){
            InfluxDB influxDB = InfluxDBFactory.connect(url,clusterConfig.getUsername(),clusterConfig.getPassword());
            influxDB.enableBatch(clusterConfig.getActions(), clusterConfig.getFlushDuration(), clusterConfig.getFlushDurationTimeUnit());
            influxDBList.add(influxDB);
            Pattern pattern = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d{1,5})");
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()){
                influxDBMap.put(matcher.group(0),influxDB);
            }
        }
    }
    @Override
    public InfluxDB getRandomInfluxDB() {
        return influxDBList.get(new Random().nextInt(influxDBList.size()));
    }

    @Override
    public InfluxDB getInfluxDBByHost(String host) {
        if (!influxDBMap.containsKey(host)){
            throw new IllegalArgumentException(String.format("can not find client that host is %s",host));
        }
        return influxDBMap.get(host);
    }

    @Override
    public InfluxDB getLRUInfluxDB() {
        if (account.get() >= Integer.MAX_VALUE - 10000){
            account.getAndSet(0);
        }
        return influxDBList.get(account.getAndIncrement()%influxDBList.size());
    }

    public static void main(String[] args){
        System.out.println(new Random().nextInt(2));
    }
}
