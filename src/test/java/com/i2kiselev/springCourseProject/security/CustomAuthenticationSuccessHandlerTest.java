package com.i2kiselev.springCourseProject.security;

import com.i2kiselev.springCourseProject.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static com.i2kiselev.springCourseProject.security.CustomAuthenticationSuccessHandler.ADMIN_HOME_URL;
import static com.i2kiselev.springCourseProject.security.CustomAuthenticationSuccessHandler.CLIENT_HOME_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomAuthenticationSuccessHandlerTest {

    @Test
    void getTargetURL_admin(){
        //setup
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new Role("ROLE_ADMIN"));
        TestingAuthenticationToken authentication = new TestingAuthenticationToken(null, null, roles);

        //act
        String result = CustomAuthenticationSuccessHandler.getTargetURL(authentication);

        //verify
        assertEquals(ADMIN_HOME_URL, result);
    }

    @Test
    void getTargetURL_client(){
        //setup
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new Role("ROLE_USER"));
        TestingAuthenticationToken authentication = new TestingAuthenticationToken(null, null, roles);

        //act
        String result = CustomAuthenticationSuccessHandler.getTargetURL(authentication);

        //verify
        assertEquals(CLIENT_HOME_URL, result);
    }

    @Test
    void getTargetURL_invalidRole(){
        //setup
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new Role("invalid role"));
        TestingAuthenticationToken authentication = new TestingAuthenticationToken(null, null, roles);

        //act&&verify
        assertThrows(IllegalStateException.class, () -> CustomAuthenticationSuccessHandler.getTargetURL(authentication));
    }
}