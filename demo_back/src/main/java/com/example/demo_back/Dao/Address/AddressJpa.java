package com.example.demo_back.dao.address;

import com.example.demo_back.dao.enums.City;
import com.example.demo_back.dao.enums.Country;
import com.example.demo_back.dao.house.HouseJpa;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Address")
public class AddressJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,name="country")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private Country country;
    @Column(nullable = false,name="city")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private City city;
    @Column(nullable = false,name="street")
    private String street;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id",referencedColumnName = "address_id")
    private HouseJpa houseJpa;
}