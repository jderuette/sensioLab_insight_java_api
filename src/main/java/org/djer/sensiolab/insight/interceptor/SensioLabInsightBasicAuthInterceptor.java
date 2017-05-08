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
package org.djer.sensiolab.insight.interceptor;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;

/**
 * @author djer
 *
 */
public class SensioLabInsightBasicAuthInterceptor implements ClientHttpRequestInterceptor {

	private static final Logger LOGGER = LoggerFactory.getLogger(SensioLabInsightBasicAuthInterceptor.class);
	private static final String ORIGIN_ENCODING = "UTF-8";
	private static final Integer PASS_VISIBLE_LEN = 4;
	private final String login;
	private final String pass;

	public SensioLabInsightBasicAuthInterceptor(final String login, final String pass) {
		this.login = login;
		this.pass = pass;
	}

	@Override

	public ClientHttpResponse intercept(final HttpRequest request, final byte[] body,
			final ClientHttpRequestExecution execution) throws IOException {
		if (LOGGER.isDebugEnabled()) {
			final String passDebug = this.partialHideString(this.pass);

			final StringBuffer sbMessage = new StringBuffer(64);
			sbMessage.append("Adding Basic Auth. User : ").append(this.login).append(", pass ").append(passDebug);

			LOGGER.debug(sbMessage.toString());
		}
		final String auth = this.login + ":" + this.pass;
		@SuppressWarnings("PMD.LawOfDemeter")
		final String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes(Charset.forName(ORIGIN_ENCODING)));
		final String authHeader = "Basic " + encodedAuth;

		request.getHeaders().add(HttpHeaders.AUTHORIZATION, authHeader);
		final HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);

		return execution.execute(requestWrapper, body);
	}

	private String partialHideString(final String stringToHide) {
		String hiddenString = null;
		if (null != stringToHide) {
			if ("".equals(stringToHide)) {
				hiddenString = "{empty}";
			} else {
				final StringBuffer sbPass = new StringBuffer();
				sbPass.append("(partial) : ");
				if (stringToHide.length() < PASS_VISIBLE_LEN) {
					sbPass.append(stringToHide);
				} else {
					sbPass.append(stringToHide.substring(0, PASS_VISIBLE_LEN)).append("...");
					if (stringToHide.length() > PASS_VISIBLE_LEN * 2) {
						sbPass.append(stringToHide.substring(stringToHide.length() - PASS_VISIBLE_LEN));
					}
				}

				hiddenString = sbPass.toString();
			}
		}
		return hiddenString;
	}

}
