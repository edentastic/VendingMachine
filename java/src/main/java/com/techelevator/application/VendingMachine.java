package com.techelevator.application;

import com.techelevator.logger.Logger;
import com.techelevator.logger.SalesLogger;
import com.techelevator.models.VendingMachineItem;
import com.techelevator.ui.UserInput;
import com.techelevator.ui.UserOutput;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendingMachine {
    //variables
    private List<VendingMachineItem> itemsList;
    private BigDecimal userMoney= new BigDecimal("0");
    private Logger logger = new Logger("Audit.txt");
    private SalesLogger salesLogger;

    public void run() {
        readFile();
        salesLogger = new SalesLogger(itemsList);
        while (true) //continuous loop
        {
            UserOutput.displayHomeScreen();
            String choice = UserInput.getHomeScreenOption();
            if (choice.equals("display")) {
                // display the vending machine slots
                UserOutput.displayVendingItems(itemsList);
            } else if (choice.equals("purchase")) {
                purchaseMenu();
            }else if(choice.equals("sales report")){
                salesLogger.writeSalesLog(itemsList);
            } else if (choice.equals("exit")) {
                // good bye
                break;
            }


        }
    }
    public void purchaseMenu(){
        boolean isSaleActive = false;
        while (true) {
            String choice = UserInput.getPurchaseScreenOption(userMoney);
            if (choice.equals("Feed Money")) {
                BigDecimal money = UserInput.feedMoney();
                addMoney(money);
            } else if (choice.equals("Select Item")) {
                UserOutput.displayVendingItems(itemsList);
                String selection = UserInput.itemSelection();
                boolean itemSold = dispense(selection, isSaleActive); //- change quantity-update userMoney
                if(itemSold && !isSaleActive){
                    isSaleActive = true;
                } else if(itemSold && isSaleActive){
                    isSaleActive =false;
                }
            } else if (choice.equals("Finish Transaction")) {
                UserOutput.displayMessage(makeChange());
                break;
            }
        }
    }

    public void readFile () {
        List<VendingMachineItem> itemsList = new ArrayList<>();
        File file = new File("catering1.csv");
        if (!file.exists()) {
            System.out.println("Error reading file, program exiting");
            System.exit(0);
        }
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] lineArray = line.split(",");
                String slot = lineArray[0];
                String name = lineArray[1];
                BigDecimal price = new BigDecimal(lineArray[2]);
                String type = lineArray[3];
                itemsList.add(new VendingMachineItem(type, price, slot, name));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error- File not readable");
        }
        this.itemsList = itemsList;
    }

    public void addMoney(BigDecimal money){
        userMoney=userMoney.add(money);
        logger.write(LocalDateTime.now() + " MONEY FED: $" + money + " $" + userMoney);
    }

    public boolean dispense(String selection, boolean isSaleActive){
        boolean doesItemExist = false;
        int itemIndex = -1;
        for (int i = 0; i <itemsList.size(); i++) {
            VendingMachineItem tempItem = itemsList.get(i);
            if (tempItem.getSlot().equalsIgnoreCase(selection)) {
                doesItemExist = true;
                itemIndex = i;
            }
        }
        if(!doesItemExist){
            UserOutput.displayMessage("Invalid Selection");
            return false;
        }
        //change the VendingMachine instance variables, give errors if invalid selection
        VendingMachineItem item = itemsList.get(itemIndex);
        BigDecimal price = item.getPrice();
        if(isSaleActive){
            price = price.subtract(new BigDecimal("1"));
        }
        if(item.getRemainingQuantity() < 1){
            UserOutput.displayMessage("Item is out of stock");
            return false;
        }if(userMoney.compareTo(price)<0){
            UserOutput.displayMessage("Not enough funds!");
            return  false;
        }
        UserOutput.displayMessage("Vending item");
        BigDecimal startMoney = userMoney;
        userMoney = userMoney.subtract(price);
        UserOutput.displayMessage(item.getName() + ": $" + item.getPrice() + " \nRemaining balance: $" + userMoney);
        UserOutput.displayMessage(item.dispenseMessage());
        item.dispenseItem();
        salesLogger.itemSold(item, isSaleActive);
        logger.write(LocalDateTime.now() + " " + item.getName() +" " + item.getSlot() + " $" + startMoney + " $"+userMoney);
        return true;

    }

    public String makeChange(){
        logger.write(LocalDateTime.now()+ " CHANGE GIVEN $" + userMoney + " $0.00");
        BigDecimal[] bigDecimalArray = userMoney.divideAndRemainder(new BigDecimal("1"));
        int dollars = bigDecimalArray[0].intValue() ; //down
        userMoney = bigDecimalArray[1].multiply(new BigDecimal (100));

      //99/25 round down
        bigDecimalArray = userMoney.divideAndRemainder(new BigDecimal("25"));
        int quarters = bigDecimalArray[0].intValue();
        userMoney = bigDecimalArray[1];
        //remainder
        //remainder/10 round down
        bigDecimalArray = userMoney.divideAndRemainder(new BigDecimal("10"));
        int dimes = bigDecimalArray[0].intValue();
        userMoney = bigDecimalArray [1];
        //remainder
         //remainder / 5 round down
        bigDecimalArray = userMoney.divideAndRemainder(new BigDecimal("5"));
        int nickels = bigDecimalArray[0].intValue();
        userMoney = bigDecimalArray[1];

        userMoney=new BigDecimal("0");
        return "Your change is " + dollars + " dollars, " + quarters + " quarters, " +
                dimes + " dimes, and "+ nickels + " nickels.";
    }

    //methods below only used for testing

    public List<VendingMachineItem> getItemsList() {
        return itemsList;
    }
    public void setLogger(Logger logger) {
        this.logger = logger;
    }
    public void setSalesLogger(SalesLogger salesLogger) {
        this.salesLogger = salesLogger;
    }
}


