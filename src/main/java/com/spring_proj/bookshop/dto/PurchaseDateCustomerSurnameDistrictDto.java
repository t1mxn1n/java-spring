package com.spring_proj.bookshop.dto;

import java.sql.Timestamp;

public interface PurchaseDateCustomerSurnameDistrictDto {
    Timestamp getPurchaseDate();
    String getCustomerSurname();
    String getCustomerDistrict();
}
