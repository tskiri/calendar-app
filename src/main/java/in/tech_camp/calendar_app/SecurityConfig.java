package in.tech_camp.calendar_app;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 📋 1. CSRF対策を無効化（Next.jsなどの外部アプリと通信するAPI開発では必須の設定です）
            .csrf(AbstractHttpConfigurer::disable)
            
            // 📋 2. 🌟PicTweetスタイルのCORS一括許可設定！
            // これにより、フロント（ポート3000）からのアクセスをブラウザがブロックしなくなります。
            .cors(cors -> cors
                .configurationSource(request -> {
                    var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
                    // フロントエンド（Next.js）のURLを許可
                    corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000"));
                    // GET, POST, DELETEなど、すべての操作方法を許可
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                    // クッキーや認証情報の同行を許可
                    corsConfiguration.setAllowCredentials(true);
                    // すべてのヘッダーを許可
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    return corsConfiguration;
                })
            )
            
            // 📋 3. 通信のアクセス許可ルール
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                // 💡 初めてカレンダーアプリを作る段階では、他のAPIを叩いた時に403エラーで弾かれないよう、
                // 一旦すべてのリクエストを「ログインなしで誰でもアクセスOK（permitAll）」に設定しておきます。
                // 今後ログイン機能を実装していく時に、ここをPicTweetのように細かく制限していきます！
                .anyRequest().permitAll()
            );

        return http.build();
    }

    // 📋 4. パスワード暗号化の部品
    // 今後、ユーザー登録やログイン機能をカレンダーアプリに足す時に必要になるので、最初から用意しておきます。
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}