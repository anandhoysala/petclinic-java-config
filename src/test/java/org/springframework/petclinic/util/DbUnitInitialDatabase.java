package org.springframework.petclinic.util;

import javax.annotation.PostConstruct;

import org.dbunit.IDatabaseTester;
import org.springframework.beans.factory.annotation.Autowired;

public class DbUnitInitialDatabase {
	@Autowired
	private IDatabaseTester databaseTester;
	
	@PostConstruct
	public void initialDatabase() throws Exception {
		databaseTester.onSetup();
	}
}
