package cliente

import com.google.gson.Gson
import model.Cliente
import model.ItemPedido
import model.Restaurante
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun listarRestaurantes(): MutableList<Restaurante> {

    val restaurantes =
        mutableListOf<Restaurante>()

    val arquivos =
        File(".").listFiles()

    val gson = Gson()

    if (arquivos != null) {

        for (arquivo in arquivos) {

            if (
                arquivo.name.startsWith("restaurante_") &&
                arquivo.name.endsWith(".json")
            ) {

                val conteudo =
                    arquivo.readText()

                val restaurante =
                    gson.fromJson(
                        conteudo,
                        Restaurante::class.java
                    )

                restaurantes.add(
                    restaurante
                )
            }
        }
    }

    return restaurantes
}

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
    }
}

fun gerarIdPedido(): Int {

    val arquivo = File("pedidos.csv")

    if (!arquivo.exists()) {
        return 1
    }

    val linhas =
        arquivo.readLines()

    var maiorId = 0

    for (i in 1 until linhas.size) {

        val campos =
            linhas[i].split(";")

        if (campos.isNotEmpty()) {

            val id =
                campos[0].toIntOrNull()

            if (
                id != null &&
                id > maiorId
            ) {
                maiorId = id
            }
        }
    }

    return maiorId + 1
}

fun salvarPedido(
    cliente: Cliente,
    restaurante: Restaurante,
    itensPedido: MutableList<ItemPedido>
) {

    criarArquivoPedidosSeNaoExistir()

    val arquivo =
        File("pedidos.csv")

    val idPedido =
        gerarIdPedido()

    val formatoData =
        DateTimeFormatter.ofPattern(
            "dd/MM/yyyy HH:mm:ss"
        )

    val dataHora =
        LocalDateTime
            .now()
            .format(formatoData)

    for (itemPedido in itensPedido) {

        val valorTotalItem =
            itemPedido.item.preco *
                    itemPedido.quantidade

        val linha =
            "$idPedido;" +
                    "$dataHora;" +
                    "${restaurante.email};" +
                    "${restaurante.nome};" +
                    "${cliente.telefone};" +
                    "${cliente.nome};" +
                    "${cliente.endereco};" +
                    "${itemPedido.item.numero_item};" +
                    "${itemPedido.quantidade};" +
                    "${itemPedido.item.descricao};" +
                    "${itemPedido.item.preco};" +
                    "$valorTotalItem;" +
                    "0"

        arquivo.appendText(
            linha + "\n"
        )
    }

    println()
    println("Pedido salvo com sucesso!")
    println("ID do Pedido: $idPedido")
    println("Status: 0 - SOLICITADO")
}

fun nomeStatus(status: Int): String {

    return when (status) {
        0 -> "SOLICITADO"
        1 -> "EM PREPARAÇÃO"
        2 -> "AGUARDANDO ENTREGADOR"
        3 -> "EM TRÂNSITO"
        4 -> "ENTREGUE"
        else -> "DESCONHECIDO"
    }
}

fun verPedidosEmAndamento(
    cliente: Cliente
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

    var encontrou = false

    println()
    println(
        "=== PEDIDOS EM ANDAMENTO ==="
    )

    for (i in 1 until linhas.size) {

        val campos =
            linhas[i].split(";")

        if (campos.size >= 13) {

            val telefoneCliente =
                campos[4]

            val status =
                campos[12].toInt()

            if (
                telefoneCliente == cliente.telefone &&
                status < 4
            ) {

                encontrou = true

                println()
                println(
                    "ID Pedido: ${campos[0]}"
                )
                println(
                    "Data/Hora: ${campos[1]}"
                )
                println(
                    "Restaurante: ${campos[3]}"
                )
                println(
                    "Item: ${campos[9]}"
                )
                println(
                    "Quantidade: ${campos[8]}"
                )
                println(
                    "Valor Unitário: R$ ${campos[10]}"
                )
                println(
                    "Valor Total Item: R$ ${campos[11]}"
                )
                println(
                    "Status: ${nomeStatus(status)}"
                )
                println(
                    "----------------------------"
                )
            }
        }
    }

    if (!encontrou) {

        println()
        println(
            "Nenhum pedido em andamento."
        )
    }
}

fun verPedidosFinalizados(
    cliente: Cliente
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

    var encontrou = false

    println()
    println(
        "=== PEDIDOS FINALIZADOS ==="
    )

    for (i in 1 until linhas.size) {

        val campos =
            linhas[i].split(";")

        if (campos.size >= 13) {

            val telefoneCliente =
                campos[4]

            val status =
                campos[12].toInt()

            if (
                telefoneCliente == cliente.telefone &&
                status == 4
            ) {

                encontrou = true

                println()
                println(
                    "ID Pedido: ${campos[0]}"
                )
                println(
                    "Data/Hora: ${campos[1]}"
                )
                println(
                    "Restaurante: ${campos[3]}"
                )
                println(
                    "Item: ${campos[9]}"
                )
                println(
                    "Quantidade: ${campos[8]}"
                )
                println(
                    "Valor Unitário: R$ ${campos[10]}"
                )
                println(
                    "Valor Total Item: R$ ${campos[11]}"
                )
                println(
                    "Status: ${nomeStatus(status)}"
                )
                println(
                    "----------------------------"
                )
            }
        }
    }

    if (!encontrou) {

        println()
        println(
            "Nenhum pedido finalizado."
        )
    }
}

