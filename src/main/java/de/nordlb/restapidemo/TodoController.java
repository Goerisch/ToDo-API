package de.nordlb.restapidemo;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
@RestController
public class TodoController {

  @Autowired
  private TodoRepository todoRepository;

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/todo")
  public ResponseEntity<Todo> getTodo(@RequestParam(value = "id") int id){
    //hole aufgabe aus DB
    Optional<Todo> todoInDb = todoRepository.findById(id);
    if(todoInDb.isPresent()) {
      return new ResponseEntity<Todo>(todoInDb.get(), HttpStatus.OK);
    }
    return new ResponseEntity("Kein Todo gefunden mit der ID "+id,HttpStatus.NOT_FOUND);

  }

  @GetMapping("/todo/all")
    public ResponseEntity<Iterable<Todo>> getAllTodos(@RequestHeader("api-secret") String secret){
      Optional<User> userBySecret = userRepository.findBySecret(secret);
      if(userBySecret.isPresent()){
        Iterable<Todo> allTodosInDb = todoRepository.findAllByUserId(userBySecret.get().getUserId());
        return new ResponseEntity<Iterable<Todo>>(allTodosInDb, HttpStatus.OK);
      }
      return new ResponseEntity("API-Secret konnte nicht zugeordnet werden", HttpStatus.BAD_REQUEST);


  }

  @PostMapping("/todo")
  public ResponseEntity<Todo> createTodo(@RequestBody Todo newTodo){
    //speichere Aufgabe in DB
    todoRepository.save(newTodo);
    return new ResponseEntity<Todo>(newTodo,HttpStatus.CREATED);
  }

  @PutMapping("/todo")
  public ResponseEntity<Todo> editTodo(@RequestBody Todo editedTodo) {
    Optional<Todo> todoInDb = todoRepository.findById(editedTodo.getId());

    if (todoInDb.isPresent()) {
      //editiere Aufgabe aus DB
      todoRepository.save(editedTodo);
      return new ResponseEntity<Todo>(editedTodo, HttpStatus.OK);
    }
    return new ResponseEntity("Todo mit id "+editedTodo.getId()+" konnte nicht gefunden werden.", HttpStatus.NOT_FOUND);
  }

  @PatchMapping("/todo/setDone")
  public ResponseEntity<Todo> setDone(@RequestParam(value = "isDone") boolean isDone, @RequestParam(value = "id") int id){
    Optional<Todo> todoInDb = todoRepository.findById(id);

    if (todoInDb.isPresent()) {
      //aktualisiere isDone
      todoInDb.get().setIsDone(isDone);
      Todo savedTodo = todoRepository.save(todoInDb.get());
      return new ResponseEntity<Todo> (savedTodo, HttpStatus.OK);
    }
    return new ResponseEntity("Todo mit id "+id+" konnte nicht gefunden werden.", HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/todo")
  public ResponseEntity delete(@RequestParam(value = "id") int id){
    //prüfung, ob Aufgabe existiert
    Optional<Todo> todoInDb = todoRepository.findById(id);
    if(todoInDb.isPresent()){
      //lösche Aufgabe aus DB
      todoRepository.deleteById(id);
      return new ResponseEntity("Todo mit ID "+ id + " wurde gelöscht", HttpStatus.OK);
    }
    return new ResponseEntity("Kein Todo mit ID "+id+ " gefunden.", HttpStatus.NOT_FOUND);

  }

}
