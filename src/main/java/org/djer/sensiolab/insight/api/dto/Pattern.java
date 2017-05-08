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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author djer
 *
 */
@XmlRootElement
public class Pattern {

	private String value;

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Patetrn [value=").append(this.value).append(']');
		return builder.toString();
	}

	/**
	 * @return the value
	 */
	@XmlElement(name = "pattern")
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
