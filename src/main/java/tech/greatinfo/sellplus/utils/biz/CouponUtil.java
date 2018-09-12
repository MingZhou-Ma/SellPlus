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
package tech.greatinfo.sellplus.utils.biz;

/**     
* @Package：tech.greatinfo.sellplus.utils   
* @ClassName：CouponUtil   
* @Description：   <p> 优惠券工具类 生成券码等.. / 废除 - 采用RandomUtils随机数据 -  {@code RandomUtils}</p>
* @Author： - Jason   
* @CreatTime：2018年8月15日 下午4:33:33   
* @Modify By：   
* @ModifyTime：  2018年8月15日
* @Modify marker：   
* @version    V1.0
*/
public class CouponUtil {
	
	/**
	 * @Description: 全部为字母
	 * @param n
	 * @return String
	 * @Autor: Jason
	 */
	public static String randomWordStr(int n) {
		String str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String str2 = "";
		int len = str1.length() - 1;
		double r;
		for (int i = 0; i < n; i++) {
			r = (Math.random()) * len;
			str2 = str2 + str1.charAt((int) r);
		}
		return str2;
	}
	
	/**
	 * @Description: 获取n位长度的随机字符串
	 * @param n      字符串长度
	 * @return String
	 * @Autor: Jason
	 */
	public static String randomStr(int n) {
		String str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		String str2 = "";
		int len = str1.length() - 1;
		double r;
		for (int i = 0; i < n; i++) {
			r = (Math.random()) * len;
			str2 = str2 + str1.charAt((int) r);
		}
		return str2;
	}
	
	/**
	 * @Description: 大写+数字
	 * @param n
	 * @return String
	 * @Autor: Jason
	 */
	public static String randomUpStr(int n) {
		String allStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		String newStr = "";
		int len = allStr.length() - 1;
		double r;
		for (int i = 0; i < n; i++) {
			r = (Math.random()) * len;
			newStr = newStr + allStr.charAt((int) r);
		}
		return newStr;
	}
	
	/**
	 * @Description: 小写
	 * @param n
	 * @return String
	 * @Autor: Jason
	 */
	public static String randomDownStr(int n) {
		String allStr = "abcdefghijklmnopqrstuvwxyz1234567890";
		String newStr = "";
		int len = allStr.length() - 1;
		double r;
		for (int i = 0; i < n; i++) {
			r = (Math.random()) * len;
			newStr = newStr + allStr.charAt((int) r);
		}
		return newStr;
	}
	
	
	//------------------------------Test-------------------------------
	public static void main(String[] args) {
		System.out.println(randomWordStr(6));
		System.out.println(randomStr(6));
		System.out.println(randomUpStr(6));
		System.out.println(randomDownStr(6));
	}

}
