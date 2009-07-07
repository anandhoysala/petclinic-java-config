package com.spring66.petclinic.jpa;

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
}
