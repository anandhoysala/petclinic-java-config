/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.petclinic.jpa;

import com.spring66.petclinic.entity.Owner;
import com.spring66.petclinic.service.Clinic;
import java.util.Collection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
public class NewEmptyJUnitTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    protected Clinic clinic;

    public NewEmptyJUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() {
        System.out.println("hello world");
        //Collection<Owner> owners = this.clinic.findOwners("Schultz");
        //int found = owners.size();
        Owner owner = new Owner();
        owner.setLastName("Schultz");
        this.clinic.storeOwner(owner);
        // assertTrue(!owner.isNew()); -- NOT TRUE FOR TOPLINK (before commit)
        Collection<Owner> owners = this.clinic.findOwners("Schultz");
        assertEquals( 1, owners.size());
    }
}