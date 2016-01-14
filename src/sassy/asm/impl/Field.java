package sassy.asm.impl;

import sassy.asm.api.IField;

public class Field implements IField {
	private String fieldName;
	private String accessType;
	private String type;

	public Field() {
		this.fieldName = "";
		this.accessType = "";
		this.type = "";
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type.substring(type.lastIndexOf(".")+1);
	}
}
