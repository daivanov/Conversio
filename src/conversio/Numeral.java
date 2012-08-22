package conversio;

import java.util.Date;

/**
 * The Numeral class mapping between database and the application
 *
 * @author: Daniil Ivanov
 */
public class Numeral {
   private Long id;
   private Integer decimalNumeral;
   private String romanNumeral;
   private Date timestamp;
   private Numeral() {
   }

   public Numeral(Integer decimalNumeral, String romanNumeral, Date timestamp) {
      this.decimalNumeral = decimalNumeral;
      this.romanNumeral = romanNumeral;
      this.timestamp = timestamp;
   }

   public Long getId() {
      return id;
   }

   private void setId(Long id) {
      this.id = id;
   }

   public Integer getDecimalNumeral() {
      return decimalNumeral;
   }

   public void setDecimalNumeral(Integer decimalNumeral) {
      this.decimalNumeral = decimalNumeral;
   }

   public String getRomanNumeral() {
      return romanNumeral;
   }

   public void setRomanNumeral(String romanNumeral) {
      this.romanNumeral = romanNumeral;
   }

   public Date getTimestamp() {
      return timestamp;
   }

   public void setTimestamp(Date timestamp) {
      this.timestamp = timestamp;
   }
}
