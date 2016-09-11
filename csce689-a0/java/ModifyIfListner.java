import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.BufferedTokenStream;
import java.util.*;


public class ModifyIfListner extends JavaBaseListener {
	TokenStreamRewriter rewriter;
    static List<String> variables;

    public ModifyIfListner(TokenStream tokens) {
        //tokenList = new BufferedTokenStream(tokens);
        rewriter = new TokenStreamRewriter(tokens);
        variables = new ArrayList<>();

    }

    @Override
    public void enterStatement(JavaParser.StatementContext ctx) {
    if(ctx.getStart().getText().equals("if")) {
        String child = ctx.getChild(1).getText().replace("(", "").replace(")", "");
          if(variables.contains(child) && child.length() > 3) {

            String field = "  { \n\t\t\t\tSystem.out.println(\""+child+" "+ctx.getStart().getLine()+"\");\n\t\t\t\t\b";
            int ix = ctx.start.getTokenIndex();
            while(!(rewriter.getTokenStream().get(ix).getText().equals(")"))) {
            //System.out.println("at "+ix+" "+rewriter.getTokenStream().get(ix).getText());
            ix++;
            }
            rewriter.insertAfter(rewriter.getTokenStream().get(ix), field);
            rewriter.insertAfter(ctx.getStop(), "\n\t\t\t} NJECTED");



              System.out.println(child + " " +ctx.getStart().getLine());
          }



        //rewriter.insertAfter(ctx.getStart(), "{");
        //String field = "!!!INJECT!!!";
        //rewriter.getTokenStream().get(ctx.start.getTokenIndex())
        
        }
    }   
    


    @Override
    public void enterVariableDeclarator(JavaParser.VariableDeclaratorContext ctx) {
        //variables.add('('+ctx.variableDeclaratorId().getText()+')');
        variables.add(ctx.variableDeclaratorId().getText());

               // System.out.println("Variable ---> "+ctx.variableDeclaratorId().getText());
            }

}