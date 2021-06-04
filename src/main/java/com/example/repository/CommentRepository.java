package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * コメントを操作するリポジトリクラス.
 * 
 * @author kyoichiro.tomatsu
 *
 */
/**
 * @author kyoichiro.tomatsu
 *
 */
@Repository
public class CommentRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** マッパー */
	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = new BeanPropertyRowMapper<>(Comment.class);
	
	/**
	 * ID検索.
	 * 引数のIDの投稿記事に付いているコメントを返します
	 * 
	 * @param articleId 投稿記事のID
	 * @return コメントの一覧
	 */
	public List<Comment> findByArticleId(int articleId){
		
		String sql = "SELECT id,name,content FROM comments WHERE article_id=:articleId ORDER BY id DESC";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		
		List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);
		
		return commentList;	
	}
	
	/**
	 * コメントの追加.
	 * 
	 * @param comment　追加するコメント
	 */
	public void insert(Comment comment) {
		
		String sql ="INSERT INTO comments(name,content,article_id) VALUES(:name,:content,:articleId)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name",comment.getName()).addValue("content", comment.getContent()).addValue("articleId", comment.getArticleId());
		
		template.update(sql, param);
	}
	
	/**
	 * コメントの削除.
	 * 
	 * @param articleId 削除するコメントID
	 */
	public void deleteByArticleId(int articleId) {

		String sql = "DELETE FROM comments WHERE article_id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", articleId);
		
		template.update(sql, param);
	}
	
	
	
}
