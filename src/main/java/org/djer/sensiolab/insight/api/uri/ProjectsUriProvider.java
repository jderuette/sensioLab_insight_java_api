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

import org.djer.sensiolab.insight.api.dto.Projects;

import com.homeadvisor.robusto.RemoteServiceCallback;

/**
 * @author djer
 *
 */
public class ProjectsUriProvider extends AbstractPagedUriProvider<Projects> {

	public ProjectsUriProvider() {
		super(1);
	}

	public ProjectsUriProvider(final Integer pageNumber) {
		super(pageNumber);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.homeadvisor.robusto.UriProvider#execute(com.homeadvisor.robusto.
	 * RemoteServiceCallback)
	 */
	@Override
	public Projects execute(final RemoteServiceCallback<Projects> callback) {
		final String uri = super.builder.projects(super.getPageNumber());
		return callback.run(uri);
	}

}
