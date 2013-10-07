Scoopit-Java
============

A java Framework to use Scoop.it API

Installing - Eclipse
====================
mvn eclipse:eclipse

Usage
=====
```java
		//get yours on https://www.scoop.it/dev/apps
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
```