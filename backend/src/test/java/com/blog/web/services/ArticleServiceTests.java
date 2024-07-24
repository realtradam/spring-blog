package com.blog.web.services;

import com.blog.web.dto.ArticleDto;
import com.blog.web.models.Article;
import com.blog.web.models.UserEntity;
import com.blog.web.repository.ArticleRepository;
import com.blog.web.repository.UserRepository;
import com.blog.web.security.SecurityUtil;
import com.blog.web.services.impl.ArticleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

//@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ExtendWith(MockitoExtension.class)
public class ArticleServiceTests {

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ArticleServiceImpl articleService;

    @Test
    @WithMockUser(username="test", password="blah")
    public void ArticleService_SaveArticle_ReturnsArticle() {
        //RegistrationDto userDto = new RegistrationDto(
        //        "test",
        //        "test@example.com",
        //        "blah"
        //);
        //userService.saveUser(userDto);
        UserEntity user = new UserEntity();
        user.setUsername("test");
        user.setEmail("test@example.com");
        user.setPassword("blah");
        //UserEntity user = userService.findByUsername("test");
        final ArticleDto articleDto = new ArticleDto(
                1,
                "Title",
                "https://duckduckgo.com",
                "Content",
                user,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(articleRepository.save(any(Article.class))).then(returnsFirstArg());
        //when(SecurityUtil.getSessionUser()).thenReturn(user.getUsername());
        try(MockedStatic<SecurityUtil> utilities = Mockito.mockStatic(SecurityUtil.class)) {
            utilities.when(SecurityUtil::getSessionUser).thenReturn(user.getUsername());
            Assertions.assertEquals(user.getUsername(), SecurityUtil.getSessionUser());
            Article article = articleService.saveArticle(articleDto).orElse(null);

            Assertions.assertNotNull(article);
        }
    }
}
