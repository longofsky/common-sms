/**
 * 
 */
package com.adachina.common.util.disseq;

/**
 * @Description
 * @Author litianlong
 * @Date 2019-03-22 11:27
 */
public class IdCreater {
	private static DisSequnceUtil disSequnceUtil = new DisSequnceUtil(1);

	private IdCreater() {
		throw new IllegalStateException("IdCreater Utility class");
	}
	
	public static long getNextId(){
		return disSequnceUtil.nextId();
	}
	public static long getNextId(int wordId){
		disSequnceUtil = new DisSequnceUtil(wordId);
		return disSequnceUtil.nextId();
	}

}
