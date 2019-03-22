package com.adachina.common.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alexzhu
 */
public class ClassUtil {
    /**
     *
     */
    public interface ClassFilter {
        /**
         * 是否接受当前class
         * @param clazz
         * @return
         */
        boolean accept(Class<?> clazz);
    }

    /**
     * 获取符合资源pattern的资源.
     * 资源pattern可以为classpath, file, http等
     *
     * @param respathPtn
     * @return
     * @author mengxianming-2016年2月25日
     */
    public static Resource[] scanResources(String respathPtn) {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            return resourcePatternResolver.getResources(respathPtn);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 扫描classpath, 获取符合资源pattern并通过过滤器的所有类
     *
     * @param respathPtn
     * @param filter
     * @return
     * @author mengxianming-2016年2月25日
     */
    public static List<Class<?>> scanClass(String respathPtn, ClassFilter filter) {
        List<Class<?>> result = new ArrayList<Class<?>>();

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resourcePatternResolver.getResources(respathPtn);
            MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource rs : resources) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(rs);
                String clazzName = metadataReader.getClassMetadata().getClassName();
                Class<?> clazz = Class.forName(clazzName);
                if (filter == null || filter.accept(clazz)) {
                    result.add(clazz);
                }
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 扫描classpath、获取符合资源pattern的指定rootClass的所有子类（包括自身).
     *
     * @param resourcePattern
     * @param rootClass       父类或者父接口
     * @return
     * @author mengxianming-2016年2月25日
     */
    public static List<Class<?>> scanSubClasses(String resourcePattern, final Class<?> rootClass) {
        List<Class<?>> list = scanClass(resourcePattern, new ClassFilter() {

            @Override
            public boolean accept(Class<?> clazz) {
                return rootClass.isAssignableFrom(clazz);
            }

        });
        //root 排在最前面
        list.remove(rootClass);
        list.add(0, rootClass);
        return list;
    }

    /**
     * 获取指定类的所有常量字段的值。<br>
     * 常量字段应该修饰为public static final。
     * 返回值Map的key为字段名、value为字段值。
     *
     * @param clazz
     * @return
     * @author mengxianming-2016年2月25日
     */
    public static Map<String, Object> getPublicFinalStaticFieldValues(Class<?> clazz) {
        Map<String, Object> vals = new LinkedHashMap<String, Object>();
        try {
            Field[] pubFields = clazz.getDeclaredFields();
            for (Field f : pubFields) {
                if (Modifier.isPublic(f.getModifiers())
                        && Modifier.isFinal(f.getModifiers())
                        && Modifier.isStatic(f.getModifiers())) {
                    vals.put(f.getName(), f.get(null));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return vals;
    }

    /**
     * 确定class是否可以被加载
     *
     * @param className   完整类名
     * @param classLoader 类加载
     * @return 是否出现
     * @author 蕉鹿
     * @date 2016.07.12
     */
    public static boolean isPresent(String className, ClassLoader classLoader) {
        try {
            Class.forName(className, true, classLoader);
            return true;
        } catch (Throwable t) {
            // Class or one of its dependencies is not present...
            return false;
        }
    }

    /**
     * Determine whether the given class has a public constructor with the given signature.
     * <p>Essentially translates <code>NoSuchMethodException</code> to "false".
     * @param clazz	the clazz to analyze
     * @param paramTypes the parameter types of the method
     * @return whether the class has a corresponding constructor
     * @see Class#getMethod
     */
    public static boolean hasConstructor(Class<?> clazz, Class<?>... paramTypes) {
        return (getConstructorIfAvailable(clazz, paramTypes) != null);
    }

    /**
     * Determine whether the given class has a public constructor with the given signature,
     * and return it if available (else return <code>null</code>).
     * <p>Essentially translates <code>NoSuchMethodException</code> to <code>null</code>.
     * @param clazz	the clazz to analyze
     * @param paramTypes the parameter types of the method
     * @return the constructor, or <code>null</code> if not found
     * @see Class#getConstructor
     */
    public static <T> Constructor<T> getConstructorIfAvailable(Class<T> clazz, Class<?>... paramTypes) {
        Assert.notNull(clazz, "Class must not be null");
        try {
            return clazz.getConstructor(paramTypes);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
