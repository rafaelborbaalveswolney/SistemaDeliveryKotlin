package cliente

import model.Cliente

fun menuPrincipal(
    cliente: Cliente
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
            "[1] Realizar Novo Pedido"
        )
        println(
            "[2] Ver Pedidos em Andamento"
        )
        println(
            "[3] Ver Pedidos Finalizados"
        )
        println(
            "[0] Sair"
        )

        print(
            "Escolha uma opção: "
        )

        val opcaoMenu =
            readln()

        if (opcaoMenu == "1") {

            realizarNovoPedido(
                cliente
            )

        } else if (
            opcaoMenu == "2"
        ) {

            verPedidosEmAndamento(
                cliente
            )

        } else if (
            opcaoMenu == "3"
        ) {

            verPedidosFinalizados(
                cliente
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

    criarArquivoClientesSeNaoExistir()
    criarArquivoPedidosSeNaoExistir()

    println(
        "=============================="
    )
    println(
        "         APP CLIENTE"
    )
    println(
        "=============================="
    )

    println(
        "[1] Entrar"
    )
    println(
        "[2] Novo Cadastro"
    )

    print(
        "Escolha uma opção: "
    )

    val opcao =
        readln()

    if (opcao == "1") {

        println()
        println(
            "=== LOGIN CLIENTE ==="
        )

        print(
            "Digite o telefone: "
        )

        val telefone =
            readln()

        val cliente =
            buscarClientePorTelefone(
                telefone
            )

        if (cliente != null) {

            println()
            println(
                "Login realizado com sucesso!"
            )

            println()
            println(
                "=== CLIENTE LOGADO ==="
            )

            println(
                "Nome: ${cliente.nome}"
            )
            println(
                "Telefone: ${cliente.telefone}"
            )
            println(
                "Endereço: ${cliente.endereco}"
            )

            menuPrincipal(
                cliente
            )

        } else {

            println()
            println(
                "Erro: cliente não encontrado."
            )
        }

    } else if (
        opcao == "2"
    ) {

        cadastrarCliente()

    } else {

        println()
        println(
            "Opção inválida."
        )
    }
}