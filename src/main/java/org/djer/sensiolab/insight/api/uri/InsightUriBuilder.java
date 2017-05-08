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
package org.djer.sensiolab.insight.api.uri;

import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author djer
 *
 */
public class InsightUriBuilder extends UriComponentsBuilder {

	private static final String HTTP_URL = "https://insight.sensiolabs.com";
	private static final String SEGMENT_API = "api";

	private static final String SEGMENT_PROJECTS = "projects";

	private static final String SEGEMENT_ANALYSES = "analyses";
	private static final String SEGEMENT_STATUS = "status";
	/**
	 * Store the last Builded URI, to avoid redundant pathSegment when retry
	 * occurs
	 */
	protected String builtdUrl;

	public InsightUriBuilder() {
		super(fromHttpUrl(HTTP_URL));
		this.pathSegment(SEGMENT_API);
	}

	public String projects() {
		this.pathSegment(SEGMENT_PROJECTS);
		return this.getURL();
	}

	public String projects(final Integer pageNumber) {
		this.pathSegment(SEGMENT_PROJECTS);
		super.queryParam("page", pageNumber);
		return this.getURL();
	}

	public String project(final String projectUuid) {
		this.pathSegment(SEGMENT_PROJECTS);
		this.pathSegment(projectUuid);
		return this.getURL();
	}

	public String analyses(final String projectUuid) {
		return this.analyses(projectUuid, 1);
	}

	public String analyses(final String projectUuid, final Integer pageNumber) {
		this.pathSegment(SEGMENT_PROJECTS);
		this.pathSegment(projectUuid);
		this.pathSegment(SEGEMENT_ANALYSES);
		return this.getURL();
	}

	public String analysis(final String projectUuid, final Integer relativeNumber) {
		this.pathSegment(SEGMENT_PROJECTS);
		this.pathSegment(projectUuid);
		this.pathSegment(SEGEMENT_ANALYSES);
		this.pathSegment(Integer.toString(relativeNumber));
		return this.getURL();
	}

	public String analysisStatus(final String projectUuid, final Integer relativeNumber) {
		this.pathSegment(SEGMENT_PROJECTS);
		this.pathSegment(projectUuid);
		this.pathSegment(SEGEMENT_ANALYSES);
		this.pathSegment(Integer.toString(relativeNumber));
		this.pathSegment(SEGEMENT_STATUS);
		return this.getURL();
	}

	public String getURL() {
		if (null == this.builtdUrl) {
			this.builtdUrl = super.build().encode().toUri().toASCIIString();
		}
		return this.builtdUrl;
	}
}
