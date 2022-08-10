package vttp2022.ssf.practicecc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.ssf.practicecc.models.Crypto;
import vttp2022.ssf.practicecc.services.CryptoService;

@Controller
@RequestMapping("/price")
public class CryptoController {

    @Autowired
    private CryptoService cryptoSvc;
    
    @GetMapping
    public String getPrice (@RequestParam String symbol, 
                            @RequestParam String currency,
                            Model model) {

        // converts all input to uppercase, search and match in uppercase to prevent case mismatch
        symbol = symbol.toUpperCase();
        currency = currency.toUpperCase();

        // calls service to perform the API call and data extraction 
        Crypto crypto = cryptoSvc.getCrypto(symbol, currency);
        
        // System.out.println(crypto.getCryptoSymbol());
        // System.out.println(crypto.getCryptoPrice().getCurrencyName());
        // System.out.println(crypto.getCryptoPrice().getPrice());

        // data injection into thymeleaf html
        model.addAttribute("symbol", symbol);
        model.addAttribute("crypto", crypto);
        
        return "price";

    }
}
