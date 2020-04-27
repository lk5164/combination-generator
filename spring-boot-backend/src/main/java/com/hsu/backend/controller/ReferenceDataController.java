package com.hsu.backend.controller;

import com.hsu.backend.model.Item;

import com.hsu.backend.repository.ReferenceDataRepository;
import com.hsu.backend.service.ReferenceDataService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value = "REST Apis to generate possible combinations!", tags = "ReferenceDataController")
@RequestMapping(ReferenceDataController.V1_PATH_PREFIX)
@RequiredArgsConstructor
public class ReferenceDataController {
    public static final String PATH_PREFIX = "/reference";
    public static final String V1_PATH_PREFIX = PATH_PREFIX + "/v1";

    @Autowired
    private ReferenceDataService referenceDataService;

    @ApiOperation(value = "Generate All possible combinations with given phone number", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Unexpected error happened on server")
//            @ApiResponse(code = 101, message = "Outcome is too large!")
    })
    @PostMapping("/generate")
    Iterable<Item> generateCombinations(@RequestBody @Valid String phoneNumber) {
        System.out.println("receiving phone number: " + phoneNumber);
        return this.referenceDataService.generateCombinations(phoneNumber);
    }

    @ApiOperation(value = "Get list of Items in the System ", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Unexpected error happened on server")
    })
    @GetMapping(value = "/getItems")
    public Iterable<Item> findAllItems() {
        return this.referenceDataService.findAllItems();
    }

    @ApiOperation(value = "Get list of Items By Pages ", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Unexpected error happened on server")
    })
    @GetMapping(value = "/getItems/size/{size}/page/{page}")
    public Iterable<Item> findByPage(@PathVariable(value = "size") Integer size,
                                     @PathVariable(value = "page") Integer page) {
        return this.referenceDataService.findByPage(size, page);
    }

    @ApiOperation(value = "Delete all existing records")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 500, message = "Unexpected error happened on server")
    })
    @DeleteMapping("/reset")
    public void deleteAll() {
        this.referenceDataService.deleteAll();
    }
//
}
