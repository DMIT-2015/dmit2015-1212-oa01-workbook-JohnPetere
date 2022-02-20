package dmit2015.johnpetere.assignment03.repository;

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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ArquillianExtension.class)
class OscarReviewRepositoryIT {
    @Inject
    private OscarReviewRepository _oscarReviewRepository;

    static OscarReview currentOscarReview; // review being added, found, update, delete .ect

    @Deployment
    public static WebArchive createDeployment(){
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        return ShrinkWrap.create(WebArchive.class,"test.war")
                .addAsLibraries(pomFile.resolve("org.hsqldb:hsqldb:2.6.1").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hamcrest:hamcrest:2.2").withTransitivity().asFile())
                .addClass(Config.class)
                .addClasses(OscarReview.class, OscarReviewRepository.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/sql/import-data.sql")
                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml");
    }
    @Order(1)
    @Test
    void shouldFindAll(){
    // CHECK DATA SOURCES
    }
}