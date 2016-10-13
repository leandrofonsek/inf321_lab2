package br.unicamp.bookstore.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import br.unicamp.bookstore.dao.DadosDeEntregaDAO;
import br.unicamp.bookstore.model.PrecoPrazo;

import com.google.gson.Gson;

public class CalcPrecoPrazo {
    
    private String url;
    public DadosDeEntregaDAO dao;

    public CalcPrecoPrazo(String url, DadosDeEntregaDAO dao) {
        super();
        this.url = url;
        this.dao = dao;
    }

    public CalcPrecoPrazo() {
        url = "http://ws.correios.com.br/calculador/";
    }

    public CalcPrecoPrazo(String url) {
        this.url = url;
    }

    public String calculaPrecoPrazo(String peso, String largura, String altura, String comprimento, String tipoEntrega, String cepOrigem, String cepDestino) {
        String json;

        try {
            URL url = new URL(this.url + "peso=" + peso + ",largura=" + largura + ",altura=" + altura + ",comprimento=" + comprimento + ",tipo=" + tipoEntrega + ",cepOrigem=" + cepOrigem + ",cepDestino=" + cepDestino +"/json");
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder jsonSb = new StringBuilder();
            String aux = "";
            while ((aux = br.readLine()) != null) {
                jsonSb.append(aux);
            }

            json = jsonSb.toString();

        } catch (Exception e) {
            return "Servico indisponivel temporariamente";
        }
        
        PrecoPrazo precoPrazo = new Gson().fromJson(json, PrecoPrazo.class);
        
        if (precoPrazo.getErro().equals(0)) {
            double valorFrete = Double.parseDouble(precoPrazo.getValor());
            int diasEntrega = Integer.parseInt(precoPrazo.getPrazoEntrega());
            dao.saveDadosDeEntrega(valorFrete, diasEntrega);            
        }
        
        return json;
    }

}
