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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;

import org.djer.sensiolab.insight.api.dto.Analyses;
import org.djer.sensiolab.insight.api.dto.Analysis;
import org.djer.sensiolab.insight.api.dto.Context;
import org.djer.sensiolab.insight.api.dto.Link;
import org.djer.sensiolab.insight.api.dto.Project;
import org.djer.sensiolab.insight.api.dto.Projects;
import org.djer.sensiolab.insight.api.dto.Violation;
import org.djer.sensiolab.insight.api.uri.InsightUriBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.homeadvisor.robusto.NonRetryableApiCommandException;

/**
 * @author djer
 *
 */
@TestPropertySource("/test.properties")
@SuppressWarnings({ "PMD.MethodNamingConventions", "PMD.TooManyMethods", "PMD.TooManyStaticImports",
		"PMD.LawOfDemeter" })
public class InsightClientTest {

	private static final String UUID_MOCKED = "UuidNotMatterMocked";
	private static InsightClient defaultClient;

	@BeforeClass
	public static void init() {
		defaultClient = InsightClient.get("UnitTest", "NoPassForUnitTest");
	}

	@Test
	@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
	public void testGetProjects() throws IOException {

		final InsightUriBuilder builder = new InsightUriBuilder();
		final MockRestServiceServer mockServer = this.mockInsightSuccessRequest("GetProjects", builder.projects(1),
				"/data/OneProjectWithAnalysis.xml");

		final Projects projects = defaultClient.getProjects();

		mockServer.verify();

		assertThat("No projects", projects, notNullValue());
		assertThat("Invalid projects size", projects.getProjects().size(), equalTo(1));
		assertThat("Invalid page number", projects.getPage(), equalTo(1));
		assertThat("Invalid total number", projects.getTotal(), equalTo(1));
		assertThat("Invalid limit number", projects.getLimit(), equalTo(10));
		assertThat("Invalid links size", projects.getLinks().size(), equalTo(1));
		final Link selfLink = projects.getLinks().get(0);
		assertThat("Invalid link Rel", selfLink.getRel(), equalTo("self"));
		assertThat("Invalid link type", selfLink.getType(), equalTo("application/vnd.com.sensiolabs.insight+xml"));
		assertThat("Invalid link Href", selfLink.getHref(),
				equalTo("https://insight.sensiolabs.com/api/projects?page=1"));
	}

	@Test
	@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
	public void testGetProject() throws IOException {
		final InsightUriBuilder builder = new InsightUriBuilder();

		final MockRestServiceServer mockServer = this.mockInsightSuccessRequest("GetProject",
				builder.project(UUID_MOCKED), "/data/Project_djer_mautic.xml");

		final Project project = defaultClient.getProject(UUID_MOCKED);

		mockServer.verify();

		assertThat("No project", project, notNullValue());

		assertThat("Invalid links size", project.getLinks().size(), equalTo(3));
		assertThat("Uuid must not be null", project.getUuid(), notNullValue());
		assertThat("Name must not be null", project.getName(), notNullValue());
		assertThat("Configuration mshould not be null", project.getConfiguration(), notNullValue());
		assertThat("Description should be null", project.getDescription(), nullValue());
		assertThat("Invalid project type", project.getType(), equalTo(Project.Type.SYMFONY2_WEB_PROJECT.getId()));
		assertThat("Invalid repository url", project.getRepositoryUrl(), notNullValue());
		assertThat("Invalid project visibility (public)", project.isPublic(), equalTo(Boolean.TRUE));
		assertThat("Invalid project visibility (private)", project.isPrivate(), equalTo(Boolean.FALSE));
		assertThat("Invalid project report aviability", project.isReportAvailable(), equalTo(Boolean.TRUE));

		// basic analysis check, full check in dedicated test
		assertThat("No Last analysiss", project.getLastAnalysis(), notNullValue());
		assertThat("No Violations in last analysis", project.getLastAnalysis().getViolations(), notNullValue());
		assertThat("Invalid number of vilation (total)", project.getLastAnalysis().getNbViolations(), equalTo(1499));
		// more than 1 000 violation, details dosen't contains all violations.
		assertThat("Invalid number of violation (from violations list)",
				project.getLastAnalysis().getViolations().size(), equalTo(994));

	}

