package com.example.anthony.loot;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class LootItem {

    private int itemID;
    private String name;
    private byte[] image;
    private String itemDescription;
    private GregorianCalendar postDate;
    private int daysToKeep;
    private int yearsToKeep;

    public LootItem(String name, int daysToKeep, int yearsToKeep){
        this.name = name;
        this.postDate = new GregorianCalendar();
        this.daysToKeep = daysToKeep;
        this.yearsToKeep = yearsToKeep;
    }

    public LootItem(String name, int daysToKeep, int yearsToKeep, String itemDescription){
        this.name = name;
        this.postDate = new GregorianCalendar();
        this.daysToKeep = daysToKeep;
        this.yearsToKeep = yearsToKeep;
        this.itemDescription = itemDescription;
    }

    public LootItem(String name, int daysToKeep, int yearsToKeep, byte[] image){
        this.name = name;
        this.postDate = new GregorianCalendar();
        this.daysToKeep = daysToKeep;
        this.yearsToKeep = yearsToKeep;
        this.image = image;
    }

    public LootItem(String name, int daysToKeep, int yearsToKeep, String description, byte[] image){
        this.name = name;
        this.postDate = new GregorianCalendar();
        this.daysToKeep = daysToKeep;
        this.yearsToKeep = yearsToKeep;
        this.image = image;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public GregorianCalendar getPostDate() {
        return postDate;
    }

    public void setPostDate(GregorianCalendar postDate) {
        this.postDate = postDate;
    }

    public int getDaysToKeep() {
        return daysToKeep;
    }

    public void setDaysToKeep(int daysToKeep) {
        this.daysToKeep = daysToKeep;
    }

    public int getYearsToKeep() {
        return yearsToKeep;
    }

    public void setYearsToKeep(int yearsToKeep) {
        this.yearsToKeep = yearsToKeep;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    private GregorianCalendar getEndDate() {
        GregorianCalendar end = postDate;
        end.add(Calendar.DAY_OF_MONTH, daysToKeep);
        end.add(Calendar.YEAR, yearsToKeep);
        return end;
    }

    public boolean isTimeExpired() {
        return getDateDiff() < 0;
    }

    public int getDateDiff() {
        long milliseconds = getEndDate().getTimeInMillis() - postDate.getTimeInMillis();
        int days = (int) (milliseconds / (1000*60*60*24));
        return days;
    }

}
