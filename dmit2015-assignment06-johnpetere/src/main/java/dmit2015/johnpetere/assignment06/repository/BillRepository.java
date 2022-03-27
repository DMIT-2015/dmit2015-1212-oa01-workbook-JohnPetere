package dmit2015.johnpetere.assignment06.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.johnpetere.assignment06.entity.Bill;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Collections;
import java.util.List;

@ApplicationScoped
@Transactional
public class BillRepository extends AbstractJpaRepository<Bill, Long> {

    public BillRepository() {
        super(Bill.class);
    }
    public List<Bill> listByDate(){
        var queryCollection = getEntityManager().createQuery("""
                SELECT b FROM Bill b order by b.dueDate
                """).getResultList();

        return queryCollection;
    }
}

