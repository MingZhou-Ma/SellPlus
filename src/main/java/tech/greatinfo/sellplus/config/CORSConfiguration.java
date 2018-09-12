package tech.greatinfo.sellplus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * 允许跨域访问, 只在 debug 时候开启
 *
 * Created by Ericwyn on 18-8-7.
 */
@Configuration
public class CORSConfiguration {
	
	//private static final Logger logger = LoggerFactory.getLogger(CORSConfiguration.class);
	
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedHeaders("*")
                        .allowedMethods("*")
                        .allowedOrigins("*");
            }
        };
    }
    
    /**
	 * @Description: bulid cors config  
	 * @Linker https://blog.csdn.net/superpeepi_csdn/article/details/72625521 
	 * https://www.cnblogs.com/fuhongwei041/p/7590425.html
	 * @return CorsConfiguration
	 * @Autor: Jason
	 */
  /*  private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        //corsConfiguration.addAllowedOrigin("http://wanguo.com/");
        corsConfiguration.addAllowedOrigin(CorsConfiguration.ALL); // 1. 设置访问源地址 [支持All]
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL); // 2. 设置访问源请求头 [支持All]
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL); // 3. 设置访问源请求方法 [支持All]
        return corsConfiguration;
    }*/

    
    /**
     * @Description: corsFilter  CorsConfiguration 和 UrlBasedCorsConfigurationSource。
     * @return CorsFilter
     * @Autor: wubin@wanguo.com
     */
   /* public CorsFilter bulidFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4 对接口配置跨域设置
        return new CorsFilter(source);
    }*/
    
    
    /**
     * @Description: FilterRegistrationBean   Integer.MIN_VALUE 过滤器的优先级  最高 
     * @return FilterRegistrationBean
     * @Autor: wubin@wanguo.com
     */
   /* @Bean
	public FilterRegistrationBean corsFilter() {
		FilterRegistrationBean bean = new FilterRegistrationBean(bulidFilter());
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE); //Integer.MIN_VALUE 过滤器的优先级  最高
		logger.info(">>> corsFilter 跨域Fliter成功加入 >>>");
		return bean;
	}*/
}