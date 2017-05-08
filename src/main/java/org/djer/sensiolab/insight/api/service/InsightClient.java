/**
 *
 */
package org.djer.sensiolab.insight.api.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.djer.sensiolab.insight.api.dto.Analyses;
import org.djer.sensiolab.insight.api.dto.Analysis;
import org.djer.sensiolab.insight.api.dto.Project;
import org.djer.sensiolab.insight.api.dto.Projects;
import org.djer.sensiolab.insight.api.helper.MultiValueMapable;
import org.djer.sensiolab.insight.api.uri.AnalysesUriProvider;
import org.djer.sensiolab.insight.api.uri.AnalysisUriProvider;
import org.djer.sensiolab.insight.api.uri.ProjectUriProvider;
import org.djer.sensiolab.insight.api.uri.ProjectsUriProvider;
import org.djer.sensiolab.insight.interceptor.SensioLabInsightBasicAuthInterceptor;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.StandardServletEnvironment;

import com.homeadvisor.robusto.ApiCommand;
import com.homeadvisor.robusto.NonRetryableApiCommandException;
import com.homeadvisor.robusto.spring.SpringClientConfiguration;
import com.homeadvisor.robusto.spring.SpringInstanceCallback;
import com.homeadvisor.robusto.spring.SpringRestClient;
import com.homeadvisor.robusto.spring.interceptor.AcceptHeaderInterceptor;
import com.homeadvisor.robusto.spring.interceptor.RequestResponseLogInterceptor;
import com.homeadvisor.robusto.spring.interceptor.ResponseTimeInterceptor;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.Setter;

/**
 * @author djer
 *
 */
@Service
@SuppressWarnings("PMD.LawOfDemeter")
public class InsightClient extends SpringRestClient {

	private static final String CONFIG_PREFIX = "insight";

	private String user;
	private String password;

	private static Map<String, InsightClient> cachedInstances = new HashMap<>();

	/**
	 * Provide a configured Insight Client. Cache client by User
	 *
	 * @param user
	 *            SesioLab Insight user (see
	 *            https://insight.sensiolabs.com/account)
	 * @param pass
	 *            SensioLab Insight password (see
	 *            https://insight.sensiolabs.com/account)
	 * @return A configured InsgthClient
	 * @throws NonRetryableApiCommandException
	 *             if configuration error occurs
	 */
	public static InsightClient get(final String user, final String pass) {
		InsightClient service;
		if (cachedInstances.containsKey(user)) {
			service = cachedInstances.get(user);
		} else {
			service = new InsightClient();
			service.setUser(user);
			service.setPassword(pass);
			try {
				service.setup();
			} catch (final Exception e) {
				throw new NonRetryableApiCommandException("Unable to perfom setup configuration", e);
			}
			cachedInstances.put(user, service);
		}
		return service;
	}

	/**
	 * Provide a configured Insight Client. with user and password form
	 * environment variables.
	 *
	 * @return A configured InsgthClient
	 * @throws NonRetryableApiCommandException
	 *             if configuration error occurs
	 */
	public static InsightClient get() {
		InsightClient service;
		final String defaultUserName = "fromEnvParams";
		if (cachedInstances.containsKey(defaultUserName)) {
			service = cachedInstances.get(defaultUserName);
		} else {
			service = new InsightClient();
			try {
				service.setup();
			} catch (final NonRetryableApiCommandException e) {
				throw e;
			} catch (final Exception e) {
				throw new NonRetryableApiCommandException("Unable to perfom setup configuration", e);
			}
			cachedInstances.put(defaultUserName, service);
		}
		return service;
	}

	private InsightClient() {
		super();
		this.init();
	}

	private InsightClient(final String user, final String password) {
		super();
		this.init();
		this.user = user;
		this.password = password;
	}

	/**
	 * Workaround for problems scanning component "SpringRestClient" as it's an
	 * abstract class, config isn't well injected
	 */
	private void init() {
		final SpringClientConfiguration insightConfig = new SpringClientConfiguration(new StandardServletEnvironment(),
				CONFIG_PREFIX);
		super.config = insightConfig;
	}

	/**
	 * Retrieve user and pass from environment if none provided. User :
	 * CONFIG_PREFIX.client.api.user Pass : CONFIG_PREFIX.client.api.pass with
	 * CONFIG_PREFIX = insight.
	 *
	 * @throws NonRetryableApiCommandException
	 *             if configuration error occurs
	 */
	@Override
	public void setup() {
		if (null == this.user) {
			final String userVarAttr = this.getServiceName().toLowerCase() + ".client.api.user";
			this.user = this.getSpringConfiguration().getProperty(userVarAttr, "");
			if ("".equals(this.user)) {
				throw new NonRetryableApiCommandException(
						"User is required, ether using factory, or using env var " + userVarAttr);
			}
		}
		if (null == this.password) {
			final String passVarAttr = this.getServiceName().toLowerCase() + ".client.api.pass";
			this.password = this.getSpringConfiguration().getProperty(passVarAttr, "");
			if ("".equals(this.password)) {
				throw new NonRetryableApiCommandException(
						"User is required, ether using factory, or using env var " + passVarAttr);
			}
		}

		try {
			super.setup();
		} catch (final Exception e) {
			throw new NonRetryableApiCommandException("Unable to perfom setup configuration", e);
		}
	}

