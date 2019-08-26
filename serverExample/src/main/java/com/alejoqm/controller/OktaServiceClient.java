package com.alejoqm.controller;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.jsonwebtoken.impl.DefaultJwtParser;
import java.security.Key;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

public class OktaServiceClient {

	private static final String clientId = "0oakoxya5rYK3OKh70h7";
	private static final String clientSecret = "0OJFGfpLhQoJHCC1gx6xgpAez861h9aiqUtDJ27v";
	private static final String baseUrl = "https://exigertrust.oktapreview.com/oauth2/auskp0sgn4DDDIY1t0h7/v1/token";
	private static final String redirectUri = "http://localhost:3030/oktaCallback?";

	private static final String token = "eyJraWQiOiJXa09nYlRWUERhN1huaXNUOUlKUWlZM005SnRPdGctZ2h2MkUwTWlTRUxVIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULmFUWkxJTHd3bXJsOTFnTnZFdFBtcFR6TGZLS2xFYjBSMzIwb2U2aDhHaE0iLCJpc3MiOiJodHRwczovL2V4aWdlcnRydXN0Lm9rdGFwcmV2aWV3LmNvbS9vYXV0aDIvYXVza3Awc2duNERERElZMXQwaDciLCJhdWQiOiJkZGlxLWRldiIsImlhdCI6MTU2MjY4NjE0NywiZXhwIjoxNTYyNjg5NzQ3LCJjaWQiOiIwb2Frb3h5YTVyWUszT0toNzBoNyIsInVpZCI6IjAwdWx2emNoam9SbFZWa2FDMGg3Iiwic2NwIjpbIm9wZW5pZCJdLCJzdWIiOiJhbGVqYW5kcm9xQGJsYW5jbGFicy5jb20iLCJyZWdpb24iOnsic3RyZWV0X2FkZHJlc3MiOm51bGwsImNvdW50cnkiOm51bGwsIndlYnNpdGUiOm51bGwsInpvbmVpbmZvIjoiQW1lcmljYS9Mb3NfQW5nZWxlcyIsImJpcnRoZGF0ZSI6bnVsbCwiZ2VuZGVyIjpudWxsLCJmb3JtYXR0ZWQiOm51bGwsInByb2ZpbGUiOm51bGwsImxvY2FsaXR5IjpudWxsLCJ1c2VyTmFtZSI6ImFsZWphbmRyb3FAYmxhbmNsYWJzLmNvbSIsImdpdmVuX25hbWUiOiJMdWlzIiwibWlkZGxlX25hbWUiOm51bGwsImxvY2FsZSI6ImVuLVVTIiwicGljdHVyZSI6bnVsbCwibmFtZSI6Ikx1aXMgUXVpbnRlcm8iLCJuaWNrbmFtZSI6bnVsbCwicGhvbmVfbnVtYmVyIjpudWxsLCJyZWdpb24iOm51bGwsInBvc3RhbF9jb2RlIjpudWxsLCJmYW1pbHlfbmFtZSI6IlF1aW50ZXJvIiwiZW1haWwiOiJhbGVqYW5kcm9xQGJsYW5jbGFicy5jb20iLCJzdGF0dXMiOiJBQ1RJVkUifX0.x55fEo2MAouPiU77v5l4lR8hXbTuTrrSnG0A27cOwCkElhFXxNhzIWuct3SJAD8-yHqqtK-1XBRgicWhnQAfjGFdcvPqFG_QNA-9mJ3GjR22kpeLG59i8Y-AgjXUySpUWRbjvWoRr9GnKF7TP9oYHQdOTSn35HwQ64iLXv6tUv6S9xdF-DKhxDip2ny2Uqaa0BNK4nl2y7tRsoafEmd-C1Em7NxqjNyH3WPPFrYME1LVvsrs407i1b1iX07mZAYnUcWfeUj4ZXDvD27ttDpl5rLLS3nEgEZZ3G5O1k10EN_yJ80GwtL-kNdJ7_vV3EG1-7ketfkjKTUzdSrCZ3BmiQ\n";

	private Client client;

	public OktaServiceClient() {
	}

	public String getToken(final String authorizationCode) {
		Form form = new Form();
		form.param("grant_type", "authorization_code").param("code", authorizationCode).param("client_id", clientId)
				.param("client_secret", clientSecret).param("redirect_uri", redirectUri);
		Entity<Form> entity = Entity.form(form);
		Response response = ClientBuilder.newClient().target(baseUrl).request().post(entity);
		Map values = response.readEntity(Map.class);

		System.out.println(values.get("token_type"));
		System.out.println(values.get("expires_in"));
		System.out.println(values.get("access_token"));
		return String.valueOf(values.get("access_token"));
	}

	public void decodeToken() {
		/*DecodedJWT jwt = JWT.decode(token);
		jwt.getClaim("region").asMap().get("userName");*/
		final Claims[] claims2 = new Claims[1];
		SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter() {
			@Override
			public Key resolveSigningKey(JwsHeader header, Claims claims) {
				claims2[0] = claims;
				System.out.println(claims.get("region", Map.class).get("userName"));
				// Examine header and claims
				return null; // will throw exception, can be caught in caller
			}
		};

		try {
			Jwts.parser()
					.setSigningKeyResolver(signingKeyResolver)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// no signing key on client. We trust that this JWT came from the server and has been verified there
		}
		System.out.println(claims2[0].get("region", Map.class).get("userName"));

	}


}
