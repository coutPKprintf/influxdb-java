package org.influxdb.dto;

/**
 * Created by cw on 16-1-8.
 */
public class HostAndPort {
    public static final String LOCALHOST_STR = "localhost";
    private String host;
    private int port;

    public HostAndPort(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof HostAndPort)) {
            return false;
        } else {
            HostAndPort hp = (HostAndPort) obj;
            String thisHost = this.convertHost(this.host);
            String hpHost = this.convertHost(hp.host);
            return this.port == hp.port && thisHost.equals(hpHost);
        }
    }

    public int hashCode() {
        return 31 * this.convertHost(this.host).hashCode() + this.port;
    }

    public String toString() {
        return this.host + ":" + this.port;
    }

    private String convertHost(String host) {
        return host.equals("127.0.0.1") ? "localhost" : (host.equals("::1") ? "localhost" : host);
    }
}