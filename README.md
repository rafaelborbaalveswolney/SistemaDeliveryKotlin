# Sistema de Delivery em Kotlin

Projeto desenvolvido para a disciplina de Desenvolvimento de Sistemas para Dispositivos Móveis.

O sistema possui duas aplicações de console:

- Cliente
- Restaurante

## Funcionalidades

### Cliente

- Cadastro de cliente
- Login por telefone
- Realização de pedidos
- Consulta de pedidos em andamento
- Consulta de pedidos finalizados

### Restaurante

- Cadastro de restaurante
- Login por e-mail
- Gerenciamento do cardápio
- Visualização de pedidos
- Alteração de status

## Status dos pedidos

| Código | Status |
|--------|--------|
| 0 | SOLICITADO |
| 1 | EM PREPARAÇÃO |
| 2 | AGUARDANDO ENTREGADOR |
| 3 | EM TRÂNSITO |
| 4 | ENTREGUE |

## Estrutura do projeto

```text
src/main/kotlin
├── cliente
│   ├── MainCliente.kt
│   ├── ClienteService.kt
│   └── PedidoClienteService.kt
├── model
│   ├── Cliente.kt
│   ├── ItemMenu.kt
│   ├── ItemPedido.kt
│   └── Restaurante.kt
└── restaurante
    ├── MainRestaurante.kt
    ├── RestauranteService.kt
    └── PedidoRestauranteService.kt

#Tecnologias

Kotlin
Gradle
Gson
JSON
CSV