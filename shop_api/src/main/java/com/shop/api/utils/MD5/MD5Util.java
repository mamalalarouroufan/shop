/**
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.shop.api.utils.MD5;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * MD5加密工具类
 * </p>
 * .
 *
 * @author
 * @Date 2014-5-9
 */
public class MD5Util {

	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(MD5Util.class);

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer builder = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			builder.append(byteToHexString(b[i]));
		return builder.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
		} catch (Exception exception) {
			LOGGER.error("Exception md5加密错误提示:{}", exception);
		}
		return resultString;
	}
	
	public static String getMD5Encode(String origin) {
		return MD5Util.MD5Encode(origin, "UTF-8");
	}

}
