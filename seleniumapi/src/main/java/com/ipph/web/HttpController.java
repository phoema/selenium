package com.ipph.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(value="/http",method={RequestMethod.POST,RequestMethod.GET})
public class HttpController {
 
	

    /**
     * Oauth2.0二次握手，根据code或者REFRESH_TOKEN的值返回AccessToken
     * @param code
     * @return
     * @throws Exception 
     */
    @RequestMapping(value="/token")
    public String token(String code,String refreshToken) throws Exception {
        return "";
        
    }

    
}