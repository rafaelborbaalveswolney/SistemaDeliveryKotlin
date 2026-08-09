package restaurante

import com.google.gson.GsonBuilder
import model.ItemMenu
import model.Restaurante
import java.io.File

fun emailJaCadastrado(email: String): Boolean {

    val arquivos = File(".").listFiles()

    if (arquivos != null) {

        for (arquivo in arquivos) {

            if (
                arquivo.name.startsWith("restaurante_") &&
                arquivo.name.endsWith(".json")
            ) {

                val conteudo = arquivo.readText()

                if (conteudo.contains("\"email\": \"$email\"")) {
                    return true
                }
            }
        }
    }

    return false
}

fun buscarRestaurantePorEmail(email: String): Restaurante? {

    val gson = GsonBuilder().create()
    val arquivos = File(".").listFiles()

    if (arquivos != null) {

        for (arquivo in arquivos) {

            if (
                arquivo.name.startsWith("restaurante_") &&
                arquivo.name.endsWith(".json")
            ) {

                val conteudo = arquivo.readText()

                val restaurante = gson.fromJson(
                    conteudo,
                    Restaurante::class.java
                )

                if (restaurante.email == email) {
                    return restaurante
                }
            }
        }
    }

    return null
}

fun buscarArquivoRestaurante(email: String): File? {

    val arquivos = File(".").listFiles()

    if (arquivos != null) {

        for (arquivo in arquivos) {

            if (
                arquivo.name.startsWith("restaurante_") &&
                arquivo.name.endsWith(".json")
            ) {

                val conteudo = arquivo.readText()

                if (conteudo.contains("\"email\": \"$email\"")) {
                    return arquivo
                }
            }
        }
    }

    return null
}

fun gerenciarCardapio(restaurante: Restaurante) {

    println()
    println("=== GERENCIAR CARDÁPIO ===")
    println("[A] Ver Cardápio")
    println("[B] Adicionar Item")
    println("[C] Remover Item")

    print("Escolha uma opção: ")
    val opcaoCardapio = readln()

    if (
        opcaoCardapio.equals(
            "A",
            ignoreCase = true
        )
    ) {

        println()
        println("=== CARDÁPIO ===")

        if (restaurante.menu.isEmpty()) {

            println("O cardápio está vazio.")

        } else {

            for (item in restaurante.menu) {

                println("Número: ${item.numero_item}")
                println("Descrição: ${item.descricao}")
                println("Preço: R$ ${item.preco}")
                println("----------------------------")
            }
        }

    } else if (
        opcaoCardapio.equals(
            "B",
            ignoreCase = true
        )
    ) {

        println()
        println("=== ADICIONAR ITEM ===")

        print("Digite o número do item: ")
        val numeroTexto = readln()

        val numeroItem =
            numeroTexto.toIntOrNull()

        if (numeroItem == null) {

            println()
            println(
                "Número do item inválido. Digite apenas números."
            )

            return
        }

        val itemJaExiste =
            restaurante.menu.find {
                it.numero_item == numeroItem
            }

        if (itemJaExiste != null) {

            println()
            println(
                "Erro: já existe um item com esse número."
            )

            return
        }

        print("Digite a descrição do item: ")
        val descricao = readln()

        print("Digite o preço do item: ")
        val precoTexto = readln()

        val preco =
            precoTexto.toDoubleOrNull()

        if (preco == null) {

            println()
            println(
                "Preço inválido. Digite apenas números."
            )

            return
        }

        if (preco <= 0) {

            println()
            println(
                "Preço inválido. Digite um valor maior que zero."
            )

            return
        }

        val novoItem = ItemMenu(
            numeroItem,
            descricao,
            preco
        )

        restaurante.menu.add(
            novoItem
        )

        val arquivo =
            buscarArquivoRestaurante(
                restaurante.email
            )

        if (arquivo != null) {

            val gson = GsonBuilder()
                .setPrettyPrinting()
                .create()

            val json =
                gson.toJson(restaurante)

            arquivo.writeText(json)

            println()
            println(
                "Item adicionado com sucesso!"
            )

        } else {

            println()
            println(
                "Erro ao localizar o arquivo do restaurante."
            )
        }

    } else if (
        opcaoCardapio.equals(
            "C",
            ignoreCase = true
        )
    ) {

        println()
        println("=== REMOVER ITEM ===")

        print(
            "Digite o número do item que deseja remover: "
        )

        val numeroTexto = readln()

        val numeroItem =
            numeroTexto.toIntOrNull()

        if (numeroItem == null) {

            println()
            println(
                "Número do item inválido. Digite apenas números."
            )

            return
        }

        val itemEncontrado =
            restaurante.menu.find {
                it.numero_item == numeroItem
            }

        if (itemEncontrado != null) {

            restaurante.menu.remove(
                itemEncontrado
            )

            val arquivo =
                buscarArquivoRestaurante(
                    restaurante.email
                )

            if (arquivo != null) {

                val gson = GsonBuilder()
                    .setPrettyPrinting()
                    .create()

                val json =
                    gson.toJson(restaurante)

                arquivo.writeText(json)

                println()
                println(
                    "Item removido com sucesso!"
                )

            } else {

                println()
                println(
                    "Erro ao localizar o arquivo do restaurante."
                )
            }

        } else {

            println()
            println(
                "Item não encontrado."
            )
        }

    } else {

        println()
        println(
            "Opção inválida."
        )
    }
}

