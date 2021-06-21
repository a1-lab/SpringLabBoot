package com.example.readinglist.controller;

import com.example.readinglist.entity.Book;
import com.example.readinglist.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class ReadingListController {
    private BookRepository bookRepository;

    public ReadingListController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("{reader}")
    public String readersBook(@PathVariable("reader") String reader, Model model) {
        List<Book> readingList = bookRepository.findByReader(reader);
        if (null != readingList){
            model.addAttribute("books", readingList);
        }

        return "readingList";
    }

    @PostMapping("{reader}")
    public String addToReadingList(@PathVariable("reader") String reader, Book book){
        book.setReader(reader);
        bookRepository.save(book);
        return "redirect:/{reader}";
    }
}
