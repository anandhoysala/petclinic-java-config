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
package com.spring66.petclinic.service;

import java.util.Collection;

import com.spring66.petclinic.entity.Owner;
import com.spring66.petclinic.entity.Pet;
import com.spring66.petclinic.entity.PetType;
import com.spring66.petclinic.entity.Vet;
import com.spring66.petclinic.entity.Visit;

/**
 * The high-level PetClinic business interface.
 *
 * <p>This is basically a data access object.
 * PetClinic doesn't have a dedicated business facade.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
public interface Clinic {
    public Collection<Owner> findOwners(String lastName);
    public void storeOwner(Owner owner);
    public Collection<Vet> getVets();
    public Collection<PetType> getPetTypes();
    public Owner loadOwner(int id);
    public void storePet(Pet pet);
    public Pet loadPet(int id);
    public void storeVisit(Visit visit);
    //public Troop loadTroop(int id);vv
}