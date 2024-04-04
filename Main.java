import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Read from stdin
        CharStream input = CharStreams.fromStream(System.in);

        // Lexer and parser setup
        ArithmeticLexer lexer = new ArithmeticLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ArithmeticParser parser = new ArithmeticParser(tokens);

        // Parse the input according to the 'prog' rule
        ParseTree tree = parser.prog();

        // Visit the parse tree to convert it to RPN
        RPNVisitor visitor = new RPNVisitor();
        String rpn = visitor.visit(tree);
        System.out.println(rpn);
    }
}
