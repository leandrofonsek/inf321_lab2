Feature: BuscarEndereco 

Scenario: Zip Code found 
	Given I can consult my address based on a zip code 
	When I consult the address for the zip code 13820-000 
	Then I should see address Jaguariúna
	
Scenario: Zip Code not found 
	Given I can consult my address based on a zip code 
	When I consult address for a zip code that does not exist 00000 
	Then I should see address "Inválido" 
	
Scenario: ViaCep are out 
	Given I can consult my address based on a zip code 
	When ViaCep API is offline 
	Then I should see status "Servico indisponivel temporariamente"