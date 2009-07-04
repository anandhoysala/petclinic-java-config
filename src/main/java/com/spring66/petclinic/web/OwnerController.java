/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring66.petclinic.web;

import static com.spring66.petclinic.web.ViewUtils.DEFAULT_VIEW;
import static com.spring66.petclinic.web.ViewUtils.OWNER_LIST_VIEW;
import static com.spring66.petclinic.web.ViewUtils.getRedirectForOwner;

import com.spring66.petclinic.entity.Owner;
import com.spring66.petclinic.service.Clinic;
import com.spring66.petclinic.validator.OwnerValidator;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

/**
 *
 * @author twinp
 */
@Controller
public class OwnerController {

    private final Clinic clinic;

    @Autowired
    public OwnerController(Clinic clinic) {
        this.clinic = clinic;
    }

    @ModelAttribute
    public Owner newRequest(@RequestParam(required=false) Integer id) {
        System.out.println("newRequest");
        return (id != null) ? this.clinic.loadOwner(id) : new Owner();
    }

    @RequestMapping(method = RequestMethod.GET)
    public void form() {
        System.out.println("form");
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String form(@ModelAttribute Owner owner, BindingResult result, SessionStatus status) {
        new OwnerValidator().validate(owner, result);
        if (result.hasErrors()) {
            return DEFAULT_VIEW;
        } else {
            this.clinic.storeOwner(owner);
            status.setComplete();
            return getRedirectForOwner(owner);
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, params = "submit")
    public String find(Owner owner, BindingResult result, Model model) {
        // find owners by last name
        Collection<Owner> results = this.clinic.findOwners(owner.getLastName());
        if (results.size() < 1) {
            // no owners found
            result.rejectValue("lastName", "notFound", "not found");
            return DEFAULT_VIEW;
        }
        if (results.size() > 1) {
            // multiple owners found
            model.addAttribute(results);
            return OWNER_LIST_VIEW;
        } else {
            // 1 owner found
            owner = results.iterator().next();
            return getRedirectForOwner(owner);
        }
    }
}
