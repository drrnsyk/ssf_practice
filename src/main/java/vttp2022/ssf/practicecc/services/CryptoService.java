package vttp2022.ssf.practicecc.services;

import java.io.Reader;
import java.io.StringReader;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.ssf.practicecc.models.Crypto;
import vttp2022.ssf.practicecc.models.CryptoPrice;

@Service
public class CryptoService {
    
    // API call from crypto compare
    public static final String URL = "https://min-api.cryptocompare.com/data/price";

    // inject in the key
    @Value("${API_KEY}")
    private String key;

    public Crypto getCrypto(String symbol, String currency) {

        // instantiate and declaration of variables
        Crypto crypto = new Crypto();
        
        String payloadStr;
        List<CryptoPrice> prices = new LinkedList<>();

        System.out.println("Getting Crypto prices from CryptoCompare.com");

        try {
            String url = UriComponentsBuilder.fromUriString(URL)
                            .queryParam("fsym", URLEncoder.encode(symbol, "UTF-8"))
                            .queryParam("tsyms", URLDecoder.decode(currency, "UTF-8"))
                            .queryParam("appid", key)
                            .toUriString();

            // create a request entity
            // create the GET request, GET url
            RequestEntity<Void> req = RequestEntity.get(url).build();
            // to make the call to OpenWeatherMap
            // need to create restTemplate
            RestTemplate template = new RestTemplate();
            // make the call
            ResponseEntity<String> resp;
            resp = template.exchange(req, String.class);
            // get the payload and do something with it
            payloadStr = resp.getBody();
            // prints out the payload 
            System.out.println("payload from API: " + payloadStr);
            // prints out the query string
            System.out.println("query string: " + currency);

        } catch (Exception ex) {
            System.err.printf("Error: %s\n", ex.getMessage());
            return crypto;
        }
        
        // spit the query string into individual currency request stored in an array
        String[] currencyArr = currency.split(",");

        // read payload (jsonstr) and convert to jsonobject
        Reader strReader = new StringReader(payloadStr);
        JsonReader jsonReader = Json.createReader(strReader);
        JsonObject payloadJsonObject = jsonReader.readObject();

        // // System.out.println("Currency = " + currency);
        // // String price = payloadJsonObject.getString(currency);
        // // System.out.println(payloadJsonObject.get(currency).toString());
        
        // get the object (decimal) from jsonobject and convert it to string, parse it to float

        for (int i = 0; i < currencyArr.length; i++) {
            
            CryptoPrice cryptoPrice = new CryptoPrice();
            cryptoPrice.setCurrencyName(currencyArr[i]);
            cryptoPrice.setPrice(Float.parseFloat(payloadJsonObject.get(currencyArr[i]).toString()));
            prices.add(cryptoPrice);
            // System.out.println(currencyArr[i]);
            // System.out.println(cryptoPrice.getCurrencyName());
            // System.out.println(cryptoPrice.getPrice());
            // System.out.println();
        }
        
        // // build the model
        // crypto.setCryptoSymbol(symbol);
        // cryptoPrice.setCurrencyName(currency);
        // cryptoPrice.setPrice(price);
        // crypto.setCryptoPrice(cryptoPrice);
        
        // System.out.println();

        // for (int i = 0; i < prices.size(); i++) {
        //     System.out.println(prices.get(i).getCurrencyName());
        // }
        
        crypto.setCryptoSymbol(symbol);
        crypto.setPrices(prices);

        return crypto;
    }

}
