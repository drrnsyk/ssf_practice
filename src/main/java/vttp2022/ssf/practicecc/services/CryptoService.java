package vttp2022.ssf.practicecc.services;

import java.io.Reader;
import java.io.StringReader;
import java.net.URLEncoder;

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
        CryptoPrice cryptoPrice = new CryptoPrice();
        String payloadStr;

        System.out.println("Getting Crypto prices from CryptoCompare.com");

        try {
            String url = UriComponentsBuilder.fromUriString(URL)
                            .queryParam("fsym", URLEncoder.encode(symbol, "UTF-8"))
                            .queryParam("tsyms", URLEncoder.encode(currency, "UTF-8"))
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

        } catch (Exception ex) {
            System.err.printf("Error: %s\n", ex.getMessage());
            return crypto;
        }
        
        // read payload (jsonstr) and convert to jsonobject
        Reader strReader = new StringReader(payloadStr);
        JsonReader jsonReader = Json.createReader(strReader);
        JsonObject payloadJsonObject = jsonReader.readObject();

        //System.out.println("Currency = " + currency);
        // String price = payloadJsonObject.getString(currency);
        // System.out.println(payloadJsonObject.get(currency).toString());
        
        // get the object (decimal) from jsonobject and convert it to string, parse it to float
        Float price = Float.parseFloat(payloadJsonObject.get(currency).toString());

        // build the model
        crypto.setCyptoSymbol(symbol);
        cryptoPrice.setCurrencyName(currency);
        cryptoPrice.setPrice(price);
        crypto.setCryptoPrice(cryptoPrice);

        return crypto;
    }

}
