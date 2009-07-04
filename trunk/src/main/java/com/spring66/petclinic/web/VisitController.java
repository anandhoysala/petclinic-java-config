/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring66.petclinic.web;

import static com.spring66.petclinic.web.ViewUtils.getRedirectForOwner;
import com.spring66.petclinic.entity.Pet;
import com.spring66.petclinic.entity.Visit;
import com.spring66.petclinic.service.Clinic;
import com.spring66.petclinic.validator.VisitValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 *
 * @author TwinP
 */
@Controller
@RequestMapping
@SessionAttributes(types = Visit.class)
public class VisitController {

    private Clinic clinic;

    @Autowired
    public VisitController(Clinic clinic) {
        this.clinic = clinic;
    }

    @RequestMapping(method = RequestMethod.GET)
    public void form(@RequestParam int petId, Model model) {
        System.out.println("VisitContoller, form, GET");
        Pet pet = this.clinic.loadPet(petId);
        Visit visit = new Visit();
        //pet.addVisit(visit);
        visit.setPet(pet);
        model.addAttribute(visit);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String form(@ModelAttribute Visit visit, BindingResult result, SessionStatus status) {
        new VisitValidator().validate(visit, result);
        if (result.hasErrors()) {
            return null; // go back to the form
        } else {
            this.clinic.storeVisit(visit);
            status.setComplete();
            return getRedirectForOwner(visit.getPet().getOwner());
        }
    }
}