	@Override
	public String getServiceName() {
		return CONFIG_PREFIX;
	}

	@Override
	protected Set<ClientHttpRequestInterceptor> createInterceptors() {
		final Set<ClientHttpRequestInterceptor> interceptors = new TreeSet<>(new AnnotationAwareOrderComparator());

		interceptors.add(new AcceptHeaderInterceptor(this.getAcceptTypeOrInsightDefault()));
		// Add interceptor **Only** when response log is enable (will remove the
		// "INFO" log generated by default).
		if (this.getSpringConfiguration().isHttpLoggingDebug()) {
			interceptors.add(new RequestResponseLogInterceptor(this.getSpringConfiguration().isHttpLoggingDebug()));
		}
		interceptors.add(new ResponseTimeInterceptor(this.getSpringConfiguration().isResponseTimingDebug()));

		interceptors.add(new SensioLabInsightBasicAuthInterceptor(this.user, this.password));
		return interceptors;
	}

	private List<String> getAcceptTypeOrInsightDefault() {
		return this.getSpringConfiguration().getProperty("insight.client.defaultAcceptTypes",
				Collections.singletonList("application/vnd.com.sensiolabs.insight+xml"));
	}

	/**
	 *
	 * @return the first page (default 10) of existing Insight projects for the
	 *         current account.
	 */
	public Projects getProjects() {
		return this.getProjects(1);
	}

	/**
	 *
	 * @param page
	 * @return the list of project from a specific page.
	 */
	public Projects getProjects(final Integer page) {
		final ApiCommand<Projects> command = this
				.restCommand(new ProjectsUriProvider(page), new SpringInstanceCallback<Projects>() {

					@Override
					public Projects runWithUrl(final String url) {
						final Projects response = InsightClient.this.getRestTemplate(this.getContext().getCommandName())
								.getForObject(url, Projects.class);
						return response;
					}
				}).build();

		return command.execute();
	}

	/**
	 *
	 * @param uuid
	 *            project ID, from project list, or
	 *            https://insight.sensiolabs.com/account ("Getting the Project
	 *            UUID").
	 * @return the Insight project
	 */
	public Project getProject(final String uuid) {
		final Setter defaultConfig = HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(10000);

		final ApiCommand<Project> command = this
				.restCommand(new ProjectUriProvider(uuid), new SpringInstanceCallback<Project>() {

					@Override
					public Project runWithUrl(final String fullUrl) {
						final RestTemplate template = InsightClient.this
								.getRestTemplate(this.getContext().getCommandName());
						final Project response = template.getForObject(fullUrl, Project.class);
						return response;
					}
				}).withHystrixCommandProperties(defaultConfig).build();

		return command.execute();
	}

	/**
	 * Update the project.
	 *
	 * @param project
	 * @return the modified project
	 */
	public Project updateProject(final Project project) {
		final HttpEntity<MultiValueMap<String, String>> request = this.createRequestDataHtmlForm(project, true);

		final ApiCommand<Project> command = this
				.restCommand(new ProjectUriProvider(project), new SpringInstanceCallback<Project>() {

					@Override
					public Project runWithUrl(final String fullUrl) {
						final ResponseEntity<Project> response = InsightClient.this
								.getRestTemplate(this.getContext().getCommandName())
								.exchange(fullUrl, HttpMethod.PUT, request, Project.class);
						return response.getBody();
					}
				}).withHystrixCommandProperties(
						HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(5000))
				.build();

		return command.execute();

	}

	/**
	 * Create a new project. *warning* if you create a project with an existing
	 * name, the project will be overwritten (it's uuid will change)
	 *
	 * @param project
	 *            the new project Data
	 * @return the created project
	 */
	public Project createProject(final Project project) {
		final HttpEntity<MultiValueMap<String, String>> request = this.createRequestDataHtmlForm(project, false);

		final ApiCommand<Project> command = this
				.restCommand(new ProjectUriProvider(), new SpringInstanceCallback<Project>() {

					@Override
					public Project runWithUrl(final String fullUrl) {
						final Project response = InsightClient.this.getRestTemplate(this.getContext().getCommandName())
								.postForObject(fullUrl, request, Project.class);
						return response;
					}
				}).withHystrixCommandProperties(
						HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(5000))
				.build();

		return command.execute();

	}

