/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.petclinic.web;

import com.spring66.petclinic.entity.Owner;
import com.spring66.petclinic.entity.Pet;
import com.spring66.petclinic.entity.Visit;
import com.spring66.petclinic.service.Clinic;
import com.spring66.petclinic.web.ViewUtils;
import com.spring66.petclinic.web.VisitController;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.bind.support.SimpleSessionStatus;

/**
 *
 * @author TwinP
 */
public class VisitControllerTest {

    private VisitController visitController;
    private Clinic clinic;
    private Model model;
    private BindingResult result;
    private SessionStatus status;

    @Before
    public void setUp() {
        model = new ExtendedModelMap();
        result = new MapBindingResult(model.asMap(), "visit");
        status = new SimpleSessionStatus();
        clinic = EasyMock.createMock(Clinic.class);
        visitController = new VisitController(clinic);
    }

    @Test
    public void setupAdd() {
        EasyMock.expect(clinic.loadPet(1)).andReturn(new Pet());
        EasyMock.replay(clinic);
        //System.out.println("Model->"+model.);
        visitController = new VisitController(clinic);
        visitController.form(1, model);
        assertTrue(model.containsAttribute("visit"));
        assertNotNull(((Visit) model.asMap().get("visit")).getPet());
    }

    @Test
    public void submitAddWithoutBindingErrors() {
        Visit visit = createValidVisit();

        clinic.storeVisit(visit);
        EasyMock.replay(clinic);

        String viewName = visitController.form(visit, result, status);

        assertThat(viewName, equalTo(ViewUtils.getRedirectForOwner(visit.getPet().getOwner())));
    }

    @Test
    public void submitAddWithBindingErrors() {
        Visit visit = createValidVisit();

        clinic.storeVisit(visit);
        EasyMock.replay(clinic);

        result.addError(new FieldError("visit", "description", "is required"));

        String viewName = visitController.form(visit, result, status);

        assertThat(viewName, equalTo(null));
    }

    @Test
    public void submitAddWithValidationErrors() {
        Visit visit = createValidVisit();

        // invalidate the visit object by setting its description property to null
        visit.setDescription(null);

        clinic.storeVisit(visit);
        EasyMock.replay(clinic);

        String viewName = visitController.form(visit, result, status);

        assertThat(viewName, equalTo(null));
    }
    private static Visit createValidVisit() {
        Owner o = new Owner();
        o.setId(99);
        Pet p = new Pet();

        Visit visit = new Visit();
        visit.setVisitDate(new Date());
        visit.setDescription("basic check-up");

        p.setId(55);
        o.addPet(p);
        p.addVisit(visit);

        return visit;
    }
}
