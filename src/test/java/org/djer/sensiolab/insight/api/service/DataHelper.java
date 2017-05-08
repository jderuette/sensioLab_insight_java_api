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
package org.djer.sensiolab.insight.api.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

/**
 * @author djer
 *
 */
public class DataHelper {

	public String loadXmlFile(final String fileName) throws IOException {
		final InputStream file = this.getFile(fileName);
		return IOUtils.toString(file, "UTF-8");
	}

	private InputStream getFile(final String fileName) {
		final Class<?> resourceLoader = this.getClass();
		final InputStream file = resourceLoader.getResourceAsStream(fileName);
		return file;
	}

}
