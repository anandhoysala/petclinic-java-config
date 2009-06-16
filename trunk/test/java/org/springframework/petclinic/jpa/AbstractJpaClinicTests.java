/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.petclinic.jpa;

import static org.junit.Assert.assertEquals;
import com.spring66.petclinic.entity.Owner;
import com.spring66.petclinic.service.Clinic;
import java.util.Collection;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;


/**
 * <p>
 * This class extends {@link AbstractJpaTests}, one of the valuable test
 * superclasses provided in the <code>org.springframework.test</code> package.
 * This represents best practice for integration tests with Spring for JPA based
 * tests which require <em>shadow class loading</em>. For all other types of
 * integration testing, the <em>Spring TestContext Framework</em> is
 * preferred.
 * </p>
 * <p>
 * AbstractJpaTests and its superclasses provide the following services:
 * <ul>
 * <li>Injects test dependencies, meaning that we don't need to perform
 * application context lookups. See the setClinic() method. Injection uses
 * autowiring by type.</li>
 * <li>Executes each test method in its own transaction, which is automatically
 * rolled back by default. This means that even if tests insert or otherwise
 * change database state, there is no need for a teardown or cleanup script.</li>
 * <li>Provides useful inherited protected fields, such as a
 * {@link SimpleJdbcTemplate} that can be used to verify database state after
 * test operations, or verify the results of queries performed by application
 * code. Alternatively, you can use protected convenience methods such as
 * {@link #countRowsInTable(String)}, {@link #deleteFromTables(String[])},
 * etc. An ApplicationContext is also inherited, and can be used for explicit
 * lookup if necessary.</li>
 * </ul>
 * <p>
 * {@link AbstractJpaTests} and related classes are shipped in
 * <code>spring-test.jar</code>.
 * </p>
 *
 * @author Rod Johnson
 * @author Sam Brannen
 * @see AbstractJpaTests
 */
@ContextConfiguration
public abstract class AbstractJpaClinicTests extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    protected Clinic clinic;


    /**
     * This method is provided to set the Clinic instance being tested by the
     * Dependency Injection injection behaviour of the superclass from the
     * <code>org.springframework.test</code> package.
     *
     * @param clinic clinic to test
     */
    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }
    /*@Test
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

    // Check lazy loading, by ending the transaction
    // endTransaction();

    // Now Owners are "disconnected" from the data store.
    // We might need to touch this collection if we switched to lazy loading
    // in mapping files, but this test would pick this up.
    o1.getPets();
    }*/
    @Test
    public void testSomething(){
        assertEquals("xxx", "x");
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
    /*@Test
    public void testUpdateOwner() throws Exception {
    Owner o1 = this.clinic.loadOwner(1);
    String old = o1.getLastName();
    o1.setLastName(old + "X");
    this.clinic.storeOwner(o1);
    o1 = this.clinic.loadOwner(1);
    assertEquals(old + "X", o1.getLastName());
    }

    @Test
    public void testLoadPet() {
    Collection<PetType> types = this.clinic.getPetTypes();
    Pet p7 = this.clinic.loadPet(7);
    assertTrue(p7.getName().startsWith("Samantha"));
    assertEquals(EntityUtils.getById(types, PetType.class, 1).getId(), p7.getType().getId());
    assertEquals("Jean", p7.getOwner().getFirstName());
    Pet p6 = this.clinic.loadPet(6);
    assertEquals("George", p6.getName());
    assertEquals(EntityUtils.getById(types, PetType.class, 4).getId(), p6.getType().getId());
    assertEquals("Peter", p6.getOwner().getFirstName());
    }

    @Test
    public void testInsertPet() {
    Owner o6 = this.clinic.loadOwner(6);
    int found = o6.getPets().size();
    Pet pet = new Pet();
    pet.setName("bowser");
    Collection<PetType> types = this.clinic.getPetTypes();
    pet.setType(EntityUtils.getById(types, PetType.class, 2));
    pet.setBirthDate(new Date());
    o6.addPet(pet);
    assertEquals(found + 1, o6.getPets().size());
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
    Visit visit = new Visit();
    p7.addVisit(visit);
    visit.setDescription("test");
    this.clinic.storePet(p7);
    // assertTrue(!visit.isNew()); -- NOT TRUE FOR TOPLINK (before commit)
    p7 = this.clinic.loadPet(7);
    assertEquals(found + 1, p7.getVisits().size());
    }*/

}
