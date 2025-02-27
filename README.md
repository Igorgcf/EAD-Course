# Plataforma EAD - Microservices Architecture

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

A plataforma EAD-Course proporciona uma solução completa para gerenciar cursos, módulos e lições em uma plataforma de ensino a distância (EAD). Com APIs RESTful bem estruturadas, ela permite criar, consultar, atualizar e deletar dados relacionados a cursos, módulos e lições, oferecendo flexibilidade para paginação e filtragem avançadas. Através de sua arquitetura de microserviço, a plataforma é ideal para organizar e estruturar o conteúdo educacional de maneira eficiente e escalável.

⚠️ Nota: Esta aplicação não utiliza camadas de segurança, como autenticação e autorização.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Database](#database)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)

## Installation

1. Clone the repository:

```bash
git clone https://github.com/IgorTecnologia/EAD-Course.git
```

2. Install dependencies with Maven

## Usage

1. Start the application with Maven
2. The API will be accessible at http://localhost:8082
3. You must have a PostgreSQL server active on the machine.
4. Attention!!! To carry out unit and integration tests of the application, the H2 database must be used.

## Collection Postman

Baixe esses arquivos e importe-os para o seu Postman para utilizar os métodos HTTP prontos juntamente com a variávei ​​de ambiente já configurada, para realizar as solicitações/respostas.

Download these files and import them into your Postman to use the ready-made HTTP methods along with the already configured environment variable, to perform the requests/responses.

[Download Collections](https://github.com/Igorgcf/EAD-Course/blob/docs-postman/EAD-COURSE.postman_collection.json)

[Download Enviroment Variables](https://github.com/Igorgcf/EAD-Course/blob/docs-postman/Environment.postman_environment.json)

## API Endpoints
The API provides the following endpoints:

**POST courses**
```markdown
POST /courses - Register Course.
```
```json
{
    "id": "d719fea0-a89b-4c6a-bd9d-170d25471523",
    "name": "Course name",
    "description": "Learning Java + Spring",
    "imageUrl": "www.image.com.br",
    "status": "CONCLUDED",
    "level": "BEGINNER",
    "instructorId": "2f1c4ce1-2e31-47a3-a2dc-cbd0ae0bd402",
    "creationDate": "2025-01-07T18:24:24Z",
    "lastUpdateDate": "2025-01-07T18:24:24Z",
    "modules": [
        {
            "id": "26d58787-ce30-499b-a0b9-d1fcce4ef1ac",
            "title": "Module name",
            "description": "POO Pillars in Java",
            "creationDate": "2025-01-07T18:24:24Z",
            "lastUpdateDate": "2025-01-07T18:24:24Z",
            "course": {
                "id": "d719fea0-a89b-4c6a-bd9d-170d25471523",
                "name": "Course name",
                "description": "Learning Java + Spring",
                "imageUrl": "www.image.com.br",
                "status": "CONCLUDED",
                "level": "BEGINNER",
                "instructorId": "2f1c4ce1-2e31-47a3-a2dc-cbd0ae0bd402",
                "creationDate": "2025-01-07T18:24:24Z",
                "lastUpdateDate": "2025-01-07T18:24:24Z",
                "modules": [],
                "links": []
            },
            "lessons": [],
            "links": []
        }
    ],
    "links": []
}
```
**GET COURSES**

Optional: Use pagination parameters and/or advanced filtering parameters (already contained in Collections.json provided above).

Opcional: Use parâmetros de paginação e/ou parâmetros de filtragem avançados (já contidos em Collections.json fornecido acima).
```markdown
GET /courses - Retrieve a pagination of all courses, along with hyperlinks to another endpoint.
```
```json
{
    "content": [
        {
            "id": "d719fea0-a89b-4c6a-bd9d-170d25471523",
            "name": "Course name",
            "description": "Learning Java + Spring",
            "imageUrl": "www.image.com.br",
            "status": "CONCLUDED",
            "level": "BEGINNER",
            "instructorId": "2f1c4ce1-2e31-47a3-a2dc-cbd0ae0bd402",
            "creationDate": "2025-01-07T18:24:24Z",
            "lastUpdateDate": "2025-01-07T18:24:24Z",
            "modules": [
                {
                    "id": "26d58787-ce30-499b-a0b9-d1fcce4ef1ac",
                    "title": "Module name",
                    "description": "POO Pillars in Java",
                    "creationDate": "2025-01-07T18:24:24Z",
                    "lastUpdateDate": "2025-01-07T18:24:24Z",
                    "course": {
                        "id": "d719fea0-a89b-4c6a-bd9d-170d25471523",
                        "name": "Course name",
                        "description": "Learning Java + Spring",
                        "imageUrl": "www.image.com.br",
                        "status": "CONCLUDED",
                        "level": "BEGINNER",
                        "instructorId": "2f1c4ce1-2e31-47a3-a2dc-cbd0ae0bd402",
                        "creationDate": "2025-01-07T18:24:24Z",
                        "lastUpdateDate": "2025-01-07T18:24:24Z",
                        "modules": [],
                        "links": []
                    },
                    "lessons": [],
                    "links": []
                }
            ],
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8082/courses/d719fea0-a89b-4c6a-bd9d-170d25471523"
                }
            ]
        }
    ]
```
**GET COURSES/ID**
```markdown
GET /courses/id - Retrieve a single course by id.
```

```json
{
    "id": "d719fea0-a89b-4c6a-bd9d-170d25471523",
    "name": "Course name",
    "description": "Learning Java + Spring",
    "imageUrl": "www.image.com.br",
    "status": "CONCLUDED",
    "level": "BEGINNER",
    "instructorId": "2f1c4ce1-2e31-47a3-a2dc-cbd0ae0bd402",
    "creationDate": "2025-01-07T18:24:24Z",
    "lastUpdateDate": "2025-01-07T18:24:24Z",
    "modules": [
        {
            "id": "26d58787-ce30-499b-a0b9-d1fcce4ef1ac",
            "title": "Module name",
            "description": "POO Pillars in Java",
            "creationDate": "2025-01-07T18:24:24Z",
            "lastUpdateDate": "2025-01-07T18:24:24Z",
            "course": {
                "id": "d719fea0-a89b-4c6a-bd9d-170d25471523",
                "name": "Course name",
                "description": "Learning Java + Spring",
                "imageUrl": "www.image.com.br",
                "status": "CONCLUDED",
                "level": "BEGINNER",
                "instructorId": "2f1c4ce1-2e31-47a3-a2dc-cbd0ae0bd402",
                "creationDate": "2025-01-07T18:24:24Z",
                "lastUpdateDate": "2025-01-07T18:24:24Z",
                "modules": [],
                "links": []
            },
            "lessons": [],
            "links": []
        }
    ],
    "links": []
}
```
**PUT COURSES**
```markdown
PUT /courses/id - Update a course by id.
```
```json
{
    "description" : "Learning Java + Spring 1.0",
    "imageUrl" : "www.image.com",
    "instructorId" : "2f1c4ce1-2e31-47a3-a2dc-cbd0ae0bd402",
    "status" : "CONCLUDED",
    "level" : "BEGINNER",
    "modules" : [
        {
            "title" : "Module 1.0",
            "description" : "Pílares de POO em Java 1.0"
        }
    ]
    
}
```
**PUT COURSES NAME**
```markdown
PUT /courses/{id}/name - Update a course name by id.
```
```json
{
  "name" : "course 2.0"

  Return HTTP status: 200 OK
  Body: "Name updated successfully." 
}
```
**DELETE USER**
```markdown
DELETE /courses/id - Delete a course by id.

Return HTTP status: 200 OK
Body: "Course deleted successfully."
```

## Database
This application uses [PostgreSQL](https://www.postgresql.org/docs/) as its database.

There are settings for PostgreSQL e H2 databases, you can use them by changing the application.yaml file.

Attention!!! To carry out unit and integration tests of the application, the H2 database must be used.

## Technologies Used

- JDK 22
- Java version 17
- Spring Boot
- Maven
- PostgreSQL
- IntelliJ IDEA
- Postman

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request to the repository.

When contributing to this project, please follow the existing code style, [commit conventions](https://www.conventionalcommits.org/en/v1.0.0/), and submit your changes in a separate branch.

Contribuições são bem-vindas! Se você encontrar algum problema ou tiver sugestões de melhorias, abra um problema ou envie uma solicitação pull ao repositório.

Ao contribuir para este projeto, siga o estilo de código existente, [convenções de commit](https://medium.com/linkapi-solutions/conventional-commits-pattern-3778d1a1e657), e envie suas alterações em uma branch separada.

![Spring](https://www.alvantia.com/wp-content/uploads/2018/05/imagen-spring-boot-ENGLISH.jpg)
