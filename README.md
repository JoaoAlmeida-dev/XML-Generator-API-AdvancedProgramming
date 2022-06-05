# XMLin

# Wiki

### You can view the initial instructions that inspired this project [here](https://andre-santos-pt.github.io/projetoxml/)

# Project description

Many of you are familiar with **XML** and it may look something like this:

```xml
<?xml version="1.0"encoding="UTF-8"standalone="no"?>
<Library subTitle="2022" title="Livraria de Lisboa">
    <books>
        <Livro Writer="Jeronimo Stilton" pages="1000">Jeronimo em Belém</Livro>
        <Livro Writer="Fernando Pessoa" pages="200">Fernando no Chiado</Livro>
    </books>
</Library>
```

This project aims to develop an intuitive way to create your own custom xml without the worries that come with specific
syntax.

---

## The project structure

The project is divided in 2 main packages

| Package    |                                                          Description |
|------------|---------------------------------------------------------------------:|
| Core       | The main **problem** representation and ways to **interact** with it |
| View       |             The way to view and **visually** interact with the model |

Now we will go more in depth on what each package has to offer

---

### Core

Inside the core we have the Model and the Utilities sub packages

#### Model

The model uses some clever abstraction to represent an xml document programmatically.

We developed a set of classes that each represent and aspect of xml:

* XmlHeader
* XMLContainer (abstract)
    * XMLDocument
    * XMLEntity
    * XMLAtribute
* XMLAnnotations

#####   

##### Annotations

#### Utilities

---

### View

---

# Developing plugins

## Classes you should implement/extend

*
*
*
*