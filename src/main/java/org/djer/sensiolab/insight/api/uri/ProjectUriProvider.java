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

import org.djer.sensiolab.insight.api.dto.Project;

import com.homeadvisor.robusto.RemoteServiceCallback;

/**
 * @author djer
 *
 */
public class ProjectUriProvider extends AbstractUriProvider<Project> {

	private final String uuid;

	public ProjectUriProvider() {
		super();
		this.uuid = null;
	}

	public ProjectUriProvider(final String projectId) {
		super();
		this.uuid = projectId;
	}

	public ProjectUriProvider(final Project project) {
		this(project.getUuid());
	}

	@Override
	public Project execute(final RemoteServiceCallback<Project> callback) {
		String uri;
		if (null == this.uuid) {
			// for project creation (POTS)
			uri = super.builder.projects();
		} else {
			uri = super.builder.project(this.uuid);
		}

		return callback.run(uri);
	}

}
