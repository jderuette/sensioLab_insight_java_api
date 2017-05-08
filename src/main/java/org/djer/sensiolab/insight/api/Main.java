/**
 *
 */
package org.djer.sensiolab.insight.api;

import org.djer.sensiolab.insight.api.dto.Analyses;
import org.djer.sensiolab.insight.api.dto.Analysis;
import org.djer.sensiolab.insight.api.dto.Project;
import org.djer.sensiolab.insight.api.dto.Projects;
import org.djer.sensiolab.insight.api.service.InsightClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * @author djer
 *
 */
// @SpringBootApplication(scanBasePackages = { "org.djer",
// "com.homeadvisor.robusto" })
@SpringBootConfiguration
@ComponentScan(excludeFilters = { @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class) })
public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	private static InsightClient client;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) {
		// SpringApplication.run(Main.class);

		// System.out.println("projects : " + getProjects());

		client = InsightClient.get();

		final String projectUuid = "6cd0e10b-8f44-42c3-a18f-b8708709f1b7";
		final String projectUuidTest = "8693de45-f030-4a4f-8920-aa1f31071798";
		LOGGER.info("project : " + projectUuid + " : " + getProject(projectUuid));
	}

	// @Bean
	// public CommandLineRunner run() throws Exception {
	// return args -> {
	// System.out.println("projects : " + getProjects());
	// };
	// }

	private static Projects getProjects() {
		return client.getProjects();
	}

	private static Project getProject(final String uuid) {
		return client.getProject(uuid);
	}

	private static Project updateProject(final String uuid) {
		final Project existingProject = client.getProject(uuid);
		existingProject.setDisableMonthly(false);
		existingProject.setRepositoryUrl("https://github.com/jderuette/BasicPhpProject.git");
		existingProject.setName("Basic public project for API tests");
		existingProject.setType(Project.Type.OTHER);

		return client.updateProject(existingProject);
	}

	private static Project createProject() {
		final Project project = new Project("Mautic_JDE_test", Project.Type.SYMFONY2_WEB_PROJECT,
				"https://github.com/jderuette/fakeNewSynfonyProject");
		return client.createProject(project);
	}

	private static Analyses getAnalyses(final String projectUuid) {
		return client.getAnalyses(projectUuid);
	}

	private static Analysis getAnalysis(final String projectUuid, final Integer analysisNumber) {
		return client.getAnalysis(projectUuid, analysisNumber);
	}

	private static Analysis getAnalysisStatus(final String projectUuid, final Integer analysisNumber) {
		return client.getAnalysisStatus(projectUuid, analysisNumber);
	}

	private static Analysis lauchAnalysis(final String projectUuid, final String reference) {
		return client.analyse(projectUuid, reference);
	}

}
