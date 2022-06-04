package com.projects.CoffeeWorld.controllers;

import com.projects.CoffeeWorld.models.Menu;
import com.projects.CoffeeWorld.models.User;
import com.projects.CoffeeWorld.repositories.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class OrderController {
    @Value("${data.path}")
    private String uploadPath;

    @Autowired
    private MenuRepository menuRepository;

    @GetMapping("/")
    public String menu(Model model) {
        return "menu";
    }


    @GetMapping("/menu")
    public String filterByType(@RequestParam(required = false) String filterType, Model model) {

        List<Menu> menus = menuRepository.findAll();
        if (filterType != null) {
            List<Menu> filtredMenus = new ArrayList<Menu>();
            for (Menu curM : menus) {
                if (curM.getTypeFood().toLowerCase().contains(filterType.toLowerCase()))
                    filtredMenus.add(curM);
            }
            if (filtredMenus.isEmpty()) {
                filtredMenus = menus;
                System.out.println("Food with " + filterType + " didn't found");
                model.addAttribute("filterError", "Food with " + filterType + " didn't found");
                filterType = "";
            }
            menus = filtredMenus;
            System.out.println("Filtred menus is + " + filtredMenus.toString());
        }
        model.addAttribute("filterTag", filterType);
        model.addAttribute("foods",menus);
        return "home";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/menu")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam String nameFood,
                      @RequestParam Double costFood,
                      @RequestParam String typeFood,
                      @RequestParam("file") MultipartFile file,
                      @RequestParam (required = false) boolean isHave,
                      Model model) throws IOException {
        Menu menu = new Menu(nameFood,costFood,typeFood,user,isHave);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            menu.setFilename(resultFilename);
        }
        menuRepository.save(menu);
        Iterable<Menu> menus = menuRepository.findAll();
        model.addAttribute("foods",menus);
        return "home";
    }

}
