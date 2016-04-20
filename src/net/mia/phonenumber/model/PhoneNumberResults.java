package net.mia.phonenumber.model;

import java.io.Serializable;
import java.util.List;

public class PhoneNumberResults implements Serializable{

	private static final long serialVersionUID = 4595872832654445355L;
	private Integer currentPage = 1;
	public static Integer rowPerPage = 48;
	private List<String> results;
	private Integer total = 0;
	private String numbers;
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public List<String> getData() {
		return results;
	}
	public void setData(List<String> results) {
		this.results = results;
	}
	public Integer getRowPerPage() {
		return rowPerPage;
	}
	public void setRowPerPage(Integer rowPerPage) {
		this.rowPerPage = rowPerPage;
	}
	public Integer getTotal() {
		return this.total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getNumbers() {
		return numbers;
	}
	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}
}
