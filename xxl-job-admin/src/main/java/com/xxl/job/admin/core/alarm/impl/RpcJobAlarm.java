package com.xxl.job.admin.core.alarm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinasofti.gotask.api.RpcService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxl.job.admin.core.alarm.JobAlarm;
import com.xxl.job.admin.core.model.XxlAlarmInfo;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.rpc.remoting.invoker.XxlRpcInvokerFactory;
import com.xxl.rpc.remoting.invoker.call.CallType;
import com.xxl.rpc.remoting.invoker.reference.XxlRpcReferenceBean;
import com.xxl.rpc.remoting.invoker.route.LoadBalance;
import com.xxl.rpc.remoting.net.NetEnum;
import com.xxl.rpc.serialize.Serializer;

public class RpcJobAlarm implements JobAlarm {
	private static Logger logger = LoggerFactory.getLogger(RpcJobAlarm.class);
	
	@Override
	public boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog, XxlAlarmInfo alarmInfo) {
		boolean flag = true;
		try {
			ObjectMapper mapper = new ObjectMapper();
			@SuppressWarnings("rawtypes")
			Map map = mapper.readValue(alarmInfo.getAlarmParam(), Map.class);
			List<String> addresses = map.get("addresses") == null ? null : (List<String>) map.get("addresses");
			if (addresses == null || addresses.size() < 1) {
				throw new Exception("xxl-rpc地址配置错误：" + map);
			}
			// alarmContent
			String alarmContent = "Alarm Job LogId=" + jobLog.getId();
			if (jobLog.getTriggerCode() != ReturnT.SUCCESS_CODE) {
				alarmContent += "<br>TriggerMsg=<br>" + jobLog.getTriggerMsg();
			}
			if (jobLog.getHandleCode() > 0 && jobLog.getHandleCode() != ReturnT.SUCCESS_CODE) {
				alarmContent += "<br>HandleCode=" + jobLog.getHandleMsg();
			}
			
			List<String> param = new ArrayList<>();
			if (map.get("param") != null) {
				param.add(map.get("param").toString());
			}
			for (String addr : addresses) {
				try {
					XxlRpcReferenceBean bean = new XxlRpcReferenceBean(NetEnum.NETTY,
							Serializer.SerializeEnum.HESSIAN.getSerializer(), CallType.SYNC,
							LoadBalance.CONSISTENT_HASH, RpcService.class, null, 500, addr, null, null, null);
					RpcService notic = (RpcService) bean.getObject();
					com.chinasofti.gotask.api.vo.ReturnT rs = notic.sedMsg("<" + info.getJobDesc() + ">执行异常", alarmContent, param);
					logger.info(rs.toString());
				} catch (Exception e) {
					flag = false;
					logger.error(">>>>>>>>>>> xxl-job, job fail alarm rpc send error, JobLogId:{}", jobLog.getId(), e);
				} finally {
					XxlRpcInvokerFactory.getInstance().stop();
					logger.info(">>>>>>>>>>> xxl-job, XxlRpcInvokerFactory stopped");
				}
			}
		} catch (Exception e) {
			return flag;
		}
		return flag;
	}
}
