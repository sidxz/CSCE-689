import java.io.IOException;
import java.util.*;


import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class JavaParserTest extends JavaBaseListener{

	static List<String> variables;

	public static void main(String[] args) throws IOException {
		if(args.length<1)
		{
			System.err.println("java JavaParserTest input-filename\n"
					+"Example: java JavaParserTest Test.java");
			return;
		}
		String inputFile = args[0];
    	CharStream input = new ANTLRFileStream(inputFile);
    	JavaLexer lexer = new JavaLexer(input);
    	CommonTokenStream tokens = new CommonTokenStream(lexer);
    	JavaParser parser = new JavaParser(tokens); //create parser

    	ParseTree tree = parser.compilationUnit();
    	ParseTreeWalker walker = new ParseTreeWalker(); // create standard walker
    	JavaParserTest listener = new JavaParserTest(); // create a parse tree listener

    	variables = new ArrayList<>();
    	
    	walker.walk(listener, tree); // traverse parse tree with listener
    }


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void enterStatement(JavaParser.StatementContext ctx){
		//System.out.println("enterStatement");

		if(ctx.getStart().getText().equals("if")) {
    	//first token is 'if'
			
			String child = ctx.getChild(1).getText().replace("(", "").replace(")", "");
			if(variables.contains(child) && child.length() > 3) {
				System.out.println(child + " " +ctx.getStart().getLine());
			}

			//System.out.println("getRuleCOntext() -> "+ctx.getRuleContext().getText());
			//System.out.println("getRuleCOntext() -> "+ctx.getCause().getText());
			/*if(ctx.getChild(1) instanceof IfStmtContext) {
				System.out.println("This");
			}*/
		}	
	}
	 @Override
	public void enterVariableDeclarator(JavaParser.VariableDeclaratorContext ctx) {
		//variables.add('('+ctx.variableDeclaratorId().getText()+')');
		variables.add(ctx.variableDeclaratorId().getText());

               // System.out.println("Variable ---> "+ctx.variableDeclaratorId().getText());
            }
}
