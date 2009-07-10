/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.petclinic.web;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;
import static org.junit.Assert.assertEquals;

import com.spring66.petclinic.entity.Owner;
import com.spring66.petclinic.service.Clinic;
import com.spring66.petclinic.web.OwnerController;

/**
 *
 * @author TwinP
 */
public class OwnerControllerTest {

    private Clinic clinic;
    private OwnerController ownerController;
    private Model model;
    private BindingResult result;
    private SessionStatus status;

    @Before
    public void setUp() {
        model = new ExtendedModelMap();
        result = new MapBindingResult(model.asMap(), "owner");
        status = new SimpleSessionStatus();

        clinic = EasyMock.createMock(Clinic.class);
        ownerController = new OwnerController(clinic);
    }
    
    @Test
    public void newRequest() {
    	Owner owner = new Owner();
    	owner.setId(1);
    	EasyMock.expect(clinic.loadOwner(1)).andReturn(owner);
    	EasyMock.replay(clinic);
    	
    	Owner result = ownerController.newRequest(1);
    	assertEquals(owner, result);
    }
}
