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

package net.smoofyuniverse.common.logger.slf4j;

import net.smoofyuniverse.common.logger.core.ILogger;
import net.smoofyuniverse.common.logger.core.LogLevel;
import org.slf4j.Logger;
import org.slf4j.Marker;

import static java.lang.String.format;

public class FLogger implements Logger {
	private ILogger delegate;

	public FLogger(ILogger delegate) {
		this.delegate = delegate;
	}

	public ILogger getDelegate() {
		return this.delegate;
	}

	@Override
	public String getName() {
		return this.delegate.getName();
	}

	@Override
	public boolean isTraceEnabled() {
		return this.delegate.isActive(LogLevel.TRACE);
	}

	@Override
	public void trace(String msg) {
		this.delegate.trace(msg);
	}

	@Override
	public void trace(String format, Object arg) {
		this.delegate.trace(format(format, arg));
	}

	@Override
	public void trace(String format, Object arg1, Object arg2) {
		this.delegate.trace(format(format, arg1, arg2));
	}

	@Override
	public void trace(String format, Object... arguments) {
		this.delegate.trace(format(format, arguments));
	}

	@Override
	public void trace(String msg, Throwable t) {
		this.delegate.trace(msg, t);
	}

	@Override
	public boolean isTraceEnabled(Marker marker) {
		return isTraceEnabled();
	}

	@Override
	public void trace(Marker marker, String msg) {
		trace(msg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg) {
		trace(format, arg);
	}

	@Override
	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		trace(format, arg1, arg2);
	}

	@Override
	public void trace(Marker marker, String format, Object... argArray) {
		trace(format, argArray);
	}

	@Override
	public void trace(Marker marker, String msg, Throwable t) {
		trace(msg, t);
	}

	@Override
	public boolean isDebugEnabled() {
		return this.delegate.isActive(LogLevel.DEBUG);
	}

	@Override
	public void debug(String msg) {
		this.delegate.debug(msg);
	}

	@Override
	public void debug(String format, Object arg) {
		this.delegate.debug(format(format, arg));
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
		this.delegate.debug(format(format, arg1, arg2));
	}

	@Override
	public void debug(String format, Object... arguments) {
		this.delegate.debug(format(format, arguments));
	}

	@Override
	public void debug(String msg, Throwable t) {
		this.delegate.debug(msg, t);
	}

	@Override
	public boolean isDebugEnabled(Marker marker) {
		return isDebugEnabled();
	}

	@Override
	public void debug(Marker marker, String msg) {
		debug(msg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg) {
		debug(format, arg);
	}

	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		debug(format, arg1, arg2);
	}

	@Override
	public void debug(Marker marker, String format, Object... arguments) {
		debug(format, arguments);
	}

	@Override
	public void debug(Marker marker, String msg, Throwable t) {
		debug(msg, t);
	}

	@Override
	public boolean isInfoEnabled() {
		return this.delegate.isActive(LogLevel.INFO);
	}

	@Override
	public void info(String msg) {
		this.delegate.info(msg);
	}

	@Override
	public void info(String format, Object arg) {
		this.delegate.info(format(format, arg));
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
		this.delegate.info(format(format, arg1, arg2));
	}

	@Override
	public void info(String format, Object... arguments) {
		this.delegate.info(format(format, arguments));
	}

	@Override
	public void info(String msg, Throwable t) {
		this.delegate.info(msg, t);
	}

	@Override
	public boolean isInfoEnabled(Marker marker) {
		return isInfoEnabled();
	}

	@Override
	public void info(Marker marker, String msg) {
		info(msg);
	}

	@Override
	public void info(Marker marker, String format, Object arg) {
		info(format, arg);
	}

	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2) {
		info(format, arg1, arg2);
	}

	@Override
	public void info(Marker marker, String format, Object... arguments) {
		info(format, arguments);
	}

	@Override
	public void info(Marker marker, String msg, Throwable t) {
		info(msg, t);
	}

	@Override
	public boolean isWarnEnabled() {
		return this.delegate.isActive(LogLevel.WARN);
	}

	@Override
	public void warn(String msg) {
		this.delegate.warn(msg);
	}

	@Override
	public void warn(String format, Object arg) {
		this.delegate.warn(format(format, arg));
	}

	@Override
	public void warn(String format, Object... arguments) {
		this.delegate.warn(format(format, arguments));
	}

	@Override
	public void warn(String format, Object arg1, Object arg2) {
		this.delegate.warn(format(format, arg1, arg2));
	}

	@Override
	public void warn(String msg, Throwable t) {
		this.delegate.warn(msg, t);
	}

	@Override
	public boolean isWarnEnabled(Marker marker) {
		return isWarnEnabled();
	}

	@Override
	public void warn(Marker marker, String msg) {
		this.delegate.warn(msg);
	}

	@Override
	public void warn(Marker marker, String format, Object arg) {
		this.delegate.warn(format(format, arg));
	}

	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		this.delegate.warn(format(format, arg1, arg2));
	}

	@Override
	public void warn(Marker marker, String format, Object... arguments) {
		this.delegate.warn(format(format, arguments));
	}

	@Override
	public void warn(Marker marker, String msg, Throwable t) {
		this.delegate.warn(msg, t);
	}

	@Override
	public boolean isErrorEnabled() {
		return this.delegate.isActive(LogLevel.ERROR);
	}

	@Override
	public void error(String msg) {
		this.delegate.error(msg);
	}

	@Override
	public void error(String format, Object arg) {
		this.delegate.error(format(format, arg));
	}

	@Override
	public void error(String format, Object arg1, Object arg2) {
		this.delegate.error(format(format, arg1, arg2));
	}

	@Override
	public void error(String format, Object... arguments) {
		this.delegate.error(format(format, arguments));
	}

	@Override
	public void error(String msg, Throwable t) {
		this.delegate.error(msg, t);
	}

	@Override
	public boolean isErrorEnabled(Marker marker) {
		return isErrorEnabled();
	}

	@Override
	public void error(Marker marker, String msg) {
		error(msg);
	}

	@Override
	public void error(Marker marker, String format, Object arg) {
		error(format, arg);
	}

	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2) {
		error(format, arg1, arg2);
	}

	@Override
	public void error(Marker marker, String format, Object... arguments) {
		error(format, arguments);
	}

	@Override
	public void error(Marker marker, String msg, Throwable t) {
		error(msg, t);
	}
}
