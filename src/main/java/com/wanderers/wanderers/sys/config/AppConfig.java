package com.wanderers.wanderers.sys.config;

import com.wanderers.wanderers.sys.security.JWTAuthenticationFilter;
import com.wanderers.wanderers.sys.security.JWTLoginFilter;
import com.wanderers.wanderers.sys.security.UserService;
import com.wanderers.wanderers.sys.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity  //启动springsecurity的web支持
public class AppConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private UserService userService;

    @Autowired
    private Environment env;

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**") //哪些请求需要跨域
                .allowedOrigins("*")
                .allowedHeaders("token")
                .exposedHeaders("token")
                .allowCredentials(true)
                .allowedMethods("DELETE","POST","GET","PUT")
                .maxAge(3600);
    }

    /**
     * 配置jwtutils的静态属性
     */
    @Bean
    @Lazy(false) //不允许懒加载
    public int readJWTConfig(){
        JWTUtils.setExpire(env.getProperty("JWT.expire",Long.class,6000000L));
        JWTUtils.setSecret(env.getProperty("JWT.secret"));
        return  1; //代表加载优先级最高
    }

    /**
     * 配置spring-security的权限验证的规则
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        JWTLoginFilter jwtLoginFilter = new JWTLoginFilter(authenticationManager());
        //设置登录请求地址
        jwtLoginFilter.setFilterProcessesUrl("signIn");

        http.cors() //允许跨域
            .and()  //and代表上一个配置完成了后回到父节点继续进行配置
            .httpBasic().disable() //不允许进行httpBasic的验证
            .csrf().disable() //关闭csrf
            .authorizeRequests() //设置不同路径的权限验证规则
            .antMatchers("/api.signUp").permitAll() //对/api/signIn进行放行
            .anyRequest().authenticated() //除了上面放行的路径，都要登录验证
            .and().addFilter(jwtLoginFilter)  //第一个filter用于登录权限控制
            .addFilter(new JWTAuthenticationFilter(authenticationManager()));//第二个filter用于验证登录后用户的具体权限
        //两个filter都不需要实现验证逻辑，只是对jwt的序列化和反序列化

    }

    /**
     * 配置加密算法
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 配偶之后userService
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}
