package restaurante

import model.Restaurante
import java.io.File

fun criarArquivoPedidosSeNaoExistir() {

    val arquivo = File("pedidos.csv")

    if (!arquivo.exists()) {

        val cabecalho =
            "id_pedido;data_hora;email_restaurante;nome_restaurante;" +
                    "telefone_cliente;nome_cliente;endereco_cliente;" +
                    "numero_item;quantidade;descricao_item;valor_unitario;" +
                    "valor_total_item;status"

        arquivo.writeText(
            cabecalho + "\n"
        )

        println(
            "Arquivo pedidos.csv criado com sucesso."
        )

        println()
    }
}

fun nomeStatusRestaurante(status: Int): String {

    return when (status) {

        0 -> "SOLICITADO"
        1 -> "EM PREPARAÇÃO"
        2 -> "AGUARDANDO ENTREGADOR"
        3 -> "EM TRÂNSITO"
        4 -> "ENTREGUE"

        else -> "DESCONHECIDO"
    }
}

fun visualizarPedidosPorStatus(
    restaurante: Restaurante
) {

    val arquivo =
        File("pedidos.csv")

    if (!arquivo.exists()) {

        println(
            "Arquivo de pedidos não encontrado."
        )

        return
    }

    val linhas =
        arquivo.readLines()

    if (linhas.size <= 1) {

        println()
        println(
            "Nenhum pedido encontrado."
        )

        return
    }

    val pedidosRestaurante =
        mutableListOf<List<String>>()

    for (i in 1 until linhas.size) {

        val campos =
            linhas[i].split(";")

        if (campos.size >= 13) {

            val emailRestaurante =
                campos[2]

            if (
                emailRestaurante ==
                restaurante.email
            ) {

                pedidosRestaurante.add(
                    campos
                )
            }
        }
    }

    if (
        pedidosRestaurante.isEmpty()
    ) {

        println()
        println(
            "Nenhum pedido encontrado para este restaurante."
        )

        return
    }

    for (status in 0..4) {

        println()
        println(
            "=============================="
        )

        println(
            "STATUS: ${nomeStatusRestaurante(status)}"
        )

        println(
            "=============================="
        )

        var encontrou =
            false

        for (
        pedido in pedidosRestaurante
        ) {

            val statusPedido =
                pedido[12].toInt()

            if (
                statusPedido == status
            ) {

                encontrou =
                    true

                println(
                    "ID Pedido: ${pedido[0]}"
                )

                println(
                    "Data/Hora: ${pedido[1]}"
                )

                println(
                    "Cliente: ${pedido[5]}"
                )

                println(
                    "Telefone: ${pedido[4]}"
                )

                println(
                    "Endereço: ${pedido[6]}"
                )

                println(
                    "Item: ${pedido[9]}"
                )

                println(
                    "Quantidade: ${pedido[8]}"
                )

                println(
                    "Valor Unitário: R$ ${pedido[10]}"
                )

                println(
                    "Valor Total Item: R$ ${pedido[11]}"
                )

                println(
                    "------------------------------"
                )
            }
        }

        if (!encontrou) {

            println(
                "Nenhum pedido neste status."
            )
        }
    }
}

fun alterarStatusPedido(
    restaurante: Restaurante
) {

    val arquivo =
        File("pedidos.csv")

    if (!arquivo.exists()) {

        println(
            "Arquivo de pedidos não encontrado."
        )

        return
    }

    val linhas =
        arquivo
            .readLines()
            .toMutableList()

    if (linhas.size <= 1) {

        println(
            "Nenhum pedido cadastrado."
        )

        return
    }

    print(
        "Digite o ID do pedido: "
    )

    val idPedido =
        readln()

    print(
        "Digite o novo status (0 a 4): "
    )

    val novoStatus =
        readln()

    if (
        novoStatus != "0" &&
        novoStatus != "1" &&
        novoStatus != "2" &&
        novoStatus != "3" &&
        novoStatus != "4"
    ) {

        println()
        println(
            "Status inválido."
        )

        return
    }

    var pedidoEncontrado =
        false

    for (i in 1 until linhas.size) {

        val campos =
            linhas[i]
                .split(";")
                .toMutableList()

        if (campos.size >= 13) {

            val id =
                campos[0]

            val emailRestaurante =
                campos[2]

            if (
                id == idPedido &&
                emailRestaurante ==
                restaurante.email
            ) {

                campos[12] =
                    novoStatus

                linhas[i] =
                    campos.joinToString(";")

                pedidoEncontrado =
                    true
            }
        }
    }

    if (pedidoEncontrado) {

        arquivo.writeText(
            linhas.joinToString("\n") +
                    "\n"
        )

        println()
        println(
            "Status do pedido alterado com sucesso!"
        )

        println(
            "Novo status: ${nomeStatusRestaurante(novoStatus.toInt())}"
        )

    } else {

        println()
        println(
            "Pedido não encontrado para este restaurante."
        )
    }
}