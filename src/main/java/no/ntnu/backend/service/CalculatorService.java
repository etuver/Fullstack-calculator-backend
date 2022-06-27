package no.ntnu.backend.service;

import net.bytebuddy.implementation.bytecode.Throw;
import no.ntnu.backend.dto.MessageDTO;
import no.ntnu.backend.entity.Expression;
import no.ntnu.backend.entity.User;
import no.ntnu.backend.repository.ExpressionRepository;
import no.ntnu.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

@Service
public class CalculatorService {

    private final DecimalFormat df = new DecimalFormat("0.000");

    private final ExpressionRepository expressionRepository;

    private final UserRepository userRepository;

    @Autowired
    public CalculatorService(ExpressionRepository expressionRepository, UserRepository userRepository){
        this.expressionRepository = expressionRepository;
        this.userRepository = userRepository;
    }

    public String calculate(String expression) {
        String result = "";
        try {
            result = getResult(expression);
            //String finalExp = expression += result;
        } catch (Exception e) {
            result = "NaN";
            System.out.println(e.getMessage());
        }
        return result;
    }


    public String getResult(String expression){
        String expression2 = expression;
        double n1 = 0;
        double n2 = 0;
        String operator = "x";
        String res = "";
        boolean firstNegative = false;
        if (String.valueOf(expression2.charAt(0)).equals("-")){
            firstNegative = true;
            expression2 = expression2.substring(1);
        }

        if (expression2.contains("-")){
            operator = "-";
            String[] parts = expression2.split("-");
            n1 = Double.parseDouble(parts[0]);
            n2 = Double.parseDouble(parts[1]);
        }else if (expression2.contains("*")){
            operator = "*";
            String[] parts = expression2.split("\\*");
            n1 = Double.parseDouble(parts[0]);
            n2 = Double.parseDouble(parts[1]);
        }else if (expression2.contains("/")){
            operator = "/";
            String[] parts = expression2.split("/");
            n1 = Double.parseDouble(parts[0]);
            n2 = Double.parseDouble(parts[1]);
        }else if (expression2.contains("+")){
            operator = "+";
            String[] parts = expression2.split("\\+");
            n1 = Double.parseDouble(parts[0]);
            n2 = Double.parseDouble(parts[1]);
        }
        if (firstNegative){
            n1 = n1 * -1;
        }

        System.out.println("n1: " +n1 + ", n2: "+n2 + ", operator: "+ operator);

        if (!checkOperator(operator)){ //checking operator
            res = "Noe gikk galt, prøv igjen";
        }else
        {
            if (operator.equals("+")){
                res = String.valueOf(df.format(n1 + n2));
            }
            else if (operator.equals("-")){
                res = String.valueOf(df.format(n1 - n2));
            }else if (operator.equals("*")){
                res = String.valueOf(df.format(n1 * n2));
            }else if (operator.equals("/")){
                if (n2 == 0){
                    throw new IllegalArgumentException("Illegal expression: divie by zero");
                }else {
                    res = String.valueOf( df.format(n1 / n2));
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

    public void deleteExpression(Long id){
        Optional<Expression> optionalExpression = expressionRepository.findById(id);
        if (optionalExpression.isEmpty()){
            System.out.println("Nothing to delete");
        }else {
            expressionRepository.delete(optionalExpression.get());
        }
    }




}


    /**
     *
     * @param expression
     * @param userEmail
     * @return

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
     */