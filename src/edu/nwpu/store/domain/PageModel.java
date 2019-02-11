package edu.nwpu.store.domain;

import java.util.ArrayList;
import java.util.List;

//携带2种数据
//1_当前页中的商品信息
//2_分页相关参数信息
public class PageModel {
	// 1_当前页中的商品信息
	private List list = new ArrayList();
	// 2_各种分页参数
	private int currentPageNum;// 当前页数，由用户指定
	private int pageSize;// 每页显示的条数，固定的
	private int totalRecords;// 总记录条数，数据库查出来的

	private int totalPageNum;// 总页数，计算出来的
	private int startIndex;// 每页开始记录的索引，计算出来的
	private int prePageNum;// 上一页
	private int nextPageNum;// 下一页

	// 一共每页显示9个页码按钮
	private int startPage;// 开始页码
	private int endPage;// 结束页码

	// 完善属性
	private String url;

	public PageModel(int currentPageNum, int pageSize, int totalRecords) {
		this.currentPageNum = currentPageNum;
		this.pageSize = pageSize;
		this.totalRecords = totalRecords;

		totalPageNum = (totalRecords % pageSize == 0) ? (totalRecords / pageSize) : (totalRecords / pageSize + 1);
		startIndex = (currentPageNum - 1) * pageSize;
		if (currentPageNum == 1) {
			prePageNum = 1;
		} else {
			prePageNum = currentPageNum - 1;
		}

		if (currentPageNum == totalPageNum) {
			nextPageNum = totalPageNum;
		} else {
			nextPageNum = currentPageNum + 1;
		}

		// 计算开始页码结束页码
		startPage = currentPageNum - 4;
		endPage = currentPageNum + 4;

		// 如果总页数大于9页
		if (totalPageNum > 9) {
			// 如果总页数50页
			// 当前页:第10页 startPage=6 endPage=14
			// 当前页:第3页 startPage=1 endPage=9
			// 当前页:第4页 startPage=1 endPage=9
			// 当前页:第48页 startPage=42 endPage=50
			// 当前页:第46页 startPage=42 endPage=50
			if (startPage <= 0) {
				startPage = 1;
				endPage = startPage + 8;
			}
			if (endPage > totalPageNum) {
				endPage = totalPageNum;
				startPage = totalPageNum - 8;
			}
		} else {
			// 如果总页数小于9页
			startPage = 1;
			endPage = totalPageNum;
		}

	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPrePageNum() {
		return prePageNum;
	}

	public void setPrePageNum(int prePageNum) {
		this.prePageNum = prePageNum;
	}

	public int getNextPageNum() {
		return nextPageNum;
	}

	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = nextPageNum;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
