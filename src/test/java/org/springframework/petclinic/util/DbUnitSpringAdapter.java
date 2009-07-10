package org.springframework.petclinic.util;
import java.io.File;
import java.io.FileInputStream;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.dbunit.DefaultDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class DbUnitSpringAdapter extends AbstractFactoryBean {

    private DataSource dataSource;

    private String dataSetFile;

    private DatabaseOperation setUpOperation = DatabaseOperation.CLEAN_INSERT;

    private DatabaseOperation tearDownOperation = DatabaseOperation.NONE;

    private IDataTypeFactory dataTypeFactory;

    private IDatabaseTester databaseTester;

    @PostConstruct
    private void initialiseDatabaseTester() throws Exception {
        IDatabaseConnection con = new DatabaseDataSourceConnection(dataSource);

        if (dataTypeFactory != null)
            con.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
                                        dataTypeFactory);

        databaseTester = new DefaultDatabaseTester(con);
        databaseTester.setSetUpOperation(setUpOperation);
        databaseTester.setTearDownOperation(tearDownOperation);
        databaseTester.setDataSet(
                new XmlDataSet(
                new FileInputStream(
                new File(dataSetFile))));
    }

	protected Object createInstance() throws Exception {
		return databaseTester;
	}

	public Class getObjectType() {
		return IDatabaseTester.class;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getDataSetFile() {
		return dataSetFile;
	}

	public void setDataSetFile(String dataSetFile) {
		this.dataSetFile = dataSetFile;
	}

	public DatabaseOperation getSetUpOperation() {
		return setUpOperation;
	}

	public void setSetUpOperation(DatabaseOperation setUpOperation) {
		this.setUpOperation = setUpOperation;
	}

	public DatabaseOperation getTearDownOperation() {
		return tearDownOperation;
	}

	public void setTearDownOperation(DatabaseOperation tearDownOperation) {
		this.tearDownOperation = tearDownOperation;
	}

	public IDataTypeFactory getDataTypeFactory() {
		return dataTypeFactory;
	}

	public void setDataTypeFactory(IDataTypeFactory dataTypeFactory) {
		this.dataTypeFactory = dataTypeFactory;
	}

}
