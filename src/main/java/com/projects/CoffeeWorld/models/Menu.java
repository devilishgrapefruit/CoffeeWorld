package com.projects.CoffeeWorld.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "menu")
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idFood;
    @Column(name = "name_food")
    private String nameFood;
    @Column(name = "cost_food")
    private Double costFood;
    @Column(name = "type_food")
    private String typeFood;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    @Column(name = "is_have")
    private boolean isHave;
    @Column(name = "filename")
    public String filename;

    public String getAuthorName() {
        return author != null ? author.getUsername() : "undefined";
    }

    public Menu(String nameFood, Double costFood, String typeFood, User author, boolean isHave) {
        this.nameFood = nameFood;
        this.costFood = costFood;
        this.typeFood = typeFood;
        this.author = author;
        this.isHave = isHave;
    }

    public Menu() {
    }
}
