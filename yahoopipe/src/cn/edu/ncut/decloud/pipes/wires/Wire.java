package cn.edu.ncut.decloud.pipes.wires;

public class Wire {
	
	private String pipeId;
	private String id;
	private String srcModuleId;
	private String srcType;
	private String srcUrl;
	private String tgtModuleId;
	private String tgtType;
	private String tgtUrl;
	private int count;

	public Wire(){
		
	}
	
	public Wire(String pipeId, String id, String srcModuleId, String srcType, String srcUrl, String tgtModuleId, String tgtType, String tgtUrl, int count){
		super();
		this.pipeId = pipeId;
		this.id = id;
		this.srcModuleId = srcModuleId;
		this.srcType = srcType;
		this.srcUrl = srcUrl;
		this.tgtModuleId = tgtModuleId;
		this.tgtType = tgtType;
		this.tgtUrl = tgtUrl;
		this.count = count;
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

	public String getSrcModuleId() {
		return srcModuleId;
	}

	public void setSrcModuleId(String srcModuleId) {
		this.srcModuleId = srcModuleId;
	}

	public String getSrcType() {
		return srcType;
	}

	public void setSrcType(String srcType) {
		this.srcType = srcType;
	}

	public String getSrcUrl() {
		return srcUrl;
	}

	public void setSrcUrl(String srcUrl) {
		this.srcUrl = srcUrl;
	}

	public String getTgtModuleId() {
		return tgtModuleId;
	}

	public void setTgtModuleId(String tgtModuleId) {
		this.tgtModuleId = tgtModuleId;
	}

	public String getTgtType() {
		return tgtType;
	}

	public void setTgtType(String tgtType) {
		this.tgtType = tgtType;
	}

	public String getTgtUrl() {
		return tgtUrl;
	}

	public void setTgtUrl(String tgtUrl) {
		this.tgtUrl = tgtUrl;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
