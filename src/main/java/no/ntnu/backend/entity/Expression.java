package no.ntnu.backend.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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


    @ManyToOne
    @JoinColumn(name = "email" )
    @Getter
    @Setter
    private User user;

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

    public Expression(User user, String expression){
        this.user = user;
        this.expression = expression;
    }




}
