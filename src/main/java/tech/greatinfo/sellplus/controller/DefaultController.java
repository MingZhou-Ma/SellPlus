package tech.greatinfo.sellplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import tech.greatinfo.sellplus.domain.Activity;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.domain.Product;
import tech.greatinfo.sellplus.domain.article.Article;
import tech.greatinfo.sellplus.service.ActivityService;
import tech.greatinfo.sellplus.service.ArticleService;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.MerchantService;
import tech.greatinfo.sellplus.service.ProductService;
import tech.greatinfo.sellplus.utils.DateUtil;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 一些测试接口, 导入默认数据这样
 *
 * Created by Ericwyn on 18-8-8.
 */
@RestController
public class DefaultController {
    @Autowired
    MerchantService merchantService;

    @Autowired
    ProductService productService;

    @Autowired
    ActivityService activityService;

    @Autowired
    CustomService customService;

    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/api/default/setMerchant",method = RequestMethod.GET)
    public ResJson setDefaultSet(){
        //增加商家
        Merchant merchant = new Merchant();
        merchant.setId(-1L);
        merchant.setAccount("test");
//        merchant.setAudit(true);
//        merchant.setBusiness("444444444444444444");
//        merchant.setGender(true);
//        merchant.setLocation("广州大学城南垃圾场对门要你命3000小卖部");
//        merchant.setName("马云云");
        merchant.setPassword("test");
//        merchant.setPhone("13078428643");
//        merchant.setShopName("要你命3000小卖部");
//        merchant.setxAxis("113.398649");
//        merchant.setyAxis("23.045816");
        merchantService.save(merchant);
        return ResJson.successJson("新增测试商户");
    }

    @RequestMapping(value = "/api/default/setProduct",method = RequestMethod.GET)
    public ResJson setDefaultProduct(){
        //增加商品
        Product product = new Product();
        product.setId(1L);
        product.setName("要命1000");
        product.setIntro("要你命3000是小卖部最新研发产品");
        product.setDepiction("要命1000维生素, 吃了活不了的维生素, 维生素是人和动物为维持正常的生理功能而必须从食物中获得的一类微量有机物质，在人体生长、代谢、发育过程中发挥着重要的作用。维生素既不参与构成人体细胞，也不为人体提供能量。 \n" +
                "维生素是维持身体健康所必需的一类有机化合物。这类物质在体内既不能是构成身体组织的原料，也不是能量的来源，而是一类调节物质，在物质代谢中起重要作用。 " +
                "维生素又名维他命，通俗来讲，即维持生命的物质 [4]  ，是维持人体生命活动必须的一类有机物质，也是保持人体健康的重要活性物质。维生素在体内的含量很少，但不可或缺。各种维生素的化学结构以及性质虽然不同，但它们却有着以下共同点：\n" +
                "①维生素均以维生素原的形式存在于食物中；\n" +
                "②维生素不是构成机体组织和细胞的组成成分，它也不会产生能量，它的作用主要是参与机体代谢的调节；\n" +
                "③大多数的维生素，机体不能合成或合成量不足，不能满足机体的需要，必须经常通过食物中获得；\n" +
                "④人体对维生素的需要量很小，日需要量常以毫克或微克计算，但一旦缺乏就会引发相应的维生素缺乏症，对人体健康造成损害；\n" +
                "维生素与碳水化合物、脂肪和蛋白质3大物质不同，在天然食物中仅占极少比例，但又为人体所必需。有些维生素如：B6.K等能由动物肠道内的细菌合成，合成量可满足动物的需要。动物细胞可将色氨酸转变成烟酸（一种B族维生素），但生成量不敷需要；维生素C除灵长类及豚鼠以外，其他动物都可以自身合成。植物和多数微生物都能自己合成维生素，不必由体外供给。许多维生素是辅基或辅酶的组成部分。\n" +
                "维生素是人和动物营养、生长所必需的某些少量有机化合物，对机体的新陈代谢、生长、发育、健康有极重要作用。如果长期缺乏某种维生素，就会引起生理机能障碍而发生某种疾病。一般由食物中取得。现阶段发现的有几十种，如维生素A、维生素B、维生素C等。\n" +
                "维生素是人体代谢中必不可少的有机化合物。人体犹如一座极为复杂的化工厂，不断地进行着各种生化反应。其反应与酶的催化作用有密切关系。酶要产生活性，必须有辅酶参加。已知许多维生素是酶的辅酶或者是辅酶的组成分子。因此，维生素是维持和调节机体正常代谢的重要物质。可以认为，最好的维生素是以“生物活性物质”的形式，存在于人体组织中。 [5] ");
        product.setPrice(100.0);
        productService.save(product);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("要你命500");
        product2.setIntro("要你命500也是小卖部最新研发产品");
        product2.setDepiction("要命500氨基酸, 吃了一定凉的氨基酸, " +
                "氨基酸为无色晶体，熔点超过200℃，比一般有机化合物的熔点高很多。α一氨基酸有酸、甜、苦、鲜4种不同味感。谷氨酸单钠盐和甘氨酸是用量最大的鲜味调味料。氨基酸一般易溶于水、酸溶液和碱溶液中，不溶或微溶于乙醇或乙醚等有机溶剂。氨基酸在水中的溶解度差别很大，例如酪氨酸的溶解度最小，25℃时，100 g水中酪氨酸仅溶解0.045 g，但在热水巾酪氨酸的溶解度较大。赖氨酸和精氨酸常以盐酸盐的形式存在，因为它们极易溶于水，因潮解而难以制得结晶 [5]  。\n" +
                "(1) 色泽和颜色 各种常见的氨基酸易成为无色结晶，结晶形状因氨基酸的结构不同而有所差异。如L一谷氨酸为四角柱形结晶，D一谷氨酸则为菱形片状结晶。\n" +
                "(2) 熔点 氨基酸结晶的熔点较高，一般在200～300℃，许多氨基酸在达到或接近熔点时会分解成胺和CO2。\n" +
                "(3)溶解度 绝大部分氨基酸都能溶于水。不同氨基酸在水中的溶解度有差别，如赖氨酸、精氨酸、脯氨酸的溶解度较大，酪氨酸、半胱氨酸、组氨酸的溶解度很小。各种氨基酸都能溶于强碱和强酸中。但氨基酸不溶或微溶于乙醇。\n" +
                "(4) 味感 氨基酸及其衍生物具有一定的味感，如酸、甜、苦、成等。其味感的种类与氨基酸的种类、立体结构有关。从立体结构上讲，一般来说，D一型氨基酸都具有甜味，其甜味强度高于相应的L一型氨基酸。\n" +
                "(5)紫外吸收特性 各种常见的氨基酸对可见光均无吸收能力。但酪氨酸、色氨酸和苯丙氨酸在紫外光区具有明显的光吸收现象。而大多数蛋白质中都含有这3种氨基酸，尤其是酪氨酸。因此，可以利用280hm波长处的紫外吸收特性定量检测蛋白质的含量。 [6] ");
        product2.setPrice(88.0);
        productService.save(product2);
        return ResJson.successJson("新增测试商品成功");
    }

