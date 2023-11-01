package com.example.demo_back.service;

import com.example.demo_back.dao.account.AccountJpa;
import com.example.demo_back.dao.enums.ScriptType;
import com.example.demo_back.dao.listscriptuser.ListScriptUserRepository;
import com.example.demo_back.dao.listscriptuser.List_Script_User;
import com.example.demo_back.dao.script.*;
import com.example.demo_back.dto.UniResponse;
import com.example.demo_back.exception.InvalidConditionException;
import com.example.demo_back.exception.InvalidParameterException;
import com.example.demo_back.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ScriptService {
    @Autowired
    ScriptRepository scriptRepository;
    @Autowired
    ListScriptUserRepository listScriptUserRepository;
    @Autowired
    ConditionalScriptRepository conditionalScriptRepository;
    @Autowired
    ScheduleScriptRepository scheduleScriptRepository;
    @Autowired
    AccountService accountService;
    public boolean checkString(String s){
        if ((s == null) | (Objects.equals(s, ""))){
            return false;
        }
        return true;
    }

    public Script findById(Integer id) throws ResourceNotFoundException{
        if(scriptRepository.findById(id).isEmpty()){
            throw new ResourceNotFoundException("No such script");
        }
        return scriptRepository.findById(id).get();
    }
    public ResponseEntity<?> addScript(ScriptRequest scriptRequest) throws InvalidParameterException, InvalidConditionException, ResourceNotFoundException {
        UniResponse response = new UniResponse();
        response.setSuccess(true);
        response.setMessage("Success");
        String t = scriptRequest.getType();
        Integer userId = scriptRequest.getUserId();
        accountService.findAccountById(userId);
        if("conditional".equalsIgnoreCase(t)){
            String condition = scriptRequest.getCondition();
            if(!checkString(condition)){
                throw new InvalidConditionException("Check you set your condition or log in again");
            }
            Script script = new Script();
            script.setScriptType(ScriptType.CONDITIONAL);
            script.setCreatorName(accountService.findAccountById(userId).getName());
            ConditionalScript conditionalScript = new ConditionalScript();
            conditionalScript.setCondition(condition);
            scriptRepository.save(script);
            Integer scriptId = scriptRepository.findNewestScript().get(0);
            conditionalScript.setId(scriptId);
            conditionalScriptRepository.save(conditionalScript);
            List_Script_User listScriptUser = new List_Script_User();
            listScriptUser.setScriptId(scriptId);
            listScriptUser.setUserId(userId);
            listScriptUserRepository.save(listScriptUser);
        }else{
            String time = scriptRequest.getTime();
            System.out.println("Time is:");
            System.out.println(time);
            if(!checkString(time)){
                throw new InvalidConditionException("Check you set your input time or log in again");
            }
            Script script = new Script();
            script.setScriptType(ScriptType.SCHEDULE);
            script.setCreatorName(accountService.findAccountById(userId).getName());
            scriptRepository.save(script);
            ScheduleScript scheduleScript = new ScheduleScript();
            Integer scriptId = scriptRepository.findNewestScript().get(0);
            scheduleScript.setId(scriptId);
            Time time1 = new Time(0);
            String [] hm = scriptRequest.getTime().split(":");
            time1.setHours(Integer.parseInt(hm[0]));
            time1.setMinutes(Integer.parseInt(hm[1]));
            scheduleScript.setTime(time1);
            scheduleScriptRepository.save(scheduleScript);
            List_Script_User list_script_user = new List_Script_User();
            list_script_user.setUserId(scriptId);
            list_script_user.setId(userId);
            listScriptUserRepository.save(list_script_user);
        }
        return ResponseEntity.ok(response);
    }
    public ResponseEntity<?> getConditional(Integer id){
        List<ConditionalScript> scripts = new ArrayList<>();
        for(Script s : accountService.findAccountById(id).getScripts()){
            if(s.getConditionalScript()!=null){
                scripts.add(s.getConditionalScript());
            }
        }
        return ResponseEntity.ok(scripts);
    }

    public ResponseEntity<?> getSchedule(Integer id){
        List<ScheduleScript> scripts = new ArrayList<>();
        for(Script s : accountService.findAccountById(id).getScripts()){
            if(s.getScheduleScript()!=null){
                scripts.add(s.getScheduleScript());
            }
        }
        return ResponseEntity.ok(scripts);
    }

    public ResponseEntity<?> getScripts(Integer id){
        AccountJpa a = accountService.findAccountById(id);
        return ResponseEntity.ok(a.getScripts());
    }
    public ResponseEntity<?> share(Integer script, Integer from, Integer to){
        accountService.findAccountById(from);
        accountService.findAccountById(to);
        findById(script);
        List_Script_User listScriptUser = new List_Script_User();
        listScriptUser.setScriptId(script);
        listScriptUser.setUserId(to);
        listScriptUserRepository.save(listScriptUser);
        return ResponseEntity.ok(listScriptUser);
    }
}
