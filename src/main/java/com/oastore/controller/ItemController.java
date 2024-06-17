package com.oastore.controller;

import com.oastore.mapper.BookStorageMapper;
import com.oastore.pojo.Bookstorage;
import com.oastore.service.BookStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ItemController {
    @Autowired
    BookStorageService bookStorageService;
    @Autowired
    BookStorageMapper bookStorageMapper;
    @RequestMapping("/item")
    public String bookreturn(){
        return "addbook";
    }
    @RequestMapping("/addItem")
    public String itemBook(Bookstorage bookstorage, Model model){
        bookStorageMapper.insertItem(bookstorage);
        List<Bookstorage> bookstorages = bookStorageMapper.selectAll();
        model.addAttribute("items",bookstorages);
        return "bookstorage";
    }
    @PostMapping("/bookstorage/ItemDel")
    public String Del( String name, Model model){
        bookStorageMapper.delete(name);
        List<Bookstorage> bookstorages =bookStorageMapper.selectAll();
        model.addAttribute("items",bookstorages);
        return "redirect:/bookstorage";
    }
}
