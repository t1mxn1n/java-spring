package com.spring_proj.bookshop.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class CustomerReadDto {
    Long id;
    String surname;
    String district;
    Short discount;

    @JsonCreator
    public CustomerReadDto(@JsonProperty("id") Long id, @JsonProperty("surname") String surname,
        @JsonProperty("district") String district, @JsonProperty("discount") Short discount) {
        this.id = id;
        this.surname = surname;
        this.district = district;
        this.discount = discount;
    }
}
