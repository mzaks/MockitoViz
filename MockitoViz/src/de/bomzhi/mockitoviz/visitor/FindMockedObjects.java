package de.bomzhi.mockitoviz.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import de.bomzhi.mockitoviz.model.MockedObject;
import de.bomzhi.mockitoviz.tools.ASTNodeHelper;

public class FindMockedObjects extends ASTVisitor{
	
	private List<MockedObject> foundMocks = new ArrayList<MockedObject>();
	
	private BindingHandler bindingHandler;
	
	@Override
	public boolean visit(MethodInvocation node) {
		if(isMockingMethod(node)){
			VariableDeclaration variableDecl = ASTNodeHelper.getParent(node, VariableDeclarationFragment.class);
			if(variableDecl != null){
				String objectName = variableDecl.getName().getIdentifier();
				VariableDeclarationStatement parent = ASTNodeHelper.getParent(variableDecl, VariableDeclarationStatement.class);
				Type type = parent.getType();
				foundMocks.add(new MockedObject(objectName, bindingHandler.getTypeName(type)));
				return true;
			}
			Assignment assignment = ASTNodeHelper.getParent(node, Assignment.class);
			if(assignment!=null){
				Expression leftHandSide = assignment.getLeftHandSide();
				if(leftHandSide instanceof SimpleName){
					SimpleName name = (SimpleName)leftHandSide;
					foundMocks.add(new MockedObject(name.getIdentifier(), bindingHandler.getTypeName(leftHandSide)));
					return true;
				}
				
			}
		}
		
		return true;
	}

	private boolean isMockingMethod(MethodInvocation node) {
		if (!(node.getName().getIdentifier().equals("mock") || node.getName()
				.getIdentifier().equals("spy")))
			return false;
		return bindingHandler.isStaticMockitoMethod(node);
	}

	

	public List<MockedObject> getFoundMocks() {
		return foundMocks;
	}

	public void setBindingHandler(BindingHandler bindingHandler) {
		this.bindingHandler = bindingHandler;
	}

	public BindingHandler getBindingHandler() {
		return bindingHandler;
	}
}
