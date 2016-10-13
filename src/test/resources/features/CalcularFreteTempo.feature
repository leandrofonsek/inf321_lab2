# encoding: UTF-8
Feature: CalcularFreteTempo

  #  Scenario Tag ID is the test case id in TestRail,TestLink,ALM. multiple cases separated with blank space
  #  1. if the tag id is empty then it will create this scenario test case in your testRail,TestLink or ALM,
  #  2. but if you specified the scenario tag id as the test case id,then it will just update the existing test case .
  #  Scenario title is the Test Case name in TestRail,TestLink,ALM
  #  Step sentence is the 'cucumber' field value in TestRail,TestLink,ALM

  @caseid1 
  Scenario Outline: Calculate shipping cost and delivery time for valid fields 
	Given I can consult the shipping cost and delivery time of a package to a address
	When I consult the shipping cost and delivery time for a valid package with dimensions <Peso>,<Largura>,<Altura> and <Comprimento>, using valid delivery type <TipoEntrega> from valid address <CEPOrigem>  to valid address <CEPDestino> 
	Then the valid result should be <Retorno> 
	And the result is saved on database
	
	Examples: 
		|Peso	|Largura|Altura	|Comprimento|TipoEntrega	|CEPOrigem	|CEPDestino	|Retorno					|
		|1		|1		|1		|1			|SED			|11111		|22222		|10,00;1					|
		|1		|1		|1		|1			|S10			|11111		|22222		|10,00;1					|
		|1		|1		|1		|1			|PAC			|11111		|22222		|10,00;1					|
		
  @caseid2 
  Scenario Outline: Calculate shipping cost and delivery time for invalid fields 
	Given I can consult the shipping cost and delivery time of a package to a address
	When I consult the shipping cost and delivery time for a invalid package with dimensions <Peso>,<Largura>,<Altura> and <Comprimento>, using invalid delivery type <TipoEntrega> from invalid address <CEPOrigem>  to invalid address <CEPDestino> 
	Then the invalid result should be <Retorno> 
	
	Examples: 
		|Peso	|Largura|Altura	|Comprimento|TipoEntrega	|CEPOrigem	|CEPDestino	|Retorno					|
		|0		|1		|1		|1			|SED			|11111		|22222		|Peso Invalido				|
		|1		|0		|1		|1			|S10			|11111		|22222		|Largura Invalida			|
		|1		|1		|0		|1			|PAC			|11111		|22222		|Altura Invalida			|
		|0		|1		|1		|0			|SED			|11111		|22222		|Comprimento Invalido		|
		|1		|1		|1		|1			|S10			|00000		|22222		|CEP origem invalido		|
		|1		|1		|1		|1			|PAC			|11111		|00000		|CEP destino invalido		|
		|1		|1		|1		|1			|SML			|11111		|22222		|Tipo de entrega invalido	|
  
  @caseid3
  Scenario: Correios are out
     Given I can consult my order delivery status based on a track number 
     When Correios API is offline
     Then I should see Correios API status Servico indisponivel temporariamente
     