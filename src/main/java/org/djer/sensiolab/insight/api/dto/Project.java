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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.djer.sensiolab.insight.api.helper.AbstractMultiValueMapable;
import org.djer.sensiolab.insight.api.helper.UniqueStringArrayList;
import org.springframework.util.MultiValueMap;

/**
 * @author djer
 *
 */
@XmlRootElement
public class Project extends AbstractMultiValueMapable {

	private static final String DEFAULT_BRANCH = "master";
	@SuppressWarnings("PMD.LongVariable")
	private static final Boolean DEFAULT_DISABLE_MONTHLY = Boolean.FALSE;
	private static final Boolean DEFAULT_PUBLIC = Boolean.FALSE;

	private List<Link> links;
	private String uuid;
	private String name;
	private String configuration;
	private String description;
	private Integer type;
	private String repositoryUrl;
	private Boolean publicProject = DEFAULT_PUBLIC;
	private Boolean reportAvailable;
	private Analysis lastAnalysis;
	private Boolean disableMonthly = DEFAULT_DISABLE_MONTHLY;
	private String branch;

	public Project() {

	}

	public Project(final String name, final Project.Type type, final String repositoryUrl, final String description,
			final Boolean isPublic, final Boolean disableMonthly, final String branch) {
		super();
		this.name = name;
		this.description = description;
		this.type = type.getId();
		this.repositoryUrl = repositoryUrl;
		this.publicProject = isPublic;
		this.disableMonthly = disableMonthly;
		this.branch = branch;
	}

	public Project(final String name, final Project.Type type, final String repositoryUrl, final String description,
			final Boolean isPublic, final Boolean disableMonthly) {
		this(name, type, repositoryUrl, description, isPublic, disableMonthly, DEFAULT_BRANCH);
	}

	public Project(final String name, final Project.Type type, final String repositoryUrl, final String description,
			final Boolean isPublic) {
		this(name, type, repositoryUrl, description, isPublic, DEFAULT_DISABLE_MONTHLY, DEFAULT_BRANCH);
	}

	public Project(final String name, final Project.Type type, final String repositoryUrl, final String description) {
		this(name, type, repositoryUrl, description, DEFAULT_PUBLIC, DEFAULT_DISABLE_MONTHLY, DEFAULT_BRANCH);
	}

	public Project(final String name, final Project.Type type, final String repositoryUrl) {
		this(name, type, repositoryUrl, null, DEFAULT_PUBLIC, DEFAULT_DISABLE_MONTHLY, DEFAULT_BRANCH);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(200);
		builder.append("Project [links=").append(this.links).append(", uuid=").append(this.uuid).append(", name=")
				.append(this.name).append(", configuration=").append(this.configuration).append(", description=")
				.append(this.description).append(", type=").append(this.type).append(", repositoryUrl=")
				.append(this.repositoryUrl).append(", isPublic=").append(this.publicProject)
				.append(", reportAvailable=").append(this.reportAvailable).append(", lastAnalysis=")
				.append(this.lastAnalysis).append(", disableMonthly=").append(this.disableMonthly).append(", branch=")
				.append(this.branch).append(']');
		return builder.toString();
	}

