/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.springframework.petclinic.jpa;

import com.spring66.petclinic.entity.Owner;
import com.spring66.petclinic.entity.Pet;
import com.spring66.petclinic.entity.PetType;
import com.spring66.petclinic.entity.Vet;
import com.spring66.petclinic.entity.Visit;
import com.spring66.petclinic.service.Clinic;
import java.util.Collection;
import java.util.Date;
import org.junit.Test;
import com.spring66.petclinic.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.config.java.test.JavaConfigContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import static org.junit.Assert.*;

/**
 *
 * @author twinp
 */
@ContextConfiguration(locations = "org.springframework.petclinic.config.TestApplicationConfig",
loader = JavaConfigContextLoader.class)
public class ClinicJPATest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    protected Clinic clinic;

    public ClinicJPATest() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testGetVets() {
        Collection<Vet> vets = this.clinic.getVets();
        // Use the inherited countRowsInTable() convenience method (from
        // AbstractTransactionalDataSourceSpringContextTests) to verify the
        // results.
        assertEquals("JDBC query must show the same number of vets", super.countRowsInTable("VETS"), vets.size());
        Vet v1 = EntityUtils.getById(vets, Vet.class, 2);
        assertEquals("Leary", v1.getLastName());
        assertEquals(1, v1.getNrOfSpecialties());
        assertEquals("radiology", (v1.getSpecialties().get(0)).getName());
        Vet v2 = EntityUtils.getById(vets, Vet.class, 3);
        assertEquals("Douglas", v2.getLastName());
        assertEquals(2, v2.getNrOfSpecialties());
        assertEquals("dentistry", (v2.getSpecialties().get(0)).getName());
        assertEquals("surgery", (v2.getSpecialties().get(1)).getName());
    }

    @Test
    public void testGetPetTypes() {
        Collection<PetType> petTypes = this.clinic.getPetTypes();
        assertEquals("JDBC query must show the same number of pet types", super.countRowsInTable("TYPES"),
                petTypes.size());
        PetType t1 = EntityUtils.getById(petTypes, PetType.class, 1);
        assertEquals("cat", t1.getName());
        PetType t4 = EntityUtils.getById(petTypes, PetType.class, 4);
        assertEquals("snake", t4.getName());
    }

    @Test
    public void testFindOwners() {
        Collection<Owner> owners = this.clinic.findOwners("Davis");
        assertEquals(2, owners.size());
        owners = this.clinic.findOwners("Daviss");
        assertEquals(0, owners.size());
    }

    @Test
    public void testLoadOwner() {
        Owner o1 = this.clinic.loadOwner(1);
        assertTrue(o1.getLastName().startsWith("Franklin"));
        Owner o10 = this.clinic.loadOwner(10);
        assertEquals("Carlos", o10.getFirstName());
        System.out.println("Pets=>" + o1.getPets().size());
        // Check lazy loading, by ending the transaction
        // endTransaction();

        // Now Owners are "disconnected" from the data store.
        // We might need to touch this collection if we switched to lazy loading
        // in mapping files, but this test would pick this up.
        o1.getPets();
    }

    @Test
    public void testInsertOwner() {
        Collection<Owner> owners = this.clinic.findOwners("Schultz");
        int found = owners.size();
        Owner owner = new Owner();
        owner.setLastName("Schultz");
        this.clinic.storeOwner(owner);
        // assertTrue(!owner.isNew()); -- NOT TRUE FOR TOPLINK (before commit)
        owners = this.clinic.findOwners("Schultz");
        assertEquals(found + 1, owners.size());
    }

    @Test
    public void testUpdateOwner() throws Exception {
        Owner o1 = this.clinic.loadOwner(1);
        String old = o1.getLastName();
        o1.setLastName(old + "X");
        this.clinic.storeOwner(o1);
        o1 = this.clinic.loadOwner(1);
        assertEquals(old + "X", o1.getLastName());
    }

    @Test
    public void insertPet() {
        Owner o6 = this.clinic.loadOwner(6);
        int found = o6.getPets().size();
        Pet pet = new Pet();
        pet.setName("bowser");
        Collection<PetType> types = this.clinic.getPetTypes();
        pet.setType(EntityUtils.getById(types, PetType.class, 2));
        pet.setBirthDate(new Date());
        o6.addPet(pet);
        assertEquals(found + 1, o6.getPets().size());
        // both storePet and storeOwner are necessary to cover all ORM tools
        //this.clinic.storePet(pet);
        this.clinic.storeOwner(o6);
        // assertTrue(!pet.isNew()); -- NOT TRUE FOR TOPLINK (before commit)
        o6 = this.clinic.loadOwner(6);
        assertEquals(found + 1, o6.getPets().size());
    }

    @Test
    public void testUpdatePet() throws Exception {
        Pet p7 = this.clinic.loadPet(7);
        String old = p7.getName();
        p7.setName(old + "X");
        this.clinic.storePet(p7);
        p7 = this.clinic.loadPet(7);
        assertEquals(old + "X", p7.getName());
    }

    @Test
    public void testInsertVisit() {
        Pet p7 = this.clinic.loadPet(7);
        int found = p7.getVisits().size();
        System.out.println("Found->" + found);
        Visit visit = new Visit();
        p7.addVisit(visit);
        visit.setDescription("test");
        visit.setName("name");

        visit.setVisitDate(new Date());
        this.clinic.storePet(p7);
        // assertTrue(!visit.isNew()); -- NOT TRUE FOR TOPLINK (before commit)
        p7 = this.clinic.loadPet(7);
        //System.out.println("found+1->"+p7.getVisits().size()+"info->"+p7.getVisits().iterator().next().getId());*/
        assertEquals(found + 1, p7.getVisits().size());
    }
}