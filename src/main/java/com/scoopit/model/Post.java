package com.scoopit.model;

import java.util.Date;
import java.util.List;

public class Post {
	public Long id;
	public String content;
	public String htmlContent;
	public String htmlFragment;
	public String insight;
	public String htmlInsight;
	public String title;
	public Integer thanksCount;
	public Integer reactionsCount;
	public Source source;
	public String twitterAuthor;
	public String url;
	public String scoopUrl;
	public String scoopShortUrl;
	public String smallImageUrl;
	public String mediumImageUrl;
	public String imageUrl;
	public String largeImageUrl;
	public String imageWidth;
	public String imageHeight;
	public String imageSize;
	public String imagePosition;
	public List<String> imageUrls;
    public List<String> tags;
	public Integer commentsCount;
	public Boolean isUserSuggestion;
	public Long pageViews;
	public Boolean edited;
	public User author;
	public Date publicationDate;
	public Date curationDate;
	public List<PostComment> comments;
	public Boolean thanked;
	public Long topicId;
	public Topic topic;
	
	@Override
	public String toString() {
		return "[" + id + "]" + title + " | " +url;
	}
}
