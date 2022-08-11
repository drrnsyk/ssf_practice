package vttp2022.ssf.practicecc.models;

import java.util.LinkedList;
import java.util.List;

public class Crypto {
    
    private String cryptoSymbol;
    // private CryptoPrice cryptoPrice;
    private List<CryptoPrice> prices = new LinkedList<>();

    public String getCryptoSymbol() {
        return cryptoSymbol;
    }
    public void setCryptoSymbol(String cyptoSymbol) {
        this.cryptoSymbol = cyptoSymbol;
    }
    // public CryptoPrice getCryptoPrice() {
    //     return cryptoPrice;
    // }
    // public void setCryptoPrice(CryptoPrice cryptoPrice) {
    //     this.cryptoPrice = cryptoPrice;
    // }
    public List<CryptoPrice> getPrices() {
        return prices;
    }
    public void setPrices(List<CryptoPrice> prices) {
        this.prices = prices;
    }

}
