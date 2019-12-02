package com.nginx.exciting.work.parser;

import java.util.Map;

public class NgixParserRunner {

    public static void main(String args[]) {
        String config = "user       www www;  ## Default: nobody\n" +
                "worker_processes  5;  ## Default: 1\n" +
                "error_log  logs/error.log;\n" +
                "pid        logs/nginx.pid;\n" +
                "worker_rlimit_nofile 8192;\n" +
                "\n" +
                "events {\n" +
                "  worker_connections  4096;  ## Default: 1024\n" +
                "}\n" +
                "\n" +
                "http {\n" +
                "  include    conf/mime.types;\n" +
                "  include    /etc/nginx/proxy.conf;\n" +
                "  include    /etc/nginx/fastcgi.conf;\n" +
                "  index    index.html index.htm index.php;\n" +
                "\n" +
                "  default_type application/octet-stream;\n" +
                "  log_format   main '$remote_addr - $remote_user [$time_local]  $status '\n" +
                "    '\"$request\" $body_bytes_sent \"$http_referer\" '\n" +
                "    '\"$http_user_agent\" \"$http_x_forwarded_for\"';\n" +
                "  access_log   logs/access.log  main;\n" +
                "  sendfile     on;\n" +
                "  tcp_nopush   on;\n" +
                "  server_names_hash_bucket_size 128; # this seems to be required for some vhosts\n" +
                "\n" +
                "  server { # php/fastcgi\n" +
                "    listen       80;\n" +
                "    server_name  domain1.com www.domain1.com;\n" +
                "    access_log   logs/domain1.access.log  main;\n" +
                "    root         html;\n" +
                "\n" +
                "    location ~ \\.php$ {\n" +
                "      fastcgi_pass   127.0.0.1:1025;\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "  server { # simple reverse-proxy\n" +
                "    listen       80;\n" +
                "    server_name  domain2.com www.domain2.com;\n" +
                "    access_log   logs/domain2.access.log  main;\n" +
                "\n" +
                "    # serve static files\n" +
                "    location ~ ^/(images|javascript|js|css|flash|media|static)/  {\n" +
                "      root    /var/www/virtual/big.server.com/htdocs;\n" +
                "      expires 30d;\n" +
                "    }\n" +
                "\n" +
                "    # pass requests for dynamic content to rails/turbogears/zope, et al\n" +
                "    location / {\n" +
                "      proxy_pass      http://127.0.0.1:8080;\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "  upstream big_server_com1 {\n" +
                "    server 127.0.0.3:8000 weight=5;\n" +
                "    server 127.0.0.3:8001 weight=5;\n" +
                "    server 192.168.0.1:8000;\n" +
                "    server 192.168.0.1:8001;\n" +
                "  }\n" +
                "\n" +
                "  upstream big_server_com2 {\n" +
                "    server 127.0.0.3:8000 weight=5;\n" +
                "    server 127.0.0.3:8001 weight=5;\n" +
                "    server 192.168.0.1:8000;\n" +
                "    server 192.168.0.1:8001;\n" +
                "  }\n" +
                "\n" +
                "  server { # simple load balancing\n" +
                "    listen          80;\n" +
                "    server_name     big.server.com;\n" +
                "    access_log      logs/big.server.access.log main;\n" +
                "\n" +
                "    location / {\n" +
                "      proxy_pass      http://big_server_com;\n" +
                "    }\n" +
                "  }\n" +
                "}";
        NgixParser ngixParser = new NgixParser();

        Map<String, Service> serviceMap = ngixParser.getServiceMap(config);

        for (Service service : serviceMap.values()) {
            System.out.println(service);
        }
    }
}