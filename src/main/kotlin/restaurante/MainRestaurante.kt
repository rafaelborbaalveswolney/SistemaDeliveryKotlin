package restaurante

import model.Restaurante

fun menuPrincipalRestaurante(
    restaurante: Restaurante
) {

    while (true) {

        println()
        println(
            "=============================="
        )

        println(
            "        MENU PRINCIPAL"
        )

        println(
            "=============================="
        )

        println(
            "[1] Gerenciar Cardápio"
        )

        println(
            "[2] Visualizar Pedidos por Status"
        )

        println(
            "[3] Alterar Status do Pedido"
        )

        println(
            "[0] Sair"
        )

        print(
            "Escolha uma opção: "
        )

        val opcaoMenu =
            readln()

        if (
            opcaoMenu == "1"
        ) {

            gerenciarCardapio(
                restaurante
            )

        } else if (
            opcaoMenu == "2"
        ) {

            println()
            println(
                "=== PEDIDOS POR STATUS ==="
            )

            visualizarPedidosPorStatus(
                restaurante
            )

        } else if (
            opcaoMenu == "3"
        ) {

            println()
            println(
                "=== ALTERAR STATUS DO PEDIDO ==="
            )

            println(
                "0 - SOLICITADO"
            )

            println(
                "1 - EM PREPARAÇÃO"
            )

            println(
                "2 - AGUARDANDO ENTREGADOR"
            )

            println(
                "3 - EM TRÂNSITO"
            )

            println(
                "4 - ENTREGUE"
            )

            println()

            alterarStatusPedido(
                restaurante
            )

        } else if (
            opcaoMenu == "0"
        ) {

            println()
            println(
                "Saindo..."
            )

            break

        } else {

            println()
            println(
                "Opção inválida."
            )
        }
    }
}

fun main() {

    criarArquivoPedidosSeNaoExistir()

    println(
        "=============================="
    )

    println(
        "      APP RESTAURANTE"
    )

    println(
        "=============================="
    )

    println(
        "[1] Entrar como Restaurante Existente"
    )

    println(
        "[2] Novo Cadastro"
    )

    print(
        "Escolha uma opção: "
    )

    val opcao =
        readln()

    if (
        opcao == "1"
    ) {

        println()
        println(
            "=== LOGIN RESTAURANTE ==="
        )

        print(
            "Digite o e-mail: "
        )

        val email =
            readln()

        val restaurante =
            buscarRestaurantePorEmail(
                email
            )

        if (
            restaurante != null
        ) {

            println()
            println(
                "Login realizado com sucesso!"
            )

            println()
            println(
                "=== RESTAURANTE LOGADO ==="
            )

            println(
                "Nome: ${restaurante.nome}"
            )

            println(
                "E-mail: ${restaurante.email}"
            )

            println(
                "Endereço: ${restaurante.endereco}"
            )

            menuPrincipalRestaurante(
                restaurante
            )

        } else {

            println()
            println(
                "Erro: restaurante não encontrado."
            )
        }

    } else if (
        opcao == "2"
    ) {

        cadastrarRestaurante()

    } else {

        println()
        println(
            "Opção inválida."
        )
    }
}