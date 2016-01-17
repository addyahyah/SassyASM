package sassy.asm.api;

public interface IField extends IModelPart {
	public String getFieldName();

	public void setFieldName(String fieldName);

	public String getAccessType();

	public void setAccessType(String accessType);

	public String getType();

	public void setType(String type);
}
