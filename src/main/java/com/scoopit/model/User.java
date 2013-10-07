package com.scoopit.model;

import java.util.List;

public class User {
	public Long id;
	public String name;
	public String shortName;
	public String bio;
	public String smallAvatarUrl;
	public String mediumAvatarUrl;
	public String avatarUrl;
	public String largeAvatarUrl;
	public List<Sharer> sharers;
	public List<Topic> curatedTopics;
	public List<Topic> followedTopics;
	
	@Override
	public String toString() {
		return "[" + id + "]" + name;
	}
}
