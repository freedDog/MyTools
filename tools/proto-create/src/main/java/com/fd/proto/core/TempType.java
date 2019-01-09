package com.fd.proto.core;

import com.fd.proto.entity.ProtoMessage;

public enum TempType {
	JAVA {
		public String getVmName() {
			return "vm/JavaTemp.vm";
		}

		public String getFileExtension() {
			return ".java";
		}

		public String parseFullName(ProtoMessage message) {
			return message.getModule() + "." + message.getMessageName();
		}
	},

	LUA {
		public String getVmName() {
			return "vm/LuaTemp.vm";
		}

		public String getFileExtension() {
			return ".lua";
		}

		public String parseFullName(ProtoMessage message) {
			// return message.getModule() + "." + message.getMessageName().toLowerCase();
			return message.getModule() + "." + message.getMessageName();
		}
	},

	JSON {
		public String getVmName() {
			return "vm/JsonTemp.vm";
		}

		public String getFileExtension() {
			return ".json";
		}

		public String parseFullName(ProtoMessage message) {
			return message.getModule() + "." + message.getMessageName();
		}
	},

	;

	public String getVmName() {
		return "";
	}

	public String getFileExtension() {
		return "";
	}

	public String parseFullName(ProtoMessage message) {
		return "";
	}
}

