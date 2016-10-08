package br.unicamp.exemplo.steps;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.assertj.core.api.Assertions;

import br.unicamp.exemplo.Calculadora;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CalculadoraSteps {

    private Calculadora calculadora;
    private Throwable throwable;

    @Before
    public void setUp() {
    	calculadora = new Calculadora();
    	throwable = null;
    }

    @Given("^I have a calculator$")
    public void i_have_a_calculator() throws Throwable {
        assertNotNull(calculadora);
    }

    @When("^I sum (\\d+) with (\\d+)$")
    public void i_sum_with(int arg1, int arg2) throws Throwable {
    	calculadora.add(arg1, arg2);
    }
    
    @When("^I multiply (\\d+) with (\\d+)$")
    public void i_mult_with(int arg1, int arg2) throws Throwable {
    	calculadora.multiply(arg1, arg2);
    }
    
    @When("^I divide (\\d+) with (\\d+)$")
    public void i_div_with(int arg1, int arg2) throws Throwable {
    	try{
    		calculadora.divide(arg1, arg2);
    	} catch(Throwable t){
    		throwable = t;
    	}
    }

    @Then("^the result should be (\\d+)$")
    public void the_result_should_be(int result) throws Throwable {
        assertEquals(result, calculadora.getResult());
    }

    @Then("^should show an error with a message:$")
	public void should_show_an_error(String message){
	   Assertions.assertThat(throwable).isNotNull().hasMessage(message);
	}


}