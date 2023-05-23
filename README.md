# Hotelaria

Para fazer as requisições REST para a entidade `CheckIn`, você pode seguir os seguintes exemplos usando o Postman:

- Para criar um novo check-in:
  - Método: POST
  - URL: `http://localhost:8080/api/checkins`
  - Corpo (formato JSON): 
    ```json
    {
      "hospede": {
        "id": 1
      },
      "dataEntrada": "2023-05-01",
      "dataSaida": "2023-05-05",
      "isAdicionalVeiculo": true
    }
    ```

- Para obter todos os check-ins:
  - Método: GET
  - URL: `http://localhost:8080/api/checkins`

- Para obter um check-in específico pelo ID:
  - Método: GET
  - URL: `http://localhost:8080/api/checkins/{id}`
  - Substitua `{id}` pelo ID do check-in desejado.

- Para atualizar um check-in existente:
  - Método: PUT
  - URL: `http://localhost:8080/api/checkins/{id}`
  - Substitua `{id}` pelo ID do check-in que deseja atualizar.
  - Corpo (formato JSON):
    ```json
    {
      "hospede": {
        "id": 1
      },
      "dataEntrada": "2023-05-01",
      "dataSaida": "2023-05-06",
      "isAdicionalVeiculo": true
    }
    ```

- Para excluir um check-in:
  - Método: DELETE
  - URL: `http://localhost:8080/api/checkins/{id}`
  - Substitua `{id}` pelo ID do check-in que deseja excluir.

- Para buscar hóspedes cadastrados pelo nome, documento ou telefone:
  - Método: GET
  - URL: `http://localhost:8080/api/checkins/hospedes?searchTerm=termoDeBusca`
  - Substitua `termoDeBusca` pelo nome, documento ou telefone que deseja pesquisar.

- Para consultar hóspedes que já realizaram o check-in e não estão mais no hotel:
  - Método: GET
  - URL: `http://localhost:8080/api/checkins/concluidos`

- Para consultar hóspedes que ainda estão no hotel:
  - Método: GET
  - URL: `http://localhost:8080/api/checkins/ativos`

- Para consultar o valor total gasto por um hóspede no hotel:
  - Método: GET
  - URL: `http://localhost:8080/api/hospedes/{id}/valor-total`
  - Substitua `{id}` pelo ID do hóspede desejado.

- Para consultar o valor da última hospedagem de um hóspede:
  - Método: GET
  - URL: `http://localhost:8080/api/hospedes/{id}/valor-ultima-hospedagem`
  - Substitua `{id}` pelo ID do hóspede desejado.


Para realizar as requisições REST para a entidade `Hospede`, você pode utilizar o Postman, seguindo os exemplos abaixo:

- Criar um novo hóspede:
  - Método: POST
  - URL: `http://localhost:8080/api/hospedes`
  - Corpo (formato JSON):
  ```json
  {
    "nome": "Nome do Hóspede",
    "documento": "123456789",
    "telefone": "987654321"
  }
  ```

- Obter todos os hóspedes:
  - Método: GET
  - URL: `http://localhost:8080/api/hospedes`

- Obter um hóspede específico pelo ID:
  - Método: GET
  - URL: `http://localhost:8080/api/hospedes/{id}`
  - Substitua `{id}` pelo ID do hóspede desejado.

- Atualizar um hóspede existente:
  - Método: PUT
  - URL: `http://localhost:8080/api/hospedes/{id}`
  - Substitua `{id}` pelo ID do hóspede que deseja atualizar.
  - Corpo (formato JSON):
  ```json
  {
    "nome": "Novo Nome do Hóspede",
    "documento": "987654321",
    "telefone": "123456789"
  }
  ```

- Excluir um hóspede:
  - Método: DELETE
  - URL: `http://localhost:8080/api/hospedes/{id}`
  - Substitua `{id}` pelo ID do hóspede que deseja excluir.
