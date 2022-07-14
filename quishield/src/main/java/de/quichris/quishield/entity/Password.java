package de.quichris.quishield.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;






@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Password {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column
    private String id;

    @Column
    private String password;


    @Column
    private String email;

    @Column
    private String username;

}
