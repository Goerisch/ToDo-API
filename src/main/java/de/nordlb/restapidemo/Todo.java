package de.nordlb.restapidemo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity
public class Todo {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String description;
  private boolean isDone;

  private UUID userId;

  public UUID getUserId() {
    return userId;
  }

  public void setUserId(UUID userId) {
    this.userId = userId;
  }

  public void setId(int id){
    this.id = id;
  }
  public int getId(){
    return this.id;
  }
  public void setDescription(String description){
    this.description=description;
  }
  public String getDescription(){
    return this.description;
  }

  public void setIsDone(boolean isDone){
    this.isDone = isDone;
  }

  public boolean getIsDone(){
    return this.isDone;
  }

}
