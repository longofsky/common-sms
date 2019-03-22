package com.adachina.common.util.list;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @Description
 * @Author litianlong
 * @Date 2019-03-22 11:38
 */
public abstract class AbstractJoinProcessorFactory {
	protected static final Logger logger = LoggerFactory.getLogger(AbstractJoinProcessorFactory.class);

	@SuppressWarnings("unchecked")
	private static void doJoin0(Object left, Object right, String joinToField) throws ReflectiveOperationException {
		Field field = left.getClass().getDeclaredField(joinToField);
		field.setAccessible(true);
		Object fieldVal = field.get(left);
		if (!Collection.class.isAssignableFrom(field.getType())) {
			fieldVal = right;
			field.set(left, fieldVal);
			return;
		}
		Collection<Object> val = (Collection<Object>) fieldVal;
		if (val == null) {
			if (Modifier.isAbstract(field.getType().getModifiers())
					|| Modifier.isInterface(field.getType().getModifiers())) {
				//属性声明的类型为抽象类型、不能实例化。使用默认的集合类
				if (List.class.isAssignableFrom(field.getType())) {
					val = new ArrayList<>();
				} else if (Set.class.isAssignableFrom(field.getType())) {
					val = new LinkedHashSet<>();
				}
			} else {
				//属性声明的类型为具体类型、可以实例化。
				val = (Collection<Object>) field.getType().newInstance();
			}
		}
		if (val != null) {
			val.add(right);
		}
		fieldVal = val;
		field.set(left, fieldVal);
	}
	
	/**
	 * 把符合连接条件的右边元素设置到左边元素的某个属性里。<br>
	 * 若指定的左边元素属性为集合类、则add到该集合里。<br>
	 * 若指定的左边元素属性为集合类、且未初始化、则先初始化该属性、再加入到集合里。<br>
	 * 若指定的左边元素属性不为集合类、则直接修改该属性值。<br>
	 * @param joinToField
	 * @return
	 */
	public static <E1, E2> JoinProcessor<E1, E2> join2LeftField(final String joinToField, Class<E1> leftType, Class<E2> rightType){
		logger.info("JoinProcessor " + joinToField + " " + leftType + "  "+rightType);
		return new JoinProcessor<E1, E2>(){

			@Override
			public void doJoin(E1 left, E2 right) throws ReflectiveOperationException {
				doJoin0(left, right, joinToField);				
			}			
			
		};
	}
	
	/**
	 * 把符合连接条件的左边元素设置到右边元素的某个属性里。<br>
	 * 若指定的右边元素属性为集合类、则add到该集合里。<br>
	 * 若指定的右边元素属性为集合类、且未初始化、则先初始化该属性、再加入到集合里。<br>
	 * 若指定的右边元素属性不为集合类、则直接修改该属性值。<br>
	 * @param joinToField
	 * @return
	 */
	public static <E1, E2> JoinProcessor<E1, E2> join2rightField(final String joinToField, Class<E1> leftType, Class<E2> rightType){
		logger.info("JoinProcessor " + joinToField + " " + leftType + "  "+rightType);
		return new JoinProcessor<E1, E2>(){

			@Override
			public void doJoin(E1 left, E2 right) throws ReflectiveOperationException {
				doJoin0(right, left, joinToField);			
			}				
			
		};
	}
	
	public static void test(final String joinToField, Object leftType, Object rightType) throws ReflectiveOperationException {
		doJoin0(leftType, rightType, joinToField);
	}

	private AbstractJoinProcessorFactory() {
		throw new IllegalStateException("Utility class");
	}
}