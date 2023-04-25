package de.nordlb.restapidemo;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @PostMapping("/register")
  private ResponseEntity<User> register(@RequestBody User newUser){

    //API-Secret generieren
    newUser.setSecret(UUID.randomUUID().toString());

    var savedUser = userRepository.save(newUser);

    return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
  }

  @GetMapping("/user")
  private ResponseEntity<User> getUser(@RequestParam(value = "id") UUID id){
    Optional<User> user = userRepository.findById(id);
    if(user.isPresent()){
      return new ResponseEntity<User>(user.get(), HttpStatus.OK);
    }

    return new ResponseEntity("Kein User mit der ID "+ id , HttpStatus.NOT_FOUND);
  }

  @GetMapping("/validate")
  private ResponseEntity<String> validate(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password){
    Optional<User> validUser = userRepository.findByEmailAndPassword(email, password);
    if(validUser.isPresent()){
      return new ResponseEntity<String>("API-Secret: "+validUser.get().getSecret(), HttpStatus.OK);
    }
    return new ResponseEntity("Falsche Anmeldedaten / User nicht vorhanden", HttpStatus.NOT_FOUND);
  }

}
