package de.bomzhi.mockitoviz.visitor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Type;
import org.junit.Test;
import org.mockito.Matchers;

import de.bomzhi.mockitoviz.model.MockedObject;

public class TestFindMockedObjects {
	
	private Block ast;
	private BindingHandler bindingHandler;
	private List<MockedObject> foundMocks;

	@Test
	public void canFindMock() throws Exception {
		String javaCode = "A a = mock(A.class);";
		setup(javaCode);
		when(bindingHandler.getTypeName(Matchers.any(Type.class))).thenReturn("A");
		findMocks();
		assertThat(foundMocks.isEmpty(), is(false));
	}

	@Test
	public void canFindMockWithProperName() throws Exception {
		String javaCode = "A a = mock(A.class);";
		setup(javaCode);
		when(bindingHandler.getTypeName(any(Type.class))).thenReturn("A");
		findMocks();
		assertThat(foundMocks.get(0).getObjectName(), is("a"));
		assertThat(foundMocks.get(0).getTypeName(), is("A"));
	}
	
	@Test
	public void canFindMockInAssignment() throws Exception {
		String javaCode = "A a; a = mock(A.class);";
		setup(javaCode);
		when(bindingHandler.getTypeName(Matchers.any(Type.class))).thenReturn("A");
		findMocks();
		assertThat(foundMocks.isEmpty(), is(false));
	}

	@Test
	public void canFindMocksWithProperNamesForDeclarationAndAssignment() throws Exception {
		String javaCode = "A a1 = mock(A.class); B b2; b2 = mock(B.class);";
		setup(javaCode);
		when(bindingHandler.getTypeName(any(Type.class))).thenReturn("A");
		when(bindingHandler.getTypeName(any(Expression.class))).thenReturn("B");
		findMocks();
		assertThat(foundMocks.get(0).getObjectName(), is("a1"));
		assertThat(foundMocks.get(0).getTypeName(), is("A"));
		assertThat(foundMocks.get(1).getObjectName(), is("b2"));
		assertThat(foundMocks.get(1).getTypeName(), is("B"));
	}
	
	@Test
	public void canFindMockAlsoIfItIsASpy() throws Exception {
		String javaCode = "A a = spy(new A());";
		setup(javaCode);
		when(bindingHandler.getTypeName(Matchers.any(Type.class))).thenReturn("A");
		findMocks();
		assertThat(foundMocks.isEmpty(), is(false));
	}
	
	private void setup(String javaCode) {
		createAst(javaCode);
		prepareBindingHandler();
	}

	private void createAst(String javaCode) {
		ASTParser newParser = ASTParser.newParser(AST.JLS3);
		newParser.setKind(ASTParser.K_STATEMENTS);
		newParser.setSource(javaCode.toCharArray());
		ast = (Block)newParser.createAST(new NullProgressMonitor());
	}

	private void prepareBindingHandler(){
		bindingHandler = mock(BindingHandler.class);
		when(bindingHandler.isStaticMockitoMethod(any(MethodInvocation.class))).thenReturn(true);
	}
	
	private void findMocks() {
		FindMockedObjects finder = new FindMockedObjects();
		finder.setBindingHandler(bindingHandler);
		ast.accept(finder);
		foundMocks = finder.getFoundMocks();
	}
}
