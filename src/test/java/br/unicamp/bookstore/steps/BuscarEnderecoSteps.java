package br.unicamp.bookstore.steps;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;

import br.unicamp.bookstore.model.Address;
import br.unicamp.bookstore.service.ViaCep;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BuscarEnderecoSteps {

    private static final String MOCK_URL = "http://localhost:8080/ws/";
    
    private ViaCep viaCep;
    private String result;

    
    @Before
    public void setUp() {
        viaCep = new ViaCep(MOCK_URL);
        result = null;
    }
    
    @Given("^I can consult my address based on a zip code$")
    public void i_can_consult_my_address() {
        assertNotNull(viaCep);
    }
    
    @When("^I consult the address for the zip code (\\d{5}-\\d{3})$")
    public void i_consult_the_address_for_the_zip_code(String cep) {
        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();
        wireMockServer.stubFor(get(urlMatching("/ws/13820-000/json"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("{  'cep': '13820-000',  "
                            + "'logradouro': '',  "
                            + "'complemento': '',  "
                            + "'bairro': '',  "
                            + "'localidade': 'Jaguariúna',  "
                            + "'uf': 'SP',  'unidade': '',  "
                            + "'ibge': '3524709',  "
                            + "'gia': '3955'}")));
        
        this.result = viaCep.buscarEndereco(cep);
        
        wireMockServer.stop();
    }
    
    @When("^I consult the address for the zip code that does not exist (\\d{5}-\\d{3})$")
    public void i_consult_the_address_for_the_zip_code_not_exist(String cep) {
        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();
        wireMockServer.stubFor(get(urlMatching("/ws/00000-000/json"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("{ 'erro': true }")));
        
        this.result = viaCep.buscarEndereco(cep);
        
        wireMockServer.stop();
    }
    
    @Then("^I should see address (.+)$")
    public void i_should_see_address(String result) throws IOException {
        Address address = new Gson().fromJson(this.result, Address.class);
        assertEquals(result, address.getLocalidade());
    }
    
    @Then("I should see message (.+)$")
    public void i_should_see_message(String message) {
        assertTrue(this.result.contains(message));
    }
    
    @When("^ViaCep API is offline$")
    public void viaCep_API_is_offline() {
        viaCep = new ViaCep("http://taerrado");
        this.result = viaCep.buscarEndereco("");
    }
    
    @Then("I should see status (.+)$")
    public void i_should_see_status(String status) {
        assertTrue(this.result.contains(status));
    }

}
