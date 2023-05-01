package com.managementapp.managementapplication.ui.response;

public class OperationStatusModel {
    private String operationName;
    private String operationStatus;

    public OperationStatusModel(String operationName, String operationStatus) {
        this.operationName = operationName;
        this.operationStatus = operationStatus;
    }

    public String getOperationName() {
        return operationName;
    }

    public String getOperationStatus() {
        return operationStatus;
    }
}
