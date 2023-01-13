package com.spring_proj.bookshop.repository;

import com.spring_proj.bookshop.dto.PurchaseCustomerSurnameShopNameDto;
import com.spring_proj.bookshop.dto.PurchaseDateCustomerSurnameDistrictDto;
import com.spring_proj.bookshop.dto.PurchaseDateQuantityCustomerSurnameDiscountBookTitleDto;
import com.spring_proj.bookshop.dto.PurchaseIdDateCustomerSurnameDto;
import com.spring_proj.bookshop.dto.PurchaseQuantityBookTitlePriceRepoDto;
import com.spring_proj.bookshop.entity.Purchase;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("select distinct p.date from Purchase p")
    List<Timestamp> findDistinctDates();

    @Query("select c.surname as customerSurname, s.name as shopName from Purchase p"
        + " join p.customer c"
        + " join p.shop s")
    List<PurchaseCustomerSurnameShopNameDto> findCustomerSurnameAndShopNameForEachPurchase();

    @Query("select"
        + " p.date as purchaseDate, p.quantity as purchaseQuantity,"
        + " c.surname as customerSurname, c.discount as customerDiscount,"
        + " b.title as bookTitle"
        + " from Purchase p"
        + " join p.customer c"
        + " join p.book b")
    List<PurchaseDateQuantityCustomerSurnameDiscountBookTitleDto> findDateQuantityDiscountSurnameTitle();

    @Query("select"
        + " p.id as purchaseId, p.date as purchaseDate,"
        + " c.surname as customerSurname"
        + " from Purchase p "
        + " join p.customer c"
        + " where p.totalPrice > :total")
    List<PurchaseIdDateCustomerSurnameDto> findPurchasesIdDateCustomerSurnameWhereTotalPriceMoreThan(float total);

    @Query("select"
        + " p.date as purchaseDate, c.surname as customerSurname, c.district as customerDistrict"
        + " from Purchase p"
        + " join p.customer c"
        + " join p.shop s"
        + " where p.date > :date and c.district = s.district"
        + " order by p.date")
    List<PurchaseDateCustomerSurnameDistrictDto> findPurchasesInCustomerDistrictAfter(Timestamp date);

    @Query("select"
        + " b.title as bookTitle, b.price as bookPrice, b.repo as bookRepo,"
        + " p.quantity as purchaseQuantity"
        + " from Purchase p"
        + " join p.shop s"
        + " join p.book b"
        + " where s.district = b.repo and b.quantity > :storedQuantity"
        + " order by b.price")
    List<PurchaseQuantityBookTitlePriceRepoDto> findBoughtBooksInSameDistrictAsRepoWhereStoredQuantityMoreThan(int storedQuantity);

}
