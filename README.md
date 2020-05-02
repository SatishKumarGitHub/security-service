**Spring Security:**

1) By default spring add the form based security when we add the spring boot security dependency in our pom.xml


2) Basic Auth 
    
        client wants to access the server 
        if a client sends a request it will get 401-UnAuthorized status
        you need to specify username and password as a req. header as Base64
        **Need to specify username and password for every single req.**
        our username and password will be encoded as base64 string
        Server does the validation whether username exists then check the password which sever knows 
        then server sends the 200 status code whatever the client requested.
        **Base auth will be good when we access external APIs**
        There is no option for logout because client needs to send username and password for very single req.
        
        
 
3) Ant matcher (White List endpoints/urls)

    white list some of endpoints 
    
4) To user our own username and password

    `protected UserDetailsService userDetailsService()`override this method  and create a bean for UserDetailsService
    
5) password should be encoded 
    to achieve this create bean for PasswordEncoder bean 
    
6) The Predefined  Interface UserDetails does not have any roles and permissions 
    
    It has only collections of GrantedAuthority 
    
    hence we are removing roles() in the user permissions
    
7) Order to define antmatchers()
    
    order of ant matchers really matter
    
    `.antMatchers("/api/**").hasAnyRole(STUDENT.name())
    .antMatchers("/management/api/**").hasAnyRole(ADMIN.name(),ADMIN_TRAINEE.name())
                     .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
                     .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
                     .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(STUDENT_WRITE.getPermission())
                  //   .antMatchers("/management/api/**").hasAnyRole(ADMIN.name(),ADMIN_TRAINEE.name())`
                  
     spring security will check 
     
     Tom you are a student : Nope
     Tom you are Admin or admin trainee yes then skip all other ant matchers for the same pattern
     
     In this case admin trainee will get the write access
     
     
8) preAuthorize();

    If you want to user permission based access on method level you have to user @PreAuthorize annotation 
    
    Syntax:
    
    String : `hasRole('ROLE_') or hasAnyRole('ROLE_','ROLE_') or hasAuthority('permission') or hasAnyAuthority('student:read','student:write')`
    @PreAuthorize
    
    And we have to tell our configuration that we are using method level permission 
   ` @EnableGlobalMethodSecurity(prePostEnabled = true)`
 
9) CSRF

    Cross Site Request Forgery 
    
    How CSRF works:
    1) when client login form browser spring security validate the request and send the CSRF token in the cookie 
    2) And Browser/ front end will use the token for any form submissions ( that are PUT, POST and DELETE)
    3) Spring security matches the CSRF Token whether it has the same token which is sent previously 


    When to use CSRF 
    
    -> Use CSRF production for any req. that could be processed by a browser by normal users 
    -> If you are creating a service that is used by non browser clients, you will likely want to disable CSRF Production
    



