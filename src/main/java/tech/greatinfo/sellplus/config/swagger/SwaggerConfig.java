/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年8月14日 下午2:00:31</p>
 *  <p> Created by Jason </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.config.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**     
* @Package：tech.greatinfo.sellplus.config.swagger   
* @ClassName：SwaggerConfig   
* @Description：   <p> SwaggerConfig @See http://ip:port/swagger-ui.html </p>
* @Author： - Jason   
* @CreatTime：2018年8月14日 下午2:00:31   
* @Modify By：   
* @ModifyTime：  2018年8月14日
* @Modify marker：   
* @version    V1.0
*/
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "enabled", havingValue = "true",prefix="swagger",matchIfMissing=false) //if swagger.enabled - true 开启
public class SwaggerConfig {
	
	/**
	 * @Description: 摘要信息
	 * @return Docket
	 * @Autor: Jason
	 */
	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(bulidApiInfo())//文档描述信息
        .select()
        .apis(RequestHandlerSelectors.basePackage("tech.greatinfo.sellplus"))
        .paths(PathSelectors.any())
        .build();
    }

	
	/**
	 * @Description: api文档描述信息
	 * @return ApiInfo
	 * @Autor: Jason
	 */
	private ApiInfo bulidApiInfo() {
        return new ApiInfoBuilder()
        .title("SellPlus-Api 销售plus接口文档")
        .description("RESTful接口文档 | 万国信息")
        .termsOfServiceUrl("http://wanguo.com")
        .contact(new Contact("Wubin", "https://wanguo.com/", "wubin@wanguo.com"))
        .version("v1.0")
        //.license("China Licence Version 1.0").licenseUrl("#")
        .build();
    }

}
