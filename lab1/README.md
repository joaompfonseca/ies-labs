# IES - Lab 01

## 1.2

Criar projeto Maven na linha de comandos:

```
$ mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
```

**Nota:** "-D" serve para passar propriedades ao Maven na linha de comandos

---
**Archetype**
Template com a estrutura de um projeto Maven, que permite criar novos projetos rapidamente.

**groupId**
Identifica univocamente o projeto de entre todos os outros. Deve seguir a convenção reverse domain name do Java, utilizando um URL que controlamos. no entanto isto não é imposto pelo Maven.
Podemos criar subgrupos se o projeto contiver vários módulos, devendo ser adicionados após o groupId do pai.

**artifactId**
Nome do jar sem a versão. Deve ser composto por letras minúsculas ou símbolos "não estranhos".
Se for um jar de terceiros, o nome deve ser igual.

**version**
Utilizada no caso de distribuirmos o jar. Deve ser composta por números e pontos.
Se for um jar de terceiros, a versão deve ser igual.

Há três tipos de repositórios no Maven:

    **local**: presente localmente na diretoria **~/.m2/repository**.
    **central**: repositório comunitário do Maven, contém as bibliotecas mais comuns.
    **remote**: repositório dos devenvolvedores, contém bibliotecas e pacotes do projeto.
---

### f)

Conteúdo principal no **pom.xml**:

	- groupId, artifactId, version
	- dependencies (opcional)
		- dependency
			- groupId, artifact, version (da dependência incluída)
			- scope (especifica o contexto em que deve ser utilizada a dependência - pode ser 
				**compile** (por omissão), 
				**provided** (em tempo de execução pelo JDK ou container), 
				**runtime** (em tempo de execução), 
				**test** (para testar), 
				**system** (similar à provided, não utilizar pois está depreciada) e 
				**pom** (apenas disponível na dependência type pom))

Estrutura de ficheiros:
	
	myweatherradar/
		pom.xml
		src/
			main/
				java/
					io/
						github/
							joaompfonseca/
								weatherradar/
									App.java
			test/
				java/
					io/
						github/
							joaompfonseca/
								weatherradar/
									AppTest.java

### g)

**Development team**

	- developers
		- developer
			- id (nickname)
			- name (nome completo)
			- email
			- url
			- organization
			- organizationUrl
			- roles
				- role (responsabilidade no projeto)
			- timezone
			
**Character encoding**

	- properties
		- project.build.sourceEncoding (UTF-8)

**Compiler version**

	- properties
		- maven.compiler.source (versão do compilador)
		- maven.compiler.target (versão do jar - pode ser igual ou inferior à do compilador)

### h)

A versão mais recente de ambas as dependências é 2.9.0.
Link para o artefacto solicitado: https://search.maven.org/artifact/com.squareup.retrofit2/retrofit/2.7.0/jar

### i)

As dependências diretas declaradas no POM podem necessitar de outras dependências, essas são obtidas automaticamente.

### j)

Obter as dependências, compilar o projeto e criar o jar:
`mvn package`

Executar o projeto:
`mvn exec:java -Dexec.mainClass="PACKAGE.CLASS"`

Limpar ficheiros gerados pelo build:
`mvn clean`

## 1.3

### b)

O ficheiro **.gitignore** permitir indicar ficheiros / pastas que não devam ser incluídos no repositório, por exemplo, versões compiladas do código.

### c)

Como criar repositório local e sincronizá-lo na cloud:

`cd project_root`

`git init`: Inicializa um repositório local

`git remote add origin <REMOTE_URL>`: Associa o repositório local ao repositório remoto

`git add .`: Adiciona todas as alterações para serem commited

`git commit -m "Initial project setup for exercise 1_3"`: Cria uma snapshot local

`git push -u origin master`: Carrega a commit local para o repositório remoto

### d)

Como clonar repositório remoto:
`git clone <REMOTE_URL>`

### e)

Log4j2 - hierarquia de "level" no ficheiro log4j2.xml:

    **ERROR > INFO > DEBUG**

Se especificarmos um de nível superior (ex **ERROR**), os de nível inferior (ex **INFO** e **DEBUG**) não são registados no log.

	- Configuration
		- Loggers
			- Root (level="")

### f)

Histórico do repositório:
```
e5111a3 Initial project setup for exercise 1_3
bdfd829 (HEAD -> master, origin/master, origin/HEAD) Added logger
```

## 1.4

### b)

Construir imagem do container:
`docker build -t getting-started .`

Executar aplicação:
`docker run -dp 3000:3000 getting-started`
    -d Executa no background (detached)
	-p Mapeia porto do host com o do container
	
### d)

Conectar a instância do PostgreSQL:
`psql -h localhost -U postgres -d postgres`

