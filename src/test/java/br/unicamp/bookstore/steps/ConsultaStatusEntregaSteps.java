package br.unicamp.bookstore.steps;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import br.unicamp.bookstore.model.StatusEntrega;
import br.unicamp.bookstore.service.Rastro;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ConsultaStatusEntregaSteps {

    private static final String MOCK_URL = "http://localhost:8080/service/rastro/";
    
    private Rastro rastro;
    private String result;

    
    @Before
    public void setUp() {
        rastro = new Rastro(MOCK_URL);
        result = null;
    }
    
    @Given("^I can consult my order delivery status based on a track number$")
    public void i_can_consult_my_order_delivery_status() {
        assertNotNull(rastro);
    }
    
    @When("^I consult the delivery status for the track number (\\d{5})$")
    public void i_consult_the_delivery_status_for_the_track_number(String objeto) {
        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();
        wireMockServer.stubFor(get(urlMatching("/service/rastro/user=user,senha=passwd,tipo=L,resultado=T,lingua=101,objeto=11111/json"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")    
                .withBody("{  'versao': '2.0',  "
                            + "'qtd': '1',  "
                            + "'numero': '" + objeto + "',  "
                            + "'sigla': 'JF',  "
                            + "'nome': 'REMESSA ECONÔMICA C/AR DIGITAL',  "
                            + "'categoria': 'REMESSA ECONÔMICA TALÃO/CARTÃO',  "
                            + "'tipo': 'BDE' ,  "
                            + "'status': '0',  "
                            + "'data': '18/03/2014',  "
                            + "'hora': '18:37',  "
                            + "'descricao': 'Objeto entregue ao destinatario',  "
                            + "'local': 'CTCE MACEIO',  "
                            + "'codigo': '57060971',  "
                            + "'cidade': 'MACEIO',  "
                            + "'uf': 'AL'}")));
        
        this.result = rastro.consultaStatusEntrega(objeto);
        
        wireMockServer.stop();
    }
    
    @When("^I consult the delivery status for a track number that does not exist (\\d{5})$")
    public void i_consult_the_delivery_status_for_a_track_number_that_not_exist(String objeto) {
        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();
        wireMockServer.stubFor(get(urlMatching("/service/rastro/user=user,senha=passwd,tipo=L,resultado=T,lingua=101,objeto=" + objeto + "/json"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("{ Objeto nao localizado }")));
        
        this.result = rastro.consultaStatusEntrega(objeto);
        
        wireMockServer.stop();
    }
    
    @Then("^ I should see OK message (.+)$")
    public void i_should_see_ok_message(String result) throws IOException {
        StatusEntrega statusEntrega = new Gson().fromJson(this.result, StatusEntrega.class);
        assertEquals(result, statusEntrega.getDescricao());
    }
    
    @Then("I should see not-OK message (.+)$")
    public void i_should_see_not_ok_message(String message) {
        assertTrue(this.result.contains(message));
    }
    
    @When("^Correios are out$")
    public void viaCep_API_is_offline() {
        rastro = new Rastro("http://taerrado");
        this.result = rastro.consultaStatusEntrega("");
    }
    
    @Then("Then I should see CORREIOS-Rastro status (.+)$")
    public void i_should_see_correios_rastro_status(String status) {
        assertTrue(this.result.contains(status));
    }

}
