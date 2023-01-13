package com.spring_proj.bookshop.dto;

import java.sql.Timestamp;

public interface PurchaseDateQuantityCustomerSurnameDiscountBookTitleDto {
    Timestamp getPurchaseDate();
    Integer getPurchaseQuantity();
    String getCustomerSurname();
    Short getCustomerDiscount();`1
    String getBookTitle();
}
