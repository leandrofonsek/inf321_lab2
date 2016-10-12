package br.unicamp.bookstore.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Rastro {
    
    private String url;
    private String usuario = "user";
    private String senha = "passwd";
    private String tipoObjeto = "L";
    private String tipoResultado = "T";
    private String lingua = "101";
    

    public Rastro() {
        url = "https://webservice.correios.com.br/service/rastro/";
    }

    public Rastro(String url) {
        this.url = url;
    }

    public String consultaStatusEntrega(String objeto) {
        String json;

        try {
            URL url = new URL(this.url + "user=" + usuario + ",senha=" + senha + ",tipo=" + tipoObjeto + ",resultado=" + tipoResultado + ",lingua=" + lingua + ",objeto=" + objeto + "/json");
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
