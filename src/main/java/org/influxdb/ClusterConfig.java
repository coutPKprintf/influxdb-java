package org.influxdb;

import org.influxdb.dto.HostAndPort;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cw on 16-1-8.
 * @Author chenwen_201116040110 [at] gmail.com
 */
public class ClusterConfig {
    /**
     * up to actions start write
     */
    private int actions = 500;

    /**
     * write by every flushDurationTimeUnit
     */
    private int flushDuration = 60;

    /**
     * time unit
     */
    private TimeUnit flushDurationTimeUnit = TimeUnit.SECONDS;

    /**
     * all clients
     */
    private Set<HostAndPort> hostAndPortList;

    /**
     * parse hostAndPortList to urlList
     */
    private Set<String> urlList;

    /**
     * username
     */
    private String username;

    /**
     * password
     */
    private String password;

    /**
     * the database to write to.
     */
    private String database;

    /**
     * the retentionPolicy to use.
     */
    private String retentionPolicy;

    public ClusterConfig(Set<HostAndPort> hostAndPortList,String database,String retentionPolicy,String username,String password){
        this.hostAndPortList = hostAndPortList;
        this.database = database;
        this.retentionPolicy = retentionPolicy;
        this.username = username;
        this.password = password;
    }

    /**
     * hostAndPortList , exp : 127.0.0.1:8086,127.0.0.1:8087
     * @param hostAndPortList
     * @param username
     * @param password
     */
    public ClusterConfig(String hostAndPortList,String database,String retentionPolicy,String username,String password){
        this.hostAndPortList = parseNodes(hostAndPortList);
        this.database = database;
        this.retentionPolicy = retentionPolicy;
        this.username = username;
        this.password = password;
    }

    public void setActions(int actions) {
        this.actions = actions;
    }

    public int getActions() {
        return actions;
    }

    public int getFlushDuration() {
        return flushDuration;
    }

    public void setFlushDuration(int flushDuration) {
        this.flushDuration = flushDuration;
    }

    public TimeUnit getFlushDurationTimeUnit() {
        return flushDurationTimeUnit;
    }

    public void setFlushDurationTimeUnit(TimeUnit flushDurationTimeUnit) {
        this.flushDurationTimeUnit = flushDurationTimeUnit;
    }

    public Set<HostAndPort> getHostAndPortList() {
        return hostAndPortList;
    }

    public void setHostAndPortList(Set<HostAndPort> hostAndPortList) {
        this.hostAndPortList = hostAndPortList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getUrlList() {
        return urlList;
    }

    public String getRetentionPolicy() {
        return retentionPolicy;
    }

    public void setRetentionPolicy(String retentionPolicy) {
        this.retentionPolicy = retentionPolicy;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    private Set<HostAndPort> parseNodes(final String nodes){
        Pattern pattern = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d{1,5})");
        Matcher matcher = pattern.matcher(nodes);
        Set<HostAndPort> hostAndPortSet = new HashSet<>();
        while (matcher.find()){
            hostAndPortSet.add(new HostAndPort(matcher.group(1),Integer.parseInt(matcher.group(2))));
        }
        if (hostAndPortSet.size() == 0){
            throw new IllegalArgumentException("parse host and port failed");
        }
        urlList = new HashSet<>();
        for(HostAndPort hostAndPort:hostAndPortSet){
            urlList.add(String.format("http://%s:%s",hostAndPort.getHost(),hostAndPort.getPort()));
        }
        return hostAndPortSet;
    }
}
