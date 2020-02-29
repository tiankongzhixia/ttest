package com.time.ttest.http;

import kong.unirest.GetRequest;
import kong.unirest.HttpRequestWithBody;
import kong.unirest.JsonPatchRequest;

public interface HttpTemplate{

     GetRequest get(String url);
     GetRequest head(String url);
     GetRequest options(String url);
     HttpRequestWithBody post(String url);
     HttpRequestWithBody delete(String url);
     HttpRequestWithBody patch(String url);
     HttpRequestWithBody put(String url);
     JsonPatchRequest jsonPatch(String url);
     HttpRequestWithBody request(String method, String url);
}
