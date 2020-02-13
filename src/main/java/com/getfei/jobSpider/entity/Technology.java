package com.getfei.jobSpider.entity;

import java.util.Arrays;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class Technology {

	private String name;
	// private TechnologyType type;
	private String type;
	private String[] aliases;

	public Technology(String name, String type, String[] aliases) {
		this.name = name;
		this.type = type;
		this.aliases = aliases;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Technology other = (Technology) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "技术 [类型=" + type + ", 名称=" + name + ", 别名="+Arrays.toString(aliases)+"]";
	}
}
