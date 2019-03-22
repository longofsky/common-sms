package com.adachina.common.util;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author alexzhu
 */
public class ClassHelper {
	private static Logger logger = LoggerFactory.getLogger(ClassHelper.class);

	private ClassHelper() {
		throw new IllegalStateException("ClassHelper Utility class");
	}

	public static Class<?> forNameWithThreadContextClassLoader(String name) throws ClassNotFoundException {
		return forName(name, Thread.currentThread().getContextClassLoader());
	}

	public static Class<?> forNameWithCallerClassLoader(String name, Class<?> caller) throws ClassNotFoundException {
		return forName(name, caller.getClassLoader());
	}

	public static ClassLoader getCallerClassLoader(Class<?> caller) {
		return caller.getClassLoader();
	}

	/**
	 * get class loader 
	 * 
	 * @param cls
	 * @return class loader
	 */
	public static ClassLoader getClassLoader(Class<?> cls) {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Exception ex) {
			logger.error("getClassLoader failed.", ex);
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = cls.getClassLoader();
		}
		return cl;
	}

	/**
	 * Return the default ClassLoader to use: typically the thread context
	 * ClassLoader, if available; the ClassLoader that loaded the ClassUtils
	 * class will be used as fallback.
	 * <p>
	 * Call this method if you intend to use the thread context ClassLoader in a
	 * scenario where you absolutely need a non-null ClassLoader reference: for
	 * example, for class path resource loading (but not necessarily for
	 * <code>Class.forName</code>, which accepts a <code>null</code> ClassLoader
	 * reference as well).
	 * 
	 * @return the default ClassLoader (never <code>null</code>)
	 * @see Thread#getContextClassLoader()
	 */
	public static ClassLoader getClassLoader() {
		return getClassLoader(ClassHelper.class);
	}

	/**
	 * Same as <code>Class.forName()</code>, except that it works for primitive
	 * types.
	 */
	public static Class<?> forName(String name) throws ClassNotFoundException {
		return forName(name, getClassLoader());
	}

	private static final String SEMICOLON= ";";
	private static final String SQUARE = "[";
	/**
	 * Replacement for <code>Class.forName()</code> that also returns Class
	 * instances for primitives (like "int") and array class names (like
	 * "String[]").
	 * 
	 * @param name the name of the Class
	 * @param classLoader the class loader to use (may be <code>null</code>,
	 *            which indicates the default class loader)
	 * @return Class instance for the supplied name
	 * @throws ClassNotFoundException if the class was not found
	 * @throws LinkageError if the class file could not be loaded
	 * @see Class#forName(String, boolean, ClassLoader)
	 */
	public static Class<?> forName(String name, ClassLoader classLoader) throws ClassNotFoundException, LinkageError {
		if(name == null){
			return null;
		}
		Class<?> clazz = resolvePrimitiveClassName(name);
		if (clazz != null) {
			return clazz;
		}

		// "java.lang.String[]" style arrays
		if (ARRAY_SUFFIX.equals(name)) {
			String elementClassName = name.substring(0, name.length() - ARRAY_SUFFIX.length());
			Class<?> elementClass = forName(elementClassName, classLoader);
			return Array.newInstance(elementClass, 0).getClass();
		}

		// "[Ljava.lang.String;" style arrays
		int internalArrayMarker = name.indexOf(INTERNAL_ARRAY_PREFIX);
		if (internalArrayMarker != -1 && name.endsWith(SEMICOLON)) {
			String elementClassName = null;
			if (internalArrayMarker == 0) {
				elementClassName = name.substring(INTERNAL_ARRAY_PREFIX.length(), name.length() - 1);
			} else if (name.startsWith(SQUARE)) {
				elementClassName = name.substring(1);
			}
			Class<?> elementClass = forName(elementClassName, classLoader);
			return Array.newInstance(elementClass, 0).getClass();
		}

		ClassLoader classLoaderToUse = classLoader;
		if (classLoaderToUse == null) {
			classLoaderToUse = getClassLoader();
		}
		return classLoaderToUse.loadClass(name);
	}

	private static final int PRIMITIVE_CLS_NAME_LEN = 8;
	/**
	 * Resolve the given class name as primitive class, if appropriate,
	 * according to the JVM's naming rules for primitive classes.
	 * <p>
	 * Also supports the JVM's internal class names for primitive arrays. Does
	 * <i>not</i> support the "[]" suffix notation for primitive arrays; this is
	 * only supported by {@link #forName}.
	 * 
	 * @param name the name of the potentially primitive class
	 * @return the primitive class, or <code>null</code> if the name does not
	 *         denote a primitive class or primitive array class
	 */
	public static Class<?> resolvePrimitiveClassName(String name) {
		Class<?> result = null;
		// Most class names will be quite long, considering that they
		// SHOULD sit in a package, so a length check is worthwhile.
		if (name != null && name.length() <= PRIMITIVE_CLS_NAME_LEN) {
			// Could be a primitive - likely.
			result = PRIMITIVE_TYPE_NAME_MAP.get(name);
		}
		return result;
	}

	/** Suffix for array class names: "[]" */
	public static final String ARRAY_SUFFIX = "[]";
	/** Prefix for internal array class names: "[L" */
	private static final String INTERNAL_ARRAY_PREFIX = "[L";

	/**
	 * Map with primitive type name as key and corresponding primitive type as
	 * value, for example: "int" -> "int.class".
	 */
	private static final Map<String, Class<?>> PRIMITIVE_TYPE_NAME_MAP = new HashMap<>(16);

	/**
	 * Map with primitive wrapper type as key and corresponding primitive type
	 * as value, for example: Integer.class -> int.class.
	 */
	private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new HashMap<>(8);

	static {
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Character.class, char.class);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Double.class, double.class);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Float.class, float.class);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Integer.class, int.class);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Long.class, long.class);
		PRIMITIVE_WRAPPER_TYPE_MAP.put(Short.class, short.class);

		Set<Class<?>> primitiveTypeNames = new HashSet<>(16);
		primitiveTypeNames.addAll(PRIMITIVE_WRAPPER_TYPE_MAP.values());
		primitiveTypeNames.addAll(Arrays.asList(new Class<?>[] { boolean[].class, byte[].class, char[].class,
				double[].class, float[].class, int[].class, long[].class, short[].class }));
		for (Iterator<Class<?>> it = primitiveTypeNames.iterator(); it.hasNext();) {
			Class<?> primitiveClass =  it.next();
			PRIMITIVE_TYPE_NAME_MAP.put(primitiveClass.getName(), primitiveClass);
		}
	}

	public static String toShortString(Object obj) {
		if (obj == null) {
			return "null";
		}
		return obj.getClass().getSimpleName() + "@" + System.identityHashCode(obj);

	}
}