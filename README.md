
# Programação Reativa com Spring WebFlux

<div align="justify">
A programação reativa é um paradigma de programação que lida com fluxos de dados assíncronos e eventos. Ela é especialmente útil em cenários onde você precisa lidar com alta concorrência e tem requisitos de baixa latência. O Spring Framework oferece o Spring WebFlux como uma solução para criar aplicativos reativos.
Ela é especialmente útil para lidar com eventos e interações em tempo real, como interfaces de usuário, streaming de dados e comunicação de rede. A programação reativa é mais comumente associada ao desenvolvimento de aplicativos em tempo real e pode ser aplicada em uma variedade de linguagens de programação, com bibliotecas e estruturas específicas para essa abordagem.

Aqui estão alguns conceitos-chave relacionados à programação reativa:


1. **Observáveis (Observables)**: Observáveis são fontes de dados que emitem valores ao longo do tempo. Eles representam fluxos de eventos, como cliques do mouse, atualizações de sensores, respostas de API, etc. Esses eventos podem ser assíncronos e ocorrer a qualquer momento.

2. **Observadores (Observers)**: Observadores são inscritos em observáveis para receber notificações sempre que um novo valor é emitido. Eles reagem aos valores emitidos pelos observáveis e realizam ações com base neles. Essas ações podem incluir atualizar a interface do usuário, processar dados ou acionar outras operações.

3. **Operadores (Operators)**: Os operadores são funções que permitem transformar, filtrar, combinar ou manipular os dados emitidos por observáveis. Eles permitem criar uma cadeia de transformações nos dados em um fluxo reativo.

4. **Assinaturas (Subscriptions)**: Uma assinatura é um vínculo entre um observador e um observável. Ela permite que o observador receba notificações dos valores emitidos pelo observável. É importante gerenciar assinaturas adequadamente para evitar vazamentos de recursos.

5. **Gestão de Estado**: A programação reativa muitas vezes simplifica a gestão de estado em aplicativos complexos. À medida que os eventos são propagados por meio de observáveis, os observadores podem reagir a eles e atualizar o estado do aplicativo de maneira reativa.

6. **Hot vs. Cold Observables**: Hot observables emitem eventos independentemente de haver observadores. Em contraste, cold observables começam a emitir eventos somente quando há observadores inscritos. A escolha entre hot e cold observables depende do cenário de uso.

Alguns exemplos de bibliotecas e estruturas que permitem a programação reativa incluem:

1. **RxJava**: Uma biblioteca para programação reativa em Java.

2. **RxJS**: Uma biblioteca para programação reativa em JavaScript.

3. **Reactor**: Uma biblioteca para programação reativa em Java, usada principalmente no desenvolvimento de aplicativos baseados em Spring.

4. **Rx.NET**: Uma biblioteca para programação reativa em C#.


A programação reativa é valiosa para lidar com tarefas assíncronas e eventos em aplicativos complexos, proporcionando um modelo mais conciso e compreensível para trabalhar com fluxos de dados em tempo real. Ela pode melhorar a eficiência, a legibilidade e a manutenção do código, tornando-a uma abordagem popular em desenvolvimento de software moderno.
</div>

## O que é o Spring WebFlux?

<div align="justify">
O Spring WebFlux é um projeto do ecossistema Spring que permite o desenvolvimento de aplicativos reativos. Ele fornece um modelo de programação baseado em eventos, no qual os desenvolvedores podem trabalhar com fluxos de dados assíncronos. O Spring WebFlux é uma alternativa ao Spring MVC, que é baseado em um modelo de programação síncrona.

Principais características do Spring WebFlux:

1. **Programação Reativa**: O Spring WebFlux permite que os desenvolvedores escrevam código reativo usando o Project Reactor, uma biblioteca de programação reativa.

2. **Modelo Não-Bloqueante**: O modelo não-bloqueante do Spring WebFlux é adequado para operações de entrada e saída assíncronas, como E/S de rede.

3. **Suporte Anotação**: O Spring WebFlux oferece suporte para anotações, tornando-o fácil de usar e integrar com outros módulos do Spring.

