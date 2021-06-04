package com.example.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

import org.springframework.jdbc.core.ResultSetExtractor;
/**
 * 投稿記事を操作するリポジトリクラス.
 * 
 * @author kyoichiro.tomatsu
 *
 */
@Repository
public class ArticleRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	/** マッパー */
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = new BeanPropertyRowMapper<>(Article.class);
	
	private static class ArticleResultSetExtractor implements ResultSetExtractor<List<Article>> {
		
		@Override
		public List<Article> extractData(ResultSet rs) throws SQLException,DataAccessException{
			
			List<Article> articleList = new ArrayList<>();
			
			Article article = new Article();
			List<Comment> commentList = new ArrayList<>();
			
			while(rs.next()) {
				
				boolean articleIsEmpty = (article.getId() == null && article.getName() == null && article.getContent() == null && article.getCommentList() == null );

				if(articleIsEmpty) {
					article.setId(rs.getInt("article_id"));
					article.setName(rs.getString("article_name"));
					article.setContent(rs.getString("article_content"));
				}

				if(article.getId() != rs.getInt("article_id")) {
					article.setCommentList(commentList);
					articleList.add(article);
					
					article = new Article();
					
					article.setId(rs.getInt("article_id"));
					article.setName(rs.getString("article_name"));
					article.setContent(rs.getString("article_content"));
					commentList = new ArrayList<Comment>();
				}
				
				Comment comment = new Comment();
				
				comment.setId(rs.getInt("comment_id"));
				comment.setName(rs.getString("comment_name"));
				comment.setContent(rs.getString("comment_content"));
				comment.setArticleId(rs.getInt("article_id"));
				
				commentList.add(comment);
				
				
			}
			article.setCommentList(commentList);
			articleList.add(article);
			
			return articleList;
		}
		
		
		
	}
	
	
	/**
	 * 投稿記事全件検索
	 * @return 全ての投稿記事
	 */
	public List<Article> findAll(){
		
		//String sql = "SELECT id,name,content FROM articles ORDER BY id DESC";
		//List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
		
		ArticleResultSetExtractor articleResultSetExtractor = new ArticleResultSetExtractor();
		String sql = "SELECT "
				+ "a.id AS article_id,a.name AS article_name,a.content AS article_content,"
				+ "c.id AS comment_id,c.name AS comment_name,c.content AS comment_content "
				+ "FROM articles AS a "
				+ "LEFT OUTER JOIN comments AS c "
				+ "ON a.id=c.article_id "
				+ "ORDER BY a.id DESC,c.id DESC";
		
		List<Article> articleList = template.query(sql,articleResultSetExtractor);
		
		return articleList;
	}
	
	/**
	 * 記事の追加.
	 * 
	 * @param article　追加する記事
	 */
	public void insert(Article article) {
		
		String sql = "INSERT INTO articles(name,content) VALUES(:name,:content)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", article.getName()).addValue("content", article.getContent());
		template.update(sql, param);
		
	}
	
	/**
	 * 記事の削除.
	 * 
	 * @param article 削除する記事
	 */
	public void deleteById(int articleId) {
		
		String sql = "DELETE FROM articles WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", articleId);
		
		template.update(sql, param);
		
	}
	
	

}
