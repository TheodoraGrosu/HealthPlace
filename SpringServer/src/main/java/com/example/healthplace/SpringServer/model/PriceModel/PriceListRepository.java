package com.example.healthplace.SpringServer.model.PriceModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Integer> {
    @Query("SELECT DISTINCT department FROM PriceList")
    List<String> findDistinctDepartment();
    PriceList findByDepartmentAndService(String department, String service);
}
