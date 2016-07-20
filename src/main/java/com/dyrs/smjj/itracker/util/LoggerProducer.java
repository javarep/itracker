package com.dyrs.smjj.itracker.util;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.jboss.logging.Logger;

public class LoggerProducer {
	@Produces
	public Logger produceLoger(InjectionPoint injectionPoint){
		return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
	}
}