	@Test(expected = NonRetryableApiCommandException.class)
	public void testCreateProject_invalidData() throws IOException {
		final InsightUriBuilder builder = new InsightUriBuilder();

		final MockRestServiceServer mockServer = this.mockInsightSuccessRequest("CreateProject",
				builder.project(UUID_MOCKED), "/data/createProjectBadRequest.xml");

		defaultClient.getProject(UUID_MOCKED);

		mockServer.verify();

		assertThat("Should have an exception from API", null, nullValue());
	}

	@Test(expected = NonRetryableApiCommandException.class)
	public void testUpdateProject() throws IOException {
		final InsightUriBuilder builder = new InsightUriBuilder();

		final MockRestServiceServer mockServer = this.mockInsightSuccessRequest("UpdateProject",
				builder.project(UUID_MOCKED), "/data/updateProjectBadRequest.xml");

		defaultClient.getProject(UUID_MOCKED);

		mockServer.verify();

		assertThat("Should have an exception from API", null, nullValue());
	}

	@Test
	public void testCreateProject() throws IOException {
		final InsightUriBuilder builder = new InsightUriBuilder();

		final MockRestServiceServer mockServer = this.mockInsightSuccessRequest("CreateProject",
				builder.project(UUID_MOCKED), "/data/createProject.xml");

		final Project createdProject = defaultClient.getProject(UUID_MOCKED);

		mockServer.verify();

		assertThat("No project created", createdProject, notNullValue());
	}

	@Test(expected = NonRetryableApiCommandException.class)
	public void testUpdate_missingKeepCode() throws IOException {
		final InsightUriBuilder builder = new InsightUriBuilder();
		final MockRestServiceServer mockServer = this.mockInsightSuccessRequest("UpdateProject", builder.projects(),
				"/data/UpdateProjectInvalidMissingKeepCode.xml");

		defaultClient.updateProject(
				new Project("FakeForUTs", Project.Type.OTHER, "https://fake.provider.com/repo/fakeForUts"));

		mockServer.verify();

		assertThat("Should have an exception from API", null, nullValue());
	}

	@Test
	@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
	public void testGetAnalyses() throws IOException {
		final InsightUriBuilder builder = new InsightUriBuilder();

		final MockRestServiceServer mockServer = this.mockInsightSuccessRequest("GetAnalyses",
				builder.analyses(UUID_MOCKED), "/data/ProjectAnalyses.xml");

		final Analyses analyses = defaultClient.getAnalyses(UUID_MOCKED);

		mockServer.verify();

		assertThat("No analyses", analyses, notNullValue());

		// Only One analysis with details. Other are "resources".
		assertThat("Invalid number of analyses", analyses.getAnalyses().size(), equalTo(1));
		assertThat("Invalid number of analyses (from total)", analyses.getTotal(), equalTo(8));
		assertThat("Invalid limit", analyses.getLimit(), notNullValue());
		assertThat("Invalid number of pages", analyses.getPage(), notNullValue());
	}

