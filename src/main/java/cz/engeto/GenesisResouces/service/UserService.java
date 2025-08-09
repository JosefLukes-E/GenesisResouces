package cz.engeto.GenesisResouces.service;

import cz.engeto.GenesisResouces.dto.User;
import cz.engeto.GenesisResouces.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class UserService {
    private final String authkeysPath = "src/main/resources/dataPersonId.txt";

    @Autowired
    UserRepository userRepository;

    private final List<User> users = new ArrayList<>();

    private void createDefaultUsers() {
        users.add(new User("Pepa", "Vyskoč", "mY6sT1jA3cLz"));
        users.add(new User("Josef", "Švejk", "nS7tJ0qR5wGh"));
        users.add(new User("Feldkurát", "Katz", "dW9pL2eU1yNc"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createAndSaveDeafaultUsers() {
        createDefaultUsers();
        userRepository.saveAll(users);
    }

    public int saveUser(User user) throws Exception {

        if (!checkPersonId(user.getPersonId()).isEmpty()) {
            return 99;
        } else if (!checkPersonIdAuthService(user.getPersonId())) {
            return 55;
        } else if (checkPersonId(user.getPersonId()).isEmpty() && checkPersonIdAuthService(user.getPersonId())) {
            userRepository.save(user);
            return 1;
        }
        return -1;
    }

    public boolean updateUser(User user) {
        userRepository.updateNameSurname(user.getId(), user.getName(), user.getSurname());
        return true;
    }

    public List<User> checkPersonId(String personaId) {
        return userRepository.findByPersonId(personaId);
    }

    public boolean checkPersonIdAuthService(String personId) throws Exception {
        List<String> personIds = readFromFile(authkeysPath);
        return personIds.contains(personId);
    }

    public User getOneUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private List<String> readFromFile(String filePath) throws Exception {
        List<String> authKeys = new ArrayList<>();
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath)))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                authKeys.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new Exception("Soubor " + filePath + " nenalezen " + e.getMessage());
        }
        return authKeys;
    }
}
