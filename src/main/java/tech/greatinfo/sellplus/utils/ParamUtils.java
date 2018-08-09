package tech.greatinfo.sellplus.utils;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Type;

import tech.greatinfo.sellplus.utils.exception.JsonParseException;

/**
 *
 * 针对 JSON POST 数据的解析工具类
 * 判断非空, 并且获取常用的数据类型 INT LONG DOUBLE STRING
 *
 * 不满足的话直接抛出一个 JsonParseExpection
 *
 * Service 层通过一个 catch 来获取并且返回固定的错误码给前端
 *
 * Created by Ericwyn on 18-8-9.
 */
public class ParamUtils {

    /**
     *
     * 获取必须参数
     *
     * @param jsonObject
     * @param key
     * @param type
     * @return
     * @throws JsonParseException
     */
    public static Object getFromJson(JSONObject jsonObject, String key, Type type) throws JsonParseException {
        try {
            if (jsonObject.get(key) == null){
                throw new JsonParseException("can not find the key '"+key+"' in JSON Object");
            }
            return jsonObject.getObject(key,type);
        }catch (Exception e){
            throw new JsonParseException("can not parse key '"+key+"' in JSON Object --> Expection : "+e.getMessage());
        }
    }

//    public static Object getFromJson(JSONObject jsonObject, String key, ParseType type) throws JsonParseException {
//        try {
//            if (jsonObject.get(key) == null){
//                throw new JsonParseException("can not find the key '"+key+"' in JSON Object");
//            }
//            switch (type){
//                case INT:
//                    return jsonObject.getInteger(key);
//                case LONG:
//                    return jsonObject.getLong(key);
//                case DOUBLE:
//                    return jsonObject.getDouble(key);
//                case STRING:
//                    return jsonObject.getString(key);
//                case Boolean:
//                    return jsonObject.getBoolean(key);
//            }
//            throw new JsonParseException("can not parse key '"+key+"' in JSON Object");
//        }catch (Exception e){
//            throw new JsonParseException("can not parse key '"+key+"' in JSON Object --> Expection : "+e.getMessage());
//        }
//    }
}