package dmit2015.johnpetere.assignment03.repository;

import dmit2015.johnpetere.assignment03.config.Config;
import dmit2015.johnpetere.assignment03.entity.OscarReview;
import dmit2015.johnpetere.assignment03.entity.OscarReview;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ArquillianExtension.class)                  // Run with JUnit 5 instead of JUnit 4
/**
 * @author: John-Peter Eberhard
 * @version: 1.0
 * @date 2021-01-04
 * Unit testing CRUD functionality from OscarRepository.
 */
public class OscarRepositoryIT {
    @Inject
    private OscarRepository _oscarRepository;
    static OscarReview currentReview;

    @Deployment
    public static WebArchive createDeployment(){

        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addAsLibraries(pomFile.resolve("org.hsqldb:hsqldb:2.6.1").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hamcrest:hamcrest:2.2").withTransitivity().asFile())
                .addClass(Config.class)
                .addClasses(OscarReview.class,OscarRepository.class )
                .addAsResource("META-INF/persistence.xml")// not reading doesn't exist?
                .addAsResource("META-INF/sql/import-data.sql") // neither does this...
//                .addAsResource("META-INF/import-data.sql")// this does not exist
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        return webArchive;
    }
    @Test
    @Order(1)
    void failToCreate(){

        currentReview = new OscarReview();
        currentReview.setNominee("");
        currentReview.setCategory("fishing");
        currentReview.setUsername("");
        currentReview.setReview("");
        Exception exception0 = assertThrows(ConstraintViolationException.class, ()->{
           _oscarRepository.add(currentReview);
        });
//        assertTrue(exception0.getMessage().contains("category is required"));
        assertTrue(exception0.getMessage().contains("review is required"));
        assertTrue(exception0.getMessage().contains("username is required"));
        assertTrue(exception0.getMessage().contains("nominee is required"));
        assertTrue(exception0.getMessage().contains("must be only film, actor, actress, editing or effects"));

    }

    @Test
    @Order(2)
    void shouldFindAll(){
        //https://javabydeveloper.com/org-hibernate-hql-internal-ast-querysyntaxexception-entity-table-is-not-mapped/
        List<OscarReview> oscarReviewsList = _oscarRepository.findAll();

        assertEquals(5, oscarReviewsList.size());



    }
    @Test
    @Order(3)
    void shouldCreate(){
        currentReview = new OscarReview();
        currentReview.setNominee("The Jar Jar Before The menace");
        currentReview.setCategory("film");
        currentReview.setUsername("JarJarLover");
        currentReview.setReview("In this story, Jar jar is hated by everyone, but loved by all star wars fans. JAR JAR IS LOVE");
        _oscarRepository.add(currentReview);

        Optional<OscarReview> optionalReview = _oscarRepository.findByID(currentReview.getId());
        assertTrue(optionalReview.isPresent());
        OscarReview existingOscar = optionalReview.get();

        assertNotNull(existingOscar);
        assertEquals(currentReview.getReview(), existingOscar.getReview());
        assertEquals(currentReview.getCategory(), existingOscar.getCategory());
        assertEquals(currentReview.getUsername(), existingOscar.getUsername());
        assertEquals(currentReview.getNominee(), existingOscar.getNominee());
    }
    @Test
    @Order(4)
    void shouldFindOne(){
        final int oscarReviewID = 1;

        Optional<OscarReview> optionalReview = _oscarRepository.findByID(oscarReviewID);
        assertTrue(optionalReview.isPresent());
        OscarReview existingOscar = optionalReview.get();
        assertNotNull(existingOscar);
        assertEquals(existingOscar.getReview(), "Jar jar lives");
        assertEquals(existingOscar.getCategory(), "film");
        assertEquals(existingOscar.getUsername(), "JarJarIsBest");
        assertEquals(existingOscar.getNominee(), "jar jar binks");
    }
    @Test
    @Order(5)
    void shouldUpdate(){
        final int oscarReviewID = 1;

        Optional<OscarReview> optionalReview = _oscarRepository.findByID(oscarReviewID);
        assertTrue(optionalReview.isPresent());
        OscarReview existingOscar = optionalReview.get();

        currentReview = existingOscar;
        currentReview.setCategory("effects");
        currentReview.setNominee("updatedNOM");
        currentReview.setUsername("UpdateUser");
        currentReview.setReview("updatedReview");
        currentReview.setLastModifiedDateTime(LocalDateTime.now());

        _oscarRepository.update(currentReview);

        Optional<OscarReview> optionalOscar = _oscarRepository.findByID(currentReview.getId());
        assertTrue(optionalOscar.isPresent());
        OscarReview updatedOscar = optionalOscar.get();

        assertNotNull(updatedOscar);
        assertEquals(currentReview.getReview(), updatedOscar.getReview());
        assertEquals(currentReview.getCategory(), updatedOscar.getCategory());
        assertEquals(currentReview.getUsername(), updatedOscar.getUsername());
        assertEquals(currentReview.getNominee(), updatedOscar.getNominee());


        assertEquals(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(currentReview.getLastModifiedDateTime()),

                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(updatedOscar.getLastModifiedDateTime()));

    }

    @Test
    @Order(6)
    void shouldDelete(){
        final int oscarReviewID = 1;

        Optional<OscarReview> optionalReview = _oscarRepository.findByID(oscarReviewID);
        assertTrue(optionalReview.isPresent());
        OscarReview existingOscar = optionalReview.get();
        assertNotNull(existingOscar);
        _oscarRepository.delete(existingOscar.getId());

        optionalReview = _oscarRepository.findByID(oscarReviewID);
        assertTrue(optionalReview.isEmpty());

    }

}