4. **Back Pressure**: Ele lida com back pressure, permitindo que os consumidores controlem a taxa de recebimento de dados, evitando sobrecarga.

5. **Flexibilidade**: Pode ser usado com servidores incorporados ou em contêineres tradicionais.
6. 
</div>

## Componentes do Spring WebFlux

<div align="justify">
O Spring WebFlux consiste em vários componentes essenciais:

- **Controladores Anotados**: Da mesma forma que o Spring MVC, o Spring WebFlux utiliza controladores anotados para manipular solicitações HTTP.

- **Router Functions**: Além dos controladores anotados, o Spring WebFlux também oferece uma maneira funcional de definir rotas usando o `RouterFunction`, o que pode ser útil para cenários mais complexos.

- **Mono e Flux**: O Spring WebFlux trabalha com dois tipos principais, `Mono` e `Flux`, que representam, respectivamente, zero ou um resultado, e zero ou muitos resultados. Esses tipos são usados para representar valores assíncronos.

- **Serviços e Repositórios**: Os serviços e repositórios são usados para manipular a lógica de negócios e o acesso a dados em seu aplicativo.

O Spring WebFlux é uma ferramenta poderosa para desenvolver aplicativos reativos em Java. Ele permite que você crie aplicativos que são escaláveis e eficientes, especialmente em cenários de alta concorrência e baixa latência. Com o uso de `Mono` e `Flux`, juntamente com a programação reativa, o Spring WebFlux oferece uma abordagem moderna para o desenvolvimento de aplicativos baseados em eventos.

Lembre-se de que a escolha entre o Spring MVC e o Spring WebFlux depende dos requisitos específicos do seu aplicativo e do estilo de programação preferido. O Spring WebFlux é uma excelente escolha para aplicativos reativos, enquanto o Spring MVC continua sendo uma opção sólida para aplicativos síncronos tradicionais.

Para saber mais sobre o Spring WebFlux, consulte a [documentação oficial](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html).
</div>

# Programação Assíncrona X Programação Síncrona

<div align="justify">
A programação reativa lida principalmente com fluxos de informações. Ela é especialmente útil quando se trata de tratar eventos e dados que fluem de forma assíncrona, como entradas do 
usuário, respostas de API, atualizações de sensores e muitos outros tipos de eventos em tempo real. Vamos discutir as diferenças entre programação reativa síncrona e assíncrona:
</div>

## Programação Síncrona:

<div align="justify">
Na programação síncrona, as operações ocorrem de forma sequencial, uma após a outra. Quando um código é executado, ele bloqueia o thread de execução até que a operação seja concluída. 
Isso significa que o programa espera pela operação para continuar. A programação síncrona é mais simples de entender e depurar, mas não é eficaz quando se lida com operações que podem 
ser demoradas, como leitura de arquivos ou comunicações de rede.
</div>

## Programação Assíncrona:

<div align="justify">
Na programação reativa, a ênfase é na programação assíncrona, que lida com operações que podem ocorrer de forma independente e, portanto, não bloqueiam o thread principal de execução. Em vez de esperar que uma operação seja concluída, um código assíncrono permite que outras operações continuem enquanto a operação assíncrona é processada em segundo plano. Isso é especialmente importante para manter a capacidade de resposta em aplicativos que exigem interatividade em tempo real.
</div>

# Conceito Observable e Observer

A programação reativa no contexto do Spring Boot envolve o uso de "Observable" e "Observer" para criar aplicativos reativos. Esses conceitos são parte integrante do modelo de programação reativa e são amplamente utilizados em bibliotecas como o Project Reactor, que é a base do Spring WebFlux.

## Observable

- Um "Observable" é uma fonte de eventos ou fluxos de dados.
- Representa uma sequência de itens ou eventos que podem ser observados ao longo do tempo.
- Em Spring Boot, "Observable" é representado por tipos de dados como `Mono` e `Flux`, que são fornecidos pelo Project Reactor.
- "Mono" representa um "Observable" que emite no máximo um item, enquanto "Flux" representa um "Observable" que pode emitir zero ou mais itens.
- Observáveis são assíncronos, permitindo que os consumidores respondam a eventos à medida que ocorrem.

## Observer

