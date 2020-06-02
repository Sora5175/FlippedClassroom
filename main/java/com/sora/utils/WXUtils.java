package com.sora.utils;

import com.alibaba.fastjson.JSONObject;
import com.sora.vo.WeiXinLoginMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/3/30 16:23
 */
public class WXUtils {
    private static Properties prop = new Properties();
    static {
        try {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("weixin.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WeiXinLoginMessage jscode2session(String code) throws Exception{
        System.out.println(code);
        WeiXinLoginMessage weiXinLoginMessage = new WeiXinLoginMessage();
        String authUrl = prop.getProperty("weixin.authUrl");
        String appId = prop.getProperty("weixin.AppID");
        String appSecret = prop.getProperty("weixin.AppSecret");
        try{
            URL url = new URL(authUrl+"?appid="+appId+
                    "&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            //设置连接超时时间和读取超时时间
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            connection.setRequestProperty("Accept", "application/json");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String str;
            StringBuffer buffer = new StringBuffer();
            StringBuilder stringBuilder = new StringBuilder();
            while ((str = bufferedReader.readLine()) != null) {
                stringBuilder.append(str);
            }
            bufferedReader.close();
            connection.disconnect();
            //获取JSON对象并转换为Java对象
            str = stringBuilder.toString();
            System.out.println(str);
            weiXinLoginMessage = JSONObject.parseObject(str, WeiXinLoginMessage.class);
        }catch (Exception e){
            throw new Exception("微信认证失败，请稍后重试");
        }
        return weiXinLoginMessage;
    }


}