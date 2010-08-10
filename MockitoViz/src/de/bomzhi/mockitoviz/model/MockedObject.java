package de.bomzhi.mockitoviz.model;

public class MockedObject {
	private final String objectName;
	private final String className;

	public MockedObject(String objectName, String className) {
		this.objectName = objectName;
		this.className = className;
	}

	public String getObjectName() {
		return objectName;
	}

	public String getTypeName() {
		return className;
	}
}
