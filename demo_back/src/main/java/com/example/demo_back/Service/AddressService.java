package com.example.demo_back.service;

import com.example.demo_back.dao.address.AddressJpa;
import com.example.demo_back.dao.address.AddressRepository;
import com.example.demo_back.dao.enums.City;
import com.example.demo_back.dao.enums.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;
    public AddressJpa getAddressById(Integer id){
        return addressRepository.findAddressJpaById(id).get(0);
    }
    public void addAddress(String country, String city,String street){
        AddressJpa a = new AddressJpa();
        a.setCity(City.valueOf(city));
        a.setCountry(Country.valueOf(country));
        a.setStreet(street);
        addressRepository.save(a);
    }

    public Integer findNewestId(){
        return addressRepository.findNewestAccount().get(0);
    }
}
