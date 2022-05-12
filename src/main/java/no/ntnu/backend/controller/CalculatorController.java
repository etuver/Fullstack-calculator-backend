package no.ntnu.backend.controller;


import no.ntnu.backend.dto.ExpressionDTO;
import no.ntnu.backend.entity.Expression;
import no.ntnu.backend.entity.User;
import no.ntnu.backend.repository.ExpressionRepository;
import no.ntnu.backend.repository.UserRepository;
import no.ntnu.backend.service.CalculatorService;
import no.ntnu.backend.service.UserService;
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
    UserRepository userRepository;

    private UserService userService;

    @Autowired
    ExpressionRepository expressionRepository;

    @Autowired
    public CalculatorController(CalculatorService calculatorService){
        this.calculatorService = calculatorService;
    }

    @PostMapping("")
    public ResponseEntity<String> postCalculation(@RequestBody ExpressionDTO data, Principal principal){
        if (data.getExpression().isBlank() || data.getExpressionUserEmail().isBlank()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to calculate");
        }
        String res = calculatorService.calculate(data.getExpression());
        String fullExp = data.getExpression() + " = " + res;
        Expression expression = new Expression(data.getExpressionUserEmail(), fullExp );
        Optional<User> optionalUser = userRepository.findById(data.getExpressionUserEmail());
        if (optionalUser.isPresent()){

            optionalUser.ifPresent(expression::setUser);

            expressionRepository.save(expression);
        }
        System.out.println("res: " + res);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/exp{email}")
    public ResponseEntity<List<Expression>> getCalculations(@PathVariable String email){
        System.out.println("Recieved get request for history");
        Optional<List<Expression>> list = expressionRepository.findAllByEspressionUser(email);

        return list.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().body(null));


    }
}


    /**
     *
     * @param data
     * @param principal
     * @return
    @PostMapping("")
    public ResponseEntity<Object> postCalculation(@RequestBody ExpressionDTO data, Principal principal){
        System.out.println("data: " + data.getExpression() + " " + data.getExpressionUserEmail());
        Optional<Expression> expression = calculatorService.calculate(data.getExpression(), data.getExpressionUserEmail());

        if (expression.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to calculate");
        }
        System.out.println("kom hit");
        return ResponseEntity.ok(new ExpressionDTO(expression.get()));
    }
     */

    /**
     @GetMapping("/exp{email}")
     public ResponseEntity<Object> getCalculations(Principal principal){
     System.out.println("Recieved get request for history");
     List<ExpressionDTO> list = calculatorService.getExpressionByUserEmail(principal.getName()).stream()
     .map(ExpressionDTO::new).collect(Collectors.toList());
     return ResponseEntity.ok(list);
     }*/