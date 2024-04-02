package com.newDemom.BudgetApplication.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private long fieldValue;

    private String fieldName;

    private String resourceName;

    public ResourceNotFoundException
            (long fieldValue,
             String fieldName,
             String resourceName) {
        super(String.format("%s with %s: %s not found", resourceName,
                fieldName, fieldValue));
        this.fieldValue = fieldValue;
        this.fieldName = fieldName;
        this.resourceName = resourceName;
    }

    public long getFieldValue() {
        return fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getResourceName() {
        return resourceName;
    }
}
