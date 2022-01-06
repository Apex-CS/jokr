package apex.ingagers.ecommerce.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;


import apex.ingagers.ecommerce.repository.UserRepository;

import static java.lang.String.format;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   
    private final UserRepository userRepository;
    private final JwtTokenFilter jwtTokenFilter;

    @Value("${cors.allowed-api}")
    private String myAllowedApi;
    @Value("${springdoc.api-docs.path}")
    private String restApiDocPath;
    @Value("${springdoc.swagger-ui.path}")
    private String swaggerPath;


    public SecurityConfig( UserRepository userRepository,JwtTokenFilter jwtTokenFilter) {
        super();


        this.userRepository = userRepository;
        this.jwtTokenFilter = jwtTokenFilter;

        // Inherit security context in async function calls
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }



    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     // auth.userDetailsService(username -> userRepository
    //     //         .findByName(username)
    //     //         .orElseThrow(
    //     //                 () -> new UsernameNotFoundException(
    //     //                         format("User: %s, not found", username)
    //     //                 )
    //     //         ));

    //     auth.inMemoryAuthentication()
    //     .withUser("user1").password("123").roles("APPRENTICE")
    //     .and()
    //     .withUser("user2").password("123").roles("SENSEI");
    // }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and();

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            // logger.error("Unauthorized request - {}", ex.getMessage());
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                        }
                )
                .and();

        // Set permissions on endpoints
        http.authorizeRequests()
                // Swagger endpoints must be publicly accessible
                 .antMatchers("/").permitAll()
                 .antMatchers(format("%s/**", restApiDocPath)).permitAll()
                 .antMatchers(format("%s/**", swaggerPath)).permitAll()
                //.antMatchers("/swagger-resources/**").permitAll();
                // Our public endpoints
                 .antMatchers("/api/v1/public/**").permitAll()
                 .antMatchers(HttpMethod.GET, "/api/v1/products").permitAll()
                // .antMatchers(HttpMethod.POST, "/api/author/search").permitAll()
                // .antMatchers(HttpMethod.GET, "/api/book/**").permitAll()
                // .antMatchers(HttpMethod.POST, "/api/book/search").permitAll();
                // Our private endpoints
                 .anyRequest().authenticated();

        // Add JWT token filter
         http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // // Used by spring security if CORS is enabled.
    // @Bean
    // public CorsFilter corsFilter() {
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     CorsConfiguration config = new CorsConfiguration();
    //     config.setAllowCredentials(false);
    //     config.addAllowedOrigin(myAllowedApi);
    //     config.addAllowedHeader("*");
    //     config.addAllowedMethod("*");
    //     source.registerCorsConfiguration("/**", config);
    //     return new CorsFilter(source);
    // }

    // // Expose authentication manager bean
    // @Override @Bean
    // public AuthenticationManager authenticationManagerBean() throws Exception {
    //     return super.authenticationManagerBean();
    // }

}