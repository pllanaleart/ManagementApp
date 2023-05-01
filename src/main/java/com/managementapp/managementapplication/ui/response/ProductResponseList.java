package com.managementapp.managementapplication.ui.response;

import com.managementapp.managementapplication.shared.dto.ProductsDto;

import java.util.List;

public class ProductResponseList {
    private List<ProductsDto> content;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean isLast;

    public ProductResponseList() {
    }

    public ProductResponseList(List<ProductsDto> productsDtos, int pageNo, int pageSize, Long totalElements, int totalPages, boolean isLast) {
        this.content = productsDtos;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.isLast = isLast;
    }

    public List<ProductsDto> getContent() {
        return content;
    }

    public void setContent(List<ProductsDto> content) {
        this.content = content;
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

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }
}
