package com.techelevator.models;

import java.math.BigDecimal;

public class VendingMachineItem {
    //instance variables
    private String type;
    private BigDecimal price;
    private String slot;
    private String name;
    private int remainingQuantity;

    //constructor
    public VendingMachineItem(String type, BigDecimal price, String slot, String name) {
        this.type = type;
        this.price = price;
        this.slot = slot;
        this.name = name;
        this.remainingQuantity=6;
    }

    //getters- no setters, read from file
    public String getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    @Override
    public String toString(){
        if(remainingQuantity==0){
            return slot+ ": " + name+"-- NO LONGER AVAILABLE";
        }
        return slot + ": " + name + " $" + price + " "+type + " " + remainingQuantity + " remaining";
    }
    public String dispenseMessage(){
        if(type.equals("Munchy")){
            return "Munchy, Munchy, so Good!";
        } else if(type.equals("Candy")){
            return "Sugar, Sugar, so Sweet!";
        } else if(type.equals("Drink")) {
            return "Drinky, Drinky, Slurp Slurp!";
        } else if(type.equals("Gum")) {
            return "Chewy, Chewy, Lots O Bubbles!";
        }else {
            return"";
        }
    }
    public void dispenseItem(){
        this.remainingQuantity --;

    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }
}
