package tech.greatinfo.sellplus.controller.wechat;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 老司机（老顾客）接口
 *
 * 具体的事务逻辑是
 *
 *      1,商家后台设定几个参数
 *
 *              老司机能够发的卷的id
 *              老司机推广名额阈值，推广卷的核销达到阈值就能够兑换一张老司机专属优惠卷，暂时没有兑换数量上限
 *              老司机专属优惠卷的 id
 *
 *      2,老司机注册
 *
 *              sell 查看自己的顾客人员列表
 *              查到到具体的要成为老司机的顾客的id或者其他唯一标识
 *              生成一个固定的链接 /api/freq/beFreq?cus={顾客id}
 *              顾客访问这个链接的时候，带上 token 参数， /api/freq/beFreq?cus= {顾客id} & token = {token}
 *              后台判断 token 是否正确然后为这个用户注册成为老司机
 *
 *      3,老司机发卷
 *
 *              所有老司机发卷都是使用同一个基础接口，也就是生成的发卷链接，只有老司机的id参数是不一样的
 *              前端获取到老司机的id参数之后，生成一个链接 /api/freq/general?freq={老司机的id}
 *              然后老司机将这个链接分享给其他用户（所以实际上这一步并不需要后台参与）
 *
 *      4，新人领卷
 *
 *              新人登录之后访问领卷接口 /api/freq/general?freq={老司机的id}，还要带上
 *              发的卷将在后台记录以下基础信息：老司机id（发卷人），新人id（领卷人），卷类型（后台预设好的）
 *
 *      5，新人卷核销
 *
 *              就是优惠卷核销的逻辑
 *
 *      6，老司机领取专属优惠卷
 *
 *              前端通过接口获取老司机的（已发卷数量，核销卷数量） 接口是 /api/freq/getFreqInfo
 *              老司机进入到专属页面后，可以点击兑换老司机卷（访问老司机卷兑换接口 /api/freq/convert ）
 *              后台根据核销的数量来发卷，发卷数量 = 核销数量 / 第1步时后台设置的核销阈值
 *
 *      至此整个老司机发卷流程结束
 *
 * Created by Ericwyn on 18-9-4.
 */
public class FrequenterConrtoller {


    // 老司机通过 Seller 的顾客链接来注册成为老司机
    @RequestMapping(value = "/api/freq/beFreq",method = RequestMethod.POST)
    public ResJson beFreq(@RequestParam("cus") Long cusId,
                          @RequestParam("token") String token){
        return null;
    }

    // 发卷无需使用接口，老司机那边生成一个链接就好了
    // 链接格式是下面这个接口 + 老司机 id
    // 用户通过这个接口来领取老司机发的优惠卷
    @RequestMapping(value = "/api/freq/general?freq",method = RequestMethod.POST)
    public ResJson general(@RequestParam("freq") String freq,
                           @RequestParam("token") String token){
        return null;
    }


    // 获取自己发卷历史以及卷使用历史
    // 总共发了多少张卷
    // 有多少人核销了卷
    @RequestMapping(value = "/api/freq/getFreqInfo",method = RequestMethod.POST)
    public ResJson getFreqInfo(@RequestParam("token") String token){
        return null;
    }


    // 老司机将推广名额兑换成老司机卷
    @RequestMapping(value = "/api/freq/convert",method = RequestMethod.POST)
    public ResJson convert(@RequestParam("token") String token){
        return null;
    }

}
