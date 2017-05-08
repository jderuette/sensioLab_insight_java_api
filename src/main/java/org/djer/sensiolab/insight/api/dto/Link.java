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

/**
 * @author djer
 *
 */
@XmlRootElement
@SuppressWarnings("PMD.ShortClassName")
public class Link {

	private String href;
	private String rel;
	private String type;

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(64);
		builder.append("Link [href=").append(this.href).append(", rel=").append(this.rel).append(", type=")
				.append(this.type).append(']');
		return builder.toString();
	}

	/**
	 * @return the href
	 */
	@XmlAttribute
	public String getHref() {
		return this.href;
	}

	/**
	 * @param href
	 *            the href to set
	 */
	public void setHref(final String href) {
		this.href = href;
	}

	/**
	 * @return the rel
	 */
	@XmlAttribute
	public String getRel() {
		return this.rel;
	}

	/**
	 * @param rel
	 *            the rel to set
	 */
	public void setRel(final String rel) {
		this.rel = rel;
	}

	/**
	 * @return the type
	 */
	@XmlAttribute
	public String getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(final String type) {
		this.type = type;
	}
}
