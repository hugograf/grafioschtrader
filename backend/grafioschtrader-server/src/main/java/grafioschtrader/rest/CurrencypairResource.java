package grafioschtrader.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import grafioschtrader.connector.instrument.IFeedConnector;
import grafioschtrader.dto.ISecuritycurrencyIdDateClose;
import grafioschtrader.entities.Currencypair;
import grafioschtrader.entities.User;
import grafioschtrader.reportviews.currencypair.CurrencypairWithHistoryquote;
import grafioschtrader.reportviews.currencypair.CurrencypairWithTransaction;
import grafioschtrader.repository.CurrencypairJpaRepository;
import grafioschtrader.repository.HistoryquoteJpaRepository;
import grafioschtrader.repository.TransactionJpaRepository;
import grafioschtrader.search.SecuritycurrencySearch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 
 * @author Hugo Graf
 *
 *         S-OK
 */
@RestController
@RequestMapping(RequestMappings.CURRENCYPAIR_MAP)
@Tag(name = RequestMappings.CURRENCYPAIR, description = "Controller for the currency pair")
public class CurrencypairResource extends UpdateCreateResource<Currencypair> {

  @Autowired
  CurrencypairJpaRepository currencypairJpaRepository;

  @Autowired
  HistoryquoteJpaRepository historyquoteJpaRepository;

  @Autowired
  TransactionJpaRepository transactionJpaRepository;

  @GetMapping(value = "/{idSecuritycurrency}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Currencypair> getCurrencypairByIdSecuritycurrency(
      @PathVariable final Integer idSecuritycurrency) {
    return new ResponseEntity<>(currencypairJpaRepository.findById(idSecuritycurrency).orElse(null), HttpStatus.OK);
  }

  @Operation(summary = "Returns all currency pairs", 
      description = "", tags = { RequestMappings.CURRENCYPAIR })
  @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Currencypair>> getAllCurrencypairs() {
    return new ResponseEntity<>(currencypairJpaRepository.findAll(), HttpStatus.OK);
  }

  @GetMapping(value = "/{baseCurrency}/{quoteCurrency}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<Currencypair> findOrCreateCurrencypairByFromAndToCurrency(
      @PathVariable final String baseCurrency, @PathVariable final String quoteCurrency) {
    return new ResponseEntity<>(
        currencypairJpaRepository.findOrCreateCurrencypairByFromAndToCurrency(baseCurrency, quoteCurrency), HttpStatus.OK);
  }

  @GetMapping(value = "/{baseCurrency}/{quoteCurrency}/{dateString}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<CurrencypairWithHistoryquote> getCurrencypairWithHistoryquoteByIdSecuritycurrencyAndDate(
      @PathVariable final String baseCurrency, @PathVariable final String quoteCurrency,
      @PathVariable final String dateString) throws ParseException {
    ISecuritycurrencyIdDateClose historyquote = null;
    final Currencypair currencypair = this.currencypairJpaRepository.findByFromCurrencyAndToCurrency(baseCurrency,
        quoteCurrency);
    if (currencypair != null) {
      historyquote = historyquoteJpaRepository
          .getCertainOrOlderDayInHistorquoteByIdSecuritycurrency(currencypair.getIdSecuritycurrency(), dateString, false);
    }
    final CurrencypairWithHistoryquote currencypairWithHistoryquote = new CurrencypairWithHistoryquote(currencypair,
        historyquote);
    return new ResponseEntity<>(currencypairWithHistoryquote, HttpStatus.OK);
  }

  @GetMapping(value = "/feedConnectors", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<IFeedConnector>> getFeedConnectors() {
    return new ResponseEntity<>(currencypairJpaRepository.getFeedConnectors(true), HttpStatus.OK);
  }

  @GetMapping(value = "/usedCurrencypairs", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Currencypair>> getUsedCurrencypairs() {
    return new ResponseEntity<>(currencypairJpaRepository.getAllUsedCurrencypairs(), HttpStatus.OK);
  }

  @Override
  protected UpdateCreateJpaRepository<Currencypair> getUpdateCreateJpaRepository() {
    return currencypairJpaRepository;
  }

  @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Currencypair>> searchByCriteria(final SecuritycurrencySearch securitycurrencySearch) {
    return new ResponseEntity<>(currencypairJpaRepository.searchByCriteria(securitycurrencySearch), HttpStatus.OK);
  }

  ////////////////////////////////////////////////////////////
  // User depended Request
  ////////////////////////////////////////////////////////////
  @GetMapping(value = "/tenant", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Currencypair>> getCurrencypairInTransactionByTenant() {
    final Integer idTenant = ((User) SecurityContextHolder.getContext().getAuthentication().getDetails()).getIdTenant();
    return new ResponseEntity<>(currencypairJpaRepository.getCurrencypairInTransactionByTenant(idTenant),
        HttpStatus.OK);
  }

  /**
   * Returns all transactions for a certain currency pair of a tenant.
   * 
   * @param idCurrencypair
   * @return
   */
  @GetMapping(value = "/tenant/{idCurrencypair}", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<CurrencypairWithTransaction> findOrCreateCurrencypairByFromAndToCurrency(
      @PathVariable final Integer idCurrencypair) {
    final User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
    return new ResponseEntity<>(
        transactionJpaRepository.getTransactionForCurrencyPair(user.getIdTenant(), idCurrencypair), HttpStatus.OK);
  }

  @GetMapping(value = "/{idPortfolio}/portfolio", produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Currencypair>> findCurrencypairByPortfolioId(@PathVariable final Integer idPortfolio) {
    final Integer idTenant = ((User) SecurityContextHolder.getContext().getAuthentication().getDetails()).getIdTenant();
    return new ResponseEntity<>(
        currencypairJpaRepository.getCurrencypairInTransactionByPortfolioId(idPortfolio, idTenant), HttpStatus.OK);
  }

}
