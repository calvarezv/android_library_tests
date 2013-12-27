package com.inzpiral.consumer.models;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Node extends BaseNode {

	@SerializedName("name")
	private String mName;

	@SerializedName("node_order")
	private int mNodeOrder;

	@SerializedName("children")
	private List<BaseNode> mChildren;

	public String getName() {
		return mName;
	}

	// Getters and Setters
	public void setName(String mName) {
		this.mName = mName;
	}

	public int getNodeOrder() {
		return mNodeOrder;
	}

	public void setNodeOrder(int mNodeOrder) {
		this.mNodeOrder = mNodeOrder;
	}

	public List<BaseNode> getChildren() {
		return mChildren;
	}

	public void setChildren(List<BaseNode> mChildren) {
		this.mChildren = mChildren;
	}
	
	// Other methods
	public int countChildren() {
		return getChildren().size();
	}

}
