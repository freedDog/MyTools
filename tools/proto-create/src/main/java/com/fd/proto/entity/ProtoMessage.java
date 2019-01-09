package com.fd.proto.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fd.proto.util.JavaTypeHelper;

/**
 * proto Message
 * ProtoMessage.java
 * @author JiangBangMing
 * 2019年1月9日下午3:18:25
 */
public class ProtoMessage {
	private String module;
	private String messageName;
	private List<ProtoAttribute> attrs;
	private List<ProtoMessage> childMessages;

	public ProtoMessage() {
		attrs = new ArrayList<>();
		childMessages = new ArrayList<>();
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getMessageName() {
		return messageName;
	}

	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	public List<ProtoAttribute> getAttributes() {
		return attrs;
	}

	public void addAttr(ProtoAttribute attr) {
		attrs.add(attr);
	}

	public void addAnnotation(List<String> annotationList) {
		for (int i = 0, len = attrs.size(); i < len; i++) {
			attrs.get(i).setAnnotation(annotationList.get(i));
		}
	}

	public void addChildMessage(ProtoMessage message) {
		childMessages.add(message);
	}

	public List<ProtoMessage> getChildMessages() {
		return childMessages;
	}

	public String getFullName() {
		return module + "." + messageName;
	}

	public List<ProtoMessage> getObjectMsgImportList() {
		List<ProtoMessage> importList = new ArrayList<>();
		Set<String> needImportTypes = new HashSet<>();
		for (ProtoAttribute attr : attrs) {
			if (attr.getJavaTypeStyle() == JavaTypeHelper.OBJECT && !attr.getType().equals(messageName)) {
				needImportTypes.add(attr.getType());
			}
		}

		for (ProtoMessage message : childMessages) {
			if (needImportTypes.contains(message.getMessageName())) {
				importList.add(message);
			}
		}
		return importList;
	}

	public int calcBaseTypeLength() {
		int length = 0;
		for (ProtoAttribute attr : attrs) {
			if (attr.getJavaTypeStyle() == JavaTypeHelper.BASE && !attr.isArray()) {
				length += JavaTypeHelper.getTypeLength(attr.getType());
			}
		}
		return length;
	}

	public boolean hasArray() {
		for (ProtoAttribute attr : attrs) {
			if (attr.isArray()) {
				return true;
			}
		}
		return false;
	}

	public String toString() {
		return "\nMessage name : " + messageName + "\nAttributes : " + attrs;
	}

}
