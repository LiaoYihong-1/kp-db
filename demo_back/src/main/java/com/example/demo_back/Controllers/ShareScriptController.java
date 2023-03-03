package com.example.demo_back.Controllers;

import com.example.demo_back.Dao.List_Script_User.ListScriptUserRepository;
import com.example.demo_back.Dao.List_Script_User.List_Script_User;
import com.example.demo_back.Dao.Script.*;
import com.example.demo_back.Dto.UniResponse;
import com.example.demo_back.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ShareScriptController {
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

    @ResponseBody
    @PostMapping("/share")
    public UniResponse main(HttpServletRequest request){
        UniResponse response = new UniResponse();
        response.setMessage("Success");
        response.setSuccess(true);
        Integer id;
        String type;
        Integer script;
        try{
            id = Integer.valueOf(request.getParameter("user_id"));
            script = Integer.valueOf(request.getParameter("shareid"));
            type = request.getParameter("type");
        }catch (Exception e){
            response.setMessage("Invalid input");
            response.setSuccess(false);
            return response;
        }
        if("condition".equalsIgnoreCase(type)){
            if(conditionalScriptRepository.findbyscriptid(script).size()!=1){
                response.setMessage("Invalid id");
                response.setSuccess(false);
                return response;
            }
            List_Script_User list_script_user = new List_Script_User();
            list_script_user.setUser_id(id);
            list_script_user.setScript_id(script);
            listScriptUserRepository.save(list_script_user);
        }else{
            if(scheduleScriptRepository.findbyscriptid(script).size()!=1){
                response.setMessage("Invalid id");
                response.setSuccess(false);
                return response;
            }
            List_Script_User list_script_user = new List_Script_User();
            list_script_user.setUser_id(id);
            list_script_user.setScript_id(script);
            listScriptUserRepository.save(list_script_user);
        }
        return response;
    }

}