	@Test
	@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
	public void testGetAnalysis() throws IOException {
		final InsightUriBuilder builder = new InsightUriBuilder();
		final Integer analysisNumber = 7;

		final MockRestServiceServer mockServer = this.mockInsightSuccessRequest("GetAnalysis",
				builder.analysis(UUID_MOCKED, analysisNumber), "/data/Analysis.xml");

		final Analysis analysis = defaultClient.getAnalysis(UUID_MOCKED, analysisNumber);

		mockServer.verify();

		assertThat("No analyses", analysis, notNullValue());
		// Links controls
		assertThat("No links", analysis.getLinks(), notNullValue());
		assertThat("Invalid Link count", analysis.getLinks().size(), equalTo(4));
		final Link selfLink = analysis.getLinks().get(0);
		assertThat("Invalid sefl link href", selfLink.getHref(),
				equalTo("https://insight.sensiolabs.com/api/projects/6cd0e10b-8f44-42c3-a18f-b8708709f1b7/analyses/7"));
		assertThat("Invalid self link rel", selfLink.getRel(), equalTo("self"));
		assertThat("Invalid self link type", selfLink.getType(), equalTo("application/vnd.com.sensiolabs.insight+xml"));

		assertThat("Invalid analysis status", analysis.getStatus(), equalTo(Analysis.Status.FINISHED.getName()));

		// Violation controls
		assertThat("No violations in analysis", analysis.getViolations(), notNullValue());

		// Check the first violation structure and data
		final Violation firstViolation = analysis.getViolations().get(0);
		assertThat("Invalid violation's severity", firstViolation.getSeverity(),
				equalTo(Violation.Severity.MINOR.getName()));
		assertThat("Invalid violation's catagory", firstViolation.getCategory(),
				equalTo(Violation.Category.BUGRISK.getName()));
		assertThat("Invalid violation's ignored", firstViolation.isIgnored(), equalTo(Boolean.FALSE));
		assertThat("Invalid violation's line number", firstViolation.getLine(), equalTo(0));
		assertThat("Invalid violation's resource", firstViolation.getResource(), nullValue());
		assertThat("Invalid violation title", firstViolation.getTitle(),
				equalTo("Version of dependencies should be fixed"));
		assertThat("Invalid violation message", firstViolation.getMessage(),
				equalTo("Package willdurand/oauth-server-bundle#dev-master is not fixed."));
		// ViolationContext controls
		assertThat("No violation's context", firstViolation.getContext(), notNullValue());
		final Context firstViolationContext = firstViolation.getContext();
		assertThat("Invalid violation's context start line", firstViolationContext.getStartLine(), nullValue());
		assertThat("Invalid violation's context end line", firstViolationContext.getEndLine(), nullValue());
		assertThat("Invalid violation's context value", firstViolationContext.getValue(), equalTo(""));
		// Violation Link controls
		assertThat("No violation Link", firstViolation.getLink(), notNullValue());
		final Link firstViolationLink = firstViolation.getLink();
		assertThat("Invalid violation's Link href", firstViolationLink.getHref(), equalTo(
				"https://insight.sensiolabs.com/projects/6cd0e10b-8f44-42c3-a18f-b8708709f1b7/analyses/7?status=existing#516819558"));
		assertThat("Invalid violation's Link rel", firstViolationLink.getRel(), equalTo("self"));
		assertThat("Invalid violation's Link type", firstViolationLink.getType(), equalTo("text/html"));

		// Check the second violation structure and data
		final Violation secondViolation = analysis.getViolations().get(1);
		assertThat("Invalid violation's severity", secondViolation.getSeverity(),
				equalTo(Violation.Severity.CRITICAL.getName()));
		assertThat("Invalid violation's catagory", secondViolation.getCategory(),
				equalTo(Violation.Category.SECURITY.getName()));
		assertThat("Invalid violation's ignored", secondViolation.isIgnored(), equalTo(Boolean.TRUE));
		assertThat("Invalid violation's line number", secondViolation.getLine(), equalTo(229));
		assertThat("Invalid violation's resource", secondViolation.getResource(),
				equalTo("app/bundles/EmailBundle/Helper/MessageHelper.php"));
		assertThat("Invalid violation title", secondViolation.getTitle(),
				equalTo("Database queries should use parameter binding"));
		assertThat("Invalid violation message", secondViolation.getMessage(), equalTo(
				"If provided by the user, the value of $leadEmail may allow an SQL injection attack. Avoid concatenating parameters to SQL query strings, and use parameter binding instead."));
		// ViolationContext controls
		assertThat("No violation's context", secondViolation.getContext(), notNullValue());
		final Context secondViolationContext = secondViolation.getContext();
		assertThat("Invalid violation's context start line", secondViolationContext.getStartLine(), equalTo(224));
		assertThat("Invalid violation's context end line", secondViolationContext.getEndLine(), equalTo(234));
		assertThat("Invalid violation's context value", secondViolationContext.getValue(), notNullValue());
		// Violation Link controls
		assertThat("No violation Link", secondViolation.getLink(), notNullValue());
		final Link secondViolationLink = secondViolation.getLink();
		assertThat("Invalid violation's Link href", secondViolationLink.getHref(), equalTo(
				"https://insight.sensiolabs.com/projects/6cd0e10b-8f44-42c3-a18f-b8708709f1b7/analyses/7?status=ignored#516819008"));
		assertThat("Invalid violation's Link rel", secondViolationLink.getRel(), equalTo("self"));
		assertThat("Invalid violation's Link type", secondViolationLink.getType(), equalTo("text/html"));

	}

