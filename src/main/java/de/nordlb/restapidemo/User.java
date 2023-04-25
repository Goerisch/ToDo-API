package de.nordlb.restapidemo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID userId;

  @Column(unique = true, nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @OneToMany
  @JoinColumn (name="userId")
  private Set<Todo> todos;

  private String secret;

  public UUID getUserId() {
    return userId;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<Todo> getTodos() {
    return todos;
  }

  public void setTodos(Set<Todo> todos) {
    this.todos = todos;
  }

  public String getSecret() {
    return secret;
  }

  @JsonIgnore
  public void setSecret(String secret) {
    this.secret = secret;
  }
}
