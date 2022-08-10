package vttp2022.ssf.practicecc.models;

public class CryptoPrice {
    
    private String currencyName;
    private Float price;

    public String getCurrencyName() {
        return currencyName;
    }
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

}
