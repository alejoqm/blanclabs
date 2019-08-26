import com.alejoqm.controller.OktaServiceClient;
import com.okta.jwt.AccessTokenVerifier;
import com.okta.jwt.Jwt;
import com.okta.jwt.JwtVerificationException;
import com.okta.jwt.JwtVerifiers;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.client.ClientBuilder;
import org.junit.Test;

public class OktaTest {

	private final String token = "eyJraWQiOiJXa09nYlRWUERhN1huaXNUOUlKUWlZM005SnRPdGctZ2h2MkUwTWlTRUxVIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULkI5Uk5Gd3I1QTNnd1Z3YlZXUWx2UTdGblFidGJRMW5ZX2gzazROUjk2ekEiLCJpc3MiOiJodHRwczovL2V4aWdlcnRydXN0Lm9rdGFwcmV2aWV3LmNvbS9vYXV0aDIvYXVza3Awc2duNERERElZMXQwaDciLCJhdWQiOiJkZGlxLWRldiIsImlhdCI6MTU2MzM5NDA3NSwiZXhwIjoxNTYzMzk3Njc1LCJjaWQiOiIwb2Frb3h5YTVyWUszT0toNzBoNyIsInVpZCI6IjAwdWx2emNoam9SbFZWa2FDMGg3Iiwic2NwIjpbIm9wZW5pZCJdLCJzdWIiOiJhbGVqYW5kcm9xQGJsYW5jbGFicy5jb20iLCJyb2xlcyI6WyJERElRLUFETUlOIiwiRERJUS1VU0VSIl0sInJlZ2lvbiI6eyJzdHJlZXRfYWRkcmVzcyI6bnVsbCwiY291bnRyeSI6bnVsbCwid2Vic2l0ZSI6bnVsbCwiem9uZWluZm8iOiJBbWVyaWNhL0xvc19BbmdlbGVzIiwiYmlydGhkYXRlIjpudWxsLCJnZW5kZXIiOm51bGwsImZvcm1hdHRlZCI6bnVsbCwicHJvZmlsZSI6bnVsbCwibG9jYWxpdHkiOm51bGwsInVzZXJOYW1lIjoiYWxlamFuZHJvcUBibGFuY2xhYnMuY29tIiwiZ2l2ZW5fbmFtZSI6Ikx1aXMiLCJtaWRkbGVfbmFtZSI6bnVsbCwibG9jYWxlIjoiZW4tVVMiLCJwaWN0dXJlIjpudWxsLCJuYW1lIjoiTHVpcyBRdWludGVybyIsIm5pY2tuYW1lIjpudWxsLCJwaG9uZV9udW1iZXIiOm51bGwsInJlZ2lvbiI6bnVsbCwicG9zdGFsX2NvZGUiOm51bGwsImZhbWlseV9uYW1lIjoiUXVpbnRlcm8iLCJlbWFpbCI6ImFsZWphbmRyb3FAYmxhbmNsYWJzLmNvbSIsInN0YXR1cyI6IkFDVElWRSJ9fQ.gvHDlnuiFZLBtVShuxKs2MVkZHtSe8mdOi6QfGxKD-IqyFgA8WHZU6qJwdWuq_tMymKiRP-jM95UG_wUTcEVPWoYg7p61-byEAiyvmkXnlXuT5kkdK61IykK0kwN8e4FRJnte62mV6mzZhl3wBUzm5ri7nmxDJdkwAX51pGFhiMIczzARptMds9xzykr-A8rg3YiPqfT_3vLc-Tg7PgFn42LsHpXs982FJ5GquLVwOy2T5uF6zFvePDwUnLglzoZy6PFPgM-MRtOZwL4Z5m4m9a37l9OCs4sBWNm7hrAa7DHSe5_9w7QRV7Xrq9dscF9toB_wxogbiGE_vrVoTIrKA";
	private final String badToken = "a";

	@Test
	public void token() {

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

		AccessTokenVerifier jwtVerifier = JwtVerifiers.accessTokenVerifierBuilder()
				.setIssuer("https://exigertrust.oktapreview.com/oauth2/default")
				.setAudience("api://default")
				.setConnectionTimeout(Duration.ofSeconds(1))
				.setReadTimeout(Duration.ofSeconds(1))
				.build();

		try {
			Jwt jwt = jwtVerifier.decode(token.replace("Bearer ", ""));
			Map<String, Object> user = jwt.getClaims();
			if(Instant.now().isAfter(jwt.getExpiresAt())) {
				System.out.println("Exceded time");
			}

		} catch (JwtVerificationException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void validate() throws IOException {
		String jwksLink = "https://exigertrust.oktapreview.com/oauth2/auskp0sgn4DDDIY1t0h7/v1/keys";

		SigningKeyResolver resolver = new SigningKeyResolverAdapter() {
			public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
				try {
					Map<String, String> jwk = getKeyById(getJwks(jwksLink), jwsHeader.getKeyId());
					BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(jwk.get("n")));
					BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(jwk.get("e")));

					return KeyFactory.getInstance("RSA").generatePublic(new RSAPublicKeySpec(modulus, exponent));
				} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
					System.out.println(e);
					return null;
				}
			}
		};

		Jws<Claims> jwsClaims = Jwts.parser()
				.setSigningKeyResolver(resolver)
				.parseClaimsJws(token);

		Map<String, Object> region = jwsClaims.getBody().get("region", Map.class);
		System.out.println(region.get("zoneinfo"));
		System.out.println(region.get("userName"));
		System.out.println(region.get("given_name"));
		System.out.println(region.get("name"));
		System.out.println(region.get("email"));
		System.out.println(region.get("status"));


		List<String> roles = jwsClaims.getBody().get("roles", List.class);
		roles.stream().forEach(rol -> System.out.println(rol));



		Map<String, Object> ret = new HashMap<>();
		ret.put("validateResponse", jwsClaims.getBody());
		ret.put("jwksLink", jwksLink);

		System.out.println(jwsClaims.getBody());
		System.out.println(jwksLink);

	}

	@SuppressWarnings("unchecked")
	private Map<String, String> getKeyById(Map<String, Object> jwks, String kid) {
		List<Map<String, String>> keys = (List<Map<String, String>>)jwks.get("keys");
		Map<String, String> ret = null;
		for (int i = 0; i < keys.size(); i++) {
			if (keys.get(i).get("kid").equals(kid)) {
				return keys.get(i);
			}
		}
		return ret;
	}

	private Map<String, Object> getJwks(String jwksLink) throws IOException {
		return ClientBuilder.newClient().target(jwksLink).request().get().readEntity(Map.class);
	}
}
