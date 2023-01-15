package com.techelevator.logger;

import com.techelevator.models.VendingMachineItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SalesLogger {
    private String [] itemNames;
    private int[] regularPriceSales;
    private int[] discountPriceSales;

    public SalesLogger(List<VendingMachineItem> itemList){
        itemNames = new String[itemList.size()];
        regularPriceSales = new int[itemList.size()];
        discountPriceSales= new int[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            itemNames[i] = itemList.get(i).getName();
            regularPriceSales[i] = 0;
            discountPriceSales[i] = 0;

        }
    }
    public void itemSold(VendingMachineItem item, boolean isSaleActive) {
        for (int i = 0; i < itemNames.length; i++) {
            if(itemNames[i].equals(item.getName())&& !isSaleActive){
                regularPriceSales[i] = regularPriceSales[i] +1;
            }else if(itemNames[i].equals(item.getName())&& isSaleActive){
                discountPriceSales[i] = discountPriceSales[i]+1;
            }
        }
    }
    public void writeSalesLog(List<VendingMachineItem> itemList){
        String dateAndTime = String.valueOf(LocalDateTime.now());
        dateAndTime=dateAndTime.replaceAll(":",".");
        File file = new File(dateAndTime + "Sales.txt");
        try(PrintWriter writer = new PrintWriter(file)){
            BigDecimal totalSales = new BigDecimal("0");
            writer.println("Taste Elevator Sales Report");
            for (int i = 0; i < itemNames.length; i++) {
                writer.println(itemNames[i]+"|" + regularPriceSales[i] + "|" + discountPriceSales[i]);
                BigDecimal discountPrice = itemList.get(i).getPrice().subtract(new BigDecimal("1"));
                totalSales= totalSales.add(itemList.get(i).getPrice().multiply(new BigDecimal(regularPriceSales[i]+"")));
                totalSales= totalSales.add(discountPrice.multiply(new BigDecimal(discountPriceSales[i]+"")));
            }
            writer.println("TOTAL SALES " + totalSales );
        } catch (FileNotFoundException e) {
            System.out.println("File is not found!");;
        }
    }
}
