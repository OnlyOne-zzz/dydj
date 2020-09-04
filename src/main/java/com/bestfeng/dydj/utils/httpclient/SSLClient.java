package com.bestfeng.dydj.utils.httpclient;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

//用于进行Https请求的HttpClient  
@SuppressWarnings("deprecation")
public class SSLClient {
	private static final String CHARSET = "UTF-8";
	private volatile static HttpClient sslClient;

	// 实现单列模式
	private SSLClient() {

	}

	public static HttpClient getInstance() throws NoSuchAlgorithmException, KeyManagementException {
		if (null == sslClient) {
			synchronized (SSLClient.class) {
				if (null == sslClient) {
					HttpParams params = new BasicHttpParams();
					// 设置一些基本参数
					HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
					HttpProtocolParams.setContentCharset(params, CHARSET);
					HttpProtocolParams.setUseExpectContinue(params, true);
					HttpProtocolParams.setUserAgent(params,
							"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) "
									+ "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
					// 超时设置
					/* 从连接池中取连接的超时时间 */
                    ConnManagerParams.setTimeout(params, 5000);
                    /* 设置连接池大小  200 */
                    ConnManagerParams.setMaxTotalConnections(params, 200);


                    /*比较特别的是 每个路由(route)最大连接数。什么是一个route？
                     * 这里route的概念可以理解为运行环境机器到目标机器的一条线路。
                     * 举例来说，我们使用HttpClient的实现来分别请求 www.baidu.com 的资源和 www.bing.com
                     * 的资源那么他就会产生两个route。
                     * 这里为什么要特别提到route最大连接数这个参数呢，因为这个参数的默认值为2，
                     * 如果不设置这个参数值默认情况下对于同一个目标机器的最大并发连接只有2个！
                     * 这意味着如果你正在执行一个针对某一台目标机器的抓取任务的时候，
                     * 哪怕你设置连接池的最大连接数为200，但是实际上还是只有2个连接在工作，
                     * 其他剩余的198个连接都在等待，都是为别的目标机器服务的。
                     */
                    /*设置最大路由*/
                    ConnPerRouteBean connPerRoute = new ConnPerRouteBean(100);
                    ConnManagerParams.setMaxConnectionsPerRoute(params, connPerRoute);

					/* 连接超时 */
					HttpConnectionParams.setConnectionTimeout(params, 10000);
					/* 请求超时 */
					HttpConnectionParams.setSoTimeout(params, 30000);

					SSLContext ctx = SSLContext.getInstance("TLS");
					X509TrustManager tm = new X509TrustManager() {
						@Override
						public void checkClientTrusted(X509Certificate[] chain, String authType)
								throws CertificateException {
						}

						@Override
						public void checkServerTrusted(X509Certificate[] chain, String authType)
								throws CertificateException {
						}

						@Override
						public X509Certificate[] getAcceptedIssuers() {
							return null;
						}
					};
					ctx.init(null, new TrustManager[] { tm }, null);
					SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
					SchemeRegistry sr = new SchemeRegistry();
					sr.register(new Scheme("https", 443, ssf));
					sr.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80)); 
					ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, sr);
					sslClient = new DefaultHttpClient(conMgr, params);
				}
			}
		}
		return sslClient;
	}
}
