package com.projects.CoffeeWorld.controllers;

import com.projects.CoffeeWorld.models.PastOrder;
import com.projects.CoffeeWorld.models.RealOrder;
import com.projects.CoffeeWorld.models.User;
import com.projects.CoffeeWorld.repositories.MenuRepository;
import com.projects.CoffeeWorld.repositories.PastOrderRepository;
import com.projects.CoffeeWorld.repositories.RealOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/cardFood")
public class CardController {
    @Autowired
    private RealOrderRepository realOrderRepository;

    @Autowired
    private PastOrderRepository pastOrderRepository;

    @Autowired
    private MenuRepository menuRepository;

    @GetMapping
    public String getCardFood(
            @AuthenticationPrincipal User user,
            Model model) {

        RealOrder realOrder = new RealOrder();
        Map<Long, Integer> card = new HashMap<Long, Integer>();
        double totalCost = 0;
        double templeCost = 0;

        String messageForReal = "Вы заказали: ";
        String totalCostText = "Итоговая стоимость: ";
        boolean haveCart = true;


        if (realOrderRepository.findRealOrderByClientId(user.getId()) != null) {
            realOrder = realOrderRepository.findRealOrderByClientId(user.getId());
            if (!realOrder.getOrderMeal().isEmpty()) {
                card = realOrder.getOrderMeal();
                Set<Long> keysFoodInCard = card.keySet();
                for (Long foodKey : keysFoodInCard) {
                    messageForReal = messageForReal + card.get(foodKey).toString() + " шт. " +
                            menuRepository.findMenuByIdFood(foodKey).getNameFood() + "; ";
                    templeCost = menuRepository.findMenuByIdFood(foodKey).getCostFood() * card.get(foodKey);
                    totalCost = totalCost + templeCost;
                }
                totalCostText = totalCostText + totalCost;
                model.addAttribute("messageForReal", messageForReal);
                model.addAttribute("messageTotalCost", totalCostText);
                model.addAttribute("flagHaveCart", haveCart);
            } else {
                messageForReal = "Вы ничего не заказывали";
                totalCost = 0;
                model.addAttribute("messageForReal", messageForReal);
                model.addAttribute("messageTotalCost", totalCostText);
            }
        } else {
            messageForReal = "Вы ничего не заказывали";
            totalCost = 0;
            model.addAttribute("messageForReal", messageForReal);
            model.addAttribute("messageTotalCost", totalCostText);
        }

        PastOrder pastOrder = new PastOrder();
        Map<Long, Integer> pastCard = new HashMap<Long, Integer>();
        boolean noOrders = true;

        if (!pastOrderRepository.findPastOrderByClientId(user.getId()).isEmpty()) {
            List<PastOrder> pastOrders =  pastOrderRepository.findPastOrderByClientId(user.getId());
            model.addAttribute("pastOrders", pastOrders);
        } else {
            model.addAttribute("noPastOrders", noOrders);
        }

        PastOrder pastOrderForAdmin = new PastOrder();
        boolean noOrdersForAdmin = true;

        if (!pastOrderRepository.findPastOrderByIsDone(false).isEmpty()) {
            List<PastOrder> pastOrdersForAdmin = pastOrderRepository.findPastOrderByIsDone(false);
            model.addAttribute("pastOrdersForAdmin", pastOrdersForAdmin);
        } else {
            model.addAttribute("noPastOrdersForAdmin", noOrdersForAdmin);
        }

        return "cart";
    }

    @PostMapping("/acceptCart")
    public String acceptCart(
            @AuthenticationPrincipal User user,
            @RequestParam("orderCartText") String orderCartText,
            Model model) {

        RealOrder realOrder = new RealOrder();
        Map<Long, Integer> card = new HashMap<Long, Integer>();
        realOrder = realOrderRepository.findRealOrderByClientId(user.getId());
        realOrderRepository.delete(realOrder);
        PastOrder pastOrder = new PastOrder();
        pastOrder.setPastId(realOrder.getIdOrder());
        pastOrder.setPastOrderText(orderCartText);
        pastOrder.setClientId(user.getId());
        pastOrder.setDone(false);
        pastOrderRepository.save(pastOrder);
        return "redirect:/cardFood";

    }

    @PostMapping("/clearCart")
    public String acceptCart(
            @AuthenticationPrincipal User user,
            Model model) {

        RealOrder realOrder = new RealOrder();
        Map<Long, Integer> card = new HashMap<Long, Integer>();
        realOrder = realOrderRepository.findRealOrderByClientId(user.getId());
        realOrderRepository.delete(realOrder);
        return "redirect:/cardFood";
    }

    @PostMapping("/cancelCart")
    public String cancelCart(
            @AuthenticationPrincipal User user,
            @RequestParam("idPastOrder") Long idPastOrder,
            Model model) {

        PastOrder pastOrder = pastOrderRepository.findPastOrderByIdPastOrder(idPastOrder);
        pastOrderRepository.delete(pastOrder);
        return "redirect:/cardFood";
    }

    @PostMapping("/confirmCart")
    public String confirmCart(
            @AuthenticationPrincipal User user,
            @RequestParam("idPastOrder") Long idPastOrder,
            Model model) {

        PastOrder pastOrder = pastOrderRepository.findPastOrderByIdPastOrder(idPastOrder);
        pastOrder.setDone(true);
        pastOrderRepository.save(pastOrder);
        return "redirect:/cardFood";
    }

}
