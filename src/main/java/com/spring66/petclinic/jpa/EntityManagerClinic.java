package com.spring66.petclinic.jpa;

import com.spring66.petclinic.entity.Owner;
import com.spring66.petclinic.entity.Pet;
import com.spring66.petclinic.entity.PetType;
import com.spring66.petclinic.entity.Vet;
import com.spring66.petclinic.entity.Visit;
import com.spring66.petclinic.service.Clinic;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EntityManagerClinic implements Clinic {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    @Override
    public Collection<Owner> findOwners(String lastName) {
        Query query = this.entityManager.createQuery("SELECT owner FROM Owner owner WHERE owner.lastName LIKE :lastName");
        query.setParameter("lastName", lastName + "%");
        return query.getResultList();
    }

    @Override
    public void storeOwner(Owner owner) {
        // Consider returning the persistent object here, for exposing
        // a newly assigned id using any persistence provider...
        Owner merged = this.entityManager.merge(owner);
        this.entityManager.flush();
        owner.setId(merged.getId());
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Collection<Vet> getVets() {
        return this.entityManager.createQuery("SELECT vet FROM Vet vet ORDER BY vet.lastName, vet.firstName").getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Collection<PetType> getPetTypes() {
        return this.entityManager.createQuery("SELECT ptype FROM PetType ptype ORDER BY ptype.name").getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Owner loadOwner(int id) {
        return this.entityManager.find(Owner.class, id);
    }

    @Override
    public void storePet(Pet pet) {
        // Consider returning the persistent object here, for exposing
        // a newly assigned id using any persistence provider...
        Pet merged = this.entityManager.merge(pet);
        this.entityManager.flush();
        pet.setId(merged.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public Pet loadPet(int id) {
        return this.entityManager.find(Pet.class, id);
    }

    @Override
    public void storeVisit(Visit visit) {
                // Consider returning the persistent object here, for exposing
        // a newly assigned id using any persistence provider...
        Visit merged = this.entityManager.merge(visit);
        this.entityManager.flush();
        visit.setId(merged.getId());
    }


}
