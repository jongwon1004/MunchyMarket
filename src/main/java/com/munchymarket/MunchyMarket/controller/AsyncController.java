package com.munchymarket.MunchyMarket.controller;

import com.munchymarket.MunchyMarket.dto.AsyncTestDto;
import com.munchymarket.MunchyMarket.service.AsyncService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/async")
public class AsyncController {

    private final AsyncService asyncService;



    @PostMapping("/run")
    public ResponseEntity<Map<String, String>> hello(@RequestBody AsyncTestDto asyncTestDto) throws InterruptedException {

        asyncService.run(asyncTestDto);

        return ResponseEntity.ok().body(Collections.singletonMap("message", "hello"));
    }
}