- Um "Observer" é um componente que subscreve a um "Observable" para receber notificações sempre que um evento ou item é emitido.
- Em Spring Boot, os "Observers" são representados por funções lambda, manipuladores de eventos ou lógica que reage aos eventos emitidos pelos "Observables".
- Os "Observers" podem executar ações específicas com base nos itens emitidos pelos "Observables".
- Eles podem lidar com os eventos de maneira reativa, como processar dados, responder a solicitações de API ou atualizar a interface do usuário em tempo real.

## Exemplo de Uso

Aqui está um exemplo simples de como "Observable" e "Observer" podem ser usados em um controlador Spring Boot:

```java
@RestController
public class ExemploController {

    @GetMapping("/api/exemplo")
    public Mono<String> getExemplo() {
        return Mono.just("Olá, mundo!"); // Observable
    }

    @GetMapping("/api/observe")
    public Mono<String> observeExemplo() {
        return Mono.just("Olá, mundo!")
            .doOnNext(item -> {
                // Observer: Executa ação com base no item emitido
                logger.info("Item observado: " + item);
            });
    }
}
```
Neste exemplo, o primeiro endpoint /api/exemplo retorna um "Observable" simples, enquanto o segundo endpoint /api/observe utiliza um "Observer" para registrar e observar o item emitido pelo "Observable".

A programação reativa com Spring Boot é útil para criar aplicativos eficientes e responsivos, especialmente em cenários com alta concorrência e requisitos de baixa latência.

# Flux e Mono

<div align="justify">
O Spring Boot, em conjunto com o Project Reactor, fornece tipos de dados poderosos chamados "Flux" e "Mono" para lidar com programação reativa. Eles desempenham um papel crucial ao criar aplicativos reativos no ecossistema Spring.
</div>

## Flux

- "Flux" é um tipo de dado usado para representar uma sequência potencialmente infinita de itens ou eventos.
- Pode emitir zero, um ou vários itens ao longo do tempo.
- É útil quando se lida com múltiplos eventos em um fluxo de dados, como transmissões em tempo real, log de eventos, etc.
- "Flux" é uma escolha comum para manipular coleções de itens ou fluxos de eventos assíncronos.

## Mono

- "Mono" é um tipo de dado usado para representar um único valor ou uma ausência de valor.
- Pode emitir no máximo um item.
- É ideal para representar o resultado de uma operação assíncrona que pode ter sucesso com um único valor ou falhar.
- "Mono" é comumente usado em operações de E/S, como chamadas de API, consultas de banco de dados, etc.

## Uso comum

### Exemplo de uso de Flux:

```java
@GetMapping("/api/eventos")
public Flux<Evento> getEventos() {
    return eventoService.getEventos(); // Retorna um Flux de eventos
}
```
Neste exemplo, a rota /api/eventos retorna um "Flux" que pode ser observado em tempo real.

### Exemplo de uso de Mono:

```java
@PostMapping("/api/criarEvento")
public Mono<Evento> criarEvento(@RequestBody Evento evento) {
    return eventoService.criarEvento(evento); // Retorna um Mono de evento
}
```
<div align="justify">
Neste caso, a rota /api/criarEvento recebe um objeto de evento no corpo da solicitação e retorna um "Mono" representando o evento criado.

A programação reativa com "Flux" e "Mono" no Spring Boot é especialmente útil para criar aplicativos eficientes e responsivos, permitindo lidar com eventos assíncronos e operações de E/S de maneira eficaz.

Lembre-se de escolher entre "Flux" e "Mono" com base em suas necessidades específicas, dependendo se você está lidando com múltiplos eventos ou operações de E/S únicas.
</div>

# `map` e `flatMap` 

<div align="justify">
Dentro do ecossistema Spring Boot, `flatMap` e `map` são métodos amplamente utilizados para transformar, processar e manipular dados em fluxos reativos, como aqueles representados por `Flux` e `Mono` do Project Reactor. Ambos desempenham um papel fundamental na programação reativa.
</div>

## `map`

