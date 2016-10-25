package edu.sjsu.cmpe275.aop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class TweetStatsImpl implements TweetStats {
	/**
	 * Get Most Productive User:
	 * <p>
	 * TreeMap to store length of each tweet with its username
	 * </p>
	 */
	public static Map<String, Integer> tweetMap = new TreeMap<String, Integer>();

	/**
	 * Get Most Active Follower:
	 * <p>
	 * Multi-Valued Map to store the list of followees for a particular follower
	 * </p>
	 **/
	public static Map<String, List<String>> followersMap = new TreeMap<String, List<String>>();

	/**
	 * Get Length of Longest Tweet:
	 * <p>
	 * Array to store the length of each successful tweet
	 * </p>
	 */
	public static ArrayList<Integer> tweetLengthStats = new ArrayList<Integer>();

	/**
	 * reset all the three measurements.
	 */
	public void resetStats() {
		tweetMap.clear();
		tweetLengthStats.clear();
		followersMap.clear();
	}

	/**
	 * @return the length of longest message successfully tweeted since the
	 *         beginning or last reset. If no messages were successfully
	 *         tweeted, return 0.
	 */
	public int getLengthOfLongestTweet() {
		if (!tweetLengthStats.isEmpty()) {
			int longestTweetLength = (Collections.max(tweetLengthStats));
			return longestTweetLength;
		}
		return 0;
	}

	/**
	 * @return the user who has attempted to follow the biggest number of
	 *         different users since the beginning or last reset. If there is a
	 *         tie, return the 1st of such users based on alphabetical order.
	 *         Even if the follow action did not succeed, it still counts toward
	 *         the stats. If no users attempted to follow anybody, return null.
	 */
	public String getMostActiveFollower() {
		List<Integer> followersList = new ArrayList<Integer>();

		if (!followersMap.isEmpty()) {
			for (Map.Entry<String, List<String>> entry : followersMap.entrySet()) {
				List<String> value = entry.getValue();
				followersList.add(value.size());
			}
			int maxFollowers = Collections.max(followersList);

			for (Map.Entry<String, List<String>> entry : followersMap.entrySet()) {
				String key = entry.getKey();
				List<String> value = entry.getValue();
				if (maxFollowers == value.size())
					return key;
			}
		}
		return null;
	}

	/**
	 * The most productive user is determined by the total length of all the
	 * messages successfully tweeted since the beginning or last reset. If there
	 * is a tie, return the 1st of such users based on alphabetical order. If no
	 * users successfully tweeted, return null.
	 *
	 * @return the most productive user.
	 */
	public String getMostProductiveUser() {
		if (!tweetMap.isEmpty()) {
			int maxLength = Collections.max(tweetMap.values());
			for (Entry<String, Integer> entry : tweetMap.entrySet()) {
				if (entry.getValue() == maxLength) {
					return entry.getKey();
				}
			}
		}
		return null;
	}

}



