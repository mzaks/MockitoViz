package de.bomzhi.jdt.exploration;

import static de.bomzhi.jdt.exploration.matcher.TypeHasName.isTypeWithName;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Before;
import org.junit.Test;

public class ExploreParser {
	
	private ASTNode ast;

	@Before
	public void createAst() {
		String javaCode = "class A {}";
		ASTParser newParser = ASTParser.newParser(AST.JLS3);
		newParser.setSource(javaCode.toCharArray());
		ast = newParser.createAST(new NullProgressMonitor());
	}
	
	@Test
	public void parseSimpleClassDeclaration() throws Exception {
		assertThat(ast, is(notNullValue()));
	}
	
	@Test
	public void ensureThatParsedElementIsAnCompilationUnit() throws Exception {
		assertThat(ast, is(CompilationUnit.class));
	}

	@Test
	public void ensureThatCompilationUnitHasTypeCalledA() throws Exception {
		CompilationUnit cu = (CompilationUnit)ast;
		List<AbstractTypeDeclaration> allTypes = cu.types();
		assertThat(allTypes, is(notNullValue()));
		assertThat(allTypes, hasItem(isTypeWithName("A")));
	}
	
	@Test
	public void ensureThatParserCanParseStatements() throws Exception {
		String javaCode = "int a = 2; int b = foo();";
		ASTParser newParser = ASTParser.newParser(AST.JLS3);
		newParser.setKind(ASTParser.K_STATEMENTS);
		newParser.setSource(javaCode.toCharArray());
		ast = newParser.createAST(new NullProgressMonitor());
		assertThat(ast, is(Block.class));
	}
}