package com.projects.CoffeeWorld.repositories;
import com.projects.CoffeeWorld.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>{
//    List<Menu> findMenuByTagFood(String tagFood);
    Menu findMenuByIdFood(Long idFood);
}
