package br.unicamp.bookstore.steps;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import br.unicamp.bookstore.model.PrecoPrazo;
import br.unicamp.bookstore.service.CalcPrecoPrazo;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CalcularFreteTempoSteps {

    private static final String MOCK_URL = "http://localhost:8080/calculador/";
    
    private CalcPrecoPrazo calcPrecoPrazo;
    private String result;

    
    @Before
    public void setUp() {
        calcPrecoPrazo = new CalcPrecoPrazo(MOCK_URL);
        result = null;
    }
    
    @Given("^I can consult the shipping cost and delivery time of a package to a address$")
    public void i_can_consult_my_order_delivery_status() {
        assertNotNull(calcPrecoPrazo);
    }
    
    @When("^I consult the shipping cost and delivery time for a package with dimensions (\\d{1}),(\\d{1}),(\\d{1}) and (\\d{1}), using delivery type (\\.{3}) from address (\\d{5})  to address (\\d{5})$")
    public void i_consult_the_shipping_cost_and_delivery_time(String peso, String largura, String altura, String comprimento, String tipo, String origem, String destino) {
        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();
        wireMockServer.stubFor(get(urlMatching("/calculador/peso=1,largura=1,altura=1,comprimento=1,tipo=SED,cepOrigem=11111,cepDestino=22222/json"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")    
                .withBody("{  'codigo': '40010',  "
                            + "'valor': '10,00',  "
                            + "'prazoEntrega': '1',  "
                            + "'valorMaoPropria': '0,00',  "
                            + "'valorAvisoRecebimento': '0,00',  "
                            + "'valorDeclarado': '0,00',  "
                            + "'entregaDomiciliar': 'S' ,  "
                            + "'entregaSabado': 'S',  "
                            + "'erro': '0'}")));
        
        wireMockServer.stubFor(get(urlMatching("/calculador/peso=1,largura=1,altura=1,comprimento=1,tipo=S10,cepOrigem=11111,cepDestino=22222/json"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "text/plain")    
                    .withBody("{  'codigo': '40215',  "
                                + "'valor': '10,00',  "
                                + "'prazoEntrega': '1',  "
                                + "'valorMaoPropria': '0,00',  "
                                + "'valorAvisoRecebimento': '0,00',  "
                                + "'valorDeclarado': '0,00',  "
                                + "'entregaDomiciliar': 'S' ,  "
                                + "'entregaSabado': 'S',  "
                                + "'erro': '0'}")));
        
        wireMockServer.stubFor(get(urlMatching("/calculador/peso=1,largura=1,altura=1,comprimento=1,tipo=PAC,cepOrigem=11111,cepDestino=22222/json"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "text/plain")    
                    .withBody("{  'codigo': '41106',  "
                                + "'valor': '10,00',  "
                                + "'prazoEntrega': '1',  "
                                + "'valorMaoPropria': '0,00',  "
                                + "'valorAvisoRecebimento': '0,00',  "
                                + "'valorDeclarado': '0,00',  "
                                + "'entregaDomiciliar': 'S' ,  "
                                + "'entregaSabado': 'S',  "
                                + "'erro': '0'}")));
        
        wireMockServer.stubFor(get(urlMatching("/calculador/peso=0,largura=1,altura=1,comprimento=1,tipo=SED,cepOrigem=11111,cepDestino=22222/json"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "text/plain")    
                    .withBody("{  'mensagem': 'Peso Invalido',  "
                                + "'erro': '1'}")));
        
        wireMockServer.stubFor(get(urlMatching("/calculador/peso=1,largura=0,altura=1,comprimento=1,tipo=S10,cepOrigem=11111,cepDestino=22222/json"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "text/plain")    
                    .withBody("{  'mensagem': 'Largura Invalida',  "
                                + "'erro': '1'}")));
        
        wireMockServer.stubFor(get(urlMatching("/calculador/peso=1,largura=1,altura=0,comprimento=1,tipo=PAC,cepOrigem=11111,cepDestino=22222/json"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "text/plain")    
                    .withBody("{  'mensagem': 'Altura Invalida',  "
                                + "'erro': '1'}")));
        
        wireMockServer.stubFor(get(urlMatching("/calculador/peso=1,largura=1,altura=1,comprimento=0,tipo=SED,cepOrigem=11111,cepDestino=22222/json"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "text/plain")    
                    .withBody("{  'mensagem': 'Comprimento Invalido',  "
                                + "'erro': '1'}")));
        
        wireMockServer.stubFor(get(urlMatching("/calculador/peso=1,largura=1,altura=1,comprimento=1,tipo=S10,cepOrigem=00000,cepDestino=22222/json"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "text/plain")    
                    .withBody("{  'mensagem': 'CEP origem invalido',  "
                                + "'erro': '1'}")));
        
        wireMockServer.stubFor(get(urlMatching("/calculador/peso=1,largura=1,altura=1,comprimento=1,tipo=PAC,cepOrigem=11111,cepDestino=00000/json"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "text/plain")    
                    .withBody("{  'mensagem': 'CEP destino invalido',  "
                                + "'erro': '1'}")));
        
        wireMockServer.stubFor(get(urlMatching("/calculador/peso=1,largura=1,altura=1,comprimento=1,tipo=SML,cepOrigem=11111,cepDestino=22222/json"))
                .willReturn(aResponse()
                    .withHeader("Content-Type", "text/plain")    
                    .withBody("{  'mensagem': 'Tipo de entrega invalido',  "
                                + "'erro': '1'}")));

        
        this.result = calcPrecoPrazo.calculaPrecoPrazo(peso,largura,altura,comprimento,tipo,origem,destino);
        
        wireMockServer.stop();
    }
    
    @Then("^ the result should be (.+)$")
    public void the_result_should_be(String result) throws IOException {
        PrecoPrazo precoPrazo = new Gson().fromJson(this.result, PrecoPrazo.class);
        assertEquals(result, precoPrazo.getMensagem());
    }
    
    @When("^Correios API is offline$")
    public void Correios_API_is_offline() {
        calcPrecoPrazo = new CalcPrecoPrazo("http://taerrado");
        this.result = calcPrecoPrazo.calculaPrecoPrazo("", "", "", "", "", "", "");
    }
    
    @Then("I should see Correios API status (.+)$")
    public void i_should_see_correios_api_status(String status) {
        assertTrue(this.result.contains(status));
    }

}
