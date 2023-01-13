package com.spring_proj.bookshop.repository;

import com.spring_proj.bookshop.dto.CustomerSurnameDiscountDto;
import com.spring_proj.bookshop.entity.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("select distinct c.district from Customer c")
    List<String> findDistinctDistricts();

    @Query("select c.surname as surname, c.discount as discount from Customer c where c.district = :district")
    List<CustomerSurnameDiscountDto> findCustomerSurnameAndDiscountLivingIn(String district);

}
