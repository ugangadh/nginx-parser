package com.nginx.exciting.work.parser;

import java.util.List;
import java.util.StringJoiner;

public class Service {

    private final String name;
    private final List<Server> serverList;

    public Service(String name, List<Server> serverList) {
        if (name == null) {
            throw new IllegalArgumentException();
        }

        this.name = name;
        this.serverList = serverList;
    }

    public String getName() {
        return name;
    }

    public List<Server> getServerList() {
        return serverList;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Service.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("serverList=" + serverList)
                .toString();
    }
}
