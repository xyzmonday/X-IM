package com.yff.xim.util;

import com.yff.xim.model.UserAccountEntity;
import io.netty.channel.ChannelHandlerContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImUtils {

    // \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),
    // 字符串在编译时会被转码一次,所以是 "\\b"
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)
    static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
            + "|windows (phone|ce)|blackberry"
            + "|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
            + "|laystation portable)|nokia|fennec|htc[-_]"
            + "|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"
            + "|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
    //移动设备正则匹配：手机端、平板
    static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
    static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);
    
    /**
     * byte数组转换成16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    
   
    
   /**
    * 获取IP地址及端口    
    * @param socketaddress
    * @return   {ip}:{prot}  字符串
    */
    public static String getIpAndProt(InetSocketAddress socketaddress) {
    	String address="";
        if (address != null) {
        	address=  getIp(socketaddress) + ":" + socketaddress.getPort();
        }  
        return address;
    }

    /**
     * 获取IP地址
     * @param socketaddress
     * @return  {ip} 字符串
     */
    public static String getIp(InetSocketAddress socketaddress) {
    	String ip="";
        if (socketaddress != null) {
        	 InetAddress address = socketaddress.getAddress();
        	 ip = (address == null) ? socketaddress.getHostName() : address.getHostAddress();
        } 
        return ip;
    }
    
    

    public static String getRemoteAddress(ChannelHandlerContext ctx) {
        InetSocketAddress remote = (InetSocketAddress) ctx.channel().remoteAddress();
        return getIpAndProt(remote);
    }

    public static String getLocalAddress(ChannelHandlerContext ctx) {
        InetSocketAddress local = (InetSocketAddress) ctx.channel().localAddress();
        return getIpAndProt(local);
    }


    /**
     * 检测是否是移动设备访问
     *
     * @return true:移动设备接入，false:pc端接入
     * @Title: check
     */
    public static String check(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String temp = GlobalConstant.ViewTemplateConfig.template;//默认模板
        try {
            String sername = request.getServerName();
            if (sername.indexOf("m.") > -1) {
                temp = GlobalConstant.ViewTemplateConfig.mobiletemplate;
            } else {
                //获取ua，用来判断是否为移动端访问
                String userAgent = request.getHeader("USER-AGENT").toLowerCase();
                if (null == userAgent) {
                    userAgent = "";
                }
                // 匹配
                Matcher matcherPhone = phonePat.matcher(userAgent);
                Matcher matcherTable = tablePat.matcher(userAgent);
                //判断是否为移动端访问
                if (matcherPhone.find() || matcherTable.find()) {
                    temp = GlobalConstant.ViewTemplateConfig.mobiletemplate;
                }
            }
            session.setAttribute("template", temp);
        } catch (Exception e) {
        }
        return temp;
    }

    /**
     * 当前页
     * @param request
     * @return
     */
    public static int getSkipToPage(HttpServletRequest request) {
        int skipToPage = 1;
        try {
            skipToPage = Integer.parseInt(request.getParameter("skipToPage"));
        } catch (Exception e) {
            skipToPage = 1;
        }
        return skipToPage;
    }


    /**
     * 取得登录用户
     *
     * @return
     */
    public static UserAccountEntity getLoginUser(HttpServletRequest request) {
        UserAccountEntity user = null;
        user = (UserAccountEntity) request.getSession().getAttribute("user");
        return user;
    }

    public static void saveLoginUser(HttpServletRequest request,UserAccountEntity userAccountEntity) {
        request.getSession().setAttribute("user",userAccountEntity);
    }
     
}