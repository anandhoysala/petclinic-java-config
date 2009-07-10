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

import static org.springframework.config.java.plugin.context.RegistrationPolicy.REPLACE_EXISTING;

import org.springframework.config.java.annotation.Configuration;
import org.springframework.config.java.annotation.Import;
import org.springframework.config.java.annotation.valuesource.PropertiesValueSource;
import org.springframework.config.java.context.JavaConfigApplicationContext;
import org.springframework.config.java.context.JavaConfigWebApplicationContext;
import org.springframework.config.java.plugin.aop.AspectJAutoProxy;
import org.springframework.config.java.plugin.context.AnnotationDrivenConfig;
import org.springframework.config.java.plugin.context.MBeanExport;
import org.springframework.config.java.plugin.tx.AnnotationDrivenTx;
import org.springframework.web.context.ContextLoaderListener;

import com.spring66.petclinic.service.Clinic;


/**
 * Application context definition for Petclinic.
 *
 * <h3>Introduction</h3>
 *
 * Also known as the 'root application context', this JavaConfig
 * {@code @Configuration} class will be loaded by Spring's
 * {@link ContextLoaderListener} servlet listener as configured by
 * {@literal WEB-INF/web.xml}.  At runtime in the webapp it will
 * serve as a parent to the context created for {@link PetclinicServletConfig}.
 * This root-web/parent-child hierarchy of contexts is the same as that used
 * by convention in traditional Spring MVC applications configured with XML.
 * <p/>
 *
 * <h3>Understanding the annotations</h3>
 *
 * {@code @Configuration} denotes that this is a class suitable for processing
 * by JavaConfig, i.e.: it exposes one or more {@code @Bean} declarations.
 * As such, it will be processed either via {@link JavaConfigApplicationContext}
 * or {@link JavaConfigWebApplicationContext}.
 * <p/>
 *
 * {@code @AnnotationDrivenConfig} behaves similarly to Spring XML's
 * {@literal <context:annotation-config/>} element, and in this case ensures
 * that our {@code Clinic} bean gets autowired with a DataSource (see
 * {@link Clinic} class definition for more details.
 * <p/>
 *
 * {@code @AnnotationDrivenTx} behaves similarly to Spring XML's
 * {@literal <tx:annotation-driven/>} element and ensures that
 * {@code @Transactional} annotations in {@code Clinic} bean are respected
 * by wrapping that bean in an Spring AOP proxy designed to handle
 * transaction management against our {@code DataSource}.
 * Note that {@literal proxyTargetClass=true} is used here to facilitate
 * exposing the {@code Clinic} bean as a JMX MBean.
 * <p/>
 *
 * {@code @AspectJAutoProxy} behaves similarly to Spring XML's
 * {@literal <aop:aspectj-autoproxy/>} element and detects any beans
 * whose classes are annotated with {@code @Aspect}.  In this case,
 * {@code CallMonitoringAspect} is an {@code @Aspect} class, and contains
 * a pointcut that matches joinpoints in our {@code Clinic} bean.  Clinic
 * will now be transactionally proxied (see above) as well as proxied
 * for call monitoring. Again, as above, {@literal proxyTargetClass=true}
 * is specified here in service of {@code Clinic} being exposed as a JMX MBean
 * <p/>
 *
 * {@code @MBeanExport} behaves similarly to Spring XML's
 * {@literal <context:mbean-export/>} element and detects any beans that
 * are either 'traditional' JMX MBeans (implement an MBean interface) or
 * use Spring's {@code @ManagedResource} and other JMX-related annotations.
 * Any such beans will be registered with a local JMX {@code MBeanServer}
 * and will thus be available for monitoring via JConsole or any other JMX
 * console application.  In this case, two of our beans are MBeans:
 * {@code Clinic}, and {@code CallMonitoringAspect}.  Both of these will
 * be managable via JConsole upon deploying the application assuming
 * {@literal -Dcom.sun.management.jmxremote} has been supplied as a VM argument
 * to the servlet container. Note that {@literal registration=REPLACE_EXISTING}
 * has been specified only to avoid errors during automated testing of this
 * application. In normal practice, you may omit this and use the default
 * (FAIL_ON_EXISTING).
 * <p/>
 *
 * @see PetclinicServletConfig
 * @see JpaPetclinicApplicationConfig
 * @see JdbcPetclinicApplicationConfig
 *
 * @author Chris Beams
 */
@Configuration
@AnnotationDrivenConfig
@AnnotationDrivenTx(proxyTargetClass=true)
@AspectJAutoProxy(proxyTargetClass=true)
@MBeanExport(registration=REPLACE_EXISTING)
@Import(JpaPetclinicApplicationConfig.class)
@PropertiesValueSource(locations = "classpath:db/jdbc.properties")
public class PetclinicApplicationConfig {
    

}