- O método `map` é usado para transformar cada elemento em um fluxo reativo (por exemplo, um `Flux`) em um novo elemento.
- Ele aplica uma função a cada elemento do fluxo e retorna um novo fluxo com os elementos transformados.
- É útil para operações de transformação simples, como mapear valores de um tipo para outro ou aplicar funções de processamento em cada elemento.

**Exemplo de uso de `map`**:

```java
Flux<Integer> numeros = Flux.just(1, 2, 3, 4, 5);
Flux<String> numerosString = numeros.map(numero -> "Número: " + numero);
```

Neste exemplo, map é usado para transformar um fluxo de números inteiros em um fluxo de strings, adicionando um prefixo "Número: " a cada valor.

## flatMap

<div align="justify">
O método flatMap é usado para transformar cada elemento em um fluxo reativo em zero, um ou vários elementos de outro fluxo.
Ele permite operações mais complexas, como aplanar fluxos aninhados ou realizar consultas de banco de dados e retornar fluxos reativos como resultado.
É útil quando você precisa realizar operações que geram fluxos internos de elementos.
Exemplo de uso de flatMap:
</div>

**Exemplo de uso de `flatMap`**:

```java
Flux<Usuario> usuarios = // Um flux de objetos de usuário
Flux<Publicacao> publicacoes = usuarios.flatMap(usuario -> publicacaoService.getPublicacoesDoUsuario(usuario.getId()));
```

Neste exemplo, `flatMap` é usado para recuperar as publicações de vários usuários e aplaná-las em um único fluxo de publicações.

## Escolhendo Entre `map` e `flatMap`

<div align="justify">
A escolha entre `map` e `flatMap` depende das necessidades específicas do seu aplicativo:

- Use `map` quando precisar transformar individualmente cada elemento de um fluxo em outro tipo.
- Use `flatMap` quando precisar realizar operações que geram fluxos internos ou quando precisar de uma maneira de aplanar esses fluxos internos em um único fluxo.

Ambos os métodos desempenham um papel fundamental na construção de operações reativas complexas e eficazes no Spring Boot.
</div>

# Classe Cliente

<div align="justify">
A classe `Cliente` representa um documento armazenado em um banco de dados MongoDB e é usada para modelar informações de clientes em um sistema. Esta classe faz parte de um aplicativo que utiliza o Spring Boot e o Spring Data MongoDB para operações de banco de dados.
</div>

```java
package com.daniel.documentos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clientes")
public class Cliente {

	@Id
    private String id;

    @NotEmpty
    private String nome;

    @NotEmpty
    private String sobrenome;

    @NotNull
    private Integer idade;

    @NotNull
    private Double salario;

    private String foto;

    // Constructor
    public Cliente() {

    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
```

## Atributos

- **id (String):** O identificador exclusivo do cliente no banco de dados.

- **nome (String):** O nome do cliente.

- **sobrenome (String):** O sobrenome do cliente.

- **idade (Integer):** A idade do cliente.

- **salario (Double):** O salário do cliente.

- **foto (String):** Um campo opcional que armazena o nome de um arquivo de imagem associado ao cliente.

## Anotações de Validação

- **@NotEmpty:** As anotações `@NotEmpty` garantem que os campos `nome` e `sobrenome` não podem ser vazios, ou seja, devem conter valores não nulos e não vazios.

- **@NotNull:** As anotações `@NotNull` garantem que os campos `idade` e `salario` não podem ser nulos, ou seja, devem conter valores válidos.

## Uso

<div align="justify">
<p>A classe Cliente é essencial para a representação e manipulação de informações de clientes em um aplicativo Spring Boot que utiliza o MongoDB como seu banco de dados. Ela fornece uma estrutura organizada para armazenar informações relevantes do cliente, como nome, sobrenome, idade e salário.</p>
<p>O uso de anotações de validação garante que os dados do cliente sejam consistentes e válidos. Os métodos getters e setters facilitam o acesso aos atributos da classe.</p>
<p>No contexto de um aplicativo Spring Boot, a classe Cliente é um componente fundamental para a gestão de clientes e a interação com o banco de dados MongoDB.</p>
</div>

# Interface ClienteDao

