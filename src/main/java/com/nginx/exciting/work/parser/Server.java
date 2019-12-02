package com.nginx.exciting.work.parser;

import java.util.StringJoiner;

public class Server {
    private String host;
    private String port;
    private boolean isCommented;

    public Server(String host, String port) {
        this.host = host;
        this.port = port;
        this.isCommented = false;
    }

    public Server(String host, String port, boolean isCommented) {
        this.host = host;
        this.port = port;
        this.isCommented = isCommented;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public boolean isCommented() {
        return isCommented;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Server.class.getSimpleName() + "[", "]")
                .add("host='" + host + "'")
                .add("port='" + port + "'")
                .add("isCommented=" + isCommented)
                .toString();
    }
}
