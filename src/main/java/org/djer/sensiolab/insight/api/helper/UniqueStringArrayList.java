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
package org.djer.sensiolab.insight.api.helper;

import java.util.ArrayList;

/**
 * @author djer
 *
 */
public class UniqueStringArrayList extends ArrayList<String> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6489741284978710082L;

	public UniqueStringArrayList(final String value) {
		super();
		super.add(value);
	}

	public UniqueStringArrayList(final Integer param) {
		this(Integer.toString(param));
	}

	public UniqueStringArrayList(final Boolean param) {
		this(Boolean.toString(param));
	}
}
