package de.quichris.quishield.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Collection;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(unique = true)
    private String id;


    @Column(unique = true)
    private String username;

    @Column
    private String profilpicture;

    @Column(unique = true)
    private String email;


    @Column(unique = true)
    private String token;

    @Column
    private String password;


}