<div align="justify">
A interface `ClienteDao` é parte de um aplicativo Spring Boot que utiliza o Spring Data MongoDB para realizar operações de persistência de dados no banco de dados MongoDB. Esta interface estende a classe `ReactiveMongoRepository`, que fornece métodos predefinidos para operações CRUD no banco de dados.
</div>

```java
package com.daniel.daos;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.daniel.documentos.Cliente;

public interface ClienteDao extends ReactiveMongoRepository<Cliente, String>{

}
```
## Métodos Herdados

A interface `ClienteDao` herda métodos da classe `ReactiveMongoRepository`. Esses métodos são genéricos e incluem operações comuns de banco de dados, como inserção, consulta, atualização e exclusão de documentos da coleção "clientes" no banco de dados MongoDB.

Alguns dos métodos herdados incluem:

- `save(S)` - Salva um novo documento no banco de dados ou atualiza um existente.

- `findById(ID)` - Encontra um documento com base no seu ID.

- `findAll()` - Recupera todos os documentos da coleção.

- `delete(ID)` - Exclui um documento com base no seu ID.

- E muitos outros métodos úteis para consultas e operações de banco de dados.

## Tipos de Dados

A interface `ClienteDao` é parametrizada com dois tipos de dados:

- **Cliente:** O primeiro tipo de dado especifica a classe do objeto que está sendo armazenado no banco de dados. Neste caso, é a classe `Cliente`, que representa os dados dos clientes.

- **String:** O segundo tipo de dado especifica o tipo de dado usado para identificar exclusivamente os documentos no banco de dados. Neste caso, é uma `String` que representa o ID dos documentos.

## Uso
<div align="justify">
<p>A interface `ClienteDao` não requer uma implementação explícita, pois herda todos os métodos necessários da classe `ReactiveMongoRepository`. Ela é usada por componentes de serviço e controladores do aplicativo Spring Boot para realizar operações de banco de dados em documentos de clientes.</p>
<p>A interface ClienteDao é um componente fundamental em um aplicativo Spring Boot que utiliza o Spring Data MongoDB para interagir com um banco de dados MongoDB. Ela simplifica as operações de banco de dados, fornecendo métodos predefinidos para a manipulação de documentos da coleção "clientes".</p>
<p>Esta interface permite que o aplicativo realize operações de CRUD de forma eficiente e é essencial para a persistência de dados de clientes no contexto do aplicativo.</p>
</div>

# Interface ClienteService 

<div align="justify">
A interface `ClienteService` define um conjunto de métodos que representam operações de serviço relacionadas aos clientes em um aplicativo Spring Boot que utiliza o Spring Data MongoDB para operações de banco de dados reativas. Esta interface é projetada para fornecer funcionalidades de serviço para a manipulação de dados do cliente.
</div>

## Métodos

### findAll()

O método `findAll()` retorna um objeto `Flux` que representa uma sequência de todos os clientes armazenados no banco de dados. É usado para recuperar todos os clientes existentes.

### findById(String id)

O método `findById(String id)` aceita um parâmetro de ID (identificador) e retorna um objeto `Mono` que representa um cliente com base no ID fornecido. É usado para recuperar um cliente específico pelo seu ID.

### save(Cliente cliente)

O método `save(Cliente cliente)` aceita um objeto `Cliente` como parâmetro e retorna um objeto `Mono` que representa o cliente após a inserção ou atualização no banco de dados. É usado para salvar ou atualizar um cliente no banco de dados.

### delete(Cliente cliente)

O método `delete(Cliente cliente)` aceita um objeto `Cliente` como parâmetro e retorna um objeto `Mono<Void>`. É usado para excluir um cliente do banco de dados.

## Uso

<div align="justify">
<p> A interface `ClienteService` é implementada por classes de serviço em um aplicativo Spring Boot. Os métodos definidos nesta interface são chamados pelos controladores ou outros componentes para realizar operações relacionadas aos clientes.</p>
<p>A interface ClienteService desempenha um papel essencial na arquitetura de um aplicativo Spring Boot reativo com o uso do Spring Data MongoDB. Ela define operações de serviço para manipulação de dados de clientes, incluindo busca, inserção, atualização e exclusão.</p>
<p>Os métodos desta interface são implementados por classes de serviço e são invocados por controladores ou outros componentes do aplicativo para lidar com operações relacionadas a clientes. Isso permite uma separação clara entre a camada de controle e a lógica de serviço.</p>
</div>

