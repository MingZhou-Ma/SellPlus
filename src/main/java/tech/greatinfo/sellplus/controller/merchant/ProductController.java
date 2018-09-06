package tech.greatinfo.sellplus.controller.merchant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.greatinfo.sellplus.domain.Product;
import tech.greatinfo.sellplus.service.ActivityService;
import tech.greatinfo.sellplus.service.MerchantService;
import tech.greatinfo.sellplus.service.ProductService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 产品管理接口
 *
 * Created by Ericwyn on 18-8-6.
 */
@RestController
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    ProductService productService;

    @Autowired
    ActivityService activityService;


    // 增加商品
    @RequestMapping(value = "/api/mer/addProduct",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson addProduct(@RequestParam(name = "token") String token,
                              @ModelAttribute Product product){
        try {
            if (tokenService.getUserByToken(token) != null){
                productService.save(product);
                return ResJson.successJson("add product success", product);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            logger.error("/api/mer/addProduct -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // 查看商品
    @RequestMapping(value = "/api/mer/listProduct",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson findProduct(@RequestParam(name = "token") String token,
                               @RequestParam(name = "start",defaultValue = "0") Integer start,
                               @RequestParam(name = "num",defaultValue = "10") Integer num){
        try {
            if (tokenService.getUserByToken(token) != null){
                return ResJson.successJson("find product success",
                        productService.findAllByPages(start,num));
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            logger.error("/api/mer/listProduct -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // 修改商品
    @RequestMapping(value = "/api/mer/updateProduct",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson findProduct(@RequestParam(name = "token") String token,
                               @ModelAttribute Product product){
        try {
            if (tokenService.getUserByToken(token) != null){
                if (product.getId() == null){
                    return ResJson.failJson(7002,"product id error",null);
                }
                Product oldEntity = null;
                if ((oldEntity = productService.findOne(product.getId())) == null ){
                    return ResJson.failJson(7003,"无法更新, 权限错误",null);
                }
                productService.update(oldEntity, product);
                return ResJson.successJson("success update product");
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            logger.error("/api/mer/updateProduct -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // 删除商品
    @RequestMapping(value = "/api/mer/delProduct",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson findProduct(@RequestParam(name = "token") String token,
                               @RequestParam(name = "productid") Long productId){
        try {
            if (tokenService.getUserByToken(token) != null){
                productService.deleteProduct(productId);
                return ResJson.successJson("success delete product");
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            logger.error("/api/mer/delProduct -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }
}
