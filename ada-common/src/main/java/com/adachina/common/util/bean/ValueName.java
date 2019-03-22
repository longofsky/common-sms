
package com.adachina.common.util.bean;

/**
 * @Description
 * @Author litianlong
 * @Date 2019-03-22 11:19
 */
public class ValueName {

	/**
	 * value of object
	 */
	private Integer value;
	/**
	 * name of object
	 */
	private String name;

	public ValueName(){
		this.value = null;
		this.name = null;
	}

	public ValueName(Integer value, String name) {
		super();
		this.value = value;
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
