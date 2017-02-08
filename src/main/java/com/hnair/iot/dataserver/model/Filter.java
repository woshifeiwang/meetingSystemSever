package com.hnair.iot.dataserver.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Value;

/**
 * 筛选
 */
public class Filter implements Serializable {

	private static final long serialVersionUID = -8712382358441065075L;

	/**
	 * 运算符
	 */
	public enum Operator {

		/** 等F于 */
		EQ,

		/** 不等于 */
		NE,

		/** 大于 */
		GT,

		/** 小于 */
		LT,

		/** 包含 */
		IN,

		/** IS */
		IS,

		/** 大于等于 */
		GTE,

		/** 小于等于 */
		LTE,

		/** 时间段 */
		RANGETIME;

		/**
		 * 从String中获取Operator
		 * 
		 * @param value 值
		 * @return String对应的operator
		 */
		public static Operator fromString(String value) {
			return Operator.valueOf(value.toLowerCase());
		}
	}

	/** 属性 */
	private String property;

	/** 运算符 */
	private Operator operator;

	/** 值 */
	private Object value;

	/** 开始时间 */
	private ZonedDateTime startTime;

	/** 结束时间 */
	private ZonedDateTime endTime;

	@Value("${time.timeSpan}")
	private String timeSpan;

	@Value("${time.amountToAdd}")
	private Long amountToAdd;

	/**
	 * 初始化一个新创建的Filter对象
	 */
	public Filter() {
	}

	/**
	 * 初始化一个新创建的Filter对象
	 * 
	 * @param property 属性
	 * @param operator 运算符
	 * @param value 值
	 */
	public Filter(String property, Operator operator, Object value) {
		this.property = property;
		this.operator = operator;
		this.value = value;
	}

	/**
	 * 初始化一个时间段
	 * 
	 * @param property 属性
	 * @param operator 运算符
	 * @param value 值
	 */
	public Filter(String property, Operator operator, ZonedDateTime startTime, ZonedDateTime endTime) {
		this.property = property;
		this.operator = operator;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * 返回等于筛选
	 * 
	 * @param property 属性
	 * @param value 值
	 * @return 等于筛选
	 */
	public static Filter eq(String property, Object value) {
		return new Filter(property, Operator.EQ, value);
	}

	/**
	 * is
	 * 
	 * @param property 属性
	 * @param value 值
	 * 
	 */
	public static Filter is(String property, Object value) {
		return new Filter(property, Operator.IS, value);
	}

	/**
	 * 返回不等于筛选
	 * 
	 * @param property 属性
	 * @param value 值
	 * @return 不等于筛选
	 */
	public static Filter ne(String property, Object value) {
		return new Filter(property, Operator.NE, value);
	}

	/**
	 * 返回大于筛选
	 * 
	 * @param property 属性
	 * @param value 值
	 * @return 大于筛选
	 */
	public static Filter gt(String property, Object value) {
		return new Filter(property, Operator.GT, value);
	}

	/**
	 * 返回小于筛选
	 * 
	 * @param property 属性
	 * @param value 值
	 * @return 小于筛选
	 */
	public static Filter lt(String property, Object value) {
		return new Filter(property, Operator.LT, value);
	}

	/**
	 * 返回大于等于筛选
	 * 
	 * @param property 属性
	 * @param value 值
	 * @return 大于等于筛选
	 */
	public static Filter ge(String property, Object value) {
		return new Filter(property, Operator.GTE, value);
	}

	/**
	 * 返回小于等于筛选
	 * 
	 * @param property 属性
	 * @param value 值
	 * @return 小于等于筛选
	 */
	public static Filter le(String property, Object value) {
		return new Filter(property, Operator.LTE, value);
	}

	/**
	 * 返回包含筛选
	 * 
	 * @param property 属性
	 * @param value 值
	 * @return 包含筛选
	 */
	public static Filter in(String property, Object value) {
		return new Filter(property, Operator.IN, value);
	}

	/**
	 * 返回时间段筛选
	 * 
	 * @param property 属性
	 * @param value 值
	 * @return 时间段
	 */
	public static Filter rangeTime(String property, ZonedDateTime startTime, ZonedDateTime endTime) {
		return new Filter(property, Operator.RANGETIME, startTime, endTime);
	}

	public ZonedDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(ZonedDateTime startTime) {
		this.startTime = startTime;
	}

	public ZonedDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(ZonedDateTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * 获取属性
	 * 
	 * @return 属性
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * 设置属性
	 * 
	 * @param property 属性
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * 获取运算符
	 * 
	 * @return 运算符
	 */
	public Operator getOperator() {
		return operator;
	}

	/**
	 * 设置运算符
	 * 
	 * @param operator 运算符
	 */
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/**
	 * 获取值
	 * 
	 * @return 值
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置值
	 * 
	 * @param value 值
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((property == null) ? 0 : property.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Filter other = (Filter) obj;
		if (operator != other.operator)
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		}
		else if (!property.equals(other.property))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		}
		else if (!value.equals(other.value))
			return false;
		return true;
	}

}