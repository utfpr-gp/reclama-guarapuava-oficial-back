package br.edu.utfpr.reclamaguarapuava.security.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.google.common.collect.ImmutableList;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_MATCHERS = {"/h2-console/**", "/v2/api-docs", "/swagger-ui.html",
        "/swagger-resources/**", "/configuration/ui", "/configuration/security", "/webjars/**"};

    private static final String[] PUBLIC_MATCHERS_READ = {"/api/v1/ocorrencias/**", "/api/v1/comentarios/**"};

    private static final String[] PUBLIC_MATCHERS_WRITE = {"/users/register", "/api/v1/comentarios/**"};

    private static final String[] PUBLIC_MATCHERS_DELETE = {"/api/v1/comentarios/**"};

    private final Environment environment;
    private final UserDetailsService userDetailsService;
    private final JWTUtil jwtUtil;

    @Autowired
    public WebSecurityConfig(Environment environment,
            @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, JWTUtil jwtUtil) {
        this.environment = environment;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ifEnvTestConfigureH2(http);

        http.authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll()
                .antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_READ).permitAll()
                .antMatchers(HttpMethod.DELETE, PUBLIC_MATCHERS_DELETE).permitAll()
                .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_WRITE).permitAll().anyRequest().authenticated().and()
                .cors().and().csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    private void ifEnvTestConfigureH2(HttpSecurity http) throws Exception {
        if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        config.setAllowedMethods(ImmutableList.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
