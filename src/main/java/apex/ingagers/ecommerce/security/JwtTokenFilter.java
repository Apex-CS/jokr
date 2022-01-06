package apex.ingagers.ecommerce.security;

import apex.ingagers.ecommerce.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.List.of;
import static java.util.Optional.ofNullable;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JWTUtil jwtTokenUtil;
    private final UserRepository userRepository;

    public JwtTokenFilter (JWTUtil jwtTokenUtil, UserRepository userRepository){
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        
        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        Claims claims = jwtTokenUtil.validateAuthorities(token);
        if(claims.get("authorities") != null){
            setUpSpringAuthentication(claims);
            chain.doFilter(request, response);
        }


        String andamosaqui = "valemadres";
        // Get user identity and set it on the spring security context
        // UserDetails userDetails = userRepository
        //         .findByName(jwtTokenUtil.getUsername(token))
        //         .orElse(null);

        // UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        //         userDetails, null,
        //         ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(of())
        // );

        // authentication
        //         .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // SecurityContextHolder.getContext().setAuthentication(authentication);
        // chain.doFilter(request, response);
    }

    private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List) claims.get("authorities");

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);

	}
}
