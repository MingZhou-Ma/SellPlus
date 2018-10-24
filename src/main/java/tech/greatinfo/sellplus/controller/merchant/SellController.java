package tech.greatinfo.sellplus.controller.merchant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.greatinfo.sellplus.domain.Seller;
import tech.greatinfo.sellplus.service.SellerSerivce;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 公司销售人员相关 API
 *
 * Created by Ericwyn on 18-8-13.
 */
@RestController
public class SellController {
    // TODO 设置默认的销售,当前默认id为1的用户为默认销售

    // TODO 查看所有的销售列表

    private static final Logger logger = LoggerFactory.getLogger(SellController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    SellerSerivce sellerSerivce;


    /**
     * 添加销售
     * @param token
     * @param seller
     * @return
     */
    @RequestMapping(value = "/api/mer/addSeller")
    public ResJson addSeller(@RequestParam(name = "token") String token, @ModelAttribute Seller seller) {
        try {
            if (tokenService.getUserByToken(token) != null){
                sellerSerivce.save(seller);
                return ResJson.successJson("add seller success", seller);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            logger.error("/api/mer/addProduct -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // 修改销售
    @RequestMapping(value = "/api/mer/updateSeller",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson updateActivity(@RequestParam(name = "token") String token,
                                  @ModelAttribute Seller seller ){
        try {
            if (tokenService.getUserByToken(token) != null){
                if (seller.getId() == null){
                    return ResJson.failJson(7004,"seller id error",null);
                }
                Seller oldSeller;
                if ((oldSeller = sellerSerivce.findOne(seller.getId())) == null ){
                    return ResJson.failJson(7003,"无法更新, 权限错误",null);
                }
                sellerSerivce.updateSeller(oldSeller,seller);
                return ResJson.successJson("update Seller success");
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            logger.error("/api/mer/updateActivity -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

//    @RequestMapping(value = "/api/mer/delSeller",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
//    public ResJson findProduct(@RequestParam(name = "token") String token,
//                               @RequestParam(name = "sellerId") Long sellerId){
//        try {
//            if (tokenService.getUserByToken(token) != null){
//                sellerSerivce.deleteSeller(sellerId);
//                return ResJson.successJson("success delete seller");
//            }else {
//                return ResJson.errorAccessToken();
//            }
//        }catch (Exception e){
//            logger.error("/api/mer/delSeller -> ",e.getMessage());
//            e.printStackTrace();
//            return ResJson.serverErrorJson(e.getMessage());
//        }
//    }

    /**
     * 查看销售列表     * @param token
     * @param start
     * @param num
     * @return
     */
    @RequestMapping(value = "/api/mer/listSeller",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson findProduct(@RequestParam(name = "token") String token,
                               @RequestParam(name = "start",defaultValue = "0") Integer start,
                               @RequestParam(name = "num",defaultValue = "10") Integer num){
        try {
            if (tokenService.getUserByToken(token) != null){
                return ResJson.successJson("find seller success",
                        sellerSerivce.findAllByPages(start,num));
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            logger.error("/api/mer/listSeller -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
