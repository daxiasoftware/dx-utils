package com.daxiasoftware.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	public static String get(String url) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));

			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (IOException e) {
		    e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

	}

	public static String postWithParameters(String url, Map<String, String> paras) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);

			if (paras != null) {
				List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
				for (String key : paras.keySet()) {
					urlParameters.add(new BasicNameValuePair(key, paras.get(key)));
				}
				post.setEntity(new UrlEncodedFormEntity(urlParameters));
			}
			HttpResponse response = client.execute(post);
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
			
			StringBuilder result = new StringBuilder();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static String postWithJsonRequestBody(String url, String requestBody) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);

			if (StringUtils.isNotBlank(requestBody)) {
				post.setEntity(new StringEntity(requestBody, "utf-8"));
			}
			post.setHeader("Content-type", "application/json");

			HttpResponse response = client.execute(post);
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
			
			StringBuilder result = new StringBuilder();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (IOException e) {
		    throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static String postWithJsonRequestBody(String url, final Map<String, String> headers, String requestBody) {
        try {
            HttpClient client = HttpClientBuilder.create().build();
            final HttpPost post = new HttpPost(url);

            if (StringUtils.isNotBlank(requestBody)) {
                post.setEntity(new StringEntity(requestBody, "utf-8"));
            }
            post.setHeader("Content-type", "application/json");
            if (headers != null) {
                headers.keySet().forEach(key -> {
                    post.setHeader(key, headers.get(key));
                });
            }

            HttpResponse response = client.execute(post);
            
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            
            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
	
	public static void main(String[] args) throws Exception {
		String result = postWithJsonRequestBody("http://www.baidu.com", null);
		System.out.println(result);
	}
	
	/**
     * 发送post请求
     * 
     * @param url
     * @param header
     * @param body
     * @return
     */
    public static String doPost(String url, Map<String, String> header, String body) {
        String result = "";
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            // 设置 url
            URL realUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)realUrl.openConnection();
            // 设置 header
            for (String key : header.keySet()) {
                connection.setRequestProperty(key, header.get(key));
            }
            // 设置请求 body
            connection.setDoOutput(true);
            connection.setDoInput(true);
            
            //设置连接超时和读取超时时间
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            try {
                out = new PrintWriter(connection.getOutputStream());
                // 保存body
                out.print(body);
                // 发送body
                out.flush();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            
            try {
                // 获取响应body
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
//          return null;
        }
        return result;
    }

    /**
     * 发送get请求
     * 
     * @param url
     * @param header
     * @return
     */
    public static String doGet(String url, Map<String, String> header) {
        String result = "";
        BufferedReader in = null;
        try {
            // 设置 url
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            // 设置 header
            for (String key : header.keySet()) {
                connection.setRequestProperty(key, header.get(key));
            }
            // 设置请求 body
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    
    public static List<NameValuePair> convertMapToPair(Map<String, String> params) {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return pairs;
    }

    public static String post(String url, Map<String, String> param) {
        String result = null;
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(convertMapToPair(param), "utf-8"));
            httpResponse = httpClient.execute(httpPost);
            result = EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        
        return result;
    }

    public static String postMulti(String url, Map<String, String> param, byte[] body) {
        String result = null;
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
        reqEntity.addPart("content", new ByteArrayBody(body, ContentType.DEFAULT_BINARY, param.get("slice_id")));

        for (Map.Entry<String, String> entry : param.entrySet()) {
            StringBody value = new StringBody(entry.getValue(), ContentType.create("text/plain", Consts.UTF_8));
            reqEntity.addPart(entry.getKey(), value);
        }
        HttpEntity httpEntiy = reqEntity.build();

        try {
            httpPost.setEntity(httpEntiy);
            httpResponse = httpClient.execute(httpPost);
            result = EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }
}
