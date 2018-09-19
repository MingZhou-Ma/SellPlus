package tech.greatinfo.sellplus.utils.obj;

import java.util.UUID;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.domain.Seller;
import tech.greatinfo.sellplus.domain.intf.User;

/**
 *
 * 用户token对象可以
 *
 * TODO：直接将token存储到数据库，这样的话当系统重启的时候已经登录的用户无需再次登录
 *
 * Created by Ericwyn on 18-1-12.
 */
public class AccessToken {
    //token 有效期 60 分钟
    public static final int MAX_EXPIRED_TIME = 120;
    private static final int USER_MERCHANT = 1;
    private static final int USER_CUSTOMER = 2;
    private static final int USER_SELLER = 3;
    //唯一编码
    private String uuid;
    //用户对象
//    private User user;

    private Merchant merchant;
    private Customer customer;
    private Seller seller;

    private int userType ;

    //时间戳
    private Long createTime;

    public AccessToken() {

    }

    /**
     * 是否初始化 uuid 和创建时间
     * @param init
     */
    public AccessToken(boolean init) {
        if (init){
            this.uuid= UUID.randomUUID().toString().replaceAll("-","");
            this.createTime=System.currentTimeMillis();
        }
    }

    public static int getMaxExpiredTime() {
        return MAX_EXPIRED_TIME;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public User getUser() {
        switch (userType){
            case USER_MERCHANT:
                return this.merchant;
            case USER_SELLER:
                return this.seller;
            case USER_CUSTOMER:
                return this.customer;
        }
        return null;
    }

    public void setUser(User user) {
        if (user instanceof Merchant){
            this.userType = USER_MERCHANT;
            this.merchant = (Merchant) user;
        }else if (user instanceof Seller){
            this.userType = USER_SELLER;
            this.seller = (Seller) user;
        }else if (user instanceof Customer){
            this.userType = USER_CUSTOMER;
            this.customer = (Customer) user;
        }
    }

    //判断验证码是否过期，过期了返回true
    // 减 1000 是为了让他过期的更快, 这样的话 refresh 的时候不那么容易出现冲突
    public boolean isExpired(){
        return (System.currentTimeMillis()-createTime)>(1000 * 60 * MAX_EXPIRED_TIME-1000);
    }

    public void refresh(){
        this.createTime = System.currentTimeMillis();
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
