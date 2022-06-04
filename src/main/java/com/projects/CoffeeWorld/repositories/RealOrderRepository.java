package com.projects.CoffeeWorld.repositories;


import com.projects.CoffeeWorld.models.RealOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealOrderRepository extends JpaRepository<RealOrder, Long> {
    RealOrder findRealOrderByClientId(Long clientId);
}
