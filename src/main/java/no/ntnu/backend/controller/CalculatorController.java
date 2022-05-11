package no.ntnu.backend.controller;


import no.ntnu.backend.dto.ExpressionDTO;
import no.ntnu.backend.entity.Expression;
import no.ntnu.backend.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exp")
public class CalculatorController {

    private final CalculatorService calculatorService;

    @Autowired
    public CalculatorController(CalculatorService calculatorService){
        this.calculatorService = calculatorService;
    }

    @PostMapping
    public ResponseEntity<Object> postCalculation(@RequestBody ExpressionDTO data, Principal principal){
        System.out.println("data: " + data.getExpression() + " " + data.getExpressionUserEmail());
        Optional<Expression> expression = calculatorService.calculate(data.getExpression(), data.getExpressionUserEmail());

        if (expression.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to calculate");
        }
        System.out.println("kom hit");
        return ResponseEntity.ok(new ExpressionDTO(expression.get()));
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getCalculations(Principal principal){
        List<ExpressionDTO> list = calculatorService.getExpressionByUserEmail(principal.getName()).stream()
                .map(ExpressionDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }



}
