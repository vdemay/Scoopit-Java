package com.scoopit.example;

import com.scoopit.client.ScoopApiExecutionException;
import com.scoopit.client.ScoopClient;
import com.scoopit.model.Post;
import com.scoopit.model.Topic;
import com.scoopit.model.User;

public class GetTopicAndUser {
	public static void main(String[] args) {
		ScoopClient scoopit = new ScoopClient(
				"<yourkey>",
				"<yoursecret>");
		try {
			Topic t = scoopit.getTopic(9014);
			System.out.println(t);
			for (Post p : t.curatedPosts) {
				System.out.println(p);
			}
			System.out.println("============******===========");
			User u = scoopit.getUser((long) 0);
			System.out.println(u);

		} catch (ScoopApiExecutionException e) {
			e.printStackTrace();
		}

	}
}
