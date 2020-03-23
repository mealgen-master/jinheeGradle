package com.jinhee2.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.jinhee2.model.OauthClientDetails;
import com.jinhee2.model.OauthToken;
import com.jinhee2.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path="/api/oauth2")
//@RequestMapping(value="/api/oauth2", produces= MediaTypes.HAL_JSON_VALUE)
public class Oauth2Controller {
	@Autowired
	private Gson gson;
	
	@Autowired
    private RestTemplate restTemplate;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;

	@GetMapping(value="/user")
	public String user() {
		return "public";
	}
	
	@GetMapping(value="/admin")
	public String admin() {
		return "private";
	}
	
	@GetMapping(value="/callback")
	public OauthToken callbackSocial(
			@RequestParam String code
	) {
		String credentials = "강진희:강진희바보";
		String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 클라이언트 id, secret을 autorization_code 타입으로 지정
        headers.add("Authorization", "Basic " + encodedCredentials);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        // 클라이언트 id로 DB에 등록한 grant_types의 값으로 refreshToken, authorization(Auth서버에 요청할 정보)을 등록
        // Auth 서버 인증 설정을 통해 저장된 값과 입력된 값을 비교 -> 코드(code) 발급 = 하나의 Key가 된다. = Auth서버의 정보(token)를 요청하는 통신시 사용
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "http://localhost:8080/oauth2/callback");
        
//        params.add("redirect_uri", "http://49.50.165.170:8080/oauth2/callback");
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        // request = code, 리소스 접근방식, redirect_uri, basic autho code 
        
        // restTemplate = 동기방식 통신
        // code를 통해 이미 인증된 클라이언트임을 확인하고 token 발급
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/oauth/token", request, String.class);
        
        // 클라우드용
//        ResponseEntity<String> response = restTemplate.postForEntity("http://49.50.165.170:8080/oauth/token", request, String.class);
        
        if (response.getStatusCode() == HttpStatus.OK) {
        	return gson.fromJson(response.getBody(), OauthToken.class);
        }
        
        return null;
	}
	
	@GetMapping(value="/insertClientDetails")
	public String insertClientDetails(
		@RequestParam(name="client_id") String client_id,
		// 리소스 보안 null 가능
		@RequestParam(name="resource_ids", required=false) String resource_ids,
		@RequestParam(name="client_secret") String client_secret,
		// 발급된 accessToken에 대해서 리소스에 접근할 수 있는 권한에 대한 스코프
		@RequestParam(name="scope") String scope,
		// 승인방식의 종류( 여러개 )
		@RequestParam(name="authorized_grant_types") String authorized_grant_types,
		@RequestParam(name="web_server_redirect_uri") String web_server_redirect_uri,
		// 클라이언트측 권한 null 가능
		@RequestParam(name="authorities", required=false) String authorities,
		@RequestParam(name="access_token_validity") Integer access_token_validity,
		@RequestParam(name="refresh_token_validity") Integer refresh_token_validity,
		// 추가적인 정보 null 가능
		@RequestParam(name="additional_information", required=false) String additional_information,
		// 사용자에게 스코프에 대한 요청을 할 것인지 여부 false
		@RequestParam(name="autoapprove") String autoapprove
	) {
		OauthClientDetails oauthClientDetails = new OauthClientDetails();
		
		oauthClientDetails.setClient_id(client_id);
		oauthClientDetails.setResource_ids(resource_ids);
		oauthClientDetails.setClient_secret(passwordEncoder.encode(client_secret));
		oauthClientDetails.setScope(scope);
		oauthClientDetails.setAuthorized_grant_types(authorized_grant_types);
		oauthClientDetails.setWeb_server_redirect_uri(web_server_redirect_uri);
		oauthClientDetails.setAuthorities(authorities);
		oauthClientDetails.setAccess_token_validity(access_token_validity);
		oauthClientDetails.setRefresh_token_validity(refresh_token_validity);
		oauthClientDetails.setAdditional_information(additional_information);
		oauthClientDetails.setAutoapprove(autoapprove);
		
		userService.insertClientDetails(oauthClientDetails);
		
		return "client " + client_id + " 저장완료";
	}
}
