/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring66.petclinic.web;

import com.spring66.petclinic.entity.Vet;
import com.spring66.petclinic.service.Clinic;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author twinp
 */
@Controller
public class VetController {
    
    private Clinic clinic;
    
    @Autowired
    public VetController(Clinic clinic) {
        this.clinic = clinic;
    }
    
    @RequestMapping
    public Collection<Vet> list(){
        return this.clinic.getVets();
    }
}
