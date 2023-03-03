package com.example.demo_back.Controllers;

import com.example.demo_back.Dao.Account.AccountRepository;
import com.example.demo_back.Dao.House.HouseRepository;
import com.example.demo_back.Dao.List_Script_User.List_Script_User;
import com.example.demo_back.Dao.List_User_House.ListUserHouseJpa;
import com.example.demo_back.Dao.List_User_House.ListUserHouseRepository;
import com.example.demo_back.Dto.UniResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ShareHouseController {
    @Autowired
    ListUserHouseRepository listUserHouseRepository;
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    AccountRepository accountRepository;

    @ResponseBody
    @PostMapping("/shareHouse")
    public UniResponse main(HttpServletRequest request){
        UniResponse response = new UniResponse();
        response.setMessage("Success");
        response.setSuccess(true);
        Integer id;
        Integer house;
        try{
            id = Integer.valueOf(request.getParameter("user_id"));
            house = Integer.valueOf(request.getParameter("shareid"));
        }catch (Exception e){
            response.setMessage("Invalid input");
            response.setSuccess(false);
            return response;
        }
        if(houseRepository.findByShareid(house).size()!=1){
            response.setMessage("Invalid id");
            response.setSuccess(false);
            return response;
        }
        ListUserHouseJpa listUserHouseJpa = new ListUserHouseJpa();
        listUserHouseJpa.setUser_id(id);
        listUserHouseJpa.setHouse_id(house);
        listUserHouseRepository.save(listUserHouseJpa);
        return response;
    }
}
