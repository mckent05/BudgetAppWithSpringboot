package com.newDemom.BudgetApplication.Domain;

import java.util.Date;

public class ErrorDetails {

    private Date timeStamp;

    private String errorMessage;

    private String details;

    public ErrorDetails(Date timeStamp, String errorMessage, String details) {
        this.timeStamp = timeStamp;
        this.errorMessage = errorMessage;
        this.details = details;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getDetails() {
        return details;
    }
}
