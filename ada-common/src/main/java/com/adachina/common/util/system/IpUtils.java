package com.adachina.common.util.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * ip工具类
 * 
 * @author fanglin
 *
 */
public class IpUtils {
	private static Logger logger = LoggerFactory.getLogger(IpUtils.class);
	private static final String IP_ENDS_WITH_COMMA =  ",";
	/**
	 * 获取系统ip
	 * @return 系统ip
	 */
	public static String getIp(){
		try {
			InetAddress address = InetAddress.getLocalHost();
			String hostAddress = address.getHostAddress();      
			return hostAddress;
		} catch (UnknownHostException e) { 
			logger.error("获取系统ip时出现异常：", e);
		}
		return null;
	} 
	
	/**
	 * 获取系统ip（网卡）
	 * @return ips，多个网卡将会有多个ip，以“,”号分割
	 */
	public static String getAllIp(){
		StringBuilder sb = new StringBuilder();
	    Enumeration<NetworkInterface> netInterfaces;  
	    try {  
	        netInterfaces = NetworkInterface.getNetworkInterfaces();  
	        InetAddress ip;  
	        while (netInterfaces.hasMoreElements()) {  
	            NetworkInterface ni = netInterfaces.nextElement();  
	            Enumeration<InetAddress> addresses=ni.getInetAddresses();  
	            while(addresses.hasMoreElements()){  
	                ip = addresses.nextElement();  
	                if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(':') == -1) {  
	                	 sb.append(ip.getHostAddress()).append(",");
	                }  
	            }  
	        }
	    } catch (Exception e) {  
	    	logger.error("获取系统ip时出现异常：", e);
	    }
	    String ip = "";
	    String ips = sb.toString();
	    if(ips.endsWith(IP_ENDS_WITH_COMMA)){
	    	ip = ips.substring(0,ips.length() -1);
	    }
	    return ip;
	}

	private IpUtils() {
		throw new IllegalStateException("Utility class");
	}
}
