package de.bomzhi.mockitoviz.tools;

import org.eclipse.jdt.core.dom.ASTNode;

public class ASTNodeHelper {

	@SuppressWarnings("unchecked")
	public static <T extends ASTNode> T getParent(ASTNode node, Class<T> parentClass) {
		ASTNode searchNode = node;
		while(searchNode.getParent() != null && !parentClass.isInstance(searchNode.getParent())){
			searchNode = searchNode.getParent();
		}
		return (T) searchNode.getParent();
	}
}
