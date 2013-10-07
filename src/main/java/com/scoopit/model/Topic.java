package com.scoopit.model;

import java.util.List;

public class Topic {
	public Long id;
	public String smallImageUrl;
	public String mediumImageUrl;
	public String imageUrl;
	public String largeImageUrl;
	public String backgroundImage;
	public String backgroundRepeat;
	public String backgroundColor;
	public String description;
	public String name;
	public String shortName;
	public String url;
	public String lang;
	public Boolean isCurator;
	public Boolean isFollowing;
	public Integer curablePostCount;
	public Integer curatedPostCount;
	public Integer unreadPostCount;
	@Deprecated
	public Integer score;
	public User creator;
	public Post pinnedPost;
	public List<Post> curablePosts;
	public List<Post> curatedPosts;
	public List<TopicTag> tags;
	public Stats stats;
}
