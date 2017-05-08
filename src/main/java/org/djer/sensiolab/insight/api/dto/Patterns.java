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

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author djer
 *
 */
@XmlRootElement
public class Patterns {

	private List<Pattern> file;
	private List<Pattern> php;
	private List<Pattern> twig;

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(64);
		builder.append("patterns [file=").append(this.file).append(", php=").append(this.php).append(", twig=")
				.append(this.twig).append(']');
		return builder.toString();
	}

	/**
	 * @return the file
	 */
	public List<Pattern> getFile() {
		return this.file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(final List<Pattern> file) {
		this.file = file;
	}

	/**
	 * @return the php
	 */
	public List<Pattern> getPhp() {
		return this.php;
	}

	/**
	 * @param php
	 *            the php to set
	 */
	public void setPhp(final List<Pattern> php) {
		this.php = php;
	}

	/**
	 * @return the twig
	 */
	public List<Pattern> getTwig() {
		return this.twig;
	}

	/**
	 * @param twig
	 *            the twig to set
	 */
	public void setTwig(final List<Pattern> twig) {
		this.twig = twig;
	}

}
