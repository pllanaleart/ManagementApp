package com.managementapp.managementapplication.ui.response.purchasesResponse;

import com.managementapp.managementapplication.shared.dto.InvoiceDto;
import com.managementapp.managementapplication.shared.dto.PurchasesDto;

import java.util.List;

public class PurchasesPagedResponseModel {

    private List<PurchaseResponseModel> content;
    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private boolean isLast;

    public PurchasesPagedResponseModel() {
    }

    public PurchasesPagedResponseModel(List<PurchaseResponseModel> content, int pageNo, int pageSize, Long totalElements, int totalPages, boolean isLast) {
        this.content = content;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.isLast = isLast;
    }

    public List<PurchaseResponseModel> getContent() {
        return content;
    }

    public void setContent(List<PurchaseResponseModel> content) {
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
