package com.example.demo_back;

import com.example.demo_back.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoBackApplicationTests {

    @Test
    void contextLoads() {
        String s = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxNnVzZXIiLCJleHAiOjE2OTk2NTIyODJ9.xH79fcDW3kUS9YXxUr5t07Knp_4L00alSBCtYFBCVZg";
        Claims claims = JwtUtils.parseToken(s);
        String userId = claims.getSubject();
        System.out.println(userId);
    }

}
