package com.erc.controller.rest;

import java.util.List;

import com.erc.controller.requests.RoleCreateRequest;
import com.erc.controller.requests.RoleUpdateRequest;
import com.erc.domain.hibernate.Role;
import com.erc.repository.hibernate.RoleRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository roleRepository;

    @ApiOperation("Find all roles")
    @GetMapping("find/all")
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @ApiOperation("Find role by it's id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Role ID", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("find/{id}")
    public Role findOne(@RequestParam Integer id) {
        return roleRepository.findOne(id);
    }

    @ApiOperation("Find role by it's name")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "Role name", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("find/{roleName}")
    public Role findRoleName(@RequestParam String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @ApiOperation("Save new role and return it")
    @PostMapping("save/one")
    public Role save(@RequestBody RoleCreateRequest request) {

        Role role = new Role();
        role.setRoleName(request.getRoleName());

        return roleRepository.save(role);
    }

    @ApiOperation("Save new role")
    @PostMapping("add/one")
    public void addOne(@RequestBody RoleCreateRequest request) {

        save(request);
    }

    @ApiOperation("Save list of roles")
    @PostMapping("save/many")
    public void save(@RequestBody List<RoleCreateRequest> roles) {
        for (RoleCreateRequest newRole : roles) {
            save(newRole);
        }
    }

    @ApiOperation("Update role data")
    @PutMapping("update")
    public Role update(@RequestBody RoleUpdateRequest request) {

        Role role = new Role();
        role.setId(request.getId());
        role.setRoleName(request.getRoleName());

        return roleRepository.update(role);
    }

    @ApiOperation("Hard delete role by it's id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Role ID", required = true, dataType = "string", paramType = "query")
    })
    @DeleteMapping("delete/{id}")
    public void delete(@RequestParam Integer id) {
        roleRepository.delete(id);
    }
}
