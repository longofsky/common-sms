package com.ada.common.util.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;

/**
 * 系统资源工具类
 * @author fanglin
 *
 */
public class SystemResource {
	private static Logger logger = LoggerFactory.getLogger(SystemResource.class);
	/**
	 * 获取进程id
	 * @return 进程id
	 */
	public static String getPid() {
        try {
            String name = ManagementFactory.getRuntimeMXBean().getName();  
            String pid = name.split("@")[0];  
            return pid;
        } catch (Exception e) {
        	logger.error("获取进程id时出现异常：", e);
        }
        return null;
    }

    private SystemResource() {
        throw new IllegalStateException("Utility class");
    }
}
