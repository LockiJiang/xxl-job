package org.xxl.job.client.starter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xxl.job.client.XxlJobClient;
import org.xxl.job.client.starter.property.URLProperties;

@Configuration
@EnableConfigurationProperties(URLProperties.class)
@ConditionalOnProperty(prefix = "xxl.admin", name = "enabled", havingValue = "true")
public class XXLConfig {
	@Autowired
	private URLProperties urlProperties;

	@Bean
	public XxlJobClient xxlJobClient() {
		XxlJobClient client = new XxlJobClient();
		if (urlProperties.getUrl() == null || urlProperties.getUrl().trim().length() == 0) {
			throw new RuntimeException("xxl.admin.url is required!");
		}
		client.setWebUrl(urlProperties.getUrl());
		URLProperties.Api api = urlProperties.getApi();
		if (api == null) {
			return client;
		}
		if (api.getAdd() != null && api.getAdd().trim().length() > 0) {
			client.setAdd(api.getAdd());
		}
		if (api.getDelete() != null && api.getDelete().trim().length() > 0) {
			client.setDelete(api.getDelete());
		}
		if (api.getUpdate() != null && api.getUpdate().trim().length() > 0) {
			client.setUpdate(api.getUpdate());
		}
		if (api.getLoadJob() != null && api.getLoadJob().trim().length() > 0) {
			client.setLoad(api.getLoadJob());
		}
		if (api.getStart() != null && api.getStart().trim().length() > 0) {
			client.setStart(api.getStart());
		}
		if (api.getStop() != null && api.getStop().trim().length() > 0) {
			client.setStop(api.getStop());
		}
		if (api.getLog() != null && api.getLog().trim().length() > 0) {
			client.setLog(api.getLog());
		}

		if (api.getAlarmList() != null && api.getAlarmList().trim().length() > 0) {
			client.setAlarmList(api.getAlarmList());
		}
		return client;
	}
}
