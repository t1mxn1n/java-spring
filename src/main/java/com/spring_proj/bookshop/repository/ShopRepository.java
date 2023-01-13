package com.spring_proj.bookshop.repository;

import com.spring_proj.bookshop.entity.Shop;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    @Query("select s.name from Shop s where s.district in :districts")
    List<String> findShopsNameOfDistricts(String[] districts);

    @Query("select distinct s"
        + " from Purchase p join p.shop s join p.customer c"
        + " where c.discount between :discountFrom and :discountTo"
        + "       and s.district <> :excludedDistrict")
    List<Shop> findShopsWhereCustomersHaveDiscountBetweenExcludingDistrict(short discountFrom, short discountTo, String excludedDistrict);

}
