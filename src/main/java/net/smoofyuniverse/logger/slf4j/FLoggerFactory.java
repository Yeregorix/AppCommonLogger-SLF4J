/*
 * Copyright (c) 2017 Hugo Dupanloup (Yeregorix)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.smoofyuniverse.logger.slf4j;

import net.smoofyuniverse.logger.core.LoggerFactory;
import org.slf4j.ILoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FLoggerFactory implements ILoggerFactory {
	private static FLoggerFactory defaultFactory;

	private Map<String, FLogger> loggers = new ConcurrentHashMap<>();
	private LoggerFactory delegate;

	public FLoggerFactory(LoggerFactory delegate) {
		if (delegate == null)
			throw new IllegalArgumentException("delegate");
		this.delegate = delegate;
	}

	public LoggerFactory getDelegate() {
		return this.delegate;
	}

	@Override
	public FLogger getLogger(String name) {
		FLogger l = this.loggers.get(name);
		if (l == null) {
			l = new FLogger(this.delegate.provideLogger(name));
			this.loggers.put(name, l);
		}
		return l;
	}

	public static FLoggerFactory getDefaultFactory() {
		if (defaultFactory == null)
			throw new IllegalStateException("Default factory not provided");
		return defaultFactory;
	}

	public static void setDefaultFactory(FLoggerFactory factory) {
		if (defaultFactory != null)
			throw new IllegalStateException("Default factory already provided");
		defaultFactory = factory;
	}
}
