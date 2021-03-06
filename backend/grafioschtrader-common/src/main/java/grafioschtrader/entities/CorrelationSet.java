package grafioschtrader.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import grafioschtrader.GlobalConstants;
import grafioschtrader.common.PropertyAlwaysUpdatable;
import grafioschtrader.types.SamplingPeriodType;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = CorrelationSet.TABNAME)
public class CorrelationSet extends TenantBaseID implements Serializable {
  
  private static final long serialVersionUID = 1L;

  public static final String TABNAME = "correlation_set";
  public static final String TABNAME_CORRELATION_INSTRUMENT = "correlation_instrument";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "id_correlation_set")
  private Integer idCorrelationSet;

  @JsonIgnore
  @Column(name = "id_tenant")
  private Integer idTenant;

  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 25)
  @PropertyAlwaysUpdatable
  @Column(name = "name")
  private String name;

  @Column(name = "note")
  @Size(max = GlobalConstants.FID_MAX_LETTERS)
  @PropertyAlwaysUpdatable
  private String note;

  @JsonFormat(pattern = GlobalConstants.STANDARD_DATE_FORMAT)
  @Column(name = "start_date")
  private LocalDate startDate;

  @Schema(description = "Sampling period of returns for correlation calculations")
  @Column(name = "sampling_period")
  @PropertyAlwaysUpdatable
  private byte samplingPeriod;

  @Schema(description = "Number of trading days or month used for rolling correlation calculations")
  @Column(name = "rolling")
  private Byte rolling;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  @JoinTable(name = TABNAME_CORRELATION_INSTRUMENT, joinColumns = {
      @JoinColumn(name = "id_correlation_set", referencedColumnName = "id_correlation_set") }, inverseJoinColumns = {
          @JoinColumn(name = "id_securitycurrency", referencedColumnName = "id_securitycurrency") })
  @ManyToMany(fetch = FetchType.EAGER)
  private List<Securitycurrency<?>> securitycurrencyList;

  private transient LocalDate endDate;
  private transient boolean isSortedByNames;

  public CorrelationSet() {
  }

  @JsonIgnore
  @Override
  public Integer getId() {
    return idCorrelationSet;
  }

  public SamplingPeriodType getSamplingPeriod() {
    return SamplingPeriodType.getSamplingPeriodTypeByValue(samplingPeriod);
  }

  public void setSamplingPeriod(SamplingPeriodType setSamplingPeriod) {
    this.samplingPeriod = setSamplingPeriod.getValue();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public Byte getRolling() {
    return rolling;
  }

  public void setRolling(Byte rolling) {
    this.rolling = rolling;
  }

  public Integer getIdCorrelationSet() {
    return idCorrelationSet;
  }

  @Override
  public Integer getIdTenant() {
    return idTenant;
  }

  @Override
  public void setIdTenant(Integer idTenant) {
    this.idTenant = idTenant;
  }

  public List<Securitycurrency<?>> getSecuritycurrencyList() {
    if (securitycurrencyList != null && !isSortedByNames) {
      Collections.sort(securitycurrencyList, Comparator.comparing(Securitycurrency::getName));
      isSortedByNames = true;
    }
    return securitycurrencyList;
  }

  public void setSecuritycurrencyList(List<Securitycurrency<?>> securitycurrencyList) {
    this.securitycurrencyList = securitycurrencyList;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  @Override
  public String toString() {
    return "CorrelationSet [idCorrelationSet=" + idCorrelationSet + ", idTenant=" + idTenant + ", name=" + name
        + ", note=" + note + ", startDate=" + startDate + ", samplingPeriod=" + samplingPeriod + ", rolling=" + rolling
        + ", securitycurrencyList=" + securitycurrencyList + "]";
  }

  public void removeInstrument(Integer idSecuritycurrency) {
    securitycurrencyList.removeIf(s -> s.idSecuritycurrency.equals(idSecuritycurrency));

  }

}
