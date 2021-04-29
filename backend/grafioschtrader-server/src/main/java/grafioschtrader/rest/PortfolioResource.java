package grafioschtrader.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import grafioschtrader.entities.Portfolio;
import grafioschtrader.entities.User;
import grafioschtrader.reports.AccountPositionGroupSummaryReport;
import grafioschtrader.reports.SecurityDividendsReport;
import grafioschtrader.reports.SecurityTransactionCostReport;
import grafioschtrader.reportviews.account.AccountPositionGrandSummary;
import grafioschtrader.reportviews.securitydividends.SecurityDividendsGrandTotal;
import grafioschtrader.reportviews.transactioncost.TransactionCostGrandSummary;
import grafioschtrader.repository.CashaccountJpaRepository;
import grafioschtrader.repository.PortfolioJpaRepository;
import grafioschtrader.repository.helper.GroupCurrency;
import grafioschtrader.repository.helper.GroupPortfolio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(RequestMappings.PORTFOLIO_MAP)
@Tag(name = RequestMappings.PORTFOLIO, description = "Controller for portfolio")
public class PortfolioResource extends UpdateCreateDeleteWithTenantResource<Portfolio> {

  @Autowired
  PortfolioJpaRepository portfolioJpaRepository;

  @Autowired
  CashaccountJpaRepository cashaccountJpaRepository;

  @Autowired
  SecurityDividendsReport securityDividendsReport;

  @Autowired
  AccountPositionGroupSummaryReport accountPositionGroupSummaryReport;

  @Autowired
  SecurityTransactionCostReport securityTransactionCostReport;

  public PortfolioResource() {
    super(Portfolio.class);
  }

  @Operation(summary = "Get a portfolio by its Id", description = "", tags = { RequestMappings.PORTFOLIO })
  @GetMapping(value = "/{idPortfolio}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Portfolio> getPortfolioByIdPortfolio(
      @Parameter(description = "Id of portfolio", required = true) @PathVariable final Integer idPortfolio) {
    final User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
    return new ResponseEntity<>(portfolioJpaRepository.findByIdTenantAndIdPortfolio(user.getIdTenant(), idPortfolio),
        HttpStatus.OK);
  }

  @Operation(summary = "Get the portfofolio which the cash or security account belongs", description = "", tags = {
      RequestMappings.PORTFOLIO })
  @GetMapping(value = "/account/{idSecuritycashaccount}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Portfolio> getPortfolioByIdSecuritycashaccount(
      @Parameter(description = "Id of cash or security account", required = true) @PathVariable final Integer idSecuritycashaccount) {
    final User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
    return new ResponseEntity<>(portfolioJpaRepository.findBySecuritycashaccountList_idSecuritycashAccountAndIdTenant(
        idSecuritycashaccount, user.getIdTenant()), HttpStatus.OK);
  }

  @Operation(summary = "Get all portfolios for tenant sorted by portfolio name", description = "", tags = {
      RequestMappings.PORTFOLIO })
  @GetMapping(value = "/tenant", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Portfolio>> getPortfoliosForTenantOrderByName() {
    final User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
    return new ResponseEntity<>(portfolioJpaRepository.setExistingTransactionOnSecurityaccount(user.getIdTenant()),
        HttpStatus.OK);
  }

  @Operation(summary = "Get a summary of all cash and security accounts grouped by portfolio", description = "Best was to see value of teants portfolios until certain date", tags = {
      RequestMappings.PORTFOLIO })
  @GetMapping(value = "/securitycashaccountsummary/portfolio", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<AccountPositionGrandSummary> getAccountPositionSummaryGroupPortfolioTenant(
      @Parameter(description = "Until which date are transactions included", required = true) @RequestParam() @DateTimeFormat(iso = ISO.DATE) final Date untilDate) {
    final User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
    return new ResponseEntity<>(accountPositionGroupSummaryReport.getAccountGrandSummaryIdTenant(user.getIdTenant(),
        new GroupPortfolio(), untilDate), HttpStatus.OK);
  }

  @Operation(summary = "Get a summary of all cash and security accounts grouped by currency", description = "Best was to see value of teants portfolios until certain date", tags = {
      RequestMappings.PORTFOLIO })
  @GetMapping(value = "/securitycashaccountsummary/currency", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<AccountPositionGrandSummary> getAccountPositionSummaryGroupCurrencyTenant(
      @Parameter(description = "Until which date are transactions included", required = true) @RequestParam() @DateTimeFormat(iso = ISO.DATE) final Date untilDate) {
    final User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
    return new ResponseEntity<>(accountPositionGroupSummaryReport.getAccountGrandSummaryIdTenant(user.getIdTenant(),
        new GroupCurrency(), untilDate), HttpStatus.OK);
  }

  @GetMapping(value = "/dividends", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<SecurityDividendsGrandTotal> getDividenInterestByTenant(
      @RequestParam() final List<Integer> idsSecurityaccount, @RequestParam() final List<Integer> idsCashaccount) {

    final User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
    return new ResponseEntity<>(securityDividendsReport.getSecurityDividendsGrandTotalByTenant(user.getIdTenant(),
        idsSecurityaccount, idsCashaccount), HttpStatus.OK);
  }

  @GetMapping(value = "/transactioncost", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<TransactionCostGrandSummary> getTransactionCostGrandSummaryByTenant() {
    final User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
    return new ResponseEntity<>(securityTransactionCostReport.getTransactionCostGrandSummary(user.getIdTenant()),
        HttpStatus.OK);
  }

  @Override
  protected UpdateCreateDeleteWithTenantJpaRepository<Portfolio> getUpdateCreateJpaRepository() {
    return portfolioJpaRepository;
  }

}
