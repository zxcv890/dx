package com.MyNIO;

 import org.apache.http.*;
 import org.apache.http.impl.DefaultConnectionReuseStrategy;
 import org.apache.http.impl.nio.DefaultClientIOEventDispatch;
 import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
 import org.apache.http.message.BasicHttpRequest;
 import org.apache.http.nio.NHttpConnection;
 import org.apache.http.nio.protocol.BufferingHttpClientHandler;
 import org.apache.http.nio.protocol.HttpRequestExecutionHandler;
 import org.apache.http.nio.reactor.IOEventDispatch;
 import org.apache.http.nio.reactor.IOReactorExceptionHandler;
 import org.apache.http.nio.reactor.SessionRequest;
 import org.apache.http.nio.reactor.SessionRequestCallback;
 import org.apache.http.params.BasicHttpParams;
 import org.apache.http.params.CoreConnectionPNames;
 import org.apache.http.params.HttpParams;
 import org.apache.http.protocol.*;

 import java.io.IOException;
 import java.io.InterruptedIOException;
 import java.net.InetAddress;
 import java.net.InetSocketAddress;
 import java.net.SocketAddress;
 import java.net.URL;
 import java.util.EventListener;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.concurrent.locks.Condition;
 import java.util.concurrent.locks.Lock;
 import java.util.concurrent.locks.ReentrantLock;
 import java.util.logging.Logger;
 import java.util.zip.GZIPInputStream;
/**
 *
 * 作用: 支持异步读取的httpClient
 * 暂时不支持socks代理
 */
public class NHttpClient {
    private int timeOut = 10000; // 10秒
    private String localAddress = null;
    private SocketAddress localSocketAddress = null; //本地端口
    private boolean useProxy = false;
    private int maxConnection = 2;
    private Map defaultHeaders = new HashMap();
    private DefaultConnectingIOReactor ioReactor;
    private String host;
    private String proxyServerType = "http";
    private String directHost = "127.0.0.1,localhost";
    private String proxyServer;
    private int proxyPort;
    private String proxyUser;
    private String proxyPassword;
    private int connections = 0;
    private Lock lock = new ReentrantLock();
    private final Condition full = lock.newCondition();

