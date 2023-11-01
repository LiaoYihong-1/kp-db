package com.example.demo_back.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashSet;

@XmlRootElement(name="Users")
@Component
@Data
public class SecurityAccountSet {

    private HashSet<SecurityAccount> users = new HashSet<>();

    @XmlElement(name = "User")
    public void setUsers(HashSet<SecurityAccount> users) {
        this.users = users;
    }
}