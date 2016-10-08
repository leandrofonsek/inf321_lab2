package br.unicamp.bookstore.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ViaCep {

    private String url;

    public ViaCep() {
        url = "http://viacep.com.br/ws/";
    }

    public ViaCep(String url) {
        this.url = url;
    }

    public String buscarEndereco(String cep) {
        String json;

        try {
            URL url = new URL(this.url + cep + "/json");
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

        return json;
    }

}
