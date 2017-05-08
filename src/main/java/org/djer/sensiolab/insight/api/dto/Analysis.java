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
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.joda.time.DateTime;

/**
 * @author djer
 *
 */
@XmlRootElement
@SuppressWarnings({ "PMD.TooManyFields", "PMD.ExcessivePublicCount" })
public class Analysis {

	private List<Link> links;
	private Integer number;
	private String grade;
	private String nextGrade;
	private List<String> grades;
	private Float remediationCost;
	private Float remediationCostNextGrade;
	private Integer nbViolations;
	private DateTime beginAt;
	private DateTime endAt;
	private Integer duration;
	private String failureMessage;
	private String failureCode;
	private Boolean failed;
	private String status;
	private String statusMessage;
	private Boolean altered;
	private List<Violation> violations;
	private Configuration configuration;
	private Integer nbViolationsNew;
	private Integer nbViolationsExisting;
	private Integer nbViolationsFixed;
	private Integer nbViolationsIgnored;
	private String launchedBy;

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(500);
		builder.append("Analysis [links=").append(this.links).append(", number=").append(this.number).append(", grade=")
				.append(this.grade).append(", nextGrade=").append(this.nextGrade).append(", grades=")
				.append(this.grades).append(", remediationCost=").append(this.remediationCost)
				.append(", remediationCostForNextGrade=").append(this.remediationCostNextGrade)
				.append(", nbViolations=").append(this.nbViolations).append(", beginAt=").append(this.beginAt)
				.append(", endAt=").append(this.endAt).append(", duration=").append(this.duration)
				.append(", failureMessage=").append(this.failureMessage).append(", failureCode=")
				.append(this.failureCode).append(", failed=").append(this.failed).append(", status=")
				.append(this.status).append(", statusMessage=").append(this.statusMessage).append(", altered=")
				.append(this.altered).append(", violations=").append(this.violations).append(", configuration=")
				.append(this.configuration).append(", nbViolationsNew=").append(this.nbViolationsNew)
				.append(", nbViolationsExisting=").append(this.nbViolationsExisting).append(", nbViolationsFixed=")
				.append(this.nbViolationsFixed).append(", nbViolationsIgnored=").append(this.nbViolationsIgnored)
				.append(", launchedBy=").append(this.launchedBy).append(']');
		return builder.toString();
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
	 * @return the number
	 */
	public Integer getNumber() {
		return this.number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(final Integer number) {
		this.number = number;
	}

	/**
	 * @return the grade
	 */
	public String getGrade() {
		return this.grade;
	}

	/**
	 * @param grade
	 *            the grade to set
	 */
	public void setGrade(final String grade) {
		this.grade = grade;
	}

	/**
	 * @return the nextGrade
	 */
	@XmlElement(name = "next-grade")
	public String getNextGrade() {
		return this.nextGrade;
	}

	/**
	 * @param nextGrade
	 *            the nextGrade to set
	 */
	public void setNextGrade(final String nextGrade) {
		this.nextGrade = nextGrade;
	}

	/**
	 * @return the grades
	 */
	@XmlTransient
	public List<String> getGrades() {
		return this.grades;
	}

	/**
	 * @param grades
	 *            the grades to set
	 */
	public void setGrades(final List<String> grades) {
		this.grades = grades;
	}

	/**
	 * @return the remediationCost
	 */
	@XmlElement(name = "remediation-cost")
	public Float getRemediationCost() {
		return this.remediationCost;
	}

	/**
	 * @param remediationCost
	 *            the remediationCost to set
	 */
	public void setRemediationCost(final Float remediationCost) {
		this.remediationCost = remediationCost;
	}

	/**
	 * @return the remediationCostForNextGrade
	 */
	@XmlElement(name = "remediation-cost-for-next-grade")
	public Float getRemediationCostForNextGrade() {
		return this.remediationCostNextGrade;
	}

	/**
	 * @param remediationCostForNextGrade
	 *            the remediationCostForNextGrade to set
	 */
	public void setRemediationCostForNextGrade(final Float remediationCostForNextGrade) {
		this.remediationCostNextGrade = remediationCostForNextGrade;
	}

	/**
	 * @return the nbViolations
	 */
	@XmlElement(name = "nb-violations")
	public Integer getNbViolations() {
		return this.nbViolations;
	}

	/**
	 * @param nbViolations
	 *            the nbViolations to set
	 */
	public void setNbViolations(final Integer nbViolations) {
		this.nbViolations = nbViolations;
	}

	/**
	 * @return the beginAt
	 */
	@XmlElement(name = "begin-at")
	public DateTime getBeginAt() {
		return this.beginAt;
	}

	/**
	 * @param beginAt
	 *            the beginAt to set
	 */
	public void setBeginAt(final DateTime beginAt) {
		this.beginAt = beginAt;
	}

	/**
	 * @return the endAt
	 */
	@XmlElement(name = "end-at")
	public DateTime getEndAt() {
		return this.endAt;
	}

	/**
	 * @param endAt
	 *            the endAt to set
	 */
	public void setEndAt(final DateTime endAt) {
		this.endAt = endAt;
	}

	/**
	 * @return the duration
	 */
	public Integer getDuration() {
		return this.duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(final Integer duration) {
		this.duration = duration;
	}

	/**
	 * @return the failureMessage
	 */
	@XmlElement(name = "failure-message")
	public String getFailureMessage() {
		return this.failureMessage;
	}

	/**
	 * @param failureMessage
	 *            the failureMessage to set
	 */
	public void setFailureMessage(final String failureMessage) {
		this.failureMessage = failureMessage;
	}

	/**
	 * @return the failureCode
	 */
	@XmlElement(name = "failure-code")
	public String getFailureCode() {
		return this.failureCode;
	}

	/**
	 * @param failureCode
	 *            the failureCode to set
	 */
	public void setFailureCode(final String failureCode) {
		this.failureCode = failureCode;
	}

	/**
	 * @return the failed
	 */
	@XmlElement(name = "failed")
	public Boolean getFailed() {
		return this.failed;
	}

	/**
	 * @param failed
	 *            the failed to set
	 */
	public void setFailed(final Boolean failed) {
		this.failed = failed;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(final String status) {
		this.status = status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	@XmlTransient
	public void setStatus(final Analysis.Status status) {
		this.status = status.getName();
	}

	/**
	 * @return the statusMessage
	 */
	@XmlElement(name = "status-message")
	public String getStatusMessage() {
		return this.statusMessage;
	}

	/**
	 * @param statusMessage
	 *            the statusMessage to set
	 */
	public void setStatusMessage(final String statusMessage) {
		this.statusMessage = statusMessage;
	}

	/**
	 * @return the isAltered
	 */
	@XmlElement(name = "altered")
	public Boolean isAltered() {
		return this.altered;
	}

	/**
	 * @param isAltered
	 *            the isAltered to set
	 */
	public void setaltered(final Boolean altered) {
		this.altered = altered;
	}

	/**
	 * @return the violations
	 */
	@XmlElementWrapper(name = "violations")
	@XmlElement(name = "violation")
	public List<Violation> getViolations() {
		return this.violations;
	}

	/**
	 * @param violations
	 *            the violations to set
	 */
	public void setViolations(final List<Violation> violations) {
		this.violations = violations;
	}

	/**
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		return this.configuration;
	}

	/**
	 * @param configuration
	 *            the configuration to set
	 */
	public void setConfiguration(final Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * @return the nbViolationsNew
	 */
	@XmlElement(name = "nb-violations-new")
	public Integer getNbViolationsNew() {
		return this.nbViolationsNew;
	}

	/**
	 * @param nbViolationsNew
	 *            the nbViolationsNew to set
	 */
	public void setNbViolationsNew(final Integer nbViolationsNew) {
		this.nbViolationsNew = nbViolationsNew;
	}

	/**
	 * @return the nbViolationsExisting
	 */
	@XmlElement(name = "nb-violations-existing")
	public Integer getNbViolationsExisting() {
		return this.nbViolationsExisting;
	}

	/**
	 * @param nbViolationsExisting
	 *            the nbViolationsExisting to set
	 */
	public void setNbViolationsExisting(final Integer nbViolationsExisting) {
		this.nbViolationsExisting = nbViolationsExisting;
	}

	/**
	 * @return the nbViolationsFixed
	 */
	@XmlElement(name = "nb-violations-fixed")
	public Integer getNbViolationsFixed() {
		return this.nbViolationsFixed;
	}

	/**
	 * @param nbViolationsFixed
	 *            the nbViolationsFixed to set
	 */
	public void setNbViolationsFixed(final Integer nbViolationsFixed) {
		this.nbViolationsFixed = nbViolationsFixed;
	}

	/**
	 * @return the nbViolationsIgnored
	 */
	@XmlElement(name = "nb-violations-ignored")
	public Integer getNbViolationsIgnored() {
		return this.nbViolationsIgnored;
	}

	/**
	 * @param nbViolationsIgnored
	 *            the nbViolationsIgnored to set
	 */
	public void setNbViolationsIgnored(final Integer nbViolationsIgnored) {
		this.nbViolationsIgnored = nbViolationsIgnored;
	}

	/**
	 * @return the launchedBy
	 */
	@XmlElement(name = "launched-by")
	public String getLaunchedBy() {
		return this.launchedBy;
	}

	/**
	 * @param launchedBy
	 *            the launchedBy to set
	 */
	public void setLaunchedBy(final String launchedBy) {
		this.launchedBy = launchedBy;
	}

	public enum Status {
		ORDERED("ordered"), RUNNING("running"), MEASURED("measured"), ANALYZED("analyzed"), FINISHED("finished");

		private String name;

		Status(final String name) {
			this.name = name;
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

	}

}
