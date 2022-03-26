package dmit2015.johnpetere.assignment06.repository;

import common.jpa.AbstractJpaRepository;
import dmit2015.johnpetere.assignment06.entity.Bill;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class BillRepository extends AbstractJpaRepository<Bill, Long> {

    public BillRepository() {
        super(Bill.class);
    }

}

