package com.finalproject.shelter.controller.api.board;

import com.finalproject.shelter.model.Header;
import com.finalproject.shelter.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/view")
public class BoardViewApiController {

    @Autowired
    private BoardRepository boardRepository;

    @GetMapping("{id}")
    public String goodview(@PathVariable Long id){
        System.out.println(id);
        return "12";
    }

}
