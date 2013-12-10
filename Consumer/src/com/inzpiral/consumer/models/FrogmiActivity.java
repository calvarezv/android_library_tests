package com.inzpiral.consumer.models;

import com.google.gson.annotations.SerializedName;

public class FrogmiActivity extends Node {

	@SerializedName("node_order")
	public int mNodeOrder;
	
	@SerializedName("original_code")
	public String mOriginalCode;

}
