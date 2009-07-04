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
package com.spring66.petclinic.validator;


import com.spring66.petclinic.entity.Owner;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;


/**
 * {@code Validator} for {@code Owner} forms.
 * 
 * @see src/main/resources/messages.properties
 * @see src/main/resources/messages_en.properties
 * @see src/main/resources/messages_de.properties
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Chris Beams
 */
public class OwnerValidator {

    public void validate(Owner owner, Errors errors) {
        if (!StringUtils.hasLength(owner.getFirstName())) {
            errors.rejectValue("firstName", "required", "required");
        }
        if (!StringUtils.hasLength(owner.getLastName())) {
            errors.rejectValue("lastName", "required", "required");
        }
        if (!StringUtils.hasLength(owner.getAddress())) {
            errors.rejectValue("address", "required", "required");
        }
        if (!StringUtils.hasLength(owner.getCity())) {
            errors.rejectValue("city", "required", "required");
        }

        String telephone = owner.getTelephone();
        if (!StringUtils.hasLength(telephone)) {
            errors.rejectValue("telephone", "required", "required");
        } else {
            for (int i = 0; i < telephone.length(); ++i) {
                if ((Character.isDigit(telephone.charAt(i))) == false) {
                    errors.rejectValue("telephone", "nonNumeric", "non-numeric");
                    break;
                }
            }
        }
    }

}
