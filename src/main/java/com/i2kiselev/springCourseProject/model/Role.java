package com.i2kiselev.springCourseProject.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class Role implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 1L;

    private String role;

    public Role(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public void setAuthority(String role) {
        this.role = role;
    }
}
