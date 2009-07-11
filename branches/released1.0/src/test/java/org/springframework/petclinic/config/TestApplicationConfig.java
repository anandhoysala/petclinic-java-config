package org.springframework.petclinic.config;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.config.java.annotation.Bean;
import org.springframework.config.java.annotation.Configuration;
import org.springframework.config.java.annotation.ExternalBean;
import org.springframework.config.java.annotation.Import;
import org.springframework.config.java.annotation.valuesource.PropertiesValueSource;
import org.springframework.config.java.plugin.aop.AspectJAutoProxy;
import org.springframework.config.java.plugin.context.AnnotationDrivenConfig;
import org.springframework.config.java.plugin.tx.AnnotationDrivenTx;
import org.springframework.config.java.support.ConfigurationSupport;
import org.springframework.petclinic.util.DbUnitInitialDatabase;
import org.springframework.petclinic.util.DbUnitSpringAdapter;

import com.spring66.petclinic.config.JpaPetclinicApplicationConfig;

@Configuration
@AnnotationDrivenConfig
@AnnotationDrivenTx(proxyTargetClass=true)
@AspectJAutoProxy(proxyTargetClass=true)
@Import(JpaPetclinicApplicationConfig.class)
@PropertiesValueSource(locations = "classpath:db/jdbc-test.properties")
public abstract class TestApplicationConfig extends ConfigurationSupport {
    /** Provided by {@link EmbeddedDataSourceConfig#dataSource()} */
    protected abstract 
    @ExternalBean
    DataSource dataSource();

    public @Bean(dependsOn="entityManagerFactory") IDatabaseTester dbUnitDatabaseTester() {
		DbUnitSpringAdapter adapter = new DbUnitSpringAdapter();
		adapter.setDataSource(dataSource());
		adapter.setDataSetFile("src/test/data/insert.xml");
		adapter.setDataTypeFactory(new MySqlDataTypeFactory());
		adapter.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		adapter.setTearDownOperation(DatabaseOperation.NONE);
		return this.getObject(adapter, IDatabaseTester.class);
	}
	
	public @Bean(dependsOn="dbUnitDatabaseTester") DbUnitInitialDatabase dbUnitInitialDatabase() {
		return new DbUnitInitialDatabase();
	}

}
