package com.example.healthplace.SpringServer.controller;

import com.example.healthplace.SpringServer.model.PriceModel.PriceListDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
public class PriceListController {
    @Autowired
    PriceListDao priceListDao;

    @GetMapping("/getDepartments")
    public List<String> getAllDepartments(){
        return priceListDao.getDistinctDepartment();
    }
    @GetMapping("/getServices")
    public Map<String, List<String>> getAllServices(){
        return priceListDao.getDistinctServices();
    }

    @PostMapping("/getPrices")
    public Map<String, Float> getFullPrice(@RequestParam("department") String department, @RequestParam("service") String service){
        return priceListDao.getPriceBD(department, service);
    }
}
