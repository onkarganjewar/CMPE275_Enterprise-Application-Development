package edu.sjsu.cmpe275.aop.aspect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import edu.sjsu.cmpe275.aop.TweetStatsImpl;

/**
 * @author Onkar Ganjewar
 */
@Aspect
public class StatsAspect {

	/** Logger to log the messages onto console */
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(StatsAspect.class.getName());

	/** String to hold the username */
	private String userName = "";

	/** Variable to store the length of a tweet */
	private int tweetLength = 0;

	/** Variable to store the name of followee */
	private String followee = "";

	/** Variable to store the name of follower */
	private String follower = "";

	/** Pointcut expression for tweet method */
	@Pointcut("execution(public void edu.sjsu.cmpe275.aop.TweetService.tweet(..))")
	public void tweetPointcut() {
	}

	/** Pointcut expression for follow method */
	@Pointcut("execution(public void edu.sjsu.cmpe275.aop.TweetService.follow(..))")
	public void followPointcut() {
	}

	/**
	 * <p>
	 * Advice to execute after the tweet is returned successfully
	 * </p>
	 */
	@AfterReturning("tweetPointcut()")
	public void afterTweet(JoinPoint joinPoint) {
		userName = joinPoint.getArgs()[0].toString();
		tweetLength = joinPoint.getArgs()[1].toString().length();
		if (tweetLength <= 140) {
			TweetStatsImpl.tweetLengthStats.add(joinPoint.getArgs()[1].toString().length());
			if (TweetStatsImpl.tweetMap.containsKey(userName))
				TweetStatsImpl.tweetMap.put(userName, TweetStatsImpl.tweetMap.get(userName) + tweetLength);
			else
				TweetStatsImpl.tweetMap.put(userName, tweetLength);
		}
	}

	/**
	 * <p>
	 * Advice to execute after the invocation of follow method irrespective of
	 * whether an exception is occurred or not.
	 * </p>
	 */
	@After("followPointcut()")
	public void afterFollow(JoinPoint jp) {
		follower = jp.getArgs()[0].toString();
		followee = jp.getArgs()[1].toString();
		putObjects(TweetStatsImpl.followersMap, follower, followee);
	}

	/**
	 * This function puts the list of users that the particular follower follows
	 * in a Multi-valued map.
	 * 
	 * @param fMap
	 *            A map containing the list of followees for a particular
	 *            follower
	 * @param uN
	 *            Name of the follower
	 * @param followee
	 *            Name of the followee
	 */
	private void putObjects(Map<String, List<String>> fMap, String uN, String followee) {
		List<String> myClassList = fMap.get(uN);
		if (myClassList == null) {
			myClassList = new ArrayList<String>();
			fMap.put(uN, myClassList);
			myClassList.add(followee);
		} else if (!myClassList.contains(followee)) {
			myClassList.add(followee);
		}
	}
}
