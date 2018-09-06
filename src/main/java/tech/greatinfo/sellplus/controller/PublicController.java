package tech.greatinfo.sellplus.controller;

import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import tech.greatinfo.sellplus.config.StaticConfig;
import tech.greatinfo.sellplus.domain.Activity;
import tech.greatinfo.sellplus.domain.Product;
import tech.greatinfo.sellplus.service.ActivityService;
import tech.greatinfo.sellplus.service.ArticleService;
import tech.greatinfo.sellplus.service.CompanyService;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.HelpService;
import tech.greatinfo.sellplus.service.MerchantService;
import tech.greatinfo.sellplus.service.ProductService;
import tech.greatinfo.sellplus.utils.DateUtil;
import tech.greatinfo.sellplus.utils.EncryptUtils;
import tech.greatinfo.sellplus.utils.ImageUtils;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 一些公共调用接口, 例如营销文章的获取
 *
 * Created by Ericwyn on 18-8-9.
 */
@RestController
public class PublicController {
    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    ArticleService articleService;

    @Autowired
    CompanyService companyService;

    @Autowired
    ProductService productService;

    @Autowired
    ActivityService activityService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    HelpService helpService;

    @Autowired
    CustomService customService;

    /**
     * 营销文章获取接口
     * @param start
     * @param num
     * @return
     */
    @RequestMapping(value = "/api/pub/listArticle", method = RequestMethod.POST)
    public ResJson listArticle(@RequestParam(value = "start",defaultValue = "0") Integer start,
                               @RequestParam(value = "num", defaultValue = "10") Integer num){
        try {
            return ResJson.successJson("list Article success", articleService.findByPage(start, num));
        }catch (Exception e){
            logger.error("/api/pub/listArticle -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 获取公司首页信息
     * @return
     */
    @RequestMapping(value = "/api/pub/getMainInfo",method = RequestMethod.POST)
    public ResJson setMainInfo(){

        try {
            HashMap<String, Object> resMap = new HashMap<>();
            List<String> bannerList = new ArrayList<>();
            String vTemp;
            for (int i=1;i<=3;i++){
                if ((vTemp = companyService.findByKey("banner"+i).getV())!=null && !vTemp.equals("null")){
                    bannerList.add(companyService.findByKey("banner"+i).getV());
                }
            }
            resMap.put("banners",bannerList);

            List<String> notifyList = new ArrayList<>();
            for (int i=1;i<=3;i++){
                if ((vTemp = companyService.findByKey("notify"+i).getV())!=null && !vTemp.equals("null")){
                    notifyList.add(companyService.findByKey("notify"+i).getV());
                }
            }
            resMap.put("notifys",notifyList);
            return ResJson.successJson("get company info success",resMap);
        }catch (Exception e){
            logger.error("/api/pub/listArticle -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 获取商品详情
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/pub/productInfo", method = RequestMethod.POST)
    public ResJson getProductInfo(@RequestBody JSONObject jsonObject){
        try {
            Long productId = (Long) ParamUtils.getFromJson(jsonObject,"productid", Long.class);
            Product product;
            if (( product = productService.findOne(productId)) != null){
                return ResJson.successJson("get product info success", product);
            }else {
                return ResJson.failJson(-1,"product id error",null);
            }
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/pub/getMainInfo");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/pub/getMainInfo");
        }catch (Exception e){
            logger.error("/api/pub/getMainInfo -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // 获取活动详情
    @RequestMapping(value = "/api/pub/activityInfo", method = RequestMethod.POST)
    public ResJson getActivityInfo(@RequestBody JSONObject jsonObject){
        try {
            Long activityId = (Long) ParamUtils.getFromJson(jsonObject,"activityid", Long.class);
            Activity activity;
            if (( activity = activityService.findOne(activityId)) != null){
                return ResJson.successJson("get activity info success", activity);
            }else {
                return ResJson.failJson(-1,"activity id error",null);
            }
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/pub/getMainInfo");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/pub/getMainInfo");
        }catch (Exception e){
            logger.error("/api/pub/getMainInfo -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // 获取商家信息
    @RequestMapping(value = "/api/pub/getMerInfo",method = RequestMethod.POST)
    public ResJson getMerInfo(){
        try {
            return ResJson.successJson("get merchant info success", merchantService.getMainMerchant());
        }catch (Exception e){
            logger.error("/api/pub/getMerInfo -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // 图片上传接口
    // TODO 上传图片鉴权, 简单的方法是使用 md5 ("spread"+时间戳前8位) 时间戳的话允许 1 分钟误差, 测试时候关闭
    @RequestMapping(value = "/api/pub/uploadPic",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson uploadPic(@RequestParam("file") MultipartFile file,
                             @RequestParam(value = "key",required = false) String key) {
        try {

            /*
            long now = System.currentTimeMillis();

            String keyPre = EncryptUtils.getMD5("spread"+(""+now/1000/100*100+60).substring(0,8));
            String keyNow = EncryptUtils.getMD5("spread"+(""+now).substring(0,8));
            String keyNext = EncryptUtils.getMD5("spread"+(""+now/1000/100*100+60).substring(0,8));
            if (!key.toLowerCase().equals(keyPre.toLowerCase())
                    && !key.toLowerCase().equals(keyNow.toLowerCase())
                    && !key.toLowerCase().equals(keyNext.toLowerCase())){
                return ResJson.failJson(6003,"key error",null);
            }
            **/

            HashMap<Object, Object> res = new HashMap<>();
            String dataFlag = DateUtil.formatDate(new Date(),"yyyy/MM/dd");
            File saveDir = new File(StaticConfig.SAVE_UPLOAD_PIC_PATH+"/"+dataFlag);
            if (!saveDir.isDirectory()) {
                saveDir.mkdirs();
            }
            if (file.isEmpty()) {
                return ResJson.failJson(6001,"file is empty",null);
            }

            byte[] bytes = file.getBytes();
            double file_Size = 1.0 * bytes.length / 1024 / 1024;
            if (file_Size > 2) {
                //单个文件的大小不许超过5
                return ResJson.failJson(6002,"file to big, max file size is 2MB",null);
            }

            // 保存图片
            String fileMd5Name = EncryptUtils.getMD5("just_for_encrypt" + System.currentTimeMillis() + Math.random() * 100);
            if (fileMd5Name == null) {
                fileMd5Name = "" + System.currentTimeMillis();
            }
            if (file.getOriginalFilename().toLowerCase().endsWith("jpg")) {
                fileMd5Name = fileMd5Name + ".jpg";
            } else if (file.getOriginalFilename().toLowerCase().endsWith("png")) {
                fileMd5Name = fileMd5Name + ".png";
            } else if (file.getOriginalFilename().toLowerCase().endsWith("gif")) {
                fileMd5Name = fileMd5Name + ".gif";
            } else {
                fileMd5Name = fileMd5Name + ".jpg";
            }
            InputStream inputStream = file.getInputStream();
            byte[] bytes_buff = new byte[1024];
            FileOutputStream outputStream = new FileOutputStream(new File(saveDir, fileMd5Name));
            while (inputStream.read(bytes_buff) != -1) {
                outputStream.write(bytes_buff);
                outputStream.flush();
            }
            inputStream.close();
            outputStream.close();
            res.put("url", StaticConfig.SAVE_UPLOAD_PIC_PATH+"/"+dataFlag+"/"+fileMd5Name);
            return ResJson.successJson("upload pic success",res);
        }catch (Exception e){
            logger.error("/api/pub/uploadPic -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // 上传 base64 图片接口
    @RequestMapping(value = "/api/pub/uploadBase64",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson uploadBase64(@RequestParam("img") String base64,
                                @RequestParam(value = "key",required = false) String key) {
        try {

            /*
            long now = System.currentTimeMillis();

            String keyPre = EncryptUtils.getMD5("spread"+(""+now/1000/100*100+60).substring(0,8));
            String keyNow = EncryptUtils.getMD5("spread"+(""+now).substring(0,8));
            String keyNext = EncryptUtils.getMD5("spread"+(""+now/1000/100*100+60).substring(0,8));
            if (!key.toLowerCase().equals(keyPre.toLowerCase())
                    && !key.toLowerCase().equals(keyNow.toLowerCase())
                    && !key.toLowerCase().equals(keyNext.toLowerCase())){
                return ResJson.failJson(6003,"key error",null);
            }
            **/

            HashMap<Object, Object> res = new HashMap<>();
            String dataFlag = DateUtil.formatDate(new Date(),"yyyy/MM/dd");
            File saveDir = new File(StaticConfig.SAVE_UPLOAD_PIC_PATH+"/"+dataFlag);
            if (!saveDir.isDirectory()) {
                saveDir.mkdirs();
            }

            byte[] bytes = base64.getBytes();
            double file_Size = 1.0 * bytes.length / 1024 / 1024;
            if (file_Size > 2) {
                //单个文件的大小不许超过5
                return ResJson.failJson(6002,"file to big, max file size is 2MB",null);
            }
            String fileMd5Name = EncryptUtils.getMD5("just_for_encrypt" + System.currentTimeMillis() + Math.random() * 100);
            if (fileMd5Name == null) {
                fileMd5Name = "" + System.currentTimeMillis();
            }

            String[] typeTemp = base64.split(";");
            String type = typeTemp[0];
            if (type.toLowerCase().endsWith("jpg")) {
                fileMd5Name = fileMd5Name + ".jpg";
            } else if (type.toLowerCase().endsWith("png")) {
                fileMd5Name = fileMd5Name + ".png";
            } else if (type.toLowerCase().endsWith("gif")) {
                fileMd5Name = fileMd5Name + ".gif";
            } else {
                fileMd5Name = fileMd5Name + ".jpg";
            }
            String outPutPath = new File(saveDir, fileMd5Name).getAbsolutePath();
            // 去除 base64 头部 "data:image/png;base64," 这一段信息
            if (ImageUtils.generateImage(typeTemp[1].replaceFirst("base64,",""),outPutPath)){
                res.put("url", StaticConfig.SAVE_UPLOAD_PIC_PATH+"/"+dataFlag+"/"+fileMd5Name);
                return ResJson.successJson("upload pic success",res);
            }else {
                return ResJson.failJson(7000,"base64 error",null);
            }
        }catch (Exception e){
            logger.error("/api/pub/uploadBase64 -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}