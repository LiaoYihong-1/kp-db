package com.example.demo_back.service;

import com.example.demo_back.dto.UniResponse;
import com.example.demo_back.dao.action.ActionJpa;
import com.example.demo_back.dao.action.ActionRepository;
import com.example.demo_back.dao.enums.ActionType;
import com.example.demo_back.dao.enums.FurnitureType;
import com.example.demo_back.dao.house.FunitureRepository;
import com.example.demo_back.dao.house.FurnitureJpa;
import com.example.demo_back.dao.listscriptaction.ListActionScript;
import com.example.demo_back.dao.listscriptaction.ListActionScriptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ListActionScriptService {
    @Autowired
    private ListActionScriptRepository listActionScriptRepository;
    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private FunitureRepository funitureRepository;
    public ResponseEntity<?> addAction(Integer scriptId, Integer furnitureId, ActionType actionType){
        UniResponse response = new UniResponse();
        response.setMessage("Success");
        response.setSuccess(true);

        FurnitureJpa f = funitureRepository.findFurnitureJpaById(furnitureId).get(0);
        FurnitureType furnitureType = f.getFurnitureType();
        try {
            ActionJpa actionJpa = actionRepository.findAllByActionAndFurnitrue(actionType, furnitureType).get(0);
            ListActionScript listActionScript = new ListActionScript();
            listActionScript.setAction_id(actionJpa.getId());
            listActionScript.setScript_id(scriptId);
            listActionScript.setFurniture_id(furnitureId);
            listActionScriptRepository.save(listActionScript);
            return ResponseEntity.ok(response);
        }catch (IndexOutOfBoundsException i){
            return ResponseEntity.notFound().build();
        }
    }
}
