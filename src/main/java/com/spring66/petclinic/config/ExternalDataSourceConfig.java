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
package org.spring66.petclinic.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.config.java.annotation.Bean;
import org.springframework.config.java.annotation.Configuration;
import org.springframework.config.java.annotation.ExternalValue;
import org.springframework.config.java.annotation.valuesource.PropertiesValueSource;

/**
 * An alternative configuration option for Petclinic's DataSource.
 * <p/>
 *
 * For convenience purposes, {@link EmbeddedDataSourceConfig} is
 * {@code @Import}ed into {@link PetclinicApplicationConfig}. The more
 * real-world scenario, of course is that the application would need
 * access to a standalone {@code DataSource}.
 * <p/>
 *
 * This configuration gets its JDBC driver classname, url, username
 * and password from an external properties file.  Note that
 * {@code @PropertiesValueSource} and {@code @ExternalValue} have both
 * been used to get access to these externalized values much like was
 * done in {@link EmbeddedDataSourceConfig}.  In this case, however,
 * {@code @ExternalValue} methods, rather than fields are used.  Both
 * forms are valid, though fields will probably be preferred in most
 * cases because they are more concise, do not require declaring the
 * class as abstract, and may be assigned a default value in the case
 * a property cannot be found at runtime.
 *
 * @see PetclinicApplicationConfig
 * @see JdbcPetclinicApplicationConfig
 * @see JpaPetclinicApplicationConfig
 * @see EmbeddedDataSourceConfig
 * @see ExternalValue
 * @see PropertiesValueSource
 * @see src/main/resources/db/jdbc.properties
 *
 * @author Chris Beams
 */
@Configuration
@PropertiesValueSource(locations="db/jdbc.properties")
public abstract class ExternalDataSourceConfig {

    abstract @ExternalValue("jdbc.driverClassName") String driverClassName();
    abstract @ExternalValue("jdbc.url") String url();
    abstract @ExternalValue("jdbc.username") String username();
    abstract @ExternalValue("jdbc.password") String password();

    /**
     * Uses Apache Commons DBCP for connection pooling. See Commons DBCP
     * documentation for the required JAR files. Alternatively you can use
     * another connection pool such as C3P0, similarly configured using Spring.
     */
    public @Bean DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(driverClassName());
        dataSource.setUrl(url());
        dataSource.setUsername(username());
        dataSource.setPassword(password());
        return dataSource;
    }

}
