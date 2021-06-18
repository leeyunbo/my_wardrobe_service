package com.cloth.wardrobe.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/wardrobes/save")
    public String wardrobesSave() {
        return "wardrobe/wardrobes-save";
    }

    @GetMapping("/wardrobes")
    public String wardrobes() {
        return "wardrobe/wardrobes-list";
    }
}
