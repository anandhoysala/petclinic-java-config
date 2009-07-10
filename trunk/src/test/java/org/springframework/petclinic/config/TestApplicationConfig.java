package org.springframework.petclinic.config;

import org.dbunit.IDatabaseTester;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.config.java.annotation.Bean;
import org.springframework.config.java.annotation.Configuration;
import org.springframework.config.java.annotation.valuesource.PropertiesValueSource;
import org.springframework.petclinic.util.DbUnitInitialDatabase;
import org.springframework.petclinic.util.DbUnitSpringAdapter;

import com.spring66.petclinic.config.JpaPetclinicApplicationConfig;

@Configuration
@PropertiesValueSource(locations = "classpath:db/jdbc-test.properties")
public abstract class TestApplicationConfig extends JpaPetclinicApplicationConfig {
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
