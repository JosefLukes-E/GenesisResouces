package cz.engeto.GenesisResouces.controller;

import cz.engeto.GenesisResouces.dto.User;
import cz.engeto.GenesisResouces.dto.UserDto;
import cz.engeto.GenesisResouces.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("create")
    public void create() {
        userService.createAndSaveDeafaultUsers();
    }

//    @GetMapping("/home")
//    public String homepage(){
//        return "index";
//    }

    @GetMapping("users")
    public Object getAllUsers(@RequestParam(required = false) boolean detail) {
        List<User> users = userService.getAllUsers();
        if (!users.isEmpty()) {
            if (detail) {
                return users;
            } else {
                List<UserDto> usersDto = new ArrayList<>();
                for (User user : userService.getAllUsers()) {
                    usersDto.add(new UserDto(user.getId(), user.getName(), user.getSurname()));
                }
                return usersDto;
            }
        } else {
            return new ResponseEntity("V databázi nejsou žádní uživatelé", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("users")
    public ResponseEntity addNewUser(@RequestBody User user) throws Exception {
        user = new User(user.getName(), user.getSurname(), user.getPersonId());

        switch (userService.saveUser(user)) {
            case 1 -> {
                return new ResponseEntity("Uživatel založen", HttpStatus.OK);
            }
            case 55 -> {
                return new ResponseEntity("Personal ID je nevalidní", HttpStatus.BAD_REQUEST);
            }
            case 99 -> {
                return new ResponseEntity("Personal ID již má jíný uživatel", HttpStatus.BAD_REQUEST);
            }
            case 0 -> {
                return new ResponseEntity("Chybné zadání", HttpStatus.BAD_REQUEST);
            }
            default -> throw new IllegalStateException("Neočekávaná hodnota " + user.getId());
        }
    }

    @GetMapping("users/{id}")
    public Object getOneUser(@PathVariable Long id, @RequestParam(required = false) boolean detail) {
        User user = userService.getOneUser(id);
        if (user != null) {
            if (detail) {
                return user;
            } else {
                return new UserDto(user.getId(), user.getName(), user.getSurname());
            }
        } else {
            return new ResponseEntity("Uživatel id " + id + " není v databázi", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        User userToDelete = userService.getOneUser(id);
        if (userToDelete != null) {
            userService.deleteUser(id);
            return new ResponseEntity("Uživatel id " + id + " byl smazán", HttpStatus.OK);
        } else {
            return new ResponseEntity("Uživatel id " + id + " nebyl nalezen", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("users")
    public ResponseEntity updateUser(@RequestBody User user) {
        User userToUpdate = userService.getOneUser(user.getId());
        if (userToUpdate != null) {
            userToUpdate.setName(user.getName());
            userToUpdate.setSurname(user.getSurname());
            userService.updateUser(user);
            return new ResponseEntity("Uživatel id " + user.getId() + " byl upraven", HttpStatus.OK);
        } else {
            return new ResponseEntity("Uživatel id " + user.getId() + " nebyl nalezen", HttpStatus.BAD_REQUEST);
        }

    }
}
