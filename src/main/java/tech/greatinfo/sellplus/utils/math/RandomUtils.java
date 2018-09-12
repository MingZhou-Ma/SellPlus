/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年8月15日 下午4:33:33</p>
 *  <p> Created by Jason </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.utils.math;

import java.util.Random;

/**
* @Package：tech.greatinfo.sellplus.utils.math   
* @ClassName：RandomUtils   
* @Description：   <p> 随机数工具类</p>
* @Author： - Jason   
* @CreatTime：2018年8月20日 下午6:27:34   
* @Modify By：   
* @ModifyTime：  2018年8月20日
* @Modify marker：   
* @version    V1.0
 */
public class RandomUtils {
	
	private static final String ALL_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	private static final String LETTER_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
	private static final String NUMBER_CHAR = "0123456789";
    
    /**
     * 获取定长的随机数，包含大小写、数字
     * @param length 随机数长度
     * @return
     */
    public static String generateString(int length) { 
        StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(ALL_CHAR.charAt(random.nextInt(ALL_CHAR.length()))); 
        } 
        return sb.toString(); 
    } 
    
    /**
     * 获取定长的随机数,包含大小写字母
     * @param length 随机数长度
     * @return
     */
    public static String generateMixString(int length) { 
        StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(LETTER_CHAR.charAt(random.nextInt(LETTER_CHAR.length()))); 
        } 
        return sb.toString(); 
    } 
    
    /**
     * 获取定长的随机数，只包含小写字母
     * @param length 随机数长度
     * @return
     */
    public static String generateLowerString(int length) { 
        return generateMixString(length).toLowerCase(); 
    } 
    
    /**
     * 获取定长的随机数,只包含大写字母
     * @param length 随机数长度
     * @return
     */
    public static String generateUpperString(int length) { 
        return generateMixString(length).toUpperCase(); 
    } 
    
    /**
     * 获取定长的随机数,只包含数字
     * @param length 随机数长度
     * @return
     */
    public static String generateNumberString(int length){
    	StringBuffer sb = new StringBuffer(); 
        Random random = new Random(); 
        for (int i = 0; i < length; i++) { 
                sb.append(NUMBER_CHAR.charAt(random.nextInt(NUMBER_CHAR.length()))); 
        } 
        return sb.toString(); 
    }
    
    /**
     * For Test by Jason
     */
    public static void main(String[] args) {
    	Integer nums = 6;
		System.out.println("***********Test******************");
		System.out.println("大小写数字:"+generateString(nums));
		System.out.println("大小写字母:"+generateMixString(nums));
		System.out.println("小写字母:"+generateLowerString(nums));
		System.out.println("大写字母:"+generateUpperString(nums));
		System.out.println("纯数字:"+generateNumberString(nums));
		System.out.println("***********Test******************");
	}
    
}
//Outputs
//***********Test******************
//大小写数字:SsvQTjEbMW
//大小写字母:nJaVyqdmAP
//小写字母:ppljietimr
//大写字母:YDAGERAFCR
//纯数字:8986305301
//***********Test******************
