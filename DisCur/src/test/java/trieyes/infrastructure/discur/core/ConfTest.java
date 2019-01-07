package trieyes.infrastructure.discur.core;

import org.junit.Test;

import com.alibaba.fastjson.JSONException;

import junit.framework.Assert;
import trieyes.infrastructure.discur.core.Conf;

public class ConfTest {
	@Test
	public void test() throws JSONException, Exception {
		//Assert.assertEquals("127.0.0.1", Conf.getIns().getIP());
		String []a ="wait1000".split("\\s+");
		Assert.assertEquals(2000, Conf.getIns().getPort());
	}
	
}
