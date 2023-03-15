package com.cs.springbootjavafx.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 45)
    @Column(name = "firstname", length = 45)
    private String firstname;

    @Size(max = 45)
    @Column(name = "lastname", length = 45)
    private String lastname;

    @Size(max = 145)
    @Column(name = "address1", length = 145)
    private String address1;

    @Size(max = 145)
    @Column(name = "address2", length = 145)
    private String address2;

    @Column(name = "zip")
    private Integer zip;

    @Size(max = 45)
    @Column(name = "city", length = 45)
    private String city;

    @Size(max = 45)
    @Column(name = "telphone", length = 45)
    private String telphone;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "creditcardnumber")
    private Long creditcardnumber;

    @Column(name = "password")
    private Integer password;

    public User(){
    }

    public User(String firstname, String lastname, String address1, String address2, Integer zip, String city, String telphone, String email, Long creditcardnumber, Integer password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address1 = address1;
        this.address2 = address2;
        this.zip = zip;
        this.city = city;
        this.telphone = telphone;
        this.email = email;
        this.creditcardnumber = creditcardnumber;
        this.password = password;
    }
}