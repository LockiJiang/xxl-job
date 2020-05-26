package org.xxl.job.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.xxl.job.client.enums.JobAlarmerEnum;
import org.xxl.job.client.model.ReturnT;
import org.xxl.job.client.model.XxlJobInfo;
import org.xxl.job.client.model.XxlJobLog;
import org.xxl.job.client.util.HttpUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class XxlJobClient {
	private ResourceBundle rb = ResourceBundle.getBundle("xxl_config");
	private String webUrl = rb.getString("xxl.admin.url");
	private String add = rb.getString("xxl.admin.api.add");
	private String load = rb.getString("xxl.admin.api.loadJob");
	private String update = rb.getString("xxl.admin.api.update");
	private String delete = rb.getString("xxl.admin.api.delete");
	private String start = rb.getString("xxl.admin.api.start");
	private String stop = rb.getString("xxl.admin.api.stop");
	private String log = rb.getString("xxl.admin.api.log");
	private String alarmList = rb.getString("xxl.admin.api.alarmList");

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getLoad() {
		return load;
	}

	public void setLoad(String load) {
		this.load = load;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	public String getDelete() {
		return delete;
	}

	public void setDelete(String delete) {
		this.delete = delete;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getAlarmList() {
		return alarmList;
	}

	public void setAlarmList(String alarmList) {
		this.alarmList = alarmList;
	}

	/**
	 * API调用返回值封装
	 * 
	 * @param isSuccess
	 * @param msg
	 * @return
	 */
	private ReturnT returns(String result) {
		ReturnT returnT = null;
		JSONObject object = JSONObject.parseObject(result);
		if ("200".equals(object.getString("code"))) {
			returnT = new ReturnT(true, object.getString("msg"), object.getString("content"));
		} else {
			returnT = new ReturnT(false, object.getString("msg"), object.getString("content"));
		}
		return returnT;
	}

	/**
	 * 添加任务
	 * 
	 * @param jobInfo
	 * @return
	 */
	public ReturnT createJob(XxlJobInfo jobInfo) {
		String url = webUrl + add;
		ReturnT returnT = null;
		try {
			Map<String, String> paramMap = JobToMap(jobInfo);
			String result = new HttpUtil().post(url, paramMap);
			returnT = returns(result);
		} catch (Exception e) {
			e.printStackTrace();
			returnT = new ReturnT(false, e.getMessage(), null);
		}
		return returnT;
	}

	/**
	 * 修改任务
	 * 
	 * @param jobInfo
	 * @return
	 */
	public ReturnT updateJob(XxlJobInfo jobInfo) {
		String url = webUrl + update;
		ReturnT returnT = null;
		try {
			Map<String, String> paramMap = JobToMap(jobInfo);
			String result = new HttpUtil().post(url, paramMap);
			returnT = returns(result);
		} catch (Exception e) {
			e.printStackTrace();
			returnT = new ReturnT(false, e.getMessage(), null);
		}
		return returnT;
	}

	/**
	 * 删除任务
	 * 
	 * @param id
	 * @return
	 */
	public ReturnT deleteJob(String id) {
		String url = webUrl + delete;
		ReturnT returnT = null;
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("id", id);
			String result = new HttpUtil().post(url, paramMap);
			returnT = returns(result);
		} catch (Exception e) {
			e.printStackTrace();
			returnT = new ReturnT(false, e.getMessage(), null);
		}
		return returnT;
	}

	/**
	 * 查询任务
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public XxlJobInfo getJob(String id) {
		String url = webUrl + load;
		XxlJobInfo jobInfo = null;
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("id", id);
			String result = new HttpUtil().post(url, paramMap);
			jobInfo = JSON.parseObject(result, XxlJobInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobInfo;
	}

	/**
	 * 停用任务
	 * 
	 * @param id
	 * @return
	 */
	public ReturnT stopJob(String id) {
		String url = webUrl + stop;
		ReturnT returnT = null;
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("id", id);
			String result = new HttpUtil().post(url, paramMap);
			returnT = returns(result);
		} catch (Exception e) {
			e.printStackTrace();
			returnT = new ReturnT(false, e.getMessage(), null);
		}
		return returnT;
	}

	/**
	 * 启用任务
	 * 
	 * @param id
	 * @return
	 */
	public ReturnT startJob(String id) {
		String url = webUrl + start;
		ReturnT returnT = null;
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("id", id);
			String result = new HttpUtil().post(url, paramMap);
			returnT = returns(result);
		} catch (Exception e) {
			e.printStackTrace();
			returnT = new ReturnT(false, e.getMessage(), null);
		}
		return returnT;
	}

	/**
	 * 任务执行日志
	 * 
	 * @param id
	 * @return
	 */
	public List<XxlJobLog> jobLog(String id) {
		String url = webUrl + log;
		List<XxlJobLog> logs = null;
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("jobId", id);
			paramMap.put("jobGroup", "0");
			paramMap.put("logStatus", "-1");
			String result = new HttpUtil().post(url, paramMap);
			logs = JSON.parseObject(result).getJSONArray("data").toJavaList(XxlJobLog.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logs;
	}

	/**
	 * 查询报警信息</br>
	 * XXL官方版本无此接口，feature/alarm-manage分支添加该接口</br>
	 * 在该版本中，任务XxlJobInfo.alarmEmail属性将失效，由XxlJobInfo.alarmIds替代
	 * 
	 * @param start页码
	 * @param length页面大小
	 * @param alarmEnum报警类型
	 * @param alarmName报警名称
	 * @param alarmDesc报警描述
	 * @return
	 */
	public Map<String, Object> alarmList(int start, int length, JobAlarmerEnum alarmerEnum, String alarmName,
			String alarmDesc) {
		String url = webUrl + alarmList;
		Map<String, Object> alarms = null;
		try {
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("start", "" + start);
			paramMap.put("length", "" + length);
			paramMap.put("alarmEnum", alarmerEnum != null ? alarmerEnum.name() : null);
			paramMap.put("alarmName", alarmName);
			paramMap.put("alarmDesc", alarmDesc);
			String result = new HttpUtil().post(url, paramMap);
			alarms = JSON.parseObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return alarms;
	}

	/**
	 * 将任务参数转换为Map<String, String>作为http请求参数
	 * 
	 * @param jobInfo
	 * @return
	 */
	private Map<String, String> JobToMap(XxlJobInfo jobInfo) {
		Map<String, String> map = new HashMap<>();
		if (jobInfo.getId() > 0) {
			map.put("id", String.valueOf(jobInfo.getId()));
		}
		map.put("jobGroup", String.valueOf(jobInfo.getJobGroup()));
		map.put("jobCron", jobInfo.getJobCron());
		map.put("jobDesc", jobInfo.getJobDesc());
		map.put("author", jobInfo.getAuthor());
		map.put("alarmEmail", jobInfo.getAlarmEmail());
		// feature/alarm-manage分支alarmIds优先级高于alarmEmail
		if (jobInfo.getAlarmIds() != null && jobInfo.getAlarmIds().trim().length() > 0) {
			map.put("alarmEmail", jobInfo.getAlarmIds());
		}
		map.put("executorRouteStrategy", String.valueOf(jobInfo.getExecutorRouteStrategy()));
		map.put("executorHandler", jobInfo.getExecutorHandler());
		map.put("executorBlockStrategy", String.valueOf(jobInfo.getExecutorBlockStrategy()));
		map.put("executorTimeout", String.valueOf(jobInfo.getExecutorTimeout()));
		map.put("executorFailRetryCount", String.valueOf(jobInfo.getExecutorFailRetryCount()));
		map.put("glueType", jobInfo.getGlueType());
		map.put("executorParam", jobInfo.getExecutorParam());
		return map;
	}
}