	@Override
	public MultiValueMap<String, String> asMultiValueMap(final Boolean forUpdate) {
		final Map<String, List<String>> projectData = new HashMap<>();
		projectData.put("insight_project[name]", new UniqueStringArrayList(this.getName()));
		projectData.put("insight_project[repositoryUrl]", new UniqueStringArrayList(this.getRepositoryUrl()));
		projectData.put("insight_project[type]", new UniqueStringArrayList(this.getType()));

		projectData.put("insight_project[disableMonthly]", new UniqueStringArrayList(this.isDisableMonthly()));
		projectData.put("insight_project[branch]", new UniqueStringArrayList(this.getBranch()));

		if (forUpdate) {
			projectData.put("insight_project[configuration]", new UniqueStringArrayList(this.getConfiguration()));
			// projectData.put("insight_project[description]",
			// asList(this.getDescription()));
			projectData.put("insight_project[public]", new UniqueStringArrayList(this.isPublic()));
			// projectData.put("insight_project[keepCode]",
			// asList(this.isKeepCode()));
		}

		return this.toMultiValueMap(projectData);
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
	 * @return the uuid
	 */
	@XmlElement(name = "id")
	public String getUuid() {
		return this.uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(final String uuid) {
		this.uuid = uuid;
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

	/**
	 * @return the configuration
	 */
	public String getConfiguration() {
		return this.configuration;
	}

	/**
	 * @param configuration
	 *            the configuration to set
	 */
	public void setConfiguration(final String configuration) {
		this.configuration = configuration;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(final String description) {
		this.description = description;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return this.type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(final Integer type) {
		this.type = type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	@XmlTransient
	public void setType(final Project.Type type) {
		this.type = type.getId();
	}

	/**
	 * @return the repositoryUrl
	 */
	@XmlElement(name = "repository-url")
	public String getRepositoryUrl() {
		return this.repositoryUrl;
	}

	/**
	 * @param repositoryUrl
	 *            the repositoryUrl to set
	 */
	public void setRepositoryUrl(final String repositoryUrl) {
		this.repositoryUrl = repositoryUrl;
	}

	/**
	 * @return the isPublic
	 */
	@XmlElement(name = "public")
	public Boolean isPublic() {
		return this.publicProject;
	}

	/**
	 * @param isPublic
	 *            the isPublic to set
	 */
	public void setPublic(final Boolean isPublic) {
		this.publicProject = isPublic;
	}

	/**
	 * @return the isPrivate
	 */
	@XmlTransient
	public Boolean isPrivate() {
		return !this.publicProject;
	}

	/**
	 * @param isPrivate
	 *            the isPrivate to set
	 */
	public void isPrivate(final Boolean isPrivate) {
		this.publicProject = !isPrivate;
	}

	/**
	 * @return the reportAvailable
	 */
	@XmlElement(name = "report-available")
	public Boolean isReportAvailable() {
		return this.reportAvailable;
	}

	/**
	 * @param reportAvailable
	 *            the reportAvailable to set
	 */
	public void setReportAvailable(final Boolean reportAvailable) {
		this.reportAvailable = reportAvailable;
	}

	/**
	 * @return the lastAnalysis
	 */
	@XmlElement(name = "last-analysis")
	public Analysis getLastAnalysis() {
		return this.lastAnalysis;
	}

	/**
	 * @param lastAnalysis
	 *            the lastAnalysis to set
	 */
	public void setLastAnalysis(final Analysis lastAnalysis) {
		this.lastAnalysis = lastAnalysis;
	}

	/**
	 * @return the disableMonthly
	 */
	public Boolean isDisableMonthly() {
		return this.disableMonthly;
	}

	/**
	 * @param disableMonthly
	 *            the disableMonthly to set
	 */
	public void setDisableMonthly(final Boolean disableMonthly) {
		this.disableMonthly = disableMonthly;
	}

	/**
	 * @return the branch
	 */
	public String getBranch() {
		return this.branch;
	}

	/**
	 * @param branch
	 *            the branch to set
	 */
	public void setBranch(final String branch) {
		this.branch = branch;
	}

	public enum Type {
		PHP_WEBSITE(0, "Php website"), PHP_LIBRARY(1, "Php library"), SYMFONY2_BUNDLE(2,
				"Symfony2 bundle"), SYMFONY1_PLUGIN(4, "Symfony1 plugin"), OTHER(6, "Other"), DRUPAL_MODULE(7,
						"Drupal module"), LARAVAL_WEB_PROJECT(8, "Laravel web project"), SILEX_WEB_PROJECT(9,
								"Silex web project"), SYMFONY2_WEB_PROJECT(10,
										"Symfony2 web project"), TYPE_SYMFONY1_WEB_PROJECT(11, "Symfony1 web project");

		@SuppressWarnings("PMD.ShortVariable")
		private final Integer id;
		private final String name;

		@SuppressWarnings("PMD.ShortVariable")
		Type(final Integer id, final String name) {
			this.id = id;
			this.name = name;
		}

		/**
		 * @return the id
		 */
		public Integer getId() {
			return this.id;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return this.name;
		}
	}
}
