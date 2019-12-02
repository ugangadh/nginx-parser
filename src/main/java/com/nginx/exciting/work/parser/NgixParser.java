package com.nginx.exciting.work.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class NgixParser {

    private static Pattern upstreamPattern = Pattern.compile("(^\\s+upstream\\s+([^\\s]+)\\s+\\{((\\s+server[^;]+;\\s+)+)}$)", Pattern.MULTILINE);
    private static Pattern serverPattern = Pattern.compile("\\s*(#)?.*server\\s+([^:]*):([^\\s;]*)[^;]*;");

    /**
     * @param config
     * @return
     */
    public Map<String, Service> getServiceMap(String config) {
        Map<String, Service> serviceMap = new HashMap<>();

        Matcher upstreamMatcher = upstreamPattern.matcher(config);

        while (upstreamMatcher.find()) {
            Service service = parseService(upstreamMatcher.group(2), upstreamMatcher.group());
            serviceMap.put(service.getName(), service);
        }

        return serviceMap;
    }

    /**
     * @param serviceName
     * @param upstreamBlock
     * @return
     */
    private Service parseService(String serviceName, String upstreamBlock) {
        List<Server> serverList = new ArrayList<>();

        Matcher serverMatcher = serverPattern.matcher(upstreamBlock);

        while (serverMatcher.find()) {
            serverList.add(new Server(serverMatcher.group(2), serverMatcher.group(3), serverMatcher.group(1) != null));
        }

        return new Service(serviceName, serverList);
    }
}