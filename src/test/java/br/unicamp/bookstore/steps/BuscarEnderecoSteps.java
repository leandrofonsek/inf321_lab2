package br.unicamp.bookstore.steps;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.google.gson.Gson;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.*;

import br.unicamp.bookstore.model.Address;
import br.unicamp.bookstore.service.ViaCep;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BuscarEnderecoSteps {

    WireMockServer wireMockServer; //No-args constructor will start on port 8080, no HTTPS
    
    
    private ViaCep viaCep;
    private String result;
    
    @Before
    public void setUp() {
        viaCep = new ViaCep();
        result = null;
        
        wireMockServer= new WireMockServer();
        wireMockServer.start();
        
        stubFor(get(urlMatching("/ws/.*"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "text/plain")
                .withBody("Hello world!")));
        
    }
    
    @Given("^I can consult my address based on a zip code$")
    public void i_can_consult_my_address() {
        assertNotNull(viaCep);
    }
    
    @When("^I consult the address for the zip code (\\d{5}-\\d{3})$")
    public void i_consult_the_address_for_the_zip_code(String cep) {
        this.result = viaCep.buscarEndereco(cep);
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
