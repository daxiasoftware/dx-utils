package com.daxiasoftware.utils;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.daxiasoftware.utils.dto.GpsDTO;
import com.daxiasoftware.utils.exception.BaiduMapException;

public class BaiduMapUtils {

    public static String getAddressByGps(String ak, double latitude, double longitude) throws BaiduMapException {
        JSONObject json = getStructuredAddressByGps(ak, latitude, longitude);
        return json.getJSONObject("result").getString("formatted_address");
    }
    
    public static JSONObject getStructuredAddressByGps(String ak, double latitude, double longitude) throws BaiduMapException {
        String url = "http://api.map.baidu.com/reverse_geocoding/v3/?ak={ak}&output=json&coordtype=wgs84ll&location={lat},{lng}";
        url = url.replace("{ak}", ak);
        url = url.replace("{lat}", latitude + "");
        url = url.replace("{lng}", longitude + "");
        String result = HttpUtils.get(url);
        JSONObject json = JSONObject.parseObject(result);
        if (json.getInteger("status") == 0) {
            return json;
        } else {
            throw new BaiduMapException(json.getIntValue("status"), json.getString("message"));
        }
    }
    
    public static GpsDTO getGpsByAddress(String ak, String address) throws BaiduMapException {
        if (StringUtils.isBlank(address)) {
            return new GpsDTO(null, null);
        }
        String url = "http://api.map.baidu.com/geocoding/v3/?address={address}&output=json&ak={ak}";
        url = url.replace("{ak}", ak);
        url = url.replace("{address}", address);
        String result = HttpUtils.get(url);
        JSONObject json = JSONObject.parseObject(result);
        if (json.getInteger("status") == 0) {
            return new GpsDTO(json.getJSONObject("result").getJSONObject("location").getDouble("lat"), 
                    json.getJSONObject("result").getJSONObject("location").getDouble("lng"));
        } else {
            throw new BaiduMapException(json.getIntValue("status"), json.getString("message"));
        }
    }
}
