package grafioschtrader.connector.instrument.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import grafioschtrader.connector.instrument.finanzench.FinanzenCHFeedConnector;
import grafioschtrader.entities.Assetclass;
import grafioschtrader.entities.Currencypair;
import grafioschtrader.entities.Historyquote;
import grafioschtrader.entities.Security;
import grafioschtrader.entities.Stockexchange;
import grafioschtrader.test.start.GTforTest;
import grafioschtrader.types.AssetclassType;
import grafioschtrader.types.Language;
import grafioschtrader.types.SpecialInvestmentInstruments;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GTforTest.class)
class FinanzenCHFeedConnectorTest {

  @Test
  void updateSecurityLastPriceTest() {
    final List<Security> securities = new ArrayList<>();

    final FinanzenCHFeedConnector finanzenCHFeedConnector = new FinanzenCHFeedConnector();
    securities.add(createSecurityIntra("aktien/swiss_re-aktie"));
    securities.add(createSecurityIntra("etf/zkb-gold-etf-aa-chf-klasse"));
    securities.add(createSecurityIntra("index/SLI"));

    securities.parallelStream().forEach(security -> {
      try {
        finanzenCHFeedConnector.updateSecurityLastPrice(security);
      } catch (final Exception e) {
        e.printStackTrace();
      }
      assertThat(security.getSLast()).as("Security %s", security.getIdConnectorIntra()).isNotNull().isGreaterThan(0.0);
    });
  }

  @Test
  void getEodSecurityHistoryTest() {
    final List<Security> securities = new ArrayList<>();

    final FinanzenCHFeedConnector finanzenCHFeedConnector = new FinanzenCHFeedConnector();

    final DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(Locale.GERMAN);
    final LocalDate from = LocalDate.parse("03.12.2018", germanFormatter);
    final LocalDate to = LocalDate.parse("25.10.2019", germanFormatter);

    final Date fromDate = Date.from(from.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    final Date toDate = Date.from(to.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

    securities.add(createSecurity("index/historisch/FTSE_MIB", AssetclassType.EQUITIES,
        SpecialInvestmentInstruments.NON_INVESTABLE_INDICES, "MTA", true, true));

    securities.add(createSecurity("obligationen/historisch/impleniasf-anl_201424-obligation-2024-ch0253592767/SWX",
        AssetclassType.FIXED_INCOME, SpecialInvestmentInstruments.DIRECT_INVESTMENT, "SIX", true, true));

    securities.add(createSecurity("etf/historisch/ishares-european-property-yield-etf-ie00b0m63284/fse",
        AssetclassType.EQUITIES, SpecialInvestmentInstruments.ETF, "FSE", true, true));

    securities.add(createSecurity("kurse/historisch/UBS/SWL", AssetclassType.EQUITIES,
        SpecialInvestmentInstruments.DIRECT_INVESTMENT, "SIX", true, true));

    securities.parallelStream().forEach(security -> {
      List<Historyquote> historyquotes = new ArrayList<>();
      try {
        historyquotes = finanzenCHFeedConnector.getEodSecurityHistory(security, fromDate, toDate);
      } catch (final Exception e) {
        e.printStackTrace();
      }
      assertThat(historyquotes.size()).isGreaterThan(220);
    });
  }

  private Security createSecurityIntra(final String quoteFeedExtend) {
    return this.createSecurity(quoteFeedExtend, null, null, null, true, false);

  }

  private Security createSecurity(final String quoteFeedExtend, final AssetclassType assectClass,
      SpecialInvestmentInstruments specialInvestmentInstruments, String stockexchangeSymbol,
      final boolean securityMarket, final boolean history) {
    final Security security = new Security();
    if (history) {
      security.setUrlHistoryExtend(quoteFeedExtend);
    } else {
      security.setUrlIntraExtend(quoteFeedExtend);
    }

    if (assectClass != null && specialInvestmentInstruments != null) {
      security.setAssetClass(
          new Assetclass(assectClass, "Bond/Aktien Schweiz", specialInvestmentInstruments, Language.GERMAN));
    }
    security.setStockexchange(new Stockexchange("XXXX", stockexchangeSymbol, null, null, false, securityMarket));

    return security;
  }

  @Test
  void getEodCurrencyHistoryTest() {

    final FinanzenCHFeedConnector finanzenCHFeedConnector = new FinanzenCHFeedConnector();
    final List<Currencypair> currencies = new ArrayList<>();
    final DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        .withLocale(Locale.GERMAN);
    final LocalDate from = LocalDate.parse("08.03.2016", germanFormatter);
    final LocalDate to = LocalDate.parse("23.10.2019", germanFormatter);
    final Date fromDate = Date.from(from.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    final Date toDate = Date.from(to.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

    currencies.add(createCurrencypair("ETH", "CHF", "devisen/historisch/ethereum-franken-kurs"));
    currencies.add(createCurrencypair("EUR", "USD", "devisen/historisch/euro-us_dollar-kurs"));

    currencies.parallelStream().forEach(currencyPair -> {
      List<Historyquote> historyquote = new ArrayList<>();
      try {
        historyquote = finanzenCHFeedConnector.getEodCurrencyHistory(currencyPair, fromDate, toDate);
      } catch (Exception e) {
        e.printStackTrace();
      }
      System.out.println("Size:" + historyquote.size());
      assertThat(historyquote.size()).isGreaterThan(1000);

    });
  }

  private Currencypair createCurrencypair(final String fromCurrency, String toCurrency, final String urlHistoryExtend) {
    Currencypair currencypair = ConnectorTestHelper.createCurrencyPair("USD", "CHF");
    currencypair.setUrlHistoryExtend(urlHistoryExtend);
    return currencypair;
  }

}
