package org.wmaop.bdd.steps;

import java.io.IOException;

import org.wmaop.bdd.jbehave.InterceptPoint;

import com.wm.app.b2b.client.ServiceException;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
import com.wm.util.coder.IDataXMLCoder;

public class MockServiceStep extends BaseServiceStep {

	private final IData idata;
	private final String execService;

	public MockServiceStep(String adviceId, InterceptPoint interceptPoint, String serviceName, String idataFile) throws Exception {

		idata = IDataFactory.create();
		IDataCursor cursor = idata.getCursor();
		IDataUtil.put(cursor, ADVICE_ID, adviceId);
		IDataUtil.put(cursor, SERVICE_NAME, serviceName);
		IDataUtil.put(cursor, INTERCEPT_POINT, interceptPoint.toString());
		IDataUtil.put(cursor, RESPONSE, stringFromClasspathResource(idataFile));
		cursor.destroy();
		execService = FIXED_RESPONSE_MOCK;
	}

	public MockServiceStep(String adviceId, InterceptPoint interceptPoint, String serviceName, String idataFile,
			String jexlExpression) throws Exception {

		idata = IDataFactory.create();
		IDataCursor cursor = idata.getCursor();
		IDataUtil.put(cursor, ADVICE_ID, adviceId);
		IDataUtil.put(cursor, SERVICE_NAME, serviceName);
		IDataUtil.put(cursor, INTERCEPT_POINT, interceptPoint.toString());
		IDataUtil.put(cursor, CONDITION, jexlExpression);
		IDataUtil.put(cursor, RESPONSE, stringFromClasspathResource(idataFile));
		
		cursor.destroy();
		
		execService = FIXED_RESPONSE_MOCK;
	}

	@Override
	void execute(ExecutionContext executionContext) throws Exception {
		invokeService(executionContext, execService, idata);
	}
}