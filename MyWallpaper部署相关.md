需要先部署的服务：Redis,MySQL,Minio,



MinIO社区版在2025-05-24T17-08-30Z这个版本之后，移除了控制台大部分管理功能，想要完整minio，请安装旧版本。

先在usr/local路径下创建minio-data文件夹以及config文件夹

-e "MINIO_ROOT_USER=xxx" \    设置minio的用户名
-e "MINIO_ROOT_PASSWORD=xxxxxxxx" \    设置minio的用户密码

```
mkdir -p /opt/minio/data
mkdir -p /opt/minio/config

docker run -d \
  --name minio \
  -p 9000:9000 \
  -p 9001:9001 \
  -e "MINIO_ROOT_USER=minio" \
  -e "MINIO_ROOT_PASSWORD=minio123" \
  -v /usr/local/minio-data:/data \
  -v /usr/local/minio-config:/root/.minio \
  --restart unless-stopped \
  minio/minio:RELEASE.2025-04-22T22-12-26Z server /data --console-address ":9001"
```





nginx配置

```

#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;
    client_max_body_size 30M;

    # 禁用 keepalive 超时（避免连接过早关闭）
    keepalive_timeout 0;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    #keepalive_timeout  65;

    #gzip  on;

    server {
        listen       8089;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
            root   dist;
            index  index.html index.htm;
	    	try_files $uri $uri/ /index.html;
        }
	
	location /wallpaper {
		proxy_pass http://59.153.164.121:8088; #代理到后端服务器的地址
	    # ...其他配置
    	proxy_set_header Host $http_host; # 超级重要
    	proxy_connect_timeout 600;     # 连接超时
    	proxy_send_timeout 600;        # 发送超时
    	proxy_read_timeout 600;        # 读取超时
    	send_timeout 600;              # 客户端响应超时
	}

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }


    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}

}

```

