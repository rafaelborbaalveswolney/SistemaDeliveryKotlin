package cliente

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import model.Cliente
import java.io.File

fun criarArquivoClientesSeNaoExistir() {

    val arquivo = File("clientes.json")

    if (!arquivo.exists()) {

        arquivo.writeText("[]")

        println("Arquivo clientes.json criado com sucesso.")
        println()
    }
}

fun carregarClientes(): MutableList<Cliente> {

    val arquivo = File("clientes.json")
    val conteudo = arquivo.readText()

    val gson = Gson()

    val tipoLista =
        object : TypeToken<MutableList<Cliente>>() {}.type

    return gson.fromJson(conteudo, tipoLista)
}

fun telefoneJaCadastrado(telefone: String): Boolean {

    val clientes = carregarClientes()

    for (cliente in clientes) {

        if (cliente.telefone == telefone) {
            return true
        }
    }

    return false
}

fun buscarClientePorTelefone(telefone: String): Cliente? {

    val clientes = carregarClientes()

    for (cliente in clientes) {

        if (cliente.telefone == telefone) {
            return cliente
        }
    }

    return null
}

fun cadastrarCliente() {

    println()
    println("=== NOVO CADASTRO ===")

    print("Digite o nome: ")
    val nome = readln()

    print("Digite o telefone: ")
    val telefone = readln()

    if (telefoneJaCadastrado(telefone)) {

        println()
        println("Erro: este telefone já está cadastrado.")

        return
    }

    print("Digite o endereço: ")
    val endereco = readln()

    val novoCliente = Cliente(
        nome,
        telefone,
        endereco
    )

    val clientes = carregarClientes()

    clientes.add(novoCliente)

    val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()

    val json = gson.toJson(clientes)

    File("clientes.json").writeText(json)

    println()
    println("Cliente cadastrado com sucesso!")

    println()
    println("=== DADOS DO CLIENTE ===")
    println("Nome: ${novoCliente.nome}")
    println("Telefone: ${novoCliente.telefone}")
    println("Endereço: ${novoCliente.endereco}")
}