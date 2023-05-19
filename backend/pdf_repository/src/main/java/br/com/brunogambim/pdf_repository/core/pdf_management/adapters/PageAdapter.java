package br.com.brunogambim.pdf_repository.core.pdf_management.adapters;

import java.util.List;
import java.util.function.Function;

public class PageAdapter<T> {
	List<T> items;
	int pageIndex;
	int pageSize;
	long totalElements;
	
	public PageAdapter() {
	}
	
	public PageAdapter(List<T> items, int pageIndex, int pageSize, long totalElements) {
		this.items = items;
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
	}

	public List<T> getItems() {
		return items;
	}
	
	public void setItems(List<T> items) {
		this.items = items;
	}
	
	public int getPageIndex() {
		return pageIndex;
	}
	
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public long getTotalElements() {
		return totalElements;
	}
	
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	
	public<R> PageAdapter<R> mapTo(Function<T, R> mapper){
		return new PageAdapter<R>(items.stream().map(mapper).toList(), pageIndex, pageSize, totalElements);
	}
}
