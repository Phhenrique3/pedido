edido Service






Microserviço de gerenciamento de pedidos em Spring Boot com CRUD completo, simulação de falhas e latência, métricas integradas com Micrometer e logs rastreáveis via MDCFilter.

🔹 Funcionalidades

CRUD completo: criar, listar, buscar, atualizar e deletar pedidos

Simulação de latência (20–400ms) e falhas aleatórias (8%) no processamento

Logs detalhados com MDC (requestId, pedidoId, userId)

Métricas integradas para monitoramento com Prometheus

Endpoint /ping para teste do serviço

🔹 Tecnologias

Java 17+

Spring Boot 3

Micrometer (Prometheus)

SLF4J/Logback para logs

Para rodar aplicação é mvn spring-boot:run 

Maven

🔹 Endpoints
Método	Endpoint	Descrição	Body/Params
POST	/pedidos	Cria um pedido	{ "valor": 123.45, "produto": "Produto X" }
GET	/pedidos	Lista todos os pedidos	-
GET	/pedidos/{id}	Busca pedido por ID	Path: id
PUT	/pedidos/{id}	Atualiza pedido por ID	Path: id, Body: { "valor": 200.00, "produto": "Produto Y" }
DELETE	/pedidos/{id}	Deleta pedido por ID	Path: id
GET	/pedidos/ping	Teste de ping	-
🔹 Exemplos de uso com curl
1️⃣ Criar pedido (POST)
curl -X POST http://localhost:8080/pedidos \
-H "Content-Type: application/json" \
-d '{
"valor": 123.45,
"produto": "Produto X"
}'


Resposta esperada:

{
"pedidoId": "a1b2c3d4-5678-90ef-ghij-klmnopqrstuv"
}

2️⃣ Listar pedidos (GET)
curl -X GET http://localhost:8080/pedidos


Resposta esperada:

[
{
"id": "a1b2c3d4-5678-90ef-ghij-klmnopqrstuv",
"valor": 123.45,
"produto": "Produto X"
}
]

3️⃣ Buscar pedido por ID (GET)
curl -X GET http://localhost:8080/pedidos/a1b2c3d4-5678-90ef-ghij-klmnopqrstuv


Resposta esperada:

{
"id": "a1b2c3d4-5678-90ef-ghij-klmnopqrstuv",
"valor": 123.45,
"produto": "Produto X"
}

4️⃣ Atualizar pedido (PUT)
curl -X PUT http://localhost:8080/pedidos/a1b2c3d4-5678-90ef-ghij-klmnopqrstuv \
-H "Content-Type: application/json" \
-d '{
"valor": 200.00,
"produto": "Produto Y"
}'


Resposta esperada:

{
"message": "Pedido atualizado com sucesso"
}

5️⃣ Deletar pedido (DELETE)
curl -X DELETE http://localhost:8080/pedidos/a1b2c3d4-5678-90ef-ghij-klmnopqrstuv


Resposta esperada:

{
"message": "Pedido deletado com sucesso"
}

6️⃣ Ping (GET)
curl -X GET http://localhost:8080/pedidos/ping


Resposta esperada:

pong

🔹 Logs com MDC

Você pode adicionar headers opcionais:

-H "X-Request-ID: 1234-abcd"
-H "X-User-Id: user-001"
-H "X-Pedido-Id: a1b2c3d4..."


Exemplo de log no Logback:

2025-11-04 21:45:12 [http-nio-8080-exec-1] INFO  c.e.p.controller.PedidoController - [d3f1e8a7-...] [pedido-123] [user-456] Pedido processado com sucesso

🔹 Métricas (Micrometer / Prometheus)

pedidos_criados_total → total de pedidos criados

pedidos_valor → distribuição dos valores dos pedidos

pedidos_falha_total → total de pedidos que falharam no processamento

Acesse as métricas em:

http://localhost:8080/actuator/prometheus

🔹 Como rodar
git clone https://github.com/Phhenrique3/pedido.git
cd pedido
mvn spring-boot:run


Serviço disponível em:

http://localhost:8080/pedidos
