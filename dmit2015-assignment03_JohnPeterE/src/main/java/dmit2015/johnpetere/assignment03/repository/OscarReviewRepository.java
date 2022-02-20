package dmit2015.johnpetere.assignment03.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.johnpetere.assignment03.entity.OscarReview;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.glassfish.jaxb.core.v2.model.core.ID;

@ApplicationScoped
@Transactional
public class OscarReviewRepository extends AbstractJpaRepository<OscarReview, ID> {

    public OscarReviewRepository() {
        super(OscarReview.class);
    }

}

