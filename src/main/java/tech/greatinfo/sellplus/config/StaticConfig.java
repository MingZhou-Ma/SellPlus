package tech.greatinfo.sellplus.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;

/**
 * Created by Ericwyn on 18-7-31.
 */

@Configuration
public class StaticConfig extends WebMvcConfigurerAdapter {
    public static final String STATIC_PATH ="static/";
    public static final String SAVE_QRCODE_PATH = STATIC_PATH+"QRcode";
    public static final String SAVE_UPLOAD_PIC_PATH = STATIC_PATH+"upload";


    //设置上传文件最大大小
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置文件大小限制 ,超出设置页面会抛出异常信息，
        // 这样在文件上传的地方就需要进行异常信息的处理了;
        factory.setMaxFileSize("3MB"); // KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("5MB");
        // Sets the directory location where files will be stored.
        // factory.setLocation("路径地址");
        return factory.createMultipartConfig();
    }

    //设置静态资源映射，该目录专门用以文件下载
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("file:"+ STATIC_PATH);
        super.addResourceHandlers(registry);
    }
}
