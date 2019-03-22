package com.adachina.common.util.list;

/**
 * @Description
 * @Author litianlong
 * @Date 2019-03-22 11:38
 */
public interface JoinProcessor<E1, E2> {
	/**
	 * 对符合连接条件的左表列(元素)与右表列(元素)进行连接处理。
	 * 
	 * @param left
	 * @param right
	 * @throws ReflectiveOperationException
	 */
	void doJoin(E1 left, E2 right) throws ReflectiveOperationException;
}
