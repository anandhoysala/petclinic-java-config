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
package com.spring66.petclinic.web;
import static java.lang.String.format;
import com.spring66.petclinic.entity.Owner;
import static com.spring66.petclinic.web.PageType.LIST;
import static com.spring66.petclinic.web.PageType.SHOW;

public class ViewUtils {
    
    static final String PET_OBJ_NAME = "pet";
    static final String OWNER = "owner";
    static final String OWNER_LIST_VIEW = format("%s/%s", OWNER, LIST);
    static final String OWNER_SHOW_REDIRECT = format("redirect:../%s/%s?id=", OWNER, SHOW);

    static final String DEFAULT_VIEW = null;

    private ViewUtils() { }

    public static String getRedirectForOwner(Owner owner) {
        System.out.println ("ViewUtils===============");
        System.out.println("ViewUtil->"+owner.getId());
        String redirect =  OWNER_SHOW_REDIRECT + owner.getId();
        System.out.println("Redirect-?"+redirect);
        return redirect;
    }

}
