
import currencyConverter.Currency;
import currencyConverter.MainWindow;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;

import org.junit.Test;



public class testCurrencyConverter 
{

    static ArrayList<Currency> currencies = Currency.init();


    /*************************************BOÎTE NOIRE*******************************/

    /*********Test de la méthode MainWondow.convert */

    /** Test la conversion avec des devises valides se trouvant dans la liste
     * @throws ParseException
     */
    @Test 
    public void testBoiteNoire1() throws ParseException {
        double amount = 4000.0;
        double expected = 4292.0;
        double conversion = MainWindow.convert("Euro", "US Dollar", currencies, amount);
        assertEquals(expected, conversion,0);
    }


    /** Test la conversion avec une dévise de départ invalide qui n'est pas dans la liste
     * @throws ParseException
     */
    @Test(expected = ParseException.class)
    public void testBoiteNoire2() throws ParseException {
        double amount = 4000.0;
        MainWindow.convert("Mexican Pesos", "US Dollar", currencies, amount);
    }

    /** Test la conversion avec une dévise d'arrivée invalide qui n'est pas dans la liste
      * @throws Exception
      */
      @Test(expected = Exception.class)
      public void testBoiteNoire3() throws ParseException {
           double amount = 4000.0;
           MainWindow.convert("US Dollar", "Chinese Yuan Remibi", currencies, amount);
      }
    /** Test la conversion des deux devises invalides
      * @throws Exception
      */
     @Test(expected = Exception.class)
    public void testBoiteNoire4() throws ParseException {
         double amount = 4000.0;
         MainWindow.convert("Roupie", "Chinese Yuan Renminbi", currencies, amount);
    }


/** Test pour les valeurs appartenant à l’intervalle du montant accepté
 * @throws ParseException
 */
@Test
public void testBoiteNoire5() throws ParseException {
    double amount = 4000.0;
    double expected = 5340.0;
    double conversion = Currency.convert(amount, 1.335);
    assertEquals(expected, conversion,0);
}

/** Test pour un montant valide à la borne inférieure
 * @throws Exception
 */
@Test
public void testBoiteNoire6() throws ParseException {
    double amount = 0.0;
    double expected = 0.0;
    double conversion = Currency.convert(amount, 1.335);
    assertEquals(expected, conversion,0);
}

/**
 * Test pour un montant valide à la borne supérieure
 * @throws ParseException
 */
@Test
public void testBoiteNoire7() throws ParseException {
    double amount = 1000000.0;
    double expected = 1335000.0;
    double conversion = Currency.convert(amount, 1.335);
    assertEquals(expected, conversion,0);
}

/** Test pour un montamt invalide proche de la borne inférieure
 * @throws Exception
 */
@Test(expected = Exception.class)
public void testBoiteNoire8() throws ParseException {
    double amount = -1.0;
    Currency.convert(amount, 1.335);
}
/** Test pour un montamt invalide éloigné de la borne inférieure
 * @throws Exception
 */
@Test(expected = Exception.class)
public void testBoiteNoire9() throws ParseException {
    double amount = -17600.0;
    Currency.convert(amount, 1.335);
}
/**
 * Test pour un montant invalide proche de la borne supérieure
 * @throws ParseException
 */
@Test(expected = Exception.class)
public void testBoiteNoire10() throws ParseException {
    double amount = 1000001.0;
    Currency.convert(amount, 1.335);

}
/** Test d' un montant invalide éloigné de la borne supéreiure
 * @throws Exception
 */
@Test(expected = Exception.class)
public void testBoiteNoire11() throws ParseException {
    double amount = 2000000;  
    Currency.convert(amount, 1.335);
}

//     /*************************************BOÎTE BLANCHE*******************************/


 
 /***Test pour le critère de couverture d'instructions***/

 /** Test de la Méthode mainwindow.convert 

 *@throws ParseException
 */
@Test
public void testBlanche1() {
    //shortName2 == Null: currency2 invalide 
    double result = MainWindow.convert("Euro", "", currencies, 4000.0);
    assertEquals(0.0, result, 0.01);

    //shortName2 != Null: les 2 devises sont valides 
    double amount = 50;
    double expected = 53.65;
    double conversion = MainWindow.convert("Euro", "US Dollar", currencies, amount);
    assertEquals(expected, conversion,0);
    }

/** Test de la méthode currency.java
 * @throws ParseException
*/
@Test
public void testBoiteBlanche2() throws ParseException{
    double amount = 50;
    Currency.convert(amount, 1.50);
}
/*******Tests pour le critère de couverture des arcs du graphe de flot de contrôle********/
/*Méthode mainwindow.convert  */

/** Arc1 : les 2 devises sont valides et se trouvent dans la liste
 *@throws ParseException
 */
@Test
public void testBoiteBlanche3() throws ParseException{
    double amount = 50;
    double expected = 53.65;
    double conversion = MainWindow.convert("Euro", "US Dollar", currencies, amount);
    assertEquals(expected, conversion,0);
}
/** Arc2 : la devise d'arrivée est invalide 
 *@throws ParseException
 */
@Test
public void testBoiteBlanche4() throws ParseException{
    double result = MainWindow.convert("Euro", "", currencies, 4000.0);
    assertEquals(0.0, result, 0.01);

}
/** Arc3: la devise de départ est invalide et n'est pas dans la liste
 * @throws ParseException
 */
@Test
public void testBoiteBlanche5() throws ParseException{
    double result =  MainWindow.convert("Roupie", "US Dollar", currencies, 4000.0);
    assertEquals(0.0, result, 0.01); 
}


/* pour la méthode currency.convert on a un seul arc et il a été déjà testé à la couverture d'instruction */ 

/********* Critère de couverture des chemins indépendants du graphe de flot de contrôle 
 * (Complexité cyclomatique = 5  pour la méthode mainWindow.convert)*/

/** Chemin 1 : les 2 devises sont valides et se trouvent dans la liste
 *@throws ParseException
 */
@Test
public void testBoiteBlanche6() throws ParseException{
    double amount = 50;
    double expected = 53.65;
    double conversion = MainWindow.convert("Euro", "US Dollar", currencies, amount);
    assertEquals(expected, conversion,0);
}
/** chemin2: la devise d'arrivée est invalide et n'est pas dans la liste
 *@throws ParseException
 */
@Test
public void testBoiteBlanche7() throws ParseException{
   double result = MainWindow.convert("Euro", "Mexican Pesos", currencies, 4000.0);
   assertEquals(0.0, result, 0.01);
}

/** chemin3: la devise de départ est invalide et n'est pas dans la liste 
 * @throws ParseException
 */
@Test
public void testBoiteBlanche8() throws ParseException{
   double result =  MainWindow.convert("Roupie", "US Dollar", currencies, 4000.0);
   assertEquals(0.0, result, 0.01); 
}

/** chemin4: Les 2 devises sont invalides  */
@Test
public void testBoiteBlanche9() throws ParseException{
    double result =  MainWindow.convert("Roupie", "Mexican Pesos", currencies, 4000.0);
    assertEquals(0.0, result, 0.01); 
}
/********* Critère de couverture des conditions: il n y a pas de conditions composés dans
 * les 2 méthodes ***/

/********* Couverture des  i-chemins *******/
//Même chose que la couverture des chemins indépendants

}

