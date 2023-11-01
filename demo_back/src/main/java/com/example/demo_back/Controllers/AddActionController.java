package com.example.demo_back.controllers;

import com.example.demo_back.dao.action.ActionJpa;
import com.example.demo_back.dao.action.ActionRepository;
import com.example.demo_back.dao.enums.ActionType;
import com.example.demo_back.dao.enums.FurnitureType;
import com.example.demo_back.dao.house.FunitureRepository;
import com.example.demo_back.dao.house.FurnitureJpa;
import com.example.demo_back.dao.listscriptaction.ListActionScriptRepository;
import com.example.demo_back.dao.listscriptaction.ListActionScript;
import com.example.demo_back.dao.script.*;
import com.example.demo_back.dto.UniResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AddActionController {
    @Autowired
    ScriptRepository scriptRepository;
    @Autowired
    FunitureRepository funitureRepository;
    @Autowired
    ListActionScriptRepository listActionScriptRepository;
    @Autowired
    ActionRepository actionRepository;

    @ResponseBody
    @PostMapping("/addaction")
    public UniResponse main(HttpServletRequest request){
        UniResponse response = new UniResponse();
        response.setMessage("Success");
        response.setSuccess(true);
        Integer script_id = Integer.valueOf(request.getParameter("script_id"));
        Integer furniture_id = Integer.valueOf(request.getParameter("target"));
        ActionType action_type = ActionType.valueOf(request.getParameter("action_type").toUpperCase());
        FurnitureJpa f = funitureRepository.findFurnitureJpaById(furniture_id).get(0);
        FurnitureType furniture_type = f.getFurnitureType();
        System.out.println(furniture_type);
        System.out.println(action_type);
        try {
            ActionJpa actionJpa = actionRepository.findAllByActionAndFurnitrue(action_type, furniture_type).get(0);
            ListActionScript list_action_script = new ListActionScript();
            list_action_script.setAction_id(actionJpa.getId());
            list_action_script.setScript_id(script_id);
            list_action_script.setFurniture_id(furniture_id);
            listActionScriptRepository.save(list_action_script);
            return response;
        }catch (IndexOutOfBoundsException i){
            response.setSuccess(false);
            response.setMessage("Make sure that you choose proper action type for furniture.Read guider carefully please.");
            return response;
        }
    }
}
