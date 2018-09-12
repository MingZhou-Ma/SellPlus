package tech.greatinfo.sellplus.filter;

import org.springframework.core.annotation.Order;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 所有的ip在规定时间内拥有规定的最大访问次数，
 * 超过这个次数将会被认为是机器人在对系统进行攻击，并拒绝访问
 *
 *
 * Created by Ericwyn on 18-3-31.
 */
@Order(1)
@WebFilter(filterName = "timeStampFilter",urlPatterns = "/api/*")
public class TimeStampFilter implements Filter {
    //静态的ipMap，缓存访问的ip以及时间戳，防止短时间内请求过多
    private static HashMap<String,Integer> ipMap=new HashMap<>();
    private static final Long cleanIpMapTime = 60L;  //规定的时间
    private static final int maxRequestNum = 40;    //在规定的时间内最大的请求数量

    private static void startClearIpMap() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        ipMap.clear();
                    }
                },
                cleanIpMapTime,
                cleanIpMapTime,
                TimeUnit.SECONDS);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        startClearIpMap();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        String ip = getIpAddr(request);
        if(ipMap.get(ip)==null){
            ipMap.put(ip,1);
//            System.out.println(ip +": 第一次");
        }else {
            int askNum=ipMap.get(ip)+1;
            ipMap.put(ip,askNum);
//            System.out.println(ip +":"+askNum+"次");
            if(askNum>maxRequestNum){
                HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
                wrapper.sendError(-200,"you request too fast");
                return;
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if(ip.equals("127.0.0.1")){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if(ip != null && ip.length() > 15){
            if(ip.indexOf(",")>0){
                ip = ip.substring(0,ip.indexOf(","));
            }
        }
        return ip;
    }
}
