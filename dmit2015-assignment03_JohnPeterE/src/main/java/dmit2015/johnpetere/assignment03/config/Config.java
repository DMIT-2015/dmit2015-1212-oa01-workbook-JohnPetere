package dmit2015.johnpetere.assignment03.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.annotation.sql.DataSourceDefinitions;

@DataSourceDefinitions({
        @DataSourceDefinition(
                name = "java:app/datasources/hsqldatabaseDS",
                className = "org.hsqldb.jdbc.JDBCDataSource",
//		url="jdbc:hsqldb:file:~/jdk/databases/dmit2015-demos-hsqldb;shutdown=true",
                url = "jdbc:hsqldb:mem:dmit2015hsqldb",
                user = "user2015",
                password = "Password2015"

        ),

})
/**
 * @author: John-Peter Eberhard
 * @version: 1.0
 *  @date:  2021-01-04
 * config file for connecting to databse.
 */
@ApplicationScoped
public class Config {
}