    @RequestMapping(value = "/api/default/setActivity",method = RequestMethod.GET)
    public ResJson setDefaultActivity(){
        Product product = productService.findOne(1L);
        Activity activity = new Activity();
        activity.setEndDate(DateUtil.formatString("2018-10-10","yyyy-MM-dd"));
        activity.setGroupNum(5);
        activity.setHelpNum(10);
        activity.setGroupPrice(66.0);
        activity.setHeadline("要你命1000大甩卖了");
        activity.setId(1L);
        activity.setGroup(true);
        activity.setProduct(product);
        activity.setStartDate(DateUtil.formatString("2018-07-10","yyyy-MM-dd"));
        activityService.save(activity);
        System.out.println("新增活动 1");

        Product product2 = productService.findOne(2L);
        Activity activity2 = new Activity();
        activity2.setEndDate(DateUtil.formatString("2018-10-10","yyyy-MM-dd"));
        activity2.setGroupNum(5);
        activity2.setHelpNum(10);
        activity2.setGroupPrice(66.0);
        activity2.setHeadline("要你命500大甩卖了");
        activity2.setId(2L);
        activity2.setGroup(false);
        activity2.setProduct(product2);
        activity2.setStartDate(DateUtil.formatString("2018-07-10","yyyy-MM-dd"));
        activityService.save(activity2);
        System.out.println("新增活动 2");

        Product product3 = productService.findOne(1L);
        Activity activity3 = new Activity();
        activity3.setEndDate(DateUtil.formatString("2018-10-10","yyyy-MM-dd"));
        activity3.setGroupNum(5);
        activity3.setHelpNum(10);
        activity3.setGroupPrice(66.0);
        activity3.setHeadline("测试活动3(拼团)");
        activity3.setId(3L);
        activity3.setGroup(false);
        activity3.setProduct(product3);
        activity3.setStartDate(DateUtil.formatString("2018-07-10","yyyy-MM-dd"));
        activityService.save(activity3);
        System.out.println("新增活动 3");

        Product product4 = productService.findOne(2L);
        Activity activity4 = new Activity();
        activity4.setEndDate(DateUtil.formatString("2018-10-10","yyyy-MM-dd"));
        activity4.setGroupNum(5);
        activity4.setHelpNum(10);
        activity4.setGroupPrice(66.0);
        activity4.setHeadline("测试活动4(助力)");
        activity4.setId(4L);
        activity4.setGroup(false);
        activity4.setProduct(product4);
        activity4.setStartDate(DateUtil.formatString("2018-07-10","yyyy-MM-dd"));
        activityService.save(activity4);
        System.out.println("新增活动 2");

        return ResJson.successJson("新增测试活动");
    }

