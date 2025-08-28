package com.example.healthplace.SpringServer.model.PriceModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PriceListDao {
    @Autowired
    private PriceListRepository repository;

    public PriceList savePriceList(PriceList priceList){
        PriceList priceList1 = PriceList.builder()
                .service(priceList.getService())
                .price(priceList.getPrice())
                .department(priceList.getDepartment())
                .discountLoyaltyCard(priceList.getDiscountLoyaltyCard())
                .discountMedicalRaport(priceList.getDiscountMedicalRaport())
                .build();
        return repository.save(priceList1);
    }

    public List<PriceList> getAllItems(){
        List<PriceList> lista = new ArrayList<>();
        Streamable.of(repository.findAll()).forEach(price -> {
            lista.add(price);
        });
        return  lista;
    }

    public List<String> getDistinctDepartment(){
        return repository.findDistinctDepartment();
    }

    public Map<String, List<String>> getDistinctServices(){
        Map<String, List<String>> services = new HashMap<>();
        List<PriceList> lista = getAllItems();
        for(PriceList item : lista){
            if(services.containsKey(item.getDepartment())){
                List<String> list = services.get(item.getDepartment());
                list.add(item.getService());
                services.put(item.getDepartment(), list);
            }else{
                List<String> list = new ArrayList<>();
                list.add(item.getService());
                services.put(item.getDepartment(), list);
            }
        }
        return  services;
    }

    public Map<String, Float> getPriceBD(String department, String service)
    {
        PriceList priceList = repository.findByDepartmentAndService(department, service);
        Map<String, Float> mapPrices = new HashMap<>();
        mapPrices.put("fullPrice", priceList.getPrice());
        mapPrices.put("discountTicket", (float) priceList.getDiscountMedicalRaport());
        mapPrices.put("discountCard", (float)priceList.getDiscountLoyaltyCard());
        return mapPrices;
    }
}
