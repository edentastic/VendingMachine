package com.techelevator.ui;

import com.techelevator.models.VendingMachineItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

/**
 * Responsibilities: This class should handle receiving ALL input from the User
 * 
 * Dependencies: None
 */
public class UserInput
{
    private static Scanner scanner = new Scanner(System.in);

    public static String getHomeScreenOption()
    {
        System.out.println("What would you like to do?");
        System.out.println();

        System.out.println("D) Display Vending Machine Items");
        System.out.println("P) Purchase");
        System.out.println("E) Exit");

        System.out.println();
        System.out.print("Please select an option: ");

        String selectedOption = scanner.nextLine();
        String option = selectedOption.trim().toUpperCase();

        if(option.equals("S")){
            return "sales report";
        }
        if (option.equals("D"))        {
            return "display";
        }
        else if (option.equals("P"))        {
            return "purchase";
        }
        else if (option.equals("E"))        {
            return "exit";
        }else{
            return "";
        }

    }

    public static String getPurchaseScreenOption(BigDecimal userMoney)    {
        System.out.println("What would you like to do?");
        System.out.println();

        System.out.println("(M) Feed Money");
        System.out.println("(S) Select Item");
        System.out.println("(F) Finish Transaction");

        System.out.println();
        System.out.println("Current Money Provided: $" + userMoney);
        System.out.print("Please select an option: ");

        String selectedOption = scanner.nextLine();
        String option = selectedOption.trim().toUpperCase();

        if (option.equals("M"))
        {
            return "Feed Money";
        }
        else if (option.equals("S"))
        {
            return "Select Item";
        }
        else if (option.equals("F"))
        {
            return "Finish Transaction";
        }
        else
        {
            return "";
        }

    }
    public static BigDecimal feedMoney(){
        System.out.println("How much money are you adding?");
        System.out.println("Please enter a $1, $5, $10, or $20");
        String moneyAmount = scanner.nextLine();
        BigDecimal money = new BigDecimal("0");
        if(moneyAmount.equals("1") || moneyAmount.equals("5") || moneyAmount.equals("10") || moneyAmount.equals("20")){
          money = new BigDecimal(moneyAmount) ;
        }
        return money;
    }

    public static String itemSelection(){
        String selection="";
        System.out.println("Please enter the slot code for the item you'd like to purchase");
        selection=scanner.nextLine();
        return selection;
    }

}
