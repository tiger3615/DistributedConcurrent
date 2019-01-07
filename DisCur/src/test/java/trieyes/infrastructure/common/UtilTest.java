package trieyes.infrastructure.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import trieyes.infrastructure.discur.common.Util;
import trieyes.infrastructure.discur.core.DisCur;

public class UtilTest {
	@Test
	public void test_file2String() {
		try {
			String r = Util.file2String("src/test/resources/test.txt");
			Assert.assertEquals("12345\r\n", r);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test_UTC() {
		try {
			System.out.println(Util.UTC("yyyy-MM-dd HH:mm:ssZ"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test_UTCDate() {
		try {
			System.out.println(Util.UTCDateTime());
			System.out.println(Util.formatTime(Util.UTCDateTime(), "yyyy-MM-dd HH:mm:ssZ"));
			String s = "js{j}";
			System.out.println(s.matches("js\\{.+\\}"));
			System.out.println(s.substring(3, s.length() - 1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test_str2DateTime() {
		try {
			System.out.println("ssss" + Util.str2DateTime("2018-03-01 07:49:38", "yyyy-MM-dd HH:mm:ss"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test_decodeEncode() {
		try {
			System.out.println(Util.encode("sf1234567", "123456"));
			System.out.println(Util.decode("sf1234567", "8C6F3A9BB06D9E12FFEF3F9E52248041"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
