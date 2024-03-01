import React, {useEffect, useState} from "react";
import Container from "@material-ui/core/Container";
import AccountBoxIcon from '@material-ui/icons/AccountBox';
import { positions } from '@material-ui/system';
import Box from '@material-ui/core/Box';
import Typography from "@material-ui/core/Typography";
import $ from "jquery";
import {createBrowserHistory} from "history";

export default function MainPerson(props){
    const [state,setState] = useState({
        age: '',
        gender: '',
        username: '',
        phone:'',
        email:'',
        getState:false
    })
    function getPersonalData(token){
        $.ajax({
                url: "api/user",
                method:"GET",
                headers: {
                    'Content-Type': 'application/json', // 设置内容类型为 JSON
                    'token': window.sessionStorage.getItem("token") // 设置授权头
                    // 可以添加其他自定义请求头
                },
                async:false,
                success:function (res){
                    setState({
                        age: res.age,
                        gender: res.gender,
                        username: res.name,
                        phone:res.contactJpa.phone,
                        email:res.contactJpa.email,
                        getState: true
                    })
                },
                error:function (){
                    alert("Make sure your account available")
                }
            }
        );
    }

    useEffect(() => {
        if(!state.getState) {
            getPersonalData(window.sessionStorage.getItem("token"));
        }
    });

    return(
        <Container >
            <Typography>
                Name:{state.username}
            </Typography>
            <Typography>
                Gender:{state.gender}
            </Typography>
            <Typography>
                Age:{state.age}
            </Typography>
            <Typography>
                Phone:{state.phone}
            </Typography>
            <Typography>
                Email:{state.email}
            </Typography>
        </Container>
    )
}