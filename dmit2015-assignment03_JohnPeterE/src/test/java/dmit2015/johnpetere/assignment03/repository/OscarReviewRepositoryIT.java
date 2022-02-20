package dmit2015.johnpetere.assignment03.repository;

import static dmit2015.johnpetere.assignment03.repository.OscarRepositoryIT.currentReview;
import static org.junit.jupiter.api.Assertions.*;
import dmit2015.johnpetere.assignment03.entity.OscarReview;
import jakarta.inject.Inject;
import org.jboss.arquillian.container.test.api.Config;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ArquillianExtension.class)
class OscarReviewRepositoryIT {
    @Inject
    private OscarReviewRepository _oscarReviewRepository;

    static OscarReview currentOscarReview; // review being added, found, update, delete .ect

    @Deployment
    public static WebArchive createDeployment(){
//        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

//        return ShrinkWrap.create(WebArchive.class,"test.war")
//                .addAsLibraries(pomFile.resolve("org.hsqldb:hsqldb:2.6.1").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("org.hamcrest:hamcrest:2.2").withTransitivity().asFile())
//                .addClass(Config.class)
//                .addClasses(OscarReview.class, OscarReviewRepository.class)
//                .addAsResource("META-INF/persistence.xml")
//                .addAsResource("META-INF/sql/import-data.sql")
//                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml");

        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
                .addAsLibraries(pomFile.resolve("org.hsqldb:hsqldb:2.6.1").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hamcrest:hamcrest:2.2").withTransitivity().asFile())
                .addClass(dmit2015.johnpetere.assignment03.config.Config.class)
                .addClasses(OscarReview.class,OscarRepository.class )
                .addAsResource("META-INF/persistence.xml")// not reading doesn't exist?
                .addAsResource("META-INF/sql/import-data.sql") // neither does this...
//                .addAsResource("META-INF/import-data.sql")// this does not exist
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
//TODO: https://moodle.nait.ca/pluginfile.php/13434211/mod_resource/content/0/Web%20API%20with%20JAX-RS.html?embed=1
//TODO: UR ON STEP 8 WRITE THE REST OF THE TESTS.... LOL
        return webArchive;
    }
    @Order(1)
    @Test
    void shouldFindAll(){
    // CHECK DATA SOURCES
        List<OscarReview> oscarReviewsList = _oscarReviewRepository.list();

        assertEquals(5, oscarReviewsList.size());

    }
    @Test
    @Order(2)
    void shouldCreate(){
        currentReview = new OscarReview();
        currentReview.setNominee("The Jar Jar Before The menace");
        currentReview.setCategory("film");
        currentReview.setUsername("JarJarLover");
        currentReview.setReview("In this story, Jar jar is hated by everyone, but loved by all star wars fans. JAR JAR IS LOVE");
        _oscarReviewRepository.create(currentReview);

        Optional<OscarReview> optionalReview = _oscarReviewRepository.findOptional(currentReview.id);
        assertTrue(optionalReview.isPresent());
        OscarReview existingOscar = optionalReview.get();

        assertNotNull(existingOscar);
        assertEquals(currentReview.getReview(), existingOscar.getReview());
        assertEquals(currentReview.getCategory(), existingOscar.getCategory());
        assertEquals(currentReview.getUsername(), existingOscar.getUsername());
        assertEquals(currentReview.getNominee(), existingOscar.getNominee());
    }
    @Order(3)
    @Test
    void shouldFindOne(){
        final int oscarReviewID = 1;

        Optional<OscarReview> optionalReview = _oscarReviewRepository.findOptional(oscarReviewID);
        assertTrue(optionalReview.isPresent());
        OscarReview existingOscar = optionalReview.get();
        assertNotNull(existingOscar);
        assertEquals(existingOscar.getReview(), "Jar jar lives");
        assertEquals(existingOscar.getCategory(), "film");
        assertEquals(existingOscar.getUsername(), "JarJarIsBest");
        assertEquals(existingOscar.getNominee(), "jar jar binks");

    }
    @Order(4)
    @Test
    void shouldUpdate(){
        final int oscarReviewID = 1;

        Optional<OscarReview> optionalReview = _oscarReviewRepository.findOptional(oscarReviewID);
        assertTrue(optionalReview.isPresent());
        OscarReview existingOscar = optionalReview.get();

        currentReview = existingOscar;
        currentReview.setCategory("effects");
        currentReview.setNominee("updatedNOM");
        currentReview.setUsername("UpdateUser");
        currentReview.setReview("updatedReview");
        currentReview.setLastModifiedDateTime(LocalDateTime.now());

        _oscarReviewRepository.update(currentReview);

        Optional<OscarReview> optionalOscar = _oscarReviewRepository.findOptional(currentReview.getId());
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
    @Order(5)
    void shouldDelete(){
        final int oscarReviewID = 1;

        Optional<OscarReview> optionalReview = _oscarReviewRepository.findOptional(oscarReviewID);
        assertTrue(optionalReview.isPresent());
        OscarReview existingOscar = optionalReview.get();
        assertNotNull(existingOscar);
        _oscarReviewRepository.delete(existingOscar.getId());

        optionalReview = _oscarReviewRepository.findOptional(oscarReviewID);
        assertTrue(optionalReview.isEmpty());
    }
}