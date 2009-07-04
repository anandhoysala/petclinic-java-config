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
package com.spring66.petclinic.config;

import com.spring66.petclinic.web.ClinicBindingInitializer;
import java.util.Properties;

import org.springframework.config.java.annotation.Bean;
import org.springframework.config.java.annotation.Configuration;
import org.springframework.config.java.plugin.context.ComponentScan;
import org.springframework.config.java.support.ConfigurationSupport;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;
import org.springframework.web.servlet.mvc.UrlFilenameViewController;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


/**
 * {@code DispatcherServlet} application context for PetClinic's web tier.
 * <p/>
 *
 * @see WEB-INF/web.xml where this class is referenced as an init-param to
 * Spring's {@code DispatcherServlet}
 *
 * {@code @ComponentScan} behaves similarly to Spring XML's
 * {@literal <context:component-scan/> element and in this case detects
 * all {@code @Controllers} within the petclinic.web package.  Like its
 * XML counterpart, {@code @ComponentScan} enables annotation-driven
 * configuration by default, so that any dependencies those controllers
 * have will be autowired in.  For example, {@link WelcomeController}
 * requires a bean of type {@link Clinic PetclinicApplicationConfig#clinic()}
 * to be wired in.  This bean will be provided at runtime by the root
 * application context created based on {@code @Bean} declarations in
 * {@link PetclinicApplicationConfig}
 *
 * @see PetclinicApplicationConfig
 * @see WelcomeController
 * @see ComponentScan
 *
 * @author Chris Beams
 */
@Configuration
@ComponentScan("com.spring66.petclinic.web")
public class PetclinicServletConfig extends ConfigurationSupport {

    /** @see ClinicBindingInitializer */
    public @Bean WebBindingInitializer clinicBindingInitializer() {
        return new ClinicBindingInitializer();
    }

    /**
     * This bean matches incoming requests to controllers by convention. 
     * For example '/owner/find' is delegated to the OwnerController.find().
     * @see ControllerClassNameHandlerMapping 
     */
    public @Bean ControllerClassNameHandlerMapping controllerMappings() {
        ControllerClassNameHandlerMapping ccnhm = new ControllerClassNameHandlerMapping();
        ccnhm.setDefaultHandler(new UrlFilenameViewController());
        return ccnhm;
    }
    
    public @Bean HandlerAdapter simpleControllerHandlerAdapter() {
        return new SimpleControllerHandlerAdapter();
    }
    
    /**
     * This bean processes annotated handler methods, applying
     * PetClinic-specific PropertyEditors for request parameter binding.
     * It overrides the default AnnotationMethodHandlerAdapter.
     */
    public @Bean AnnotationMethodHandlerAdapter handlerAdapter() {
        AnnotationMethodHandlerAdapter adapter = new AnnotationMethodHandlerAdapter();
        adapter.setWebBindingInitializer(clinicBindingInitializer());
        return adapter;
    }

    /**
     * This bean resolves specific types of exceptions to corresponding
     * logical view names for error views. The default behaviour of
     * DispatcherServlet is to propagate all exceptions to the servlet
     * container: this will happen here with all other types of exceptions.
     */
    public @Bean HandlerExceptionResolver exceptionResolver() {
        Properties mappings = new Properties();
        mappings.put(DataAccessException.class.getName(), "dataAccessFailure");
        mappings.put(TransactionException.class.getName(), "dataAccessFailure");

        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        resolver.setExceptionMappings(mappings);

        return resolver;
    }

    /**
     * This bean configures the 'prefix' and 'suffix' properties of
     * InternalResourceViewResolver, * which resolves logical view
     * names returned by Controllers. For example, a logical view name
     * of "vets" will be mapped to "/WEB-INF/jsp/vets.jsp".
     */
    public @Bean ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    /**
     * Message source for this context, loaded from localized "messages_xx" files.
     * Could also reside in the root application context, as it is generic,
     * but is currently just used within PetClinic's web tier.
     */
    public @Bean MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setBasename("messages");
        return ms;
    }

}
