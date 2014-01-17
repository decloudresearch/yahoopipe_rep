package cn.edu.ncut.decloud.pipes.modules;

public class Module {

	private String pipeId;
	private String id;
	private String type;
	private String url;
	private String sourceType;

	public Module() {

	}

	public Module(String pipeId, String id, String type, String url) {
		super();
		this.pipeId = pipeId;
		this.id = id;
		this.type = type;
		this.url = url;
	}

	public String getPipeId() {
		return pipeId;
	}

	public void setPipeId(String pipeId) {
		this.pipeId = pipeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
}
