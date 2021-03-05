package grafioschtrader.validation;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {

  private String startField;
  private String endField;

  @Override
  public void initialize(DateRange dateRange) {
    startField = dateRange.start();
    endField = dateRange.end();
  }

  @Override
  public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
    try {
      Object startDate = getFieldValue(object, startField);
      if (startDate instanceof LocalDate) {
        LocalDate endLocalDate = (LocalDate) getFieldValue(object, endField);
        return (((LocalDate) startDate).isBefore(endLocalDate));
      } else {
        Date endDate = (Date) getFieldValue(object, endField);
        return ((Date) startDate).before(endDate);
      }
    } catch (Throwable e) {
      // log error
      return false;
    }
  }

  private Object getFieldValue(Object object, String fieldName) throws Exception {
    Class<?> clazz = object.getClass();
    Field field = clazz.getDeclaredField(fieldName);
    field.setAccessible(true);
    return field.get(object);
  }

}
