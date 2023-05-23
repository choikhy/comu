package com.com304;

import com.com304.dto.BoardDTO;
import com.com304.dto.PageRequestDTO;
import com.com304.dto.PageResponseDTO;
import com.com304.service.BoardService;
import com.com304.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;

@SpringBootApplication
public class Com304Application {

    public static void main(String[] args) {
        SpringApplication.run(Com304Application.class, args);
    }

}
