package com.spring66.petclinic.web;

import com.spring66.petclinic.entity.PetType;
import com.spring66.petclinic.service.Clinic;
import java.beans.PropertyEditorSupport;

public class PetTypeEditor extends PropertyEditorSupport {

    private final Clinic clinic;

    public PetTypeEditor(Clinic clinic) {
        this.clinic = clinic;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        for (PetType type : this.clinic.getPetTypes()) {
            if (type.getName().equals(text)) {
                setValue(type);
            }
        }
    }
}
