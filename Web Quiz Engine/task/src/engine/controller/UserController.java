package engine.controller;

import engine.exception.UserNotFoundException;
import engine.model.User;
import engine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class UserController {
    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserController() {

    }

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @PostMapping(path = "/api/register")
    public User register(@RequestBody @NotNull @Valid User user) {

       // Optional<User> userfound = repository.findByEmail(user.getEmail());
        User userfound = repository.findByEmail(user.getEmail());
        if(userfound !=null){
           throw new UserNotFoundException("Email in use. Try different one");
        }
       user.setPassword(passwordEncoder.encode(user.getPassword())); //encode the password
        user.setRoles("USER");
        System.out.println(user.getPassword()+ " User is registering " + user);
        return repository.save(user);
    }
}
