# encoding: UTF-8
Feature: ConsultaStatusEntrega

  #  Scenario Tag ID is the test case id in TestRail,TestLink,ALM. multiple cases separated with blank space
  #  1. if the tag id is empty then it will create this scenario test case in your testRail,TestLink or ALM,
  #  2. but if you specified the scenario tag id as the test case id,then it will just update the existing test case .
  #  Scenario title is the Test Case name in TestRail,TestLink,ALM
  #  Step sentence is the 'cucumber' field value in TestRail,TestLink,ALM

  @caseid1 
  Scenario: Track number found
     Given I can consult my order delivery status based on a track number 
     When I consult the delivery status for the track number 11111
     Then I should see OK message Objeto entregue ao destinatario
     
	@caseid2
  Scenario: Track number not found
     Given I can consult my order delivery status based on a track number 
     When I consult the delivery status for a track number that does not exist 00000
     Then I should see not-OK message Objeto nao localizado
     
     @caseid3
  Scenario: Correios are out
     Given I can consult my order delivery status based on a track number 
     When Correios is offline
     Then I should see CORREIOS-Rastro status Servico indisponivel temporariamente
     
     
     