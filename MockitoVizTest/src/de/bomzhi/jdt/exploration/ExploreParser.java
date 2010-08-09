package de.bomzhi.jdt.exploration;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.junit.Test;

public class ExploreParser {
	
	@Test
	public void parseSimpleClassDeclaration() throws Exception {
		String javaCode = "class A {}";
		ASTParser newParser = ASTParser.newParser(AST.JLS3);
		newParser.setSource(javaCode.toCharArray());
		ASTNode ast = newParser.createAST(new NullProgressMonitor());
		assertThat(ast, is(notNullValue()));
	}
}
