package br.unicamp.bookstore.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Rule;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.gson.Gson;

import br.unicamp.bookstore.model.Address;
import br.unicamp.bookstore.service.ViaCep;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BuscarEnderecoSteps {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();
    
    private ViaCep viaCep;
    private Address address;
    
    @Before
    public void setUp() {
        viaCep = new ViaCep();
        address = null;
    }
    
    @Given("^I can consult my address based on a zip code$")
    public void i_can_consult_my_address() throws Throwable {
        assertNotNull(viaCep);
    }
    
    @When("^I consult the address for the zip code (\\d{5}-\\d{3})$")
    public void i_consult_the_address_for_the_zip_code(String cep) throws Throwable {
        String result = viaCep.buscarEndereco(cep);
        address = new Gson().fromJson(result, Address.class);
    }
    
    @Then("^I should see address (.+)$")
    public void i_should_see_address(String result) throws Throwable {
        assertEquals(result, address.getLocalidade());
    }

}
