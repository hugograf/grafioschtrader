package grafioschtrader.data.verification;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import grafioschtrader.common.DataHelper;
import grafioschtrader.dto.CashAccountTransfer;
import grafioschtrader.entities.Transaction;
import grafioschtrader.exceptions.DataViolationException;
import grafioschtrader.repository.TransactionJpaRepository;
import grafioschtrader.test.start.GTforTest;
import grafioschtrader.types.TransactionType;

/**
 * It checks the total amount of a transaction.
 * TODO It should be integrated in future test.  
 * @author hugo
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GTforTest.class)
class CheckCashaccountAmountTransaction {

  @Autowired
  TransactionJpaRepository transactionJpaRepository;

  @Test
  @Disabled
  void testSingleTransaction() {
    Map<Integer, Transaction> openPositionMap  = new HashMap<>();
    List<Transaction> transactions = this.transactionJpaRepository.findByIdTenantOrderByTransactionTimeDesc(7);
    for (Transaction transaction : transactions) {
      try {
        if(transaction.getSecurity() == null) {
          transaction.validateCashaccountAmount(null);
        } else if(!transaction.isMarginClosePosition()) {
          if(transaction.isMarginOpenPosition()) {
            transaction.setSecurityRisk(DataHelper.round(Math.abs(transaction.validateSecurityGeneralCashaccountAmount(0))
                * (transaction.getTransactionType() == TransactionType.ACCUMULATE ? 1.0 : -1.0)));
            openPositionMap.put(transaction.getIdTransaction(), transaction);
          }
          transaction.validateCashaccountAmount(null);
        } else {
          // It must be margin close transaction
          transaction.validateCashaccountAmount(openPositionMap.get(transaction.getConnectedIdTransaction()));
        }
      } catch (DataViolationException e) {
        System.out.println(transaction);
        System.out.println();
      }
    }
  }

  @Test
  @Disabled
  void testDoubleTransaction() {
    List<Transaction> transactions = this.transactionJpaRepository.findAll(sortByIdAsc());
    final Comparator<Transaction> comparator = (t1, t2) -> t1.getIdTransaction().compareTo(t2.getIdTransaction());

    for (Transaction transaction : transactions) {
      if (transaction.getTransactionType() == TransactionType.WITHDRAWAL && transaction.isCashaccountTransfer()) {
        try {
          Transaction searchTrans = new Transaction();
          searchTrans.setIdTransaction(transaction.getConnectedIdTransaction());
          int index = Collections.binarySearch(transactions, searchTrans, comparator);
          CashAccountTransfer cashAccountTransfer = new CashAccountTransfer(transaction, transactions.get(index));
          cashAccountTransfer.validateWithdrawalCashaccountAmount();
        } catch (DataViolationException e) {
          System.out.println(transaction);
          System.out.println();
        }
      }
    }
  }

  private Sort sortByIdAsc() {
    return Sort.by(Sort.Direction.ASC, "idTransaction");
  }

}