# Classe ClienteServiceImpl 

A classe `ClienteServiceImpl` faz parte de um aplicativo Spring Boot que utiliza o Spring Data MongoDB para realizar operações de serviço relacionadas aos clientes. Ela é uma implementação da interface `ClienteService` e fornece lógica de serviço para manipular dados de clientes.

```java
package com.daniel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daniel.daos.ClienteDao;
import com.daniel.documentos.Cliente;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClienteServiceImpl implements ClienteService {

	    @Autowired
	    private ClienteDao clienteDao;

	    @Override
	    public Flux<Cliente> findAll() {
	      return clienteDao.findAll();
	    }

	    @Override
	    public Mono<Cliente> findById(String id) {
	      return clienteDao.findById(id);
	    }

	    @Override
	    public Mono<Cliente> save(Cliente cliente) {
	       return clienteDao.save(cliente);
	    }

	    @Override
	    public Mono<Void> delete(Cliente cliente) {
	      return clienteDao.delete(cliente);
	    }
}
```

## Anotações

- `@Service`: A anotação `@Service` é usada para indicar que esta classe é um componente de serviço gerenciado pelo Spring Framework. Ela é responsável por encapsular a lógica de negócios relacionada aos clientes.

- `@Autowired`: A anotação `@Autowired` é usada para injetar a dependência da classe `ClienteDao` no serviço. Isso permite que o serviço acesse as operações de banco de dados definidas na interface `ClienteDao`.

## Métodos

### findAll()
<div align="justify">
O método `findAll()` é uma implementação do método da interface `ClienteService` e retorna um objeto `Flux` que representa uma sequência de todos os clientes armazenados no banco de dados. Ele é usado para recuperar todos os clientes existentes.
</div>

### findById(String id)
<div align="justify">
O método `findById(String id)` é uma implementação do método da interface `ClienteService` e aceita um parâmetro de ID (identificador). Ele retorna um objeto `Mono` que representa um cliente com base no ID fornecido. É usado para recuperar um cliente específico pelo seu ID.
</div>

### save(Cliente cliente)
<div align="justify">
O método `save(Cliente cliente)` é uma implementação do método da interface `ClienteService`. Ele aceita um objeto `Cliente` como parâmetro e retorna um objeto `Mono` que representa o cliente após a inserção ou atualização no banco de dados. É usado para salvar ou atualizar um cliente no banco de dados.
</div>

### delete(Cliente cliente)
<div align="justify">
O método `delete(Cliente cliente)` é uma implementação do método da interface `ClienteService`. Ele aceita um objeto `Cliente` como parâmetro e retorna um objeto `Mono<Void>`. É usado para excluir um cliente do banco de dados.
</div>
  
## Uso
<div align="justify">
<p>A classe `ClienteServiceImpl` é usada como um componente de serviço em um aplicativo Spring Boot. Ela encapsula a lógica de serviço relacionada aos clientes e realiza operações de banco de dados por meio da injeção da classe `ClienteDao`.</p>

<p>A classe ClienteServiceImpl desempenha um papel importante na camada de serviço de um aplicativo Spring Boot reativo com o uso do Spring Data MongoDB. Ela implementa as operações definidas na interface ClienteService e fornece lógica de serviço para manipular dados de clientes.</p>

<p>Essa classe facilita a interação com o banco de dados, permitindo que as operações CRUD sejam realizadas de forma reativa. Isso ajuda a manter uma separação clara entre a lógica de serviço e a camada de controle do aplicativo.</p>
</div>

# Classe ClienteController

A classe `ClienteController` é parte de um aplicativo Spring Boot que fornece endpoints RESTful para gerenciar informações de clientes. Ela atua como um controlador REST e é responsável por lidar com solicitações HTTP relacionadas a clientes, como criar, ler, atualizar e excluir.

## Anotações

- `@RestController`: A anotação `@RestController` é usada para indicar que esta classe é um controlador REST e deve ser escaneada em busca de manipuladores de solicitação HTTP.

