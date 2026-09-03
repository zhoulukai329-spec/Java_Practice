package main.resources.java.Controller;

import main.resources.java.Service.*;

@RestController

@RequestMapping("/framework_dynconfig-1.1.0/login")
public class UserController {

    public UserController(UserService userService){
        
        @GetMapping("/{id}")
        public User getUserById(@PathVariable Long id){
            return userService.getById(id);
        } 
        
        @GetMapping("/nickname")
        public User getUserByName(@PathVariable String nickname){
            return userService.getByName(nickname);
        }

        @PostMapping
        public int insertUser(@RequestBody User user){
            return userService.insert(user);
        }
        
        @DeleteMapping("/{id}")
        public int deleteByID(@PathVariable Long id){
            return userService.deleteByID(id);
        }

        @DeleteMapping("/nickname")
        public int deleteByName(@PathVariable String name){
            return userService.deleteByName(name);
        }
        
        @PutMapping("/{id}")
        public int update(@PathVariable Long id){
            return userService.update(id);
        }

        @
    }
}
