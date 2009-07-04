/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring66.petclinic.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OrderBy;
import org.springframework.core.style.ToStringCreator;

/**
 *
 * @author TwinP
 */
@Entity
@Table(name = "owners")
public class Owner extends Person {

    private String identityNumber;
    private String address;
    private String city;
    private String telephone;
    private List<Pet> pets;

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone the telephone to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return the pets
     */
    @OneToMany(mappedBy = "owner", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @OrderBy(clause = "name desc")
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<Pet> getPets() {
        if (this.pets == null) {
            this.pets = new ArrayList<Pet>();
        }
        return this.pets;
    }

    /**
     * @param pets the pets to set
     */
    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public void addPet(Pet pet) {
        pet.setOwner(this);
        this.getPets().add(pet);
    }

    /**
     * @return the identityNumber
     */
    public String getIdentityNumber() {
        return identityNumber;
    }

    /**
     * @param identityNumber the identityNumber to set
     */
    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    protected void setPetsInternal(List<Pet> pets) {
        this.pets = pets;
    }
    
    @Transient
    protected List<Pet> getPetsInternal() {
        if (this.pets == null) {
            this.pets = new ArrayList<Pet>();
        }
        return this.pets;
    }
    
    public Pet getPet(String name) {
        return getPet(name, false);
    }

    /**
     * Return the Pet with the given name, or null if none found for this Owner.
     *
     * @param name to test
     * @return true if pet name is already in use
     */
    public Pet getPet(String name, boolean ignoreNew) {
        name = name.toLowerCase();
        for (Pet pet : getPetsInternal()) {
            if (!ignoreNew || !pet.isNew()) {
                String compName = pet.getName();
                compName = compName.toLowerCase();
                if (compName.equals(name)) {
                    return pet;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", this.getId()).append("new", this.isNew()).append("lastName", this.getLastName()).append("firstName", this.getFirstName()).append("address", this.address).append("city", this.city).append("telephone", this.telephone).toString();
    }
}
