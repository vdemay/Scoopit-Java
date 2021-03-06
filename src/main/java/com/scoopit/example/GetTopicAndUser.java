package com.scoopit.example;

import com.scoopit.client.ScoopApiExecutionException;
import com.scoopit.client.ScoopClient;
import com.scoopit.model.Post;
import com.scoopit.model.ResolverResult.Type;
import com.scoopit.model.Topic;
import com.scoopit.model.User;

public class GetTopicAndUser {
    public static void main(String[] args) throws ScoopApiExecutionException {
        // get yours on https://www.scoop.it/dev/apps
        ScoopClient scoopit = new ScoopClient("<youapikey>", "<youapisecret>");

        System.out.println("============******===========");
        Topic t = scoopit.getTopic(9014);
        System.out.println(t);
        for (Post p : t.curatedPosts) {
            System.out.println(p);
        }
        System.out.println("============******===========");
        User u = scoopit.getUser(0l);
        System.out.println(u);

        System.out.println("============******===========");
        System.out.println(scoopit.resolveId("fixies", Type.Topic));
        try {
            System.out.println(scoopit.resolveId("some-topic-that-should-,not-exists", Type.Topic));
        } catch (ScoopApiExecutionException e) {
            if (e.response.getCode() == 404) {
                System.out.println("=> not found!");
            } else {
                throw e;
            }
        }

    }
}
