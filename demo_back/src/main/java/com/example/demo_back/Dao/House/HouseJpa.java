package com.example.demo_back.dao.house;

import com.example.demo_back.dao.account.AccountJpa;
import com.example.demo_back.dao.address.AddressJpa;
import com.example.demo_back.dao.contact.ContactJpa;
import com.example.demo_back.dao.enums.HouseType;
import com.example.demo_back.dao.enums.PostgreSQLEnumType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "House")
@TypeDef(name="pgsql_enum",typeClass = PostgreSQLEnumType.class)
public class HouseJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false,name="type")
    @Enumerated(EnumType.STRING)
    @Type(type = "pgsql_enum")
    private HouseType houseType;
    @Column(nullable = false,name="address_id")
    @JsonIgnore
    private Integer addressId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id",referencedColumnName = "address_id")
    private AddressJpa address;
    @ManyToMany(cascade = CascadeType.REFRESH,mappedBy = "houses",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AccountJpa> users;
}
