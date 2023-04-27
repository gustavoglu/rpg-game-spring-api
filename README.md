# Rpg game
API REST Rpg game

## Sobre o serviço

Esta api é um jogo de turnos RPG, é escolhido os personagens e os turnos são dividos em ataque,defesa e calculo de dano.
### Swagger doc endpoint.

```
/swagger-ui/index.html
http://localhost:8080/swagger-ui/index.html
```

### Segue os Endpoints.

```
http://localhost:8080/api/v1/characters
http://localhost:8080/api/v1/match
```
### Criando os Personagens padrão no banco

```
POST /api/v1/characters/create-default-characters

RESPONSE: HTTP 201 (Created)
Location header: http://localhost:8080/api/v1/characters/
```

### Criando outros Personagens

```
POST /api/v1/characters
Accept: application/json
Content-Type: application/json

{
"agility": 0,
"defense": 0,
"diceAmount": 0,
"diceFaces": 0,
"health": 0,
"id": 0,
"isDeleted": true,
"name": "string",
"strength": 0,
"type": "Hero",
}

RESPONSE: HTTP 201 (Created)
Location header: http://localhost:8080/api/v1/characters/1
```
### Começando um novo jogo

```
POST /api/v1/match/new-game
Accept: application/json
Content-Type: application/json

{
  "character1Id": 0,
  "character2Id": 0,
  "player1Name": "string",
  "player2Name": "string"
}

RESPONSE: HTTP 201 (Created)
Location header: http://localhost:8080/api/v1/match/1
```

### Turnos do jogo
### Ataque
```
POST /api/v1/match/1/attack

RESPONSE: HTTP 200 (Ok)
Location header: http://localhost:8080/api/v1/match/1
```
### Defesa

```
POST /api/v1/match/1/defense

RESPONSE: HTTP 200 (Ok)
Location header: http://localhost:8080/api/v1/match/1
```
### Calcular dano

```
POST /api/v1/match/1/damage-calc

RESPONSE: HTTP 200 (Ok)
Location header: http://localhost:8080/api/v1/match/1
```
### Histórico dos turnos

```
GET /api/v1/match/1/turn-logs

RESPONSE: HTTP 200 (Ok)
```

### O banco de dados utilizado é o H2

É possivel alterar o local e nome do banco alterando o application.properties

```
spring.datasource.url=jdbc:h2:file:./data/rpg_game
```

### H2 endpoint

É possivel alterar o local e nome do banco alterando o application.properties

```
/h2
http://localhost:8080/h2
```