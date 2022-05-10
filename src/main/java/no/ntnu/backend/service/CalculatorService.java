package no.ntnu.backend.service;

import no.ntnu.backend.dto.MessageDTO;
import no.ntnu.backend.entity.Expression;
import no.ntnu.backend.entity.User;
import no.ntnu.backend.repository.ExpressionRepository;
import no.ntnu.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

@Service
public class CalculatorService {

    private final ExpressionRepository expressionRepository;

    private final UserRepository userRepository;

    @Autowired
    public CalculatorService(ExpressionRepository expressionRepository, UserRepository userRepository){
        this.expressionRepository = expressionRepository;
        this.userRepository = userRepository;
    }

    public Optional<Expression> calculate(String expression, String userEmail) {
        Optional<User> user = userRepository.findById(userEmail);
        if (user.isEmpty()) {
            return Optional.empty();
        }

        try {
            String result = getResult(expression);
            String finalExp = expression += result;
            Expression finalExpression = new Expression(userEmail, finalExp);
            return Optional.of(expressionRepository.save(finalExpression));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }


    public String getResult(String expression){
        double n1 = 0;
        double n2 = 0;
        String operator = "x";
        String res = "";
        if (expression.contains("-")){
            operator = "-";
            String[] parts = expression.split("-");
            n1 = Double.parseDouble(parts[0]);
            n2 = Double.parseDouble(parts[1]);
        }else if (expression.contains("*")){
            operator = "*";
            String[] parts = expression.split("\\*");
            n1 = Double.parseDouble(parts[0]);
            n2 = Double.parseDouble(parts[1]);
        }else if (expression.contains("/")){
            operator = "/";
            String[] parts = expression.split("/");
            n1 = Double.parseDouble(parts[0]);
            n2 = Double.parseDouble(parts[1]);
        }else if (expression.contains("+")){
            operator = "+";
            String[] parts = expression.split("\\+");
            n1 = Double.parseDouble(parts[0]);
            n2 = Double.parseDouble(parts[1]);
        }
        System.out.println("n1: " +n1 + ", n2: "+n2 + ", operator: "+ operator);

        if (!checkOperator(operator)){ //checking operator
            res = "Noe gikk galt, prøv igjen";
        }else
        {
            if (operator.equals("+")){
                res = String.valueOf(n1 + n2);
            }
            else if (operator.equals("-")){
                res = String.valueOf(n1 - n2);
            }else if (operator.equals("*")){
                res = String.valueOf(n1 * n2);
            }else if (operator.equals("/")){
                if (n2 == 0){
                    throw new IllegalArgumentException("Illegal expression: divie by zero");
                }else {
                    res = String.valueOf(n1 / n2);
                }
            }else {
                throw new IllegalArgumentException("Illegal expression: bad operator");
            }

        }
        return res;

    }


    public boolean checkOperator(String operator){
        String[] operators = {"+", "-", "/", "*"};
        for (String o: operators){
            if (operator.equals(o) ){
                return true;
            }
        }
        return false;
    }

    public List<Expression> getExpressionByUserEmail(String email){
        return expressionRepository.findAllByEspressionUser(email);
    }

    public void deleteExpression(Long id){
        Optional<Expression> optionalExpression = expressionRepository.findById(id);
        if (optionalExpression.isEmpty()){
            System.out.println("Nothing to delete");
        }else {
            expressionRepository.delete(optionalExpression.get());
        }
    }




}
