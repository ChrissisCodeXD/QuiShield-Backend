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
    @Column
    private String id;



    @Column
    private String username;

    @Column
    private String profilpicture;

    @Column
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Password> passwords = new ArrayList<>();


    @Column
    private String password;


}
