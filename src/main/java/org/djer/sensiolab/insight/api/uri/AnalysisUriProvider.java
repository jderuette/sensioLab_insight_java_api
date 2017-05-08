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

import org.djer.sensiolab.insight.api.dto.Analysis;
import org.djer.sensiolab.insight.api.dto.Project;

import com.homeadvisor.robusto.RemoteServiceCallback;

/**
 * @author djer
 *
 */
public class AnalysisUriProvider extends AbstractUriProvider<Analysis> {

	private final String projectUuid;
	private final Integer relativeNumber;
	private final Boolean status;

	public AnalysisUriProvider(final Project project) {
		this(project.getUuid());
	}

	public AnalysisUriProvider(final String projectId) {
		this(projectId, null);
	}

	public AnalysisUriProvider(final Project project, final Integer relativeNumber) {
		this(project.getUuid(), relativeNumber);
	}

	public AnalysisUriProvider(final String projectId, final Integer relativeNumber) {
		this(projectId, relativeNumber, Boolean.FALSE);
	}

	public AnalysisUriProvider(final String projectId, final Integer relativeNumber, final Boolean status) {
		super();
		this.projectUuid = projectId;
		this.relativeNumber = relativeNumber;
		this.status = status;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.homeadvisor.robusto.UriProvider#execute(com.homeadvisor.robusto.
	 * RemoteServiceCallback)
	 */
	@Override
	public Analysis execute(final RemoteServiceCallback<Analysis> callback) {
		String uri;
		if (null == this.relativeNumber) {
			// for Analysis creation (launch : POST)
			uri = super.builder.analyses(this.projectUuid);
		} else {
			if (this.status) {
				uri = super.builder.analysisStatus(this.projectUuid, this.relativeNumber);
			} else {
				uri = super.builder.analysis(this.projectUuid, this.relativeNumber);
			}
		}

		return callback.run(uri);
	}

}