- `@RequestMapping("/api/clientes")`: A anotação `@RequestMapping` define o prefixo da URL para todos os endpoints definidos nesta classe.

- `@Autowired`: A anotação `@Autowired` é usada para injetar a dependência da classe `ClienteService` no controlador. Isso permite que o controlador acesse os métodos de serviço relacionados aos clientes.

## Endpoints

### POST /registrarClienteFoto

Este endpoint permite registrar um cliente com uma foto. Ele aceita um objeto `Cliente` e um arquivo de imagem como entrada. O arquivo de imagem é armazenado no servidor com um nome gerado aleatoriamente. Após o armazenamento da imagem, o cliente é salvo no banco de dados e o cliente criado é retornado como resposta.

### POST /upload/{id}

Este endpoint permite fazer upload de uma foto para um cliente existente com base no ID fornecido. A foto é armazenada no servidor com um nome gerado aleatoriamente. Após o armazenamento da imagem, o cliente é atualizado no banco de dados com a nova foto e o cliente atualizado é retornado como resposta.

### GET /

Este endpoint retorna todos os clientes cadastrados no banco de dados em formato JSON.

### GET /{id}

Este endpoint retorna os detalhes de um cliente específico com base no ID fornecido. Se o cliente não for encontrado, é retornada uma resposta "não encontrado".

### POST /

Este endpoint permite criar um novo cliente. Aceita um objeto `Cliente` como entrada e retorna o cliente criado como resposta. Se ocorrerem erros de validação, uma resposta de erro com detalhes será retornada.

### PUT /{id}

Este endpoint permite atualizar um cliente existente com base no ID fornecido. Ele aceita um objeto `Cliente` como entrada, atualiza os dados do cliente no banco de dados e retorna o cliente atualizado como resposta.

### DELETE /{id}

Este endpoint permite excluir um cliente com base no ID fornecido. Se o cliente for encontrado e excluído com sucesso, uma resposta "sem conteúdo" é retornada. Caso contrário, uma resposta "não encontrado" é retornada.

## Uso

<div align="justify">
<p>A classe `ClienteController` é usada como um controlador REST em um aplicativo Spring Boot para fornecer uma API de gerenciamento de clientes. Ela lida com solicitações HTTP de entrada, chama métodos do serviço `ClienteService` e retorna respostas HTTP apropriadas.</p>
<p>A classe ClienteController desempenha um papel fundamental na exposição de endpoints RESTful para operações relacionadas a clientes em um aplicativo Spring Boot. Ela fornece uma interface para criar, ler, atualizar e excluir informações de clientes por meio de solicitações HTTP.</p>
</div>

# Configuração `application.properties`

Neste aplicativo Spring Boot, foram configuradas as seguintes propriedades:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/springboot_webflux
config.uploads.path=C://Users//d4nan//Pictures//projetoSpringBootWebFlux-foto//fotoPerfil
```

- `spring.data.mongodb.uri`: Essa propriedade define a URI de conexão ao banco de dados MongoDB. O URI especifica o host (`localhost`), a porta (`27017`) e o nome do banco de dados (`springboot_webflux`). Isso é essencial para estabelecer a conexão com o banco de dados MongoDB.

- `config.uploads.path`: Esta propriedade especifica o caminho onde as imagens enviadas pelos clientes serão armazenadas no servidor. No exemplo fornecido, as imagens são armazenadas em `C://Users//d4nan//Pictures//projetoSpringBootWebFlux-foto//fotoPerfil`.

## Banco de Dados MongoDB
<div align="justify">
O aplicativo utiliza o banco de dados MongoDB para armazenar os dados dos clientes. O MongoDB é um banco de dados NoSQL amplamente utilizado, conhecido por sua flexibilidade e escalabilidade. É um banco de dados orientado a documentos, o que significa que os dados são armazenados em documentos no formato BSON (Binary JSON). O MongoDB é particularmente adequado para aplicativos que requerem flexibilidade na modelagem de dados e escalabilidade horizontal.
</div>

## Autor
Desenvolvido por **`Daniel Penelva de Andrade`**. 

