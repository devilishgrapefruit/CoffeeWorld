package com.projects.CoffeeWorld.controllers;

import com.projects.CoffeeWorld.models.Menu;
import com.projects.CoffeeWorld.models.RealOrder;
import com.projects.CoffeeWorld.models.User;
import com.projects.CoffeeWorld.repositories.MenuRepository;
import com.projects.CoffeeWorld.repositories.RealOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RealOrderRepository realOrderRepository;


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/delete/{menu}")
    public String deleteFood(
            @RequestParam("foodId") Menu menu,
            Model model) {
        Menu menuFind;
        menuFind = menuRepository.findMenuByIdFood(menu.getIdFood());
        if (menuFind == null)
            System.out.println("Menu null");
        menuRepository.delete(menuFind);
        return "redirect:/menu";
    }

    @PostMapping("/addToCart/{menu}")
    public String addToCart(@RequestParam("foodId") Menu menu, @AuthenticationPrincipal User user, Model model) {

        RealOrder realOrder = new RealOrder();
        Map<Long, Integer> card = new HashMap<Long, Integer>();

        if (realOrderRepository.findRealOrderByClientId(user.getId()) != null) {
            realOrder = realOrderRepository.findRealOrderByClientId(user.getId());
            if (!realOrder.getOrderMeal().isEmpty()) {
                card = realOrder.getOrderMeal();
                if (card.get(menu.getIdFood()) != null) {
                    int totalFood = card.get(menu.getIdFood());
                    totalFood += 1;
                    card.replace(menu.getIdFood(), totalFood);
                } else {
                    card.put(menu.getIdFood(), 1);
                }
            } else {
                card.put(menu.getIdFood(), 1);
            }
            realOrder.setOrderMeal(card);
            realOrderRepository.save(realOrder);
        } else {
            realOrder.setClientId(user.getId());
            card.put(menu.getIdFood(), 1);
            realOrder.setOrderMeal(card);
            realOrderRepository.save(realOrder);
        }

        return "redirect:/menu";
    }


}