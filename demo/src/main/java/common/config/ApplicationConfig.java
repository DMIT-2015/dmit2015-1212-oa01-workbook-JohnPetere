//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package common.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.enterprise.context.ApplicationScoped;

@DataSourceDefinitions({@DataSourceDefinition(
		name = "java:app/datasources/hsqldatabaseDS",
		className = "org.hsqldb.jdbc.JDBCDataSource",
		url = "jdbc:hsqldb:mem:dmit2015hsqldb",
		user = "user2015",
		password = "Password2015"
)})
@ApplicationScoped
public class ApplicationConfig {
	public ApplicationConfig() {
	}
}
