package com.spring_proj.bookshop.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.spring_proj.bookshop.dto.BookCreateEditDto;
import com.spring_proj.bookshop.dto.BookReadDto;
import com.spring_proj.bookshop.dto.BookTitlePriceDto;
import com.spring_proj.bookshop.exception.JsonConvertationException;
import com.spring_proj.bookshop.mapper.BookCreateEditMapper;
import com.spring_proj.bookshop.mapper.BookReadMapper;
import com.spring_proj.bookshop.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final BookReadMapper bookReadMapper;
    private final BookCreateEditMapper bookCreateEditMapper;
    ObjectMapper objectMapper = new ObjectMapper();

    public List<BookReadDto> findAll() {
        return bookRepository.findAll()
            .stream()
            .map(bookReadMapper::map)
            .collect(Collectors.toList());
    }

    public Optional<BookReadDto> findById(long id) {
        return bookRepository.findById(id)
            .map(bookReadMapper::map);
    }

    @Transactional
    public BookReadDto create(BookCreateEditDto bookDto) {
        return Optional.of(bookDto)
            .map(bookCreateEditMapper::map)
            .map(bookRepository::save)
            .map(bookReadMapper::map)
            .orElseThrow();
    }

    @Transactional
    public Optional<BookReadDto> update(long id, BookCreateEditDto bookDto) {
        return bookRepository.findById(id)
            .map(entity -> bookCreateEditMapper.map(bookDto, entity))
            .map(bookRepository::saveAndFlush)
            .map(bookReadMapper::map);
    }

    @Transactional
    public BookReadDto patch(long id, JsonPatch patch) throws JsonConvertationException {
        BookCreateEditDto bookCreateEditDto = bookRepository.findById(id)
            .map(bookCreateEditMapper::map)
            .orElseThrow(() -> new ResourceNotFoundException("No book with ID = " + id));

        try {
            JsonNode patched = patch.apply(
                objectMapper.convertValue(bookCreateEditDto, JsonNode.class));
            bookCreateEditDto = objectMapper.treeToValue(patched, BookCreateEditDto.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new JsonConvertationException(e.getMessage());
        }

        return update(id, bookCreateEditDto).get();
    }

    @Transactional
    public boolean delete(long id) {
        return bookRepository.findById(id)
            .map(entity -> {
                bookRepository.delete(entity);
                bookRepository.flush();
                return true;
            }).orElse(false);
    }

    public List<BookTitlePriceDto> findDistinctTitlesAndPrices() {
        return bookRepository.findDistinctTitlesAndPrices();
    }

    public List<BookTitlePriceDto> findBooksContainingWordOrCostsMoreThan(String word, int price) {
        return bookRepository.findBooksContainingWordOrCostsMoreThan(word, price);
    }

}
