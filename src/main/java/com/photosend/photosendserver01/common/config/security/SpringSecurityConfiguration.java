package com.photosend.photosendserver01.common.config.security;


import com.photosend.photosendserver01.common.util.file.KeyValueFileLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private KeyValueFileLoader keyValueFileLoader;

    @Value("${photosend.credential.admin.file-path}")
    private String WEB_ADMIN_CREDENTIAL_FILE_PATH;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String adminUserName = keyValueFileLoader.getValueFromFile(WEB_ADMIN_CREDENTIAL_FILE_PATH, "username");
        String adminUserPassword = keyValueFileLoader.getValueFromFile(WEB_ADMIN_CREDENTIAL_FILE_PATH, "password");

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
                .inMemoryAuthentication()
                .withUser(adminUserName)
                .password(encoder.encode(adminUserPassword))
                .roles("admin");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/admin/products", "/admin/orders")
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable();
    }
}
