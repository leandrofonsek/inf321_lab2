package br.unicamp.bookstore.steps;

import org.junit.Rule;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import br.unicamp.bookstore.service.ViaCep;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BuscarEnderecoSteps {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();
    
    private ViaCep viaCep;
    
    @Given("^I have a CEP$")
    public void i_have_a_calculator() throws Throwable {
        //assertNotNull(CEP);
    }
    
    @When("^I send (\\d{5}-\\d{3}) to Correios$")
    public void i_send_to_Correios(String cep) throws Throwable {
        
    }
    
    @Then("^the result should be (.+)$")
    public void the_result_should_be(int result) throws Throwable {
        //assertEquals(result, calculadora.getResult());
    }

}
