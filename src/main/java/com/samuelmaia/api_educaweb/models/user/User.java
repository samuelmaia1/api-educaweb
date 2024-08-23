package com.samuelmaia.api_educaweb.models.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;

@Table(name = "users")
@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(unique = true)
    private String login;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String type;

    private String password;

    public User(UserRequestPost data){
        this.email = data.email();
        this.login = data.login();
        this.password = data.password();
        this.type = data.type();
    }
}
