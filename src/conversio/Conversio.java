package conversio;

import java.util.List;
import java.util.Date;
import java.util.Iterator;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * The Conversio class implements an conversion of a decimal number into
 * roman number. First command line argument is used as input.
 *
 * @author: Daniil Ivanov
 */
public class Conversio {
    private static SessionFactory factory;

    /**
    * Main entry point of application
    */
    public static void main(String[] args) {
        try {
            Configuration cfg = new Configuration().configure("conversio//hibernate.cfg.xml");
            factory = cfg.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Conversio c = new Conversio();
        Romanizer romanizer = new Romanizer();

        /* Add few numeral records in database */
        try {
        	Long numID1 = c.addNumeral(10,
        			romanizer.convertDecimalToRoman(Integer.toString(10)));
        	Long numID2 = c.addNumeral(101,
        			romanizer.convertDecimalToRoman(Integer.toString(101)));
        	Long numID3 = c.addNumeral(321,
        			romanizer.convertDecimalToRoman(Integer.toString(321)));

        	/* List down all the numerals */
            c.listNumerals();

            /* Update numeral's records */
            c.updateNumeral(numID1, new Date());

            /* Delete an numeral from the database */
            c.deleteNumeral(numID2);

            /* List down new list of the numerals */
            c.listNumerals();
        } catch (IllegalArgumentException e) {
        	System.out.println("Conversion failed: " + e.toString());
        }
    }

    /**
    * Appends data row in the database
    * 
    * @param  decimal  decimal numeral as integer value 
    * @param  roman    roman numeral as string value
    * @return  id of data row
    */
    public Long addNumeral(Integer decimal, String roman) {
        Session session = factory.openSession();
        Transaction tx = null;
        Long numeralID = null;
        try {
            tx = session.beginTransaction();
            Numeral numeral = new Numeral(decimal, roman, new Date());
            numeralID = (Long) session.save(numeral);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null)
            	tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return numeralID;
    }

    /**
    * Fetched data rows from the database
    */
    public void listNumerals() {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Numeral> numerals = session.createQuery("FROM Numeral").list();
            for (Iterator<Numeral> iterator = numerals.iterator(); iterator.hasNext(); ){
                Numeral numeral = iterator.next();
                System.out.print("Decimal: " + numeral.getDecimalNumeral());
                System.out.print(" Roman  : " + numeral.getRomanNumeral());
                System.out.println(" Timestamp: " + numeral.getTimestamp());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
    * Updates a data row in the database
    * 
    * @param  NumeralID  id of the row 
    * @param  timestamp  new timestamp to be saved in the row
    */
    /* Method to UPDATE salary for an numeral */
    public void updateNumeral(Long NumeralID, Date timestamp) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
         tx = session.beginTransaction();
         Numeral numeral = (Numeral)session.get(Numeral.class, NumeralID);
         numeral.setTimestamp(timestamp);
         session.update(numeral);
         tx.commit();
      } catch (HibernateException e) {
         if (tx!=null) tx.rollback();
         e.printStackTrace();
      } finally {
         session.close();
      }
   }

    /**
    * Deletes a data row from the database
    * 
    * @param  NumeralID  id of the row to be deleted 
    */
    public void deleteNumeral(Long NumeralID) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Numeral numeral = (Numeral)session.get(Numeral.class, NumeralID);
            session.delete(numeral);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