Executar comandos SQL com o Docker, no terminal:
`docker exec -it pg-docker psql -U postgres -c "CREATE DATABASE testdb;"`

Executar comandos SQL com o Docker, no terminal e com um ficheiro:
`docker exec -it pg-docker psql -U postgres -f /opt/scripts/test_script.sql`

### e)

Inicia os serviços especificados no docker-compose.yml:
`docker compose up -d`
    -d Executa no background (detached)

Encerra os serviços especificados no docker-compose.yml (dentro do projeto):
`docker compose down` ou `CTRL+C`
    --volumes Remove os dados utilizados no volume

Encerra os serviços iniciados em background:
`docker compose stop`

Visualizar containers em execução:
`docker compose ps`

Ficheiro **docker-compose.yml**:
```
version: "3.9"
services:
  web:
    build: .
	ports:
	  - 8000:8000           # Mapeamento do porto 8000 do host para o porto 8000 do container
	volumes:
	  - .:/code             # Mapeamento da diretoria . do host para a diretoria /code do container. Permite modificar código sem reconstruir a imagem, pois encontra-se no sistema de ficheiros do host (e não dentro do container).
    environment:
      FLASK_DEBUG: True     # Especifica que o comando "flask run" deve executar em modo de desenvolvimento. Atualiza o código sempre que este muda.
  redis:
    image: "redis:alpine"
```

## 1.5

Gera o **.jar** do projeto IpmaClientApi e adiciona-lo ao repositório local em **~/.ms/repository**:
`mvn install`
 
Para incluir o projeto IpmaClientApi no projeto WeatherForecastByCity, basta adicioná-lo como dependência no **pom.yml**.

O Maven irá procura sempre pelo **.jar** no repositório local, só depois procura no repositório central do Maven (online).

## Review Questions

### a)
O default lifecycle do Maven é composto pelas seguintes grandes fases/passos:

    **validate**: verifica se a estrutura do projeto está correta. No caso das dependências, verifica se estas foram transferidas e estão presentes no repositório local.
    **compile**: compila o código fonte, e guarda os ficheiros **.class** na diretoria **/target**.
	**test**: executa os testes unitários do projeto (**white-box** - necessitam de acesso ao código fonte).
	**package**: junta num pacote todo o código compilado num único ficheiro distribuível **.jar**.
	**integration test**: executa os testes de integração do projeto (**black-box** - não tem acesso ao código fonte, são mais difíceis de desenvolver).
	**verify**: verifica a validade do projeto e se este cumpre com os padrões de qualidade.
	**install**: instala o código empacotado (**.jar**) no repositório local do Maven.
	**deploy**: copia o código empacotado (**.jar**) para o repositório remoto, para ser partilhado por outros desenvolvedores.

Ao executar o passo **n**, todos os **n-1** passos anteriores são executados sequencialmente.

### b)
O Maven é uma ferramenta cujo principal objetivo é a gestão do projeto, com foco na estrutura, princípios, lifecycle e dependências.
O produto final gerado pelo Maven é um ficheiro **.jar**, um programa em Java executável. Portanto, o seu foco não é a execução, mas sim a criação de um executável.
No entanto, podemos utilizar o seu plugin **exec** para executar o projeto Java.

### c)
`git clone <REMOTE_URL>`: Clona o repositório em REMOTE_URL na máquina local.

`git add .`: Adiciona todas as alterações no Working Directory para a Staging Area.

`git commit -m "MESSAGE"`: Adiciona todas as alterações na Staging Area para o repositório local.

`git push`: Envia para o repositório remoto as alterações feitas no repositório local.

### d)
Boas práticas para a escrita de boas mensagens de commit (de uma só linha):

    - Manter a mensagem curta, não mais de 50 caracteres.
	- Começar a mensagem com letra maiúscula.
	- Não terminar as mensagens com um ponto final, pois desperdiça 1 dos 50 caracteres recomendados.
	- Escrever as mensagens no modo imperativo ao invés do modo indicativo, como se estivessemos a dar uma ordem:
	    ex.: "Fix a bug with X" em vez de "Fixed a bug with X".
		Deve completar a frase: "Se aplicada, esta commit deve..." ("If applied, this commit will...")

### e)
No caso de uma base de dados, esta deve ser persistente (estar em memória).
Em ambiente de desenvolvimento podemos prescindir dessa persistência, mas em produção é importante garantir que os dados persistam, mesmo em caso de falha do container.
Se não configurarmos nada, após o ciclo de vida do container, todos os dados são eliminados.
Para garantir a persistência mesmo depois do container falhar/desligar, devemos mapear uma diretoria local do host para uma diretoria apropriada dentro do container.
Normalmente, criamos um volume com o docker e associamos ao container no qual queremos persistência de dados.
