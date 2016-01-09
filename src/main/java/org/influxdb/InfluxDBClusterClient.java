package org.influxdb;

/**
 * this is the client for connecting to a cluster of InfluxDB servers.
 * Created by cw on 16-1-8.
 * @Author chenwen_201116040110 [at] gmail.com
 */
public interface InfluxDBClusterClient {

    /**
     * get a random cluster from servers
     * @return
     */
    InfluxDB getRandomInfluxDB();

    /**
     *
     * get a certain cluster by host , exp: 127.0.0.1:8086
     * @param host
     * @return
     */
    InfluxDB getInfluxDBByHost(String host);

    /**
     * get a last use influxdb cluster
     * @return
     */
    InfluxDB getLRUInfluxDB();
}
