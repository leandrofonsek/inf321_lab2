Feature: BuscarEndereco 
  
Scenario: Buscar Endereco 
	Given I have a CEP 
	When I send 10100-000 to Correios 
	Then the result should be "Avenida Brasil" 