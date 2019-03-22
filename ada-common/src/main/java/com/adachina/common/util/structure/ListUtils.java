package com.adachina.common.util.structure;

import com.adachina.common.util.CheckUtil;
import com.adachina.common.util.list.JoinProcessor;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.util.*;


/**
 * @author lxh
 */
public class ListUtils {
	private static Log log = LogFactory.getLog(ListUtils.class);

	public static interface ElementFilter<T> {
		/**
		 * 是否过滤当前元素
		 * 
		 * @param ele
		 * @return
		 */
		boolean filter(T ele);
	}

	/**
	 *
	 * @param beanList
	 * @param keyProp
	 * @param <K>
	 * @param <T>
	 * @return
	 */
	public static <K, T> Map<K, T> listToMap(List<T> beanList, String keyProp)  {
		Map<K, T> result = new LinkedHashMap<K, T>();
		if (!CheckUtil.isEmpty(beanList) && !CheckUtil.isEmpty(keyProp)) {
			for (T bean : beanList) {
				try {
					@SuppressWarnings("unchecked")
					K val = (K) PropertyUtils.getProperty(bean, keyProp);
					if (val != null) {
						result.put(val, bean);
					}
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
		return result;
	}

	/**
	 *
	 * @param beanList
	 * @param keyProp
	 * @param <K>
	 * @param <T>
	 * @return
	 */
	public static <K, T> Map<K, List<T>> groupListBy(List<T> beanList, String keyProp) {
		Map<K, List<T>> result = new LinkedHashMap<K, List<T>>();
		if (!CheckUtil.isEmpty(beanList) && !CheckUtil.isEmpty(keyProp)) {
			for (T bean : beanList) {
				try {
					@SuppressWarnings("unchecked")
					K val = (K) PropertyUtils.getProperty(bean, keyProp);
					if (val != null) {
						List<T> list = result.get(val);
						if (CheckUtil.isEmpty(list)) {
							list = new ArrayList<T>();
							result.put(val, list);
						}
						list.add(bean);
					}
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
		return result;
	}

	/**
	 *
	 * @param list
	 * @param prop
	 * @param descOrAsc
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> orderBy(List<T> list, final String prop, final String descOrAsc) {
		Comparator<? super T> comp = new Comparator<T>() {
			@SuppressWarnings({"unchecked", "rawtypes"})
			@Override
			public int compare(T o1, T o2) {
				try {
					Object propVal1 = getProperty(o1, prop);
					Object propVal2 = getProperty(o2, prop);
					if (propVal1 == propVal2) {
						return 0;
					}
					if (propVal1 == null) {
						return 1;
					}
					if (propVal2 == null) {
						return -1;
					}
					if (propVal1.getClass() != propVal2.getClass()) {
						throw new RuntimeException(
								"element's property value must have the same type.");
					}
					if (!Comparable.class.isAssignableFrom(propVal1.getClass())) {
						throw new RuntimeException(
								"element's property in the list not implement Comparable interface. The list cannot sort.");
					}
					int result = ((Comparable) propVal1).compareTo(propVal2);
					return "desc".equalsIgnoreCase(descOrAsc) ? -result : result;
				}  catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		};
		ArrayList<T> result = new ArrayList<T>(list);
		Collections.sort(result, comp);
		return result;
	}

	public static Object getProperty(Object bean, String prop)  {
		// cast map[key] --> map(key) which quired by PropertyUtils
		prop = prop.replace("[", "(").replace("]", ")");

		try {
			return PropertyUtils.getProperty(bean, prop);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 *
	 * @param list
	 * @param condition
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> filter(List<T> list, ElementFilter<T> condition) {
		List<T> result = new ArrayList<T>();
		if (list != null) {
			for (T ele : list) {
				if (condition.filter(ele)) {
					result.add(ele);
				}
			}
		}
		return result;
	}

	/**
	 *
	 * @param maplist
	 * @param condition
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> Map<K, V> filterMap(Map<K, V> maplist, ElementFilter<Map.Entry<K, V>> condition) {
		Map<K, V> result = new LinkedHashMap<K, V>();
		if (maplist != null) {
			for (Map.Entry<K, V> entry : maplist.entrySet()) {
				if (condition.filter(entry)) {
					result.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return result;
	}

	/**
	 *
	 * @param list
	 * @param condition
	 * @param <T>
	 * @return
	 */
	public static <T> T first(List<T> list, ElementFilter<T> condition) {
		if (list != null) {
			for (T ele : list) {
				if (condition.filter(ele)) {
					return ele;
				}
			}
		}
		return null;
	}
	
	public static <Element, PropType> List<PropType> select(List<Element> list, String property, 
			String errorMessage) {
		return select(list, property, false, false, errorMessage);
	}

	public static <Element, PropType> List<PropType> selectDistinct(List<Element> list, String property,
			 String errorMessage) {
		return select(list, property, false, true,  errorMessage);
	}

	public static <Element, PropType> List<PropType> selectExcludeEmptyValue(List<Element> list, String property,
			 String errorMessage)  {
		return select(list, property, true, false,  errorMessage);
	}

	public static <Element, PropType> List<PropType> selectDistinctExcludeEmptyValue(List<Element> list,
			String property,  String errorMessage) {
		return select(list, property, true, true, errorMessage);
	}

	/**
	 *
	 * @param list
	 * @param property
	 * @param excludeEmptyValue
	 * @param distinct
	 * @param <Element>
	 * @param <PropType>
	 * @return
	 */
	public static <Element, PropType> List<PropType> select(List<Element> list, String property,
			boolean excludeEmptyValue, boolean distinct, String errorMsg) {
		List<PropType> result = new ArrayList<PropType>();
		if (list != null && !CheckUtil.isEmpty(property)) {
			try {
				for (Element bean : list) {
					if (bean == null) {
						continue;
					}
					@SuppressWarnings("unchecked")
					PropType val = (PropType) getProperty(bean, property);
					if (excludeEmptyValue && CheckUtil.isEmpty(val)) {
						continue;
					}
					result.add(val);
				}
			} catch (Exception e) {
				throw new RuntimeException(errorMsg, e);
			}
		}
		if (distinct) {
			result = new ArrayList<PropType>(new LinkedHashSet<PropType>(result));
		}
		return result;
	}

	public static <T> T uniqueRecord(List<T> list, String errorMsg) {
		if (list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw new RuntimeException(errorMsg);
		}
		return list.get(0);
	}

	public static <T> T uniqueRecord(List<T> list, Object... errorMsgArgs) {
		if (list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw new RuntimeException("Records not unique");
		}
		return list.get(0);
	}

	/**
	 * 将字符类型的数字list转换为真实的数字类型。<br>
	 * 目前支持基本数字类型的包装类以及BigInteger, BigDecimal等支持通过构造函数把string类型的 数字构造自身的数字类型。
	 * 
	 * @param strNumbers
	 * @param toType
	 * @return
	 * @author mengxianming-2015年11月16日
	 */
	public static <N extends Number> List<N> toNumbers(List<String> strNumbers, Class<N> toType){
		if (CheckUtil.isEmpty(strNumbers)) {
			return Collections.emptyList();
		}
		List<N> numbers = new ArrayList<N>(strNumbers.size());
		try {
			for (String num : strNumbers) {
				Constructor<N> c = toType.getConstructor(String.class);
				numbers.add(c.newInstance(num));
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return numbers;
	}

	/**
	 * 取list每个元素的两个属性作为map的key、value，返回该map。 key、value的类型根据指定的参数会做cast处理。<br>
	 * 其中、若value的实际类型与指定参数(valType)不匹配、且valType为数字类型、<br>
	 * 则内部会把value转换为String、然后 String转换成valType指定的数字类型。<br>
	 * 
	 * @param beanList
	 * @param keyProp
	 * @param keyType
	 * @param valProp
	 * @param valType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K, V> listToMap(List<?> beanList, String keyProp, Class<K> keyType, String valProp,
			Class<V> valType)  {
		Map<K, V> result = new LinkedHashMap<K, V>();
		try {
			for (Object bean : beanList) {
				Object key = PropertyUtils.getProperty(bean, keyProp);
				Object val = PropertyUtils.getProperty(bean, valProp);
				// 若数字类型不匹配、则进行类型转换
				if (val != null && val.getClass() != valType && Number.class.isAssignableFrom(valType)) {
					val = getValueFromConstructor(keyType, valType, key, val, false);
				}
				// date 2017年7月5日 吴涛 添加KEY强转数字类型
				if (key != null && key.getClass() != keyType && Number.class.isAssignableFrom(keyType)) {
					key = getValueFromConstructor(keyType, valType, key, val, true);
				}
				if (val != null) {
					result.put((K) key, (V) val);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		return result;
	}

	public static <W,X> Object getValueFromConstructor(Class<W> keyType, Class<X> valType, Object keyValue,
														Object valValue, boolean isKey) throws ReflectiveOperationException {
		if(isKey){
			Constructor<W> c = keyType.getConstructor(String.class);
			if (c != null) {
				log.debug("property值与指定数字类型不匹配、将转换为指定数字类型. keyRealType=" + valValue.getClass() + ", key=" + valValue
						+ "keyRequiredType=" + valType);
				return c.newInstance(keyValue.toString());
			}
		}else{
			Constructor<X> c = valType.getConstructor(String.class);
			if (c != null) {
				log.debug("property值与指定数字类型不匹配、将转换为指定数字类型. valRealType=" + valValue.getClass() + ", val=" + valValue
						+ "valRequiredType=" + valType);
				return c.newInstance(valValue.toString());
			}
		}
		return null;
	}

	/**
	 *
	 * @param beanList
	 * @param keyProp
	 * @return
	 */
	public static List<Integer> getListKeys(List<?> beanList, String keyProp) {
		List<Integer> result = new ArrayList<Integer>();
		if (!CheckUtil.isEmpty(beanList) && !CheckUtil.isEmpty(keyProp)) {
			for (Object bean : beanList) {
				try {
					Integer val = (Integer) PropertyUtils.getProperty(bean, keyProp);
					if (val != null) {
						result.add(val);
					}
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
		return result;
	}

	/**
	 * 从List对象中获取某一属性值的List
	 * 
	 * @brief 从List对象中获取某一属性值的List
	 * @details 从List对象中获取某一属性值的List
	 * @param beanList
	 * @param keyProp
	 * @param propType
	 * @return List<T>
	 * @exception @author
	 *                石头
	 * @date 2017年3月14日 上午11:32:44
	 * @note 石头@ 2017年3月14日添加了此方法
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getListKeys(List<?> beanList, String keyProp, Class<T> propType) {
		List<T> result = new ArrayList<T>();
		if (!CheckUtil.isEmpty(beanList) && !CheckUtil.isEmpty(keyProp)) {
			for (Object bean : beanList) {
				try {
					Object propVal = PropertyUtils.getProperty(bean, keyProp);

					if (propVal != null && propType.isAssignableFrom(propVal.getClass())) {
						result.add(((T) propVal));
					}
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
		return result;
	}

	/**
	 * 对两个list进行等值连接。
	 * 
	 * @param left
	 * @param right
	 * @param leftJoinProp
	 * @param rightJoinProp
	 * @param process
	 * @throws Exception
	 */
	public static <E1, E2> void equijoin(List<E1> left, List<E2> right, String leftJoinProp, String rightJoinProp,
			JoinProcessor<E1, E2> process)  {
		try {
			Map<Object, List<E2>> rightMap = ListUtils.groupListBy(right, rightJoinProp);
			for (E1 bean : left) {
				Object leftval = getProperty(bean, leftJoinProp);
				List<E2> matches = rightMap.get(leftval);
				if (matches != null) {
					for (E2 rbean : matches) {
						process.doJoin(bean, rbean);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对两个list进行自然等值连接。
	 * 
	 * @param left
	 * @param right
	 * @param joinProp
	 * @param process
	 * @throws Exception
	 */
	public static <E1, E2> void naturalJoin(List<E1> left, List<E2> right, String joinProp,
			JoinProcessor<E1, E2> process) {
		equijoin(left, right, joinProp, joinProp, process);
	}

	/**
	 * 多个list合并成一个list
	 * 
	 * @param lists
	 * @return
	 */
	@SafeVarargs
	public static <E> List<E> addAll(List<E>... lists) {
		List<E> result = new ArrayList<E>();
		if (lists != null && lists.length > 0) {
			for (List<E> list : lists) {
				if (list != null) {
					result.addAll(list);
				}
			}
		}
		return result;
	}

	/**
	 * 从源列表里获取指定属性值、返回这些属性组成的动态对象(Map)列表。<br>
	 * 动态对象的属性名或者为指定字段的属性名、或者为该属性名对应的别名。<br>
	 * 若属性别名不指定则取属性名自身。
	 * 
	 * @param list
	 *            目标对象
	 * @param proNames
	 *            目标属性名列表
	 * @return 动态对象列表
	 */
	public static List<Map<String, Object>> selectDynaObjList(List<?> list, List<String> proNames,
			List<String> proAliasList)  {
		if (list == null || list.size() == 0 || proNames == null || proNames.size() == 0) {
			return Collections.emptyList();
		}

		proAliasList = proAliasList == null ? new ArrayList<String>() : proAliasList;
		List<Map<String, Object>> result = new ArrayList<>();
		for (Object bean : list) {
			LinkedHashMap<String, Object> e = new LinkedHashMap<String, Object>();
			for (int i = 0; i < proNames.size(); i++) {
				Object val = getProperty(bean, proNames.get(i));
				String mapppedProName = i < proAliasList.size() ? proAliasList.get(i) : proNames.get(i);
				e.put(mapppedProName, val);
			}

			result.add(e);
		}

		return result;
	}

	/**
	 *
	 * @param beanList
	 * @param <T>
	 * @return
	 */
	public static <T> Set<T> listToSet(List<T> beanList) {
		Set<T> result = new HashSet<T>();
		if (!CheckUtil.isEmpty(beanList)) {
			for (T bean : beanList) {
				result.add(bean);
			}
		}
		return result;
	}

	/**
	 * @brief 分割list集合
	 * @details 按照指定长度分割list集合
	 * @param list
	 *            原始集合
	 * @param len
	 *            指定长度
	 * @return 返回新的list集合
	 * @see
	 * @author 船长
	 * @date 2017-05-31 15:34:00
	 * @note
	 */
	public static <T> List<List<T>> splitList(List<T> list, int len) {
		if (list == null || list.size() == 0 || len < 1) {
			return null;
		}
		List<List<T>> result = new ArrayList();
		int size = list.size();
		int count = (size + len - 1) / len;
		for (int i = 0; i < count; i++) {
			List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
			result.add(subList);
		}
		return result;
	}

	public static List<String> getListByMap(Map<String, String> map, boolean isKey) {
		List<String> list = new ArrayList<String>();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			if (isKey) {
				list.add(key);
			} else {
				list.add(map.get(key));
			}
		}
		return list;
	}

	/**
	 * @brief 分割list集合 支持泛型
	 * @details 按照指定长度分割list集合
	 * @param list
	 *            原始集合
	 * @param len
	 *            指定长度
	 * @return 返回新的list集合
	 * @see
	 * @author 靑夏
	 * @date 2017年8月8日17:48:28
	 * @note
	 */
	public static <T> List<List<T>> splitListNew(List<T> list, int len) {
		if (list == null || list.size() == 0 || len < 1) {
			return null;
		}
		List<List<T>> result = new ArrayList<>();
		int size = list.size();
		int count = (size + len - 1) / len;
		for (int i = 0; i < count; i++) {
			List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
			result.add(subList);
		}
		return result;
	}


	private ListUtils() {
		throw new IllegalStateException("Utility class");
	}
}