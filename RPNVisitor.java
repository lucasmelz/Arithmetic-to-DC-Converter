import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import java.util.HashMap;
import java.util.Map;

public class RPNVisitor extends ArithmeticBaseVisitor<String> {

    private Map<String, Integer> variableIndices = new HashMap<>();
    private int nextIndex = 0; // To keep track of the next available index for a new variable.

    @Override
    public String visitMulDiv(ArithmeticParser.MulDivContext ctx) {
        String left = visit(ctx.expr(0)); // Visit the left side of the expression
        String right = visit(ctx.expr(1)); // Visit the right side of the expression
        return left + " " + right + " " + ctx.op.getText(); // Concatenate in RPN order
    }

    @Override
    public String visitAddSub(ArithmeticParser.AddSubContext ctx) {
        String left = visit(ctx.expr(0));
        String right = visit(ctx.expr(1));
        return left + " " + right + " " + ctx.op.getText();
    }

    @Override
    public String visitInt(ArithmeticParser.IntContext ctx) {
        return ctx.INT().getText(); // Integers are returned as is
    }

    @Override
    public String visitVar(ArithmeticParser.VarContext ctx) {
        String varName = ctx.ID().getText();
        if (variableIndices.containsKey(varName)) {
            int index = variableIndices.get(varName);
            return "l" + index; // Use the load command with the variable's numeric index.
        }
        return ""; // Optionally handle undefined variables.
    }

    @Override
    public String visitParens(ArithmeticParser.ParensContext ctx) {
        return visit(ctx.expr()); // Ignore the parentheses and visit the expression inside
    }

    @Override
    public String visitAssignment(ArithmeticParser.AssignmentContext ctx) {
        String variable = ctx.ID().getText();
        String expression = visit(ctx.expr());
        int index;
        if (!variableIndices.containsKey(variable)) {
            index = nextIndex++;
            variableIndices.put(variable, index);
        } else {
            index = variableIndices.get(variable);
        }
        return expression + " s" + index; // Store the expression's result in the register indexed by the variable's
                                          // index.
    }

    @Override
    public String visitProg(ArithmeticParser.ProgContext ctx) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < ctx.stat().size(); i++) {
            String result = visit(ctx.stat(i));
            if (!result.isEmpty()) {
                builder.append(result);
                if (i < ctx.stat().size() - 1) {
                    builder.append("\n");
                }
            }
        }
        return builder.toString();
    }

    @Override
    public String visitPrintExpr(ArithmeticParser.PrintExprContext ctx) {
        // Handle expressions that should print their result
        String expr = visit(ctx.expr());
        return expr + " p"; // 'p' to print in dc
    }

    @Override
    public String visitPrintExprSemi(ArithmeticParser.PrintExprSemiContext ctx) {
        // Directly handle the expression part, identical logic to visitPrintExpr
        String expr = visit(ctx.expr());
        return expr + " p"; // 'p' to print in dc
    }

    @Override
    public String visitAssignStat(ArithmeticParser.AssignStatContext ctx) {
        // Handle assignments
        String assignment = visit(ctx.assignment());
        // Possibly additional handling for silent assignment
        return assignment;
    }

    @Override
    public String visitAssignStatSemi(ArithmeticParser.AssignStatSemiContext ctx) {
        // Directly handle the assignment part, identical logic to visitAssignStat
        String assignment = visit(ctx.assignment());
        // Possibly additional handling for silent assignment
        return assignment;
    }

    @Override
    public String visitEmptyStat(ArithmeticParser.EmptyStatContext ctx) {
        // Handle empty statements (e.g., a lone newline or semicolon)
        return ""; // You might simply return an empty string
    }

}
