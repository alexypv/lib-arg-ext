package su.opencode.library.web.secure;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public WebSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        // включаем защиту от CSRF атак;
        httpSecurity.csrf()
                .disable()
                // указываем правила запросов
                // по которым будет определятся доступ к ресурсам и остальным данны
                .authorizeRequests()

                .antMatchers(
                        "/api/catalogs/createCatalog",
                        "/api/catalogs/deleteCatalog",
                        "/api/books/createBook",
                        "/api/books/editBook",
                        "/api/books/deleteBook",
                        "/api/books/removeBookFromCatalog",
                        "/api/books/moveBook",
                        "/api/books/getBooksForCreateOrder",
                        "/api/orders/createOrder"

                ).hasAnyRole("LIBRARIER", "ADMIN", "GLOBAL")

                .antMatchers(
                        "directory",
                        "/api/catalogs/loadCatalogs",
                        "/api/books/openCatalog/**",
                        "/api/books/openBook/**",
                        "/info/**"
                ).permitAll().anyRequest().authenticated().and()
                .apply(new JwtConfigurer(jwtTokenProvider)).and()

                //Изменяю сообщение при ошибке доступа
                .exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен");
        });

        httpSecurity.formLogin()
                // указываем страницу с формой логина
                .loginPage("/login")
                // указываем action с формы логина
                .loginProcessingUrl("/j_spring_security_check")
                .defaultSuccessUrl("/home", true)
                // указываем URL при неудачном логине
                .failureUrl("/login?error")
                // Указываем параметры логина и пароля с формы логина
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                // даем доступ к форме логина всем
                .permitAll().and()
                .exceptionHandling();

        httpSecurity.logout()
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                .logoutUrl("/logout")
                // указываем URL при удачном логауте
                .logoutSuccessUrl("/login")
                // делаем не валидной текущую сессию
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling();

    }
}