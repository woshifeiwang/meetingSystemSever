package com.hnair.iot.dataserver.util;

import java.util.List;

import com.hnair.iot.dataserver.model.Filter;

public class Criterias {
	/**
	 * Default find And.
	 */
	private static final boolean DEFAULT_ANDOR_CASE = true;

	private List<Filter> listFilter;

	private boolean isAndOr = DEFAULT_ANDOR_CASE;
	
	private int pageNo;
	
	private int pageSize;
	
	
	public Criterias() {}
	
	public Criterias(List<Filter> listFilter,int PageNo,int pageSize) {
		this.listFilter = listFilter;
		this.isAndOr = DEFAULT_ANDOR_CASE;
		this.pageNo = PageNo;
		this.pageSize = pageSize;
	}
	
	public Criterias(List<Filter> listFilter, boolean isAndOr,int PageNo,int pageSize) {
		this.listFilter = listFilter;
		this.isAndOr = isAndOr;
		this.pageNo = PageNo;
		this.pageSize = pageSize;
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public boolean isAndOr() {
		return isAndOr;
	}

	public void setAndOr(boolean isAndOr) {
		this.isAndOr = isAndOr;
	}

	public List<Filter> getListFilter() {
		return listFilter;
	}

	public void setListFilter(List<Filter> listFilter) {
		this.listFilter = listFilter;
	}

}
