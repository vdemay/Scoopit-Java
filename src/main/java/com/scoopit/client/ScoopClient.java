package com.scoopit.client;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import com.scoopit.model.Post;
import com.scoopit.model.ResolverResult;
import com.scoopit.model.Topic;
import com.scoopit.model.User;

/**
 * Very basic scoop client that executes queries anonymously authenticated via
 * OAuth
 * 
 */
public class ScoopClient {

	private ObjectMapper mapper;

	private OAuthService client;

	public ScoopClient(String apiKey, String apiSecret) {
		client = new ServiceBuilder().provider(ScoopApi.class).apiKey(apiKey)
				.apiSecret(apiSecret).build();
		mapper = new ObjectMapper();
		mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
		mapper.configure(
				DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	}

	/*
	 * OAuthRequest request = builOAuthRequest(Verb.GET, endpoint);
	 * 
	 * for (Map.Entry<String, String> parameter : parameters.entries()) {
	 * request.addBodyParameter(parameter.getKey(), parameter.getValue()); }
	 * 
	 * signRequest(request); return sendRequest(request); }
	 */

	private JsonNode doGet(String endpoint, Multimap<String, String> parameters)
			throws ScoopApiExecutionException {
		OAuthRequest request = builOAuthRequest(Verb.GET, endpoint);
		for (Map.Entry<String, String> parameter : parameters.entries()) {
			request.addQuerystringParameter(parameter.getKey(),
					parameter.getValue());
		}

		signRequest(request);
		return sendRequest(request);
	}

	private OAuthRequest builOAuthRequest(Verb verb, String endpoint) {
		// Build oauth request
		OAuthRequest request;
		request = new OAuthRequest(verb, "http://www.scoop.it" + endpoint);
		// Allow direct access to running nodes
		request.addHeader("X-SCOOP-HCK-X-DOMAINS", "true");
		return request;
	}

	private void signRequest(OAuthRequest request) {
		client.signRequest(OAuthConstants.EMPTY_TOKEN, request);
	}

	private JsonNode sendRequest(OAuthRequest request)
			throws ScoopApiExecutionException {
		Response response = request.send();
		if (!response.isSuccessful()) {
			throw new ScoopApiExecutionException(response,
					"Error executing query: " + response.getCode() + " - "
							+ response.getBody(), null);
		}
		JsonNode ret;
		try {
			ret = mapper.readTree(response.getBody());
		} catch (JsonProcessingException e) {
			throw new ScoopApiExecutionException(
					response,
					"Error parsing the json in the response: " + e.getMessage(),
					e);
		} catch (IOException e) {
			throw new ScoopApiExecutionException(response,
					"Errore reading the response: " + e.getMessage(), e);
		}
		return ret;
	}

	// profile

	private static final String PROFILE_ENDPOINT = "/api/1/profile";

	public User getUser(Long id) throws ScoopApiExecutionException {
		return getUser(id, null, null, null, null, null, null, null, null);
	}
	public User getUser(Long id, Integer curated, Integer curable,
			Integer ncomments, Boolean getCuratedTopics,
			Boolean getFollowedTopics, Boolean getTags, Boolean getCreator,
			Boolean getStats) throws ScoopApiExecutionException {
		Multimap<String, String> params = LinkedListMultimap.create();
		if (id != null) {
			params.put("id", id.toString());
		}
		if (curated != null) {
			params.put("curated", curated.toString());
		} else {
			params.put("curated", "30");
		}
		if (curable != null) {
			params.put("curable", curable.toString());
		} else {
			params.put("curable", "0");
		}
		if (ncomments != null) {
			params.put("ncomments", ncomments.toString());
		} else {
			params.put("ncomments", "0");
		}
		if (getCuratedTopics != null) {
			params.put("getCuratedTopics", getCuratedTopics.toString());
		} else {
			params.put("getCuratedTopics", "false");
		}
		if (getFollowedTopics != null) {
			params.put("getFollowedTopics", getFollowedTopics.toString());
		} else {
			params.put("getFollowedTopics", "false");
		}
		if (getTags != null) {
			params.put("getTags", getTags.toString());
		} else {
			params.put("getTags", "false");
		}
		if (getCreator != null) {
			params.put("getCreator", getCreator.toString());
		}
		if (getStats != null) {
			params.put("getStats", getStats.toString());
		} else {
			params.put("getStats", "false");
		}

		JsonNode root = doGet(PROFILE_ENDPOINT, params);
		if (root == null) {
			return null;
		}
		User model;
		try {
			model = mapper.readValue(root.get("user"), User.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return model;
	}

	// topic

	private static final String TOPIC_ENDPOINT = "/api/1/topic";

	public enum TopicOrder {
		tag, curationDate, user
	}

	public Topic getTopic(long id) throws ScoopApiExecutionException {
		return getTopic(id, null, null, null, null, null, null, null);
	}

	public Topic getTopic(long id, int nb, int page)
			throws ScoopApiExecutionException {
		return getTopic(id, nb, page, null, null, null, null, null);
	}

	public Topic getTopic(long id, Integer curated, Integer page,
			Integer curable, TopicOrder order, String tag, Long since,
			Integer ncomments) throws ScoopApiExecutionException {
		Multimap<String, String> params = LinkedListMultimap.create();
		params.put("id", Long.toString(id));

		if (curated != null) {
			params.put("curated", curated.toString());
		} else {
			params.put("curated", "30");
		}
		if (curable != null) {
			params.put("curable", curable.toString());
		} else {
			params.put("curable", "0");
		}
		if (ncomments != null) {
			params.put("ncomments", ncomments.toString());
		} else {
			params.put("ncomments", "0");
		}
		JsonNode root = doGet(TOPIC_ENDPOINT, params);
		if (root == null) {
			return null;
		}
		Topic model;
		try {
			model = mapper.readValue(root.get("topic"), Topic.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return model;
	}

	// post

	private static final String POST_ENDPOINT = "/api/1/post";

	public Post getPost(long id) throws ScoopApiExecutionException {
		return getPost(id, null);
	}

	public Post getPost(long id, Integer ncomments)
			throws ScoopApiExecutionException {
		Multimap<String, String> params = LinkedListMultimap.create();
		params.put("id", Long.toString(id));
		if (ncomments != null) {
			params.put("ncomments", ncomments.toString());
		} else {
			params.put("ncomments", "0");
		}
		JsonNode root = doGet(POST_ENDPOINT, params);
		if (root == null) {
			return null;
		}
		Post model;
		try {
			model = mapper.readValue(root.get("post"), Post.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return model;
	}

    // Resolver
    private static String RESOLVER_ENDPOINT = "/api/1/resolver";

    public Long resolveId(String shortName, ResolverResult.Type type) throws ScoopApiExecutionException {
        Multimap<String, String> params = LinkedListMultimap.create();
        if (type != null) {
            params.put("type", type.toString());
        } else {
            params.put("type", "Topic");
        }
        params.put("shortName", shortName);
        JsonNode root = doGet(RESOLVER_ENDPOINT, params);
        if (root == null) {
            return null;
        }

        ResolverResult model;
        try {
            model = mapper.readValue(root, ResolverResult.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return model.id;
    }
}
