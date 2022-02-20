package dmit2015.johnpetere.assignment03.repository;

import dmit2015.johnpetere.assignment03.entity.OscarReview;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**

 */
// managing the entity
// sort of like the DAL in ASP
@ApplicationScoped
@Transactional
//@PersistenceContext(unitName = "hsqldatabase-jpa-pu")
/**
 * @author John-Peter Eberhard
 * @version: 1.0
 * @date:  2021-01-04
 * CRUD functions for a entity called 'OscarReview'
 *
 */
public class OscarRepository {
    // You need to properly configure or create the entiyt class before anything
    // private void
    // add CRUD stuff
    @PersistenceContext(unitName = "hsqldatabase-jpa-pu")
    private EntityManager en;
    //Create
    public void add(OscarReview review){en.persist(review);}
    //READ sorta
    public Optional<OscarReview> findByID(long oscarID){
        Optional<OscarReview> optionalOscarReview = Optional.empty();

        try{
            OscarReview singleResultQuery = en.find(OscarReview.class, oscarID);
            if(singleResultQuery!= null){
                optionalOscarReview = Optional.of(singleResultQuery);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    return optionalOscarReview;
    }
    public List<OscarReview> findAll(){
        return en.createQuery(
                "SELECT x FROM OscarReview x", OscarReview.class
        ).getResultList();
    }
    public List<OscarReview> findAllOscarReviewByUsername(){
        return en.createQuery(
                "SELECT o FROM OscarReview o ORDER BY o.username",OscarReview.class
        ).getResultList();
    }


    //UPDATE
    public void update(OscarReview updatedReview) {
        Optional<OscarReview> optionalOscarReview = findByID(updatedReview.getId());
        if (optionalOscarReview.isPresent()) {
            OscarReview existingReview = optionalOscarReview.get();
            existingReview.setReview(updatedReview.getReview());
            existingReview.setCategory(updatedReview.getCategory());
            existingReview.setUsername(updatedReview.getUsername());
            existingReview.setNominee(updatedReview.getNominee());
            en.merge(existingReview);
            en.flush();

        }
    }
    //DELETE
    public void delete(long oscarID){
        Optional<OscarReview> optionalOscarReview = findByID(oscarID);
        if(optionalOscarReview.isPresent()){
            OscarReview existingOscar = optionalOscarReview.get();
            en.remove(existingOscar);
            en.flush();;
        }
        }


}
