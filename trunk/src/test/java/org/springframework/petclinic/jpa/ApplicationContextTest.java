/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.petclinic.jpa;

import com.spring66.petclinic.entity.Vet;
import com.spring66.petclinic.service.Clinic;
import java.util.Collection;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.config.java.test.JavaConfigContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import static org.junit.Assert.*;

/**
 *
 * @author twinp
 */
@ContextConfiguration(locations = "com.spring66.petclinic.config.JpaPetclinicApplicationConfig",
loader = JavaConfigContextLoader.class)
public class ApplicationContextTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    protected Clinic clinic;

    public ApplicationContextTest() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void isClinicServiceReady() {
		assertNotNull(clinic);
    }




}