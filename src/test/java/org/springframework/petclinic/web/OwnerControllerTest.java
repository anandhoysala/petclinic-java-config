/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.petclinic.web;

import com.spring66.petclinic.service.Clinic;
import com.spring66.petclinic.web.OwnerController;
import org.easymock.EasyMock;
import org.junit.Before;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;

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
}
