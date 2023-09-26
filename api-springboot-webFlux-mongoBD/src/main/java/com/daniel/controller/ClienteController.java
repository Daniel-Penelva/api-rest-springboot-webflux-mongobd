package com.daniel.controller;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;

import com.daniel.documentos.Cliente;
import com.daniel.service.ClienteService;
import com.daniel.service.ClienteServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	@Value("${config.uploads.path}")
	private String path;
	
	/**- registrando cliente com (upload) foto - http://localhost:8080/api/clientes/registrarClienteFoto
	    * Explicando passo a passo do código:
	     * 
	     * 1. `@PostMapping("registrarClienteFoto")`: Esta é uma anotação que mapeia a rota "registrarClienteFoto" para este método. Isso significa que 
	     *     quando uma solicitação HTTP POST é feita para "/registrarClienteFoto", este método será acionado.
	     * 
	     * 2. `public Mono<ResponseEntity<Cliente>> registrarClienteComFoto(Cliente cliente, @RequestPart FilePart file)`: Este é o método que lida com 
	     *     o registro do cliente. Ele recebe dois parâmetros: `Cliente cliente` e `FilePart file`. O primeiro parâmetro representa os dados do 
	     *     cliente, e o segundo parâmetro representa o arquivo de imagem que está sendo carregado.
	     * 
	     * 3. `cliente.setFoto(...)`: Neste trecho, um nome de arquivo para a foto é gerado com base no nome original do arquivo e um UUID 
	     *     (Identificador Único Universal) aleatório. O nome do arquivo é limpo de espaços em branco e caracteres especiais.
	     * 
	     * 4. `return file.transferTo(new File(path + cliente.getFoto()))`: Aqui, o arquivo de imagem é transferido para o local de armazenamento no 
	     *     servidor. O caminho do arquivo é construído concatenando o caminho raiz (`path`) com o nome do arquivo da foto gerado anteriormente.
	     * 
	     * 5. `then(clienteService.save(cliente) ...`: Após o upload bem-sucedido da imagem, o cliente é salvo no banco de dados. O método `save` 
	     *     retorna um `Mono<Cliente>`.
	     * 
	     * 6. `.map(c -> ResponseEntity.created(URI.create("/api/clientes".concat(c.getId())) ...`: Depois que o cliente é salvo no banco de dados, 
	     *      ele é mapeado para uma resposta HTTP. O `ResponseEntity` representa uma resposta HTTP, e neste caso, uma resposta de "created" 
	     *     (código de status 201) é criada, indicando que o registro foi bem-sucedido. O URI no cabeçalho de resposta aponta para a localização 
	     *     do novo recurso do cliente.
	     * 
	     * 7. `.contentType(MediaType.APPLICATION_JSON_UTF8).body(c)));`: A resposta é configurada com o tipo de mídia JSON e o corpo da resposta 
	     *     contém os detalhes do cliente recém-criado.
	     * 
	     * Em resumo, este método lida com o registro de um cliente, incluindo o upload de uma foto. O cliente é salvo no banco de dados, a foto é 
	     * armazenada no servidor e uma resposta HTTP é retornada para indicar o sucesso da operação. É uma ação comum em aplicativos web para lidar 
	     * com o envio de arquivos, como imagens de perfil de usuário.
	    */
	    @PostMapping("/registrarClienteFoto")
	    public Mono<ResponseEntity<Cliente>> registrarClienteComFoto(Cliente cliente, @RequestPart FilePart file){
	        cliente.setFoto(UUID.randomUUID().toString() + "-" + file.filename()
	            .replace(" ","")
	            .replace(":","")
	            .replace("//",""));
	        
	        return file.transferTo(new File(path + cliente.getFoto())).then(clienteService.save(cliente))
	        		.map(c -> ResponseEntity.created(URI.create("/api/clientes".concat(c.getId())))
	        				.contentType(MediaType.APPLICATION_JSON_UTF8).body(c));
	    }
	    
	    
	    /** subir (upload) foto - http://localhost:8080/api/clientes/upload/{id}
	     * Explicando passo a passo do código:
	     * 
	     * 1. `@PostMapping("/upload/{id}")`: Esta é uma anotação que mapeia uma solicitação HTTP POST para a rota "/upload/{id}". O `{id}` é uma 
	     *     variável de caminho (path variable) que permite que você especifique o ID do cliente na URL.
	     * 
	     * 2. `public Mono<ResponseEntity<Cliente>> subirFoto(@PathVariable String id, @RequestPart FilePart file)`: Este é o método que lida com o 
	     *     upload da foto do cliente. Ele recebe o ID do cliente da URL como um parâmetro de caminho e o arquivo de imagem como um parâmetro de 
	     * solicitação (`@RequestPart`).
	     * 
	     * 3. `return clienteService.findById(id).flatMap(c -> { ... })`: O método começa por buscar o cliente com o ID especificado usando o serviço 
	     *    `clienteService`. O método `findById(id)` retorna um `Mono<Cliente>` representando o cliente encontrado. Em seguida, ele usa o operador 
	     *    `flatMap` para executar uma ação quando o cliente for encontrado. A ação é definida como uma função lambda que recebe o cliente (`c`) e 
	     *     faz o seguinte:
	     * 
	     *     - Gera um novo nome de arquivo para a foto com base no nome original do arquivo, usando um UUID (Identificador Único Universal) 
	     *       gerado aleatoriamente e removendo espaços, dois pontos e barras (caracteres que podem causar problemas em nomes de arquivo).
	     * 
	     *     - Transfere o arquivo de imagem (`file`) para o local de armazenamento no servidor com o novo nome de arquivo.
	     * 
	     *     - Salva as alterações no objeto do cliente no banco de dados por meio do serviço `clienteService`.
	     * 
	     * 4. `.map(c -> ResponseEntity.ok(c))`: Após as operações de upload e salvamento serem concluídas com sucesso, o cliente modificado é 
	     *     mapeado para uma resposta HTTP "OK" (código de status 200) usando `ResponseEntity.ok(c)`, onde `c` é o cliente modificado. Isso 
	     *     indica que o upload foi bem-sucedido e a resposta contém o cliente atualizado.
	     * 
	     * 5. `.defaultIfEmpty(ResponseEntity.notFound().build())`: Se o cliente não for encontrado no banco de dados (por exemplo, se o ID 
	     *     especificado não corresponder a nenhum cliente existente), o método `findById(id)` retornará um `Mono` vazio. Nesse caso, o operador 
	     *    `defaultIfEmpty` é usado para fornecer uma resposta padrão, que é uma resposta HTTP "Não Encontrado" (código de status 404) criada com 
	     *    `ResponseEntity.notFound().build()`. Isso indica que o cliente não foi encontrado.
	     * 
	     * Em resumo, este método de controlador lida com o upload de uma foto para um cliente específico. Ele busca o cliente com base no ID 
	     * fornecido, realiza o upload da foto, atualiza o objeto do cliente e retorna uma resposta apropriada com base no resultado das operações. 
	     * Se o cliente não for encontrado, uma resposta "Não Encontrado" é retornada. Caso contrário, uma resposta "OK" contendo o cliente atualizado 
	     * é retornada para indicar um upload bem-sucedido.
	    */
	    @PostMapping("/upload/{id}")
	    public Mono<ResponseEntity<Cliente>> subirFoto(@PathVariable String id, @RequestPart FilePart file){
	        
	        return clienteService.findById(id).flatMap(c -> {
	            c.setFoto(UUID.randomUUID().toString() + "-" + file.filename()
	                .replace(" ","")
	                .replace(":","")
	                .replace("//",""));
	            
	            return file.transferTo(new File(path + c.getFoto())).then(clienteService.save(c));
	        }).map(c -> ResponseEntity.ok(c)).defaultIfEmpty(ResponseEntity.notFound().build());
	    }
	    
	    
	    /** Listar Clientes - http://localhost:8080/api/clientes
	     * Explicando passo a passo do código:
	     * 
	     * 1. `@GetMapping`: Esta é uma anotação que mapeia uma solicitação HTTP GET para este método. O método lida com solicitações para listar 
	     *     clientes.
	     * 
	     * 2. `public Mono<ResponseEntity<Flux<Cliente>>> listarClientes()`: Este é o método que lista os clientes. Ele retorna um `Mono` que 
	     *     encapsula uma `ResponseEntity` que, por sua vez, contém um `Flux` de clientes.
	     * 
	     * 3. `return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(clienteService.findAll()))`: Dentro do método, 
	     *     é criado um `Mono` usando `Mono.just(...)`. O conteúdo desse `Mono` é uma `ResponseEntity` que é construída da seguinte maneira:
	     * 
	     *   - `ResponseEntity.ok()`: Isso cria uma resposta HTTP com um código de status "OK" (código 200). Isso indica que a solicitação foi 
	     *      bem-sucedida.
	     * 
	     *   - `contentType(MediaType.APPLICATION_JSON_UTF8)`: Isso define o tipo de mídia da resposta como "application/json" com codificação UTF-8. 
	     *      Indica que o corpo da resposta conterá dados no formato JSON.
	     * 
	     *   - `body(clienteService.findAll())`: Aqui, o método `findAll()` do serviço `clienteService` provavelmente retorna um `Flux` de clientes. 
	     *      Esse `Flux` contém uma sequência de objetos `Cliente`.
	     * 
	     * 4. `Mono<ResponseEntity<Flux<Cliente>>>`: O tipo de retorno do método é `Mono<ResponseEntity<Flux<Cliente>>>`. Isso significa que o método 
	     *     retorna um `Mono` que, quando assinado (subscrito), produzirá uma resposta HTTP (um `ResponseEntity`) que contém um fluxo de clientes 
	     *     (um `Flux<Cliente>`).
	     * 
	     * Em resumo, este método de controlador lida com uma solicitação GET para listar todos os clientes. Ele retorna uma resposta HTTP "OK" com
	     * um corpo que contém um fluxo de clientes no formato JSON. Essa estrutura é comum em aplicativos Spring WebFlux, onde os resultados são 
	     * encapsulados em tipos reativos, como `Mono` e `Flux`, para lidar com programação reativa e assíncrona. O cliente pode usar a API REST 
	     * para consumir esses dados no formato JSON.
	    */
	    @GetMapping
	    public Mono<ResponseEntity<Flux<Cliente>>> listarClientes(){

	        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(clienteService.findAll()));
	    }


	    /**
	     * Listar detalhes de Clientes - http://localhost:8080/api/clientes/{id}
	     * 
	     * 1. `@GetMapping("/{id}")`: Esta é uma anotação que mapeia uma solicitação HTTP GET para uma rota que inclui um segmento de caminho 
	     *     variável `{id}`. O `{id}` é uma variável de caminho (path variable) que permite que você especifique o ID do cliente na URL.
	     * 
	     * 2. `public Mono<ResponseEntity<Cliente>> verDetalhesDeCliente(@PathVariable String id)`: Este é o método que lida com a visualização dos 
	     *     detalhes de um cliente com base no ID fornecido na URL. Ele recebe o ID do cliente da URL como um parâmetro de caminho usando a 
	     *     anotação `@PathVariable`.
	     * 
	     * 3. `return clienteService.findById(id).map(c -> { ... })`: O método começa por buscar o cliente com o ID especificado usando o serviço 
	     *    `clienteService`. O método `findById(id)` provavelmente retorna um `Mono<Cliente>` que representa o cliente encontrado. Em seguida, 
	     *     ele usa o operador `map` para mapear o cliente (`c`) para uma resposta HTTP.
	     * 
	     *     - `ResponseEntity.ok()`: Isso cria uma resposta HTTP com um código de status "OK" (código 200), indicando que a solicitação foi 
	     *        bem-sucedida.
	     * 
	     *     - `contentType(MediaType.APPLICATION_JSON_UTF8)`: Isso define o tipo de mídia da resposta como "application/json" com codificação 
	     *        UTF-8. Isso indica que o corpo da resposta conterá dados no formato JSON.
	     * 
	     *     - `body(c)`: Aqui, o corpo da resposta é definido como o cliente (`c`) encontrado no banco de dados. O cliente é serializado em 
	     *        formato JSON e é o conteúdo da resposta.
	     * 
	     * 4. `.defaultIfEmpty(ResponseEntity.notFound().build())`: Se o cliente não for encontrado no banco de dados (por exemplo, se o ID 
	     *     especificado não corresponder a nenhum cliente existente), o método `findById(id)` retornará um `Mono` vazio. Nesse caso, o operador 
	     *     `defaultIfEmpty` é usado para fornecer uma resposta padrão. Essa resposta padrão é criada com `ResponseEntity.notFound().build()`, o 
	     *      que indica que o cliente não foi encontrado e retorna um código de status "Não Encontrado" (código 404).
	     * 
	     * Em resumo, este método de controlador lida com uma solicitação GET para visualizar os detalhes de um cliente com base no ID fornecido na 
	     * URL. Se o cliente é encontrado no banco de dados, uma resposta HTTP "OK" com os detalhes do cliente em formato JSON é retornada. Se o 
	     * cliente não for encontrado, uma resposta "Não Encontrado" é retornada. Essa estrutura é comum em aplicativos Spring WebFlux para fornecer 
	     * respostas reativas e assíncronas para solicitações REST.
	    */
	    @GetMapping("/{id}")
	    public Mono<ResponseEntity<Cliente>> verDetalhesDeCliente(@PathVariable String id){
	        return clienteService.findById(id).map(c -> ResponseEntity.ok().
	            contentType(MediaType.APPLICATION_JSON_UTF8).body(c)).defaultIfEmpty(ResponseEntity.notFound().build());
	    }
	    
	    
	    /**
	     * Criando cliente - http://localhost:8080/api/clientes/
	     * Explicando passo a passo do código:
	     * 
	     * 1. `@PostMapping`: Esta é uma anotação que mapeia uma solicitação HTTP POST para este método de controlador. Isso significa que este método 
	     *     será executado quando uma solicitação POST for feita para o URL mapeado por padrão nesta classe de controlador.
	     *     
	     * 2. `public Mono<ResponseEntity<Map<String, Object>>> criarCliente(@Valid @RequestBody Mono<Cliente> monoCliente)`: Este é o método que lida 
	     *     com a criação de um cliente. Ele recebe um objeto `Mono` chamado `monoCliente` que contém os dados do cliente a ser criado. O `@Valid` 
	     *     indica que as validações definidas nas classes de entidade (no seu caso, na classe `Cliente`) devem ser aplicadas aos dados recebidos.
	     *     
	     * 3. `Map<String, Object> resposta = new HashMap<String, Object>();`: Aqui, é criado um mapa para armazenar a resposta que será retornada ao 
	     *     cliente. O mapa será preenchido com informações sobre o cliente ou erros, dependendo do resultado da operação.
	     *     
	     * 4. `return monoCliente.flatMap(cliente -> { ... }):`: O método começa por usar o operador `flatMap` em `monoCliente`. O `flatMap` é usado para 
	     *     manipular o valor contido no `Mono`. No caso, ele pega o cliente recebido, que é do tipo `Cliente`, e continua a execução.
	     * 
	     * 5. `clienteService.save(cliente).map(c -> { ... }):`: Aqui, o método `save` do serviço `clienteService` é chamado para salvar o cliente no 
	     *     banco de dados. O resultado é um `Mono<Cliente>`. Em seguida, o operador `map` é usado para mapear o cliente salvo (`c`) em uma resposta.
	     *     
	     * 6. O bloco dentro de `map` preenche o mapa de resposta com informações sobre o cliente, como o cliente em si, uma mensagem de sucesso e um 
	     * carimbo de data e hora. Em seguida, ele cria uma resposta HTTP com um código de status "Criado" (código 201), que indica que a operação foi 
	     * bem-sucedida.
	     * 
	     * 7. `onErrorResume(t -> { ... }):`: Se ocorrer algum erro durante a execução do código dentro de `flatMap` (por exemplo, validação ou erro de 
	     *     banco de dados), o método `onErrorResume` captura o erro e lida com ele. Ele pode ser usado para fornecer uma resposta alternativa em caso 
	     *     de erro.
	     *     
	     * 8. `Mono.just(t).cast(WebExchangeBindException.class) ...`: Dentro do bloco `onErrorResume`, o código tenta identificar se o erro é uma 
	     *     instância de `WebExchangeBindException`. Se for, ele lida com os erros de validação dos campos.
	     *     
	     * 9. O código dentro do bloco `flatMap` manipula os erros de validação, coletando os detalhes dos erros de campo em uma lista e preenchendo o 
	     *    mapa de resposta com informações sobre os erros. Em seguida, ele cria uma resposta HTTP com um código de status "Solicitação Inválida" 
	     *    (código 400).
	     * 
	     * Em resumo, este método de controlador lida com a criação de um cliente, valida os dados do cliente e retorna uma resposta apropriada com 
	     * informações sobre o cliente ou erros, dependendo do resultado da operação. É um exemplo de como o Spring WebFlux pode lidar com operações 
	     * assíncronas e respostas reativas em um controlador RESTful.
	     * */
	    @PostMapping
	    public Mono<ResponseEntity<Map<String, Object>>> criarCliente(@Valid @RequestBody Mono<Cliente> monoCliente){
	        
	        Map<String, Object> resposta = new HashMap<String, Object>();

	        return monoCliente.flatMap(cliente -> {
	            return clienteService.save(cliente).map(c -> {
	                resposta.put("cliente", c);
	                resposta.put("mensagem", "Cliente criado com sucesso");
	                resposta.put("timestamp", new Date());
	                return ResponseEntity.created(URI.create("/api/clientes/".concat(c.getId())))
	                    .contentType(MediaType.APPLICATION_JSON_UTF8)
	                    .body(resposta);
	            });
	        }).onErrorResume(t -> {
	            return Mono.just(t).cast(WebExchangeBindException.class)
	                .flatMap(e -> Mono.just(e.getFieldErrors()))     
	                .flatMapMany(Flux::fromIterable)
	                .map(fieldError -> "O campo" + fieldError.getField() + " " + fieldError.getDefaultMessage())
	                .collectList()
	                .flatMap(list -> {
	                    resposta.put("errors", list);
	                    resposta.put("timestamp", new Date());
	                    resposta.put("status", HttpStatus.BAD_REQUEST.value());

	                    return Mono.just(ResponseEntity.badRequest().body(resposta));
	                });
	        });
	    }
	    
	    
	    /**
	     * Criando cliente - http://localhost:8080/api/clientes/{id}
	     * Explicando passo a passo do código:
	     * 
	     * 1. `@PutMapping("/{id}")`: Esta é uma anotação que mapeia uma solicitação HTTP PUT para este método de controlador. O valor `"/{id}"` na 
	     *     anotação indica que o ID do cliente a ser editado será passado como parte do URL.
	     * 
	     * 2. `public Mono<ResponseEntity<Cliente>> editarCliente(@RequestBody Cliente cliente, @PathVariable String id)`: Este é o método que lida com a 
	     *     edição do cliente. Ele recebe dois parâmetros: `cliente`, que é o objeto de cliente que contém os dados a serem atualizados, e `id`, que é 
	     *     o ID do cliente a ser editado. O `@RequestBody` indica que o objeto `cliente` deve ser desserializado a partir do corpo da solicitação HTTP, 
	     *     que é esperado estar em formato JSON.
	     *     
	     * 3. `return clienteService.findById(id).flatMap(c -> { ... }):` O método começa por chamar o serviço `clienteService` para encontrar o cliente 
	     *     com o ID especificado (`id`) no banco de dados. O resultado é um `Mono` que emite o cliente encontrado ou um valor vazio se o cliente não 
	     *     for encontrado.
	     *     
	     * 4. `flatMap(c -> { ... }):` Em seguida, é usado o operador `flatMap` para processar o cliente encontrado (`c`) ou o valor vazio (caso o cliente 
	     *     não seja encontrado).
	     *     
	     * 5. `c.setNome(cliente.getNome());`, `c.setSobrenome(cliente.getSobrenome());`, `c.setIdade(cliente.getIdade());`, 
	     *    `c.setSalario(cliente.getSalario());`: No bloco dentro de `flatMap`, os atributos do cliente existente (`c`) são atualizados com os valores 
	     *    do objeto `cliente` recebido na solicitação. Isso atualiza os dados do cliente com base nas informações recebidas.
	     *    
	     * 6. `return clienteService.save(c);`: Após a atualização dos dados do cliente, o método chama o serviço `clienteService` para salvar o cliente 
	     *     atualizado no banco de dados. Isso retorna um `Mono` que emite o cliente atualizado.
	     *
	     * 7. `map(c -> ResponseEntity.created(URI.create("/api/clientes/".concat(c.getId())) ...`: O operador `map` é usado para criar uma resposta HTTP 
	     *    apropriada. Se o cliente foi atualizado com sucesso, ele cria uma resposta com um código de status "Criado" (código 201) e inclui o cliente 
	     *    atualizado no corpo da resposta. O cabeçalho `Location` é definido com o URL do cliente recém-atualizado.
	     *    
	     * 8. `defaultIfEmpty(ResponseEntity.notFound().build());`: Se o `Mono` resultante de `clienteService.findById(id)` não emitiu um cliente (ou seja, 
	     *     o cliente não foi encontrado), o método `defaultIfEmpty` é usado para fornecer uma resposta com um código de status "Não Encontrado" 
	     *     (código 404).
	     *     
	     * Em resumo, este método de controlador lida com a edição de um cliente com base em seu ID. Ele encontra o cliente existente, atualiza seus dados 
	     * com base nas informações recebidas, salva o cliente atualizado no banco de dados e fornece uma resposta apropriada, seja com um código de status 
	     * "Criado" se o cliente for atualizado com sucesso, ou com um código de status "Não Encontrado" se o cliente não for encontrado.
	     * */
	    @PutMapping("/{id}")
	    public Mono<ResponseEntity<Cliente>> editarCliente(@RequestBody Cliente cliente, @PathVariable String id){
	    	
	    	return clienteService.findById(id).flatMap(c -> {
	    		c.setNome(cliente.getNome());
	    		c.setSobrenome(cliente.getSobrenome());
	    		c.setIdade(cliente.getIdade());
	    		c.setSalario(cliente.getSalario());
	    		
	    		return clienteService.save(c);
	    		
	    	}).map(c -> ResponseEntity.created(URI.create("/api/clientes/".concat(c.getId())))
	    			.contentType(MediaType.APPLICATION_JSON_UTF8)
	    			.body(c)).defaultIfEmpty(ResponseEntity.notFound().build());
	    }
	    
	    
	    /**
	     * Criando cliente - http://localhost:8080/api/clientes/{id}
	     * Explicando passo a passo do código:
	     * 
	     * 1. `@DeleteMapping("/{id}")`: Esta é uma anotação que mapeia uma solicitação HTTP DELETE para este método de controlador. O valor `"/{id}"` na 
	     *     anotação indica que o ID do cliente a ser excluído será passado como parte do URL.
	     *     
	     * 2. `public Mono<ResponseEntity<Void>> deletarCliente(@PathVariable String id)`: Este é o método que lida com a exclusão do cliente. Ele recebe 
	     *     um parâmetro `id` que é o ID do cliente a ser excluído.
	     *     
	     * 3. `return clienteService.findById(id).flatMap(c -> { ... }):` O método começa por chamar o serviço `clienteService` para encontrar o cliente 
	     *     com o ID especificado (`id`) no banco de dados. O resultado é um `Mono` que emite o cliente encontrado ou um valor vazio se o cliente não 
	     *     for encontrado.
	     *     
	     * 4. `flatMap(c -> { ... }):` Em seguida, é usado o operador `flatMap` para processar o cliente encontrado (`c`) ou o valor vazio (caso o cliente 
	     *     não seja encontrado).
	     *     
	     * 5. `return clienteService.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));`: No bloco dentro de `flatMap`, o método 
	     *     `delete` do serviço `clienteService` é chamado para excluir o cliente. O operador `then` é usado para executar uma ação após a exclusão, 
	     *      que neste caso cria uma resposta com um código de status "No Content" (código 204) para indicar que a exclusão foi bem-sucedida.
	     *      
	     * 6. `defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));`: Se o `Mono` resultante de `clienteService.findById(id)` não emitiu um 
	     *     cliente (ou seja, o cliente não foi encontrado), o método `defaultIfEmpty` é usado para fornecer uma resposta com um código de status "Não 
	     *     Encontrado" (código 404).
	     *     
	     * Em resumo, este método de controlador lida com a exclusão de um cliente com base em seu ID. Ele encontra o cliente existente, exclui-o do banco 
	     * de dados, e fornece uma resposta apropriada, seja com um código de status "No Content" se a exclusão for bem-sucedida, ou com um código de 
	     * status "Não Encontrado" se o cliente não for encontrado. O método retorna um `Mono` que emite a resposta, tornando-o adequado para operações 
	     * assíncronas em um ambiente reativo.
	     */
	    @DeleteMapping("/{id}")
	    public Mono<ResponseEntity<Void>> deletarCliente(@PathVariable String id){
	    	
	    	return clienteService.findById(id).flatMap(c -> {
	    		return clienteService.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
	    	}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	    }
}
