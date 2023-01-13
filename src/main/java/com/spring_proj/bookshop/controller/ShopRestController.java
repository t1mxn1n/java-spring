package com.spring_proj.bookshop.controller;

import com.spring_proj.bookshop.dto.ShopCreateEditDto;
import com.spring_proj.bookshop.dto.ShopReadDto;
import com.spring_proj.bookshop.entity.Shop;
import com.spring_proj.bookshop.exception.ParameterFormatException;
import com.spring_proj.bookshop.exception.ResourceNotFoundException;
import com.spring_proj.bookshop.response.DeleteResponse;
import com.spring_proj.bookshop.service.ShopService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shops")
@RequiredArgsConstructor
public class ShopRestController {

    private final ShopService shopService;

    @GetMapping
    public List<ShopReadDto> findAll() {
        return shopService.findAll();
    }

    @GetMapping("/{id}")
    public ShopReadDto findById(@PathVariable Long id) {
        return shopService.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No shop with ID = " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShopReadDto create(@RequestBody ShopCreateEditDto shop) {
        return shopService.create(shop);
    }

    @PutMapping("/{id}")
    public ShopReadDto updateById(@PathVariable Long id, @RequestBody ShopCreateEditDto shop) {
        return shopService.update(id, shop)
            .orElseThrow(() -> new ResourceNotFoundException("No shop with ID = " + id));
    }

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    public ShopReadDto patchById(@PathVariable Long id, @RequestBody JsonPatch patch) {
        return shopService.patch(id, patch);
    }

    @DeleteMapping("/{id}")
    public DeleteResponse delete(@PathVariable Long id) {
        if (shopService.delete(id)) {
            return new DeleteResponse("Shop with ID = " + id + " was deleted");
        } else {
            throw new ResourceNotFoundException("No shop with ID = " + id);
        }
    }

    @GetMapping("/names-in-districts")
    public List<String> findShopsNameOfDistricts(@RequestParam String[] districts) {
        return shopService.findShopsNameOfDistricts(districts);
    }

    @GetMapping("/where-customers-have-discount-between-and-not-in-district")
    public List<Shop> findShopsWhereCustomersHaveDiscountBetweenExcludingDistrict(
        @RequestParam Short discountFrom, @RequestParam Short discountTo,
        @RequestParam String excludedDistrict) {
        if (excludedDistrict.length() == 0) {
            throw new ParameterFormatException("Parameter excludedDistrict cannot be empty.");
        }
        return shopService.findShopsWhereCustomersHaveDiscountBetweenExcludingDistrict(discountFrom,
            discountTo, excludedDistrict);
    }
}
