package sassy.asm.impl;

import sassy.asm.api.IField;
import sassy.asm.visitor.ITraverser;
import sassy.asm.visitor.IVisitor;

public class Field implements IField, ITraverser {
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

	@Override
	public void accept(IVisitor v) {
		v.visit(this);
	}
}