    @RequestMapping(value = "/api/default/testUpdate",method = RequestMethod.GET)
    public ResJson updateTest(){
        Customer customer = new Customer();
        customer.setPhone("新手机号");
        Customer old = customService.getOne(1L);
        customService.update(old, customer);
        return ResJson.successJson("OK");
    }

    @RequestMapping(value = "/api/defalut/setUUID")
    public ResJson test222(){
        Merchant one = merchantService.findOne(1L);
        one.setLocation("小姑未街道第 1 号楼 223 房");
        one.setPhone("2220 8888");
        one.setShopName("超级无敌大商店");
        merchantService.save(one);
        return null;
    }

    @RequestMapping(value = "/api/defalut/setArticle")
    public ResJson test223(){
        Merchant one = merchantService.findOne(1L);
        Article article = new Article();
        article.setAuthor("作者");
        article.setContent("内容2018-09-10 22:03:50.209 [WARN ] org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver - Resolved exception caused by Handler execution: org.springframework.orm.jpa.JpaSystemException: Null value was assigned to a property [class tech.greatinfo.sellplus.domain.Customer.frequenter] of primitive type setter of tech.greatinfo.sellplus.domain.Customer.frequenter; nested exception is org.hibernate.PropertyAccessException: Null value was assigned to a property [class tech.greatinfo.sellplus.domain.Customer.frequenter] of primitive type setter of tech.greatinfo.sellplus.domain.Customer.frequenter\n");
        article.setCreateDate(new Date());
        article.setTitle("标题1");
        articleService.save(article);


        article = new Article();
        article.setAuthor("作者2");
        article.setContent("内容2018-09-10 22:03:50.209 [WARN ] org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver - Resolved exception caused by Handler execution: org.springframework.orm.jpa.JpaSystemException: Null value was assigned to a property [class tech.greatinfo.sellplus.domain.Customer.frequenter] of primitive type setter of tech.greatinfo.sellplus.domain.Customer.frequenter; nested exception is org.hibernate.PropertyAccessException: Null value was assigned to a property [class tech.greatinfo.sellplus.domain.Customer.frequenter] of primitive type setter of tech.greatinfo.sellplus.domain.Customer.frequenter\n");
        article.setCreateDate(new Date());
        article.setTitle("标题2");
        articleService.save(article);

        article = new Article();
        article.setAuthor("作者3");
        article.setContent("内容2018-09-10 22:03:50.209 [WARN ] org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver - Resolved exception caused by Handler execution: org.springframework.orm.jpa.JpaSystemException: Null value was assigned to a property [class tech.greatinfo.sellplus.domain.Customer.frequenter] of primitive type setter of tech.greatinfo.sellplus.domain.Customer.frequenter; nested exception is org.hibernate.PropertyAccessException: Null value was assigned to a property [class tech.greatinfo.sellplus.domain.Customer.frequenter] of primitive type setter of tech.greatinfo.sellplus.domain.Customer.frequenter\n");
        article.setCreateDate(new Date());
        article.setTitle("标题3");
        articleService.save(article);

        article = new Article();
        article.setAuthor("作者4");
        article.setContent("内容2018-09-10 22:03:50.209 [WARN ] org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver - Resolved exception caused by Handler execution: org.springframework.orm.jpa.JpaSystemException: Null value was assigned to a property [class tech.greatinfo.sellplus.domain.Customer.frequenter] of primitive type setter of tech.greatinfo.sellplus.domain.Customer.frequenter; nested exception is org.hibernate.PropertyAccessException: Null value was assigned to a property [class tech.greatinfo.sellplus.domain.Customer.frequenter] of primitive type setter of tech.greatinfo.sellplus.domain.Customer.frequenter\n");
        article.setCreateDate(new Date());
        article.setTitle("标题4");
        articleService.save(article);

        return null;
    }

    @RequestMapping(value = "/ping")
    public String ping(){
        return "pong";
    }
}