fun realizarNovoPedido(
    cliente: Cliente
) {

    println()
    println(
        "=== RESTAURANTES DISPONÍVEIS ==="
    )

    val restaurantes =
        listarRestaurantes()

    if (restaurantes.isEmpty()) {

        println(
            "Nenhum restaurante cadastrado."
        )

        return
    }

    for (i in restaurantes.indices) {

        println(
            "[${i + 1}] ${restaurantes[i].nome}"
        )
    }

    println()
    print(
        "Escolha o restaurante: "
    )

    val escolhaTexto =
        readln()

    val escolha =
        escolhaTexto.toIntOrNull()

    if (escolha == null) {

        println()
        println(
            "Opção inválida. Digite apenas números."
        )

        return
    }

    if (
        escolha < 1 ||
        escolha > restaurantes.size
    ) {

        println()
        println(
            "Restaurante inválido."
        )

        return
    }

    val restauranteEscolhido =
        restaurantes[escolha - 1]

    println()
    println(
        "=== RESTAURANTE ESCOLHIDO ==="
    )
    println(
        "Nome: ${restauranteEscolhido.nome}"
    )

    println()
    println(
        "=== CARDÁPIO ==="
    )

    if (
        restauranteEscolhido.menu.isEmpty()
    ) {

        println(
            "O cardápio está vazio."
        )

        return
    }

    for (
    item in restauranteEscolhido.menu
    ) {

        println(
            "Número: ${item.numero_item}"
        )
        println(
            "Descrição: ${item.descricao}"
        )
        println(
            "Preço: R$ ${item.preco}"
        )
        println(
            "----------------------------"
        )
    }

    val itensPedido =
        mutableListOf<ItemPedido>()

    println()
    println(
        "=== SELEÇÃO DE ITENS ==="
    )

    while (true) {

        print(
            "Digite o número do item ou pressione Enter para finalizar: "
        )

        val numeroDigitado =
            readln()

        if (
            numeroDigitado.isEmpty()
        ) {
            break
        }

        val numeroItem =
            numeroDigitado.toIntOrNull()

        if (numeroItem == null) {

            println()
            println(
                "Número do item inválido. Digite apenas números."
            )
            println()

            continue
        }

        val itemEncontrado =
            restauranteEscolhido.menu.find {
                it.numero_item == numeroItem
            }

        if (itemEncontrado == null) {

            println()
            println(
                "Item não encontrado."
            )
            println()

            continue
        }

        print(
            "Digite a quantidade: "
        )

        val quantidadeTexto =
            readln()

        val quantidade =
            quantidadeTexto.toIntOrNull()

        if (quantidade == null) {

            println()
            println(
                "Quantidade inválida. Digite apenas números."
            )
            println()

            continue
        }

        if (quantidade <= 0) {

            println()
            println(
                "Quantidade inválida. Digite uma quantidade maior que zero."
            )
            println()

            continue
        }

        val itemPedido =
            ItemPedido(
                itemEncontrado,
                quantidade
            )

        itensPedido.add(
            itemPedido
        )

        println()
        println(
            "Item adicionado ao pedido."
        )
        println()
    }

    if (itensPedido.isEmpty()) {

        println()
        println(
            "Nenhum item foi selecionado."
        )

        return
    }

    var valorTotalPedido =
        0.0

    println()
    println(
        "=== RESUMO DO PEDIDO ==="
    )

    for (
    itemPedido in itensPedido
    ) {

        val totalItem =
            itemPedido.item.preco *
                    itemPedido.quantidade

        valorTotalPedido +=
            totalItem

        println(
            "Item: ${itemPedido.item.descricao}"
        )
        println(
            "Quantidade: ${itemPedido.quantidade}"
        )
        println(
            "Preço Unitário: R$ ${itemPedido.item.preco}"
        )
        println(
            "Total do Item: R$ $totalItem"
        )
        println(
            "----------------------------"
        )
    }

    println()
    println(
        "Valor Total do Pedido: R$ $valorTotalPedido"
    )

    println()
    print(
        "Confirmar pedido? [S/N]: "
    )

    val confirmacao =
        readln()

    if (
        confirmacao.equals(
            "S",
            ignoreCase = true
        )
    ) {

        println()
        println(
            "Pedido confirmado!"
        )

        salvarPedido(
            cliente,
            restauranteEscolhido,
            itensPedido
        )

    } else if (
        confirmacao.equals(
            "N",
            ignoreCase = true
        )
    ) {

        println()
        println(
            "Pedido cancelado."
        )

    } else {

        println()
        println(
            "Opção inválida."
        )
    }
}