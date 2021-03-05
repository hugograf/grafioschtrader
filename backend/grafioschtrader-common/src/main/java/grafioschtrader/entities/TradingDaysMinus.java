package grafioschtrader.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import grafioschtrader.GlobalConstants;

@Entity
@Table(name = TradingDaysMinus.TABNAME)
@NamedStoredProcedureQuery(name = "TradingDaysMinusKey.copyTradingMinusToOtherStockexchange", procedureName = "copyTradingMinusToOtherStockexchange", parameters = {
    @StoredProcedureParameter(mode = ParameterMode.IN, name = "sourceIdStockexchange", type = Integer.class),
    @StoredProcedureParameter(mode = ParameterMode.IN, name = "targetIdStockexchange", type = Integer.class),
    @StoredProcedureParameter(mode = ParameterMode.IN, name = "dateFrom", type = LocalDate.class),
    @StoredProcedureParameter(mode = ParameterMode.IN, name = "dateTo", type = LocalDate.class) })
public class TradingDaysMinus {

  public static final String TABNAME = "trading_days_minus";

  @JsonIgnore
  @EmbeddedId
  private TradingDaysMinusKey tradingDaysMinusKey;

  public TradingDaysMinus() {
  }

  public TradingDaysMinus(Integer idStockexchange, LocalDate tradingDate) {
    this.tradingDaysMinusKey = new TradingDaysMinusKey(idStockexchange, tradingDate);
  }

  public Integer getIdStockexchange() {
    return tradingDaysMinusKey.idStockexchange;
  }

  @JsonFormat(pattern = GlobalConstants.STANDARD_DATE_FORMAT)
  public LocalDate getTradingDateMinus() {
    return tradingDaysMinusKey.tradingDateMinus;
  }

  public static class TradingDaysMinusKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_stockexchange")
    public Integer idStockexchange;

    @Column(name = "trading_date_minus")
    public LocalDate tradingDateMinus;

    public TradingDaysMinusKey() {
    }

    public TradingDaysMinusKey(Integer idStockexchange, LocalDate tradingDateMinus) {
      this.idStockexchange = idStockexchange;
      this.tradingDateMinus = tradingDateMinus;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o)
        return true;
      if (o == null || getClass() != o.getClass())
        return false;
      TradingDaysMinusKey that = (TradingDaysMinusKey) o;
      return Objects.equals(idStockexchange, that.idStockexchange)
          && Objects.equals(tradingDateMinus, that.tradingDateMinus);
    }

    @Override
    public int hashCode() {
      return Objects.hash(idStockexchange, tradingDateMinus);
    }

  }
}
