package com.erc.controller.rest;

import com.erc.controller.requests.RoleUpdateRequest;
import com.erc.controller.requests.UserCreateRequest;
import com.erc.controller.requests.UserUpdateRequest;
import com.erc.domain.hibernate.Role;
import com.erc.domain.hibernate.User;
import com.erc.repository.hibernate.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @ApiOperation("Find all users")
    @GetMapping("/find/all")
    public List<User> findAll() {
        return userRepository.findAll();
    }

//    @ApiOperation("Find user by his id")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "User ID", required = true, dataType = "string", paramType = "path")
//    })
//    @GetMapping("/{id}")
//    public User findOne(@PathVariable("id") Long id) {
//        return userRepository.findOne(id);
//    }

    @ApiOperation("Find user by his id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "User ID", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{id}")
    public User findOne(@RequestParam Long id) {
        return userRepository.findOne(id);
    }

    @ApiOperation("Find user by his login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "login", value = "User login", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{login}")
    public User findByLogin(@RequestParam String login) {
        return userRepository.findByLogin(login);
    }

    @ApiOperation("Find user by his login and password")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "login", value = "User login", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "User password", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{login, password}")
    public User findByLoginAndPassword(@RequestParam String login, @RequestParam String password) {
        return userRepository.findByLoginAndPassword(login, password);
    }

    @ApiOperation("Save new user and return him")
    @PostMapping("/save/{request}")
    public User save(@RequestBody UserCreateRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setPatronymic(request.getPatronymic());
        user.setPassportNumber(request.getPassportNumber());
        user.setPassportSeries(request.getPassportSeries());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setBirthDate(request.getBirthDate());
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }

    @ApiOperation("Save new user")
    @PostMapping("/addone/{request}")
    public void addOne(@RequestBody UserCreateRequest request) {

        save(request);
    }

    @ApiOperation("Save list of users")
    @PostMapping("/save/{users}")
    public void save(@RequestBody List<UserCreateRequest> users) {
        for (UserCreateRequest newUser : users) {
            save(newUser);
        }
    }

    @ApiOperation("Update user data")
    @PostMapping("/update")
    public User update(@RequestBody UserUpdateRequest request) {
        User user = new User();
        user.setId(request.getId());
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setPatronymic(request.getPatronymic());
        user.setPassportNumber(request.getPassportNumber());
        user.setPassportSeries(request.getPassportSeries());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setBirthDate(request.getBirthDate());
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());

        return userRepository.update(user);
    }

    @ApiOperation("Hard delete user by his id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "User ID", required = true, dataType = "string", paramType = "query")
    })
    @DeleteMapping("/delete/{id}")
    public void delete(@RequestParam Long id) {
        userRepository.delete(id);
    }

    @ApiOperation("Save roles for current user id")
    //TODO bad for UI
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId", value = "User ID", required = true, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "requests", value = "Roles for user", required = true, dataType = "string", paramType = "body")
//    })
    @PostMapping("/save/roles")
    public void saveUserRoles(@RequestParam Long userId, @RequestBody List<RoleUpdateRequest> requests) {

        List<Role> roles = new ArrayList<>();

        for (RoleUpdateRequest addRole : requests) {
            Role role = new Role();
            role.setRoleName(addRole.getRoleName());
            role.setId(addRole.getId());
            roles.add(role);
        }

        userRepository.saveUserRoles(userId, roles);
    }

}
