package com.managementapp.managementapplication.ui.response;

import com.managementapp.managementapplication.shared.dto.InvoiceDto;

import java.util.List;

public class InvoiceTransferList {
    private List<InvoiceDto> content;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean isLast;

    public InvoiceTransferList() {
    }

    public InvoiceTransferList(List<InvoiceDto> content, int pageNo, int pageSize, Long totalElements, int totalPages, boolean isLast) {
        this.content = content;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.isLast = isLast;
    }

    public List<InvoiceDto> getContent() {
        return content;
    }

    public void setContent(List<InvoiceDto> content) {
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
