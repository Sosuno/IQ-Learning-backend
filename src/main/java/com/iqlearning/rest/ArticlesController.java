package com.iqlearning.rest;

import com.iqlearning.database.entities.Articles;
import com.iqlearning.database.entities.Comment;
import com.iqlearning.database.entities.User;
import com.iqlearning.database.service.interfaces.IArticlesService;
import com.iqlearning.database.service.interfaces.IUserService;
import com.iqlearning.database.utils.ArticleComment;
import com.iqlearning.database.utils.FullArticle;
import com.iqlearning.rest.resource.ArticleForm;
import com.iqlearning.rest.resource.CommentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins=("http://localhost:3000"))
public class ArticlesController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IArticlesService articlesService;

    @PostMapping("/article/add")
    public ResponseEntity<?> sendMessage(@RequestHeader Map<String, String> headers, @RequestBody ArticleForm articleForm) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if (user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(articleForm.getContent() == null) return new ResponseEntity<>("Article content is empty", HttpStatus.BAD_REQUEST);
        if(articleForm.getTitle() == null) return new ResponseEntity<>("Article title is empty", HttpStatus.BAD_REQUEST);
        Articles articles = new Articles(user.getId(), articleForm.getContent(), articleForm.getTitle(), 0, new Timestamp(System.currentTimeMillis()), new Long[0], articleForm.getTags(), articleForm.getDescription());
        //if(articleForm.getImage() != null) articles.setImage(articleForm.getImage());
        Articles saved = articlesService.saveArticle(articles);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @GetMapping("/article/get")
    public ResponseEntity<?> getAllArticles(@RequestHeader Map<String, String> headers) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if (user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        List<FullArticle> articleList = articlesService.getAll();
        return new ResponseEntity<>(articleList, HttpStatus.OK);
    }

    @GetMapping("/article/get/user")
    public ResponseEntity<?> getUserArticles(@RequestHeader Map<String, String> headers) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if (user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        List<FullArticle> articleList = articlesService.getUserArticles(user.getId());
        return new ResponseEntity<>(articleList, HttpStatus.OK);
    }

    @GetMapping("/article/get/comments/{id}")
    public ResponseEntity<?> getArticleComments(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if (user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        List<ArticleComment> commentList = articlesService.getArticleComments(id);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }

    @PostMapping("/comment/add")
    public ResponseEntity<?> postComment(@RequestHeader Map<String, String> headers, @RequestBody CommentForm commentForm) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if (user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(commentForm.getId() == null) return new ResponseEntity<>("Specify article id", HttpStatus.BAD_REQUEST);
        if(articlesService.getArticle(commentForm.getId()) == null)  return new ResponseEntity<>("Article with id " + commentForm.getId() + " doesn't exist", HttpStatus.BAD_REQUEST);
        if(commentForm.getComment() == null) return new ResponseEntity<>("Cannot add empty comment", HttpStatus.BAD_REQUEST);
        Comment comment = new Comment(commentForm.getId(), user.getId(), commentForm.getComment(), 0, new Timestamp(System.currentTimeMillis()), new Long[0]);
        Comment saved = articlesService.saveComment(comment);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @PutMapping("/comment/update")
    public ResponseEntity<?> editComment(@RequestHeader Map<String, String> headers, @RequestBody CommentForm commentForm) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if (user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        if(commentForm.getId() == null) return new ResponseEntity<>("Specify comment id", HttpStatus.BAD_REQUEST);
        Comment comment = articlesService.getComment(commentForm.getId());
        if(comment == null) return new ResponseEntity<>("Comment with id " + commentForm.getId() + " doesn't exist", HttpStatus.BAD_REQUEST);
        if(commentForm.getComment() != null) comment.setComment(commentForm.getComment());
        Comment updated = articlesService.saveComment(comment);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PutMapping("/article/update")
    public ResponseEntity<?> editArticle(@RequestHeader Map<String, String> headers, @RequestBody ArticleForm articleForm) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if (user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        Articles articles;
        if(articleForm.getId() != null)  articles = articlesService.getArticle(articleForm.getId());
        else return new ResponseEntity<>("Article id cannot be empty", HttpStatus.BAD_REQUEST);
        if(articles == null) return new ResponseEntity<>("Article with id " + articleForm.getId() + " doesn't exist", HttpStatus.BAD_REQUEST);
        if(user.getId() != articles.getOwner()) return new ResponseEntity<>("You are not the owner", HttpStatus.FORBIDDEN);
        if(articleForm.getContent() != null) articles.setContent(articleForm.getContent());
        if(articleForm.getTitle() != null) articles.setTitle(articleForm.getTitle());
        if(articleForm.getTags() != null) articles.setTags(articleForm.getTags());
        //if(articleForm.getImage() != null) articles.setImage(articleForm.getImage());
        Articles saved = articlesService.saveArticle(articles);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @PutMapping("/article/upvote/{id}")
    public ResponseEntity<?> upvoteArticle(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if (user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        Articles upvoted = articlesService.upvoteArticle(id,user.getId());
        return new ResponseEntity<>(upvoted, HttpStatus.OK);
    }

    @PutMapping("/comment/upvote/{id}")
    public ResponseEntity<?> upvoteComment(@RequestHeader Map<String, String> headers, @PathVariable Long id) {
        String session = headers.get("authorization").split(" ")[1];
        User user = userService.getUserBySession(session);
        if (user.getId() == -1) return new ResponseEntity<>("No active session", HttpStatus.UNAUTHORIZED);
        Comment upvoted = articlesService.upvoteComment(id,user.getId());
        return new ResponseEntity<>(upvoted, HttpStatus.OK);
    }


}
