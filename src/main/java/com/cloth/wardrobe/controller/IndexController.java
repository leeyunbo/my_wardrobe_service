package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.service.WardrobeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final WardrobeService wardrobeService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/wardrobes/save")
    public String wardrobesSave() {
        return "wardrobe/wardrobes-save";
    }

    @GetMapping("/wardrobes")
    public String wardrobes(Model model, @RequestParam(name = "page_number") int page) {
        Pageable pageable = PageRequest.of(page-1,10);
        model.addAttribute("wardrobes", wardrobeService.findAll(pageable));
        return "wardrobe/wardrobes-list";
    }

    @GetMapping("/wardrobes/{id}")
    public String wardrobeById(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("wardrobe", wardrobeService.findById(id));
        return "wardrobe/wardrobe-detail-view";
    }
}
