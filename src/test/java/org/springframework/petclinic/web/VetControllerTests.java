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
package org.springframework.petclinic.web;

import com.spring66.petclinic.entity.Vet;
import com.spring66.petclinic.service.Clinic;
import com.spring66.petclinic.web.VetController;
import com.spring66.petclinic.web.WelcomeController;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.Collection;

import org.easymock.EasyMock;
import org.junit.Test;



/**
 * Unit tests for {@link WelcomeController}.
 *
 * @author Chris Beams
 */
public class VetControllerTests {
    @Test
    public void list() {
        Clinic clinic = EasyMock.createMock(Clinic.class);
        Collection<Vet> expected = new ArrayList<Vet>();
        EasyMock.expect(clinic.getVets()).andReturn(expected);
        EasyMock.replay(clinic);

        VetController vetController = new VetController(clinic);

        Collection<Vet> actual = vetController.list();
        assertThat(actual, is(expected));
        EasyMock.verify(clinic);
    }
}
