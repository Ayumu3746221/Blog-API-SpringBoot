## Blog API

フロントエンドとバックエンドに分けた初めて個人開発、DB周りのCRUD操作とアクセス制御を実装したものを目指す。

## 概要

- 目的：Web Application開発の開発の流れを抑えるため、独学でやってきた限界点を確認するため。
- 主な機能（API）
  a\.DBへのCRUD操作を可能にするAPI
  b\.OAuth2で発行されたTokenを検証するフィルター
  c\.Cross設定（アクセス制限）
  d\.GCPに関連ファイルをアップロードする機能

## 使用技術

**言語とフレームワーク**

- java 21
- SpringBoot
- PostgreSQL

**デプロイ先等**

- Render.com
- Google Cloud Storage
- Google Cloud SQL for PostgreSQL

※Tokenの検証以外はGoogleのライブラリを使用

**AI（LLM）**

- Gemini2.0
- ChatGPT4o
- GitHub Copilot
- V0

## 実際のコード

**エンドポイント**

下記は公開APIです、指定のエンドポイント https://my-blog-api-au9m.onrender.com/api/public/v1/published/articles のようにPostman等から確認できるようにしてあります。

```java
@RestController
@RequestMapping("/api/public/v1")
public class PublicContentsController {

    @Autowired
    private ContentService contentService;

    @GetMapping("/published/articles")
    public List<PublishedContentListDTO> getPublishedArticles() {
        return contentService.getPublishedArticles();
    }

    @GetMapping("/articles/{contentId}")
    public ArticleDTO getArticleByContentId(@PathVariable("contentId") Integer contentId) {
        return contentService.getArticleByContentId(contentId);
    }
}
```

このような感じで主要機能のエンドポイントを実装しています。詳しくは[エンドポイント](./blogAPI/src/main/java/com/example/blog/controller)を閲覧ください。

**ビジネスロジック**

下記は記事の取得,編集,削除を行うためのサービスクラスです。
全文は[サービスクラス](./blogAPI/src/main/java/com/example/blog/service)をご参照ください。

```java
@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public List<PublishedContentListDTO> getPublishedArticles() {
        return contentRepository.findPublishedArticles();
    }

    public List<AllContentListDTO> getAllArticles() {
        return contentRepository.findAllArticles();
    }

    public ArticleDTO getArticleByContentId(Integer contentId) {
        return contentRepository.findArticleByContentId(contentId);
    }

    public void updateArticle(UpdateArticleDTO updateArticleDTO) {

        Content oldContent = contentRepository.findByContentId(updateArticleDTO.getContentId());
        Content newContent = updateArticleDTO.updateEntity(oldContent, updateArticleDTO.getTitle(), updateArticleDTO.getExcerpt(), updateArticleDTO.getImageUrl(), updateArticleDTO.getIsPublished());

        contentRepository.save(newContent);
    }

    public Content createArticle(ContentDTO contentDTO , String contentUrl) {
        Content newContent = contentDTO.toEntity(contentUrl);
        Content savedContent = contentRepository.save(newContent);
        return savedContent;
    }

    public void deleteArticle(Integer contentId) {
        contentRepository.deleteById(contentId);
    }
}

```

**アクセス制御**

下記でフィルターやCross設定を実装しています。Filterは後述します。
全文は[セキュリティ設定](./blogAPI/src/main/java/com/example/blog/config/SecurityConfig.java)をご参照ください

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.cross-origin.enabled.url}")
    private String crossOriginEnabled;

    @Autowired
    private GoogleTokenFilter googleTokenFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf((csrf) -> csrf.disable())
            .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").authenticated()
            .requestMatchers("/api/public/**").permitAll()
            .anyRequest().permitAll()
            )
            .addFilterBefore(googleTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(crossOriginEnabled));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

```

**フィルター**

Google OAuth2の認可を通過したアクセスかどうかを検証するFilterです。
なんとか動かせていますが半分理解できていないのが現在の課題、できればGoogleが公開しているライブラリを使いたいのですが上手く動作しないためURIにリクエストを送る形式を採用しています。」

全文は[フィルター](./blogAPI/src/main/java/com/example/blog/Security/GoogleTokenFilter.java)をご参照ください。

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.cross-origin.enabled.url}")
    private String crossOriginEnabled;

    @Autowired
    private GoogleTokenFilter googleTokenFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf((csrf) -> csrf.disable())
            .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**").authenticated()
            .requestMatchers("/api/public/**").permitAll()
            .anyRequest().permitAll()
            )
            .addFilterBefore(googleTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(crossOriginEnabled));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

```

## 反省点

- 最初からライブラリを使うべきだった、Google Cloud Storageを扱うときに初期は直接URIにリクエストを送っていたがエラーの温床。
- アクセス制御は早めに実装するべきだった、最後の工程で実装したがもっと早くて良かった。
- APIを作り切ってからUIを構成するべき、後からの追加が多すぎて都度UIを変更していた、工数がかなり増えた。
- APIをもっと分割してファイル削除とDB削除は分けて行った方が良い、公開するAPIなら今のままでも良い
- テストコードを勉強するべき
