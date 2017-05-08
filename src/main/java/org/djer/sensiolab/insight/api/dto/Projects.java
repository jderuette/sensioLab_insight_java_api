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

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author djer
 *
 */
@XmlRootElement
public class Projects {

	private Integer page;
	private Integer total;
	private Integer limit;
	private List<Link> links;
	private List<Project> projectList;

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(100);
		builder.append("Projects [page=").append(this.page).append(", total=").append(this.total).append(", limit=")
				.append(this.limit).append(", links=").append(this.links).append(", projects=").append(this.projectList)
				.append(']');
		return builder.toString();
	}

	/**
	 * @return the page
	 */
	@XmlAttribute
	public Integer getPage() {
		return this.page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(final Integer page) {
		this.page = page;
	}

	/**
	 * @return the total
	 */
	@XmlAttribute
	public Integer getTotal() {
		return this.total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(final Integer total) {
		this.total = total;
	}

	/**
	 * @return the limit
	 */
	@XmlAttribute
	public Integer getLimit() {
		return this.limit;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(final Integer limit) {
		this.limit = limit;
	}

	/**
	 * @return the links
	 */
	@XmlElement(name = "link")
	public List<Link> getLinks() {
		return this.links;
	}

	/**
	 * @param links
	 *            the links to set
	 */
	public void setLinks(final List<Link> links) {
		this.links = links;
	}

	/**
	 * @return the projects
	 */
	@XmlElement(name = "project")
	public List<Project> getProjects() {
		return this.projectList;
	}

	/**
	 * @param projects
	 *            the projects to set
	 */
	public void setProjects(final List<Project> projects) {
		this.projectList = projects;
	}

}
