package vttp2022.ssf.practicecc.models;

public class Crypto {
    
    private String cryptoSymbol;
    private CryptoPrice cryptoPrice;

    public String getCryptoSymbol() {
        return cryptoSymbol;
    }
    public void setCryptoSymbol(String cyptoSymbol) {
        this.cryptoSymbol = cyptoSymbol;
    }
    public CryptoPrice getCryptoPrice() {
        return cryptoPrice;
    }
    public void setCryptoPrice(CryptoPrice cryptoPrice) {
        this.cryptoPrice = cryptoPrice;
    }


}
