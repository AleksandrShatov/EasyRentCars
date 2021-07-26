package com.erc.controller.rest;

import com.erc.controller.requests.ModelCreateRequest;
import com.erc.controller.requests.ModelUpdateRequest;
import com.erc.domain.hibernate.Model;
import com.erc.repository.hibernate.ModelRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/model")
@RequiredArgsConstructor
public class ModelController {

    private final ModelRepository modelRepository;

    @ApiOperation("Find all models")
    @GetMapping("/find/all")
    public List<Model> findAll() {
        return modelRepository.findAll();
    }

    @ApiOperation("Find model by it's id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Model ID", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{id}")
    public Model findOne(@RequestParam Long id) {
        return modelRepository.findOne(id);
    }

    @ApiOperation("Find model by model")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "model", value = "Model", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{model}")
    public List<Model> findByModel(@RequestParam String model) {
        return modelRepository.findByModel(model);
    }

    @ApiOperation("Find model by manufacturer")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "manufacturer", value = "Model manufacturer", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{manufacturer}")
    public List<Model> findByManufacturer(@RequestParam String manufacturer) {
        return modelRepository.findByManufacturer(manufacturer);
    }

    @ApiOperation("Find model by fuel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fuel", value = "Model fuel", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/find/{fuel}")
    public List<Model> findByFuel(@RequestParam String fuel) {
        return modelRepository.findByFuel(fuel);
    }

    @ApiOperation("Save new model and return it")
    @PostMapping("/save/{request}")
    public Model save(@RequestBody ModelCreateRequest request) {

        Model model = new Model();
        model.setManufacturer(request.getManufacturer());
        model.setModel(request.getModel());
        model.setFuel(request.getFuel());
        model.setEngineVolume(request.getEngineVolume());

        return modelRepository.save(model);
    }

    @ApiOperation("Save new model")
    @PostMapping("/addone/{request}")
    public void addOne(@RequestBody ModelCreateRequest request) {

        save(request);
    }

    @ApiOperation("Save list of models")
    @PostMapping("/save/{models}")
    public void save(@RequestBody List<ModelCreateRequest> models) {
        for (ModelCreateRequest newModel : models) {
            save(newModel);
        }
    }

    @ApiOperation("Update model data")
    @PostMapping("/update")
    public Model update(@RequestBody ModelUpdateRequest request) {

        Model model = new Model();
        model.setId(request.getId());
        model.setManufacturer(request.getManufacturer());
        model.setModel(request.getModel());
        model.setFuel(request.getFuel());
        model.setEngineVolume(request.getEngineVolume());

        return modelRepository.update(model);
    }

    @ApiOperation("Hard delete model by it's id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Model ID", required = true, dataType = "string", paramType = "query")
    })
    @DeleteMapping("/delete/{id}")
    public void delete(@RequestParam Long id) {
        modelRepository.delete(id);
    }

}
