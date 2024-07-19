
package com.blog.web.repository;

import com.blog.web.dto.ArticleDto;
import com.blog.web.dto.RegistrationDto;
import com.blog.web.models.Article;
import com.blog.web.models.UserEntity;
import com.blog.web.services.ArticleService;
import com.blog.web.services.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ArticleRepositoryTests {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;


    @Test
    @WithMockUser(username="test", password="blah")
    public void ArticleRepository_Search_ReturnSearch() {
        // Arrange
        RegistrationDto userDto = new RegistrationDto(
                "test",
                "test@example.com",
                "blah"
        );
        userService.saveUser(userDto);
        UserEntity user = userService.findByUsername("test");
        final ArticleDto articleDto1 = new ArticleDto(
                1,
                "Title",
                "https://duckduckgo.com",
                "Content",
                user,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        articleService.saveArticle(articleDto1);
        final ArticleDto articleDto2 = new ArticleDto(
                2,
                "itleblah",
                "https://duckduckgo.com",
                "Content",
                user,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        articleService.saveArticle(articleDto2);
        final ArticleDto articleDto3 = new ArticleDto(
                3,
                "dontfindme",
                "https://duckduckgo.com",
                "Content",
                user,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        articleService.saveArticle(articleDto3);

        // Act
        List<Article> list = articleRepository.searchArticles("itle"); // partial string should find 2 articles

        // Assert
        Assertions.assertEquals(2, list.size());
    }
}
