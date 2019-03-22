package com.adachina.common.util;


import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Enumeration;

/**
 * 取app的各种信息
 *
 * @author 戴德荣
 * @date 2018-11-23
 * @since 3.2.0
 */
public class ProjectInfoUtils {


    private ProjectInfoUtils() {

    }

    /**
     * 项目名称，一般指jar包名称
     * 针对 springboot 时 为jar包名称
     */
    private static String projectName = null;

    /**
     * 设置project名称
     *
     * @param projectName
     */
    public static void setProjectName(String projectName) {
        if (projectName == null) {
            return;
        }
        ProjectInfoUtils.projectName = projectName;
    }

    /**
     * 读取当前project包名称
     *
     * @return
     */
    public static String getProjectName() {
        if (projectName == null) {
            projectName = getProjectNameByCurrClass();
        }
        return projectName;
    }

    /**
     * 根据当前类所在的getProtectionDomain来取jar包
     *
     * @return 可执行jar包名称
     */
    private static String getProjectNameByCurrClass() {
        //通过当前类的jar来加载
        PermissionCollection permissionCollection = null;
        if (ProjectInfoUtils.class.getProtectionDomain() != null) {
            permissionCollection = ProjectInfoUtils.class.getProtectionDomain().getPermissions();
        }
        if (permissionCollection != null) {
            Enumeration<Permission> elements = permissionCollection.elements();
            if (elements.hasMoreElements()) {
                String name = elements.nextElement().getName();
                String jarSuffix = ".jar";
                if (name.endsWith(jarSuffix)) {
                    name = name.replace(jarSuffix, "");
                }
                String forwardSlash = "/";
                if (name.contains(forwardSlash)) {
                    int lastIndex = name.lastIndexOf(forwardSlash) + 1;
                    return name.substring(lastIndex);
                }
            }
        }
        return "";
    }


}
