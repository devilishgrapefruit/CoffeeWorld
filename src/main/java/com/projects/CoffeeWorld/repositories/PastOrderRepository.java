package com.projects.CoffeeWorld.repositories;

import com.projects.CoffeeWorld.models.PastOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PastOrderRepository extends JpaRepository<PastOrder, Long> {
    List<PastOrder> findPastOrderByClientId(Long clientId);
    PastOrder findPastOrderByIdPastOrder(Long pastOrderId);
    List<PastOrder> findPastOrderByIsDone(boolean isDone);
}