fun cadastrarRestaurante() {

    println()
    println(
        "=== NOVO CADASTRO ==="
    )

    print(
        "Digite o nome do restaurante: "
    )
    val nome = readln()

    print(
        "Digite o e-mail: "
    )
    val email = readln()

    if (
        emailJaCadastrado(email)
    ) {

        println()
        println(
            "Erro: este e-mail já está cadastrado."
        )

        return
    }

    print(
        "Digite o endereço: "
    )

    val endereco =
        readln()

    val menu =
        mutableListOf<ItemMenu>()

    println()
    println(
        "=== CADASTRO INICIAL DO CARDÁPIO ==="
    )

    while (true) {

        print(
            "Digite o número do item ou pressione Enter para finalizar: "
        )

        val numeroItem =
            readln()

        if (
            numeroItem.isEmpty()
        ) {
            break
        }

        val numero =
            numeroItem.toIntOrNull()

        if (numero == null) {

            println()
            println(
                "Número do item inválido. Digite apenas números."
            )
            println()

            continue
        }

        val itemJaExiste =
            menu.find {
                it.numero_item == numero
            }

        if (
            itemJaExiste != null
        ) {

            println()
            println(
                "Erro: já existe um item com esse número."
            )
            println()

            continue
        }

        print(
            "Digite a descrição do item: "
        )

        val descricao =
            readln()

        print(
            "Digite o preço do item: "
        )

        val precoTexto =
            readln()

        val preco =
            precoTexto.toDoubleOrNull()

        if (preco == null) {

            println()
            println(
                "Preço inválido. Digite apenas números."
            )
            println()

            continue
        }

        if (preco <= 0) {

            println()
            println(
                "Preço inválido. Digite um valor maior que zero."
            )
            println()

            continue
        }

        val item =
            ItemMenu(
                numero,
                descricao,
                preco
            )

        menu.add(
            item
        )

        println()
        println(
            "Item cadastrado com sucesso."
        )
        println()
    }

    val restaurante =
        Restaurante(
            nome,
            email,
            endereco,
            menu
        )

    val gson =
        GsonBuilder()
            .setPrettyPrinting()
            .create()

    val json =
        gson.toJson(
            restaurante
        )

    var id = 1

    while (
        File(
            "restaurante_$id.json"
        ).exists()
    ) {

        id++
    }

    val arquivo =
        File(
            "restaurante_$id.json"
        )

    arquivo.writeText(
        json
    )

    println()
    println(
        "Restaurante salvo com sucesso!"
    )

    println(
        "Arquivo criado: restaurante_$id.json"
    )

    println()
    println(
        "=== DADOS DO RESTAURANTE ==="
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

    println()
    println(
        "=== CARDÁPIO CADASTRADO ==="
    )

    if (
        restaurante.menu.isEmpty()
    ) {

        println(
            "O cardápio está vazio."
        )

    } else {

        for (
        item in restaurante.menu
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
    }
}

