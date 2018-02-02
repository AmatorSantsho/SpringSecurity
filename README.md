Spring Security. 

Проект реализован на основе Spring MVC архитектуры

Используемые технологии:

       java version 1.8
       spring version 5.0.2
       spring-security version.5.0
       logback version 1.2.3
       postgresql 9.5
       maven 3.0.5
       tomcat 8.0.27

Подключение к проекту Spring Security.

1. Добавляем зависимости в проект в pom.xml:

       <dependency>
             <groupId>org.springframework.security</groupId>
             <artifactId>spring-security-web</artifactId>
            <version>${spring-security.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-config</artifactId>
            <version>${spring-security.version}</version>
        </dependency>

2.Добавляем фильтр  в файл web.xml:

      <filter>
        <filter-name>springSecurityFilterChain</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
      </filter>
    <filter-mapping>
        <filter-name>springSecurityFilterChain</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

3. Производим настройку конфигурации spring-security в xml файле:

       <beans:beans xmlns="http://www.springframework.org/schema/security"
             xmlns:beans="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-     beans.xsd
       http://www.springframework.org/schema/security http://www.springframework.org/schema/security/spring-security.xsd">

       <http pattern="/resources/**" security="none"/>
       <http>

        <intercept-url pattern="/log" access="isAnonymous()"/>
        <intercept-url pattern="/admin" access="hasRole('ROLE_ADMIN')"/>
        <intercept-url pattern="/**" access="isAuthenticated()"/>
        <form-login login-processing-url="/spring_security_check"
                    login-page="/log"
                    authentication-failure-url="/log?error=true"
                    />
        <logout
                logout-success-url="/log"
                logout-url="/logout"
                delete-cookies="JSESSIONID"/>
        <csrf disabled="true"/>
        <access-denied-handler error-page="/accessDenied"/>

       </http>
       <beans:bean class="org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder" id="passwordEncoder"/>

       <authentication-manager>
        <authentication-provider user-service-ref="userServiceImpl">
            <password-encoder ref="passwordEncoder"/>
        </authentication-provider>
       </authentication-manager>
       </beans:beans>

Элемент http позволяет настроить авторизацию в приложении.

Установив атрибут элемента use-expressions="true" мы разрешаем использовать выражения SpEL для определения контроля доступа к приложению по указанному url.

Например:
       
       <http use-expressions="true">
       <intercept-url pattern="/log*" access="isAnonymous()" />
       intercept-url – накладываем ограничение на доступ к приложению по адре-су(url), указанному в атрибуте pattern. В атрибуте access указывается SpEL выражение, результатом выполнения которого всегда будет тип Boolean.
Результат вычисляется на этапе работы приложения для данного user как указано в таблице:

       Expression                  Description
       hasRole([role])             Returns true if the current principal has the specified role.
       hasAnyRole([role1,role2])   Returns true if the current principal has any of the supplied roles 
       principal                   Allows direct access to the principal ob-ject representing the current user
       authentication              Allows direct access to the current Authenti-cation object obtained from the SecurityContext
       permitAll                   Always evaluates to true
       denyAll                     Always evaluates to false
       isAnonymous()               Returns true if the current principal is an anonymous user
       isRememberMe()              Returns true if the current principal is a remember-me user
       isAuthenticated()           Returns true if the user is not anonymous
       isFullyAuthenticated()      Returns true if the user is not an anonymous or a remember-me user

Также, авторизованный доступ можно прикручивать на методы, для этого в настройку контекста приложения нужно добавить, следующий элемент:

       <global-method-security secured-annotations="enabled"/>
Пример  наложения ограничения в методе:

          public class UserService {
          @PreAuthorize("hasRole('admin')")
          public Collection<? extends GrantedAuthority> getAuthorities(UserDetails userDetails) {
          return userDetails.getAuthorities();
          }
          
Аннотация @PreAuthorize("hasRole('admin')") означает, что доступ к методу getAuthorities получат пользователи с правами admin.

       <http pattern="/resources/**" security="none"/>

security="none" - позволяет отключить проверку безопасности для указанного url /resources/**".
Элемент form-login настраивает форму для аутентификации. Если ее оставить пустой спринг перенаправит запрос на дефолтную преоставляемую спрингом форму логина.
Атрибуты элемента form-login позволяют настроить ее:
login-page – страница для логина (созданная самим);
default-target-url – страница для перехода в случае успешной аутентифи-кации;
authentication-failure-url - страница для перехода в случае неуспешной аутентификации;
logout logout-success-url- адрес для перехода при логауте;

       <http-basic />  - подключение механизма базовой аутентификации (логин и па-роль передаются в headere  в формате base64-encoded)

Если атрибут элемента http  выставить  auto-config="true" то в настройки автоматически будут включены некоторые основные сервисы:

       <form-login />
       <http-basic />
       <logout />
Процесс аутентификации собственно происходит менеджером аутентификации, который настраивается в элементе authentication-manager.
Существуют различные виды  аутентификации на сервере:
 - inMemory  - аутентификация, когда данные о пользователях и паролях хранятся захардкодировано в коде на сервере, например в xml-файле. Например:

       <authentication-manager>
               <authentication-provider >
                   <user-service>
        <user name="user@yandex.ru" password="password" authorities="ROLE_USER"/>
        <user name="admin@gmail.com" password="admin" authorities="ROLE_ADMIN"/>
                   </user-service>
               </authentication-provider>
           </authentication-manager>
authorities="ROLE_USER" – указывает, какие роли получит юзер, после успешной аутентификации.
 - JDBC аутентификация – когда, данные о пользователях достаются из БД на основании sql-запросов. Пример настройки:

       <jdbc-user-service data-source-ref="dataSource"
       users-by-username-query="SELECT email, password, enabled FROM users WHERE email = ?"
       authorities-by-username-query="SELECT u.email, r.role FROM users u, user_roles r WHERE u.id = r.user_id AND u.email = ?" />

 - UserDetailsService аутентификация
Реализация собственного  authentication-provider путем имплементации спринговского UserDetailsService интерфейса (нужно реализовать метод UserDetails loadUserByUsername(String username))  и вернуть заполненного UserDetails.

intercept-url pattern="/**"  - следует указывать последним в списке всех паттернов при настройке доступа к приложению.

Пароль в открытом виде не рекомендуется указывать. Спринг требует наличия декодера или указания типа декодирования. Например {noop}password. Пароль имеет префикс {noop}, чтобы указать DelegatingPasswordEncoderу, что  необходимо использовать NoOpPasswordEncoder. Это не безопасно для производства, но делает чтение в примерах проще. Обычно пароли должны быть хэшированы с использованием BCrypt. Spring начиная с версии 5.0 по умолчанию пароли кодирует, поэтому без указания encodera  приложение работать не будет.


       

Branches:

InMemory_Auth - example of configuring in-memory authentication for a single user.

JDBC_Auth - example of using JDBC based authentication. To use you need Postgresql DB.


