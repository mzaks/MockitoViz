/**
 * 
 */
package de.bomzhi.jdt.exploration.matcher;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;

public class TypeHasName extends BaseMatcher<AbstractTypeDeclaration>{

	private final String name;

	public TypeHasName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean matches(Object item) {
		AbstractTypeDeclaration type = (AbstractTypeDeclaration)item;
		return type.getName().getIdentifier().equals(name);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("type with name ").appendValue(name);
	}
	
	@Factory
	public static TypeHasName isTypeWithName(String typeName) {
		return new TypeHasName(typeName);
	}
}