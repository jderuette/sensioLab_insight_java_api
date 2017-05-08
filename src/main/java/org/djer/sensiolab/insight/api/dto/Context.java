/**
 * Copyright 2017 Djer13

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
limitations under the License.
 */
package org.djer.sensiolab.insight.api.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * @author djer
 *
 */
@XmlRootElement
public class Context {

	private Integer startLine;
	private Integer endLine;
	private String value;

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(64);
		builder.append("Context [startLine=").append(this.startLine).append(", endLine=").append(this.endLine)
				.append(", value=").append(this.value).append(']');
		return builder.toString();
	}

	/**
	 * @return the startLine
	 */
	@XmlAttribute(name = "start-line")
	public Integer getStartLine() {
		return this.startLine;
	}

	/**
	 * @param startLine
	 *            the startLine to set
	 */
	public void setStartLine(final Integer startLine) {
		this.startLine = startLine;
	}

	/**
	 * @return the endLine
	 */
	@XmlAttribute(name = "end-line")
	public Integer getEndLine() {
		return this.endLine;
	}

	/**
	 * @param endLine
	 *            the endLine to set
	 */
	public void setEndLine(final Integer endLine) {
		this.endLine = endLine;
	}

	/**
	 * @return the value
	 */
	@XmlValue
	public String getValue() {
		return this.value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(final String value) {
		this.value = value;
	}
}
