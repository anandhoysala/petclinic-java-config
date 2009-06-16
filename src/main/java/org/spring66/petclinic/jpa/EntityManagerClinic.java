package org.spring66.petclinic.jpa;

import com.spring66.petclinic.entity.Owner;
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
}
