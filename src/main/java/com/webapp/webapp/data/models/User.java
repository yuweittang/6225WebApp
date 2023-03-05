package com.webapp.webapp.data.models;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String username;
    private String password;
    @CreatedDate
    private LocalDateTime account_created;
    @LastModifiedDate
    private LocalDateTime account_updated;

    public User() {

    }

    /**
     * @return Integer return the id
     */

    /**
     * @return Integer return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return String return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return String return the lastname
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    /**
     * @return String return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    // /**
    // * @return String return the password
    // */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return LocalDateTime return the account_created
     */
    public LocalDateTime getAccount_created() {
        return account_created;
    }

    /**
     * @param account_created the account_created to set
     */
    public void setAccount_created(LocalDateTime account_created) {
        this.account_created = account_created;
    }

    /**
     * @return LocalDateTime return the account_updated
     */
    public LocalDateTime getAccount_updated() {
        return account_updated;
    }

    /**
     * @param account_updated the account_updated to set
     */
    public void setAccount_updated(LocalDateTime account_updated) {
        this.account_updated = account_updated;
    }

}
