package com.projects.CoffeeWorld.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Map;

@Entity
@Data
public class PastOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idPastOrder;
    private Long pastId;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<Long, Integer> orderMeal;
    private Long clientId;
    private boolean pickup;
    private String addressOrder;
    private String timeOrder;
    private boolean isDone;
    private String date;
    private String pastOrderText;

}