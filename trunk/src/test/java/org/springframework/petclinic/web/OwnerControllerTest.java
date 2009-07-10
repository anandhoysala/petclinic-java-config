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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static com.spring66.petclinic.web.ViewUtils.DEFAULT_VIEW;

import com.spring66.petclinic.entity.Owner;
import com.spring66.petclinic.service.Clinic;
import com.spring66.petclinic.web.OwnerController;
import com.spring66.petclinic.web.ViewUtils;
import java.util.ArrayList;
import org.springframework.petclinic.OwnerTests;
import org.springframework.validation.FieldError;

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
    public void loadOwner() {
        Owner expected = new Owner();
        EasyMock.expect(clinic.loadOwner(1)).andReturn(expected);
        EasyMock.replay(clinic);

        Owner actual = ownerController.newRequest(1);

        assertThat(actual, is(expected));
    }

    @Test
    public void createOwner() {
        Owner owner = ownerController.newRequest(null);
        assertNotNull(owner);
    }

    @Test
    public void formWithoutErrors() {
        Owner owner = OwnerTests.createValidOwner();

        clinic.storeOwner(owner);
        EasyMock.replay(clinic);

        String viewName = ownerController.form(owner, result, status);

        assertThat(viewName, equalTo(ViewUtils.getRedirectForOwner(owner)));
        assertThat(status.isComplete(), equalTo(true));
        EasyMock.verify(clinic);
    }

    @Test
    public void formWithBindingErrors() {
        EasyMock.replay(clinic); // expect no calls

        Owner owner = OwnerTests.createValidOwner();

        // fake a binding error
        result.addError(new FieldError("owner", "firstName", "is required"));

        String viewName = ownerController.form(owner, result, status);

        assertThat(viewName, equalTo(DEFAULT_VIEW));
        assertThat(status.isComplete(), equalTo(false));
        EasyMock.verify(clinic);
    }

    @Test
    public void formWithValidationErrors() {
        EasyMock.replay(clinic); // expect no calls

        Owner owner = OwnerTests.createValidOwner();
        owner.setFirstName(""); // owner is now invalid

        String viewName = ownerController.form(owner, result, status);

        assertThat(viewName, equalTo(DEFAULT_VIEW));
        assertThat(status.isComplete(), equalTo(false));
        EasyMock.verify(clinic);
    }

    @Test
    public void submitFindWithUnknownLastName() {
        Owner owner = new Owner();
        owner.setLastName("Smith");

        // mock up responding to a search for 'Smith' with an empty result
        EasyMock.expect(clinic.findOwners(owner.getLastName())).andReturn(new ArrayList<Owner>());
        EasyMock.replay(clinic);

        String viewName = ownerController.find(owner, result, model);

        EasyMock.verify(clinic);
        assertThat(viewName, equalTo(DEFAULT_VIEW));
        assertThat(result.hasErrors(), is(true));
        assertThat(result.getFieldError("lastName"), notNullValue());
    }

    @Test
    public void submitFindWithLastNameMatchingMultipleOwners() {
        Owner owner = new Owner();
        owner.setLastName("Jones");

        // mock up responding to a search for 'Jones' with two results
        ArrayList<Owner> matches = new ArrayList<Owner>();
        matches.add(new Owner());
        matches.add(new Owner());
        EasyMock.expect(clinic.findOwners(owner.getLastName())).andReturn(matches);
        EasyMock.replay(clinic);

        String viewName = ownerController.find(owner, result, model);

        EasyMock.verify(clinic);
        assertThat(viewName, equalTo(ViewUtils.OWNER_LIST_VIEW));
        assertThat(result.hasErrors(), is(false));
    }

    @Test
    public void submitFindWithLastNameMatchingSingleResult() {
        Owner owner = new Owner();
        owner.setLastName("Obscure");

        // mock up responding to a search for 'Obscure' with a single result
        ArrayList<Owner> matches = new ArrayList<Owner>();
        Owner match1 = new Owner();
        match1.setId(32);
        matches.add(match1);
        EasyMock.expect(clinic.findOwners(owner.getLastName())).andReturn(matches);
        EasyMock.replay(clinic);

        String viewName = ownerController.find(owner, result, model);

        EasyMock.verify(clinic);
        assertThat(viewName, equalTo(ViewUtils.getRedirectForOwner(match1)));
    }
}
