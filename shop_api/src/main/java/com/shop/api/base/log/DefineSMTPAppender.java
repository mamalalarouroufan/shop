package com.shop.api.base.log;

import org.apache.log4j.net.SMTPAppender;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 利用缓存，分批发送邮件
 */
public class DefineSMTPAppender extends SMTPAppender {

	public DefineSMTPAppender() {
		/*
		 * 将缓冲中的事件发送邮件（事件数没有达到bufferSize的一半）
		 * 参考文章《RunTime.getRunTime().addShutdownHook用法》
		 */ Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (cb.length() > 0) {
					sendBuffer();
				}
			}
		});
	}

	@Override
	public void append(LoggingEvent event) {
		if (!(checkEntryConditions())) {
			return;
		}
		event.getThreadName();
		event.getNDC();
		event.getMDCCopy();
		if (this.getLocationInfo()) {
			event.getLocationInformation();
		}
		cb.add(event);
		// 事件的个数达到bufferSize的一半的时候就会发一封邮件
		if (this.evaluator.isTriggeringEvent(event)) {
			if (cb.length() >= this.getBufferSize() / 2) {
				sendBuffer();
			}
		}
	}
}