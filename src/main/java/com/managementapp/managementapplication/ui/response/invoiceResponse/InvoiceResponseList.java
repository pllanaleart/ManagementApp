package com.managementapp.managementapplication.ui.response.invoiceResponse;

import java.util.List;

public class InvoiceResponseList {
    private List<InvoiceResponseModel> content;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean isLast;

    public InvoiceResponseList(List<InvoiceResponseModel> content, int pageNo, int pageSize, Long totalElements, int totalPages, boolean isLast) {
        this.content = content;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.isLast = isLast;
    }

    public InvoiceResponseList() {
    }

    public List<InvoiceResponseModel> getContent() {
        return content;
    }

    public void setContent(List<InvoiceResponseModel> content) {
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
