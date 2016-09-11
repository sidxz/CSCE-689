
/* PART 2 */

import java.io.IOException;
import java.util.*;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.TokenStreamRewriter;

public class JavaParserTest extends JavaBaseListener{

	static List<String> variables;
	TokenStreamRewriter rewriter;

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

    	//ModifyIfListner extractor = new ModifyIfListner(tokens);
		variables = new ArrayList<>();
    	
    	walker.walk(listener, tree); // traverse parse tree with listener
    	//walker.walk(extractor, tree); // traverse parse tree with listener
		//System.out.println(extractor.rewriter.getText());

    }


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void enterStatement(JavaParser.StatementContext ctx){
		/*
		Filter all statements starting with if
		*/
		
		if(ctx.getStart().getText().equals("if")) {
    		/*
    		Check if the if statement is followed by a variable, and length of variable is greater than 3
    		*/
			String child = ctx.getChild(1).getText().replace("(", "").replace(")", "");
			if(variables.contains(child) && child.length() > 3) {
				System.out.println(child + " " +ctx.getStart().getLine());
			}

		}	
	}

	/*
	Generate a list of all the variables
	*/
	 @Override
	public void enterVariableDeclarator(JavaParser.VariableDeclaratorContext ctx) {
		variables.add(ctx.variableDeclaratorId().getText());
            }
}
