package com.newDemom.BudgetApplication.Exception;

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
}
