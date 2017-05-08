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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author djer
 *
 */
@XmlRootElement
public class Configuration {
	private List<Branch> ignoredBranches;
	private String preComposerScript;
	@SuppressWarnings("PMD.LongVariable")
	private String postComposerScript;
	private String workingDirectory;
	private Integer phpVersion;
	private String phpIni;
	@SuppressWarnings("PMD.LongVariable")
	private List<Condition> commitFailureConditions;
	@SuppressWarnings("PMD.LongVariable")
	private List<Directory> globalExcludedDirs;
	private List<Pattern> excludedPatterns;
	private Patterns patterns;
	private List<Rules> rules;
	private Parameters parameters;
	private String env;

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(512);
		builder.append("Configuration [ignoredBranches=").append(this.ignoredBranches).append(", preComposerScript=")
				.append(this.preComposerScript).append(", postComposerScript=").append(this.postComposerScript)
				.append(", workingDirectory=").append(this.workingDirectory).append(", phpVersion=")
				.append(this.phpVersion).append(", phpIni=").append(this.phpIni).append(", commitFailureConditions=")
				.append(this.commitFailureConditions).append(", globalExcludedDirs=").append(this.globalExcludedDirs)
				.append(", excludedPatterns=").append(this.excludedPatterns).append(", patterns=").append(this.patterns)
				.append(", rules=").append(this.rules).append(", parameters=").append(this.parameters).append(", env=")
				.append(this.env).append(']');
		return builder.toString();
	}

	/**
	 * @return the ignoredBranches
	 */
	@XmlElement(name = "ignored-branches")
	public List<Branch> getIgnoredBranches() {
		return this.ignoredBranches;
	}

	/**
	 * @param ignoredBranches
	 *            the ignoredBranches to set
	 */
	public void setIgnoredBranches(final List<Branch> ignoredBranches) {
		this.ignoredBranches = ignoredBranches;
	}

	/**
	 * @return the preComposerScript
	 */
	@XmlElement(name = "pre-composer-script")
	public String getPreComposerScript() {
		return this.preComposerScript;
	}

	/**
	 * @param preComposerScript
	 *            the preComposerScript to set
	 */
	public void setPreComposerScript(final String preComposerScript) {
		this.preComposerScript = preComposerScript;
	}

	/**
	 * @return the postComposerScript
	 */
	@XmlElement(name = "post-composer-script")
	public String getPostComposerScript() {
		return this.postComposerScript;
	}

	/**
	 * @param postComposerScript
	 *            the postComposerScript to set
	 */
	@SuppressWarnings("PMD.LongVariable")
	public void setPostComposerScript(final String postComposerScript) {
		this.postComposerScript = postComposerScript;
	}

	/**
	 * @return the workingDirectory
	 */
	@XmlElement(name = "working-directory")
	public String getWorkingDirectory() {
		return this.workingDirectory;
	}

	/**
	 * @param workingDirectory
	 *            the workingDirectory to set
	 */
	public void setWorkingDirectory(final String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	/**
	 * @return the phpVersion
	 */
	@XmlElement(name = "php-version")
	public Integer getPhpVersion() {
		return this.phpVersion;
	}

	/**
	 * @param phpVersion
	 *            the phpVersion to set
	 */
	public void setPhpVersion(final Integer phpVersion) {
		this.phpVersion = phpVersion;
	}

	/**
	 * @return the phpIni
	 */
	@XmlElement(name = "php-ini")
	public String getPhpIni() {
		return this.phpIni;
	}

	/**
	 * @param phpIni
	 *            the phpIni to set
	 */
	public void setPhpIni(final String phpIni) {
		this.phpIni = phpIni;
	}

	/**
	 * @return the commitFailureConditions
	 */
	@XmlElement(name = "commit-failure-conditions")
	public List<Condition> getCommitFailureConditions() {
		return this.commitFailureConditions;
	}

	/**
	 * @param commitFailureConditions
	 *            the commitFailureConditions to set
	 */
	@SuppressWarnings("PMD.LongVariable")
	public void setCommitFailureConditions(final List<Condition> commitFailureConditions) {
		this.commitFailureConditions = commitFailureConditions;
	}

	/**
	 * @return the globalExcludedDirs
	 */
	@XmlElement(name = "global-excluded-dirs")
	public List<Directory> getGlobalExcludedDirs() {
		return this.globalExcludedDirs;
	}

	/**
	 * @param globalExcludedDirs
	 *            the globalExcludedDirs to set
	 */
	@SuppressWarnings("PMD.LongVariable")
	public void setGlobalExcludedDirs(final List<Directory> globalExcludedDirs) {
		this.globalExcludedDirs = globalExcludedDirs;
	}

	/**
	 * @return the excludedPatterns
	 */
	@XmlElement(name = "excluded-patterns")
	public List<Pattern> getExcludedPatterns() {
		return this.excludedPatterns;
	}

	/**
	 * @param excludedPatterns
	 *            the excludedPatterns to set
	 */
	public void setExcludedPatterns(final List<Pattern> excludedPatterns) {
		this.excludedPatterns = excludedPatterns;
	}

	/**
	 * @return the patterns
	 */
	public Patterns getPatterns() {
		return this.patterns;
	}

	/**
	 * @param patterns
	 *            the patterns to set
	 */
	public void setPatterns(final Patterns patterns) {
		this.patterns = patterns;
	}

	/**
	 * @return the rules
	 */
	public List<Rules> getRules() {
		return this.rules;
	}

	/**
	 * @param rules
	 *            the rules to set
	 */
	public void setRules(final List<Rules> rules) {
		this.rules = rules;
	}

	/**
	 * @return the parameters
	 */
	public Parameters getParameters() {
		return this.parameters;
	}

	/**
	 * @param parameters
	 *            the parameters to set
	 */
	public void setParameters(final Parameters parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the env
	 */
	public String getEnv() {
		return this.env;
	}

	/**
	 * @param env
	 *            the env to set
	 */
	public void setEnv(final String env) {
		this.env = env;
	}

}
