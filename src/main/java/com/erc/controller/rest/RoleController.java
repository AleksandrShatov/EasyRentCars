package com.erc.controller.rest;

import com.erc.controller.requests.RoleCreateRequest;
import com.erc.domain.hibernate.Role;
import com.erc.repository.hibernate.RoleRepository;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    @ApiOperation("Find all roles")
    @GetMapping("/find/all")
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @ApiOperation("Find role by it's id")
    @GetMapping("/find/{id}")
    public Role findOne(@PathVariable Integer id) {
        return roleRepository.findOne(id);
    }

    @ApiOperation("Save new role and return it")
    @PostMapping("/save/{request}")
    public Role save(@RequestBody RoleCreateRequest request) {

        Role role = new Role();
        role.setRoleName(request.getRoleName());

        return roleRepository.save(role);
    }
}
