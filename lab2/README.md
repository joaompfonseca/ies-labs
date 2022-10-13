# IES - Lab 02

## 2.1

**Servlet:** classe que é uma interface genérica que serve de fundação ao ambiente Java Enterprise. Recebe pedidos do cliente, processa-os e reponde-lhes. Deve ser lançado num container multithreaded para ser usado.

`Request -> Server -> Target Servlet`

### a)

Executar servidor aplicacional:
`./startup.sh` (em **<path>/bin**)

Verificar se servidor está online:
  - `curl -I 127.0.0.1:8080`
  - http://localhost:8080
  - `tail logs/catalina.out` (em **<path>**)
  
### b)

O Tomcat inclui um ambiente de gestão (Manager App), que permite dar deploy/un-deploy aplicações. Para utilizar, é preciso registar pelo menos um role.
http://localhost:8080/manager

Adicionar role em `conf/tomcat-users.xml`:
```
<role rolename="manager-gui"/>
<role rolename="manager-script"/>
<user username="admin" password="secret" roles="manager-gui,manager-script"/>
```

### c)

Archetype utilizado para a aplicação web:
`org.codehaus.mojo.archetypes:webapp-javaee7:v1.1` 

### d)

Executando `mvn install`, é gerado um fichero **.war** (Web Archive) em **<project>/target**.

### e)

Utilizando a Manager App, na secção Deploy/WAR file to deploy:
  - Escolher o ficheiro **.war** em **<project>/target**;
  - OU, mover o ficheiro **.war** para **<tomcat>/webapps**.
  
### i)

## 2.3

**Spring Boot:** plataforma de desenvolvimento rápido de apps, construída sobre a Spring Framework. Prioritiza a convenção à configuração, permitindo criar apps para produção com um mínimo de esforço.

### a)

Criação de um projeto com **Spring Boot**:
 1. Utilizar o **Spring Initializr** para criar o projeto;
 2. Adicionar as dependências necessárias (**Spring Web**);
 3. As templates do **Spring Initializr** irão incluir uma coleção de dependências transitivas relevantes. Poderá originar conflitos se adicionarmos as mesmas dependências com versões diferentes no POM;
 4. Existe um wrapper script para construir e executar a aplicação Maven:
    `./mvnw spring-boot:run`
 5. Página acessível em http://localhost:8080/.

### b)

@SpringBootApplication - Indica a classe main do projeto em Spring Boot.

@Controller - Lida com os HTTP requests.
@GetMapping("/page") - Assegura que os HTTP GET requests são mapeados para o método que se encontra abaixo.
@RequestParam(name="name", required=false,  defaultValue="World") String name - Indica que o parâmetro "name" do URL deve ter o seu valor mapeada no argumento name do método em que se encontra.
O método greeting() retorna a string "greeting", que corresponde à template "greeting.html".

th:text - Indica ao Thymeleaf que deve efetuar renderização server-side do HTML.

**Spring Boot Devtools:** permite alterar o código e atualizar a página sem reiniciar a aplicação.

Ficheiro `/src/main/resources/application.properties`:
    `server.port=PORT` - altera porto da aplicação.

### c)

Criação de um **REST endpoint** com Spring Boot:
 1. Criar uma **Resource Representation Class** - Serve para modelar a representação da resposta (em JSON). Deve incluir os atributos, construtores e getters;
 2. Criar um **Resource Controller** (@RestController) - Lida com os HTTP GET requests e devolver uma instancia da classe criada anteriormente;

**MVC Controller** vs **RESTful Web Service Controller**:
  - Diferem na forma de criação da resposta do corpo do HTTP:
      - MVC: Renderização server-side com views dos dados para o HTML;
      - RESTful: popula e returna um objeto cujos dados são transformados diretamente para JSON.

## 2.4

## Review Questions

### a) 
Um **web container / servlet container** é um componente do servidor web e implementação da especificação do JakartaEE, que tem como principal objetivo hospedar servlets.
Responsável por gerir o ciclo de vida das servlets, mapear um URL para uma determinada servlet e controlar o acesso dos utilizadores a recursos segundo as suas permissões.
O Apache Tomcat é uma implementação de um servlet container.

### b)
Em suma, a abordagem Model-View-Controller no Spring Boot centra-se numa servlet dispatcher que distribui os pedidos para as servlets handler.
O handler default baseia-se nas anotações **@Controller/@RestController** e **@GetMapping**.
O controller é responsável por preparar o modelo com os dados e selecionar uma view / responder ao request.

Abordagem Model-View-Controller do Spring Boot (segundo os exercícios do guião):
  - **Model**:
      - **WebApp**: Atributo model no mapping (2.3b); 
      - **REST endpoint**: Estrutura da classe representativa do recurso, que deve possuir os atributos, construtores e getters (2.3c e 2.4);
  - **View**:       
      - **WebApp**: Template de página HTML utilizando a depêndência Thymeleaf para indicar onde inserir os dados (2.3b);
      - **REST endpoint**: Classe representativa do recurso que é modelada para JSON (2.3c e 2.4);
  - **Controller**:
      - **WebApp**: Utiliza a anotação **@Controller**, os seus mappings inserem adicionam os atributos ao model e retornam uma String correspondente ao nome da respetiva página HTML (2.3c);
      - **REST endpoint**: Utiliza a anotação **@RestController**, os seus mappings criam e retornam uma classe representativa do recurso a devolver, que neste guião foi transformada em JSON (2.3c e 2.4);
      
### c)
As dependências "starter" têm como objetivo:
  - Importar **transitivamente** várias dependências para o projeto;
  - Aumentar a produtivadade ao reduzir o tempo de configuração das dependências do projeto;
  - Reduzir o número de dependências explícitas no **pom.xml**;
  - Garantir a compatibilidade entre as várias dependências importadas;
  - Evitar que o desenvolvedor tenha de se lembrar do nome e versão das dependências.

### d)
A anotação @SpringBootApplication inclui implicitamente as seguintes anotações:
  - **@Configuration**: marca a classe como sendo fonte de definições de "beans" (objetos geridos pelo container IOC do String) da aplicação;
  - **@EnableAutoConfiguration**: indica ao Spring Boot para adicionar beans conforme as definições do classpath, outros beans e outras propriedades;
  - **@ComponentScan**: indica ao Spring que deve procurar por outros componentes, configurações e serviços na package do groupId (ex.: `com/example`).

### e)

  - **Utilizar JSON para enviar e receber dados**: o JSON tornou-se no formato padrão, suportado pela maioria das linguagens de programação, que fornecem métodos para ler e manipular JSON;
  - **Utilizar nomes em vez de verbos para os endpoints**: o nome do endpoint deve transparecer a sua função, sendo os métodos (verbos) HTTP (GET, POST, etc.) a especificar a ação no endpoint;
  - **Nomear coleções com nomes no plural**: indica ao utilizador que existem mais recursos desse tipo, e não apenas um;
  - **Nesting nos endpoints para mostrar relacionamentos**: permite entender melhor as relações entre diferentes endpoints (`/post/author` - se um post puder ser escrito por mais do que um autor), e para isso não deve ter muitos níveis de profundidade;
  - **Fornecer documentação da API**: fornece informação acerca dos endpoints disponíveis, além de ajudar os utilizadores a aprender como usar a API corretamente.
  
