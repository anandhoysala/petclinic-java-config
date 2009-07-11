/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.springframework.petclinic;

import com.spring66.petclinic.entity.Owner;
import com.spring66.petclinic.entity.Pet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 *
 * @author TwinP
 */
public class OwnerTests {

    @Test
    public void testHasPet() {
        Owner owner = new Owner();
        Pet fido = new Pet();
        fido.setName("Fido");
        assertNull(owner.getPet("Fido"));
        assertNull(owner.getPet("fido"));
        owner.addPet(fido);
        assertEquals(fido, owner.getPet("Fido"));
        assertEquals(fido, owner.getPet("fido"));
    }

    /**
     * Creates an {@link Owner} object with valid properties but no pets.
     */
    public static Owner createValidOwner() {
        Owner owner = new Owner();
        owner.setFirstName("Frances");
        owner.setLastName("Farmer");
        owner.setAddress("123 Pike Place");
        owner.setCity("Seattle");
        owner.setTelephone("2065551212");
        return owner;
    }

}
