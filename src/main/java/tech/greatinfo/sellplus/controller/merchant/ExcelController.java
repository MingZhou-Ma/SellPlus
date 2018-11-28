package tech.greatinfo.sellplus.controller.merchant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.DateUtil;
import tech.greatinfo.sellplus.utils.ExcelUtil;
import tech.greatinfo.sellplus.utils.obj.ResJson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 导出excel表
 */
@RestController
public class ExcelController {

    private static final Logger logger = LoggerFactory.getLogger(ExcelController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    CustomService customService;

    /**
     * UV统计
     * @param token
     * @return
     */
    @RequestMapping(value = "/api/mer/exportXls", method = RequestMethod.GET)
    public ResJson exportXls(@RequestParam(name = "token") String token, HttpServletResponse response) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            List<Customer> list = customService.getAllByPhoneNotNull();

            String[] headTitle = {"昵称", "手机号码", "所属销售", "注册时间"};
            String[][] data = new String[list.size()][headTitle.length];
            for (int i = 0; i < list.size(); i++) {
                Customer customer = list.get(i);
                data[i][0] = customer.getNickname();
                data[i][1] = customer.getPhone();
                data[i][2] = customer.getSeller().getName();
                data[i][3] = DateUtil.formatDate(customer.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            }

            try {
                ExcelUtil.exportXls("客户表格", "客户列表", headTitle, data, response);
                return ResJson.successJson("success");
            } catch (IOException e) {
                e.printStackTrace();
                return ResJson.failJson(4000, "下载excel失败", null);
            }
        } catch (Exception e) {
            logger.error("/api/mer/exportXls -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
