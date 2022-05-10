package no.ntnu.backend.dto;

import lombok.Data;
import no.ntnu.backend.entity.Expression;

@Data
public class ExpressionDTO {

    private long expressionId;

    private String expressionUserEmail;

    private String expression;


    public ExpressionDTO(String expressionUserEmail, String expression){
        this.expressionUserEmail = expressionUserEmail;
        this.expression = expression;
    }

    public ExpressionDTO(Expression expression){
        this.expressionId = expression.getExpressionId();
        this.expressionUserEmail = expression.getEspressionUser();
        this.expression = expression.getExpression();
    }

}