	/**
	 * Get the first page (defaut 10 item) analyses for a specific project
	 *
	 * @param projectUuid
	 * @return
	 */
	public Analyses getAnalyses(final String projectUuid) {
		return this.getAnalyses(projectUuid, 1);
	}

	/**
	 * get the specified page analyses for a specific project
	 *
	 * @param projectUuid
	 * @param page
	 * @return
	 */
	public Analyses getAnalyses(final String projectUuid, final Integer page) {
		final ApiCommand<Analyses> command = this
				.restCommand(new AnalysesUriProvider(projectUuid, page), new SpringInstanceCallback<Analyses>() {

					@Override
					public Analyses runWithUrl(final String fullUrl) {
						final Analyses response = InsightClient.this.getRestTemplate(this.getContext().getCommandName())
								.getForObject(fullUrl, Analyses.class);
						return response;
					}
				}).build();

		return command.execute();
	}

	/**
	 *
	 * @param projectUuid
	 * @param analysNumber
	 * @return The analysis details.
	 */
	public Analysis getAnalysis(final String projectUuid, final Integer analysNumber) {
		final ApiCommand<Analysis> command = this.restCommand(new AnalysisUriProvider(projectUuid, analysNumber),
				new SpringInstanceCallback<Analysis>() {

					@Override
					public Analysis runWithUrl(final String fullUrl) {
						final Analysis response = InsightClient.this.getRestTemplate(this.getContext().getCommandName())
								.getForObject(fullUrl, Analysis.class);
						return response;
					}
				}).build();

		return command.execute();
	}

	/**
	 * Return a partial analysis data (without list of violations).
	 *
	 * @param projectUuid
	 * @param analysNumber
	 * @return
	 */
	public Analysis getAnalysisStatus(final String projectUuid, final Integer analysNumber) {
		final ApiCommand<Analysis> command = this
				.restCommand(new AnalysisUriProvider(projectUuid, analysNumber, Boolean.TRUE),
						new SpringInstanceCallback<Analysis>() {

							@Override
							public Analysis runWithUrl(final String fullUrl) {
								final Analysis response = InsightClient.this
										.getRestTemplate(this.getContext().getCommandName())
										.getForObject(fullUrl, Analysis.class);
								return response;
							}
						})
				.build();

		return command.execute();
	}

	/**
	 * Launch an analyze for the project, with the default reference. Perform a
	 * getAnalysisStatus to follow analysis status evolution.
	 *
	 * @param projectUuid
	 * @return A partial analysis (violations may be absent).
	 */
	public Analysis analyse(final String projectUuid) {
		return this.analyse(projectUuid, null);
	}

	/**
	 * Launch an analyze for the project, with the specified reference
	 *
	 * @param projectUuid
	 * @param reference
	 *            the reference (branch or tag or commit ID)
	 * @return A partial analysis (violations may be absent).
	 */
	public Analysis analyse(final String projectUuid, final String reference) {
		MultiValueMap<String, String> data = null;
		if (null != reference) {
			data = new LinkedMultiValueMap<>();
			data.add("reference", reference);
		}

		final HttpEntity<MultiValueMap<String, String>> request = this.createRequestDataHtmlForm(data);

		final ApiCommand<Analysis> command = this
				.restCommand(new AnalysisUriProvider(projectUuid), new SpringInstanceCallback<Analysis>() {

					@Override
					public Analysis runWithUrl(final String fullUrl) {
						final Analysis response = InsightClient.this.getRestTemplate(this.getContext().getCommandName())
								.postForObject(fullUrl, request, Analysis.class);
						return response;
					}
				}).build();

		return command.execute();
	}

	/**
	 * Create a request to post Form value if request require data.
	 *
	 * @param data
	 *            data map or Null if none are required for this request
	 * @return a request or null
	 */
	private HttpEntity<MultiValueMap<String, String>> createRequestDataHtmlForm(
			final MultiValueMap<String, String> data) {

		HttpEntity<MultiValueMap<String, String>> request = null;

		if (null != data) {
			request = new HttpEntity<>(data, this.createHtmlFormHeader());
		}
		return request;
	}

	private HttpHeaders createHtmlFormHeader() {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return headers;
	}

	private HttpEntity<MultiValueMap<String, String>> createRequestDataHtmlForm(final MultiValueMapable entity,
			final Boolean forUpdate) {

		HttpEntity<MultiValueMap<String, String>> request = null;

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		request = new HttpEntity<>(entity.asMultiValueMap(forUpdate), this.createHtmlFormHeader());
		return request;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return this.user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(final String user) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(final String password) {
		this.password = password;
	}

}
