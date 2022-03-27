package dmit2015.johnpetere.assignment06.view;


import dmit2015.johnpetere.assignment06.entity.Bill;
import dmit2015.johnpetere.assignment06.repository.BillRepository;

import lombok.Getter;
import org.omnifaces.util.Messages;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Named("currentBillListController")
@ViewScoped
public class BillListController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private BillRepository _billRepository;

    @Getter
    private List<Bill> billList;

    @Getter List<Bill> ouststangBills;
    @PostConstruct  // After @Inject is complete
    public void init() {
        try {
            billList = _billRepository.listByDate();

//            ouststangBills = billList.stream()
//                    .findAny(bill -> bill.getPaymentBalance().doubleValue()   < BigDecimal.ZERO.doubleValue());
            ouststangBills = billList.stream().filter(bill -> bill.getPaymentBalance().doubleValue() > BigDecimal.ZERO.doubleValue()).toList();
        } catch (Exception ex) {
            Messages.addGlobalError(ex.getMessage());
        }
    }
}