    public void addConnection() throws Exception {
        lock.lock();
        try {
            if (connections > maxConnection) {
                full.await();
            }
            connections++;
        } finally {
            lock.unlock();
        }
    }
    public void removeConnection() {
        lock.lock();
        try {
            if (connections <= maxConnection) {
                full.signal();
            }
            connections--;
        } finally {
            lock.unlock();
        }
    }
    public boolean isRunning() {
        return connections > 0;
    }
    public int getConnections() {
        return connections;
    }
    public Map getDefaultHeaders() {
        return defaultHeaders;
    }
    public void setDefaultHeaders(Map defaultHeaders) {
        this.defaultHeaders = defaultHeaders;
    }
    public String getDirectHost() {
        return directHost;
    }
    public void setDirectHost(String directHost) {
        this.directHost = directHost;
    }
    public String getHost() {
        return host;
    }
    public void setHost(String host) {
        this.host = host;
    }
    public String getLocalAddress() {
        return localAddress;
    }
    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }
    public SocketAddress getLocalSocketAddress() {
        return localSocketAddress;
    }
    public void setLocalSocketAddress(SocketAddress localSocketAddress) {
        this.localSocketAddress = localSocketAddress;
    }
    public int getMaxConnection() {
        return maxConnection;
    }
    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }
    public String getProxyPassword() {
        return proxyPassword;
    }
    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }
    public int getProxyPort() {
        return proxyPort;
    }
    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }
    public String getProxyServer() {
        return proxyServer;
    }
    public void setProxyServer(String proxyServer) {
        this.proxyServer = proxyServer;
    }
    public String getProxyServerType() {
        return proxyServerType;
    }
    public void setProxyServerType(String proxyServerType) {
        this.proxyServerType = proxyServerType;
    }
    public String getProxyUser() {
        return proxyUser;
    }
    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }
    public int getTimeOut() {
        return timeOut;
    }
    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }
    public boolean isUseProxy() {
        return useProxy;
    }
    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }
    public void init() throws Exception {
        HttpParams params = new BasicHttpParams();
        params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5000).
                setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeOut).
                setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, 512 * 1024).
                setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);
        // setBooleanParameter(CoreConnectionPNames., true);
        if ( localAddress == null || localAddress.equals("") ) {
            localSocketAddress = InetSocketAddress.createUnresolved(localAddress, 0);
        }
        defaultHeaders.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1) Gecko/20090624 Firefox/3.5 GTB5");
        defaultHeaders.put("Accept-Language", "zh-cn,zh;q=0.5");
        defaultHeaders.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
        defaultHeaders.put("Accept", "*/*");
        /**
         * 设置几个固定的http 头
         */
        // defaultHeaders.put("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1) Gecko/20090624 Firefox/3.5 GTB5");
        // defaultHeaders.put("Accept-Language", "zh-cn,zh;q=0.5");
        // defaultHeaders.put("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");
        // defaultHeaders.put("Accept", "*/*");
        ioReactor = new DefaultConnectingIOReactor(2, params);
        BasicHttpProcessor httpproc = new BasicHttpProcessor();
        httpproc.addInterceptor(new RequestContent());
        httpproc.addInterceptor(new RequestTargetHost());
        httpproc.addInterceptor(new RequestConnControl());
        httpproc.addInterceptor(new RequestUserAgent());
        httpproc.addInterceptor(new RequestExpectContinue());
        BufferingHttpClientHandler handler = new BufferingHttpClientHandler(
                httpproc,
                new MyHttpRequestExecutionHandler(),
                new DefaultConnectionReuseStrategy(),
                params);
        handler.setEventListener((org.apache.http.nio.protocol.EventListener) new EventLogger());
        final IOEventDispatch ioEventDispatch = new DefaultClientIOEventDispatch(handler, params);
        ioReactor.setExceptionHandler(new IOReactorExceptionHandler() {
            public boolean handle(IOException e) {
                e.printStackTrace();
                return true;
            }
            public boolean handle(RuntimeException e) {
                e.printStackTrace();
                return true;
            }
        });
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    ioReactor.execute(ioEventDispatch);
                } catch (InterruptedIOException ex) {
                } catch (Exception e) {
                }
            }
        });
        t.start();
    }
    public void destroy() throws Exception {
        if (ioReactor != null) {
            ioReactor.shutdown();
        }
    }
    //减少dns查询
    private Map dns = new HashMap();
    public void getUrl(String url, NHttpClientCallback callback) throws Exception {
        addConnection();
        if (!url.startsWith("http://")) {
            url += "http://" + host;
        }
        URL u = new URL(url);
        int port = u.getPort() < 0 ? u.getDefaultPort() : u.getPort();
        String path = u.getPath();
        if (path == null || path.equals("")) {
            path = "/";
        }
        if (u.getQuery() != null) {
            path += "?" + u.getQuery();
        }
        if (dns.get(u.getHost()) == null) {
            InetAddress address = InetAddress.getByName(u.getHost());
            dns.put(u.getHost(), address);
        }
        InetAddress address = (InetAddress) dns.get(u.getHost());
        SessionRequest sessionRequest = null;
        InternalObject object = new InternalObject(path, callback);
        object.setUrl(url);
        if (!useProxy) {
            sessionRequest = ioReactor.connect(
                    new InetSocketAddress(address, port),
                    localSocketAddress, //localhost
                    object,//attachment
                    new MySessionRequestCallback());
        } else {
            //TODO
            SocketAddress addr = new InetSocketAddress(proxyServer, proxyPort);
            sessionRequest = ioReactor.connect(
                    addr,
                    localSocketAddress, //localhost
                    object,//attachment
                    new MySessionRequestCallback());
        }
 /* * */
        sessionRequest.waitFor();
        if (sessionRequest.getException() != null) {
            throw sessionRequest.getException();
        }
    }
    private class InternalObject {
        private NHttpClientCallback callback;
        private String uri;
        private String url;
        public InternalObject(String uri, NHttpClientCallback callback) {
            this.uri = uri;
            this.callback = callback;
        }
        public NHttpClientCallback getCallback() {
            return callback;
        }
        public void setCallback(NHttpClientCallback callback) {
            this.callback = callback;
        }
        public String getUri() {
            return uri;
        }
        public void setUri(String uri) {
            this.uri = uri;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
    }
    private class MySessionRequestCallback implements SessionRequestCallback {
        public MySessionRequestCallback() {
            super();
        }
        public void cancelled(final SessionRequest request) {
        }
        public void completed(final SessionRequest request) {
        }
        public void failed(final SessionRequest request) {
        }
        public void timeout(final SessionRequest request) {
        }
    }
    private class EventLogger implements EventListener {
        public void connectionOpen(final NHttpConnection conn) {
        }
        public void connectionTimeout(final NHttpConnection conn) {
        }
        public void connectionClosed(final NHttpConnection conn) {
        }
        public void fatalIOException(final IOException ex, final NHttpConnection conn) {
        }
        public void fatalProtocolException(final HttpException ex, final NHttpConnection conn) {
        }
    }
    private class MyHttpRequestExecutionHandler implements HttpRequestExecutionHandler {
        private final static String REQUEST_SENT = "request-sent";
        private final static String RESPONSE_RECEIVED = "response-received";
        public MyHttpRequestExecutionHandler() {
            super();
        }
        public void initalizeContext(final HttpContext context, final Object attachment) {
            InternalObject internalObject = (InternalObject) attachment;
            context.setAttribute("internalObject", internalObject);
        }
        public void finalizeContext(final HttpContext context) {
            Object flag = context.getAttribute(RESPONSE_RECEIVED);
            if (flag == null) {
                // Signal completion of the request execution
            }
        }
        public HttpRequest submitRequest(final HttpContext context) {
            InternalObject internalObject = (InternalObject) context.getAttribute("internalObject");
            Object flag = context.getAttribute(REQUEST_SENT);
            if (flag == null) {
                try {
                    // Stick some object into the context
                    context.setAttribute(REQUEST_SENT, Boolean.TRUE);
                    System.out.println("Sending request to " + internalObject.getUrl());
                    BasicHttpRequest httpRequest = new BasicHttpRequest("GET", internalObject.getUri());
                    //FIXMED me
                    // httpRequest.addHeader("Accept-Encoding", "gzip,deflate");
                    Iterator iteratorDefault = defaultHeaders.keySet().iterator();
                    while (iteratorDefault.hasNext()) {
                        String key = (String) iteratorDefault.next();
                        httpRequest.setHeader(key, (String) defaultHeaders.get(key));
                    }
                    return httpRequest;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            } else {
                // No new request to submit
                return null;
            }
        }
        public void handleResponse(final HttpResponse response, final HttpContext context) {
            InternalObject internalObject = (InternalObject) context.getAttribute("internalObject");
            HttpEntity entity = response.getEntity();
            String content = "";
            try {
                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new IOException("invalid response code=" + response.getStatusLine().getStatusCode() + ",url=" + internalObject.getUrl());
                }
                Header[] headers = response.getAllHeaders();
                for (Header header : headers) {
                }
                if (entity.getContentEncoding() != null && "gzip".equals(entity.getContentEncoding().getValue())) {
                    //是压缩的流
                    GZIPInputStream inStream = new GZIPInputStream(entity.getContent());
                    content = IOUtils.toString(inStream);
                } else {
                    content = IOUtils.toString(entity.getContent(), "GBK");
                    // content = EntityUtils.toString(entity, "GBK");
                }
                System.out.println("-----------------------");
                System.out.println("response " + response.getStatusLine() + " of url=" + internalObject.getUrl() + ",content=" + content.length());
                System.out.println("content=" + content.indexOf("page-info"));
                System.out.println("-----------------------");
                //System.out.println("content="+content);
                internalObject.getCallback().finished(content);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                removeConnection();
            }
            context.setAttribute(RESPONSE_RECEIVED, Boolean.TRUE);
        }
    }
    /**
     *
     * 作用:
     */
    public interface NHttpClientCallback {
        public void finished(String content);
    }
}