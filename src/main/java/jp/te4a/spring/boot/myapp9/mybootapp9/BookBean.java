package jp.te4a.spring.boot.myapp9.mybootapp9;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookBean {
 @Id
 @GeneratedValue
 private Integer id ;
 @Column(nullable = false)
 private String title;
 private String writter;
 private String publisher;
 private Integer price;
}

