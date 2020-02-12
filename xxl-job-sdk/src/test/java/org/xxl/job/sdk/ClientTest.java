package org.xxl.job.sdk;

import org.junit.Test;
import org.xxl.job.client.XxlJobClient;
import org.xxl.job.client.enums.ExecutorBlockStrategyEnum;
import org.xxl.job.client.enums.ExecutorRouteStrategyEnum;
import org.xxl.job.client.model.XxlJobInfo;

public class ClientTest {
	@Test
	public void addJob() {
		XxlJobInfo jobInfo = new XxlJobInfo();
		jobInfo.setAlarmEmail("jiangyang003@chinssofti.com");
		jobInfo.setAuthor("蒋洋");
		jobInfo.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION);
		jobInfo.setExecutorFailRetryCount(0);
		jobInfo.setExecutorHandler("procedureHandler");
		jobInfo.setExecutorParam("{\"type\":\" DB2\",\"url\":\"jdbc:db2://10.0.0.65:50000/DATANALY\",\"user\":\"db2admin\",\"code\":\"db2admin\",\"pros\":[\"PD_ZM_K_RLIC_D\",\"PD_ZM_K_CASEINFO_D(5)\"]}");
		jobInfo.setExecutorRouteStrategy(ExecutorRouteStrategyEnum.FIRST);
		jobInfo.setExecutorTimeout(0);
		jobInfo.setJobCron("0 0 12 * * ? *");
		jobInfo.setJobDesc("system测试任务");
		jobInfo.setJobGroup(1);
		
		XxlJobClient client = new XxlJobClient();
		System.out.println(client.createJob(jobInfo));
	}
	
	@Test
	public void updateJob() {
		XxlJobInfo jobInfo = new XxlJobInfo();
		jobInfo.setId(4);
		jobInfo.setAlarmEmail("jiangyang003@chinssofti.com");
		jobInfo.setAuthor("蒋洋");
		jobInfo.setExecutorBlockStrategy(ExecutorBlockStrategyEnum.SERIAL_EXECUTION);
		jobInfo.setExecutorFailRetryCount(0);
		jobInfo.setExecutorHandler("procedureHandler");
		jobInfo.setExecutorParam("{\"type\":\"DB2\",\"url\":\"jdbc:db2://10.0.0.65:50000/DATANALY\",\"user\":\"db2admin\",\"code\":\"db2admin\",\"pros\":[\"PD_ZM_K_RLIC_D\",\"PD_ZM_K_CASEINFO_D(5)\"]}");
		jobInfo.setExecutorRouteStrategy(ExecutorRouteStrategyEnum.FIRST);
		jobInfo.setExecutorTimeout(0);
		jobInfo.setJobCron("0 0/2 * * * ? *");
		jobInfo.setJobDesc("system测试任务");
		jobInfo.setJobGroup(1);
		
		XxlJobClient client = new XxlJobClient();
		System.out.println(client.updateJob(jobInfo));
	}
	
	@Test
	public void startJob() {
		XxlJobClient client = new XxlJobClient();
		System.out.println(client.startJob(4 + ""));
	}
	
	@Test
	public void stopJob() {
		XxlJobClient client = new XxlJobClient();
		System.out.println(client.stopJob(4 + ""));
	}
	
	@Test
	public void removeJob() {
		XxlJobClient client = new XxlJobClient();
		System.out.println(client.deleteJob(7 + ""));
	}
	
	@Test
	public void loadJob() {
		XxlJobClient client = new XxlJobClient();
		System.out.println(client.getJob(4 + ""));
	}
	
	@Test
	public void jobLog() {
		XxlJobClient client = new XxlJobClient();
		System.out.println(client.jobLog(4 + ""));
	}
}
