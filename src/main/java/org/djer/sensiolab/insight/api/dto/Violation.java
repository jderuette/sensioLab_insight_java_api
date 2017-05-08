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
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author djer
 *
 */
@XmlRootElement
public class Violation {

	private String title;
	private String message;
	private String resource;
	private Integer line;
	private String severity;
	private String category;
	private Boolean ignored;
	private Context context;
	private Link link;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * @return the resource
	 */
	public String getResource() {
		return this.resource;
	}

	/**
	 * @param resource
	 *            the resource to set
	 */
	public void setResource(final String resource) {
		this.resource = resource;
	}

	/**
	 * @return the line
	 */
	public Integer getLine() {
		return this.line;
	}

	/**
	 * @param line
	 *            the line to set
	 */
	public void setLine(final Integer line) {
		this.line = line;
	}

	/**
	 * @return the severity
	 */
	@XmlAttribute
	public String getSeverity() {
		return this.severity;
	}

	/**
	 * @param severity
	 *            the severity to set
	 */
	public void setSeverity(final String severity) {
		this.severity = severity;
	}

	/**
	 * @param severity
	 *            the severity to set
	 */
	@XmlTransient
	public void setSeverity(final Violation.Severity severity) {
		this.severity = severity.getName();
	}

	/**
	 * @return the category
	 */
	@XmlAttribute
	public String getCategory() {
		return this.category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(final String category) {
		this.category = category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	@XmlTransient
	public void setCategory(final Violation.Category category) {
		this.category = category.getName();
	}

	/**
	 * @return the ignored
	 */
	@XmlAttribute
	public Boolean isIgnored() {
		return this.ignored;
	}

	/**
	 * @param ignored
	 *            the ignored to set
	 */
	public void setIgnored(final Boolean ignored) {
		this.ignored = ignored;
	}

	/**
	 * @return the context
	 */
	public Context getContext() {
		return this.context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(final Context context) {
		this.context = context;
	}

	/**
	 * @return the link
	 */
	public Link getLink() {
		return this.link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(final Link link) {
		this.link = link;
	}

	public enum Severity {
		INFO("info"), MINOR("minor"), MAJOR("major"), CRITICAL("critical");

		private String name;

		Severity(final String name) {
			this.name = name;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(final String name) {
			this.name = name;
		}
	}

	public enum Category {

		ARCHITECTURE("architecture"), BUGRISK("bugrisk"), CODESTYLE("codestyle"), DEADCODE("deadcode"), PERFORMANCE(
				"performance"), READABILITY("readability"), SECURITY("security");

		private String name;

		Category(final String name) {
			this.name = name;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return this.name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(final String name) {
			this.name = name;
		}
	}

}
