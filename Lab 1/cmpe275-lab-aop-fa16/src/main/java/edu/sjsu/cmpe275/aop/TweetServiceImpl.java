package edu.sjsu.cmpe275.aop;

import java.io.IOException;
import java.util.logging.Logger;

public class TweetServiceImpl implements TweetService {

//	private int failureAttempts = 0;
	private static int tweetFailureCounter = 0;
	private static int followFailureCounter = 0;
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(TweetServiceImpl.class.toString());

	public void tweet(String user, String message) throws IllegalArgumentException, IOException {
		if (message.length() > 140)
			throw (new java.lang.IllegalArgumentException());
		else if (tweetFailureCounter < 2) {
			logger.info("failure attempts are " + tweetFailureCounter);
			tweetFailureCounter++;
			throw new IOException();
		} else
			return;
	}

	public void follow(String follower, String followee) throws IOException {
		if (followFailureCounter < 5) {
			followFailureCounter++;
			throw new IOException();
		}
	}
}
