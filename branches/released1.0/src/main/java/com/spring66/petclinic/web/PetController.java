/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring66.petclinic.web;

import com.spring66.petclinic.entity.Owner;
import com.spring66.petclinic.entity.Pet;
import com.spring66.petclinic.entity.PetType;
import com.spring66.petclinic.service.Clinic;
import com.spring66.petclinic.validator.PetValidator;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import static com.spring66.petclinic.web.ViewUtils.getRedirectForOwner;
import static com.spring66.petclinic.web.ViewUtils.PET_OBJ_NAME;
/**
 *
 * @author twinp
 */
@Controller
@SessionAttributes(PET_OBJ_NAME)
public class PetController {

    private final Clinic clinic;

    @Autowired
    public PetController(Clinic clinic) {
        this.clinic = clinic;
    }

    @ModelAttribute
    public Collection<PetType> populatePetTypes() {
        return this.clinic.getPetTypes();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Pet form(@RequestParam(required = false) Integer ownerId, @RequestParam(required = false) Integer id) {
        System.out.println("PetController GET");
        if (id != null) {
            Pet pet = this.clinic.loadPet(id);
            System.out.println("PET->"+pet.getOwner().getFirstName());
            return pet;
        } else {
            Owner owner = this.clinic.loadOwner(ownerId);
            Pet pet = new Pet();
            owner.addPet(pet);
            return pet;
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public String form(@ModelAttribute Pet pet, BindingResult result, SessionStatus status) {
        System.out.println("PetController POST");
        new PetValidator().validate(pet, result);
        if (result.hasErrors()) {
            return ViewUtils.DEFAULT_VIEW;
        } else {
            this.clinic.storePet(pet);
            status.setComplete();
             Pet p = this.clinic.loadPet(pet.getId());
             System.out.println("Pet has Owner->"+p.getId()+" and=>"+p.getName());
            return getRedirectForOwner(p.getOwner());
        }
    }
}
