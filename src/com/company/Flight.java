package com.company;

public class Flight {
    private String id;
    private String price;
    private String taxes;
    private String firstFlightFrom;
    private String firstFlightChangeStop ="";
    private String firstFlightTo;
    private String secondFlightFrom;
    private String secondFlightChangeStop ="";
    private String secondFlightTo;
    private String departueTime1;
    private String arivalTime1;
    private String departueTime2;
    private String arivalTime2;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }

    public String getFirstFlightFrom() {
        return firstFlightFrom;
    }

    public void setFirstFlightFrom(String firstFlightFrom) {
        this.firstFlightFrom = firstFlightFrom;
    }

    public String getFirstFlightChangeStop() {
        return firstFlightChangeStop;
    }

    public void setFirstFlightChangeStop(String firstFlightChangeStop) {
        this.firstFlightChangeStop = firstFlightChangeStop;
    }

    public String getFirstFlightTo() {
        return firstFlightTo;
    }

    public void setFirstFlightTo(String firstFlightTo) {
        this.firstFlightTo = firstFlightTo;
    }

    public String getSecondFlightFrom() {
        return secondFlightFrom;
    }

    public void setSecondFlightFrom(String secondFlightFrom) {
        this.secondFlightFrom = secondFlightFrom;
    }

    public String getSecondFlightChangeStop() {
        return secondFlightChangeStop;
    }

    public void setSecondFlightChangeStop(String secondFlightChangeStop) {
        this.secondFlightChangeStop = secondFlightChangeStop;
    }

    public String getSecondFlightTo() {
        return secondFlightTo;
    }

    public void setSecondFlightTo(String secondFlightTo) {
        this.secondFlightTo = secondFlightTo;
    }

    public String getDepartueTime1() {
        return departueTime1;
    }

    public void setDepartueTime1(String departueTime1) {
        this.departueTime1 = departueTime1;
    }

    public String getArivalTime1() {
        return arivalTime1;
    }

    public void setArivalTime1(String arivalTime1) {
        this.arivalTime1 = arivalTime1;
    }

    public String getDepartueTime2() {
        return departueTime2;
    }

    public void setDepartueTime2(String departueTime2) {
        this.departueTime2 = departueTime2;
    }

    public String getArivalTime2() {
        return arivalTime2;
    }

    public void setArivalTime2(String arivalTime2) {
        this.arivalTime2 = arivalTime2;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id='" + id + '\'' +
                ", price='" + price + '\'' +
                ", taxes='" + taxes + '\'' +
                ", firstFlightFrom='" + firstFlightFrom + '\'' +
                ", firstFlightChangeStop='" + firstFlightChangeStop + '\'' +
                ", firstFlightTo='" + firstFlightTo + '\'' +
                ", secondFlightFrom='" + secondFlightFrom + '\'' +
                ", secondFlightChangeStop='" + secondFlightChangeStop + '\'' +
                ", secondFlightTo='" + secondFlightTo + '\'' +
                ", departueTime1='" + departueTime1 + '\'' +
                ", arivalTime1='" + arivalTime1 + '\'' +
                ", departueTime2='" + departueTime2 + '\'' +
                ", arivalTime2='" + arivalTime2 + '\'' +
                '}';
    }
}
