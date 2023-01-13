package com.spring_proj.bookshop.dto;

import java.sql.Timestamp;

public interface PurchaseIdDateCustomerSurnameDto {
    Integer getPurchaseId();
    Timestamp getPurchaseDate();
    String getCustomerSurname();
}
