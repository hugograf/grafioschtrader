package grafioschtrader.search;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import grafioschtrader.types.AssetclassType;
import grafioschtrader.types.SpecialInvestmentInstruments;

public class SecuritycurrencySearch implements Serializable {
  private static final long serialVersionUID = 1L;

  private String isin;
  private AssetclassType assetclassType;
  private String name;
  private Integer idStockexchange;
  private String stockexchangeCounrtyCode;
  private String tickerSymbol;
  private String currency;

  private SpecialInvestmentInstruments specialInvestmentInstruments;
  private String subCategoryNLS;
  private boolean onlyTenantPrivate;
  private Boolean shortSecurity;
  private boolean excludeDerivedSecurity;

  private String idConnectorHistory;

  private String idConnectorIntra;

  @DateTimeFormat(pattern = "yyyyMMdd")
  private Date activeDate;
 
  private boolean withHoldings;

  public String getIsin() {
    return isin;
  }

  public void setIsin(String isin) {
    this.isin = isin;
  }

  public String getName() {
    return name;
  }

  public String getSubCategoryNLS() {
    return subCategoryNLS;
  }

  public void setSubCategoryNLS(String subCategoryNLS) {
    this.subCategoryNLS = subCategoryNLS;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getIdStockexchange() {
    return idStockexchange;
  }

  public void setIdStockexchange(Integer idStockexchange) {
    this.idStockexchange = idStockexchange;
  }

  public String getStockexchangeCounrtyCode() {
    return stockexchangeCounrtyCode;
  }

  public void setStockexchangeCounrtyCode(String stockexchangeCounrtyCode) {
    this.stockexchangeCounrtyCode = stockexchangeCounrtyCode;
  }

  public String getTickerSymbol() {
    return tickerSymbol;
  }

  public void setTickerSymbol(String tickerSymbol) {
    this.tickerSymbol = tickerSymbol;
  }

  public AssetclassType getAssetclassType() {
    return assetclassType;
  }

  public void setAssetclassType(AssetclassType assetclassType) {
    this.assetclassType = assetclassType;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public SpecialInvestmentInstruments getSpecialInvestmentInstruments() {
    return specialInvestmentInstruments;
  }

  public void setSpecialInvestmentInstruments(SpecialInvestmentInstruments specialInvestmentInstruments) {
    this.specialInvestmentInstruments = specialInvestmentInstruments;
  }

  public boolean isOnlyTenantPrivate() {
    return onlyTenantPrivate;
  }

  public void setOnlyTenantPrivate(boolean onlyTenantPrivate) {
    this.onlyTenantPrivate = onlyTenantPrivate;
  }

  public Date getActiveDate() {
    return activeDate;
  }

  public void setActiveDate(Date activeDate) {
    this.activeDate = activeDate;
  }

  public Boolean getShortSecurity() {
    return shortSecurity;
  }

  public void setShortSecurity(Boolean shortSecurity) {
    this.shortSecurity = shortSecurity;
  }

  public String getIdConnectorHistory() {
    return idConnectorHistory;
  }

  public void setIdConnectorHistory(String idConnectorHistory) {
    this.idConnectorHistory = idConnectorHistory;
  }

  public String getIdConnectorIntra() {
    return idConnectorIntra;
  }

  public void setIdConnectorIntra(String idConnectorIntra) {
    this.idConnectorIntra = idConnectorIntra;
  }

  public boolean isExcludeDerivedSecurity() {
    return excludeDerivedSecurity;
  }

  public void setExcludeDerivedSecurity(boolean excludeDerivedSecurity) {
    this.excludeDerivedSecurity = excludeDerivedSecurity;
  }

  public boolean getWithHoldings() {
    return withHoldings;
  }

  public void setWithHoldings(boolean withHoldings) {
    this.withHoldings = withHoldings;
  }

  @Override
  public String toString() {
    return "SecuritycurrencySearch [isin=" + isin + ", name=" + name + ", tickerSymbol=" + tickerSymbol + ", currency="
        + currency + ", assetclassType=" + assetclassType + ", specialInvestmentInstruments="
        + specialInvestmentInstruments + ", subCategoryNLS=" + subCategoryNLS + ", onlyTenantPrivate="
        + onlyTenantPrivate + ", shortSecurity=" + shortSecurity + ", activeDate=" + activeDate + "]";
  }

}
