package no.ntnu.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Expression {

    @Id
    @GeneratedValue
    @Getter
    private long expressionId;


    @Getter
    @Setter
    private String espressionUser;

    @Getter
    @Setter
    private String expression;

    public Expression() {
    }

    public Expression(String userEmail, String expression){
        this.espressionUser = userEmail;
        this.expression = expression;
    }

    public Expression(Long expressionId, Expression expression){
        this.expressionId = expressionId;
        this.espressionUser = expression.getEspressionUser();
        this.expression = expression.getExpression();
    }




}
