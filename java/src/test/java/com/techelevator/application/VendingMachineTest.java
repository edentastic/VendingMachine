package com.techelevator.application;

import com.techelevator.logger.SalesLogger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class VendingMachineTest {
    private VendingMachine vendingMachine;
    private SalesLogger testSalesLogger;

    @Before
    public void createAndFillVendingMachine(){
        vendingMachine=new VendingMachine();
        vendingMachine.readFile();
        vendingMachine.setSalesLogger(new SalesLogger(vendingMachine.getItemsList()));
    }

    @Test
    public void test_dispense_send_in_A4_and_true_have__enough_money_return_true() {
        //arrange
        String selection = "A4";
        boolean isSaleActive = true;
        vendingMachine.addMoney(new BigDecimal("5"));

        //act
        boolean actual = vendingMachine.dispense(selection, isSaleActive);

        //assert
        Assert.assertTrue(actual);

    }
    @Test
    public void test_dispense_send_in_A1_and_true_have_0_65_enough_money_return_true() {
        //arrange
        String selection = "A1";
        boolean isSaleActive = true;
        vendingMachine.addMoney(new BigDecimal("0.65"));

        //act
        boolean actual = vendingMachine.dispense(selection, isSaleActive);

        //assert
        Assert.assertTrue(actual);

    }
    @Test
    public void test_dispense_send_in_A1_and_true_have_0_64_not_enough_money_return_false() {
        //arrange
        String selection = "A1";
        boolean isSaleActive = true;
        vendingMachine.addMoney(new BigDecimal("0.64"));

        //act
        boolean actual = vendingMachine.dispense(selection, isSaleActive);

        //assert
        Assert.assertFalse(actual);

    }
    @Test
    public void test_dispense_send_in_A1_and_false_have_1_65_enough_money_return_true() {
        //arrange
        String selection = "A1";
        boolean isSaleActive = false;
        vendingMachine.addMoney(new BigDecimal("1.65"));

        //act
        boolean actual = vendingMachine.dispense(selection, isSaleActive);

        //assert
        Assert.assertTrue(actual);

    }
    @Test
    public void test_dispense_send_in_A1_and_false_have_1_64_enough_money_return_false() {
        //arrange
        String selection = "A1";
        boolean isSaleActive = false;
        vendingMachine.addMoney(new BigDecimal("1.64"));

        //act
        boolean actual = vendingMachine.dispense(selection, isSaleActive);

        //assert
        Assert.assertFalse(actual);

    }
    @Test
    public void test_dispense_send_in_invalid_and_false_have_5_enough_money_return_false() {
        //arrange
        String selection = "Z4";
        boolean isSaleActive = false;
        vendingMachine.addMoney(new BigDecimal("5"));

        //act
        boolean actual = vendingMachine.dispense(selection, isSaleActive);

        //assert
        Assert.assertFalse(actual);

    }
    @Test
    public void test_dispense_send_in_empty_string_and_false_have_5_enough_money_return_false() {
        //arrange
        String selection = "";
        boolean isSaleActive = false;
        vendingMachine.addMoney(new BigDecimal("5"));

        //act
        boolean actual = vendingMachine.dispense(selection, isSaleActive);

        //assert
        Assert.assertFalse(actual);
    }

    @Test
    public void test_dispense_send_in_A1_string_and_false_have_5_enough_money_return_false() {
        //arrange
        String selection = "A1";
        boolean isSaleActive = false;
        vendingMachine.getItemsList().get(0).setRemainingQuantity(0);
        vendingMachine.addMoney(new BigDecimal("5"));

        //act
        boolean actual = vendingMachine.dispense(selection, isSaleActive);

        //assert
        Assert.assertFalse(actual);
    }

    @Test
    public void test_makeChange_$3_75() {
        //arrange
        BigDecimal money = new BigDecimal("3.75");
        vendingMachine.addMoney(money);
        int dollars=3;
        int quarters=3;
        int nickels=0;
        int dimes=0;

        String expected = "Your change is " + dollars + " dollars, " + quarters + " quarters, " +
                dimes + " dimes, and "+ nickels + " nickels.";

        //act
        String actual = vendingMachine.makeChange();

        //assert
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void test_makeChange_$7_25() {
        //arrange
        BigDecimal money = new BigDecimal("7.25");
        vendingMachine.addMoney(money);
        int dollars=7;
        int quarters=1;
        int nickels=0;
        int dimes=0;

        String expected = "Your change is " + dollars + " dollars, " + quarters + " quarters, " +
                dimes + " dimes, and "+ nickels + " nickels.";

        //act
        String actual = vendingMachine.makeChange();

        //assert
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void test_makeChange_$1_99() { //loses pennies
        //arrange
        BigDecimal money = new BigDecimal("1.99");
        vendingMachine.addMoney(money);
        int dollars=1;
        int quarters=3;
        int dimes=2;
        int nickels=0;

        String expected = "Your change is " + dollars + " dollars, " + quarters + " quarters, " +
                dimes + " dimes, and "+ nickels + " nickels.";

        //act
        String actual = vendingMachine.makeChange();

        //assert
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void test_makeChange_$0_00() { //loses pennies
        //arrange
        BigDecimal money = new BigDecimal("0");
        vendingMachine.addMoney(money);
        int dollars=0;
        int quarters=0;
        int dimes=0;
        int nickels=0;

        String expected = "Your change is " + dollars + " dollars, " + quarters + " quarters, " +
                dimes + " dimes, and "+ nickels + " nickels.";

        //act
        String actual = vendingMachine.makeChange();

        //assert
        Assert.assertEquals(expected,actual);
    }
}