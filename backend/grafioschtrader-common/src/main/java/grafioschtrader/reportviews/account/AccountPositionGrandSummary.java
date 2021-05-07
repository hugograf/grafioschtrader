package grafioschtrader.reportviews.account;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import grafioschtrader.common.DataHelper;
import grafioschtrader.reportviews.DateTransactionCurrencypairMap;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Grand summary of all cash and security accounts.
 * 
 * @author Hugo Graf
 *
 */
public class AccountPositionGrandSummary {

  @Schema(description = "Currency of security, ISO 4217")
  public String mainCurrency;

  @JsonIgnore
  public double grandCashBalance = 0.0;

  @Schema(description = "Total value in the main currency of all portfolios including securities")
  public double grandValueMC = 0.0;

  public double grandCashBalanceMC = 0.0;
  public double grandExternalCashTransferMC = 0.0;
  public double grandCashTransferMC = 0.0;
  public double grandCashAccountTransactionFeeMC = 0.0;
  public double grandValueSecuritiesMC = 0.0;
  public double grandGainLossSecuritiesMC = 0.0;
  public double grandGainLossCurrencyMC = 0.0;
  public double grandAccountFeesMC = 0.0;
  public double grandAccountInterestMC = 0.0;


  public double grandAccountInterestLastCloseMC = 0.0;
  
  private int precisionMC;

  public List<AccountPositionGroupSummary> accountPositionGroupSummaryList = new ArrayList<>();

  public AccountPositionGrandSummary(String mainCurrency, int precisionMC) {
    this.mainCurrency = mainCurrency;
    this.precisionMC = precisionMC;
  }

  public void calcTotals(final DateTransactionCurrencypairMap dateTransactionCurrencypairMap) {
    for (AccountPositionGroupSummary accountPositionGroupSummary : accountPositionGroupSummaryList) {
      accountPositionGroupSummary.calcTotals(dateTransactionCurrencypairMap);
      grandCashBalance += accountPositionGroupSummary.groupCashBalance;
      grandExternalCashTransferMC += accountPositionGroupSummary.groupExternalCashTransferMC;
      grandCashTransferMC += accountPositionGroupSummary.groupCashTransferMC;
      grandCashAccountTransactionFeeMC += accountPositionGroupSummary.groupCashAccountTransactionFeeMC;
      grandValueMC += accountPositionGroupSummary.groupValueMC;
      grandCashBalanceMC += accountPositionGroupSummary.groupCashBalanceMC;
      grandValueSecuritiesMC += accountPositionGroupSummary.groupValueSecuritiesMC;
      grandGainLossSecuritiesMC += accountPositionGroupSummary.groupGainLossSecuritiesMC;
      grandGainLossCurrencyMC += accountPositionGroupSummary.groupGainLossCurrencyMC;
      grandAccountFeesMC += accountPositionGroupSummary.groupAccountFeesMC;
      grandAccountInterestMC += accountPositionGroupSummary.groupAccountInterestMC;
      grandAccountInterestLastCloseMC += accountPositionGroupSummary.groupAccountInterestLastCloseMC;
    }
  }

 

  public double getGrandCashAccountTransactionFeeMC() {
    return DataHelper.round(grandCashAccountTransactionFeeMC, precisionMC);
  }

  public double getGrandValueMC() {
    return DataHelper.round(grandValueMC, precisionMC);
  }

  public double getGrandCashBalanceMC() {
    return DataHelper.round(grandCashBalanceMC, precisionMC);
  }

  public double getGrandExternalCashTransferMC() {
    return DataHelper.round(grandExternalCashTransferMC, precisionMC);
  }

  public double getGrandCashTransferMC() {
    return DataHelper.round(grandCashTransferMC, precisionMC);
  }

  public double getGrandValueSecuritiesMC() {
    return DataHelper.round(grandValueSecuritiesMC, precisionMC);
  }

  public double getGrandGainLossSecuritiesMC() {
    return DataHelper.round(grandGainLossSecuritiesMC, precisionMC);
  }

  public double getGrandGainLossCurrencyMC() {
    return DataHelper.round(grandGainLossCurrencyMC, precisionMC);
  }

  public double getGrandAccountFeesMC() {
    return DataHelper.round(grandAccountFeesMC, precisionMC);
  }

  public double getGrandAccountInterestMC() {
    return DataHelper.round(grandAccountInterestMC, precisionMC);
  }

  public double getGrandAccountInterestLastCloseMC() {
    return DataHelper.round(grandAccountInterestLastCloseMC, precisionMC);
  }

  @Override
  public String toString() {
    return "AccountPositionGrandSummary [mainCurrency=" + mainCurrency + ", grandBalance=" + grandCashBalance
        + ", grandValueMC=" + grandValueMC + ", grandSaldoMC=" + grandCashBalanceMC + ", grandExternalTransferMC="
        + grandExternalCashTransferMC + ", grandTransferMC=" + grandCashTransferMC + ", grandValueSecuritiesMC="
        + grandValueSecuritiesMC + ", grandGainLossSecuritiesMC=" + grandGainLossSecuritiesMC
        + ", grandGainLossCurrencyMC=" + grandGainLossCurrencyMC + ", grandAccountFeesMC=" + grandAccountFeesMC
        + ", grandAccountInterestMC=" + grandAccountInterestMC + ",  grandAccountInterestLastCloseMC="
        + grandAccountInterestLastCloseMC + "]";
  }

}
