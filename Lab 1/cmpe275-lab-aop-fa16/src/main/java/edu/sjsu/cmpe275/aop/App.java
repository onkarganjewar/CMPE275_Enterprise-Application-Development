package edu.sjsu.cmpe275.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Onkar Ganjewar aop-lab
 */
public class App {

	/**
	 * This solves the warning problem but not the real issue which is leaving
	 * the context open and causing a leak. You could do the same with
	 * a @SupressWarnings annotation, but still better to solve the root
	 * problem, don't you think
	 */
	private static ApplicationContext context;

	public static void main(String[] args) {
		/***
		 * Following is a dummy implementation of App to demonstrate bean
		 * creation with Application context. You may make changes to suit your
		 * need, but this file is NOT part of the submission.
		 */

		// context = new ClassPathXmlApplicationContext("context.xml");

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");

		TweetService tweeter = (TweetService) ctx.getBean("tweetService");
		TweetStats stats = (TweetStats) ctx.getBean("tweetStats");

		try {

			tweeter.follow("alex", "bob");
			tweeter.follow("bob", "john");
			tweeter.follow("bob", "alze");
			tweeter.follow("bob", "alex");
			tweeter.follow("john", "alex");
			tweeter.follow("john", "a2");
			tweeter.follow("john", "a4");
			tweeter.follow("alex", "bob");

			tweeter.tweet("alice", "first tweet");
			// tweeter.tweet("alex", "aaafirst tweet");
			// tweeter.tweet("alex", "first tweet");
			stats.resetStats();
			tweeter.tweet("bob", "second tweet");
			// To throw an illegal argument exception
//			tweeter.tweet("alice",
//					" This message is larger than 140 characters which will make compiler to throw an IllegalArgumentException."
//							+ "Lorem  ipsum dolor sit amet, Lorem ipsum dolor sit amet.");
			tweeter.tweet("john", "third tweet");
			tweeter.follow("alex", "bob");
			tweeter.follow("bob", "alex");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("Most productive user: " + stats.getMostProductiveUser());
			System.out.println("Most active follower: " + stats.getMostActiveFollower());
			System.out.println("Length of the longest tweet: " + stats.getLengthOfLongestTweet());
			if (ctx != null) {
				ctx.close();
			}
		}
	}
}
