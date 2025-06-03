package br.com.oscar.api.controllers;

import br.com.oscar.api.models.IndicadosAoOscar;
import br.com.oscar.api.repositories.IndicadosAoOscarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Controladora Rest
// Uma controladora REST é uma classe que recebe requisições HTTP e retorna respostas HTTP
// Ela é responsável por receber as requisições, processar as informações e retornar uma resposta
// Ela é responsável por fazer a comunicação entre o cliente e o servidor

// Permite que o front-end acesse a API sem problemas de CORS
@CrossOrigin
@RestController // Diz ao SPRING que é uma controladora REST
@RequestMapping("/api/indicacoes") //URL base dessa controladora
public class IndicadosAoOscarController {

     /*
        CRUD          ->  Create, Read/Retrieve, Update and Delete
        PostMapping   ->  Enviar/criar dados para o banco
        GetMapping    ->  Trazer dados do banco
        RequestMapping->  Trazer dados do banco
        PutMapping    ->  Atualizar dados do banco
        DeleteMapping ->  Apaga dados do banco
     */
    // AutoWired significa que o SPRING vai injetar a dependência
    // Injetar dependência significa que o SPRING vai criar uma instância do objeto
    // e vai passar para a classe que está pedindo

    @Autowired
    IndicadosAoOscarRepository dbConnection;

    @GetMapping
    //Método que retorna todos os registros do banco
    public List<IndicadosAoOscar> findAllRecords(){
        return dbConnection.findAll();
    }

    @GetMapping(value = "/pagina", produces = "application/json") // Método que retorna todos os registros do banco de forma paginada
    public List<IndicadosAoOscar> findAllRecordsPageable(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return dbConnection.findAll().stream()
                .skip(page * size)
                .limit(size)
                .toList();
    }
    // exemplo de URL: http://localhost:8080/api/indicacoes/pagina?page=0&size=10

    @PostMapping(value = "/inserir", produces = "application/json") // Criar um novo registro
    public IndicadosAoOscar create(@RequestBody IndicadosAoOscar exemplo){
        dbConnection.save(exemplo);
        return exemplo;
    }

    // Encontrar um único elemento por id
    @RequestMapping(value = "/buscar/id/{id}", method = RequestMethod.GET)
    public Optional<IndicadosAoOscar> searchById(@PathVariable int id) {
        return dbConnection.findById(id);
    }

    // Encontrar elementos por nome do indicado
    @RequestMapping(value = "/buscar/nome/{name}", method = RequestMethod.GET)
    public List<IndicadosAoOscar> searchByName(@PathVariable String name) {
        return dbConnection.findBynomeIndicadoContaining(name);
    }

    @DeleteMapping(value = "/deletar/{id}", produces = "application/json") // Deletar um registro
    public String delete(@PathVariable int id) {
        IndicadosAoOscar indicado = dbConnection.findIndicadoByidRegistro(id).get();
        dbConnection.delete(indicado);
        // return "Registro deletado com sucesso!";
        return "{deleted:" + id + "}";
    }

    @PutMapping(value = "/atualizar/{id}", produces = "application/json") // Atualizar um registro
    public IndicadosAoOscar update(@PathVariable int id, @RequestBody IndicadosAoOscar exemplo) {
        IndicadosAoOscar indicado = dbConnection.findIndicadoByidRegistro(id).get();
        indicado.setAnoFilmagem(exemplo.getAnoFilmagem());
        indicado.setAnoCerimonia(exemplo.getAnoCerimonia());
        indicado.setEdicaoCerimonia(exemplo.getEdicaoCerimonia());
        indicado.setCategoria(exemplo.getCategoria());
        indicado.setNomeFilme(exemplo.getNomeFilme());
        indicado.setNomeIndicado(exemplo.getNomeIndicado());
        indicado.setVencedor(exemplo.getVencedor());
        dbConnection.save(indicado);
        return indicado;
    }
}
