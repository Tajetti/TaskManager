# Todo App

Uma aplicação JavaFX moderna para gerenciamento de tarefas (TODO list), desenvolvida com Java 21 e banco de dados H2.

## Funcionalidades

- ✅ Criar novas tarefas
- ✏️ Editar tarefas existentes
- ✅ Marcar tarefas como concluídas
- 🗑️ Deletar tarefas
- Interface gráfica intuitiva com JavaFX

## Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **JavaFX 21** - Framework para interface gráfica
- **H2 Database** - Banco de dados em memória
- **Maven** - Gerenciamento de dependências e build

## Como Executar

### Pré-requisitos

- JDK 21 instalado
- Maven instalado

### Passos

1. Clone o repositório
2. Navegue até o diretório do projeto
3. Execute o comando:

```bash
mvn javafx:run
```

A aplicação será iniciada automaticamente e a interface gráfica será aberta.

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── br/com/tajetti/
│   │       ├── Controller/
│   │       │   └── TarefaController.java
│   │       ├── Model/
│   │       │   ├── DAO/
│   │       │   │   └── TarefaDAO.java
│   │       │   ├── Database/
│   │       │   │   └── ConnectionFactory.java
│   │       │   └── Entity/
│   │       │       ├── StatusTarefa.java
│   │       │       └── Tarefa.java
│   │       ├── Service/
│   │       │   └── TarefaService.java
│   │       ├── View/
│   │       │   └── MainView.java
│   │       └── Main.java
│   └── resources/
└── test/
    └── java/
```

## Arquitetura

O projeto segue o padrão MVC (Model-View-Controller):

- **Model**: Entidades e acesso a dados
- **View**: Interface gráfica com JavaFX
- **Controller**: Lógica de negócio e comunicação entre Model e View

## Banco de Dados

A aplicação utiliza H2 como banco de dados, que é criado automaticamente na primeira execução. Os dados são persistidos em arquivo local.

## Desenvolvimento

Para contribuir ou modificar:

1. Faça suas alterações
2. Execute `mvn clean compile` para verificar se não há erros
3. Teste a aplicação com `mvn javafx:run`

## Licença

Este projeto é open source e está sob a licença MIT.