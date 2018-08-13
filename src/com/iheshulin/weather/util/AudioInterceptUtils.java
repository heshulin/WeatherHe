package com.iheshulin.weather.util;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;


public class AudioInterceptUtils {

	public static String convertAudioFiles(String src, String target) {
		FileInputStream fis;
		FileOutputStream fos;
		try {
			fis = new FileInputStream(src);
			fos = new FileOutputStream(target);
			byte[] b = InputStreamToByte(fis);
			byte[] c = Arrays.copyOfRange(b, 44, b.length);
			fos.write(c);
			IOUtils.closeQuietly(fis);
			IOUtils.closeQuietly(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return target;

	}

	private static byte[] InputStreamToByte(FileInputStream fis) throws IOException {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		long size = fis.getChannel().size();
		byte[] buffer = null;
		if (size <= Integer.MAX_VALUE) {
			buffer = new byte[(int) size];
		} else {
			buffer = new byte[8];
			for (int ix = 0; ix < 8; ++ix) {
				int offset = 64 - (ix + 1) * 8;
				buffer[ix] = (byte) ((size >> offset) & 0xff);
			}
		}
		int len;
		while ((len = fis.read(buffer)) != -1) {
			byteStream.write(buffer, 0, len);
		}
		byte[] data = byteStream.toByteArray();
		IOUtils.closeQuietly(byteStream);
		return data;
	}

}
