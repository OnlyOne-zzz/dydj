package com.bestfeng.dydj.configuration;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 请求流多次重用
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[]              data;
    private       Map<String, String> headers = new HashMap<>();

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		data = IOUtils.toByteArray(request.getInputStream());
	}

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }
	@Override
    public String getHeader(String name) {
        String value = this.headers.get(name);
        if (value != null) {
            return value;
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return super.getHeaders(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String>         set         = new HashSet<>(headers.keySet());
        Enumeration<String> enumeration = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            set.add(name);
        }
        return Collections.enumeration(set);
    }

    @Override
    public ServletInputStream getInputStream() {
        // 在调用getInputStream函数时，创建新的流，包含原先数据流中的信息，然后返回
        return new MyServletInputStream(new ByteArrayInputStream(data));
    }

	public class MyServletInputStream extends ServletInputStream {
		private InputStream inputStream;

		public MyServletInputStream(InputStream inputStream) {
			this.inputStream = inputStream;
		}

		@Override
		public int read() throws IOException {
			return inputStream.read();
		}

		@Override
		public boolean isFinished() {
			return true;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setReadListener(ReadListener readListener) {

		}
	}

}