	@Test
	@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
	public void testGetAnalysisStatus_measured() throws IOException {
		final InsightUriBuilder builder = new InsightUriBuilder();
		final Integer analysisNumber = 9;

		final MockRestServiceServer mockServer = this.mockInsightSuccessRequest("GetAnalysis",
				builder.analysisStatus(UUID_MOCKED, analysisNumber), "/data/AnalysisMeasured.xml");

		final Analysis analysis = defaultClient.getAnalysisStatus(UUID_MOCKED, analysisNumber);

		mockServer.verify();

		assertThat("No analysis", analysis, notNullValue());
		assertThat("Invalid analysis status", analysis.getStatus(), equalTo(Analysis.Status.MEASURED.getName()));
		assertThat("Invalid end of analysis", analysis.getEndAt(), nullValue());
		assertThat("Invalid number o vilations", analysis.getNbViolations(), equalTo(0));
		assertThat("Invalid number of existing violations", analysis.getNbViolationsExisting(), equalTo(0));
		assertThat("Invalid number of fixed violations", analysis.getNbViolationsFixed(), equalTo(0));
		assertThat("Invalid number of ignored violations", analysis.getNbViolationsIgnored(), equalTo(0));
		assertThat("invalid number of new violations", analysis.getNbViolationsNew(), equalTo(0));

	}

	@Test
	public void testGetAnalysisStatus() throws IOException {
		final InsightUriBuilder builder = new InsightUriBuilder();
		final Integer analysisNumber = 8;

		final MockRestServiceServer mockServer = this.mockInsightSuccessRequest("GetAnalysisStatus",
				builder.analysisStatus(UUID_MOCKED, analysisNumber), "/data/AnalysisStatus.xml");

		final Analysis analysis = defaultClient.getAnalysisStatus(UUID_MOCKED, analysisNumber);

		mockServer.verify();

		assertThat("No analysis", analysis, notNullValue());

	}

	@Test
	@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
	public void testAnalyse() throws IOException {
		final InsightUriBuilder builder = new InsightUriBuilder();

		final MockRestServiceServer mockServer = this.mockInsightSuccessRequest("Analyse",
				builder.analyses(UUID_MOCKED), "/data/LaunchAnalysis.xml");

		final Analysis analysis = defaultClient.analyse(UUID_MOCKED);

		mockServer.verify();

		assertThat("No analysis", analysis, notNullValue());
		assertThat("Invali analysis status", analysis.getStatus(), equalTo(Analysis.Status.ORDERED.getName()));
		assertThat("Invalid end of analysis", analysis.getEndAt(), nullValue());
		assertThat("Invalid number of violations", analysis.getNbViolations(), equalTo(0));
		assertThat("Invalid number of existing violations", analysis.getNbViolationsExisting(), equalTo(0));
		assertThat("Invalid number of fixed violations", analysis.getNbViolationsFixed(), equalTo(0));
		assertThat("Invalid number of ignored violations", analysis.getNbViolationsIgnored(), equalTo(0));
		assertThat("Invalid number of new violations", analysis.getNbViolationsNew(), equalTo(0));

	}

	private MockRestServiceServer buildMockServer(final String commandName) {
		final RestTemplate restTemplate = defaultClient.getRestTemplate(commandName);

		final MockRestServiceServer mockServer = MockRestServiceServer.bindTo(restTemplate).build();

		return mockServer;
	}

	private MockRestServiceServer mockInsightSuccessRequest(final String commandName, final String uri,
			final String fileName) throws IOException {

		final MockRestServiceServer mockServer = this.buildMockServer(commandName);
		mockServer.reset();
		mockServer.expect(requestTo(uri)).andRespond(withSuccess(new DataHelper().loadXmlFile(fileName),
				MediaType.valueOf("application/vnd.com.sensiolabs.insight+xml")));

		return mockServer;
	}

}
