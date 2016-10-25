package edu.sjsu.cmpe275.aop.aspect;

import java.io.IOException;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;

/**
 * @author Onkar Ganjewar
 */
@Aspect
public class RetryAspect {

	/** Logger to log the messages onto console */
	private static final Logger logger = Logger.getLogger(RetryAspect.class.getName());

	/** Counter to retry the failure exception attempts */
	private int failureCounter = 0;

	@Around("execution(public void edu.sjsu.cmpe275.aop.TweetService.*(..))")
	public void retryAllMethods(ProceedingJoinPoint joinPoint) throws Throwable {
		boolean retry = true;
		failureCounter = 0;
		while (retry) {
			try {
				retry = false;
				if (failureCounter > 0)
					System.out.println(
							"Retrying method " + joinPoint.getSignature().getName() + " " + failureCounter + " time");
				joinPoint.proceed();
			} catch (IOException ex) {
				if (failureCounter == 0)
					System.out.println("Invocation Failed for method " + joinPoint.getSignature().getName()
							+ "... Retrying now ...");
				failureCounter++;
				if (failureCounter < 3) {
					retry = true;
				}
			} catch (IllegalArgumentException ex) {
				logger.info("ILLEGAL ARGUMENT EXCEPTION CAUGHT");
				return;
			}
		}
		return;
	}
}
